package fr.uiytt.pvptweaks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReloadConfig implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Main.getConfigManager().loadData();
		sender.sendMessage(Main.header + "The config was reloaded.");
		return true;
	}

}
