package org._Shane.Utils.ReflectionResource;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class APIReflection
{
    private static String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    private static String nmsPackaging = "net.minecraft.server." + version + ".";

    /**
     *
     * @param className class name to search for
     * @return class found (could be null.)
     */
    public static Class<?> getNMS(String className)
    {
        Class<?> nmsClass = null;
        try {
            nmsClass = Class.forName(nmsPackaging + className);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return nmsClass;
    }

    /**
     *
     * @param clazz Class to search method in.
     * @param method Method name to search for and return.
     * @return Found method in class. (Could be null.)
     */
    public static Method getMethod(Class<?> clazz, String method)
    {
        for (Method m : clazz.getMethods())
        {
            if (m.getName().equalsIgnoreCase(method))
                return m;
        }
        return null;
    }

    /**
     *
     * @param entity Entity class to get CraftEntity getHandle method.
     * @return entity's getHandle method.
     */
    public static Object getHandle(Entity entity)
    {
        Object NMSEntity = null;
        Method getHandle = getMethod(entity.getClass(), "getHandle");
        try
        {

            NMSEntity = getHandle.invoke(entity, new Object[0]);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return NMSEntity;
    }

    /**
     *
     * @param world World class to get CraftWorld getHandle method
     * @return world's getHandle method.
     */
    public static Object getHandle(World world)
    {
        Object NMSWorld = null;
        Method getHandle = getMethod(world.getClass(), "getHandle");
        try
        {

            NMSWorld = getHandle.invoke(world, new Object[0]);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return NMSWorld;
    }

    /**
     *
     * @param player Player to send packet to.
     * @param packet Packet to send to player.
     */
    public static void sendPacket(Player player, Object packet)
    {
        try {
            Object nmsPlayer = getHandle(player);
            Field playerConnection = nmsPlayer.getClass().getDeclaredField("playerConnection");
            Object connection = playerConnection.get(nmsPlayer);
            Method packetMethod = getMethod(connection.getClass(), "sendPacket");
            packetMethod.invoke(connection, new Object[] { packet });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param entity Entity's class to get it's DataWatcher method.
     * @param text Text to change Entity's dataWatcher. (Bit new to DataWatcher, need to look more into it.)
     */
    public static void changeDW(Object entity, String text)
    {
        try {
            Object dataWatcher = getMethod(entity.getClass(), "getDataWatcher");
            Method a = getNMS("DataWatcher").getMethod("a", int.class, Object.class);
            Field d = getNMS("DataWatcher").getDeclaredField("d");
            Map<?, ?> map = (Map<?, ?>) d.get(dataWatcher);
            map.remove(10);
            a.invoke(dataWatcher, 10, text);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
