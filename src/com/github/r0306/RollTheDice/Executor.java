package com.github.r0306.RollTheDice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
	
	private FileConfiguration playerInventories = null;
	private File playerInventoryFile = null;
	
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
				else if (args[0].equalsIgnoreCase("leave"))
				{
					
					if (checkPerms(player, "rtd.play"))
					{
						
						leaveMatch(player);
					
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
				else if (args[0].equalsIgnoreCase("setmin"))
				{
					
					if (checkPerms(player, "rtd.configure"))
					{
						
						setMin(player, args[1]);
						
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
				saveInventory(player);
				player.sendMessage(gold + pluginName + daqua + "You have joined the match.");
				
				if(calculateRemaining() >= 0)
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
	
	public void saveInventory(Player player)
	{
		
		getInventories().set("Inventories." + player.getName() + ".Main", player.getInventory().getContents());
		getInventories().set("Inventories." + player.getName() + ".Armor", player.getInventory().getArmorContents());
		saveInventories();
		player.getInventory().clear();
		player.sendMessage(gold + pluginName + dgreen + "Your inventory has been saved and will be restored after the match");
		
	}
	
	public void restoreInventory(Player player)
	{
		
		player.getInventory().clear();
		ItemStack[] main = (ItemStack[]) getInventories().get("Inventories." + player.getName() + ".Main");
		ItemStack[] armor = (ItemStack[]) getInventories().get("Inventories." + player.getName() + ".Armor");
		player.getInventory().setContents(main);
		player.getInventory().setArmorContents(armor);
		getInventories().set("Inventories." + player.getName(), null);
		saveInventories();
		player.sendMessage(gold + pluginName + dgreen + "Your inventory has been restored.");
		
	}
	
	public void saveExperience(Player player)
	{
		
		float exp = player.getExp();
		int level = player.getLevel();
		getInventories().set("Experience." + player.getName() + ".Level", level);
		getInventories().set("Experience." + player.getName() + ".Exp", exp);
		saveInventories();
	
	}
	
	public void loadExperience(Player player)
	{
		
		float exp = (float) getInventories().getDouble("Experience." + player.getName() + ".Exp");
		int level = getInventories().getInt("Experience." + player.getName() + ".Level");
		player.setLevel(level);
		player.setExp(exp);
		
	}
	
	public void reloadInventories()
	{
	
		if (playerInventoryFile == null)
		{
	    
			playerInventoryFile = new File(plugin.getDataFolder(), "PlayerInventories.yml");
	    
		}
	    
		playerInventories = YamlConfiguration.loadConfiguration(playerInventoryFile);
	 
	    InputStream defConfigStream = plugin.getResource("playerInventories.yml");
	    
	    if (defConfigStream != null)
	    {
	    
	    	YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        playerInventories.setDefaults(defConfig);
	   
	    }
	
	}
	
	public FileConfiguration getInventories()
	{
	
		if (playerInventoryFile == null)
		{
	    
			this.reloadInventories();
	    
		}
	    
		return playerInventories;
	
	}
	
	public void saveInventories() 
	{
		
	    if (playerInventories == null || playerInventoryFile == null)
	    {
	    	
	    	return;
	    
	    }
	    try
	    {
	     
	    	playerInventories.save(playerInventoryFile);
	    
	    } catch (IOException ex) {
	    
	    	plugin.getLogger().log(Level.SEVERE, "Could not save config to " + playerInventoryFile, ex);
	    
	    }

	}
	
	public void leaveMatch(Player player)
	{
		
		if (inMatch.contains(player))
		{
							
				player.sendMessage(gold + pluginName + aqua + "You have left the match.");
				inMatch.remove(player);
				
				for (Player p : inMatch)
				{
					
					p.sendMessage(dgreen + player.getName() + " has left the match.");
					
				}
				
		}
		else
		{
			
			player.sendMessage(gold + pluginName + red + "You were not in the match.");
		}
		
	}
	
	public void setMin(Player player, String min)
	{
		
		try 
		{
			
			int minimum = Integer.parseInt(min);
			plugin.getConfig().set("RTD.Minimum", minimum);
			plugin.saveConfig();
			player.sendMessage(gold + pluginName + dgreen + "Minimum successfully set.");
			
		} catch (NumberFormatException e) {
			
			player.sendMessage(gold + pluginName + red + "You must enter a valid number!");
		
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
