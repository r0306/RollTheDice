package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Util.Plugin;
import com.github.r0306.RollTheDice.Util.Util;

public class DelayCoolDown 
{
	
	public DelayCoolDown()
	{
	
	
	
	}
		
	private static HashMap<Player, Integer> ids = new HashMap<Player, Integer>();

	public static void scheduleDelayedCoolDown(Player player, final Long ticks)
	{
		
		final Player p = player;
		final Float exp = Util.delayExp(ticks);
		
		p.setExp(0F);
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
			   
			   int counter = 0;
			
			   public void run()
			   {
			    

				   if (counter < ticks)
				   {   

					   p.setExp(p.getExp() + exp);
					   counter ++;
					   
				   }
				   else
				   {
					 
					   p.setExp(1);
					   Bukkit.getServer().getScheduler().cancelTask(ids.get(p));
					   ids.remove(p);
					   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p, id);
		
	}
	
}
