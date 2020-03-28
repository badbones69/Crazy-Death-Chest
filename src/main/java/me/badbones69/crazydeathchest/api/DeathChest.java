package me.badbones69.crazydeathchest.api;

import me.badbones69.crazydeathchest.Methods;
import me.badbones69.crazydeathchest.api.interfaces.HologramController;
import me.badbones69.crazydeathchest.api.objects.DeathChestLocation;
import me.badbones69.crazydeathchest.api.objects.FileManager.Files;
import me.badbones69.crazydeathchest.api.objects.ItemBuilder;
import me.badbones69.crazydeathchest.hooks.HologramsSupport;
import me.badbones69.crazydeathchest.hooks.HolographicSupport;
import me.badbones69.crazydeathchest.hooks.Support;
import org.bukkit.Bukkit;
import org.bukkit.Color;
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
    private Plugin plugin;
    /**
     * The hologram api that is being hooked into.
     */
    private HologramController hologramController;
    private List<DeathChestLocation> chestLocations = new ArrayList<>();
    private ItemBuilder chestType;
    private boolean useHologram;
    private double hologramHeight;
    private List<String> hologramMessage;
    private boolean useChestBypass;
    private boolean useClaimPermission;
    private boolean dropItems;
    private String dropInventoryName;
    private boolean dropUnclaimedItems;
    private boolean claimFirework;
    private List<Color> fireworkColors = new ArrayList<>();
    
    public static DeathChest getInstance() {
        return instance;
    }
    
    public void load() {
        plugin = Bukkit.getPluginManager().getPlugin("CrazyDeathChest");
        Support.updatePluginStates();
        chestLocations.clear();
        fireworkColors.clear();
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
        FileConfiguration config = Files.CONFIG.getFile();
        String settings = "Settings.";
        chestType = new ItemBuilder().setMaterial(config.getString(settings + "Chest-Settings.Block"));
        useHologram = config.getBoolean(settings + "Chest-Settings.Holograms.Enabled");
        hologramHeight = config.getDouble(settings + "Chest-Settings.Holograms.Height");
        hologramMessage = config.getStringList(settings + "Chest-Settings.Holograms.Message");
        useChestBypass = config.getBoolean(settings + "Chest-Settings.Permissions.Bypass-Permission");
        useClaimPermission = config.getBoolean(settings + "Chest-Settings.Permissions.Claim-Permission");
        dropItems = config.getBoolean(settings + "Chest-Settings.Drop-Items");
        dropInventoryName = Methods.color(config.getString(settings + "Chest-Settings.Drop-Inventory-Name"));
        dropUnclaimedItems = config.getBoolean(settings + "Chest-Settings.Drop-Unclaimed-Items");
        claimFirework = config.getBoolean(settings + "Chest-Settings.Fireworks.Claim-Firework");
        for (String color : config.getStringList(settings + "Chest-Settings.Fireworks.Colors")) {
            fireworkColors.add(Methods.getColor(color));
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
    
    public ItemBuilder getChestType() {
        return chestType;
    }
    
    public boolean useHologram() {
        return useHologram;
    }
    
    public double getHologramHeight() {
        return hologramHeight;
    }
    
    public List<String> getHologramMessage() {
        return hologramMessage;
    }
    
    public boolean useChestBypass() {
        return useChestBypass;
    }
    
    public boolean useClaimPermission() {
        return useClaimPermission;
    }
    
    public boolean isDropItems() {
        return dropItems;
    }
    
    public String getDropInventoryName() {
        return dropInventoryName;
    }
    
    public boolean isDropUnclaimedItems() {
        return dropUnclaimedItems;
    }
    
    public boolean isClaimFirework() {
        return claimFirework;
    }
    
    public List<Color> getFireworkColors() {
        return fireworkColors;
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
            hologramController.removeHologram(chestLocation.getChestBlock());
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
            if (block.equals(location.getChestBlock())) {
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