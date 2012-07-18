package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

import com.github.r0306.RollTheDice.Util.Plugin;

import net.minecraft.server.Packet18ArmAnimation;

public class _26 extends Arena implements Listener
{
	
	static HashMap<String, Integer> ids = new HashMap<String, Integer>();

	public static void scheduleArmSwing(final Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{
					
				for (Player p : Bukkit.getOnlinePlayers())
				{
				
					((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getAnimation(player));
					
				}
			
			}
			
		}, 2L, 2L);
		
		ids.put(player.getName(), id);
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 26))
		{
			
			cancelSwing(player);
			
		}
		
	}
	
	public void cancelSwing(Player player)
	{
		
		if (ids.containsKey(player.getName()))
		{
			
			Bukkit.getScheduler().cancelTask(ids.get(player.getName()));
			ids.remove(player.getName());
			
		}
		
	}
	
	public static Packet18ArmAnimation getAnimation(Player player)
	{
		
		Packet18ArmAnimation packet = new Packet18ArmAnimation();
		packet.a = player.getEntityId();
		packet.b = 1;

		return packet;
		
	}
	
}
