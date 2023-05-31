package fr.elysiumapi.spigot.items;

import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public abstract class ItemBuilder {

    private ItemStack item;
    private String name;
    private List<String> lores;
    private boolean glowing;

    public ItemBuilder(ItemStack item, String name){
        this.item = item;
        this.name = name;
        this.buildItem();
    }

    public ItemBuilder(ItemStack item, String name, boolean glowing){
        this.item = item;
        this.name = name;
        this.glowing = glowing;
        this.buildItem();
    }

    public ItemBuilder(ItemStack item, String name, List<String> lores){
        this.item = item;
        this.name = name;
        this.lores = lores;
        this.buildItem();
    }

    public ItemBuilder(ItemStack item, String name, List<String> lores, boolean glowing){
        this.item = item;
        this.name = name;
        this.lores = lores;
        this.glowing = glowing;
        this.buildItem();
    }

    public void buildItem(){
        ItemMeta itemMeta = this.item.getItemMeta();
        if(name != null) itemMeta.setDisplayName(this.name);
        if(this.glowing){
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            this.item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        if(this.lores != null) itemMeta.setLore(this.lores);
        this.item.setItemMeta(itemMeta);
    }

    public void addEnchant(Enchantment enchant, int level){
        this.item.addEnchantment(enchant, level);
    }

    public abstract void action(ElysiumPlayer player);

    public abstract void action(Player player);

    public abstract void actionEntity(Player player, Entity entity);

    public abstract void actionTarget(ElysiumPlayer player, ElysiumPlayer target);

    public abstract void actionTarget(UUID player, UUID target);

    public ItemStack getItem() {
        return this.item;
    }

    public int getAmount() {
        return item.getAmount();
    }

    public List<String> getLores() {
        return lores;
    }

    public String getName() {
        return name;
    }
}