package io.github.randomkiddo.mobswitch;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.Location;

import java.util.ArrayList;

public class MobSwitch extends JavaPlugin implements Listener {
    private boolean swapped = false;
    private ArrayList<EntityType> entities;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        this.entities = new ArrayList<EntityType>();
        for (EntityType entity : EntityType.values()) { this.entities.add(entity); }
    }
    private EntityType rand() {
        int index = (int)(Math.random() * entities.size());
        return this.entities.get(index);
    }
    @EventHandler
    public void onSpawn(CreatureSpawnEvent spawn) {
    	Location location = spawn.getLocation();
    	if (!swapped) {
    		swapped = true;
    		spawn.setCancelled(true);
    		Bukkit.getServer().getWorlds().get(0).spawnEntity(location, rand());
    	}
    	swapped = false;
    }
}