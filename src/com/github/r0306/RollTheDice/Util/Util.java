package com.github.r0306.RollTheDice.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.RollTheDice.Executor;

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
		
		String[] fullName = i.getType().toString().split("_");
		String typeName = fullName[1];
		
		if (typeName.equalsIgnoreCase("Helmet"))
			return "Head";
		else if (typeName.equalsIgnoreCase("ChestPlate"))
			return "Body";
		else if (typeName.equalsIgnoreCase("Leggings"))
			return "Legs";
		else if (typeName.equalsIgnoreCase("Boots"))
			return "Foot";
		
		
		return null;
			
	}
	
	public static Float calculateTotalLevelExp(Integer level)
	{
		
		return (3.5F * level) + 6.75F;
		
	}
	
	public static Float getExpToAdd(Integer level, Long ticks)
	{
		
		return calculateTotalLevelExp(level) / ticks;
		
	}
	
	public static Float delayExp(Long ticks)
	{
		
		return (float) (100F / (float) ticks) / 100F;
		
	}
	
	public static void restoreEverything(Player player)
	{
		
		Executor e = new Executor(null);
		e.restoreInventory(player);
		e.restoreExperience(player);
		
	}
	
}
