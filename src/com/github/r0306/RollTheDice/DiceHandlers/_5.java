package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.github.r0306.RollTheDice.Disguise.Disguise;

public class _5 extends Arena implements Listener
{

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 5))
		{
			
			if (getLivingEntities().contains(event.getRightClicked().getType()))
			{
			
				Disguise disguise = new Disguise();
				int id = disguise.getNextAvailableID();
				Disguise d = new Disguise(id, Disguise.MobType.fromString(event.getRightClicked().getType().getName()));
				disguise.disguisePlayer(player, d);
				player.sendMessage(arg0);
			
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
	
}
