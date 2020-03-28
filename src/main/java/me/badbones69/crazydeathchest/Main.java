package me.badbones69.crazydeathchest;

import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.objects.FileManager;
import me.badbones69.crazydeathchest.controllers.FireworkDamageAPI;
import me.badbones69.crazydeathchest.controllers.InventoryClose;
import me.badbones69.crazydeathchest.controllers.PlayerClaim;
import me.badbones69.crazydeathchest.controllers.PlayerDeath;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    private FileManager fileManager = FileManager.getInstance();
    private DeathChest deathChest = DeathChest.getInstance();
    
    @Override
    public void onEnable() {
        fileManager.logInfo(true).setup(this);
        deathChest.load();
        registerListeners();
    }
    
    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerDeath(), this);
        pm.registerEvents(new PlayerClaim(), this);
        pm.registerEvents(new InventoryClose(), this);
        pm.registerEvents(new FireworkDamageAPI(this), this);
    }
    
}