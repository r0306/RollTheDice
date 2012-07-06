package com.github.r0306.RollTheDice.Util;

import com.github.r0306.RollTheDice.RollTheDice;

public class Plugin 
{
	
	private static RollTheDice plugin;
	
	public Plugin(RollTheDice plugin)
	{
		
		Plugin.plugin = plugin;
		
	}
	
	public static RollTheDice getPlugin()
	{
		
		return plugin;
		
	}

}
