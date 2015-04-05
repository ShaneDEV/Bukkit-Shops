package org._Shane.Utils.Rendering;

import org._Shane.Utils.ReflectionResource.APIReflection;
import org._Shane.Utils.Other.Misc;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Tab
{
    /**
     *
     * @param player Player to send packet to.
     * @param header Message to be displayed above tab list.
     * @param footer Message to be displayed below tab list.
     */
    public static void tab_Header_Footer(Player player, String header, String footer)
    {
        try {
            Class<?> IChatBaseComponent = APIReflection.getNMS("IChatBaseComponent");
            Class<?> Packet = APIReflection.getNMS("PacketPlayOutPlayerListHeaderFooter");
            Class<?> ChatSerializer = APIReflection.getNMS("ChatSerializer");
            Method aChatSerializer = ChatSerializer.getDeclaredMethod("a", new Class[] { String.class });

            Object packet = Packet.newInstance();
            Object head = aChatSerializer.invoke(null, new Object[]{Misc.Minecraft_JSON_Message(header)});
            Object foot = aChatSerializer.invoke(null, new Object[]{Misc.Minecraft_JSON_Message(footer)});

            Field hField = packet.getClass().getDeclaredField("a");
            hField.setAccessible(true);
            hField.set(packet, IChatBaseComponent.cast(head));

            Field fField = packet.getClass().getDeclaredField("b");
            fField.setAccessible(true);
            fField.set(packet, IChatBaseComponent.cast(foot));

            APIReflection.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
