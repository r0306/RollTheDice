package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class _32 extends Arena implements Listener
{
	
	static ArrayList<String> godModed = new ArrayList<String>();
	
	static long DELAY_TICKS_GOD = 100L;
	static long DELAY_TICKS_COOLDOWN = 200L;
	
	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
		
			Player player = (Player) event.getEntity();
			
			if (isIn(player, 32))
			{
				
				if (isGoded(player))
				{
					
					event.setCancelled(true);
					
				}
				
			}
			
		}
		
	}

	public static void scheduleGodModeDelay(Player player)
	{
		
		setGodMode(player, true);
		
		DelayCoolDown.scheduleDelayedCoolDownEndGodMode(player, DELAY_TICKS_GOD);
		
	}
	
	public static void scheduleGodModeCoolDown(Player player)
	{
		
		setGodMode(player, false);
		
		DelayCoolDown.scheduleDelayedCoolDownGodMode(player, DELAY_TICKS_COOLDOWN);
		
	}
	
	public static void setGodMode(Player player, boolean on)
	{
		
		if (on)
		{	
			
			godModed.add(player.getName());
		
		}
		else
		{
			
			if (godModed.contains(player.getName()))
			{
				
				godModed.remove(player.getName());
				
			}
			
		}
			
	}
	
	public boolean isGoded(Player player)
	{
		
		return godModed.contains(player.getName());
		
	}
		
}
