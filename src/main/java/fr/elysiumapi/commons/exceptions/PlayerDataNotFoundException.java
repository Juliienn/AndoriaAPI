package fr.elysiumapi.commons.exceptions;

public class PlayerDataNotFoundException extends Exception{

    public PlayerDataNotFoundException(){
        super("Compte du joueur non trouvé");
    }
}
