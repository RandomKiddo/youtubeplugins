package io.github.randomkiddo.mobswitch;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.ArrayList;

public class MobSwitch extends JavaPlugin implements Listener {
    private HashMap<EntityType, EntityType> swapPairs;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        this.swapPairs = new HashMap<EntityType, EntityType>();
        ArrayList<EntityType> entities = new ArrayList<EntityType>();
        for (EntityType entity : EntityType.values()) { entities.add(entity); }
        for (int i = 0; i < entities.size(); ++i) {
            EntityType value = entities.get(0);
            if (this.swapPairs.get(value) != null) { continue; }
            EntityType pair = rand(entities, value);
            this.swapPairs.put(value, pair);
            this.swapPairs.put(pair, value);
        }
    }
    private EntityType rand(ArrayList<EntityType> entities, EntityType check) {
        int index = (int)(Math.random() * entities.size());
        EntityType value = entities.get(index);
        if (value == check || this.swapPairs.get(value) != null) {
            rand(entities, check);
        } 
        return value;
    }
    @EventHandler
    public void onSpawn(EntitySpawnEvent spawn) {
    	Location location = spawn.getLocation();
    	spawn.setCancelled(true);
    	EntityType type = this.swapPairs.get(spawn.getEntityType());
    	Bukkit.getWorld("world").spawnEntity(location, type);
    }
}