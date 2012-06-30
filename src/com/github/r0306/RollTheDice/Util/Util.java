package com.github.r0306.RollTheDice.Util;

import java.io.InputStream;

import org.bukkit.ChatColor;

public class Util
{
	
	public static Integer toInt(String string)
	{
		
		return Integer.parseInt(string);
		
	}
	
	public  String toString(Integer i)
	{

		return "_" + Integer.toString(i);
		
	}
		
	public static String colorizeText(String string)
	{
      
		string = string.replaceAll("&0", ChatColor.BLACK+"");
        string = string.replaceAll("&1", ChatColor.DARK_BLUE+"");
        string = string.replaceAll("&2", ChatColor.DARK_GREEN+"");
        string = string.replaceAll("&3", ChatColor.DARK_AQUA+"");
        string = string.replaceAll("&4", ChatColor.DARK_RED+"");
        string = string.replaceAll("&5", ChatColor.DARK_PURPLE+"");
        string = string.replaceAll("&6", ChatColor.GOLD+"");
        string = string.replaceAll("&7", ChatColor.GRAY+"");
        string = string.replaceAll("&8", ChatColor.DARK_GRAY+"");
        string = string.replaceAll("&9", ChatColor.BLUE+"");
        string = string.replaceAll("&a", ChatColor.GREEN+"");
        string = string.replaceAll("&b", ChatColor.AQUA+"");
        string = string.replaceAll("&c", ChatColor.RED+"");
        string = string.replaceAll("&d", ChatColor.LIGHT_PURPLE+"");
        string = string.replaceAll("&e", ChatColor.YELLOW+"");
        string = string.replaceAll("&f", ChatColor.WHITE+"");
        
        return string;
    
	}
	
}
