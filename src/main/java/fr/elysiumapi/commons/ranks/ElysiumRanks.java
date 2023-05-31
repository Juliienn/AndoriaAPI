package fr.elysiumapi.commons.ranks;

import fr.elysiumapi.commons.Symbols;

import java.util.HashMap;

public enum ElysiumRanks {

    HININ("Hinin", "§7", 1, 0),
    GUERRIER("Guerrier", "§3", 2, 0),
    DAIMYO("Daimyo", "§d", 3, 0),
    TAISHO("Taisho", "§b", 4, 0),
    SAMURAI("Samurai", "§6", 5, 0),
    YOUTUBEUR("Youtubeur", "§6", 5, -1),
    YOUTUBEURPLUS("Youtubeur+", "§6", 6, -1),
    ASSISTANT("Samurai"+ Symbols.STAR, "§6", 7, -1),
    BUILDER("Builder", "§a", 8, -1),
    MODERATEUR("Modérateur", "§a", 9, -1),
    SUPERMODERATEUR("Modérateur+", "§2", 10, -1),
    ADMIN("Administrateur", "§c", 11, -1);

    String prefix;
    String tagId;
    int power;
    int price;

    private static HashMap<String, ElysiumRanks> ranks = new HashMap<>();

    static{
        for(ElysiumRanks rank : ElysiumRanks.values()){
            ranks.put(rank.getPrefix(), rank);
        }
    }

    ElysiumRanks(String prefix, String tagId, int power, int price){
        this.prefix = prefix;
        this.tagId = tagId;
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

    public String getTagId() {
        return tagId;
    }

    public int getPrice() {
        return price;
    }
}