package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class _1 extends Arena implements Listener 
{

	@EventHandler
	public void fenceDefense(EntityDamageByEntityEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (isIn(player, 1))
			{
				
				if (player.getItemInHand().getType() == Material.FENCE)
				{
					
					if (event.getCause() == DamageCause.ENTITY_ATTACK || checkProjectile(event.getDamager(), event.getCause()))
					{
						
						event.setDamage(event.getDamage() - (event.getDamage() / 3));

					}
					
				}
				
			}
					
		}
		
	}
	
	public boolean checkProjectile(Entity entity, DamageCause dmg)
	{
		
		if (dmg == DamageCause.PROJECTILE)
		{
			
			if (entity.getType() == EntityType.ARROW)
			{
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
}
