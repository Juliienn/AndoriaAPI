package fr.elysiumapi.commons.player;

import fr.elysiumapi.spigot.inventories.ElysiumInventory;

import java.util.UUID;

public interface IElysiumPlayer {

    UUID getUUID();

    String getIp();

    void openInventory(ElysiumInventory inventory);

    void connect(String serverName);

}