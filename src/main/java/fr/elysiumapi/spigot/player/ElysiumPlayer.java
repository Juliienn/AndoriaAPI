package fr.elysiumapi.spigot.player;

import fr.elysiumapi.spigot.inventories.ElysiumInventory;
import fr.elysiumapi.utils.PlayerUtils;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class ElysiumPlayer {

    private final Player player;

    public ElysiumPlayer(Player player){
        this.player = player;
    }

    public abstract void connect(String serverName);

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

    public static ElysiumPlayer getIElysiumPlayer(UUID uuid){
        return PlayerUtils.elysiumPlayer.get(uuid);
    }
}