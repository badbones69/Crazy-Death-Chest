package me.badbones69.crazydeathchest.controllers;

import me.badbones69.crazydeathchest.Methods;
import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.events.DeathChestClaimEvent;
import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerClaim implements Listener {
    
    private DeathChest deathChest = DeathChest.getInstance();
    
    @EventHandler
    public void onClaim(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasBlock()) {
            Block block = e.getClickedBlock();
            if (deathChest.isDeathChestLocation(block)) {
                e.setCancelled(true);
                if (canClaim(player)) {
                    DeathChestLocation chestLocation = deathChest.getDeathChestLocation(block);
                    DeathChestClaimEvent event = new DeathChestClaimEvent(player, chestLocation);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        List<ItemStack> drops = chestLocation.getDroppedItems();
                        block.setType(Material.AIR);
                        if (deathChest.isClaimFirework()) {
                            Methods.fireWork(block.getLocation(), deathChest.getFireworkColors());
                        }
                        if (deathChest.isDropItems()) {
                            drops.forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
                        } else {
                            Inventory inventory = Bukkit.createInventory(null, 54, deathChest.getDropInventoryName());
                            drops.forEach(inventory :: addItem);
                            player.openInventory(inventory);
                        }
                        deathChest.removeDeathChestLocation(block);
                    }
                }
            }
        }
    }
    
    private boolean canClaim(Player player) {
        return deathChest.useClaimPermission() && player.hasPermission("crazydeathchest.claim");
    }
    
}