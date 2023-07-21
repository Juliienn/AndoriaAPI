package fr.andoriaapi.spigot.inventories;

import fr.andoriaapi.spigot.items.InventoryItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AndoriaInventory {

    private final List<InventoryItem> items;
    private final String name;
    private final int size;
    private final Inventory inventory;

    public AndoriaInventory(String name, int size){
        this.items = new ArrayList<>();
        this.name = name;
        this.size = size;
        this.inventory = Bukkit.createInventory(null, size, name);
    }

    public void setItem(InventoryItem item, int slot){
        this.inventory.setItem(slot, item.getItem());
        items.add(item);
    }

    public void clear(){
        items.clear();
        inventory.clear();
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

    public List<InventoryItem> getItems() {
        return items;
    }
}