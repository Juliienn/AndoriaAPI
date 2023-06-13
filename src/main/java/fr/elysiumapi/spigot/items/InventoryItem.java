package fr.elysiumapi.spigot.items;

import com.google.common.collect.Sets;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class InventoryItem extends ItemBuilder{

    private static final Set<InventoryItem> inventoryItems = Sets.newHashSet();

    public InventoryItem(ItemStack item, String name) {
        super(item, name, false);
        inventoryItems.add(this);
    }

    public InventoryItem(ItemStack item, String name, boolean glowing) {
        super(item, name, glowing, false);
        inventoryItems.add(this);
    }

    public InventoryItem(ItemStack item, String name, List<String> lores) {
        super(item, name, lores, false);
        inventoryItems.add(this);
    }

    public InventoryItem(ItemStack item, String name, List<String> lores, boolean glowing) {
        super(item, name, lores, glowing, false);
        inventoryItems.add(this);
    }

    public void setItem(Inventory inventory, int slot){
        inventory.setItem(slot, getItem());
    }

    public abstract void onClick(InventoryClickEvent event);

    public void action(ElysiumPlayer elysiumPlayer){
        
    }

    public void action(Player player){

    }

    public void actionEntity(Player player, Entity entity){

    }

    public void actionTarget(ElysiumPlayer player, ElysiumPlayer target){

    }

    public void actionTarget(UUID player, UUID target){

    }

    public static Set<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }
}