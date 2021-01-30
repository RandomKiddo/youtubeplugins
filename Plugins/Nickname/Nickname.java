package io.github.randomkiddo.nickname;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import java.util.HashMap;

public class Nickname extends JavaPlugin implements Listener {
	private HashMap<Player, String> nicks;
	@Override
	public void onEnable() {
		this.nicks = new HashMap<Player, String>();
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && 
		    (command.getName().equalsIgnoreCase("nick") || command.getName().equalsIgnoreCase("nickname"))) {
			Player player = (Player)sender;
			String nick = args[0];
			player.setDisplayName(nick);
			player.setPlayerListName(nick);
			this.nicks.put(player, nick);
			return true;
		}
		return false;
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent death) {
		Player player = death.getEntity();
		String nick = this.nicks.get(player);
		if (nick != null) {
			String currentMessage = death.getDeathMessage();
			int index = currentMessage.indexOf(player.getName());
			String newMessage = currentMessage.substring(0, index) 
				+ nick 
				+ currentMessage.substring(index + player.getName().length());
			death.setDeathMessage(newMessage);
		}
	}
}
