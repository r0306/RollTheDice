package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _21 extends Arena implements Listener
{

	static HashMap<String, Integer> ids = new HashMap<String, Integer>();
	static ArrayList<String> isDashing = new ArrayList<String>();
	final long DELAY_TICKS = 100L;
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 21))
		{
			
			if (event.getAction() == Action.LEFT_CLICK_BLOCK)
			{
			
				if (player.getExp() == 1F)
				{
					
					schedulePreDash(player);
					player.setExp(0F);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onCollide(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 21))
		{
			
			if (isDashing(player))
			{
				
				for (Entity entity : player.getNearbyEntities(2, 2, 2))
				{
					
					if (entity instanceof LivingEntity)
					{
					
						LivingEntity e = (LivingEntity) entity;
						e.damage(5, player);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public void schedulePreDash(final Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
			   
			   int counter = 0;
			
			   public void run()
			   {
			    			
				   Random random = new Random();
				   
				   player.getLocation().getWorld().playEffect(player.getLocation(), Effect.SMOKE, random.nextInt(9), 50);
				   
				   counter ++;
				   
				   if (counter == 10)
				   {
					   
					   Bukkit.getScheduler().cancelTask(ids.get(player.getName()));
					   scheduleRepeatingDash(player);
					   setDashing(player, true);   
					   
				   }
			   
			   }
			
		}, 5L, 5L);
		
		ids.put(player.getName(), id);
		
	}
	
	public void scheduleRepeatingDash(final Player player)
	{
	
		final Vector direction = getConstantDirection(player);
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
			   
			   int counter = 0;
			   Location location = player.getLocation();
			
			   public void run()
			   {
			    								
					player.setVelocity(direction);
					
					player.getLocation().getWorld().createExplosion(location, 0);
					
					counter ++;
					
					
					if (counter == 50)
					{
						
						Bukkit.getScheduler().cancelTask(ids.get(player.getName()));
						setDashing(player, false);
						DelayCoolDown.scheduleDelayedCoolDown(player, DELAY_TICKS);
						
					}
					
					if (counter % 5 == 0) location = player.getLocation();
					
			   }
			
		}, 1L, 1L);
		
		ids.put(player.getName(), id);
		
	}
		
	public Vector getConstantDirection(Player player)
	{
		
		Vector direction = player.getLocation().getDirection().normalize();
		
		direction.setX(direction.getX() * 3D);
		direction.setY(0D);
		direction.setZ(direction.getZ() * 3D);

		return direction;
		
	}
	
	public boolean isDashing(Player player)
	{
		
		return isDashing.contains(player.getName());
		
	}
	
	public void setDashing(Player player, boolean dashing)
	{
		
		if (dashing)
		{
			
			isDashing.add(player.getName());
			
		}
		else if (isDashing.contains(player.getName()))
		{
			
			isDashing.remove(player.getName());
			
		}
		
	}
		
}
