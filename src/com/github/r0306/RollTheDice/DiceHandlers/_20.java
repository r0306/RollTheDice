package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.r0306.RollTheDice.Util.Colors;

public class _20 extends Arena implements Listener, Colors
{
	
	static HashMap<String, List<Location>> explosiveBlocks = new HashMap<String, List<Location>>();
	static HashMap<String, Location> openingChest = new HashMap<String, Location>();
	
	@EventHandler
	public void onChestPlace(BlockPlaceEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 20))
		{
			
			if (event.getBlock().getType() == Material.CHEST)
			{
				
				if (!explosiveBlocks.containsKey(player.getName()))
				{
					
					List<Location> list = new ArrayList<Location>();
					explosiveBlocks.put(player.getName(), list);
					
				}
				
				List<Location> list = explosiveBlocks.get(player.getName());
				list.add(event.getBlock().getLocation());
				
				explosiveBlocks.put(player.getName(), list);
				
				player.sendMessage(gold + pluginName + daqua + "Fake care package set.");
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onTrapOpen(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			
			for (List<Location> list : explosiveBlocks.values())
			{
			
				if (list.contains(event.getClickedBlock().getLocation()))
				{
					
					registerOpeningChest(player, event.getClickedBlock().getLocation());
					
				}
				
			}
			
		}
		
	}
		
	public void registerOpeningChest(Player player, Location location)
	{
		
		if (!openingChest.containsKey(player.getName()))
		{
			
			openingChest.put(player.getName(), location);
			
		}
		
	}
	
	public void removeOpeningChest(Player player)
	{
		
		if (openingChest.containsKey(player.getName()))
		{
			
			openingChest.remove(player.getName());
			
		}
		
	}
	
}
