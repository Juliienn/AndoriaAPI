package fr.elysiumapi.database.ban;

import fr.elysiumapi.database.sql.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class MuteManager {

    public static void mute(UUID uuid, String reason, String bannerName, Timestamp expireDate){
        try {
            Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO muted (uuid,reason,bannername,expiredate,effectdate) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, reason);
            preparedStatement.setString(3, bannerName);
            preparedStatement.setTimestamp(4, expireDate);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void unmute(UUID uuid){
        try {
            Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM muted WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}