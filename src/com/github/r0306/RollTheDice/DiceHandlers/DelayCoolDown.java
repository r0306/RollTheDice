package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import net.minecraft.server.Packet43SetExperience;
import net.minecraft.server.Packet8UpdateHealth;
import net.minecraft.server.Packet9Respawn;
import net.minecraft.server.WorldType;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Plugin;
import com.github.r0306.RollTheDice.Util.Util;

public class DelayCoolDown implements Colors
{
	
	public DelayCoolDown()
	{
	
	
	
	}
		
	private static HashMap<Player, Integer> ids = new HashMap<Player, Integer>();

	public static void scheduleDelayedCoolDown(Player player, final Long ticks)
	{
		
		final Player p = player;
		final Float exp = Util.delayExp(ticks);
		
		p.setExp(0F);
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
			   
			   int counter = 0;
			
			   public void run()
			   {
			    

				   if (counter < ticks)
				   {   

					   p.setExp(p.getExp() + exp);
					   counter ++;
					   
				   }
				   else
				   {
					 
					   p.setExp(1);
					   Bukkit.getServer().getScheduler().cancelTask(ids.get(p));
					   ids.remove(p);
					   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p, id);
		
	}
	
	public static void scheduleDelayedCoolDownRespawn(final Player player, final Long ticks)
	{
		
		final Player p = player;
		final Float exp = Util.delayExp(ticks);
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
			   
			   int counter = 0;
			
			   public void run()
			   {
			    

				   if (counter < ticks && p.getExp() >= 0F)
				   {   

					   p.setExp(p.getExp() - exp);
					   ((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getExp(p.getExp(), p.getLevel()));
					   counter ++;
					   
				   }
				   else
				   {
					 
					  p.setExp(0F);
	
					  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
					  {
							
						  @Override
						  public void run() 
						  {

							  ((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getRespawn());
							 
							  for (Player p : Bukkit.getOnlinePlayers())
							  {
								  
								  p.showPlayer(player);
								  
							  }
							 
							  player.teleport(player.getLocation());
							  
						  }
						
					  }, 0L);
;
					  p.sendMessage(gold + pluginName + green + "You have been revived.");
					  Bukkit.getServer().getScheduler().cancelTask(ids.get(p));
					  ids.remove(p);
					   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p, id);
		
	}
	
	public static Packet8UpdateHealth getHealth()
	{
		
	    Packet8UpdateHealth packet = new Packet8UpdateHealth();
	    packet.a = 20;
	    packet.b = (short) 20;
	    packet.c = 5;
	    
	    return packet;
	    
	}
	
	public static Packet9Respawn getRespawn()
	{
		
		Packet9Respawn packet = new Packet9Respawn();
		packet.a = 0;
		packet.b = (byte)1;
		packet.c = (byte)0;
		packet.d = (short) 256;
		packet.e = WorldType.VERSION_1_1f;
		
		return packet;
		
	}
	
	public static Packet43SetExperience getExp(float exp, int level)
	{
		
		Packet43SetExperience packet = new Packet43SetExperience();
		packet.a = exp;
		packet.b = (short) level;

		return packet;	
		
	}
	
	
}
