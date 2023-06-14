package fr.elysiumapi.spigot.items;

import fr.elysiumapi.commons.player.IElysiumPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemBasic extends ItemBuilder{

    public ItemBasic(ItemStack item, String name) {
        super(item, name, false);
    }

    public ItemBasic(ItemStack item, String name, boolean glowing) {
        super(item, name, glowing, false);
    }

    public ItemBasic(ItemStack item, String name, List<String> lores) {
        super(item, name, lores, false);
    }

    public ItemBasic(ItemStack item, String name, List<String> lores, boolean glowing) {
        super(item, name, lores, glowing, false);
    }

    public void action(IElysiumPlayer player) {

    }

    public void action(Player player) {

    }

    public void actionEntity(Player player, Entity entity) {

    }

    public void actionTarget(Player player, Player target) {

    }
}