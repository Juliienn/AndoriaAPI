package fr.elysiumapi.commons.commands;

import fr.elysiumapi.commons.player.IElysiumPlayer;

public interface IElysiumCommand {

    int getPowerRequired();

    String getCommandName();

    void execute(IElysiumPlayer player, String[] args);

}
