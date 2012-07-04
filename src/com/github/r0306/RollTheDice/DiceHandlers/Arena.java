package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

public class Arena 
{
	
	public static List<Player> inMatch = new ArrayList<Player>();
	public boolean isStarted;
	public static HashMap<Player, Integer> dice = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
	public static HashMap<Player, String> disguise = new HashMap<Player, String>();

	public void clearAllFields()
	{
		
		inMatch.clear();
		isStarted = false;
		dice.clear();
		kills.clear();
		
	}
	
	public boolean isIn(Player player, Integer side)
	{
		
		return dice.containsKey(player) && dice.get(player) == side;
		
	}
	
}
