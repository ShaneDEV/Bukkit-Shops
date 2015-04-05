package org._Shane.Utils.Rendering.Holograms;

import org._Shane.Utils.ReflectionResource.APIReflection;
import org._Shane.Utils.Other.Misc;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class RawHologram
{
    private World world;
    private double x, y, z;
    private String line;
    private int hologramID;
    private boolean isActive = false;
    public RawHologram(World world, double x, double y, double z, String line)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.line = line;
    }

    /**
     *
     * @param player Player to send packet to spawn in new Hologram.
     */
    public void spawnHologram(Player player)
    {
        try {
            Class<?> WS = APIReflection.getNMS("World");
            Object worldServer = APIReflection.getHandle(world);
            Class<?> EntityArmorStand = APIReflection.getNMS("EntityArmorStand");
            Class<?> EntityLiving = APIReflection.getNMS("EntityLiving");
            Object stand = EntityArmorStand.getConstructor(new Class[]{ WS }).newInstance(new Object[]{ WS.cast(worldServer) });

            Method setLocation = APIReflection.getMethod(stand.getClass(), "setLocation");
            setLocation.invoke(stand, new Object[]{ this.x, this.y, this.z, 0.0F, 0.0F});

            Method setCustomName = APIReflection.getMethod(stand.getClass(), "setCustomName");
            setCustomName.invoke(stand, new Object[]{Misc.colourize(line)});

            Method setCustomNameVisible = APIReflection.getMethod(stand.getClass(), "SetCustomNameVisible");
            setCustomNameVisible.invoke(stand, new Object[]{ new Boolean(true)});

            Method setBasePlate = APIReflection.getMethod(stand.getClass(), "setBasePlate");
            setBasePlate.invoke(stand, new Object[]{new Boolean(false)});

            Method setInvisible = APIReflection.getMethod(stand.getClass(), "setInvisible");
            setInvisible.invoke(stand, new Object[]{new Boolean(true)});

            Object bukkitEntity;
            Method getBukkitEntity = APIReflection.getMethod(stand.getClass(), "getBukkitEntity");
            bukkitEntity = getBukkitEntity.invoke(stand, new Object[0]);

            ArmorStand armorStand = (ArmorStand) bukkitEntity;
            armorStand.setRemoveWhenFarAway(false);
            hologramID = armorStand.getEntityId();

            Class<?> PacketPlayOutSpawnEntityLiving = APIReflection.getNMS("PacketPlayOutSpawnEntityLiving");
            Object packet = PacketPlayOutSpawnEntityLiving.getConstructor(new Class[]{EntityLiving}).newInstance(new Object[]{EntityLiving.cast(stand)});
            APIReflection.sendPacket(player, packet);
            isActive = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param player Player to remove this Hologram from.
     */
    public void deSpawnHologram(Player player)
    {
        try {
            Class<?> PacketPlayOutEntityDestroy = APIReflection.getNMS("PacketPlayOutEntityDestroy");
            Object packet = PacketPlayOutEntityDestroy.getConstructor(new Class[]{Integer.TYPE}).newInstance(Integer.valueOf(hologramID));
            APIReflection.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param player Player to send updated hologram to.
     * @param line Message to update hologram with.
     */
    public void setLine(Player player, String line)
    {
        if (isActive)
            deSpawnHologram(player);

        this.line = line;
        spawnHologram(player);
    }
}
