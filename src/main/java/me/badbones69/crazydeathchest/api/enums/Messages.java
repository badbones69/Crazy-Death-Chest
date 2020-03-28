package me.badbones69.crazydeathchest.api.enums;

import me.badbones69.crazydeathchest.Methods;
import me.badbones69.crazydeathchest.api.objects.FileManager.Files;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum Messages {
    
    RELOAD("Config-Reload", "&7You have just reloaded the Config.yml"),
    INVENTORY_FULL("Inventory-Full", "&cYour inventory is to full. Please open up some space to buy that."),
    PLAYERS_ONLY("Reload", "&cOnly players can use this command."),
    NO_PERMISSION("No-Permission", "&cYou do not have permission to use that command!"),
    NO_PERMISSION_TO_VOUCHER("No-Permission-To-Voucher", "&cYou do not have permission to use that voucher."),
    HELP("Help",
    Arrays.asList(
    "&8- &6/Voucher Help &3Lists all the commands for vouchers.",
    "&8- &6/Voucher Types &3Lists all types of vouchers and codes.",
    "&8- &6/Voucher Redeem <Code> &3Allows player to redeem a voucher code.",
    "&8- &6/Voucher Give <Type> [Amount] [Player] [Arguments] &3Gives a player a voucher.",
    "&8- &6/Voucher GiveAll <Type> [Amount] [Arguments] &3Gives all players a voucher.",
    "&8- &6/Voucher Open [Page] &3Opens a GUI so you can get vouchers easy.",
    "&8- &6/Voucher Reload &3Reloaded the configuration files."));
    
    private String path;
    private String defaultMessage;
    private List<String> defaultListMessage;
    
    private Messages(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }
    
    private Messages(String path, List<String> defaultListMessage) {
        this.path = path;
        this.defaultListMessage = defaultListMessage;
    }
    
    public static String convertList(List<String> list) {
        String message = "";
        for (String line : list) {
            message += Methods.color(line) + "\n";
        }
        return message;
    }
    
    public static void addMissingMessages() {
        FileConfiguration messages = Files.MESSAGES.getFile();
        boolean saveFile = false;
        for (Messages message : values()) {
            if (!messages.contains("Messages." + message.getPath())) {
                saveFile = true;
                if (message.getDefaultMessage() != null) {
                    messages.set("Messages." + message.getPath(), message.getDefaultMessage());
                } else {
                    messages.set("Messages." + message.getPath(), message.getDefaultListMessage());
                }
            }
        }
        if (saveFile) {
            Files.MESSAGES.saveFile();
        }
    }
    
    public String getMessage() {
        return getMessage(true);
    }
    
    public String getMessage(String placeholder, String replacement) {
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put(placeholder, replacement);
        return getMessage(placeholders, true);
    }
    
    public String getMessage(HashMap<String, String> placeholders) {
        return getMessage(placeholders, true);
    }
    
    public String getMessageNoPrefix() {
        return getMessage(false);
    }
    
    public String getMessageNoPrefix(String placeholder, String replacement) {
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put(placeholder, replacement);
        return getMessage(placeholders, false);
    }
    
    public String getMessageNoPrefix(HashMap<String, String> placeholders) {
        return getMessage(placeholders, false);
    }
    
    public static String replacePlaceholders(HashMap<String, String> placeholders, String message) {
        for (String placeholder : placeholders.keySet()) {
            message = message.replaceAll(placeholder, placeholders.get(placeholder))
            .replaceAll(placeholder.toLowerCase(), placeholders.get(placeholder));
        }
        return message;
    }
    
    public static List<String> replacePlaceholders(HashMap<String, String> placeholders, List<String> messageList) {
        List<String> newMessageList = new ArrayList<>();
        for (String message : messageList) {
            for (String placeholder : placeholders.keySet()) {
                newMessageList.add(message.replaceAll(placeholder, placeholders.get(placeholder))
                .replaceAll(placeholder.toLowerCase(), placeholders.get(placeholder)));
            }
        }
        return newMessageList;
    }
    
    private String getMessage(boolean prefix) {
        return getMessage(new HashMap<>(), prefix);
    }
    
    private String getMessage(HashMap<String, String> placeholders, boolean prefix) {
        String message;
        boolean isList = isList();
        boolean exists = exists();
        if (isList) {
            if (exists) {
                message = Methods.color(convertList(Files.MESSAGES.getFile().getStringList("Messages." + path)));
            } else {
                message = Methods.color(convertList(getDefaultListMessage()));
            }
        } else {
            if (exists) {
                message = Methods.color(Files.MESSAGES.getFile().getString("Messages." + path));
            } else {
                message = Methods.color(getDefaultMessage());
            }
        }
        for (String placeholder : placeholders.keySet()) {
            message = message.replaceAll(placeholder, placeholders.get(placeholder))
            .replaceAll(placeholder.toLowerCase(), placeholders.get(placeholder));
        }
        if (isList) {//Don't want to add a prefix to a list of messages.
            return Methods.color(message);
        } else {//If the message isn't a list.
            if (prefix) {//If the message needs a prefix.
                return Methods.getPrefix(message);
            } else {//If the message doesn't need a prefix.
                return Methods.color(message);
            }
        }
    }
    
    private boolean exists() {
        return Files.MESSAGES.getFile().contains("Messages." + path);
    }
    
    private boolean isList() {
        if (Files.MESSAGES.getFile().contains("Messages." + path)) {
            return !Files.MESSAGES.getFile().getStringList("Messages." + path).isEmpty();
        } else {
            return defaultMessage == null;
        }
    }
    
    private String getPath() {
        return path;
    }
    
    private String getDefaultMessage() {
        return defaultMessage;
    }
    
    private List<String> getDefaultListMessage() {
        return defaultListMessage;
    }
    
}