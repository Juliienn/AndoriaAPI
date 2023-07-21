package fr.andoriaapi.spigot.items;

public class Enchantment{

    private final org.bukkit.enchantments.Enchantment enchantment;
    private final int level;

    public Enchantment(org.bukkit.enchantments.Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public org.bukkit.enchantments.Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }
}
