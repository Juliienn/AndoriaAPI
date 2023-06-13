package fr.elysiumapi.spigot.items;

import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

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

    public void action(ElysiumPlayer player) {

    }

    public void action(Player player) {

    }

    public void actionEntity(Player player, Entity entity) {

    }

    public void actionTarget(ElysiumPlayer player, ElysiumPlayer target) {

    }

    @Override
    public void actionTarget(UUID player, UUID target) {

    }
}