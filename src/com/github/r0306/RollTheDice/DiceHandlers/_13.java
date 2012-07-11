package com.github.r0306.RollTheDice.DiceHandlers;

import java.lang.reflect.Field;
import java.util.HashMap;

import net.minecraft.server.DataWatcher;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet24MobSpawn;
import net.minecraft.server.Packet38EntityStatus;
import net.minecraft.server.Packet41MobEffect;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.r0306.RollTheDice.Disguise.Disguise.MobType;
import com.github.r0306.RollTheDice.Util.Plugin;

public class _13 extends Arena implements Listener
{
	
	static HashMap<Player, Integer> ids = new HashMap<Player, Integer>();
	static DataWatcher metadata;

	public static void schedulePlayerOrgasm(final Player player)
	{
		
		((CraftPlayer)player).getHandle().netServerHandler.sendPacket(getMobSpawnPacket(new Location(player.getWorld(), 0, 0, 0)));
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			@Override
			public void run() 
			{
			
				player.getWorld().playEffect(player.getLocation(), Effect.GHAST_SHRIEK, 500);
				
			}
			
		}, 5L, 5L);
		
		ids.put(player, id);
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 13))
		{
			
			Bukkit.getScheduler().cancelTask(ids.get(player));
			
		}
		
	}
	
	  public static Packet41MobEffect getMobEffect(Player player) {
		    Packet41MobEffect packet = new Packet41MobEffect();
		    packet.a = -1947483648;
		    try {
		      Field metadataField = packet.getClass().getDeclaredField("b");
		      metadataField.setAccessible(true);
		      metadataField.set(packet, (byte)2);
		    } catch (Exception e) {
		    //  System.out.println("DisguiseCraft was unable to set the metadata for a " + this.mob.name() + " disguise!");
		      e.printStackTrace();
		    }
		    return packet;
		  }
	  
	  public static Packet38EntityStatus getMobStatus(Player player) {
		  Packet38EntityStatus packet = new Packet38EntityStatus();
		    packet.a = -1947483648;
		    try {
		      Field metadataField = packet.getClass().getDeclaredField("b");
		      metadataField.setAccessible(true);
		      metadataField.set(packet, (byte)2);
		    } catch (Exception e) {
		    //  System.out.println("DisguiseCraft was unable to set the metadata for a " + this.mob.name() + " disguise!");
		      e.printStackTrace();
		    }
		    return packet;
		  }
	  
	  public static Packet24MobSpawn getMobSpawnPacket(Location loc)
	  {

	      int x = MathHelper.floor(loc.getX() * 32.0D);
	      int y = MathHelper.floor(loc.getY() * 32.0D);
	      int z = MathHelper.floor(loc.getZ() * 32.0D);
		    metadata = new DataWatcher();
		    metadata.a(0, Byte.valueOf((byte) 0));
		    metadata.a(12, Integer.valueOf(0));
	      metadata.a(16, Byte.valueOf((byte) 0));
	      Packet24MobSpawn packet = new Packet24MobSpawn();
	      packet.a = -1947483648;
	      packet.b = (byte)56;
	      packet.c = x;
	      packet.d = y;
	      packet.e = z;
	      packet.f = degreeToByte(Bukkit.getPlayer("r0306").getLocation().getYaw());
	      packet.g = degreeToByte(Bukkit.getPlayer("r0306").getLocation().getPitch());
	      packet.h = packet.f;
	      try {
	        Field metadataField = packet.getClass().getDeclaredField("i");
	        metadataField.setAccessible(true);
	        metadataField.set(packet, metadata);

	      } catch (Exception e) {
	    //    System.out.println("DisguiseCraft was unable to set the metadata for a " + this.mob.name() + " disguise!");
	        e.printStackTrace();
	      }

	      return packet;
	
	
	  }
	  
	  public static byte degreeToByte(float degree)
	  {
	   
		  return (byte)(int)((int)degree * 256.0F / 360.0F);
	  
	  }
	
	
	
}
