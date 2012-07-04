package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Util.Util;

public class _3 extends Arena implements Listener
{

	final long DELAY_TICKS = 60; 
	private HashMap<Player, Integer> ids = new HashMap<Player, Integer>();
	private HashMap<Entity, Player> casters = new HashMap<Entity, Player>();
	
	private RollTheDice plugin;
	public _3(RollTheDice plugin)
	{
		
		this.plugin = plugin;
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 3))
		{
			
			if (player.getItemInHand().getType() == Material.BLAZE_ROD)
			{
				
				if (!ids.containsKey(player))
				{
					
					magicShockWave(player);
					scheduleDelayedCoolDownFire(player);
				
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
		
		Entity entity = player.getWorld().spawn(player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch()), Snowball.class);
		casters.put(entity, player);
	
	}
	
	public void scheduleDelayedCoolDownFire(Player player)
	{
		
		final Player p = player;
		final Double exp = Util.getExpToAdd(player.getLevel(), DELAY_TICKS);
		final int level = p.getLevel();
		
		int id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
		{

			   public void run()
			   {
			    
				   if (p.getExp() < Util.calculateTotalLevelExp(level))
				   {   
					 
					   p.setExp((float) (p.getExp() + 0.01));
				   
				   }
				   else
				   {
					   
					   plugin.getServer().getScheduler().cancelTask(ids.get(p));
					   ids.remove(p);
					   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p, id);
		
	}
	
}
