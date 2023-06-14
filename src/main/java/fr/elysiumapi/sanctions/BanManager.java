package fr.elysiumapi.sanctions;

import fr.elysiumapi.database.sql.DatabaseManager;
import fr.elysiumapi.utils.SanctionsUtils;

import java.sql.*;
import java.util.UUID;

public class BanManager {

    public static void ban(UUID uuid, String reason, String bannerName, Timestamp expireDate) {
        try (Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO banned (uuid, reason, bannername, permanently, effectdate, expiredate) VALUES (?, ?, ?, ?, ?, ?)");
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
        try (Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM banned WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadBanList() {
        try (Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, reason, bannername, permanently, effectdate, expiredate FROM banned");
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


    public class BanInfo{

        private final UUID uuid;
        private final String reason;
        private final String bannerName;
        private final Timestamp effectDate;
        private final Timestamp expireDate;

        public BanInfo(UUID uuid, String reason, String bannerName, Timestamp effectDate, Timestamp expireDate) {
            this.uuid = uuid;
            this.reason = reason;
            this.bannerName = bannerName;
            this.effectDate = effectDate;
            this.expireDate = expireDate;
        }

        public boolean isPermanly(){
            return this.expireDate == null;
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