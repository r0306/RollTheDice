package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class _27 extends Arena implements Listener
{

	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 27))
		{
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500, 2), true);
			
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (isIn(player, 27))
			{
				
				if (event.getCause() == DamageCause.FALL)
				{
					
					if (event.getDamage() >= 1)
					{
						
						event.setDamage(0);
						event.setCancelled(true);
						
					}
					
				}
				
			}
			
			
		}
			
	}
	
}
