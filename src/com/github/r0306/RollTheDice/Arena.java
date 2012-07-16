package com.github.r0306.RollTheDice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arena 
{
	
	public static List<String> inMatch = new ArrayList<String>();
	public boolean isStarted;
	public static HashMap<String, Integer> dice = new HashMap<String, Integer>();
	public static HashMap<String, Integer> kills = new HashMap<String, Integer>();
	public static HashMap<String, String> disguise = new HashMap<String, String>();

	public void clearAllFields()
	{
		
		inMatch.clear();
		isStarted = false;
		dice.clear();
		kills.clear();
		
	}
	
}
