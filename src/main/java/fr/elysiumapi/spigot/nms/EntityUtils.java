package fr.elysiumapi.spigot.nms;

import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class EntityUtils extends NMSReflexion{

    public static void freezeEntity(Entity e) {
        try {
            Class<?> craftEntityClass = Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + ".entity.CraftEntity");
            Class<?> entityInsentientClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".EntityInsentient");
            Class<?> pathfinderGoalSelectorClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".PathfinderGoalSelector");
            Class<?> navigationClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".Navigation");

            Object craftEntity = craftEntityClass.cast(e);
            Object entity = craftEntityClass.getMethod("getHandle").invoke(craftEntity);

            if (!entityInsentientClass.isInstance(entity))
                return;

            Object ce = entityInsentientClass.cast(entity);

            Field bField = pathfinderGoalSelectorClass.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = pathfinderGoalSelectorClass.getDeclaredField("c");
            cField.setAccessible(true);

            Set<?> newLinkedHashSet = (Set<?>) Class.forName("java.util.LinkedHashSet").getConstructor().newInstance();

            bField.set(ce.getClass().getField("goalSelector").get(ce), newLinkedHashSet);
            bField.set(ce.getClass().getField("targetSelector").get(ce), newLinkedHashSet);
            cField.set(ce.getClass().getField("goalSelector").get(ce), newLinkedHashSet);
            cField.set(ce.getClass().getField("targetSelector").get(ce), newLinkedHashSet);

            Method aMethod = ce.getClass().getMethod("getNavigation");
            Object navigation = aMethod.invoke(ce);

            Method aTrueMethod = navigationClass.getMethod("a", boolean.class);
            aTrueMethod.invoke(navigation, true);
        } catch (Exception ignored) {
        }
    }
}