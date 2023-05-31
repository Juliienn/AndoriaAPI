package fr.elysiumapi.spigot.items;

import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public abstract class InventoryItem extends ItemBuilder{


    public InventoryItem(ItemStack item, String name) {
        super(item, name);
    }

    public InventoryItem(ItemStack item, String name, boolean glowing) {
        super(item, name, glowing);
    }

    public InventoryItem(ItemStack item, String name, List<String> lores) {
        super(item, name, lores);
    }

    public InventoryItem(ItemStack item, String name, List<String> lores, boolean glowing) {
        super(item, name, lores, glowing);
    }

    public void setItem(Inventory inventory, int slot){
        inventory.setItem(slot, getItem());
    }

    public abstract void action(ElysiumPlayer player);

    public abstract void action(Player player);

    public void actionEntity(Player player, Entity entity){

    }

    public void actionTarget(ElysiumPlayer player, ElysiumPlayer target){

    }

    public void actionTarget(UUID player, UUID target){

    }
}