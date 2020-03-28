package me.badbones69.crazydeathchest.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Support {
    
    HOLOGRAPHIC_DISPLAYS("HolographicDisplays"),
    HOLOGRAMS("Holograms");
    
    private String name;
    private static Map<Support, Boolean> cachedPluginState = new HashMap<>();
    
    private Support(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isPluginLoaded() {
        return cachedPluginState.get(this);
    }
    
    public Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin(name);
    }
    
    /**
     * Used to update the states of plugins CE hooks into.
     */
    public static void updatePluginStates() {
        cachedPluginState.clear();
        Arrays.stream(values()).forEach(supportedPlugin -> cachedPluginState.put(supportedPlugin, supportedPlugin.getPlugin() != null));
    }
    
}