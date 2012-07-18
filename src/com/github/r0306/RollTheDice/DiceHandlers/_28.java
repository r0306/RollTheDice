package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.r0306.RollTheDice.Util.Colors;

public class _28 extends Arena implements Listener, Colors
{

	static HashMap<String, Integer> powerLevels = new HashMap<String, Integer>();
	static ArrayList<String> maxedOut = new ArrayList<String>();
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		if (event.getEntity().getKiller() != null)
		{
			
			Player player = event.getEntity().getKiller();
			
			if (isIn(player, 28))
			{
				
				increasePower(player);
				
				int powerLevel = getPowerLevel(player);
				
				if (!maxedOut.contains(player.getName()))
				{
				
					if (powerLevel == 10)
					{
						
						player.sendMessage(gold + pluginName + daqua + "Your power level has been maxed out at " + yellow + calculatePercentage(powerLevel) + "%" + daqua + ".");
						maxedOut.add(player.getName());
						
					}
					else
					{
					
						player.sendMessage(gold + pluginName + daqua + "Your power level is now at " + yellow + calculatePercentage(powerLevel) + "%" + daqua + ".");
					
					}
					
				}
					
			}
			
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		
		if (event.getDamager() instanceof Player)
		{
			
			Player player = (Player) event.getDamager();
			
			if (isIn(player, 28))
			{
				
				event.setDamage(getResultantDamage(event.getDamage(), getPowerLevel(player)));
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 28))
		{
			
			if (isDay(player) && event.getTo().getBlock().getLightFromSky() > 10)
			{
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 500, 2), true);
				player.setFireTicks(500);
				
			}
			else
			{
				
				if (player.hasPotionEffect(PotionEffectType.WEAKNESS))
				{
					
					player.removePotionEffect(PotionEffectType.WEAKNESS);
					player.setFireTicks(0);
					
				}
				
			}
		
		}
		
	}
	
	public boolean isDay(Player player)
	{
		
		long time = player.getWorld().getTime();
		
		return time < 12300 || time > 23850;
		
	}
	
	public void increasePower(Player player)
	{
		
		if (!powerLevels.containsKey(player.getName()))
		{
			
			powerLevels.put(player.getName(), 1);
			
		}
		else if (getPowerLevel(player) < 10)
		{
			
			powerLevels.put(player.getName(), powerLevels.get(player.getName()) + 1);
		
		}
		
	}
	
	public int getPowerLevel(Player player)
	{
		
		if (powerLevels.containsKey(player.getName()))
		{
			
			return powerLevels.get(player.getName());
			
		}
		
		return 0;
		
	}
	
	public int getResultantDamage(int damage, int power)
	{
		
		return damage + power;
		
	}
	
	public int calculatePercentage(int power)
	{
		
		return 100 + power * 10;
		
	}
	
}
