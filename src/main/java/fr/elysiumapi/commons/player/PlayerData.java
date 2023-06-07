package fr.elysiumapi.commons.player;

import fr.elysiumapi.commons.ranks.ElysiumRanks;

import java.sql.Timestamp;
import java.util.UUID;

public class PlayerData {

    private int id;
    private UUID uuid;
    private String name;
    private ElysiumRanks rank;
    private int money;
    private int vip;
    private Timestamp createdat;

    public PlayerData(int id, UUID uuid,String name, ElysiumRanks rank, int money, int vip, Timestamp createdat){
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.vip = vip;
        this.createdat = createdat;
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