package fr.elysiumapi.database.player;

import fr.elysiumapi.exceptions.PlayerDataNotFoundException;
import fr.elysiumapi.commons.ranks.ElysiumRanks;
import fr.elysiumapi.commons.ranks.PlayerRank;
import fr.elysiumapi.database.redis.JedisConnector;
import fr.elysiumapi.database.redis.JedisManager;
import fr.elysiumapi.database.sql.DatabaseManager;
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
            }
        } catch (PlayerDataNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return playerData;
    }

    public void sendPlayerDataToRedis(PlayerData playerData){
        Jedis jedis = jedisConnector.getJedisRessource();
        jedis.set(JedisManager.PLAYERS.getRedisAccess() + playerData.getUUID(), playerData.getName() + "/" + playerData.getMoney() + "/" + playerData.getVIP() + "/" + playerData.getCreationDate().getTime());
        jedis.set(JedisManager.RANKS.getRedisAccess() + playerData.getUUID(), playerData.getRankInfos().getRank().getPrefix() + "/" + playerData.getRankInfos().getPurchasedDate() + "/" + playerData.getRankInfos().getExpirationDate());
        jedis.close();
    }

    public void sendPlayerDataToSQL(PlayerData playerData) {
        try (Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
             PreparedStatement playerStatement = connection.prepareStatement("UPDATE players SET pseudo = ?, money = ?, vip = ? WHERE uuid = ?");
             PreparedStatement rankStatement = connection.prepareStatement("UPDATE grades SET grade = ?, purchased_date = ?, expiration_date = ? WHERE uuid = ?")) {

            // Mise à jour des données du joueur
            playerStatement.setString(1, playerData.getName());
            playerStatement.setInt(2, playerData.getMoney());
            playerStatement.setInt(3, playerData.getVIP());
            playerStatement.setString(4, playerData.getUUID().toString());
            playerStatement.executeUpdate();

            // Mise à jour des données du grade
            rankStatement.setString(1, playerData.getRankInfos().getRank().getPrefix());
            rankStatement.setTimestamp(2, playerData.getRankInfos().getPurchasedDate());
            rankStatement.setTimestamp(3, playerData.getRankInfos().getExpirationDate());
            rankStatement.setString(4, playerData.getUUID().toString());
            rankStatement.executeUpdate();

            // Validation de la transaction
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public PlayerData getPlayerDataFromDatabase(UUID uuid, String name) throws PlayerDataNotFoundException {
        try (Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
             PreparedStatement playerStatement = connection.prepareStatement("SELECT pseudo, money, vip, created_at FROM players WHERE uuid = ?");
             PreparedStatement rankStatement = connection.prepareStatement("SELECT grade, purchased_date, expiration_date FROM grades WHERE uuid = ?")) {

            playerStatement.setString(1, uuid.toString());
            ResultSet playerSet = playerStatement.executeQuery();

            if (playerSet.next()) {
                String pseudo = playerSet.getString("pseudo");
                if (!(pseudo.equals(name))) {
                    pseudo = name;
                }
                int money = playerSet.getInt("money");
                int vip = playerSet.getInt("vip");
                Timestamp creation_date = playerSet.getTimestamp("created_at");

                rankStatement.setString(1, uuid.toString());
                ResultSet rankSet = rankStatement.executeQuery();
                if (rankSet.next()) {
                    String rankName = rankSet.getString("grade");
                    Timestamp purchased_date = rankSet.getTimestamp("purchased_date");
                    Timestamp expiration_date = rankSet.getTimestamp("expiration_date");
                    PlayerRank playerRank = new PlayerRank(ElysiumRanks.nameToRank(rankName), purchased_date, expiration_date);
                    return new PlayerData(uuid, name, playerRank, money, vip, creation_date);
                } else {
                    createAccount(uuid, name);
                }
                rankSet.close();
            } else {
                createAccount(uuid, name);
            }
            playerSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public PlayerData getPlayerDataFromRedis(UUID uuid){
        Jedis jedis = jedisConnector.getJedisRessource();
        String playerDatas = jedis.get(JedisManager.PLAYERS.getRedisAccess() + uuid.toString());
        String gradeDatas = jedis.get(JedisManager.RANKS.getRedisAccess() + uuid.toString());
        if(playerDatas == null){
            return null;
        }
        String[] accountDatas = playerDatas.split("/");
        String name = accountDatas[0];
        int money = Integer.parseInt(accountDatas[1]);
        int vip = Integer.parseInt(accountDatas[2]);
        Timestamp creation_date = new Timestamp(Long.parseLong(accountDatas[3]));

        String[] rankDatas = gradeDatas.split("/");
        String rankName = rankDatas[0];
        Timestamp purchased_date = new Timestamp(Long.parseLong(rankDatas[1]));
        Timestamp expiration_date = new Timestamp(Long.parseLong(rankDatas[2]));
        PlayerRank playerRank = new PlayerRank(ElysiumRanks.nameToRank(rankName), purchased_date, expiration_date);

        PlayerData playerData = new PlayerData(uuid, name, playerRank, money, vip, creation_date);

        jedis.close();
        return playerData;
    }

    public void createAccount(UUID uuid, String name) {
        try (Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
             PreparedStatement playerStatement = connection.prepareStatement("INSERT INTO players (uuid, pseudo, money, vip, created_at) VALUES (?, ?, ?, ?, ?)");
             PreparedStatement rankStatement = connection.prepareStatement("INSERT INTO grades (uuid, grade, purchased_date, expiration_date) VALUES (?, ?, ?, ?)")) {

            playerStatement.setString(1, uuid.toString());
            playerStatement.setString(2, name);
            playerStatement.setInt(3, 1000);
            playerStatement.setInt(4, 0);
            playerStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            playerStatement.executeUpdate();

            rankStatement.setString(1, uuid.toString());
            rankStatement.setString(2, "Hinin");
            rankStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            rankStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            rankStatement.executeUpdate();

            Jedis jedis = jedisConnector.getJedisRessource();
            jedis.set(JedisManager.PLAYERS.getRedisAccess() + uuid.toString(), name + "/" + "1000/0/" + System.currentTimeMillis());
            jedis.set(JedisManager.RANKS.getRedisAccess() + uuid.toString(), "Hinin/" + System.currentTimeMillis() + "/" + System.currentTimeMillis());
            jedis.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}