package fr.elysiumapi.sanctions;

import fr.elysiumapi.database.sql.DatabaseManager;
import fr.elysiumapi.utils.SanctionsUtils;

import java.sql.*;
import java.util.UUID;

public class MuteManager {

    public static void mute(UUID uuid, String reason, String bannerName, Timestamp expireDate) {
        try (Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO muted (uuid, reason, bannername, expiredate, effectdate) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, reason);
            preparedStatement.setString(3, bannerName);
            preparedStatement.setTimestamp(4, expireDate);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unmute(UUID uuid) {
        try (Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM muted WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMutedList() {
        try (Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, reason, bannername, effectdate, expiredate FROM muted");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                String reason = resultSet.getString("reason");
                String bannerName = resultSet.getString("bannername");
                Timestamp effectDate = resultSet.getTimestamp("effectdate");
                Timestamp expireDate = resultSet.getTimestamp("expiredate");
                SanctionsUtils.getMutedList().put(uuid, new MuteInfo(uuid, reason, bannerName, effectDate, expireDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class MuteInfo{
        private final UUID uuid;
        private final String reason;
        private final String bannerName;
        private final Timestamp effectDate;
        private final Timestamp expireDate;

        public MuteInfo(UUID uuid, String reason, String bannerName, Timestamp effectDate, Timestamp expireDate) {
            this.uuid = uuid;
            this.reason = reason;
            this.bannerName = bannerName;
            this.effectDate = effectDate;
            this.expireDate = expireDate;
        }

        public UUID getUUID() {
            return uuid;
        }

        public String getReason() {
            return reason;
        }

        public String getBannerName() {
            return bannerName;
        }

        public Timestamp getEffectDate() {
            return effectDate;
        }

        public Timestamp getExpireDate() {
            return expireDate;
        }
    }
}