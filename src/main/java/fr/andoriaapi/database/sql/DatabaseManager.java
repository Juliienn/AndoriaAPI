package fr.andoriaapi.database.sql;

public enum DatabaseManager {

    PLAYERS(new DatabaseConnector("51.254.39.58", "root", "Champagne2003@", "andoriaplayers", 3306)),
    FACTIONS(new DatabaseConnector("51.254.39.58", "root", "Champagne2003@", "andoriafactions", 3306)),
    SANCTIONS(new DatabaseConnector("51.254.39.58", "root", "Champagne2003@", "andoriasanctions", 3306));

    private final DatabaseConnection databaseConnection;

    DatabaseManager(DatabaseConnector databaseConnector) {
        this.databaseConnection = new DatabaseConnection(databaseConnector);
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public static void initConnection(DatabaseManager databaseManager){
        databaseManager.databaseConnection.initConnection();
    }

    public static void closeConnection(DatabaseManager databaseManager){
        databaseManager.databaseConnection.closeConnection();
    }
}