package fr.elysiumapi.database.redis;

public enum JedisManager {

    PLAYERS("players:"),
    RANKS("ranks:"),
    FACTION("factions:"),
    SANCTIONS("sanctions:");

    final String redisAccess;

    JedisManager(String redisAccess){
        this.redisAccess = redisAccess;
    }

    public String getRedisAccess() {
        return redisAccess;
    }
}
