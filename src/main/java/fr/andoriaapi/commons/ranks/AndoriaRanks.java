package fr.andoriaapi.commons.ranks;

import fr.andoriaapi.commons.Symbols;
import fr.andoriaapi.commons.shop.Buyable;

public enum AndoriaRanks implements Buyable {

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

    AndoriaRanks(String prefix, String tagId, int power, boolean buyable){
        this.prefix = prefix;
        this.tagId = tagId;
        this.power = power;
        this.buyable = buyable;
    }

    public static AndoriaRanks nameToRank(String rankName){
        for(AndoriaRanks ranks : AndoriaRanks.values()){
            if(ranks.getPrefix().equalsIgnoreCase(rankName)){
                return ranks;
            }
        }
        return AndoriaRanks.HININ;
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
}