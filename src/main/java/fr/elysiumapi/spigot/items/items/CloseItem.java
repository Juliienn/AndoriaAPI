package fr.elysiumapi.items.items;

import fr.elysiumapi.items.ItemBuilder;
import fr.elysiumapi.player.ElysiumPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CloseItem extends ItemBuilder {

    public CloseItem() {
        super(new ItemStack(Material.DARK_OAK_DOOR_ITEM), "Fermer l'inventaire");
    }

    public void action(ElysiumPlayer player) {

    }

    public void action(Player player) {
        player.closeInventory();
    }

    public void actionEntity(Player player, Entity entity) {

    }

    public void actionTarget(ElysiumPlayer player, ElysiumPlayer target) {

    }

    @Override
    public void actionTarget(UUID player, UUID target) {

    }
}
