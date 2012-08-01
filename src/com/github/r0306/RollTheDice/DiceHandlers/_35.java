package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _35 extends Arena implements Listener
{

	@EventHandler
	public void onDamage(final EntityDamageByEntityEvent event)
	{
		
		if (event.getDamager() instanceof Player)
		{
			
			Player player = (Player) event.getDamager();
			
			if (isIn(player, 35))
			{
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
				{

					@Override
					public void run() 
					{
				
						event.getEntity().setVelocity(event.getEntity().getVelocity().multiply(2));
				
					}
					
				}, 1L);
			
			}
			
		}
		
	}
	
}
