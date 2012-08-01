package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

public class _37 extends Arena implements Listener
{

	@EventHandler
	public void onGrapple(PlayerFishEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 37))
		{
			
			if (event.getState() == State.IN_GROUND)
			{
				
				
				
			}
			
		}
		
	}
	
}
