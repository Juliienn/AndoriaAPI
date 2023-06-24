package fr.elysiumapi.commons.shop;

public interface Buyable {

    int getPrice();
    Class<? extends Buyable> getType();
    
}
