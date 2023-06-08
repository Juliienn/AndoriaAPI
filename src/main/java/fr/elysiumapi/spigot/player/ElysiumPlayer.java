package fr.elysiumapi.spigot.player;

import fr.elysiumapi.database.player.PlayerData;
import fr.elysiumapi.spigot.ElysiumAPI;
import fr.elysiumapi.spigot.inventories.ElysiumInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class ElysiumPlayer {
    private final Player player;
    private PlayerData playerData;
    public ElysiumPlayer(Player player) {
        this.player = player;
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

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public PlayerData getPlayerData() {
        return playerData;
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
}