package com.github.r0306.RollTheDice;

import org.bukkit.plugin.java.JavaPlugin;

public class RollTheDice extends JavaPlugin implements Colors
{
	
	public static String version = "";
	public static String website = "";
	
	public void onEnable()
	{
		
		version = getDescription().getVersion();
		website = getDescription().getWebsite();
		System.out.println("RollTheDice version [" + getDescription().getVersion() + "] loaded.");
		
	}
	
	public void onDisable()
	{
		
		System.out.println("RollTheDice version [" + getDescription().getVersion() + "] unloaded.");
		
	}

}
