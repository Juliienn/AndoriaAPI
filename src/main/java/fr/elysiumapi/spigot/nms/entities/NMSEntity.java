package fr.elysiumapi.spigot.nms.entities;

import fr.elysiumapi.spigot.nms.NMSReflexion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class NMSEntity extends NMSReflexion {

    private static final Class<?> entityInsentientClass;
    private static final Class<?> entityTypesClass;

    static{
        try {
            entityInsentientClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".EntityInsentient");
            entityTypesClass = Class.forName("net.minecraft.server." + NMS_VERSION + ".EntityTypes");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerEntity(String name, int id, Class<? extends entityInsentientClass> nmsClass, Class<? extends entityInsentientClass> customClass){
        try {

            List<Map<?, ?>> dataMap = new ArrayList<>();
            for (Field f : entityInsentientClass.getDeclaredFields()){
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                }
            }

            if (dataMap.get(2).containsKey(id)){
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }

            Method method = entityTypesClass.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
