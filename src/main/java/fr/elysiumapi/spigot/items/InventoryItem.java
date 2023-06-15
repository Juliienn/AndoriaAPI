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

    @Override
    public void action(ElysiumPlayer elysiumPlayer){
        
    }
    @Override
    public void action(Player player){

    }
    @Override
    public void actionEntity(Player player, Entity entity){

    }
    @Override
    public void actionTarget(Player player, Player target){

    }

    public static Set<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }
}