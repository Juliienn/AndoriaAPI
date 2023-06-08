package fr.elysiumapi.database.redis;

public class JedisConnection {

    private final String host;
    private final int port;
    private final String password;

    public JedisConnection(String host, int port, String password){
        this.host = host;
        this.port = port;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }
}
