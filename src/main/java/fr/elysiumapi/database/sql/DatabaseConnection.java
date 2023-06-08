package fr.elysiumapi.database.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private final DatabaseConnector database;
    private HikariDataSource hikariDataSource;

    public DatabaseConnection(DatabaseConnector database){
        this.database = database;
    }

    private void setHikariCP(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(16);
        hikariConfig.setJdbcUrl(database.getUrl());
        hikariConfig.setUsername(database.getUser());
        hikariConfig.setPassword(database.getPass());
        hikariConfig.setMaxLifetime(600000L);
        hikariConfig.setIdleTimeout(300000L);
        hikariConfig.setLeakDetectionThreshold(300000L);
        hikariConfig.setConnectionTimeout(10000L);
        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public void initPool(){
        this.setHikariCP();
    }

    public void closePool(){
        this.hikariDataSource.close();
    }

    public Connection getConnection() throws SQLException {
        if(this.hikariDataSource == null){
            this.setHikariCP();
        }
        return hikariDataSource.getConnection();
    }
}
