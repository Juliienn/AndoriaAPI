package fr.elysiumapi.listeners;

import fr.elysiumapi.ElysiumAPI;
import fr.elysiumapi.items.InventoryItem;
import fr.elysiumapi.items.ItemBuilder;
import fr.elysiumapi.player.ElysiumPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemsListeners implements Listener {

    private ElysiumAPI elysiumAPI;

    public ItemsListeners(ElysiumAPI elysiumAPI){

        this.elysiumAPI = elysiumAPI;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event){
        final Player player = event.getPlayer();
        for(ItemBuilder items : elysiumAPI.getItems()){
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
        for(ItemBuilder items : elysiumAPI.getItems()){
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
        for(InventoryItem items : elysiumAPI.getInventoryItems()){
            if(event.getCurrentItem().equals(items)){
                items.action((Player) event.getWhoClicked());
                items.action(ElysiumPlayer.getElysiumPlayer((Player) event.getWhoClicked()));
            }
        }
    }
}