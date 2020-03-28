package me.badbones69.crazydeathchest.controllers;

import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.DropInventoryManager;
import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class InventoryClose implements Listener {
    
    private DeathChest deathChest = DeathChest.getInstance();
    private DropInventoryManager manager = DropInventoryManager.getInstance();
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        Inventory inventory = e.getInventory();
        if (manager.inDropInventory(player)) {
            if (e.getView().getTitle().equals(deathChest.getDropInventoryName())) {
                if (deathChest.isDropUnclaimedItems()) {
                    DeathChestLocation chestLocation = manager.getDeathDropLocation(player);
                    Arrays.asList(inventory.getContents()).forEach(drop -> player.getWorld().dropItemNaturally(chestLocation.getChestBlock().getLocation().add(.5, 0, .5), drop));
                }
                inventory.clear();
            }
            manager.removeDropInventory(player);
        }
    }
    
}