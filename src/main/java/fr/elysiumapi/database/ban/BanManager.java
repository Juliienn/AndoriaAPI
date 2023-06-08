package fr.elysiumapi.database.ban;

import fr.elysiumapi.database.sql.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class BanManager {

    public static void ban(UUID uuid, String reason, String bannerName){
        try {
            Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO banned (uuid,reason,bannername,permanly,effectdate,expiredate) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, reason);
            preparedStatement.setString(3, bannerName);
            preparedStatement.setBoolean(4, true);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ban(UUID uuid, String reason, String bannerName, Timestamp expireDate){
        try {
            Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO banned (uuid,reason,bannername,permanly,effectdate,expiredate) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, reason);
            preparedStatement.setString(3, bannerName);
            preparedStatement.setBoolean(4, false);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(6, expireDate);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unban(UUID uuid){
        try {
            Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM banned WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
