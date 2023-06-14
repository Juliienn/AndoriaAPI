package fr.elysiumapi.exceptions;

public class IllegalDurationException extends Exception{

    public IllegalDurationException(){
        super("Une durée doit forcément être positive");
    }
}
