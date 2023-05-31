package fr.elysiumapi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.elysiumapi.bungeecord.PluginMessageListener;
import fr.elysiumapi.items.InventoryItem;
import fr.elysiumapi.items.ItemBuilder;
import fr.elysiumapi.listeners.ItemsListeners;
import fr.elysiumapi.listeners.PlayerListener;
import fr.elysiumapi.player.ElysiumPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ElysiumAPI extends JavaPlugin {

    private static ElysiumAPI instance;
    private static Map<UUID, ElysiumPlayer> players;
    private List<ItemBuilder> items;

    private List<InventoryItem> inventoryItems;

    @Override
    public void onEnable() {
        // Initialisations
        instance = this;

        players = Maps.newHashMap();
        items = Lists.newArrayList();
        inventoryItems = Lists.newArrayList();

        getServer().getMessenger().registerIncomingPluginChannel(this, "playerStats", new PluginMessageListener());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "playerStats");

        //Listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemsListeners(this), this);
    }

    public static Map<UUID, ElysiumPlayer> getPlayers() {
        return players;
    }

    public static ElysiumAPI getInstance() {
        return instance;
    }

    public List<ItemBuilder> getItems() {
        return items;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }
}