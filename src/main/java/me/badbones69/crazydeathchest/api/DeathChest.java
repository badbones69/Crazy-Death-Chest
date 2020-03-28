package me.badbones69.crazydeathchest.api;

import me.badbones69.crazydeathchest.api.interfaces.HologramController;
import me.badbones69.crazydeathchest.hooks.HologramsSupport;
import me.badbones69.crazydeathchest.hooks.HolographicSupport;
import me.badbones69.crazydeathchest.hooks.Support;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class DeathChest {
    
    private static DeathChest instance = new DeathChest();
    private Plugin plugin = Bukkit.getPluginManager().getPlugin("CrazyDeathChest");
    /**
     * The hologram api that is being hooked into.
     */
    private HologramController hologramController;
    
    public static DeathChest getInstance() {
        return instance;
    }
    
    public void load() {
        Support.updatePluginStates();
        if (Support.HOLOGRAPHIC_DISPLAYS.isPluginLoaded()) {
            hologramController = new HolographicSupport();
        } else if (Support.HOLOGRAMS.isPluginLoaded()) {
            hologramController = new HologramsSupport();
        }
        //Removes all holograms so that they can be replaced.
        if (hologramController != null) {
            hologramController.removeAllHolograms();
        }
    }
    
    public Plugin getPlugin() {
        return plugin;
    }
    
    /**
     * Get the hologram plugin settings that is being used.
     * @return The hologram controller for the holograms.
     */
    public HologramController getHologramController() {
        return hologramController;
    }
    
}