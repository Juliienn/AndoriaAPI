package fr.andoriaapi.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public final class AndoriaAPI extends JavaPlugin {
    private static AndoriaAPI instance;

    @Override
    public void onEnable() {
        // Initialisations
        instance = this;

    }
    public static AndoriaAPI getInstance() {
        return instance;
    }
}
