package fr.uiytt.pvptweaks;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import de.leonhard.storage.Yaml;
import fr.uiytt.pvptweaks.events.FoodConsume;
import fr.uiytt.pvptweaks.events.ForceModifier;

public class Main extends JavaPlugin {
	
	private static ConfigManager config;
	public static final String header = ChatColor.BOLD + "" + ChatColor.BLACK  + "[" + ChatColor.YELLOW + "PvpTweaks" + ChatColor.BOLD + "" + ChatColor.BLACK + "] " + ChatColor.GRAY;
	
	@Override
	public void onEnable() {
		
		
		File configFile = new File(this.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
		configFile.getParentFile().mkdirs();
		
		
		if(!configFile.exists()) {
			getLogger().fine("Config.yml not found, Downloading...");
			try {
				FileUtils.copyURLToFile(new URL(ConfigManager.ConfigLink), configFile, 10000, 10000);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		Yaml YamlConfig = new Yaml("config","plugins/PvpTweaks");
		config = new ConfigManager(YamlConfig);
		
		if(ConfigManager.isGoldenAppleModuleEnabled()) {
			getServer().getPluginManager().registerEvents(new FoodConsume(), this);
		}
		if(ConfigManager.isStrengthModuleEnabled()) {
			getServer().getPluginManager().registerEvents(new ForceModifier(), this);
		}
		if(ConfigManager.isUpdate())  {
			(new PluginUpdater(this)).checkForUpdate();
		}
		
		this.getCommand("pvptweaks").setExecutor(new CommandReloadConfig());

		
		
		
	}
	
	@Override
	public void onDisable() {
		
	}

	public static ConfigManager getConfigManager() {
		return config;
	}


}
