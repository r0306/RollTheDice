package com.github.r0306.RollTheDice.DiceHandlers;

import java.lang.reflect.Field;

import net.minecraft.server.Packet38EntityStatus;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
			
			for (Player p : Bukkit.getOnlinePlayers())
			{
				
				((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getMobStatus(player));
				p.hidePlayer(player);
				
			}

			player.sendMessage(gold + pluginName + blue + "REVIVING...");

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
			{
				
				@Override
				public void run() 
				{

					player.setExp(1F);
					player.setHealth(20);
					System.out.println(player.isDead());

				}
				
			
			}, 0L);
/*
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
			{
				
				@Override
				public void run() 
				{

					player.setHealth(20);

				}
				
			
			}, 5L);
*/
			DelayCoolDown.scheduleDelayedCoolDownRespawn(player, DELAY_TICKS);
			
		}
		
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event)
	{
		System.out.println(event.getPlayer());
		Player player = event.getPlayer();
		
		if (isIn(player, 17))
		{


		
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
