package fr.elysiumapi.commons.commands;

import fr.elysiumapi.commons.player.IElysiumPlayer;

public abstract class ElysiumCommand {

    private final int powerRequired;
    private final String commandName;

    public ElysiumCommand(int powerRequired, String commandName){
        this.powerRequired = powerRequired;
        this.commandName = commandName;
    }

    public abstract void execute(IElysiumPlayer player, String[] args);

    public int getPowerRequired(){
        return this.powerRequired;
    }

    public String getCommandName(){
        return this.commandName;
    }
}
