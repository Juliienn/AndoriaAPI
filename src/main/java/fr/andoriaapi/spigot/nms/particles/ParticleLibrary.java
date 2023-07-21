package fr.andoriaapi.spigot.nms.particles;

import fr.andoriaapi.spigot.nms.NMSReflexion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class ParticleLibrary extends NMSReflexion {

    private static final Class<?> packetClass;
    private static final Class<?> enumParticleClass;

    static {
        try {
            packetClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".PacketPlayOutWorldParticles");
            enumParticleClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".EnumParticle");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void spawnParticleRing(Player player, String particleName, Location center, double radius, double height, int particleCount) {
        double increment = (2 * Math.PI) / particleCount;

        for (int i = 0; i < particleCount; i++) {
            double angle = i * increment;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            Location particleLocation = new Location(center.getWorld(), x, center.getY() + height, z);
            spawnParticle(player, particleName, particleLocation, 0, 0, 0, 1.0F, 1);
        }
    }

    public static void spawnHeartParticle(Player player, String particleName, Location center, double size, int amount, float speed) {
        double angle;
        double x, y, z;
        double t;
        double pi = Math.PI;
        double radius = size * Math.sqrt(2);

        for (int i = 0; i < amount; i++) {
            t = i / 20.0 * pi;
            angle = 2 * t;
            x = radius * Math.cos(angle) * Math.sin(t);
            y = radius * Math.cos(t) + size * 2;
            z = radius * Math.sin(angle) * Math.sin(t);

            Location particleLocation = center.clone().add(x, y, z);

            spawnParticle(player, particleName, particleLocation, 0, 0, 0, speed, 1);
        }
    }

    public static void spawnParticle(Player player, String particleName, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        try {
            Object particleEnum = enumParticleClass.getField(particleName).get(null);
            Constructor<?> packetConstructor = packetClass.getDeclaredConstructor(enumParticleClass, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
            Object packet = packetConstructor.newInstance(particleEnum, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, count, null);

            sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void spawnParticleWithSize(Player player, String particleName, Location location, float size, float speed, int count) {
        try {

            Object particleEnum = enumParticleClass.getField(particleName).get(null);
            Constructor<?> packetConstructor = packetClass.getConstructor(enumParticleClass, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);

            Object packet = packetConstructor.newInstance(particleEnum, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), size, size, size, speed, count);

            sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendParticleToAll(String particleName, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        for (Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
            spawnParticle(player, particleName, location, offsetX, offsetY, offsetZ, speed, count);
        }
    }

    public static void sendParticleToRange(String particleName, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, double range) {
        for (Player player : location.getWorld().getPlayers()) {
            if (player.getLocation().distance(location) <= range) {
                spawnParticle(player, particleName, location, offsetX, offsetY, offsetZ, speed, count);
            }
        }
    }
}