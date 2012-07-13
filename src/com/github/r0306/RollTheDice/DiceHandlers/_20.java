package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _20 extends Arena implements Listener
{
	
	static HashMap<Entity, Integer> ids = new HashMap<Entity, Integer>();

	@EventHandler
	public void onThrow(ExpBottleEvent event)
	{
		
		ThrownExpBottle entity = event.getEntity();
		
		if (entity.getShooter() instanceof Player)
		{
			
			Player player = (Player) entity.getShooter();
			
			if (inMatch.contains(player))
			{
				
				carePackageSmoke(entity);
				
			}
			
		}
		
	}
	
	public void carePackageSmoke(final ThrownExpBottle entity)
	{
		
		final Location location = entity.getLocation();
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			int counter = 0;
			
			@Override
			public void run() 
			{
				
				location.getWorld().playEffect(location, Effect.EXTINGUISH, 500);
				
				for (int i = 0; i < 9; i ++)
				{
					
					location.getWorld().playEffect(location, Effect.SMOKE, i, 500);
			
				}
				
				counter ++;
				
				if (counter == 30)
				{
					
					Bukkit.getScheduler().cancelTask(ids.get(entity));
					dropCarePackage(entity, location);
					
				}
			
			}
			
		}, 10L, 10L);

		ids.put(entity, id);
		
	}
	
	public void dropCarePackage(final Entity entity, final Location location)
	{
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
		
			Location loc = new Location(location.getWorld(), location.getBlockX(), 256, location.getBlockZ());
			loc.
			
			@Override
			public void run() 
			{
				
				
			
			}
			
		}, 10L, 10L);
		
	}
	
}
