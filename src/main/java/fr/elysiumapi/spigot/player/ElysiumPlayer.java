package fr.elysiumapi.spigot.player;

import fr.elysiumapi.spigot.ElysiumAPI;
import fr.elysiumapi.spigot.inventories.ElysiumInventory;
import fr.elysiumapi.commons.ranks.ElysiumRanks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class ElysiumPlayer {
    private Player player;
    private int id;
    private UUID uuid;
    private ElysiumRanks rank;
    private int money;
    private int vip;
    public ElysiumPlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    //public void connect(ServerList server){
    //    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    //    out.writeUTF("Connect");
    //    out.writeUTF(server.getName());
    //    this.player.sendPluginMessage(ElysiumAPI.getInstance(), "BungeeCord", out.toByteArray());
    //}

    public String getIP(Player player){
        return player.getAddress().getAddress().getHostAddress();
    }

    public void openInventory(ElysiumInventory inventory) {
        player.openInventory(inventory.getInventory());
    }





    public static ElysiumPlayer getElysiumPlayer(UUID uuid){
        if(ElysiumAPI.getPlayers().get(uuid) == null){
            ElysiumAPI.getPlayers().put(uuid, new ElysiumPlayer(Bukkit.getPlayer(uuid)));
        }
        return ElysiumAPI.getPlayers().get(uuid);
    }

    public static ElysiumPlayer getElysiumPlayer(Player player){
        if(ElysiumAPI.getPlayers().get(player.getUniqueId()) == null){
            ElysiumAPI.getPlayers().put(player.getUniqueId(), new ElysiumPlayer(player));
        }
        return ElysiumAPI.getPlayers().get(player.getUniqueId());
    }





    //RANK
    public void setRank(ElysiumRanks rank) {
        this.rank = rank;
    }

    public ElysiumRanks getRank() {
        return rank;
    }

    // MONEY
    public void addMoney(int amount){
        this.money +=amount;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    //VIP
    public void addVip(int amount){
        this.vip +=amount;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getVip() {
        return vip;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Player getPlayer() {
        return player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}