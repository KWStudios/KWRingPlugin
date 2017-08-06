package org.kwstudios.play.kwringplugin.loader;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginLoader extends JavaPlugin {

	private static PluginLoader instance = null;

	@Override
	public void onEnable() {
		super.onEnable();

		PluginLoader.instance = this;

		PluginDescriptionFile pluginDescriptionFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");

		new EventListener(this, getConfig());

		logger.info(pluginDescriptionFile.getName() + " was loaded successfully! (Version: "
				+ pluginDescriptionFile.getVersion() + ")");
	}

	@Override
	public void onDisable() {
		super.onDisable();

		PluginDescriptionFile pluginDescriptionFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");

		logger.info(pluginDescriptionFile.getName() + " was unloaded successfully! (Version: "
				+ pluginDescriptionFile.getVersion() + ")");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a Player!");
			return false;
		}

		Player ringer = (Player) sender;
		
		if (command.getLabel().equalsIgnoreCase("ring")) {
			if (args.length < 1) {
				ringer.sendMessage("Usage: /ring <player> [Custom Message]");
				return false;
			}
			String ringeeName = args[0];
			Player ringee = Bukkit.getPlayer(ringeeName);
			
			if (ringee == null) {
				ringer.sendMessage("Player " + ringeeName + " does not seem to be online right now...");
				return true;
			} else {
				ringee.playSound(ringee.getLocation(), Sound.ENDERMAN_SCREAM, 100F, 1F);
				ringee.sendMessage("The player " +  ringer.getName() + " notifies you.");
				if (args.length > 1) {
					String msg = "";
					for (int i = 1; i < args.length; i++) {
						msg += args[i] + " ";
					}
					ringee.sendMessage("Custom Message: " + msg);
				}
				
				ringer.sendMessage("The player " + ringeeName + " was notified!");
			}
		}

		return true;
	}

	public static PluginLoader getInstance() {
		return PluginLoader.instance;
	}
}
