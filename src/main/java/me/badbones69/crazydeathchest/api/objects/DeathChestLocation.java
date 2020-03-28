package me.badbones69.crazydeathchest.api.objects;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class DeathChestLocation {
    
    private UUID ownerUUID;
    private Block chestBlock;
    private List<ItemStack> droppedItems;
    
    public DeathChestLocation(UUID ownerUUID, Block chestBlock, List<ItemStack> droppedItems) {
        this.ownerUUID = ownerUUID;
        this.chestBlock = chestBlock;
        this.droppedItems = droppedItems;
    }
    
    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    
    public OfflinePlayer getOwner() {
        return Bukkit.getOfflinePlayer(ownerUUID);
    }
    
    public Block getChestBlock() {
        return chestBlock;
    }
    
    public List<ItemStack> getDroppedItems() {
        return droppedItems;
    }
    
}