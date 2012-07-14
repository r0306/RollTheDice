package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
			
		//	if (inMatch.contains(player))
		//	{

				carePackageSmoke(entity);
				
		//	}
			
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

				location.getWorld().playEffect(location.getBlock().getRelative(0, 8, 0).getLocation(), Effect.ENDER_SIGNAL, 500);
				
				location.getWorld().playEffect(location, Effect.EXTINGUISH, 500);
				
				for (int i = 0; i < 9; i ++)
				{
				
					for (int x = 0; x < 9; x ++)
					{

						location.getWorld().playEffect(location.getBlock().getRelative(0, i, 0).getLocation(), Effect.SMOKE, x, 500);
						location.getWorld().playEffect(location.getBlock().getRelative(0, i, 0).getLocation(), Effect.SMOKE, x, 500);
					

					}
					
					for (int x = 8; x > -1; x --)
					{
					
						location.getWorld().playEffect(location.getBlock().getRelative(0, i, 0).getLocation(), Effect.SMOKE, x, 500);
						location.getWorld().playEffect(location.getBlock().getRelative(0, i, 0).getLocation(), Effect.SMOKE, x, 500);
					

					}
						
				}
								
				counter ++;
				
				if (counter == 60)
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
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
		
			Location loc = new Location(location.getWorld(), location.getBlockX(), 255, location.getBlockZ());
			Material material = loc.getBlock().getType();
			byte data = loc.getBlock().getData();

			@Override
			public void run() 
			{
			
				material = loc.getBlock().getType();
				data = loc.getBlock().getData();	
				loc.getBlock().setType(Material.CHEST);
				
				if (loc.getBlockY() != 255)
				{

					loc.getBlock().getRelative(0, 1, 0).setType(material);
					loc.getBlock().getRelative(0, 1, 0).setData(data);
					
				}

				loc = loc.subtract(0, 1, 0);
				
				if (loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.WATER && loc.getBlock().getType() != Material.STATIONARY_WATER && loc.getBlock().getType() != Material.LAVA && loc.getBlock().getType() != Material.STATIONARY_LAVA)
				{
					
					loc.getWorld().playEffect(loc, Effect.GHAST_SHOOT, 10);
					Bukkit.getScheduler().cancelTask(ids.get(entity));
					ids.remove(entity);
					
				}
								
			}
			
		}, 1L, 1L);
		
		ids.put(entity, id);
		
	}
	
}
