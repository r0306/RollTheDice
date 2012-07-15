package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class _20 extends Arena implements Listener
{
	
	static HashMap<Player, List<Location>> explosiveBlocks = new HashMap<Player, List<Location>>();
	
	@EventHandler
	public void onChestPlace(BlockPlaceEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 20))
		{
			
			if (event.getBlock().getType() == Material.CHEST)
			{
				
				if (!explosiveBlocks.containsKey(player))
				{
					
					List<Location> list = new ArrayList<Location>();
					explosiveBlocks.put(player, list);
					
				}
				
				List<Location> list = explosiveBlocks.get(player);
				list.add(event.getBlock().getLocation());
				
				explosiveBlocks.put(player, list);
				
			}
			
		}
		
	}
	
}
