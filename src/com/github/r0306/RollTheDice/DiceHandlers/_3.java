package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

public class _3 extends Arena implements Listener
{

	final long DELAY_TICKS = 80; 
	private HashMap<Entity, Player> casters = new HashMap<Entity, Player>();
		
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
	
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) { 
			
			if (isIn(player, 3))
			{
				
				if (player.getItemInHand().getType() == Material.BLAZE_ROD)
				{
					
					if (player.getExp() == 1F)
					{
						
						magicShockWave(player);
						DelayCoolDown.scheduleDelayedCoolDown(player, DELAY_TICKS);
					
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		
		if (event.getCause() == DamageCause.PROJECTILE)
		{
			
			if (checkEntity(event.getDamager()))
			{
				
				event.setDamage(8);
				damageDB.put(event.getEntity(), casters.get(event.getDamager()));
		
			}
			
		}
		
	}
	
	public boolean checkEntity(Entity damager)
	{
		
		for (Entity e : casters.keySet())
		{
			
			if (e == damager)
			{
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
		
	public void magicShockWave(Player player)
	{

		Entity entity = player.launchProjectile(Snowball.class);
		casters.put(entity, player);
		player.setExp(0F);
	
	}
		
}
