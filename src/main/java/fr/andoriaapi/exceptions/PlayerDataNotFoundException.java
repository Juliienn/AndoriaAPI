package fr.andoriaapi.exceptions;

public class PlayerDataNotFoundException extends Exception{

    public PlayerDataNotFoundException(){
        super("Compte du joueur non trouvé");
    }
}
