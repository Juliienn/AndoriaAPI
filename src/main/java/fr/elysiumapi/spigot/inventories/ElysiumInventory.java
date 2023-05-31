package fr.elysiumapi.spigot.inventories;

import fr.elysiumapi.spigot.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ElysiumInventory {

    private Inventory inventory;
    private String name;
    private int size;

    public ElysiumInventory(String name, int size){
        this.inventory = Bukkit.createInventory(null, size, name);
    }

    public void setItem(ItemBuilder item, int slot){
        this.inventory.setItem(slot, item.getItem());
    }

    public void setItem(ItemStack item, int slot){
        this.inventory.setItem(slot, item);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}