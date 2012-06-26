package com.github.r0306.RollTheDice;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Executor implements CommandExecutor, Colors
{
	
	private RollTheDice plugin;
	
	public Executor(RollTheDice plugin)
	{
		
		this.plugin = plugin;
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		
		Player player = null;
		if (sender instanceof Player)
		{
			player = (Player) sender;
		}
		else
		{
			sender.sendMessage(red + "You must be a player to use this command!");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("rtd"))
		{
			
			if (args.length == 0)
			{
				
				mainPage(player);
				
			}
			else if (args.length == 1)
			{
				
				if (args[0].equalsIgnoreCase("help"))
				{
					
					helpPage(player);
				
				}
				else if (args[0].equalsIgnoreCase("enable"))
				{
					
					if (checkPerms(player, "rtd.configure"))
					{
						
						enableRTD(player);
					
					}
					
				}
				else if (args[0].equalsIgnoreCase("disable"))
				{
					
					if (checkPerms(player, "rtd.configure"))
					{
						
						disableRTD(player);
						
					}
					
				}
				
			}
			else if (args.length == 2)
			{
				
				if (args[0].equalsIgnoreCase("setworld"))
				{
					
					if (checkPerms(player, "rtd.configure"))
					{
						
						setWorld(player, args[1]);
						
					}
					
				}
	
			}
			
		}
		
		return true;
	}
	
	public void mainPage(Player player)
	{
		
		player.sendMessage("This server is running RollTheDice version" + version + ".");
		player.sendMessage(gray + "For help, type " + dgray + "/rta help" + gray + ".");
		
	}
	
	public void helpPage(Player player)
	{
		
		player.sendMessage(daqua + "Visit the wiki at: " + yellow + website + daqua + " for in-depth information.");
		player.sendMessage(daqua + "List of commands:");
		player.sendMessage(daqua + "------------------");
		player.sendMessage(dgreen + "/dta" + white + " - " + dgreen + "Displays basic plugin information.");
		player.sendMessage(dgreen + "/dta help" + white + " - " + dgreen + "Displays help page.");
		player.sendMessage(dgreen + "/dta join" + white + " - " + dgreen + "Join the match if it hasn't started yet.");
		
		
	}
	
	public void setWorld(Player player, String world)
	{
		
		if (Bukkit.getWorld(world) != null)
		{
			
			plugin.getConfig().set("RTD.World", world);
			plugin.saveConfig();
			player.sendMessage(gold + pluginName + dgreen + "Default world for match set to: " + yellow + world + dgreen + ".");
			player.sendMessage(gold + pluginName + dgreen + "If a match is currently ongoing, changes will take place upon next match");
			
		}
		else
		{
			
			player.sendMessage("gold" + pluginName + red + "World does not exist. Check your spelling and try again.");
			
		}
		
	}
	
	public void disableRTD(Player player)
	{
		
		if (plugin.getConfig().getBoolean("RTD.Enabled"))
		{
			
			plugin.getConfig().set("RTD.Enabled", false);
			plugin.saveConfig();
			player.sendMessage(gold + pluginName + dgreen + "RTD matches are now disabled.");
			player.sendMessage(gold + pluginName + dgreen +  "If a match is ongoing, the changes will take effect after the match ends.");
			
		}
		else
		{
			
			player.sendMessage(gold + pluginName + red + "RTD is already disabled!");
			
		}
		
	}
	
	public void enableRTD(Player player)
	{
		
		if (!plugin.getConfig().getBoolean("RTD.Enabled"))
		{
			
			plugin.getConfig().set("RTD.Enabled", true);
			plugin.saveConfig();
			player.sendMessage(gold + pluginName + dgreen + "RTD matches are now enabled.");			
			
		}
		
	}
	
	public static boolean checkPerms(Player player, String permission)
	{
		
		if (!player.hasPermission(permission))
		{
			
			player.sendMessage(red + "You do not have permission!");
		}
		
		return player.hasPermission(permission);
		
	}

}
