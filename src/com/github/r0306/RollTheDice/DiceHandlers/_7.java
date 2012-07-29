package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import com.github.r0306.RollTheDice.Util.Plugin;

public class _7 extends Arena implements Listener
{

	static HashMap<Location, Byte> iceLocations = new HashMap<Location, Byte>();
	static List<Location> water = new ArrayList<Location>();
	
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

					iceLocations.put(block.getLocation(), block.getData());
					block.setType(Material.ICE);
					scheduleDelayedThaw(player, block);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void preventSpread(BlockFromToEvent event)
	{
				
		if (event.getBlock().getType() == Material.STATIONARY_WATER || event.getBlock().getType() == Material.WATER)
		{
			
			if (event.getToBlock().getType() == Material.STATIONARY_WATER || event.getToBlock().getType() == Material.WATER)
			{

			    if (isSource(event.getBlock().getData()))
				{

					event.setCancelled(true);
					
				}
			
			}
			
		}
		
	}
	
	@EventHandler
	public void onForm(BlockSpreadEvent event)
	{
		
		if (event.getBlock().getType() == Material.WATER || event.getBlock().getType() == Material.STATIONARY_WATER)
		{
			
			if (water.contains(event.getSource()))
			{
				
				event.setCancelled(true);
				
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
					
						if (!isSource(iceLocations.get(block.getLocation())))
						{
							
							block.setType(Material.WATER);
							block.setData(iceLocations.get(block.getLocation()));
							
						}
						else if (isSource(iceLocations.get(block.getLocation())))
						{
							
							block.setType(Material.WATER);
							
						}
						
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
		
	public boolean isSource(byte block)
	{
		
		return Integer.parseInt(Byte.toString(block)) == 0;
		
	}
	
	public String source(boolean isSource)
	{
		
		return (isSource) ? "source" : "stream";
		
	}
	
}
