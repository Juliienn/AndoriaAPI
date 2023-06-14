package fr.elysiumapi.commons.ranks;

import fr.elysiumapi.commons.Symbols;
import fr.elysiumapi.commons.shop.Buyable;

import java.util.HashMap;

public enum ElysiumRanks implements Buyable {

    HININ("Hinin", "§7", 0, false),
    GUERRIER("Guerrier", "§3", 1, true),
    DAIMYO("Daimyo", "§d", 2, true),
    TAISHO("Taisho", "§b", 3, true),
    SAMURAI("Samurai", "§6", 4, true),
    YOUTUBEUR("Youtubeur", "§6", 5, false),
    YOUTUBEURPLUS("Youtubeur+", "§6", 6, false),
    ASSISTANT("Samurai"+ Symbols.STAR, "§6", 7, false),
    BUILDER("Builder", "§a", 8, false),
    MODERATEUR("Modérateur", "§a", 9, false),
    SUPERMODERATEUR("Modérateur+", "§2", 10, false),
    ADMIN("Administrateur", "§c", 11, false);

    final String prefix;
    final String tagId;
    final int power;
    final boolean buyable;

    private final static HashMap<String, ElysiumRanks> ranks = new HashMap<>();

    static{
        for(ElysiumRanks rank : ElysiumRanks.values()){
            ranks.put(rank.getPrefix(), rank);
        }
    }

    ElysiumRanks(String prefix, String tagId, int power, boolean buyable){
        this.prefix = prefix;
        this.tagId = tagId;
        this.power = power;
        this.buyable = buyable;
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

    @Override
    public int getPrice() {
        return this.power*500;
    }

    @Override
    public Class<? extends Buyable> getType() {
        return ElysiumRanks.class;
    }
}