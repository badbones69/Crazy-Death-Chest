package me.badbones69.crazydeathchest.api.objects;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class DeathChestLocation {
    
    private UUID ownerUUID;
    private Block chestLocation;
    private List<ItemStack> droppedItems;
    private ItemBuilder blockType;
    
    public DeathChestLocation(UUID ownerUUID, Block chestLocation, List<ItemStack> droppedItems, ItemBuilder blockType) {
        this.ownerUUID = ownerUUID;
        this.chestLocation = chestLocation;
        this.droppedItems = droppedItems;
        this.blockType = blockType;
    }
    
    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    
    public OfflinePlayer getOwner() {
        return Bukkit.getOfflinePlayer(ownerUUID);
    }
    
    public Block getChestLocation() {
        return chestLocation;
    }
    
    public List<ItemStack> getDroppedItems() {
        return droppedItems;
    }
    
    public ItemBuilder getBlockType() {
        return blockType;
    }
    
}