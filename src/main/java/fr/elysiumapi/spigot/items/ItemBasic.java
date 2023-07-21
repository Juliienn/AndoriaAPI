package fr.elysiumapi.spigot.items;

import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemBasic extends ItemBuilder{

    public ItemBasic(ItemStack item, String name) {
        super(item, name);
    }

    public ItemBasic(ItemStack item, String name, Enchantment... enchants) {
        super(item, name, enchants);
    }

    public ItemBasic(ItemStack item, String name, List<String> lores, Enchantment... enchants) {
        super(item, name, lores, enchants);
    }

    public ItemBasic(ItemStack item, String name, boolean glowing) {
        super(item, name, glowing);
    }

    public ItemBasic(ItemStack item, String name, List<String> lores) {
        super(item, name, lores);
    }

    public ItemBasic(ItemStack item, String name, List<String> lores, boolean glowing) {
        super(item, name, lores, glowing);
    }

    public void action(ElysiumPlayer player) {

    }

    public void action(Player player) {

    }

    public void actionEntity(Player player, Entity entity) {

    }

    public void actionTarget(Player player, Player target) {

    }
}