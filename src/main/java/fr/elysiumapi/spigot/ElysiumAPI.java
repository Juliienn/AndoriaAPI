package fr.elysiumapi.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public final class ElysiumAPI extends JavaPlugin {
    private static ElysiumAPI instance;

    @Override
    public void onEnable() {
        // Initialisations
        instance = this;

    }

    public static ElysiumAPI getInstance() {
        return instance;
    }
}
