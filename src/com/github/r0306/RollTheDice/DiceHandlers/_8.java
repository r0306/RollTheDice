package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class _8 extends Arena implements Listener
{

	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 8))
		{
			
			player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 500);
			player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 500);
			player.getWorld().playEffect(player.getLocation(), Effect.EXTINGUISH, 500);

		}
		
	}

}
