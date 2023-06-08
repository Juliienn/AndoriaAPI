package fr.elysiumapi.database.redis;

public enum JedisManager {

    FACTION("factions:"),
    PLAYERS("players:"),
    SANCTIONS("sanctions:");

    final String redisAccess;

    JedisManager(String redisAccess){
        this.redisAccess = redisAccess;
    }

    public String getRedisAccess() {
        return redisAccess;
    }
}
