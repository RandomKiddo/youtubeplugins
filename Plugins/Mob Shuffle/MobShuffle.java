package io.github.randomkiddo.mobshuffle;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.ArrayList;
import java.util.HashMap;

public class MobShuffle extends JavaPlugin implements Listener {
	private ArrayList<EntityType> entityTypes;
	private HashMap<EntityType, EntityType> swapPairs;
	private boolean respawned;
	@Override
	public void onEnable() {
		this.entityTypes = new ArrayList<EntityType>();
		fillEntityTypes();
		this.swapPairs = new HashMap<EntityType, EntityType>();
		generateSwapPairs();
		correctFishPairs();
		this.respawned = false;
		Bukkit.getServer().getPluginManager().registerEvents(this,  this);
	}
	private void fillEntityTypes() {
		for (EntityType entityType : EntityType.values()) {
			this.entityTypes.add(entityType);
		}
	}
	private void generateSwapPairs() {
		int numGenerated = 0;
		while (numGenerated < entityTypes.size()) {
			EntityType one = this.entityTypes.get(
			(int)(Math.random() * this.entityTypes.size())
			);
			EntityType two = this.entityTypes.get(
			(int)(Math.random() * this.entityTypes.size())
			);
			boolean tilt = checkForTilt(one, two);
			if (!tilt) {
				this.swapPairs.put(one, two);
				this.swapPairs.put(two, one);
				numGenerated += 2;
			}
		}
	}
	private boolean checkForTilt(EntityType one, EntityType two) {
		return one == two ||
		(this.swapPairs.get(one) != null && this.swapPairs.get(two) != null);
	}
	private void correctFishPairs() {
		this.swapPairs.put(EntityType.COD, EntityType.COD);
		this.swapPairs.put(EntityType.PUFFERFISH,  EntityType.PUFFERFISH);
		this.swapPairs.put(EntityType.SALMON, EntityType.SALMON);
		this.swapPairs.put(EntityType.SQUID, EntityType.SQUID);
	}
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent spawn) {
		if (spawn.getSpawnReason() == SpawnReason.NATURAL && !this.respawned) {
			Location location = spawn.getLocation();
			World world = spawn.getEntity().getWorld();
			EntityType originalType = spawn.getEntityType();
			spawn.setCancelled(true);
			EntityType swapType = this.swapPairs.get(originalType);
			this.respawned = true;
			world.spawnEntity(location, swapType);
		}
		this.respawned = false;
	}
}
