package me.badbones69.crazydeathchest;

import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.objects.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    private FileManager fileManager = FileManager.getInstance();
    private DeathChest deathChest = DeathChest.getInstance();
    
    @Override
    public void onEnable() {
        fileManager.logInfo(true).setup(this);
        deathChest.load();
    }
    
}