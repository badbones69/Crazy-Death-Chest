package me.badbones69.crazydeathchest;

import me.badbones69.crazydeathchest.api.enums.Messages;
import me.badbones69.crazydeathchest.api.objects.FileManager.Files;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Methods {
    
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
    
}