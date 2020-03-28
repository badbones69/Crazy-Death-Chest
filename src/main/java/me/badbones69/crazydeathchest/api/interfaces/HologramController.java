package me.badbones69.crazydeathchest.api.interfaces;

import org.bukkit.block.Block;

import java.util.List;

public interface HologramController {
    
    void createHologram(Block location, List<String> message);
    
    void removeHologram(Block location);
    
    void removeAllHolograms();
    
}