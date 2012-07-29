package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class _31 extends Arena implements Listener
{
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 31))
		{

			if (!getTransparentMaterials().contains(player.getLocation().getBlock().getRelative(getPlayerDirection(player)).getType()) && !getTransparentMaterials().contains(player.getEyeLocation().getBlock().getRelative(getPlayerDirection(player)).getType()))
			{

				if (hasMoved(event.getFrom(), event.getTo(), player))
				{
				
					walkThroughWalls(player);
					
				}
					
			}

		}
		
	}
	
	public boolean hasMoved(Location from, Location to, Player player)
	{
		
		double x = from.getX();
		double z = from.getZ();
		double X = to.getX();
		double Z = to.getX();
		
		return x != X && z != Z;
		
	}
	
	public boolean isNextTo(Player player)
	{
		
		Location location = player.getLocation();
		
		BlockFace face = getPlayerDirection(player);
		
		Location facingLocation = player.getLocation().getBlock().getRelative(face).getLocation();
		
		return location.distance(facingLocation) <= 1.4;
		
	}
	
	public void walkThroughWalls(final Player player)
	{
		
		if (checkThickness(player) && isNextTo(player))
		{
			
			BlockFace face = getPlayerDirection(player);
			
			Location location = player.getLocation().getBlock().getRelative(face, 2).getLocation();
			
			location.setYaw(player.getLocation().getYaw());
			
			location.setPitch(player.getLocation().getPitch());
			
			player.teleport(location);
			
		}
		
	}
	
	
	public boolean checkThickness(Player player)
	{
		
		BlockFace face = getPlayerDirection(player);
		
		Block block = player.getLocation().getBlock().getRelative(face, 2);		
		
		return getTransparentMaterials().contains(block.getType());
			
	}
	
    public BlockFace getPlayerDirection(Player player)
    {
    
    	BlockFace dir = null;
        
    	float y = player.getLocation().getYaw();
        
    	if( y < 0 ){y += 360;}
        
    	y %= 360;
        
    	int i = (int)((y+8) / 22.5);
        
    	if(i == 0){dir = BlockFace.WEST;}
        else if(i == 1){dir = BlockFace.WEST_NORTH_WEST;}
        else if(i == 2){dir = BlockFace.NORTH_WEST;}
        else if(i == 3){dir = BlockFace.NORTH_NORTH_WEST;}
        else if(i == 4){dir = BlockFace.NORTH;}
        else if(i == 5){dir = BlockFace.NORTH_NORTH_EAST;}
        else if(i == 6){dir = BlockFace.NORTH_EAST;}
        else if(i == 7){dir = BlockFace.EAST_NORTH_EAST;}
        else if(i == 8){dir = BlockFace.EAST;}
        else if(i == 9){dir = BlockFace.EAST_SOUTH_EAST;}
        else if(i == 10){dir = BlockFace.SOUTH_EAST;}
        else if(i == 11){dir = BlockFace.SOUTH_SOUTH_EAST;}
        else if(i == 12){dir = BlockFace.SOUTH;}
        else if(i == 13){dir = BlockFace.SOUTH_SOUTH_WEST;}
        else if(i == 14){dir = BlockFace.SOUTH_WEST;}
        else if(i == 15){dir = BlockFace.WEST_SOUTH_WEST;}
        else {dir = BlockFace.WEST;}
        
    	return dir;
   
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
				   				Material.SNOW, Material.STATIONARY_WATER, Material.STONE_BUTTON, Material.SUGAR_CANE_BLOCK, Material.THIN_GLASS, Material.TNT, Material.TORCH, Material.TRAP_DOOR, Material.VINE, Material.WALL_SIGN,
				   				Material.WATER, Material.WEB, Material.WHEAT, Material.WOODEN_DOOR, Material.WORKBENCH, Material.YELLOW_FLOWER};
		
		return Arrays.asList(materials);
		
	}
		
}
