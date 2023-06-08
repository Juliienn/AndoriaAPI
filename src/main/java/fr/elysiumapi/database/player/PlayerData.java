package fr.elysiumapi.database.player;

import fr.elysiumapi.commons.ranks.ElysiumRanks;

import java.sql.Timestamp;
import java.util.UUID;

public class PlayerData {

    private final int id;
    private final UUID uuid;
    private final String name;
    private ElysiumRanks rank;
    private int money;
    private int vip;
    private final Timestamp createdat;

    public PlayerData(int id, UUID uuid,String name, ElysiumRanks rank, int money, int vip, Timestamp createdat){
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.vip = vip;
        this.createdat = createdat;
    }

    public void addMoney(int amount){
        this.money += amount;
    }

    public void addVip(int amount){
        this.vip += amount;
    }

    public void setRank(ElysiumRanks rank) {
        this.rank = rank;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ElysiumRanks getRank() {
        return rank;
    }

    public int getMoney() {
        return money;
    }

    public int getVip() {
        return vip;
    }

    public Timestamp getCreationDate() {
        return createdat;
    }

    public int getId() {
        return id;
    }
}