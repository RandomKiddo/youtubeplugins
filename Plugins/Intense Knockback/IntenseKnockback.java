package io.github.randomkiddo.intenseknockback;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.Collection;

public class IntenseKnockback extends JavaPlugin implements Listener {
	private static ArrayList<Player> players;
	@Override
	public void onEnable() {
		IntenseKnockback.players = getOnlinePlayers();
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player player : IntenseKnockback.players) {
					PotionEffect speed = new PotionEffect(
						PotionEffectType.SPEED,
						1200,
						40
					);
					player.addPotionEffect(speed);
				}
			}
		}, 1, 1200L);
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
	public void onEntityDamage(EntityDamageEvent damageTaken) {
		if (damageTaken.getEntity() instanceof Player) {
			Player player = (Player)damageTaken.getEntity();
			player.setVelocity(
				player.getLocation().getDirection().multiply(-30)
			);
		}
	}
	@EventHandler
	public void onRespawn(PlayerRespawnEvent respawn) {
		final Player player = respawn.getPlayer();
		final PotionEffect speed = new PotionEffect(
			PotionEffectType.SPEED,
			1200,
			25
		);
		player.addPotionEffect(speed);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				player.addPotionEffect(speed);
			}
		}, 20*5);
	}
}
