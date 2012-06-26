package com.github.r0306.RollTheDice;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitHandler extends Executor implements Listener 
{
	
	public PlayerQuitHandler(RollTheDice plugin) {
		super(plugin);
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

}
