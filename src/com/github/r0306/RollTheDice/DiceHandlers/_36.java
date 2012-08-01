package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class _36 extends Arena implements Listener
{

	@EventHandler
	public void onTurn(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 36))
		{
			
			if (event.getFrom().getYaw() > event.getTo().getYaw())
			{
				
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
}
