package io.github.randomkiddo.potionrepeater;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import java.util.ArrayList;
import java.util.Collection;

public class PotionRepeater extends JavaPlugin {
	private boolean enabled;
	public static final PotionEffectType[] effects = {
			PotionEffectType.ABSORPTION,
			PotionEffectType.BAD_OMEN,
			PotionEffectType.BLINDNESS,
			PotionEffectType.CONDUIT_POWER,
			PotionEffectType.CONFUSION,
			PotionEffectType.DAMAGE_RESISTANCE,
			PotionEffectType.DOLPHINS_GRACE,
			PotionEffectType.FAST_DIGGING,
			PotionEffectType.FIRE_RESISTANCE,
			PotionEffectType.GLOWING,
			PotionEffectType.HARM,
			PotionEffectType.HEAL,
			PotionEffectType.HEALTH_BOOST,
			PotionEffectType.HERO_OF_THE_VILLAGE,
			PotionEffectType.HUNGER,
			PotionEffectType.INCREASE_DAMAGE,
			PotionEffectType.INVISIBILITY,
			PotionEffectType.JUMP,
			PotionEffectType.LEVITATION,
			PotionEffectType.LUCK,
			PotionEffectType.NIGHT_VISION,
			PotionEffectType.POISON,
			PotionEffectType.REGENERATION,
			PotionEffectType.SATURATION,
			PotionEffectType.SLOW,
			PotionEffectType.SLOW_DIGGING,
			PotionEffectType.SLOW_FALLING,
			PotionEffectType.SPEED,
			PotionEffectType.UNLUCK,
			PotionEffectType.WATER_BREATHING,
			PotionEffectType.WEAKNESS,
			PotionEffectType.WITHER
	};
	private static ArrayList<Player> players;
	@Override
	public void onEnable() {
		this.enabled = true;
		PotionRepeater.players = getOnlinePlayers();
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player player : PotionRepeater.players) {
					PotionEffectType effectType = getRandomEffect();
					PotionEffect effect = new PotionEffect(
						effectType,
						1200, //1200 ticks = 60 seconds = 1 minute
						1
					);
					player.addPotionEffect(effect);
				}
			}
		}, 0, 1200L);
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
	private PotionEffectType getRandomEffect() {
		int index = (int)(Math.random() * PotionRepeater.effects.length);
		return PotionRepeater.effects[index];
	}
	@Override
	public void onDisable() {
		this.enabled = false;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (this.enabled && command.getName().equalsIgnoreCase("startpotions")) {
			onEnable();
			return true;
		} else if (this.enabled && command.getName().equalsIgnoreCase("stoppotions")) {
			onDisable();
			return true;
		}
		return false;
	}
}
