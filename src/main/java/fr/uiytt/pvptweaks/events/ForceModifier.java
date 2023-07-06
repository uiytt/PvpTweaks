package fr.uiytt.pvptweaks.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.uiytt.pvptweaks.ConfigManager;

public class ForceModifier implements Listener{

	private static boolean percentage;

	@EventHandler
	public void PlayerDamagedByPlayer(EntityDamageByEntityEvent event) {
		if(!ConfigManager.isStrengthModuleEnabled()) {
			return;
		}
		if(!(event.getDamager() instanceof Player)) {
			return;
		}
		Player damager = (Player) event.getDamager();
		if(!damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
			return;
		}
		Double damage = event.getDamage();
		PotionEffect strength = damager.getPotionEffect(PotionEffectType.INCREASE_DAMAGE);
		damage = damage - (Double.valueOf((strength.getAmplifier() + 1)) * 3.0);
		if(isPercentage()) {
			Double finaledamage = damage * (1 + (Double.parseDouble(ConfigManager.getStrengthvalue()) * ((double) strength.getAmplifier() + 1.0) / 100.0));
			event.setDamage(finaledamage);
		}
		else {
			Double finaledamage = damage + Double.parseDouble(ConfigManager.getStrengthvalue()) * ((double) strength.getAmplifier() + 1.0);
			event.setDamage(finaledamage);
		}
	}
	
	public static boolean isPercentage() {
		return percentage;
	}
	public static void setPercentage(boolean percentage) {
		ForceModifier.percentage = percentage;
	}
}
