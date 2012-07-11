package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class _12 extends Arena implements Listener
{

	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 12))
		{
			
			player.resetPlayerTime();
			
		}
		
	}
	
}
