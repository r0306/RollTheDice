package com.github.r0306.RollTheDice.DiceHandlers;

import java.lang.reflect.Field;
import java.util.HashMap;

import net.minecraft.server.Packet38EntityStatus;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _14 extends Arena implements Listener
{

	static HashMap<String, Integer> ids = new HashMap<String, Integer>();
	
	public static void scheduleDelayedDamage(final Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{
			
				for (Player p : Bukkit.getOnlinePlayers())
				{
					
					((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getMobStatus(player));
					
				}

			}
			
		}, 10L, 10L);
		
		ids.put(player.getName(), id);
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 14))
		{
			
			Bukkit.getScheduler().cancelTask(ids.get(player.getName()));
			ids.remove(player.getName());
			
		}
		
	}
	
	public static Packet38EntityStatus getMobStatus(Player player)
	{
	
		Packet38EntityStatus packet = new Packet38EntityStatus();
		packet.a = player.getEntityId();
	   
		try
	    {
		
	    	Field metadataField = packet.getClass().getDeclaredField("b");
	    	metadataField.setAccessible(true);
		    metadataField.set(packet, (byte)2);
		    
	    } catch (Exception e) {

		      e.printStackTrace();

	    }
		
	    return packet;
		
	}
	
}
