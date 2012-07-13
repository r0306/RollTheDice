package com.github.r0306.RollTheDice.DiceHandlers;

import java.lang.reflect.Field;

import net.minecraft.server.Packet38EntityStatus;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Plugin;

public class _17 extends Arena implements Listener, Colors
{

	final long DELAY_TICKS = 120L;
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		final Player player = event.getEntity();
		
		if (isIn(player, 17))
		{
			
			player.setHealth(20);
			player.setExp(1F);
			
			for (Player p : Bukkit.getOnlinePlayers())
			{
				
				((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getMobStatus(player));
				
			}
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
			{
				
				@Override
				public void run() 
				{

					for (Player p : Bukkit.getOnlinePlayers())
					{
						
						p.hidePlayer(player);
				
					}
					

				}
				
			
			}, 15L);
			
			player.sendMessage(gold + pluginName + blue + "REVIVING...");

			DelayCoolDown.scheduleDelayedCoolDownRespawn(player, DELAY_TICKS);
			
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
		    metadataField.set(packet, (byte)3);
		    
	    } catch (Exception e) {

		      e.printStackTrace();

	    }
		
	    return packet;
		
	}

}
