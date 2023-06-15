package fr.elysiumapi.spigot.commands;

import fr.elysiumapi.spigot.player.ElysiumPlayer;
import fr.elysiumapi.database.player.PlayerData;
import fr.elysiumapi.database.player.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ElysiumCommand implements CommandExecutor {

    private PlayerDataManager playerDataManager;
    private final int powerRequired;
    private final String commandName;

    public ElysiumCommand(PlayerDataManager playerDataManager, int powerRequired, String commandName){
        this.playerDataManager = playerDataManager;
        this.powerRequired = powerRequired;
        this.commandName = commandName;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            ElysiumPlayer iElysiumPlayer = ElysiumPlayer.getIElysiumPlayer(player.getUniqueId());
            PlayerData playerData = playerDataManager.getPlayerData(player.getUniqueId(), player.getName());
            if(playerData.getRankInfos().getRank().getPower() >= this.powerRequired){
                execute(iElysiumPlayer, args);
                execute(player, args);
            }else{
                player.sendMessage("§7Commande inconnue, pour afficher les commandes: §e/help");
                return false;
            }
        }
        return false;
    }

    public abstract void execute(ElysiumPlayer player, String[] args);

    public abstract void execute(Player player, String[] args);

    public int getPowerRequired(){
        return this.powerRequired;
    }

    public String getCommandName(){
        return this.commandName;
    }
}
