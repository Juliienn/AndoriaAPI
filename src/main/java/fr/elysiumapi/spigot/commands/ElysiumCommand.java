package fr.elysiumapi.commands;

import fr.elysiumapi.player.ElysiumPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ElysiumCommand implements CommandExecutor {

    private String commandName;
    private int powerRequired;

    public ElysiumCommand(String commandName, int powerRequired){
        this.commandName = commandName;
        this.powerRequired = powerRequired;
    }

    public abstract void execute(ElysiumPlayer player, String[] args);

    public boolean onCommand(CommandSender commandSender, Command command, String name, String[] args) {
        if(commandSender instanceof Player){
            if(name.equalsIgnoreCase(commandName)){
                ElysiumPlayer player = ElysiumPlayer.getElysiumPlayer((Player)commandSender);
                if(player.getRank().getPower() >= this.powerRequired) {
                    execute(ElysiumPlayer.getElysiumPlayer((Player) commandSender), args);
                }else{
                    commandSender.sendMessage(ChatColor.RED + "Commande inconnue, pour afficher les commandes disponibles /help");
                }
            }
        }
        return false;
    }

    public int getPowerRequired() {
        return powerRequired;
    }

    public String getCommandName() {
        return commandName;
    }
}
