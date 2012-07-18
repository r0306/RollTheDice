package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class _24 extends Arena implements Listener
{

	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (isIn(player, 24))
			{
				
				if (event.getCause() == DamageCause.BLOCK_EXPLOSION || event.getCause() == DamageCause.ENTITY_EXPLOSION)
				{
					
					event.setCancelled(true);
					
				}
				
			}
			
		}
		
	}
	
}
