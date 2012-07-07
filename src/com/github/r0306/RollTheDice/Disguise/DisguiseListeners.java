package com.github.r0306.RollTheDice.Disguise;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet18ArmAnimation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.World;

import com.github.r0306.RollTheDice.DiceHandlers.Arena;

public class DisguiseListeners extends Disguise implements Listener
{

  public DisguiseListeners() {
	

  }	

  public DisguiseListeners(int entityID, LinkedList<String> data, MobType mob) {
		super(entityID, data, mob);
	}

@EventHandler
  public void onPlayerMove(PlayerMoveEvent event)
  {
    
	//if (Arena.disguise.containsKey(event.getPlayer()))
   // {
	if (disguiseDB.containsKey(event.getPlayer().getName())) {
		Player player = event.getPlayer();
		sendMovement(event.getPlayer(), null, event.getPlayer().getVelocity(), event.getTo());
	}
		
   // }
  
  }

  @EventHandler(priority=EventPriority.LOW)
  public void onPlayerJoin(PlayerJoinEvent event)
  {
	    Player player = event.getPlayer();
	    EntityPlayer entity = ((CraftPlayer)player).getHandle();
	    if (!(entity.netServerHandler instanceof DCHandler)) {
	        entity.netServerHandler.disconnected = true;
	        NetServerHandler newHandler = new DCNetServerHandler(entity.server, entity.netServerHandler.networkManager, entity);
	        newHandler.a(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
	        entity.server.networkListenThread.a(newHandler);
	    }

  }
	
  /*	
	showWorldDisguises(event.getPlayer());

    if (disguiseQuitters.contains(event.getPlayer().getName()))
    {
      
    	event.getPlayer().sendMessage(ChatColor.RED + "You were undisguised because you left the server.");
      
    	disguiseQuitters.remove(event.getPlayer().getName());
    
    }
    
	disguisePlayer(event.getPlayer(), new Disguise(getNextAvailableID(), MobType.Blaze));
  */
  

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    if (disguiseDB.containsKey(player.getName())) {
      unDisguisePlayer(player);
      disguiseQuitters.add(player.getName());
    }

    halfUndisguiseAllToPlayer(player);
  }

  @EventHandler
  public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
    showWorldDisguises(event.getPlayer());

    if (disguiseDB.containsKey(event.getPlayer().getName())) {
      Player disguisee = event.getPlayer();
      Disguise disguise = (Disguise)disguiseDB.get(disguisee.getName());

      Packet killPacket = disguise.getEntityDestroyPacket();
      Packet killListPacket = disguise.getPlayerInfoPacket(disguisee, false);
      Packet revivePacket = disguise.getMobSpawnPacket(disguisee.getLocation());
      Packet revivePlayerPacket = disguise.getPlayerSpawnPacket(disguisee.getLocation(), (short)disguisee.getItemInHand().getTypeId());
      Packet reviveListPacket = disguise.getPlayerInfoPacket(disguisee, true);

      if (killListPacket == null)
        undisguiseToWorld(event.getFrom(), disguisee, new Packet[] { killPacket });
      else {
        undisguiseToWorld(event.getFrom(), disguisee, new Packet[] { killPacket, killListPacket });
      }
      
      if (disguise.isPlayer())
        disguiseToWorld(disguisee.getWorld(), disguisee, new Packet[] { revivePlayerPacket, reviveListPacket });
      else
        disguiseToWorld(disguisee.getWorld(), disguisee, new Packet[] { revivePacket });
    }
  }

  @EventHandler
  public void onTarget(EntityTargetEvent event) {
    if ((!event.isCancelled()) && 
      ((event.getTarget() instanceof Player))) {
      Player player = (Player)event.getTarget();
      if ((disguiseDB.containsKey(player.getName()))) {
          event.setCancelled(true);
        
      }
        else if ((!((Disguise)disguiseDB.get(player.getName())).isPlayer()) && ((event.getReason() == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) || (event.getReason() == EntityTargetEvent.TargetReason.RANDOM_TARGET)))
          event.setCancelled(true);
    }
  }

  @EventHandler
  public void onDamage(EntityDamageEvent event) {
    if ((!event.isCancelled()) && 
      ((event.getEntity() instanceof Player))) {
      Player player = (Player)event.getEntity();
      if (disguiseDB.containsKey(player.getName()))
      {
        Packet18ArmAnimation packet = new Packet18ArmAnimation();
        packet.a = ((Disguise)disguiseDB.get(player.getName())).entityID;
        packet.b = 2;
        sendPacketToWorld(player.getWorld(), new Packet[] { packet });
      }
    }
  }
  
  
  
  @EventHandler
  public void onDisguiseHit(PlayerInvalidInteractEvent event) {
    if (event.getAction()) {
    	System.out.println("hi");
      Player attacked = getPlayerFromDisguiseID(event.getTarget());
      if (attacked != null)
      {
        ((CraftPlayer)event.getPlayer()).getHandle().attack(((CraftPlayer)attacked).getHandle());
      }
    }
  }

  
  
  
  @EventHandler
  public void onPickup(PlayerPickupItemEvent event)
  {
    if ((!event.isCancelled()) && 
      (disguiseDB.containsKey(event.getPlayer().getName()))) {
      Disguise disguise = (Disguise)disguiseDB.get(event.getPlayer().getName());
      if ((disguise.data != null) && (disguise.data.contains("nopickup")))
        event.setCancelled(true);
    }
  }
}
