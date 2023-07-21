package fr.andoriaapi.database.player;

import fr.andoriaapi.commons.ranks.PlayerRank;

import java.sql.Timestamp;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private final String name;
    private final PlayerRank rank;
    private int money;
    private int pbs;
    private final Timestamp creation_date;
    private Timestamp last_connection;

    public PlayerData(UUID uuid,String name, PlayerRank rank, int money, int pbs, Timestamp creation_date, Timestamp last_connection){
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.money = money;
        this.pbs = pbs;
        this.creation_date = creation_date;
        this.last_connection = last_connection;
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
    public void creditPbs(int amount){
        this.pbs += amount;
    }

    public void removePbs(int amount){
        this.pbs-=amount;
    }
    public int getPbs(){
        return this.pbs;
    }

    public Timestamp getCreationDate() {
        return creation_date;
    }

    public void setLastConnection(long time){
        this.last_connection = new Timestamp(time);
    }

    public Timestamp getLastConnection() {
        return last_connection;
    }
}