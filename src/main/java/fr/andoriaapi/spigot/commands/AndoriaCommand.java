package fr.andoriaapi.spigot.commands;

import fr.andoriaapi.spigot.player.AndoriaPlayer;
import fr.andoriaapi.database.player.PlayerData;
import fr.andoriaapi.database.player.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AndoriaCommand implements CommandExecutor {

    private final PlayerDataManager playerDataManager;
    private final int powerRequired;
    private final String commandName;

    public AndoriaCommand(PlayerDataManager playerDataManager, int powerRequired, String commandName){
        this.playerDataManager = playerDataManager;
        this.powerRequired = powerRequired;
        this.commandName = commandName;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase(this.commandName)) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                AndoriaPlayer elysiumPlayer = AndoriaPlayer.getAndoriaPlayer(player.getUniqueId());
                PlayerData playerData = playerDataManager.getPlayerData(player.getUniqueId(), player.getName());
                if (this.powerRequired == 0 || playerData.getRankInfos().getRank().getPower() >= this.powerRequired) {
                    execute(elysiumPlayer, args);
                    execute(player, args);
                } else {
                    player.sendMessage("§7Commande inconnue, pour afficher les commandes: §e/help");
                    return false;
                }
            }
        }
        return false;
    }

    public abstract void execute(AndoriaPlayer player, String[] args);

    public abstract void execute(Player player, String[] args);

    public int getPowerRequired(){
        return this.powerRequired;
    }

    public String getCommandName(){
        return this.commandName;
    }
}
