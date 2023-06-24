package fr.elysiumapi.spigot.player;

import fr.elysiumapi.spigot.inventories.ElysiumInventory;
import fr.elysiumapi.spigot.items.ItemBuilder;
import fr.elysiumapi.utils.PlayerUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ElysiumPlayer {

    private final Player player;
    private final List<ItemBuilder> items;

    public ElysiumPlayer(Player player){
        this.player = player;
        this.items = new ArrayList<>();
    }

    public void connect(String serverName){

    }

    public void addItem(ItemBuilder item){
        this.player.getInventory().addItem(item.getItem());
        items.add(item);
    }

    public void setItem(ItemBuilder item, int slot){
        this.player.getInventory().setItem(slot, item.getItem());
        items.add(item);
    }

    public void openInventory(ElysiumInventory inventory){
        this.player.openInventory(inventory.getInventory());
    }

    public void sendMessage(String message){
        this.player.sendMessage(message);
    }

    public String getIP(){
        return this.player.getAddress().getAddress().getHostAddress();
    }

    public UUID getUUID(){
        return this.player.getUniqueId();
    }

    public Player getPlayer() {
        return player;
    }

    public List<ItemBuilder> getItems() {
        return items;
    }

    public static ElysiumPlayer getIElysiumPlayer(UUID uuid){
        return PlayerUtils.elysiumPlayer.get(uuid);
    }
}