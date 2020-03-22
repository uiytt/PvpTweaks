package fr.uiytt.pvptweaks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.leonhard.storage.Yaml;
import fr.uiytt.pvptweaks.events.ForceModifier;

public class ConfigManager {

	private Yaml config;
	
	public static final int SpigotID = 76387;
	public static final String ConfigLink = "https://raw.githubusercontent.com/uiytt/PvpTweaks/master/config.yml";
	
	private static boolean GoldenAppleModuleEnabled = true;
	private static int FoodRegen = 0;
	private static List<PotionEffect> potionseffects = new ArrayList<PotionEffect>();
	private static boolean StrengthModuleEnabled = true;
	private static String Strengthvalue = "0.75";
	private static boolean update = true;
	
	
	public ConfigManager(Yaml config) {
		this.config = config;;
		this.loadData();
	}
	
	public void loadData() {
		GoldenAppleModuleEnabled = true;
		StrengthModuleEnabled = true;
		String [] defaulteffect = {"FOODREGEN:4","REGENERATION:600:0","ABSORPTION:2400:0"};
		update = true;
		if(potionseffects != null && !potionseffects.isEmpty()) {
			potionseffects.clear();
		}
		List<String> effects = Arrays.asList(config.getOrSetDefault("GoldenApplemodule.effect",defaulteffect));
		
		for(String effect : effects) {
			String[] parts = effect.split(":");
			if(effect.contains("FOODREGEN")) {
				try {
					FoodRegen = Integer.parseInt(parts[1]);
				} catch(NumberFormatException e) {
					e.printStackTrace();
				}
				
			}else {
				PotionEffectType potioneffecttype = PotionEffectType.getByName(parts[0]);
				if(potioneffecttype != null) {
					try {
						PotionEffect  potioneffect = new PotionEffect(potioneffecttype, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
						potionseffects.add(potioneffect);
					} catch(NumberFormatException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		//Deactivate goldenAppleModule if false in config
		if(!config.getOrSetDefault("GoldenApplemodule.enabled", true)) {
			GoldenAppleModuleEnabled = false;
		}
		//Deactivate Strength module if false in config
		if(!config.getOrSetDefault("Strengthmodule.enabled",true)) {
			StrengthModuleEnabled = false;
		}
		//Deactivate checkforupdate if false in config
		if(!config.getOrSetDefault("checkforupdate",true)) {
			update = false;
		}
		ForceModifier.setPercentage(false);
		String strengthstring = config.getOrSetDefault("Strengthmodule.value","0.75");
		if(strengthstring.contains((CharSequence) "%")) {
			String[] valueList = strengthstring.split("%");
			Strengthvalue = valueList[0];
			ForceModifier.setPercentage(true);
		} else {
			Strengthvalue =  strengthstring; 
		}
		
		
		
	}

	public static boolean isGoldenAppleModuleEnabled() {
		return GoldenAppleModuleEnabled;
	}

	public static int getFoodRegen() {
		return FoodRegen;
	}

	public static List<PotionEffect> getPotionseffects() {
		return potionseffects;
	}

	public static boolean isUpdate() {
		return update;
	}

	public static String getStrengthvalue() {
		return Strengthvalue;
	}

	public static boolean isStrengthModuleEnabled() {
		return StrengthModuleEnabled;
	}

	
}
