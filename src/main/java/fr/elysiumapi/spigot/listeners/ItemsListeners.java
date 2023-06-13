package fr.elysiumapi.spigot.listeners;

import fr.elysiumapi.spigot.items.InventoryItem;
import fr.elysiumapi.spigot.items.ItemBuilder;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemsListeners implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event){
        final Player player = event.getPlayer();
        for(ItemBuilder items : ItemBuilder.getItemBuilders()){
            if(items.getItem().equals(event.getItem())){
                items.action(player);
                items.action(ElysiumPlayer.getElysiumPlayer(player));
                break;
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteractEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        for(ItemBuilder items : ItemBuilder.getItemBuilders()){
            if(items.getItem().equals(event.getPlayer().getItemInHand())){
                if(event.getRightClicked() instanceof Player){
                    ElysiumPlayer player1 = ElysiumPlayer.getElysiumPlayer(event.getRightClicked().getUniqueId());
                    items.actionTarget(ElysiumPlayer.getElysiumPlayer(player), player1);
                    return;
                }
                items.actionEntity(player, event.getRightClicked());
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        for(InventoryItem items : InventoryItem.getInventoryItems()){
            if(event.getCurrentItem().equals(items.getItem())){
                items.onClick(event);
                break;
            }
        }
    }
}