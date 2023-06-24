package fr.elysiumapi.spigot.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public abstract class NMSReflexion {

    protected static final String NMS_VERSION;
    protected static final Class<?> packetClass;

    static{
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        NMS_VERSION = packageName.substring(packageName.lastIndexOf('.') + 1);
        try {
            packetClass= Class.forName("net.minecraft.server.Packet");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);

            Method getHandleMethod = craftPlayerClass.getMethod("getHandle");
            Object entityPlayer = getHandleMethod.invoke(craftPlayer);

            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Method sendPacketMethod = playerConnection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + NMS_VERSION + ".Packet"));

            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}