package fr.elysiumapi.spigot;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.elysiumapi.database.ElysiumDatabase;
import fr.elysiumapi.database.player.PlayerManager;
import fr.elysiumapi.database.redis.JedisConnection;
import fr.elysiumapi.database.redis.JedisConnector;
import fr.elysiumapi.spigot.items.InventoryItem;
import fr.elysiumapi.spigot.items.ItemBuilder;
import fr.elysiumapi.spigot.listeners.ItemsListeners;
import fr.elysiumapi.spigot.listeners.PlayerListener;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ElysiumAPI extends JavaPlugin {

    public JedisConnector jedisConnector;

    public PlayerManager playerManager;
    public ElysiumDatabase proxyDatabase;
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

        //Listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemsListeners(this), this);

        this.proxyDatabase = new ElysiumDatabase();
        this.playerManager = new PlayerManager(proxyDatabase);
        this.proxyDatabase.setJedisConnector(new JedisConnector(new JedisConnection("localhost", 6379,"")));
        this.jedisConnector = proxyDatabase.getJedisConnector();
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