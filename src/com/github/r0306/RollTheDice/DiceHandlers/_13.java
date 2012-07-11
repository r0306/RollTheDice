package com.github.r0306.RollTheDice.DiceHandlers;

import java.lang.reflect.Field;
import java.util.HashMap;

import net.minecraft.server.DataWatcher;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet24MobSpawn;
import net.minecraft.server.Packet29DestroyEntity;
import net.minecraft.server.Packet38EntityStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _13 extends Arena implements Listener
{
	
	static HashMap<Player, Integer> ids = new HashMap<Player, Integer>();
	static DataWatcher metadata;
	static int id = -185554454;

	public static void schedulePlayerOrgasm(final Player player)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{
			
				int tempID = getNewId();
				
				for (Player p : Bukkit.getServer().getOnlinePlayers())
				{
				
					((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getMobSpawnPacket(player.getLocation(), tempID));
					((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getMobStatus(tempID));
					((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getEntityDestroyPacket(tempID));

				}

			}
			
		}, 50L, 50L);
		
		ids.put(player, id);
		
	}
		
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 13))
		{
			
			Bukkit.getScheduler().cancelTask(ids.get(player));
			ids.remove(player);
			
		}
		
	}
		  
	public static Packet38EntityStatus getMobStatus(int mobID)
	{
	
		Packet38EntityStatus packet = new Packet38EntityStatus();
		packet.a = mobID;
	   
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
	  
	public static Packet24MobSpawn getMobSpawnPacket(Location loc, int mobID)
	{

		int x = MathHelper.floor(loc.getX() * 32.0D);
	    int y = MathHelper.floor(loc.getY() * 32.0D);
	    int z = MathHelper.floor(loc.getZ() * 32.0D);
		
	    metadata = new DataWatcher();
		metadata.a(0, Byte.valueOf((byte) 0));
		metadata.a(12, Integer.valueOf(0));
	    metadata.a(16, Byte.valueOf((byte) 0));
	   
	    Packet24MobSpawn packet = new Packet24MobSpawn();
	    packet.a = mobID;
	    packet.b = (byte)56;
	    packet.c = x;
	    packet.d = y;
	    packet.e = z;
	    packet.f = degreeToByte(loc.getYaw());
	    packet.g = degreeToByte(loc.getPitch());
	    packet.h = packet.f;
	   
	    try
	    {
	    
	    	Field metadataField = packet.getClass().getDeclaredField("i");
	        metadataField.setAccessible(true);
	        metadataField.set(packet, metadata);

	     } catch (Exception e) {

	        e.printStackTrace();

	     }
     
	    return packet;
	
	  }
	  
	  public static Packet29DestroyEntity getEntityDestroyPacket(int mobID)
	  {
	   
		  id --;
		  
		  return new Packet29DestroyEntity(mobID);
	  
	  }
	  
	  public static byte degreeToByte(float degree)
	  {
	   
		  return (byte)(int)((int)degree * 256.0F / 360.0F);
	  
	  }
	  
	  public static int getNewId()
	  {
		  
		  return id ++;
		  
	  }
	
	
}
