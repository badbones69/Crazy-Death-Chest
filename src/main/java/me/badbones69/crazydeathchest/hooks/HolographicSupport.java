package me.badbones69.crazydeathchest.hooks;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.badbones69.crazydeathchest.Methods;
import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.interfaces.HologramController;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.List;

public class HolographicSupport implements HologramController {
    
    private static HashMap<Block, Hologram> holograms = new HashMap<>();
    private DeathChest deathChest = DeathChest.getInstance();
    
    public void createHologram(Block block, List<String> message) {
        double hight = .5;
        Hologram hologram = HologramsAPI.createHologram(deathChest.getPlugin(), block.getLocation().add(.5, hight, .5));
        for (String line : message) {
            hologram.appendTextLine(Methods.color(line));
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