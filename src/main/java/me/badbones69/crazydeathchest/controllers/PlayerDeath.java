package me.badbones69.crazydeathchest.controllers;

import me.badbones69.crazydeathchest.Methods;
import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.enums.Messages;
import me.badbones69.crazydeathchest.api.events.DeathChestSpawnEvent;
import me.badbones69.crazydeathchest.api.interfaces.HologramController;
import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerDeath implements Listener {
    
    private DeathChest deathChest = DeathChest.getInstance();
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        List<ItemStack> drops = new ArrayList<>(e.getDrops());
        //Player doesn't bypass and does drop items.
        if (!hasBypass(player) && !drops.isEmpty()) {
            Block block = player.getLocation().getBlock();
            DeathChestLocation chestLocation = new DeathChestLocation(player.getUniqueId(), block, drops);
            DeathChestSpawnEvent event = new DeathChestSpawnEvent(player, chestLocation);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                deathChest.addDeathChestLocation(chestLocation);
                block.setType(deathChest.getChestType().getMaterial());
                e.getDrops().clear();
                if (deathChest.useHologram()) {
                    HologramController hologram = deathChest.getHologramController();
                    HashMap<String, String> placeholders = new HashMap<>();
                    ItemStack weapon = null;
                    if (player.getKiller() != null) {
                        weapon = Methods.getItemInHand(player.getKiller());
                        if (weapon == null || weapon.getType() == Material.AIR) {
                            placeholders.put("%Killer%", player.getKiller().getName());
                            placeholders.put("%Item_Name%", "Hand");
                            placeholders.put("%Item_Type%", "Hand");
                        } else {
                            placeholders.put("%Killer%", player.getKiller().getName());
                            placeholders.put("%Item_Name%", weapon.hasItemMeta() && weapon.getItemMeta().hasDisplayName() ? weapon.getItemMeta().getDisplayName() : weapon.getType().name());
                            placeholders.put("%Item_Type%", weapon.getType().name());
                        }
                    } else {
                        if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                            placeholders.put("%Killer%", ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager().getName());
                        }
                        placeholders.put("%Item_Name%", "None");
                        placeholders.put("%Item_Type%", "None");
                    }
                    placeholders.put("%Player%", player.getName());
                    if (weapon.getType() == Material.AIR) {
                        weapon = null;
                        placeholders.put("%Item%", "Hand");
                    }
                    hologram.createHologram(block, Messages.replacePlaceholders(placeholders, deathChest.getHologramMessage()), weapon);
                }
            }
        }
    }
    
    private boolean hasBypass(Player player) {
        return deathChest.useChestBypass() && player.hasPermission("crazydeathchest.bypass");
    }
    
}