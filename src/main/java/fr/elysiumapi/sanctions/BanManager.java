package fr.elysiumapi.sanctions;

import fr.elysiumapi.database.sql.DatabaseManager;
import fr.elysiumapi.sanctions.infos.BanInfo;
import fr.elysiumapi.utils.SanctionsUtils;

import java.sql.*;
import java.util.UUID;

public class BanManager {

    public static void ban(UUID uuid, String reason, String bannerName, Timestamp expireDate) {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO banned (uuid, reason, bannername, permanently, effectdate, expiredate) VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, reason);
            preparedStatement.setString(3, bannerName);
            preparedStatement.setBoolean(4, expireDate == null);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(6, expireDate == null ? new Timestamp(System.currentTimeMillis()) : expireDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unban(UUID uuid) {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM banned WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadBanList() {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, reason, bannername, permanently, effectdate, expiredate FROM banned");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                String reason = resultSet.getString("reason");
                String bannerName = resultSet.getString("bannername");
                boolean permanently = resultSet.getBoolean("permanently");
                Timestamp effectDate = resultSet.getTimestamp("effectdate");

                Timestamp expireDate = null;
                if (!permanently) {
                    expireDate = resultSet.getTimestamp("expiredate");
                }
                SanctionsUtils.getBanList().put(uuid, new BanInfo(uuid, reason, bannerName, effectDate, expireDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
