package me.badbones69.crazydeathchest.hooks;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.badbones69.crazydeathchest.Methods;
import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.interfaces.HologramController;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class HolographicSupport implements HologramController {
    
    private static HashMap<Block, Hologram> holograms = new HashMap<>();
    private DeathChest deathChest = DeathChest.getInstance();
    
    public void createHologram(Block block, List<String> message) {
        createHologram(block, message, null);
    }
    
    public void createHologram(Block block, List<String> message, ItemStack itemStack) {
        double hight = deathChest.getHologramHeight();
        Hologram hologram = HologramsAPI.createHologram(deathChest.getPlugin(), block.getLocation().add(.5, hight, .5));
        for (String line : message) {
            if (line.toLowerCase().contains("%item%") && itemStack != null) {
                hologram.appendItemLine(itemStack);
            } else {
                hologram.appendTextLine(Methods.color(line));
            }
        }
        holograms.put(block, hologram);
    }
    
    public void removeHologram(Block block) {
        if (holograms.containsKey(block)) {
            Hologram hologram = holograms.get(block);
            holograms.remove(block);
            hologram.delete();
        }
    }
    
    public void removeAllHolograms() {
        for (Block block : holograms.keySet()) {
            holograms.get(block).delete();
        }
        holograms.clear();
    }
    
}