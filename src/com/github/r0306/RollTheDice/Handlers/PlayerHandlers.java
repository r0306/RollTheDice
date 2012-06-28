package com.github.r0306.RollTheDice.Handlers;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.r0306.RollTheDice.Executor;
import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Disguise.Disguise;
import com.github.r0306.RollTheDice.Disguise.Disguise.MobType;

public class PlayerHandlers extends Executor implements Listener 
{
	
	private RollTheDice plugin;
	
	public PlayerHandlers(RollTheDice plugin) {
		super(plugin);
		this.plugin = plugin;
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (inMatch.contains(player) && isStarted)
		{
			
			leaveMatch(player);
			
		}
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		
		Player player = event.getPlayer();
		
		List<String> playerNames = plugin.getConfig().getStringList("Players.List");
		LinkedList<String> test = new LinkedList<String>();
		
		Disguise d = new Disguise(10, test , MobType.Cow);
		Disguise.disguiseDB.put(player.getName(), d);
		
		if (!playerNames.contains(player.getName()))
		{
			
			playerNames.add(player.getName());
			
		}
		
		plugin.getConfig().set("Players.List", playerNames);
		plugin.getConfig().set("Data." + player.getName() + ".Wins", 0);
		plugin.getConfig().set("Data." + player.getName() + ".Kills", 0);
		plugin.saveConfig();
		
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		Player killer = null;
		int min = plugin.getConfig().getInt("RTD.Minimum");

		if (inMatch.contains(player))
		{
			
			if (player.getKiller() != null)
			{
				
				killer = player.getKiller();
				
			}
			else
			{
				
				
				
			}
				
			kills.put(killer, kills.get(killer) + 1);
				
			if (kills.get(killer) >= min)
			{
				
				stopMatch(false, killer);
				
			}
			
		}
		
	}

}
