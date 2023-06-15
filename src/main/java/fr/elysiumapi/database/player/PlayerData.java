package fr.elysiumapi.database.player;

import fr.elysiumapi.commons.ranks.PlayerRank;

import java.sql.Timestamp;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private final String name;
    private final PlayerRank rank;
    private int money;
    private int vip;
    private final Timestamp createdat;

    public PlayerData(UUID uuid,String name, PlayerRank rank, int money, int vip, Timestamp createdat){
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.money = money;
        this.vip = vip;
        this.createdat = createdat;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public PlayerRank getRankInfos() {
        return rank;
    }

    public void creditMoney(int amount){
        this.money += amount;
    }

    public void removeMoney(int amount){
        this.money-=amount;
    }
    public int getMoney(){
        return this.money;
    }
    public void creditVIP(int amount){
        this.vip += amount;
    }

    public void removeVIP(int amount){
        this.vip-=amount;
    }
    public int getVIP(){
        return this.vip;
    }

    public Timestamp getCreationDate() {
        return createdat;
    }
}