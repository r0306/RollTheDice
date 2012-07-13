package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Plugin;

public class _19 extends Arena implements Listener, Colors
{
	
	static HashMap<Player, Integer> ids = new HashMap<Player, Integer>();
	static HashMap<Player, String> CAPTCHAList = new HashMap<Player, String>();

	public void scheduleRepeatingCAPTCHA(Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{
			
				

			}
			
		}, 50L, 50L);
		
		ids.put(player, id);
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player))
			{
				
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		
		Entity damager = event.getDamager();
		
		if (event.getDamager() instanceof Player)
		{
			
			
			
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 2019))
		{
			
			if (CAPTCHAList.containsKey(player))
			{
				
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player))
			{
				
				if (event.getMessage().equals(CAPTCHAList.get(player)))
				{
					
					player.sendMessage(gold + pluginName + daqua + "Matched! You may proceed.");
					CAPTCHAList.remove(player);
					
				}
				else
				{
				
					player.sendMessage(gold + pluginName + red + "CAPTCHA did not match. Remember it's case-sensitive.");
					
				}
			
			}
			
		}
		
	}
	
	public void promptCAPTCHA(Player player)
	{
		
		String CAPTCHA = generateNewCAPTCHA();
		
		CAPTCHAList.put(player, CAPTCHA);
		
		player.sendMessage(gold + pluginName + daqua + "To procced, enter the following case-sensitive CAPTCHA: " + green + CAPTCHA);
		
	}
	
	public String generateNewCAPTCHA()
	{
		
		int length = 15;
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		Random rand = new Random();
		
		char[] letters = new char[length];

		
		for (int i = 0; i < length; i++)
	    {
	       
			letters[i] = characters.charAt(rand.nextInt(characters.length()));
	    
	    }

		return new String(letters);
		
	}
	
}
