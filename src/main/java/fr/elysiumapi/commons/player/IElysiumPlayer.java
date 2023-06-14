package fr.elysiumapi.commons.player;

import fr.elysiumapi.spigot.inventories.ElysiumInventory;
import fr.elysiumapi.utils.PlayerUtils;

import java.util.UUID;

public interface IElysiumPlayer {

    UUID getUUID();

    String getIp();

    void openInventory(ElysiumInventory inventory);

    void connect(String serverName);

    static IElysiumPlayer getIElysiumPlayer(UUID uuid){
        return PlayerUtils.elysiumPlayer.get(uuid);
    }
}