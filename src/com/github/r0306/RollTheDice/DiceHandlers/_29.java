package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import net.minecraft.server.Packet12PlayerLook;
import net.minecraft.server.Packet32EntityLook;
import net.minecraft.server.Packet35EntityHeadRotation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _29 extends Arena implements Listener
{
	
	static HashMap<String, Integer> ids = new HashMap<String, Integer>();
	static HashMap<String, Float> yaws = new HashMap<String, Float>();

	public static void scheduleTurning(final Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{

				
				for (Player p: Bukkit.getOnlinePlayers())
					((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getHeadRotatePacket(player));

			}
			
		}, 1L, 1L);
		
		ids.put(player.getName(), id);
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 29))
		{
			
			cancelTurning(player);
			
		}
		
	}
	
	public void cancelTurning(Player player)
	{
		
		if (ids.containsKey(player.getName()))
		{
			
			Bukkit.getScheduler().cancelTask(ids.get(player.getName()));
			ids.remove(player.getName());
			
		}
		
	}
	
	  public static Packet35EntityHeadRotation getHeadRotatePacket(Player player) {
		    return new Packet35EntityHeadRotation(player.getEntityId(), degreeToByte(player.getLocation().getYaw() + 1));
		  }
	
	public static Packet32EntityLook getHead(Player player)
	{
		
		Packet32EntityLook packet = new Packet32EntityLook();
		packet.a = player.getEntityId();
		yawHandler(player);
		System.out.println(getYaw(player));
		packet.b = 0;
		packet.c = 0;
		packet.d = 0;
		packet.e = degreeToByte(player.getLocation().getYaw() + 1);
		packet.f = degreeToByte(player.getLocation().getPitch());
		
		return packet;
	
	}
	
    public static byte degreeToByte(float degree)
    {
   
	    return (byte)(int)((int)degree * 256.0F / 360.0F);
  
    }
	
	public static void yawHandler(Player player)
	{
		
		if (!yaws.containsKey(player.getName()))
		{
			
			yaws.put(player.getName(), 220F);
			
		}
		else
		{
			
			yaws.put(player.getName(), yaws.get(player.getName()) + 1);
			
		}
		
	}
	
	public static float getYaw(Player player)
	{
		
		if (yaws.containsKey(player.getName()))
		{
			return 220F;
			//return yaws.get(player.getName());
			
		}
		
		return 220F;
		
	}
	
}
