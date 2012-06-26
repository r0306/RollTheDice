package com.github.r0306.RollTheDice;

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

}
