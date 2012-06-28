package com.github.r0306.RollTheDice.Disguise;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.Packet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.World;

import com.github.r0306.RollTheDice.Arena;

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
		sendMovement(event.getPlayer(), null, event.getPlayer().getVelocity(), event.getTo());
		System.out.println("hi");
	}
		
   // }
  
  }

  @EventHandler(priority=EventPriority.LOW)
  public void onPlayerJoin(PlayerJoinEvent event)
  {
  	
	showWorldDisguises(event.getPlayer());

    if (disguiseQuitters.contains(event.getPlayer().getName()))
    {
      
    	event.getPlayer().sendMessage(ChatColor.RED + "You were undisguised because you left the server.");
      
    	disguiseQuitters.remove(event.getPlayer().getName());
    
    }
    
	disguisePlayer(event.getPlayer(), new Disguise(getNextAvailableID(), MobType.Blaze));
  
  }

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
