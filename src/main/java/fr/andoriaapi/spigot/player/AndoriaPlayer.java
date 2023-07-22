package fr.andoriaapi.spigot.player;

import fr.andoriaapi.spigot.inventories.AndoriaInventory;
import fr.andoriaapi.spigot.items.ItemBuilder;
import fr.andoriaapi.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AndoriaPlayer {

    private final Player player;
    private AndoriaInventory openedInventory;
    private final List<ItemBuilder> items;

    public AndoriaPlayer(Player player){
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

    public void openInventory(AndoriaInventory inventory){
        this.openedInventory = null;
        this.player.openInventory(inventory.getInventory());
        this.openedInventory = inventory;
    }

    public void closeInventory(){
        this.openedInventory = null;
        player.closeInventory();
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

    public void setOpenedInventory(AndoriaInventory openedInventory) {
        this.openedInventory = openedInventory;
    }

    public AndoriaInventory getOpenedInventory() {
        return openedInventory;
    }

    public static AndoriaPlayer getAndoriaPlayer(UUID uuid){
        if(!(PlayerUtils.elysiumPlayer.containsKey(uuid))){
            PlayerUtils.elysiumPlayer.put(uuid, new AndoriaPlayer(Bukkit.getPlayer(uuid)) {
            });
        }
        return PlayerUtils.elysiumPlayer.get(uuid);
    }
}