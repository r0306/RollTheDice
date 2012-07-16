package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.r0306.RollTheDice.Util.Colors;

public class Arena implements Colors
{
	
	public static List<String> inMatch = new ArrayList<String>();
	public boolean isStarted;
	public static HashMap<String, Integer> dice = new HashMap<String, Integer>();
	public static HashMap<String, Integer> kills = new HashMap<String, Integer>();
	public static HashMap<String, String> disguise = new HashMap<String, String>();
	public static HashMap<Entity, String> damageDB = new HashMap<Entity, String>();
	
	public void clearAllFields()
	{
		
		inMatch.clear();
		isStarted = false;
		dice.clear();
		kills.clear();
		disguise.clear();
		damageDB.clear();
		
	}
	
	public boolean isIn(Player player, Integer side)
	{
		
		return dice.containsKey(player.getName()) && dice.get(player.getName()) == side;
		
	}
	
	public void broadcast(String message, Player exempt)
	{
		
		for (String name : inMatch)
		{
			
			Player p = Bukkit.getPlayerExact(name);
			
			if (p != exempt)
			{
			
				p.sendMessage(gold + pluginName + message);
			
			}
				
		}
		
	}
	
	public void getHandlers(Player player, Integer side)
	{
		
		switch (side)
		{
	       
			case 1: break;
	        case 2: break;
	        case 3: break;
	        case 4: break;
	        case 5: break;
	        case 6: _6.blindPlayer(player);
	        		break;
	        case 7: break;
	        case 8: break;
	        case 9: player.setHealth(2);
	        		break;
	        case 10: break;
	        case 11: break;
	        case 12: player.setPlayerTime(80000L, true); 
	        	     break;
	        case 13: _13.schedulePlayerOrgasm(player);
	                 break;
	        case 14: _14.scheduleDelayedDamage(player);
	                 break;
	        case 18: _18.scheduleRepeatingUpdate(player);
	                 break;
	        case 19: _19.scheduleDelayedCAPTCHA(player);
	        		 break;
	        case 20: break;
	        case 21: break;
	        	
		}

	}
	
}
