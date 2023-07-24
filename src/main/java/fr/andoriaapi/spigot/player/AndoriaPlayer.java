package fr.andoriaapi.spigot.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.andoriaapi.spigot.inventories.AndoriaInventory;
import fr.andoriaapi.spigot.items.ItemBuilder;
import fr.andoriaapi.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AndoriaPlayer {

    private final Player player;
    private final UUID uuid;
    private AndoriaInventory openedInventory;
    private final List<ItemBuilder> items;

    public AndoriaPlayer(Player player){
        this.player = player;
        this.items = new ArrayList<>();
        this.uuid = this.generateRealUUID();
    }

    public void connect(String serverName){

    }

    public void addItem(ItemBuilder item){
        this.player.getInventory().addItem(item.getItem());
        items.add(item);
    }

    public void setItem(ItemBuilder item, int slot){
        this.player.getInventory().setItem(slot, item.getItem());
        items.add(item);
    }

    public void openInventory(AndoriaInventory inventory){
        this.openedInventory = null;
        this.player.openInventory(inventory.getInventory());
        this.openedInventory = inventory;
    }

    public void closeInventory(){
        this.openedInventory = null;
        player.closeInventory();
    }

    public void sendMessage(String message){
        this.player.sendMessage(message);
    }

    public String getIP(){
        return this.player.getAddress().getAddress().getHostAddress();
    }

    private UUID generateRealUUID(){
        String uuid = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + this.player.getName()).openStream()));
            uuid = (((JsonObject)new JsonParser().parse(in)).get("id")).toString().replaceAll("\"", "");
            uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            in.close();
        } catch (Exception e) {
            System.out.println("Unable to get UUID of: " + this.player.getName() + "!");
            uuid = "er";
        }
        return UUID.fromString(uuid);
    }

    public Player getPlayer() {
        return player;
    }

    public List<ItemBuilder> getItems() {
        return items;
    }

    public void setOpenedInventory(AndoriaInventory openedInventory) {
        this.openedInventory = openedInventory;
    }

    public AndoriaInventory getOpenedInventory() {
        return openedInventory;
    }

    public UUID getUUID() {
        return uuid;
    }

    public static AndoriaPlayer getAndoriaPlayer(UUID uuid){
        if(!(PlayerUtils.andoriaPlayers.containsKey(uuid))){
            PlayerUtils.andoriaPlayers.put(uuid, new AndoriaPlayer(Bukkit.getPlayer(uuid)) {
            });
        }
        return PlayerUtils.andoriaPlayers.get(uuid);
    }
}