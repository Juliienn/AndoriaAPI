package fr.elysiumapi.database.player;

import fr.elysiumapi.commons.exceptions.PlayerDataNotFoundException;
import fr.elysiumapi.commons.ranks.ElysiumRanks;
import fr.elysiumapi.database.ElysiumDatabase;
import fr.elysiumapi.database.redis.JedisManager;
import fr.elysiumapi.database.sql.DatabaseManager;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.UUID;

public class PlayerManager {

    private final ElysiumDatabase elysiumDatabase;

    public PlayerManager(ElysiumDatabase elysiumDatabase){
        this.elysiumDatabase = elysiumDatabase;
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

    public PlayerData getPlayerDataFromRedis(UUID uuid) throws PlayerDataNotFoundException {
        Jedis jedis = elysiumDatabase.getJedisConnector().getJedisRessource();
        String jedisDatas = jedis.get(JedisManager.PLAYERS.getRedisAccess() + uuid.toString());
        String[] accountDatas = jedisDatas.split(":");
        PlayerData playerData = new PlayerData(Integer.parseInt(accountDatas[0]), uuid, accountDatas[1], ElysiumRanks.nameToRank(accountDatas[2]), Integer.parseInt(accountDatas[3]), Integer.parseInt(accountDatas[4]), new Timestamp(Long.parseLong(accountDatas[5])));
        jedis.close();
        return playerData;
    }

    public PlayerData getPlayerDataFromDatabase(UUID uuid, String name) throws PlayerDataNotFoundException{
        try {
            final Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, pseudo, grade, money, vip, createdat FROM players WHERE uuid = ?");
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
                Timestamp timestamp = resultSet.getTimestamp("createdat");
                return new PlayerData(0, uuid, name, ElysiumRanks.nameToRank(rankName), money, vip, timestamp);
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
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid,pseudo,grade,money,vip,createdat) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, "Hinin");
            preparedStatement.setInt(4, 1000);
            preparedStatement.setInt(5, 0);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Jedis jedis = elysiumDatabase.getJedisConnector().getJedisRessource();
        jedis.set(JedisManager.PLAYERS.getRedisAccess() + uuid.toString(), "0:" +  name + ":Hinin:1000:0:" + System.currentTimeMillis());
        jedis.close();
    }

    public void sendPlayerDataToRedis(PlayerData playerData){
        Jedis jedis = elysiumDatabase.getJedisConnector().getJedisRessource();
        jedis.set(JedisManager.PLAYERS.getRedisAccess() + playerData.getUuid(), playerData.getId() + ":" + playerData.getName() + ":" + playerData.getRank().getPrefix() + ":" + playerData.getMoney() + ":" + playerData.getVip() + ":" + playerData.getCreationDate().getTime());
        jedis.close();
    }

    public void sendPlayerDataToSQL(PlayerData playerData){
        try {
            final Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET pseudo = ?, grade = ?, money = ?, vip = ? WHERE uuid = ?");
            preparedStatement.setString(1, playerData.getName());
            preparedStatement.setString(2, playerData.getRank().getPrefix());
            preparedStatement.setInt(3, playerData.getMoney());
            preparedStatement.setInt(4, playerData.getVip());
            preparedStatement.setString(5, playerData.getUuid().toString());
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