package io.github.randomkiddo.customcompass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.ArrayList;

public class CustomCompass extends JavaPlugin implements Listener {
	private ArrayList<Player> players;
	private Player runner;
	private Location worldSpawnNought;
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		this.players = getOnlinePlayers();
		this.worldSpawnNought = Bukkit.getWorld("world").getSpawnLocation();
	}
	private ArrayList<Player> getOnlinePlayers() {
		Collection<? extends Player> playerLikeEntities = Bukkit.getOnlinePlayers();
		return filter(playerLikeEntities);
	}
	private static ArrayList<Player> filter(Collection<? extends Player> playerLikeEntities) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Object entity : playerLikeEntities) {
			if (entity instanceof Player) {
				players.add((Player)entity);
			}
		}
		return players;
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent interact) {
		boolean action = interact.getAction() == Action.RIGHT_CLICK_AIR || interact.getAction() == Action.RIGHT_CLICK_BLOCK;
		boolean item = interact.getItem().getType() == Material.COMPASS;
		if (action && item) {
			Bukkit.getWorld("world").setSpawnLocation(this.runner.getLocation());
		}
	}
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent respawn) {
		if (!respawn.isBedSpawn()) {
			respawn.setRespawnLocation(this.worldSpawnNought);
		}
		respawn.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("speedrunner")) {
			for (Player player : this.players) {
				if (player.getName().equalsIgnoreCase(args[0])) {
					this.runner = player;
					return true;
				}
			}
		}
		return false;
	}
}