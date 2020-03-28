package me.badbones69.crazydeathchest.api;

import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DropInventoryManager {
    
    private static DropInventoryManager instance = new DropInventoryManager();
    private Map<UUID, Inventory> dropInventories = new HashMap<>();
    private Map<UUID, DeathChestLocation> deathDropLocations = new HashMap<>();
    
    public static DropInventoryManager getInstance() {
        return instance;
    }
    
    public boolean inDropInventory(Player player) {
        return dropInventories.containsKey(player.getUniqueId());
    }
    
    public DeathChestLocation getDeathDropLocation(Player player) {
        return deathDropLocations.getOrDefault(player.getUniqueId(), null);
    }
    
    public void addDropInventory(Player player, Inventory inventory, DeathChestLocation chestLocation) {
        dropInventories.put(player.getUniqueId(), inventory);
        deathDropLocations.put(player.getUniqueId(), chestLocation);
    }
    
    public void removeDropInventory(Player player) {
        dropInventories.remove(player.getUniqueId());
        deathDropLocations.remove(player.getUniqueId());
    }
    
}