package fr.elysiumapi.database.sql;

public enum DatabaseManager {

    PLAYERS(new DatabaseConnector("localhost", "root", "", "elysium", 3306)),
    FACTIONS(new DatabaseConnector("localhost", "root", "", "factions", 3306)),
    SANCTIONS(new DatabaseConnector("localhost", "root", "", "sanctions", 3306));

    private final DatabaseConnection databaseConnection;

    DatabaseManager(DatabaseConnector databaseConnector) {
        this.databaseConnection = new DatabaseConnection(databaseConnector);
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public static void initConnectionWithoutHikari(DatabaseManager databaseManager){
        databaseManager.databaseConnection.initConnection();
    }

    public static void initConnection(DatabaseManager databaseManager){
        databaseManager.databaseConnection.initPool();
    }

    public static void initConnections(){
        for(DatabaseManager databaseManager : DatabaseManager.values()){
            databaseManager.databaseConnection.initPool();
        }
    }

    public static void closeConnections(){
        for(DatabaseManager databaseManager : DatabaseManager.values()){
            databaseManager.databaseConnection.closePool();
        }
    }
}