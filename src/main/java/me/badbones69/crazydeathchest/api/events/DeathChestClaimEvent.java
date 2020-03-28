package me.badbones69.crazydeathchest.api.events;

import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeathChestClaimEvent extends Event {
    
    private HandlerList handlers = new HandlerList();
    private Player player;
    private DeathChestLocation chestLocation;
    
    public DeathChestClaimEvent(Player player, DeathChestLocation chestLocation) {
        this.player = player;
        this.chestLocation = chestLocation;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public DeathChestLocation getDeathChestLocation() {
        return chestLocation;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
}