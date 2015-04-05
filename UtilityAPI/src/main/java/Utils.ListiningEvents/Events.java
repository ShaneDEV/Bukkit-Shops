package Utils.ListiningEvents;

import org._Shane.Utils.Rendering.Tab;
import org._Shane.Utils.Rendering.Titles;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Titles.sendTitleAction(event.getPlayer(), "&c&lSERVER NAME", Titles.Actions.TITLE, 5, 15, 5);
        Titles.sendTitleAction(event.getPlayer(), "&f&l" + event.getPlayer().getName() + " &c&lwelcome to server!", Titles.Actions.SUBTITLE, 5, 15, 5);
        Tab.tab_Header_Footer(event.getPlayer(), "&c&lHello", "&a&lHello");
    }
}
