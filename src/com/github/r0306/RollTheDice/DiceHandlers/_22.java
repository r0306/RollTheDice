package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class _22 extends Arena implements Listener
{

	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 22))
		{
			
			BlockFace direction = getPlayerDirection(player);
			Location location = player.getLocation().getBlock().getRelative(direction.getOppositeFace()).getLocation();
			
			player.getWorld().playEffect(location, Effect.SMOKE, blockFaceToData(direction), 50);
			
		}
		
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
    
    public int blockFaceToData(BlockFace face)
    {
    	
    	if (face == BlockFace.SOUTH_EAST || face == BlockFace.SOUTH_SOUTH_EAST) return 0;
    	else if (face == BlockFace.SOUTH) return 1;
    	else if (face == BlockFace.SOUTH_WEST || face == BlockFace.SOUTH_SOUTH_WEST) return 2;
    	else if (face == BlockFace.EAST) return 3;
    	else if (face == BlockFace.UP || face == BlockFace.SELF || face == BlockFace.DOWN) return 4;
    	else if (face == BlockFace.WEST || face == BlockFace.WEST_NORTH_WEST || face == BlockFace.WEST_SOUTH_WEST) return 5;
    	else if (face == BlockFace.NORTH_EAST || face == BlockFace.NORTH_NORTH_EAST) return 6;
    	else if (face == BlockFace.NORTH) return 7;
    	else if (face == BlockFace.NORTH_WEST || face == BlockFace.NORTH_NORTH_WEST) return 8;
    	else return 4;
    
    }
	
}
