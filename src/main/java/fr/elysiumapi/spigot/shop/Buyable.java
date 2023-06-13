package fr.elysiumapi.spigot.shop;

public interface Buyable {

    int getPrice();

    Class<? extends Buyable> getType();
    
}
