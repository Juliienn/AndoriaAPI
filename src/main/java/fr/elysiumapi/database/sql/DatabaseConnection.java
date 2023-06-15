package fr.elysiumapi.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private Connection connection;
    private final DatabaseConnector database;


    public DatabaseConnection(DatabaseConnector database){
        this.database = database;
    }

    public void initConnection(){
        try {
            this.connection = DriverManager.getConnection(database.getUrl(), database.getUser(), database.getPass());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if(this.connection == null){
            this.initConnection();
        }
        return connection;
    }
}
