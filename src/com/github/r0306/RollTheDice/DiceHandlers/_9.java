package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class _9 extends Arena implements Listener
{

	@EventHandler
	public void onRegen(EntityRegainHealthEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (isIn(player, 9))
			{
				
				if (player.getHealth() + event.getAmount() > 2)
				{
					
					event.setCancelled(true);
					
				}
				
			}
			
		}
		
	}
	
}
