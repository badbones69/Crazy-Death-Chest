package me.badbones69.crazydeathchest.api.interfaces;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface HologramController {
    
    void createHologram(Block location, List<String> message);
    
    void createHologram(Block location, List<String> message, ItemStack itemStack);
    
    void removeHologram(Block location);
    
    void removeAllHolograms();
    
}