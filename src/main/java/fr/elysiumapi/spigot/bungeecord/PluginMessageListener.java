package fr.elysiumapi.bungeecord;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.elysiumapi.player.ElysiumPlayer;
import fr.elysiumapi.ranks.ElysiumRanks;
import org.bukkit.entity.Player;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(channel.equalsIgnoreCase("playerStats")){
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            if(subChannel.equals("getStats")){
                int id = in.readInt();
                String uuid = in.readUTF();
                String rank = in.readUTF();
                int money = in.readInt();
                int vip = in.readInt();
                ElysiumPlayer elysiumPlayer = ElysiumPlayer.getElysiumPlayer(player);
                elysiumPlayer.setId(id);
                elysiumPlayer.setRank(ElysiumRanks.nameToRank(rank));
                elysiumPlayer.setMoney(money);
                elysiumPlayer.setVip(vip);
            }
        }
    }
}
