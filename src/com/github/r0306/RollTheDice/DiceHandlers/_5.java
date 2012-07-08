package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Disguise.Disguise;
import com.github.r0306.RollTheDice.Disguise.Disguise.MobType;
import com.github.r0306.RollTheDice.Util.Colors;

public class _5 extends Arena implements Listener, Colors
{

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 5))
		{
			
			if (getLivingEntities().contains(event.getRightClicked().getType()))
			{
				
				if (RollTheDice.disguiseAPI == null)
				{
			
					Disguise disguise = new Disguise();
					
					if (Disguise.isDisguised(player))
					{
						
						disguise.unDisguisePlayer(player);
						
					}

					int id = disguise.getNextAvailableID();
					MobType mob = Disguise.MobType.fromString(event.getRightClicked().getType().getName());
					Disguise d = new Disguise(id, mob);
					disguise.disguisePlayer(player, d);
				
				}
				else
				{
					
					if (RollTheDice.disguiseAPI.isDisguised(player))
					{
						
						RollTheDice.disguiseAPI.undisguisePlayer(player);
						
					}
					
					RollTheDice.disguiseAPI.disguisePlayer(player, new pgDev.bukkit.DisguiseCraft.Disguise(RollTheDice.disguiseAPI.newEntityID(), pgDev.bukkit.DisguiseCraft.Disguise.MobType.fromString(event.getRightClicked().getType().getName())));
					
				
				}
				
				player.sendMessage(gold + pluginName + daqua + "You have disguised as " + yellow + event.getRightClicked().getType().getName() + daqua + ".");
			
			}
	
		}
		
	}
	
	@EventHandler
	public void onInteractAir(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 5))
		{
			
			if (event.getAction() == Action.RIGHT_CLICK_AIR)
			{
				
				if (player.getItemInHand().getType() == Material.AIR)
				{
					
					if (RollTheDice.disguiseAPI == null)
					{
				
						Disguise disguise = new Disguise();
						
						if (Disguise.isDisguised(player))
						{
							
							disguise.unDisguisePlayer(player);		
							player.sendMessage(gold + pluginName + daqua + "You are now undisguised.");
							
						}
					
					}
					else
					{
						
						if (RollTheDice.disguiseAPI.isDisguised(player))
						{
							
							RollTheDice.disguiseAPI.undisguisePlayer(player);
							player.sendMessage(gold + pluginName + daqua + "You are now undisguised.");
							
						}
											
					}
					
				}
				
			}
			
		}
		
	}
	
	public List<EntityType> getLivingEntities()
	{
		
		EntityType[] entities = {EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COW, EntityType.CREEPER,
				                 EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.GHAST, EntityType.GIANT, EntityType.IRON_GOLEM,
				                 EntityType.MAGMA_CUBE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG, EntityType.PIG_ZOMBIE,
				                 EntityType.PLAYER, EntityType.SHEEP, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SNOWMAN,
				                 EntityType.SPIDER, EntityType.SQUID, EntityType.VILLAGER, EntityType.WOLF, EntityType.ZOMBIE};
		
		return Arrays.asList(entities);
		
	}
		
		
}
	

