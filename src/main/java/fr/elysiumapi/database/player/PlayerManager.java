package fr.elysiumapi.database.player;

import fr.elysiumapi.commons.exceptions.PlayerDataNotFoundException;
import fr.elysiumapi.commons.ranks.ElysiumRanks;
import fr.elysiumapi.commons.ranks.PlayerRank;
import fr.elysiumapi.database.redis.JedisConnector;
import fr.elysiumapi.database.redis.JedisManager;
import fr.elysiumapi.database.sql.DatabaseManager;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.UUID;

public class PlayerManager {

    private final JedisConnector jedisConnector;

    public PlayerManager(JedisConnector jedisConnector){
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

    public PlayerData getPlayerDataFromRedis(UUID uuid){
        Jedis jedis = jedisConnector.getJedisRessource();
        PlayerData playerData = null;
        String jedisDatas = jedis.get(JedisManager.PLAYERS.getRedisAccess() + uuid.toString());
        if(jedisDatas == null){
            return null;
        }
        String[] accountDatas = jedisDatas.split(":");
        playerData = new PlayerData(Integer.parseInt(accountDatas[0]), uuid, accountDatas[1], new PlayerRank(ElysiumRanks.nameToRank(accountDatas[2]), new Timestamp(Long.parseLong(accountDatas[3])), new Timestamp(Long.parseLong(accountDatas[4]))), Integer.parseInt(accountDatas[5]), Integer.parseInt(accountDatas[6]), new Timestamp(Long.parseLong(accountDatas[7])));
        jedis.close();
        return playerData;
    }

    public PlayerData getPlayerDataFromDatabase(UUID uuid, String name) throws PlayerDataNotFoundException{
        try {
            final Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, pseudo, grade, expires_at, purchased_at, money, vip, created_at FROM players WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            final ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String pseudo = resultSet.getString("pseudo");
                if(!(pseudo.equals(name))){
                    pseudo = name;
                }
                String rankName = resultSet.getString("grade");
                int money = resultSet.getInt("money");
                int vip = resultSet.getInt("vip");
                Timestamp timestamp = resultSet.getTimestamp("created_at");
                return new PlayerData(0, uuid, name, new PlayerRank(ElysiumRanks.nameToRank(rankName), resultSet.getTimestamp("expires_at"), resultSet.getTimestamp("purchased_at")), money, vip, timestamp);
            }else {
                createAccount(uuid, name);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createAccount(UUID uuid, String name){
        try {
            Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid,pseudo,grade,expires_at,purchased_at,money,vip,created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, "Hinin");
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()+(long) 20*365*24*60*60*1000));
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(6, 1000);
            preparedStatement.setInt(7, 0);
            preparedStatement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Jedis jedis = jedisConnector.getJedisRessource();
        jedis.set(JedisManager.PLAYERS.getRedisAccess() + uuid.toString(), "0:" +  name + ":Hinin" + System.currentTimeMillis()+(long) 20*365*24*60*60*1000 + ":" + System.currentTimeMillis() + ":1000:0:" + System.currentTimeMillis());
        jedis.close();
    }

    public void sendPlayerDataToRedis(PlayerData playerData){
        Jedis jedis = jedisConnector.getJedisRessource();
        jedis.set(JedisManager.PLAYERS.getRedisAccess() + playerData.getUuid(), playerData.getId() + ":" + playerData.getName() + ":" + playerData.getRankInfos().getRank().getPrefix() + ":" + playerData.getRankInfos().getExpires_at().getTime() + ":" + playerData.getRankInfos().getPurchased_at().getTime() + ":" + playerData.getMoney() + ":" + playerData.getVip() + ":" + playerData.getCreationDate().getTime());
        jedis.close();
    }

    public void sendPlayerDataToSQL(PlayerData playerData){
        try {
            final Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET pseudo = ?, grade = ?, expires_at = ?, purchased_at = ?, money = ?, vip = ? WHERE uuid = ?");
            preparedStatement.setString(1, playerData.getName());
            preparedStatement.setString(2, playerData.getRankInfos().getRank().getPrefix());
            preparedStatement.setTimestamp(3, playerData.getRankInfos().getExpires_at());
            preparedStatement.setTimestamp(4, playerData.getRankInfos().getPurchased_at());
            preparedStatement.setInt(5, playerData.getMoney());
            preparedStatement.setInt(6, playerData.getVip());
            preparedStatement.setString(7, playerData.getUuid().toString());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UUID uuidFromName(String playerName){
        try {
            Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM players WHERE pseudo = ?");
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String uuid = resultSet.getString("uuid");
                return UUID.fromString(uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String nameFromUUID(UUID uuid){
        try {
            Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT pseudo FROM players WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("pseudo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}