package fr.elysiumapi.spigot.nms;

import fr.elysiumapi.spigot.ElysiumAPI;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class CosmeticUtils extends NMSReflexion{

    public static void launchfw(Location location, final FireworkEffect effect)
    {
        Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.addEffect(effect);
        fwm.setPower(0);
        fw.setFireworkMeta(fwm);
        try {
            Class<?> craftFireworkClass = Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + ".entity.CraftFirework");
            Object craftFirework = craftFireworkClass.cast(fw);

            Object handle = craftFireworkClass.getMethod("getHandle").invoke(craftFirework);
            handle.getClass().getMethod("setInvisible", boolean.class).invoke(handle, true);

            Bukkit.getServer().getScheduler().runTaskLater(ElysiumAPI.getInstance(), () ->
            {
                Class<?> craftWorldClass = null;
                try {
                    craftWorldClass = Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + ".CraftWorld");
                    Object craftWorld = craftWorldClass.cast(location.getWorld());
                    Object world = craftWorldClass.getMethod("getHandle").invoke(craftWorld);

                    Class<?> entityFireworksClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".EntityFireworks");
                    Object fireworks = entityFireworksClass.cast(handle);

                    world.getClass().getMethod("broadcastEntityEffect", entityFireworksClass, byte.class)
                            .invoke(world, fireworks, (byte) 17);

                    fireworks.getClass().getMethod("die").invoke(fireworks);
                } catch (Exception e) {
                }
            }, 1);
        } catch (Exception e) {
        }
    }

}
