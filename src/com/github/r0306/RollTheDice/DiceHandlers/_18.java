package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _18 extends Arena implements Listener
{

	static HashMap<String, Integer> ids = new HashMap<String, Integer>();

	public static void scheduleRepeatingUpdate(final Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			int radius = 20;
			
			@Override
			public void run() 
			{
					
				Location location = player.getLocation();
				Location locationAhead = location.getBlock().getRelative(getPlayerFacing(player), 20).getLocation();
				for (int x = -(radius); x <= radius; x++)
				{
					
					for (int y = -(radius); y <= radius; y++)
					{
						
						for (int z = -(radius); z <= radius; z++)
						{
						
							Location loc = locationAhead.getBlock().getRelative(x, y, z).getLocation();
			            	
							if (!getTransparentMaterials().contains(loc.getBlock().getType()))
							{
							
								player.sendBlockChange(loc, Material.SNOW_BLOCK.getId(), (byte) 0);

							}

						}
					
					}
			
				}
				
				for (int x = -(radius); x <= radius; x++)
				{
					
					for (int y = -(radius); y <= radius; y++)
					{
						
						for (int z = -(radius); z <= radius; z++)
						{
							
							Location loc = location.getBlock().getRelative(x, y, z).getLocation();
			            	
							if (!getTransparentMaterials().contains(loc.getBlock().getType()))
							{
							
								player.sendBlockChange(loc, Material.SNOW_BLOCK.getId(), (byte) 0);
						
							}
						
						}
					
					}
				
				}		
				
			}
		
		}, 0L, 100L);
		
		ids.put(player.getName(), id);
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
			
		Player player = event.getEntity();
		
		if (isIn(player, 18))
		{
			
			Bukkit.getScheduler().cancelTask(ids.get(player.getName()));
			ids.remove(player.getName());
			
		}
		
	}
	
	public static BlockFace getPlayerFacing(Player player)
	{
	
         float y = player.getLocation().getYaw();
         if( y < 0 ) y += 360;
         y %= 360;
         int i = (int)((y+8) / 22.5);
         
         if(i == 0) return BlockFace.WEST;
         else if(i == 1) return BlockFace.NORTH_WEST;
         else if(i == 2) return BlockFace.NORTH_WEST;
         else if(i == 3) return BlockFace.NORTH_WEST;
         else if(i == 4) return BlockFace.NORTH;
         else if(i == 5) return BlockFace.NORTH_EAST;
         else if(i == 6) return BlockFace.NORTH_EAST;
         else if(i == 7) return BlockFace.NORTH_EAST;
         else if(i == 8) return BlockFace.EAST;
         else if(i == 9) return BlockFace.SOUTH_EAST;
         else if(i == 10) return BlockFace.SOUTH_EAST;
         else if(i == 11) return BlockFace.SOUTH_EAST;
         else if(i == 12) return BlockFace.SOUTH;
         else if(i == 13) return BlockFace.SOUTH_WEST;
         else if(i == 14) return BlockFace.SOUTH_WEST;
         else if(i == 15) return BlockFace.SOUTH_WEST;

         return BlockFace.WEST;
		
	}
	
	public static List<Material> getTransparentMaterials()
	{
		
		Material[] materials = {Material.AIR, Material.BED, Material.BED_BLOCK, Material.BREWING_STAND, Material.BROWN_MUSHROOM,
				   				Material.BURNING_FURNACE, Material.CACTUS, Material.CAKE_BLOCK, Material.CAULDRON, Material.CHEST,
				   				Material.DEAD_BUSH, Material.DETECTOR_RAIL, Material.DIODE, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON,
				   				Material.DISPENSER, Material.DRAGON_EGG, Material.EGG, Material.ENCHANTMENT_TABLE, Material.ENDER_PORTAL, Material.ENDER_PORTAL_FRAME,
				   				Material.ENDER_STONE, Material.FENCE, Material.FENCE_GATE, Material.FIRE, Material.FURNACE, Material.GLASS, Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2,
				   				Material.IRON_DOOR_BLOCK, Material.IRON_FENCE, Material.JUKEBOX, Material.LADDER, Material.LAVA, Material.LEVER, Material.LONG_GRASS, Material.MELON_STEM,
				   				Material.MOB_SPAWNER, Material.NETHER_FENCE, Material.NETHER_STALK, Material.NETHER_WARTS, Material.PAINTING, Material.PISTON_BASE, Material.PISTON_EXTENSION,
				   				Material.PISTON_MOVING_PIECE, Material.PISTON_STICKY_BASE, Material.PORTAL, Material.PUMPKIN_STEM, Material.RED_ROSE, Material.RED_MUSHROOM, Material.SAPLING, Material.SIGN, Material.SIGN_POST, Material.STATIONARY_LAVA,
				   				Material.STATIONARY_WATER, Material.STONE_BUTTON, Material.SUGAR_CANE_BLOCK, Material.THIN_GLASS, Material.TNT, Material.TORCH, Material.TRAP_DOOR, Material.VINE, Material.WALL_SIGN,
				   				Material.WATER, Material.WEB, Material.WHEAT, Material.WOODEN_DOOR, Material.WORKBENCH, Material.YELLOW_FLOWER};
		
		return Arrays.asList(materials);
		
	}
	
}
