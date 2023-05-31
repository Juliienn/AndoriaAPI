package fr.elysiumapi.ranks;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;

import java.util.HashMap;

public enum ElysiumRanks {

    HININ("Hinin", ChatColor.GRAY, 1, 0),
    GUERRIER("Guerrier", ChatColor.DARK_AQUA, 2, 0),
    DAIMYO("Daimyo", ChatColor.LIGHT_PURPLE, 3, 0),
    TAISHO("Taisho", ChatColor.AQUA, 4, 0),
    SAMURAI("Samurai", ChatColor.GOLD, 5, 0),
    YOUTUBEUR("Youtubeur", ChatColor.GOLD, 5, -1),
    YOUTUBEURPLUS("Youtubeur+", ChatColor.WHITE, 6, -1),
    ASSISTANT("Assistant", ChatColor.GOLD, 7, -1),
    BUILDER("Builder", ChatColor.GREEN, 8, -1),
    MODERATEUR("Modérateur", ChatColor.GREEN, 9, -1),
    SUPERMODERATEUR("Modérateur+", ChatColor.DARK_GREEN, 10, -1),
    ADMIN("Administrateur", ChatColor.RED, 11, -1);

    String prefix;
    ChatColor tag;
    int power;
    int price;

    private static HashMap<String, ElysiumRanks> ranks = Maps.newHashMap();

    static{
        for(ElysiumRanks rank : ElysiumRanks.values()){
            ranks.put(rank.getPrefix(), rank);
        }
    }

    ElysiumRanks(String prefix, ChatColor tag, int power, int price){
        this.prefix = prefix;
        this.tag = tag;
        this.power = power;
        this.price = price;
    }

    public static ElysiumRanks nameToRank(String rankName){
        if(ranks.get(rankName) != null){
            return ranks.get(rankName);
        }
        return ElysiumRanks.HININ;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getPower() {
        return power;
    }

    public ChatColor getTag() {
        return tag;
    }

    public int getPrice() {
        return price;
    }
}