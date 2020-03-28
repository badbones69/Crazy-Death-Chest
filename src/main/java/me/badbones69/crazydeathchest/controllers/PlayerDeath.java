package me.badbones69.crazydeathchest.controllers;

import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.events.DeathChestSpawnEvent;
import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerDeath implements Listener {
    
    private DeathChest deathChest = DeathChest.getInstance();
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        List<ItemStack> drops = e.getDrops();
        //Player doesn't bypass and does drop items.
        if (!(ignorePlayer(player) && drops.isEmpty())) {
            Block block = player.getLocation().getBlock();
            DeathChestLocation chestLocation = new DeathChestLocation(player.getUniqueId(), block, drops);
            DeathChestSpawnEvent event = new DeathChestSpawnEvent(player, chestLocation);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                deathChest.addDeathChestLocation(chestLocation);
                block.setType(deathChest.getChestType().getMaterial());
                e.getDrops().clear();
            }
        }
    }
    
    private boolean ignorePlayer(Player player) {
        return deathChest.useChestBypass() && player.hasPermission("crazydeathchest.bypass");
    }
    
}