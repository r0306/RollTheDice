package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class _4 extends Arena implements Listener
{

	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 4))
		{

			if (!player.isSprinting())
			{
			
				player.setSprinting(true);
			
			}
			
			player.setVelocity(player.getVelocity().multiply(0.1));
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500, 1));
		}
		
	}
		
}
