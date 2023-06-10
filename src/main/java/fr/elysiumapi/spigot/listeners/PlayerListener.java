package fr.elysiumapi.spigot.listeners;

import fr.elysiumapi.spigot.ElysiumAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        final Player player = event.getPlayer();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event){
        ElysiumAPI.getPlayers().remove(event.getPlayer().getUniqueId());
    }
}
