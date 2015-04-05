package org._Shane.Utils.Rendering;

import org._Shane.Utils.ReflectionResource.APIReflection;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Titles
{
    /* Available title actions.
        TITLE
        SUBTITLE
        TIMES
        CLEAR
        RESET */



    /**
     *
     * @param player Player to send packet to.
     * @param message Message to be rendered when packet is successfully sent.
     * @param type Type to declare Title Actions.
     * @param fadeIn Time (Millis) to fade in.
     * @param stay Time (Millis) to stay rendered.
     * @param fadeOut Time (Millis) to fade out.
     */
    public static void sendTitleAction(Player player, String message, String type, int fadeIn, int stay, int fadeOut)
    {
        try {
            Class<?> IChatBaseComponent = APIReflection.getNMS("IChatBaseComponent");
            Class<?> PacketPlayOutTitle = APIReflection.getNMS("PacketPlayOutTitle");
            Class<?> ChatSerializer = APIReflection.getNMS("ChatSerializer");
            Class<?> ETitleAction = APIReflection.getNMS("EnumTitleAction");
            Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", new Class[] { String.class });
            Field f = ETitleAction.getDeclaredField(type);
            f.setAccessible(true);

            Object o = aChatSerializer.invoke(null, new Object[] {message});
            Object packet = PacketPlayOutTitle.getConstructor(new Class[] {ETitleAction, IChatBaseComponent, Integer.TYPE, Integer.TYPE, Integer.TYPE}).newInstance(f.get(ETitleAction), IChatBaseComponent.cast(o), Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut));
            APIReflection.sendPacket(player, packet);
        } catch (Exception e)
        {

        }
    }
}


