package com.github.r0306.RollTheDice.Util;

import java.io.InputStream;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Util
{
	
	public static Integer toInt(String string)
	{
		
		return Integer.parseInt(string);
		
	}
	
	public static  String toString(Integer i)
	{

		return Integer.toString(i);
		
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
	
	public static void setArmorContents(Player player, ItemStack[] armor)
	{
		System.out.println(armor[0].getType().toString());
		for (ItemStack i : armor)
		{
			
			if (checkArmor(i).equalsIgnoreCase("Head"))
				player.getInventory().setHelmet(i);
			else if (checkArmor(i).equalsIgnoreCase("Body"))
				player.getInventory().setChestplate(i);
			else if (checkArmor(i).equalsIgnoreCase("Legs"))
				player.getInventory().setLeggings(i);
			else if (checkArmor(i).equalsIgnoreCase("Foot"))
				player.getInventory().setBoots(i);
			
		}
		
	}
	
	public static String checkArmor(ItemStack i)
	{
		
		if (i.getType().toString().contains("Helmet"))
			return "Head";
		else if (i.getType().toString().contains("(?!)ChestPlate"))
			return "Body";
		else if (i.getType().toString().contains("(?!)Leggings"))
			return "Legs";
		else if (i.getType().toString().contains("(?!)Boots"))
			return "Foot";
		
		
		return null;
			
	}
	
}
