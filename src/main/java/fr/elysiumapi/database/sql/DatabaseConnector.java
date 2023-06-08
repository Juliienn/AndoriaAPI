package fr.elysiumapi.database.sql;

public class DatabaseConnector {
    private final String host;
    private final String user;
    private final String pass;
    private final String databaseName;
    private final int port;

    public DatabaseConnector(String host, String user, String pass, String databaseName, int port){
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.databaseName = databaseName;
        this.port = port;
    }

    public String getUrl(){
        return "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?characterEncoding=utf8";
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

}