package fr.elysiumapi.spigot.items.shop;

import fr.elysiumapi.spigot.items.InventoryItem;
import fr.elysiumapi.spigot.shop.Buyable;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AcceptItem extends InventoryItem {
    public AcceptItem(Buyable buyable) {
        super(new ItemStack(Material.WOOL, 1, (byte)13), "§2Confirmer: " + buyable.getPrice() + " points VIP");
    }
}