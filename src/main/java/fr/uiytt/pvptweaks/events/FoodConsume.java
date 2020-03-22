package fr.uiytt.pvptweaks.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fr.uiytt.pvptweaks.ConfigManager;

public class FoodConsume implements Listener {

	@EventHandler
	public void onItemConsume (PlayerItemConsumeEvent event) {
		if(!ConfigManager.isGoldenAppleModuleEnabled()) {
			return;
		}
		if(event.getItem().getType() == Material.GOLDEN_APPLE) {
			Player player = event.getPlayer();
			event.setCancelled(true);
			player.getInventory().removeItem(new ItemStack(Material.GOLDEN_APPLE,1));
			player.updateInventory();
			
			for(PotionEffect effect : ConfigManager.getPotionseffects()) {
				if(player.hasPotionEffect(effect.getType())) {
					player.removePotionEffect(effect.getType());
				}
				player.addPotionEffect(effect);
			}
			
			player.setFoodLevel(player.getFoodLevel() + ConfigManager.getFoodRegen());
		}
	}
	
}
