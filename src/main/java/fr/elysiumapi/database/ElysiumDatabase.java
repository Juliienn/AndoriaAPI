package fr.elysiumapi.database;

import fr.elysiumapi.database.redis.JedisConnector;

public class ElysiumDatabase {

    public JedisConnector jedisConnector;

    public void setJedisConnector(JedisConnector jedisConnector) {
        this.jedisConnector = jedisConnector;
    }

    public JedisConnector getJedisConnector() {
        return jedisConnector;
    }
}
