package com.github.r0306.RollTheDice;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Executor extends Arena implements CommandExecutor, Colors
{
	
	private RollTheDice plugin;
	
	public Executor(RollTheDice plugin)
	{
		
		this.plugin = plugin;
		
	}
	
	private int delay;
	private int delaySeconds;
	private int id;
	
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
				else if (args[0].equalsIgnoreCase("join"))
				{
					
					if (checkPerms(player, "rtd.play"))
					{
						
						joinMatch(player);
						
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
	
	public void joinMatch(Player player)
	{
		
		if (!inMatch.contains(player))
		{
			
			if (!isStarted)
			{
				
				inMatch.add(player);
				player.sendMessage(gold + pluginName + daqua + "You have joined the match.");
				
				if(calculateRemaining() != 0)
				{
					
					player.sendMessage(gold + pluginName + daqua + calculateRemaining() + " more players are needed to start the match.");
				
				}
				else
				{
					
					startCountDown();
					
				}
				
			}
			else
			{
				
				player.sendMessage(gold + pluginName + red + "Match already started. Please wait until match ends.");
				
			}
			
		}
		else
		{
			
			player.sendMessage(gold + pluginName + red + "You are already in the match!");
			
		}
		
	}
	
	public Integer calculateRemaining()
	{
		
		int min = plugin.getConfig().getInt("RTD.Minimum");
		int size = inMatch.size();
		
		return min - size;
		
	}
	
	public void startCountDown()
	{
		
		delay = plugin.getConfig().getInt("RTD.Delay") * 20;
		delaySeconds = plugin.getConfig().getInt("RTD.Delay");
		Bukkit.broadcastMessage(gold + pluginName + daqua + "Match will start in " + delaySeconds + " seconds. Type " + purple + "/rta join" + daqua + " to join!");
		delaySeconds--;
		id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

		   public void run() 
		   {
			 
			   if (delaySeconds > 0)
			   {
				   
				   for (Player player : inMatch)
				   {
					   
					   player.sendMessage(gold + pluginName + dgreen + "Match will begin in " + delay + " seconds.");
					   delaySeconds--;
					   
				   }
			   
			   }
			   else
			   {
				   
				   isStarted = true;
				   for (Player player : inMatch)
				   {
					   
					   player.sendMessage(gold + pluginName + aqua + "Match has started.");
					   startMatch(player);
					   
				   }
				   plugin.getServer().getScheduler().cancelTask(id);
				   
			   }
			   
		   }
		}, delay, delay);
		
	}
	
	public void startMatch(Player player)
	{
		
		
		
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
