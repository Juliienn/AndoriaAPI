package fr.andoriaapi.utils;

import fr.andoriaapi.spigot.player.AndoriaPlayer;
import fr.andoriaapi.database.sql.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class PlayerUtils {

    public static HashMap<UUID, AndoriaPlayer> andoriaPlayers;

    public static UUID uuidFromName(String playerName){
        Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM players WHERE name = ?")){
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

    public static String nameFromUUID(UUID uuid){
        Connection connection = DatabaseManager.PLAYERS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM players WHERE uuid = ?")){
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}