package fr.andoriaapi.database.player;

import fr.andoriaapi.exceptions.PlayerDataNotFoundException;
import fr.andoriaapi.commons.ranks.AndoriaRanks;
import fr.andoriaapi.commons.ranks.PlayerRank;
import fr.andoriaapi.database.redis.JedisConnector;
import fr.andoriaapi.database.sql.DatabaseManager;
import fr.andoriaapi.utils.DurationUtils;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.UUID;

public class PlayerDataManager {

    private final JedisConnector jedisConnector;

    public PlayerDataManager(JedisConnector jedisConnector){
        this.jedisConnector = jedisConnector;
    }

    public PlayerData getPlayerData(UUID uuid, String name){
        PlayerData playerData = null;
        try {
            playerData = getPlayerDataFromRedis(uuid);
            if(playerData == null){
                playerData = getPlayerDataFromDatabase(uuid, name);
                sendPlayerDataToRedis(playerData);
            }
        } catch (PlayerDataNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return playerData;
    }

    public void sendPlayerDataToRedis(PlayerData playerData){
        Jedis jedis = jedisConnector.getJedisRessource();
        jedis.set("players:" + playerData.getUUID(), playerData.getName() + "/" + playerData.getMoney() + "/" + playerData.getPbs() + "/" + playerData.getCreationDate().getTime());
        jedis.set("grades:" + playerData.getUUID(), playerData.getRankInfos().getRank().getPrefix() + "/" + playerData.getRankInfos().getPurchasedDate().getTime() + "/" + playerData.getRankInfos().getExpirationDate().getTime());
        jedis.close();
    }

    public void sendPlayerDataToSQL(PlayerData playerData) {
        Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
        try (PreparedStatement playerStatement = connection.prepareStatement("UPDATE players SET name = ?, money = ?, pbs = ?, last_connection = ? WHERE uuid = ?");
             PreparedStatement rankStatement = connection.prepareStatement("UPDATE grades SET rank = ?, purchased_date = ?, expiredate = ? WHERE uuid = ?")) {

            // Mise à jour des données du joueur
            playerStatement.setString(1, playerData.getName());
            playerStatement.setInt(2, playerData.getMoney());
            playerStatement.setInt(3, playerData.getPbs());
            playerStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            playerStatement.setString(5, playerData.getUUID().toString());
            playerStatement.executeUpdate();

            // Mise à jour des données du grade
            rankStatement.setString(1, playerData.getRankInfos().getRank().getPrefix());
            rankStatement.setTimestamp(2, playerData.getRankInfos().getPurchasedDate());
            rankStatement.setTimestamp(3, playerData.getRankInfos().getExpirationDate());
            rankStatement.setString(4, playerData.getUUID().toString());
            rankStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public PlayerData getPlayerDataFromDatabase(UUID uuid, String name) throws PlayerDataNotFoundException {
        Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
        try (PreparedStatement playerStatement = connection.prepareStatement("SELECT name, money, pbs, creation_date, last_connection FROM players WHERE uuid = ?");
             PreparedStatement rankStatement = connection.prepareStatement("SELECT rank, purchased_date, expiredate FROM grades WHERE uuid = ?")) {

            playerStatement.setString(1, uuid.toString());
            ResultSet playerSet = playerStatement.executeQuery();

            if (playerSet.next()) {
                String pseudo = playerSet.getString("name");
                if (!(pseudo.equals(name))) {
                    pseudo = name;
                }
                int money = playerSet.getInt("money");
                int vip = playerSet.getInt("pbs");
                Timestamp creation_date = playerSet.getTimestamp("creation_date");
                Timestamp last_connection = playerSet.getTimestamp("last_connection");

                rankStatement.setString(1, uuid.toString());
                ResultSet rankSet = rankStatement.executeQuery();
                if (rankSet.next()) {
                    String rankName = rankSet.getString("grade");
                    Timestamp purchased_date = rankSet.getTimestamp("purchased_date");
                    Timestamp expiration_date = rankSet.getTimestamp("expiredate");
                    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                    if((currentDate).after(expiration_date)){
                        rankName = "Hinin";
                        purchased_date = currentDate;
                        expiration_date = new Timestamp(DurationUtils.TIMESTAMP_LIMIT);
                    }
                    PlayerRank playerRank = new PlayerRank(AndoriaRanks.nameToRank(rankName), purchased_date, expiration_date);
                    rankSet.close();
                    playerSet.close();
                    return new PlayerData(uuid, name, playerRank, money, vip, creation_date, last_connection);
                }
                return createAccount(uuid, name);
            }
            return createAccount(uuid, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public PlayerData getPlayerDataFromRedis(UUID uuid){
        Jedis jedis = jedisConnector.getJedisRessource();
        String playerDatas = jedis.get("players:" + uuid.toString());
        String gradeDatas = jedis.get("grades:" + uuid.toString());
        if(playerDatas == null){
            return null;
        }
        String[] accountDatas = playerDatas.split("/");
        String name = accountDatas[0];
        int money = Integer.parseInt(accountDatas[1]);
        int vip = Integer.parseInt(accountDatas[2]);
        Timestamp creation_date = new Timestamp(Long.parseLong(accountDatas[3]));
        Timestamp last_connection = new Timestamp(Long.parseLong(accountDatas[4]));

        String[] rankDatas = gradeDatas.split("/");
        String rankName = rankDatas[0];
        Timestamp purchased_date = new Timestamp(Long.parseLong(rankDatas[1]));
        Timestamp expiration_date = new Timestamp(Long.parseLong(rankDatas[2]));
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        if((currentDate).after(expiration_date)){
            rankName = "Hinin";
            purchased_date = currentDate;
            expiration_date = new Timestamp(DurationUtils.TIMESTAMP_LIMIT);
        }
        PlayerRank playerRank = new PlayerRank(AndoriaRanks.nameToRank(rankName), purchased_date, expiration_date);

        PlayerData playerData = new PlayerData(uuid, name, playerRank, money, vip, creation_date, last_connection);

        jedis.close();
        return playerData;
    }

    public PlayerData createAccount(UUID uuid, String name) {
        Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
        try (PreparedStatement playerStatement = connection.prepareStatement("INSERT INTO players (uuid, name, money, pbs, creation_date, last_connection) VALUES (?, ?, ?, ?, ?, ?)");
             PreparedStatement rankStatement = connection.prepareStatement("INSERT INTO grades (uuid, rank, purchased_date, expiredate) VALUES (?, ?, ?, ?)")) {

            playerStatement.setString(1, uuid.toString());
            playerStatement.setString(2, name);
            playerStatement.setInt(3, 1000);
            playerStatement.setInt(4, 0);
            playerStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            playerStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            playerStatement.executeUpdate();

            rankStatement.setString(1, uuid.toString());
            rankStatement.setString(2, "Hinin");
            rankStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            rankStatement.setTimestamp(4, new Timestamp(DurationUtils.TIMESTAMP_LIMIT));
            rankStatement.executeUpdate();

            Jedis jedis = jedisConnector.getJedisRessource();
            jedis.set("players:" + uuid.toString(), name + "/" + "1000/0/" + System.currentTimeMillis());
            jedis.set("grades:" + uuid.toString(), "Hinin/" + System.currentTimeMillis() + "/" + DurationUtils.TIMESTAMP_LIMIT);
            jedis.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new PlayerData(uuid, name, new PlayerRank(AndoriaRanks.HININ, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), 1000, 0, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }
}