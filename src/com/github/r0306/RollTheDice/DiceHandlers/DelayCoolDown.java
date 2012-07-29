package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import net.minecraft.server.Packet43SetExperience;
import net.minecraft.server.Packet8UpdateHealth;
import net.minecraft.server.Packet9Respawn;
import net.minecraft.server.WorldType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.github.r0306.RollTheDice.KillStreaks.CarePackage;
import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Plugin;
import com.github.r0306.RollTheDice.Util.Util;

public class DelayCoolDown implements Colors
{
	
	public DelayCoolDown()
	{
	
	
	
	}
		
	private static HashMap<String, Integer> ids = new HashMap<String, Integer>();
	private static HashMap<String, Integer> chestIds = new HashMap<String, Integer>();

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
					   ((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getExp(p.getExp(), p.getLevel()));
					   counter ++;
					   
				   }
				   else
				   {
					 
					   p.setExp(1);
					   Bukkit.getServer().getScheduler().cancelTask(ids.get(p.getName()));
					   ids.remove(p.getName());
					   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p.getName(), id);
		
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

					  p.sendMessage(gold + pluginName + green + "You have been revived.");
					  Bukkit.getServer().getScheduler().cancelTask(ids.get(p.getName()));
					  ids.remove(p.getName());
					   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p.getName(), id);
		
	}
	
	public static void scheduleDelayedChestOpen(final Player player, final long ticks, final Inventory inventory, final boolean fake)
	{
		
		final float exp = Util.delayExp(ticks);
		final float originalExp = player.getExp();
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
			
			   int counter = 0;
			
			   public void run()
			   {
			    

				   if (counter < ticks)
				   {   

					   player.setExp(player.getExp() + exp);
					   ((CraftPlayer)player).getHandle().netServerHandler.sendPacket(getExp(player.getExp(), player.getLevel()));
					   counter ++;
					   
				   }
				   else
				   {
					   
					   CarePackage cp = new CarePackage();
					   cp.unRegisterOpeningPackage(player);
					   player.setExp(originalExp);
					   
					   if (fake)
					   {
						   
						   _20 fakeCP = new _20();
						   generatePackageExplosion(cp.getPlayerOpeningChest(player));
						   fakeCP.removeOpeningChest(player);
						   
						   
					   }
					   else
					   {
						   
						   player.openInventory(inventory);

						   
					   }


					   cancelChestOpen(player);

			   					   
				   }
				   
			   }
			
		}, 1L, 1L);
		
		chestIds.put(player.getName(), id);
				
	}
	
	public static void scheduleDelayedCoolDownEndGodMode(final Player player, final Long ticks)
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
					  
					  Bukkit.getServer().getScheduler().cancelTask(ids.get(p.getName()));
					  ids.remove(p.getName());
	
					  _32.scheduleGodModeCoolDown(player);
					  	   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p.getName(), id);
		
	}
	
	public static void scheduleDelayedCoolDownGodMode(final Player player, final Long ticks)
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
					   ((CraftPlayer)p).getHandle().netServerHandler.sendPacket(getExp(p.getExp(), p.getLevel()));
					   counter ++;
					   
				   }
				   else
				   {
					 
					   p.setExp(1);
					   
					   Bukkit.getServer().getScheduler().cancelTask(ids.get(p.getName()));
					   ids.remove(p.getName());
					   
					   _32.scheduleGodModeDelay(player);
					   
				   }
			   
			   }
			
		}, 1L, 1L);
		
		ids.put(p.getName(), id);
		
	}
	
	public static void generatePackageExplosion(Location location)
	{
		
		location.getWorld().createExplosion(location, 1.5F);
		
	}
	
	public static boolean isOpeningPackage(Player player)
	{
		
		return chestIds.containsKey(player.getName());
		
	}
	
	public static void cancelChestOpen(Player player)
	{
		
		if (chestIds.containsKey(player.getName()))
		{
			
		   Bukkit.getServer().getScheduler().cancelTask(chestIds.get(player.getName()));
		   chestIds.remove(player.getName());
		
		}
		   
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
