package com.github.r0306.RollTheDice.DiceHandlers;

import net.minecraft.server.Packet70Bed;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Plugin;

public class _33 implements Colors
{

	public static void sendCredits(final Player player)
	{
		
		player.sendMessage(gold + pluginName + dgreen + "Entering credits sequence in 5 seconds...");
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{
			
			@Override
			public void run() 
			{
					
				((CraftPlayer)player).getHandle().netServerHandler.sendPacket(getCreditsPacket());
			
			}
		
		}, 100L);
		
	}
	
	public static Packet70Bed getCreditsPacket()
	{
		
		Packet70Bed packet = new Packet70Bed();

		packet.b = 4;
		
		return packet;
		
	}
	
}
