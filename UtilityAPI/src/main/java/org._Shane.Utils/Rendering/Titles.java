package org._Shane.Utils.Rendering;

import org._Shane.Utils.Other.Misc;
import org._Shane.Utils.ReflectionResource.APIReflection;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Titles
{
    public enum Actions
    {
        TITLE("TITLE"),
        SUBTITLE("SUBTITLE"),
        TIMES("TIMES"),
        CLEAR("CLEAR"),
        RESET("RESET");

        private String name;
        private Actions(String name)
        { this.name = name; }

        public String getName()
        { return name; }
    }

    /**
     *
     * @param player Player to send packet to.
     * @param message Message to be rendered when packet is successfully sent.
     * @param action value of Actions enum for Titles/Subtitles.
     * @param fadeIn Time (Millis) to fade in.
     * @param stay Time (Millis) to stay rendered.
     * @param fadeOut Time (Millis) to fade out.
     */
    public static void sendTitleAction(Player player, String message, Actions action, int fadeIn, int stay, int fadeOut)
    {
        try {
            Class<?> IChatBaseComponent = APIReflection.getNMS("IChatBaseComponent");
            Class<?> PacketPlayOutTitle = APIReflection.getNMS("PacketPlayOutTitle");
            Class<?> ChatSerializer = APIReflection.getNMS("ChatSerializer");
            Class<?> ETitleAction = APIReflection.getNMS("EnumTitleAction");
            Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", new Class[] { String.class });

            Field titleAction = ETitleAction.getDeclaredField(action.getName());
            titleAction.setAccessible(true);

            Object o = aChatSerializer.invoke(null, new Object[] {Misc.colourize("{\"text\": \"" + message + "\"}")});
            Object packet = PacketPlayOutTitle.getConstructor(new Class[] {ETitleAction, IChatBaseComponent, Integer.TYPE, Integer.TYPE, Integer.TYPE}).newInstance(titleAction.get(ETitleAction), IChatBaseComponent.cast(o), Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut));
            APIReflection.sendPacket(player, packet);
        } catch (Exception e)
        {

        }
    }
}
