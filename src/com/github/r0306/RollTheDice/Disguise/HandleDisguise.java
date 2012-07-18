package com.github.r0306.RollTheDice.Disguise;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Disguise.Disguise.MobType;

public class HandleDisguise 
{

	public static void disguisePlayerAsEntity(Player player, EntityType entityType)
	{
		
		if (RollTheDice.disguiseAPI != null)
		{
		
			if (RollTheDice.disguiseAPI.isDisguised(player))
			{
				
				RollTheDice.disguiseAPI.undisguisePlayer(player);
				
			}
			
			pgDev.bukkit.DisguiseCraft.Disguise disguise = new pgDev.bukkit.DisguiseCraft.Disguise(RollTheDice.disguiseAPI.newEntityID(), pgDev.bukkit.DisguiseCraft.Disguise.MobType.fromString(entityType.getName()));
			
			RollTheDice.disguiseAPI.disguisePlayer(player, disguise);
			
		}
		else
		{
			
			Disguise disguise = new Disguise();
			
			if (Disguise.isDisguised(player))
			{
				
				disguise.unDisguisePlayer(player);
				
			}

			int id = disguise.getNextAvailableID();
			
			MobType mob = Disguise.MobType.fromString(entityType.getName());
			
			Disguise d = new Disguise(id, mob);
			
			disguise.disguisePlayer(player, d);
			
		}
		
	}
	
	public static void unDisguisePlayer(Player player)
	{
		
		if (RollTheDice.disguiseAPI != null)
		{
			
			if (RollTheDice.disguiseAPI.isDisguised(player))
			{
				
				RollTheDice.disguiseAPI.undisguisePlayer(player);
				
			}
			
		}
		else if (Disguise.isDisguised(player))
		{
			
			Disguise disguise = new Disguise();
			
			disguise.unDisguisePlayer(player);
			
		}
		
	}
	
	public static List<EntityType> getLivingEntities()
	{
		
		EntityType[] entities = {EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COW, EntityType.CREEPER,
				                 EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.GHAST, EntityType.GIANT, EntityType.IRON_GOLEM,
				                 EntityType.MAGMA_CUBE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG, EntityType.PIG_ZOMBIE,
				                 EntityType.PLAYER, EntityType.SHEEP, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SNOWMAN,
				                 EntityType.SPIDER, EntityType.SQUID, EntityType.VILLAGER, EntityType.WOLF, EntityType.ZOMBIE};
		
		return Arrays.asList(entities);
		
	}
	
}
