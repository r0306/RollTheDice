package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Util.Colors;

public class _2 extends Arena implements Listener, Colors
{
	
	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent event)
	{
		
		if (event.getDamager() instanceof Player)
		{
			
			Player player = (Player) event.getDamager();
			
			if (isIn(player, 2))
			{
				
				event.setCancelled(true);
				player.sendMessage(gold + pluginName + red + "You cower in fear in the face of your opponents.");
			
			}
			
		}
		
	}
	
	@EventHandler
	public void onBowShoot(EntityShootBowEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (isIn(player, 2))
			{
				
				event.setCancelled(true);
				player.sendMessage(gold + pluginName + red + "Wow. I never knew you could even hold a bow!");
				
			}
			
		}
		
	}
	
}
