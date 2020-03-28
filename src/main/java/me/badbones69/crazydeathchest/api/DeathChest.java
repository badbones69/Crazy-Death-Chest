package me.badbones69.crazydeathchest.api;

public class DeathChest {
    
    private static DeathChest instance = new DeathChest();
    
    public static DeathChest getInstance() {
        return instance;
    }
    
}