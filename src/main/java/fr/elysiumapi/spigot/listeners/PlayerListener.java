package fr.elysiumapi.spigot.listeners;

import fr.elysiumapi.spigot.ElysiumAPI;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import fr.elysiumapi.commons.Symbols;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private ElysiumAPI elysiumAPI;

    public PlayerListener(ElysiumAPI elysiumAPI) {
        this.elysiumAPI = elysiumAPI;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event){
        ElysiumPlayer player = ElysiumPlayer.getElysiumPlayer(event.getPlayer());
        event.setFormat(ChatColor.YELLOW + Symbols.ARROW_RIGHT_FULL + " " + player.getPlayerData().getRank().getTagId() + player.getPlayerData().getRank().getPrefix() + " " + player.getPlayerData().getName() + ": " + ChatColor.WHITE + event.getMessage());
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        final Player player = event.getPlayer();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event){
        ElysiumAPI.getPlayers().remove(event.getPlayer());
    }
}
