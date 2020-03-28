package me.badbones69.crazydeathchest.api.events;

import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeathChestSpawnEvent extends Event implements Cancellable {
    
    private HandlerList handlers = new HandlerList();
    private boolean isCancelled;
    private Player player;
    private DeathChestLocation chestLocation;
    
    public DeathChestSpawnEvent(Player player, DeathChestLocation chestLocation) {
        this.player = player;
        this.chestLocation = chestLocation;
        isCancelled = false;
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
    
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }
    
    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }
    
}