package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Util;

public class Arena implements Colors
{
	
	public static List<Player> inMatch = new ArrayList<Player>();
	public boolean isStarted;
	public static HashMap<Player, Integer> dice = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
	public static HashMap<Player, String> disguise = new HashMap<Player, String>();
	public static HashMap<Entity, Player> damageDB = new HashMap<Entity, Player>();
	
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
		
		return dice.containsKey(player) && dice.get(player) == side;
		
	}
	
	public void broadcast(String message, Player exempt)
	{
		
		for (Player p : inMatch)
		{
			
			if (p != exempt)
			{
			
				p.sendMessage(gold + pluginName + message);
			
			}
				
		}
		
	}
	
}
