package com.github.r0306.RollTheDice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.RollTheDice.DiceHandlers.Dice;
import com.github.r0306.RollTheDice.Util.Colors;

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
	private int stopId;
	
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
				else if (args[0].equalsIgnoreCase("kills"))
				{
					
					getKills(player);
					
				}
				else if (args[0].equalsIgnoreCase("wins"))
				{
					
					getWins(player);
					
				}
				else if (args[0].equalsIgnoreCase("leaderboard"))
				{
					
					calculateLeaderBoard(player);
					
				}
				else if (args[0].equalsIgnoreCase("list"))
				{
					
					list(player);
					
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
				else if (args[0].equalsIgnoreCase("setdelay"))
				{
					
					if (checkPerms(player, "rtd.configure"))
					{
						
						setDelay(player, args[1]);
						
					}
					
				}
				else if (args[0].equalsIgnoreCase("settimelimit"))
				{
					
					if (checkPerms(player, "rtd.configure"))
					{
						
						setTimeLimit(player, args[1]);
						
					}
					
				}
	
			}
			
		}
		
		return true;
	}
	
	public void mainPage(Player player)
	{
		
		player.sendMessage(daqua + "This server is running RollTheDice version " + yellow + version + ".");
		player.sendMessage(gray + "For help, type " + purple + "/rtd help" + gray + ".");
		
	}
	
	public void helpPage(Player player)
	{
		
		player.sendMessage(daqua + "Visit the wiki at: " + yellow + website + daqua + " for in-depth information.");
		player.sendMessage(daqua + "List of commands:");
		player.sendMessage(daqua + "------------------");
		player.sendMessage(dgreen + "/rtd" + white + " - " + aqua + "Displays basic plugin information.");
		player.sendMessage(dgreen + "/rtd help" + white + " - " + aqua + "Displays help page.");
		player.sendMessage(dgreen + "/rtd list" + white + " - " + aqua + "Check the players in the match.");
		player.sendMessage(dgreen + "/rtd join" + white + " - " + aqua + "Join the match if it hasn't started yet.");
		player.sendMessage(dgreen + "/rtd leave" + white + " - " + aqua + "Leave the ongoing match.");
		player.sendMessage(dgreen + "/rtd wins" + white + " - " + aqua + "Check how many wins you have attained.");
		player.sendMessage(dgreen + "/rtd kills" + white + " - " + aqua + "Check the number of players you have killed.");
		player.sendMessage(dgreen + "/rtd leaderboard" + white + " - " + aqua + "Displays the top ranking players and their scores.");
		
		if (player.hasPermission("rtd.configure"))
		{
			
			player.sendMessage(daqua + " ");
			player.sendMessage(daqua + "Below is a list of admin commands:");
			player.sendMessage(daqua + "------------------");
			player.sendMessage(dgreen + "/rtd setworld <world>" + white + " - " + aqua + "Sets the world for which RTD will run in.");
			player.sendMessage(dgreen + "/rtd enable" + white + " - " + aqua + "Enables RTD matches on the server.");
			player.sendMessage(dgreen + "/rtd disable" + white + " - " + aqua + "Disables RTD matches on the server.");
			player.sendMessage(dgreen + "/rtd setmin <kills>" + white + " - " + aqua + "Set the minimum number of players needed to start a match.");
			player.sendMessage(dgreen + "/rtd setdelay <seconds>" + white + " - " + aqua + "Number of seconds to wait before starting when the minimum number of players is met.");
			player.sendMessage(dgreen + "/rtd settimelimit <minutes>" + white + " - " + aqua + "Time to wait before ending the ongoing match.");
			
		}
		
	}
	
	public void getKills(Player player)
	{
		
		int kills = plugin.getConfig().getInt("Data." + player.getName() + ".Kills");
		player.sendMessage(gold + pluginName + daqua + "You have " + yellow + kills + daqua + " kills.");
		
	}
	
	public void list(Player player)
	{
		
		if (isStarted)
		{
		
			if (inMatch.size() != 0)
			{
				
				player.sendMessage(gold + pluginName + dgreen + "Players in the match:");
				player.sendMessage(gold + pluginName + dgreen + "----------------------------------------");
				int counter = 1;
				
				for (Player p : inMatch)
				{
					
					player.sendMessage(gold + pluginName + yellow + counter + ". " + green + p.getName());
					counter++;
					
				}
				
			}
			else
			{
				
				player.sendMessage(gold + pluginName + red + "There are no players in the match right now.");
				
			}
		
		}
		else
		{
			
			player.sendMessage(gold + pluginName + red + "A match is not in progress!");
			
		}
	
	}
	
	public void setDelay(Player player, String delay)
	{
		
		try
		{
			
			int time = Integer.parseInt(delay);
			plugin.getConfig().set("RTD.Delay", time);
			plugin.saveConfig();
			player.sendMessage(gold + pluginName + daqua + "Delay time successfully set.");
			player.sendMessage(gold + pluginName + dgreen +  "If a match is ongoing, the changes will take effect after the match ends.");
			
		} catch (NumberFormatException e) {
			
			player.sendMessage(gold + pluginName + red + "You must enter a valid number!");
			
		}
		
		
	}
	
	public void setTimeLimit(Player player, String limit)
	{
		
		try
		{
			
			int time = Integer.parseInt(limit);
			plugin.getConfig().set("RTD.Time-Limit", time);
			plugin.saveConfig();
			player.sendMessage(gold + pluginName + daqua + "Time limit successfully set.");
			player.sendMessage(gold + pluginName + dgreen +  "If a match is ongoing, the changes will take effect after the match ends.");
			
		} catch (NumberFormatException e) {
			
			player.sendMessage(gold + pluginName + red + "You must enter a valid number!");
			
		}
		
	}
	
	public void calculateLeaderBoard(Player player)
	{
		
		List<String> players = plugin.getConfig().getStringList("Players.List");
		int highest = 0;
		HashMap<String, Integer> topWins = new HashMap<String, Integer>();
		HashMap<String, Integer> topKills = new HashMap<String, Integer>();
		List<String> list = new ArrayList<String>();
		String name = "";
		
		for (int l = 0; l < 10; l ++)
		{
			
			if (topWins.size() <= players.size())
			{
				
				for (String p : players)
				{
					
					int i = plugin.getConfig().getInt("Data." + p + ".Wins");
					
					if (highest < i && i != 0)
					{
						
						name = p;
						highest = i;
						
					}
					
				}
				
				topWins.put(name, highest);
				players.remove(name);
				list.add(name);
				name = "";
				highest = 0;
			
			}
			
		}
		
		name = "";
		highest = 0;
		List<String> players1 = plugin.getConfig().getStringList("Players.List");
		List<String> list1 = new ArrayList<String>();
		
		for (int l = 0; l < 10; l ++)
		{
			
			if (topKills.size() <= players1.size())
			{
				
				for (String p : players1)
				{
					
					int i = plugin.getConfig().getInt("Data." + p + ".Kills");
					
					if (highest < i && i != 0)
					{
						
						name = p;
						highest = i;
						
					}
					
				}
				
				topKills.put(name, highest);
				players1.remove(name);
				list1.add(name);
				name = "";
				highest = 0;
			
			}
			
		}
		
		player.sendMessage(gold + pluginName + daqua + "LeaderBoards:");
		player.sendMessage(daqua + "////////////////////////////////////////////////////////////////////////////");
		player.sendMessage(gold + "Wins:");
		
		for (int i = 0; i < topWins.size(); i ++)
		{
			
			player.sendMessage(yellow + "" + i + "." + green + players.indexOf(i) + " - " + topWins.get(players.indexOf(i)) + " wins");
		
		}
		
		player.sendMessage(" ");
		player.sendMessage(gold + "Kills:");
		
		for (int i = 0; i < topKills.size(); i ++)
		{
			
			player.sendMessage(yellow + "" + i + "." + green + players1.indexOf(i) + " - " + topKills.get(players1.indexOf(i)) + " kills");
		
		}
		
		player.sendMessage(daqua + "////////////////////////////////////////////////////////////////////////////");
		
	}
	
	public void getWins(Player player)
	{
		
		int wins = plugin.getConfig().getInt("Data." + player.getName() + ".Wins");
		player.sendMessage(gold + pluginName + daqua + "You have " + yellow + wins + daqua + " wins.");		
		
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
				saveExperience(player);
				player.sendMessage(gold + pluginName + daqua + "You have joined the match.");
				
				if(calculateRemaining() > 0)
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
	
	public void setMinKills(Player player, String kills)
	{
		
		int killNum;
		
		try
		{
			
			killNum = Integer.parseInt(kills);
			plugin.getConfig().set("RTD.Kills", killNum);
			
		} catch (NumberFormatException e) {
			
			player.sendMessage(gold + pluginName + red + "You must enter a valid number!");
			
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
	
	public void restoreExperience(Player player)
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
			restoreInventory(player);
			restoreExperience(player);
			
			if (isStarted)
			{
			
				for (Player p : inMatch)
				{
					
					p.sendMessage(dgreen + player.getName() + " has left the match.");
					
				}
			
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
			player.sendMessage(gold + pluginName + dgreen + "Minimum players successfully set.");
			
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
		
		delaySeconds = plugin.getConfig().getInt("RTD.Delay");
		Bukkit.broadcastMessage(gold + pluginName + daqua + "Match will start in " + delaySeconds + " seconds. Type " + purple + "/rtd join" + daqua + " to join!");
		delaySeconds = delaySeconds - 10;
		id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

		   public void run() 
		   {
			 
			   if (delaySeconds > 0)
			   {
				   
				   for (Player player : inMatch)
				   {
					 
					   player.sendMessage(gold + pluginName + daqua + "Match will begin in " + delaySeconds + " seconds.");
					   
				   }
				   
				   delaySeconds = delaySeconds - 10;
			   
			   }
			   else
			   {
				   
				   if (inMatch.size() > 1)
				   {
					   
					   isStarted = true;
					   for (Player player : inMatch)
					   {
						   
						   player.sendMessage(gold + pluginName + aqua + "Match has started.");
						   
					   }
					   startMatch(inMatch);
					   plugin.getServer().getScheduler().cancelTask(id);
					   
				   }
				   else
				   {
					   
					   if (inMatch.size() == 1)
					   {
						   
						   inMatch.get(0).sendMessage(gold + pluginName + daqua + "There are not enough players. Match has been cancelled.");
						   leaveMatch(inMatch.get(0));
						   plugin.getServer().getScheduler().cancelTask(id);
						   
					   }
					   
				   }
				   
			   }
			   
		   }
		}, 200L, 200L);
		
	}
	
	public void startMatch(List<Player> players)
	{

		for (Player player : players)
		{
			
			assignPlayer(player, Dice.roll());
			
		}
		
		int timeLimit = plugin.getConfig().getInt("RTD.Time-Limit") * 1200;
		stopId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			   public void run() 
			   {
			  
				   stopMatch(true, null);
			   
			   }
			   
		}, timeLimit);
		
	}
	
	public void checkWinner()
	{
		
		int highest = 0;
		List<Player> winners = new ArrayList<Player>();
		String winnerList = "";
		
		for (Player player : kills.keySet())
		{
			
			int pKills = kills.get(player);
			if (highest < pKills)
			{
				
				highest = pKills;
				winners.clear();
				winners.add(player);
				
			}
			
		}
		
		for (Player player : winners)
		{
			
			if (winners.indexOf(player) != winners.size() - 1)
			{
				
				winnerList += yellow + player.getName() + dgreen + ",";
			
			}
			else
			{
				
				winnerList += yellow + player.getName();
				
			}
			
		}
		
		updateScores(winners);
		
		Bukkit.broadcastMessage(gold + pluginName + daqua + "The match has ended!");
		
		if (highest == 0)
		{
			
			Bukkit.broadcastMessage(gold + pluginName + dgreen + "There was no winner that round.");
			Bukkit.broadcastMessage(gold + pluginName + daqua + "Want to be the next winner? Type " + purple + "/rtd join" + daqua + " now!");
			
		}
		else
		{
		
			if (winners.size() == 1)
			{
			
				Bukkit.broadcastMessage(gold + pluginName + dgreen + "The winner of the match is " + yellow + winners.get(0) + dgreen + " with " + yellow + highest + dgreen + " kills.");
			
			}
			else
			{
				
				Bukkit.broadcastMessage(gold + pluginName + dgreen + "The winners are: " + winnerList + dgreen + ". All tied at a score of " + yellow + highest + dgreen + ".");
				
			}
			
			Bukkit.broadcastMessage(gold + pluginName + daqua + "Want to beat their score? Type " + purple + "/rtd join" + daqua + " now!");
			
		}
	
	}
	
	public void updateScores(List<Player> winners)
	{
		
		for (Player player : inMatch)
		{
			
			plugin.getConfig().set("Data." + player.getName() + ".Kills", plugin.getConfig().getInt("Data." + player.getName() + ".Kills" + kills.get(player)));
			
			if (winners.contains(player))
			{
				
				plugin.getConfig().set("Data." + player.getName() + ".Wins", plugin.getConfig().getInt("Data." + player.getName() + ".Wins" + 1));
				
			}
			
		}
		
		plugin.saveConfig();
		
	}
	
	public void stopMatch(boolean limitReached, Player p)
	{
		
		if (limitReached)
		{
			
			Bukkit.broadcastMessage(gold + pluginName + daqua + "The time limit was reached.");
			checkWinner();
			
		}
		
		else
		{
			
			plugin.getServer().getScheduler().cancelTask(stopId);
			broadcastWinner(p);
			
		}
		
		for (Player player : inMatch)
		{
			
			leaveMatch(player);
			
		}
		
		clearAllFields();
			
	}

	public void broadcastWinner(Player player)
	{
	
		Bukkit.broadcastMessage(gold + pluginName + yellow + player.getName() + dgreen + " has won the match with a total of " + yellow + kills.get(player) + dgreen + " kills.");
		Bukkit.broadcastMessage(gold + pluginName + daqua + "Want to beat their score? Type " + purple + "/rtd join" + daqua + " now!");
		
	}
	
	public void assignPlayer(Player player, Integer side)
	{
		
		dice.put(player, side);
		
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
