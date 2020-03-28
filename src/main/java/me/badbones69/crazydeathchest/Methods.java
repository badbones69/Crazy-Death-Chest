package me.badbones69.crazydeathchest;

import me.badbones69.crazydeathchest.api.DeathChest;
import me.badbones69.crazydeathchest.api.enums.Messages;
import me.badbones69.crazydeathchest.api.enums.Version;
import me.badbones69.crazydeathchest.api.objects.FileManager.Files;
import me.badbones69.crazydeathchest.controllers.FireworkDamageAPI;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.List;

public class Methods {
    
    private static DeathChest deathChest = DeathChest.getInstance();
    
    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    
    public static String removeColor(String msg) {
        return ChatColor.stripColor(msg);
    }
    
    public static String getPrefix() {
        return color(Files.CONFIG.getFile().getString("Settings.Prefix"));
    }
    
    public static String getPrefix(String message) {
        return color(Files.CONFIG.getFile().getString("Settings.Prefix") + message);
    }
    
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static boolean hasPermission(Player player, String node) {
        if (!player.hasPermission("crazydeathchest." + node)) {
            player.sendMessage(Messages.NO_PERMISSION.getMessage());
            return false;
        }
        return true;
    }
    
    public static boolean hasPermission(CommandSender sender, String node) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("crazydeathchest." + node)) {
                player.sendMessage(Messages.NO_PERMISSION.getMessage());
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    
    public static boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
    
    @SuppressWarnings({"deprecation", "squid:CallToDeprecatedMethod"})
    public static ItemStack getItemInHand(Player player) {
        if (Version.isNewer(Version.v1_8_R3)) {
            return player.getInventory().getItemInMainHand();
        } else {
            return player.getItemInHand();
        }
    }
    
    public static Entity fireWork(Location location, List<Color> colors) {
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffects(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(colors).trail(false).flicker(false).build());
        fireworkMeta.setPower(0);
        firework.setFireworkMeta(fireworkMeta);
        FireworkDamageAPI.addFirework(firework);
        detonate(firework);
        return firework;
    }
    
    private static void detonate(final Firework f) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(deathChest.getPlugin(), f :: detonate, 2);
    }
    
    public static Color getColor(String color) {
        if (color != null) {
            switch (color.toUpperCase()) {
                case "AQUA":
                    return Color.AQUA;
                case "BLACK":
                    return Color.BLACK;
                case "BLUE":
                    return Color.BLUE;
                case "FUCHSIA":
                    return Color.FUCHSIA;
                case "GRAY":
                    return Color.GRAY;
                case "GREEN":
                    return Color.GREEN;
                case "LIME":
                    return Color.LIME;
                case "MAROON":
                    return Color.MAROON;
                case "NAVY":
                    return Color.NAVY;
                case "OLIVE":
                    return Color.OLIVE;
                case "ORANGE":
                    return Color.ORANGE;
                case "PURPLE":
                    return Color.PURPLE;
                case "RED":
                    return Color.RED;
                case "SILVER":
                    return Color.SILVER;
                case "TEAL":
                    return Color.TEAL;
                case "WHITE":
                    return Color.WHITE;
                case "YELLOW":
                    return Color.YELLOW;
            }
            try {
                String[] rgb = color.split(",");
                return Color.fromRGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
            } catch (Exception ignore) {
            }
        }
        return Color.WHITE;
    }
    
}