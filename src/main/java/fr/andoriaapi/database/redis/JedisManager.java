package fr.andoriaapi.database.redis;

public enum JedisManager {

    PLAYERS("players:"),
    RANKS("ranks:"),
    FACTION("factions:");

    final String redisAccess;

    JedisManager(String redisAccess){
        this.redisAccess = redisAccess;
    }

    public String getRedisAccess() {
        return redisAccess;
    }
}
