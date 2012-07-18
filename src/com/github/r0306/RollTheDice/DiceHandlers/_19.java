package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Plugin;

public class _19 extends Arena implements Listener, Colors
{
	
	static HashMap<String, Integer> ids = new HashMap<String, Integer>();
	static HashMap<String, String> CAPTCHAList = new HashMap<String, String>();

	public static void scheduleDelayedCAPTCHA(final Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{
							
				promptCAPTCHA(player);
			
			}
			
		}, 200L);
		
		ids.put(player.getName(), id);
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player.getName()))
			{
				
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 19))
		{
			
			if (ids.containsKey(player.getName()))
			{
				
				Bukkit.getScheduler().cancelTask(ids.get(player.getName()));
				ids.remove(player.getName());
				
			}
			
			if (CAPTCHAList.containsKey(player.getName()))
			{
				
				CAPTCHAList.remove(player.getName());
				
			}
			
		}
		
	}
		
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player.getName()))
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
			
			Player player = (Player) damager;
			
			if (isIn(player, 19))
			{
				
				if (CAPTCHAList.containsKey(player.getName()))
				{
					
					event.setCancelled(true);
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player.getName()))
			{
				
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onShoot(PlayerFishEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player.getName()))
			{
				
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onEggThrow(PlayerEggThrowEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player.getName()))
			{
				
				event.getEgg().remove();
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player.getName()))
			{
				
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player.getName()))
			{
						
				player.teleport(event.getFrom());

			}
			
		}
		
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 19))
		{
			
			if (CAPTCHAList.containsKey(player.getName()))
			{
				
				if (event.getMessage().equals(CAPTCHAList.get(player)))
				{
					
					player.sendMessage(gold + pluginName + daqua + "Matched! You may proceed.");
					CAPTCHAList.remove(player.getName());
					scheduleDelayedCAPTCHA(player);
					
				}
				else
				{
				
					player.sendMessage(gold + pluginName + red + "CAPTCHA did not match. Remember it's case-sensitive.");
					
				}
				
				event.setCancelled(true);
			
			}
			
		}
		
	}
	
	public static void promptCAPTCHA(Player player)
	{
		
		String CAPTCHA = generateNewCAPTCHA();
		
		CAPTCHAList.put(player.getName(), CAPTCHA);
		
		player.sendMessage(gold + pluginName + daqua + "To procced, enter the following case-sensitive CAPTCHA: " + green + CAPTCHA);
		
	}
	
	public static String generateNewCAPTCHA()
	{
		
		int length = 15;
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		Random rand = new Random();
		
		char[] letters = new char[length];

		
		for (int i = 0; i < length; i++)
	    {
	       
			letters[i] = characters.charAt(rand.nextInt(characters.length()));
	    
	    }

		return new String(letters);
		
	}
	
}
