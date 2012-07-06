package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Util.Plugin;

public class MovementHandlers 
{

	static HashMap<Player, Integer> taskIds = new HashMap<Player, Integer>();
	static HashMap<Player, Location> locations = new HashMap<Player, Location>();
			
	public static void schedulePotionCheck(final Player player, final PotionEffectType effect)
	{
		MovementHandlers.registerMovement(player);
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
			   			
			   public void run()
			   {

				   if (!player.hasPotionEffect(effect))
				   {
					   
					   player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500, 1));
					   //cancelTask(getTaskId(player));
					   //removePlayer(player);
					   
				   }
				   
				   if (checkLocations(player))
				   {

					   player.removePotionEffect(effect);
					   player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 0, 0));
					   cancelTask(getTaskId(player));
					   removePlayer(player);
					   
				   }
				   else
				   {
					   
						MovementHandlers.registerMovement(player);
				   }
			   
			   }
			
		}, 20L,20L);
		
		taskIds.put(player, id);
		
	}
	
	public static void cancelTask(int id)
	{
		
		Bukkit.getScheduler().cancelTask(id);
		
	}
	
	public static int getTaskId(Player player)
	{
		
		return taskIds.get(player);
		
	}
	
	public static boolean isScheduled(Player player)
	{
		
		return taskIds.containsKey(player);
		
	}
	
	public static boolean checkVelocity(Player player)
	{
		
		return (player.getVelocity().length() < 0.079D);
		
	}
	
	public static void removePlayer(Player player)
	{
		
		taskIds.remove(player);
		
	}
	
	public static void registerMovement(Player player)
	{
		
		locations.put(player, player.getLocation());
	
	}
	
	public static boolean checkLocations(Player player)
	{
		
		double x = locations.get(player).getX();
		double y = locations.get(player).getY();
		double z = locations.get(player).getZ();
		double X = player.getLocation().getX();
		double Y = player.getLocation().getY();
		double Z = player.getLocation().getZ();
		
		return (x == X && y == Y && z == Z);
		
	}
	
}