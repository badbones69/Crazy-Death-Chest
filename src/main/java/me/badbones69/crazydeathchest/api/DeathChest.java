package me.badbones69.crazydeathchest.api;

import me.badbones69.crazydeathchest.api.interfaces.HologramController;
import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import me.badbones69.crazydeathchest.api.objects.FileManager.Files;
import me.badbones69.crazydeathchest.hooks.HologramsSupport;
import me.badbones69.crazydeathchest.hooks.HolographicSupport;
import me.badbones69.crazydeathchest.hooks.Support;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeathChest {
    
    private static DeathChest instance = new DeathChest();
    private Plugin plugin = Bukkit.getPluginManager().getPlugin("CrazyDeathChest");
    /**
     * The hologram api that is being hooked into.
     */
    private HologramController hologramController;
    private List<DeathChestLocation> chestLocations = new ArrayList<>();
    
    public static DeathChest getInstance() {
        return instance;
    }
    
    public void load() {
        Support.updatePluginStates();
        chestLocations.clear();
        if (Support.HOLOGRAPHIC_DISPLAYS.isPluginLoaded()) {
            hologramController = new HolographicSupport();
        } else if (Support.HOLOGRAMS.isPluginLoaded()) {
            hologramController = new HologramsSupport();
        }
        //Removes all holograms so that they can be replaced.
        if (hologramController != null) {
            hologramController.removeAllHolograms();
        }
        FileConfiguration data = Files.DATA.getFile();
        for (String location : data.getConfigurationSection("Chest-Locations").getKeys(false)) {
            String path = "Chest-Locations." + location + ".";
            ArrayList<ItemStack> droppedItems = new ArrayList<>();
            for (Object item : data.getList(path + "Items", new ArrayList<>())) {
                droppedItems.add((ItemStack) item);
            }
            chestLocations.add(new DeathChestLocation(
            UUID.fromString(data.getString(path + "UUID")),
            getBlockFromString(data.getString(path + "Block")),
            droppedItems));
        }
    }
    
    public void saveDeathChestLocations() {
        FileConfiguration data = Files.DATA.getFile();
        if (!chestLocations.isEmpty()) {
            int i = 1;
            for (DeathChestLocation chestLocation : chestLocations) {
                String path = "Chest-Locations." + i + ".";
                data.set(path + "UUID", chestLocation.getOwnerUUID());
                data.set(path + "Block", getStringFromBlock(chestLocation.getChestBlock()));
                data.set("Items", chestLocation.getDroppedItems());
            }
        } else {
            data.set("Chest-Locations", new ArrayList<>());
        }
        Files.DATA.saveFile();
    }
    
    public Plugin getPlugin() {
        return plugin;
    }
    
    public List<DeathChestLocation> getDeathChestLocations() {
        return chestLocations;
    }
    
    public void addDeathChestLocation(DeathChestLocation chestLocation) {
        if (isDeathChestLocation(chestLocation.getChestBlock())) {
            removeDeathChestLocation(chestLocation.getChestBlock());
        }
        chestLocations.add(chestLocation);
    }
    
    public void removeDeathChestLocation(Block block) {
        DeathChestLocation chestLocation = getDeathChestLocation(block);
        if (chestLocation != null) {
            chestLocations.remove(chestLocation);
        }
    }
    
    public void removeDeathChestLocation(DeathChestLocation chestLocation) {
        chestLocations.remove(chestLocation);
    }
    
    public DeathChestLocation getDeathChestLocation(Location location) {
        return getDeathChestLocation(location.getBlock());
    }
    
    public DeathChestLocation getDeathChestLocation(Block block) {
        for (DeathChestLocation location : chestLocations) {
            if (location.getChestBlock() == block) {
                return location;
            }
        }
        return null;
    }
    
    public boolean isDeathChestLocation(Location location) {
        return isDeathChestLocation(location.getBlock());
    }
    
    public boolean isDeathChestLocation(Block block) {
        return getDeathChestLocation(block) != null;
    }
    
    /**
     * Get the hologram plugin settings that is being used.
     * @return The hologram controller for the holograms.
     */
    public HologramController getHologramController() {
        return hologramController;
    }
    
    private String getStringFromBlock(Block block) {
        return "world:" + block.getWorld().getName()
        + ", x:" + block.getX()
        + ", y:" + block.getY()
        + ", z:" + block.getZ();
    }
    
    private Block getBlockFromString(String locationString) {
        World w = Bukkit.getWorlds().get(0);
        int x = 0;
        int y = 0;
        int z = 0;
        for (String i : locationString.toLowerCase().split(", ")) {
            if (i.startsWith("world:")) {
                w = Bukkit.getWorld(i.replace("world:", ""));
            } else if (i.startsWith("x:")) {
                x = Integer.parseInt(i.replace("x:", ""));
            } else if (i.startsWith("y:")) {
                y = Integer.parseInt(i.replace("y:", ""));
            } else if (i.startsWith("z:")) {
                z = Integer.parseInt(i.replace("z:", ""));
            }
        }
        return new Location(w, x, y, z).getBlock();
    }
    
}