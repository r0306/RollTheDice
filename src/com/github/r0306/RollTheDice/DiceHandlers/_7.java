package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _7 extends Arena implements Listener
{

	static HashMap<Location, Material> iceLocations = new HashMap<Location, Material>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 7))
		{
			
			Location location = player.getLocation().subtract(0, 1, 0);
			
			for(BlockFace face : BlockFace.values())
			{
			 
				Block block = location.getBlock().getRelative(face);
				
				if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER)
				{
					
					iceLocations.put(block.getLocation(), block.getType());
					block.setType(Material.ICE);
					scheduleDelayedThaw(player, block);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void preventSpread(BlockFromToEvent event)
	{
		
		if (event.getBlock().getType() == Material.WATER || event.getBlock().getType() == Material.STATIONARY_WATER)
		{
			
			if (event.getToBlock().getType() == Material.AIR)
			{
				
				for (BlockFace face : BlockFace.values())
				{
					
					Location location = event.getBlock().getRelative(face).getLocation();
					
					for (BlockFace f : BlockFace.values())
					{
					
						Location location2 = location.getBlock().getRelative(f).getLocation();
						
						if (location2.getBlock().getRelative(face).getType() == Material.ICE)		
						{
							
							if (iceLocations.containsKey(location2.getBlock()))
							{
								
								event.setCancelled(true);
							
							}
							
						}
						
					}
				
				}
			
			}
			
		}
		
	}
	
	public void scheduleDelayedThaw(final Player player, final Block block)
	{
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{
			
				if (block.getType() == Material.ICE)
				{
					
					if (!isAround(player, block.getLocation()))
					{
						
						block.setType(iceLocations.get(block.getLocation()));
						iceLocations.remove(block.getLocation());
						
					}
					else
					{
						
						scheduleDelayedThaw(player, block);
						
					}
					
				}
				
			}
			
		}, 50L);
		
	}
	
	public boolean isAround(Player player, Location location)
	{
				
		Location playerLoc = player.getLocation();
		
		int x = playerLoc.getBlockX();
		int y = playerLoc.getBlockY();
		int z = playerLoc.getBlockZ();
		int X = location.getBlockX();
		int Y = location.getBlockY();
		int Z = location.getBlockZ();
		
		return (Math.abs(X - x) <= 3 && Math.abs(Y - y) <= 3 && Math.abs(Z - z ) <= 3);
		
	}
	
}
