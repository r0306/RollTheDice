package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _6 extends Arena
{
	
	private static HashMap<Player, Integer> ids = new HashMap<Player, Integer>();

	public void clearAllPlayers()
	{
		
		for (Player p : dice.keySet())
		{
			
			if (dice.get(p) == 6)
			{
				
				unblindPlayer(p);
				
			}
			
		}
		
	}
	
	public void unblindPlayer(Player player)
	{
		
		if (ids.containsKey(player))
		{
			
			Bukkit.getScheduler().cancelTask(ids.get(player));
		
		}
	
	}
	
	public static void blindPlayer(final Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{
			
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 2), true);
				
			}
			
		}, 0L, 100L);
		
		ids.put(player, id);
		
	}
	
}
