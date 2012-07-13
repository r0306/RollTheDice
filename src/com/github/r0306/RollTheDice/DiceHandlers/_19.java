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
	
	static HashMap<Player, Integer> ids = new HashMap<Player, Integer>();
	static HashMap<Player, String> CAPTCHAList = new HashMap<Player, String>();

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
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 19))
		{
			
			if (ids.containsKey(player))
			{
				
				Bukkit.getScheduler().cancelTask(ids.get(player));
				ids.remove(player);
				
			}
			
			if (CAPTCHAList.containsKey(player))
			{
				
				CAPTCHAList.remove(player);
				
			}
			
		}
		
	}
		
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
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
			
			Player player = (Player) damager;
			
			if (isIn(player, 19))
			{
				
				if (CAPTCHAList.containsKey(player))
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
			
			if (CAPTCHAList.containsKey(player))
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
			
			if (CAPTCHAList.containsKey(player))
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
			
			if (CAPTCHAList.containsKey(player))
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
			
			if (CAPTCHAList.containsKey(player))
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
			
			if (CAPTCHAList.containsKey(player))
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
			
			if (CAPTCHAList.containsKey(player))
			{
				
				if (event.getMessage().equals(CAPTCHAList.get(player)))
				{
					
					player.sendMessage(gold + pluginName + daqua + "Matched! You may proceed.");
					CAPTCHAList.remove(player);
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
		
		CAPTCHAList.put(player, CAPTCHA);
		
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
