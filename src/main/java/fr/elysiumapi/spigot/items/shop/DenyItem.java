package fr.elysiumapi.spigot.items.shop;

import fr.elysiumapi.spigot.items.InventoryItem;
import fr.elysiumapi.spigot.shop.Buyable;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class DenyItem extends InventoryItem {

    public DenyItem(Buyable buyable) {
        super(new ItemStack(Material.WOOL, 1, (byte)14), "§4Annuler");
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        event.getWhoClicked().closeInventory();
    }
}