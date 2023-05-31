package fr.elysiumapi.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.elysiumapi.ElysiumAPI;
import fr.elysiumapi.player.ElysiumPlayer;
import fr.elysiumapi.utils.Symbols;
import org.bukkit.Bukkit;
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
        event.setFormat(ChatColor.YELLOW + Symbols.ARROW_RIGHT_FULL + " " + player.getRank().getTag() + player.getRank().getPrefix() + " " + player.getPlayer().getName() + ": " + ChatColor.WHITE + event.getMessage());
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        final Player player = event.getPlayer();
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("getStats");
        out.writeUTF(player.getUniqueId().toString());

        Bukkit.getScheduler().scheduleSyncDelayedTask(elysiumAPI, () -> {
                player.sendPluginMessage(elysiumAPI, "playerStats", out.toByteArray());
        }, 3L);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event){
        ElysiumAPI.getPlayers().remove(event.getPlayer());
    }
}
