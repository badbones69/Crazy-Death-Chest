package me.badbones69.crazydeathchest.hooks;

import com.sainttx.holograms.HologramPlugin;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.line.ItemLine;
import com.sainttx.holograms.api.line.TextLine;
import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.interfaces.HologramController;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HologramsSupport implements HologramController {
    
    private DeathChest deathChest = DeathChest.getInstance();
    private static HashMap<Block, Hologram> holograms = new HashMap<>();
    private static HologramManager hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();
    
    public void createHologram(Block block, List<String> message) {
        createHologram(block, message, null);
    }
    
    public void createHologram(Block block, List<String> message, ItemStack itemStack) {
        double hight = deathChest.getHologramHeight() - .5;//Doing this as Holograms seems to add .5 height when adding lines or something..
        Hologram hologram = new Hologram(new Random().nextInt() + "", block.getLocation().add(.5, hight, .5));
        for (String line : message) {
            if (line.toLowerCase().contains("%item%") && itemStack != null) {
                hologram.addLine(new ItemLine(hologram, itemStack));
            } else {
                hologram.addLine(new TextLine(hologram, line));
            }
        }
        hologramManager.addActiveHologram(hologram);
        holograms.put(block, hologram);
    }
    
    public void removeHologram(Block block) {
        if (holograms.containsKey(block)) {
            Hologram hologram = holograms.get(block);
            hologramManager.deleteHologram(hologram);
            holograms.remove(block);
        }
    }
    
    public void removeAllHolograms() {
        for (Block location : holograms.keySet()) {
            Hologram hologram = holograms.get(location);
            hologramManager.deleteHologram(hologram);
        }
        holograms.clear();
    }
    
}