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
        if (isNotMob(this.entities.get(index))) {
        	return rand();
        }
        return this.entities.get(index);
    }
    private boolean isNotMob(EntityType check) {
    	EntityType[] notMobs = {
    			EntityType.AREA_EFFECT_CLOUD, EntityType.ARROW,
    			EntityType.DRAGON_FIREBALL, EntityType.DROPPED_ITEM,
    			EntityType.EGG, EntityType.ENDER_CRYSTAL,
    			EntityType.ENDER_PEARL, EntityType.ENDER_SIGNAL,
    			EntityType.EVOKER_FANGS, EntityType.EXPERIENCE_ORB,
    			EntityType.FALLING_BLOCK, EntityType.FIREBALL,
    			EntityType.FIREWORK, EntityType.FISHING_HOOK,
    			EntityType.ITEM_FRAME, EntityType.LEASH_HITCH,
    			EntityType.LIGHTNING, EntityType.PAINTING,
    			EntityType.PRIMED_TNT, EntityType.SHULKER_BULLET,
    			EntityType.SMALL_FIREBALL, EntityType.SNOWBALL,
    			EntityType.SPECTRAL_ARROW, EntityType.SPLASH_POTION,
    			EntityType.THROWN_EXP_BOTTLE, EntityType.UNKNOWN,
    			EntityType.WITHER_SKULL,
    			EntityType.WITHER, EntityType.ENDER_DRAGON // Player & Server Protections
    	};
    	for (EntityType type : notMobs) {
    		if (check == type) {
    			return true;
    		}
    	}
    	return false;
    }
    @EventHandler
    public void onSpawn(CreatureSpawnEvent spawn) {
    	Location location = spawn.getLocation();
    	if (!swapped) {
    		swapped = true;
    		spawn.setCancelled(true);
    		try {
    			Bukkit.getServer().getWorlds().get(0).spawnEntity(location, rand());
    		} catch (Exception err) {
    			spawn.setCancelled(false);
    		} 
    	}
    	swapped = false;
    }
}