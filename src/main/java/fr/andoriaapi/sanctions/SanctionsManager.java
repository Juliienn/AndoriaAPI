package fr.andoriaapi.sanctions;

import fr.andoriaapi.database.sql.DatabaseManager;
import fr.andoriaapi.sanctions.infos.SanctionsInfo;
import fr.andoriaapi.utils.DurationUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class SanctionsManager {

    private static final HashMap<UUID, SanctionsInfo> muted;
    private static final HashMap<UUID, SanctionsInfo> banned;


    static{
        muted = new HashMap<>();
        banned = new HashMap<>();
    }

    public static void apply(SanctionsType sanctionsType, UUID uuid, String reason, String bannerName, Timestamp expireDate) {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sanctions (uuid, type, reason, bannername, effectdate, expiredate) VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, sanctionsType.getTableName());
            preparedStatement.setString(3, reason);
            preparedStatement.setString(4, bannerName);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(6, expireDate == null ? new Timestamp(DurationUtils.TIMESTAMP_LIMIT) : expireDate);
            preparedStatement.executeUpdate();
            if(sanctionsType.equals(SanctionsType.MUTE)){
                muted.put(uuid, new SanctionsInfo(sanctionsType, uuid, reason, bannerName, new Timestamp(System.currentTimeMillis()), expireDate));
                return;
            }
            banned.put(uuid, new SanctionsInfo(sanctionsType, uuid, reason, bannerName, new Timestamp(System.currentTimeMillis()), expireDate));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove(SanctionsType sanctionsType, UUID uuid) {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM sanctions WHERE uuid = ?, type = ?")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, sanctionsType.getTableName());
            preparedStatement.executeUpdate();
            if(sanctionsType.equals(SanctionsType.MUTE)){
                muted.remove(uuid);
                return;
            }
            banned.remove(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadList() {
        Connection connection = DatabaseManager.SANCTIONS.getDatabaseConnection().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, type, reason, bannername, permanently, effectdate, expiredate FROM sanctions");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                String type = resultSet.getString("type");
                String reason = resultSet.getString("reason");
                String bannerName = resultSet.getString("bannername");
                Timestamp effectDate = resultSet.getTimestamp("effectdate");
                Timestamp expireDate = resultSet.getTimestamp("expiredate");
                SanctionsType sanctionsType = SanctionsType.fromName(type);
                if(sanctionsType.equals(SanctionsType.MUTE)){
                    muted.put(uuid, new SanctionsInfo(sanctionsType, uuid, reason, bannerName, effectDate, expireDate));
                }else{
                    banned.put(uuid, new SanctionsInfo(sanctionsType, uuid, reason, bannerName, effectDate, expireDate));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<UUID, SanctionsInfo> getMuted() {
        return muted;
    }

    public static HashMap<UUID, SanctionsInfo> getBanned() {
        return banned;
    }
}
