package org._Shane.Utils.Core;

import Utils.ListiningEvents.Events.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilityPlugin extends JavaPlugin
{
    public static Plugin plugin;
    public void onEnable()
    {
        plugin = this; //First thing to be enabled.
        this.getServer().getPluginManager().registerEvents(new Events(), this);
    }

    public void onDisable()
    {
        Bukkit.getScheduler().cancelTasks(plugin);
        plugin = null; //Last thing to be disabled.
    }
}
