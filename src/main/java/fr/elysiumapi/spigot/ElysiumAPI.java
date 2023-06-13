package fr.elysiumapi.spigot;

import com.google.common.collect.Maps;
import fr.elysiumapi.spigot.listeners.ItemsListeners;
import fr.elysiumapi.spigot.listeners.PlayerListener;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public final class ElysiumAPI extends JavaPlugin {
    private static ElysiumAPI instance;
    private static Map<UUID, ElysiumPlayer> players;

    @Override
    public void onEnable() {
        // Initialisations
        instance = this;

        players = Maps.newHashMap();

        //Listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ItemsListeners(), this);

    }

    public static Map<UUID, ElysiumPlayer> getPlayers() {
        return players;
    }

    public static ElysiumAPI getInstance() {
        return instance;
    }
}
