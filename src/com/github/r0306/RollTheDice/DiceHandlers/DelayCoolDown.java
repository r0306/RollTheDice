package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Util.Util;

public class DelayCoolDown 
{
	
	private RollTheDice plugin;
	
	public DelayCoolDown()
	{
	
	
	
	}
	
	public DelayCoolDown(RollTheDice plugin)
	{
		
		this.plugin = plugin;
		
	}
		
	private HashMap<Player, Integer> ids = new HashMap<Player, Integer>();

	public void scheduleDelayedCoolDown(Player player, final Long ticks)
	{
		
		final Player p = player;
		final Float exp = Util.delayExp(ticks);
		
		int id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
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
					   plugin.getServer().getScheduler().cancelTask(ids.get(p));
					   ids.remove(p);
					   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p, id);
		
	}
	
}
