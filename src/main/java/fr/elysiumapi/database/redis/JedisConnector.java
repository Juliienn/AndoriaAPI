package fr.elysiumapi.database.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnector {

    private JedisPool jedisPool;
    private final JedisConnection jedisConnection;

    public JedisConnector(JedisConnection jedisConnection){
        this.jedisConnection = jedisConnection;
    }

    public Jedis getJedisRessource(){
        return jedisPool.getResource();
    }

    public void killConnection(){
        jedisPool.destroy();
        jedisPool.close();
    }

    public void connect() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(-1);
        config.setJmxEnabled(false);
        try {
            this.jedisPool = new JedisPool(config, this.jedisConnection.getHost(), this.jedisConnection.getPort(), 0, this.jedisConnection.getPassword());
            this.jedisPool.getResource().close();

            System.out.println("§cConnected to redis");
        } catch (Exception e) {
            System.out.println("§cCan't connect to database");
        }
    }

    public JedisConnection getJedisConnection() {
        return jedisConnection;
    }
}
