package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.r0306.RollTheDice.Disguise.HandleDisguise;

public class _23 extends Arena implements Listener
{

	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 23))
		{
		
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 500, 2), true);
						
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (isIn(player, 23))
			{
					
				event.setDamage(6 - getDamageReduced(player));
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onShoot(ProjectileLaunchEvent event)
	{

		if (event.getEntity().getShooter() instanceof Player)
		{
			
			Player player = (Player) event.getEntity().getShooter();
			
			if (isIn(player, 23))
			{
				
				player.sendMessage(gold + pluginName + red + "You can't launch projectiles, you're a zombie!");
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 23))
		{
			
			event.setMessage("BRAINS!");
			
		}
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (isIn(player, 23))
		{
			
			HandleDisguise.unDisguisePlayer(player);
			
		}
		
	}
		
	public static void disguiseAsZombie(Player player)
	{
		
		HandleDisguise.disguisePlayerAsEntity(player, EntityType.ZOMBIE);
		
	}
		
	public int getDamageReduced(Entity entity)
    {
		
	    double red = 0.0;
		
		if (entity instanceof Player)
		{
			
			Player player = (Player) entity;
			
			PlayerInventory inv = player.getInventory();
		    
			ItemStack boots = inv.getBoots();
		    ItemStack helmet = inv.getHelmet();
		    ItemStack chest = inv.getChestplate();
		    ItemStack pants = inv.getLeggings();
		    

		    
		    if (helmet.getType() == Material.LEATHER_HELMET)red = red + 0.04;
		    else if (helmet.getType() == Material.GOLD_HELMET)red = red + 0.08;
		    else if (helmet.getType() == Material.CHAINMAIL_HELMET)red = red + 0.08;
		    else if (helmet.getType() == Material.IRON_HELMET)red = red + 0.08;
		    else if (helmet.getType() == Material.DIAMOND_HELMET)red = red + 0.12;
	
		    if (boots.getType() == Material.LEATHER_BOOTS)red = red + 0.04;
		    else if (boots.getType() == Material.GOLD_BOOTS)red = red + 0.04;
		    else if (boots.getType() == Material.CHAINMAIL_BOOTS)red = red + 0.04;
		    else if (boots.getType() == Material.IRON_BOOTS)red = red + 0.08;
		    else if (boots.getType() == Material.DIAMOND_BOOTS)red = red + 0.12;
		    
		    if (pants.getType() == Material.LEATHER_LEGGINGS)red = red + 0.08;
		    else if (pants.getType() == Material.GOLD_LEGGINGS)red = red + 0.12;
		    else if (pants.getType() == Material.CHAINMAIL_LEGGINGS)red = red + 0.16;
		    else if (pants.getType() == Material.IRON_LEGGINGS)red = red + 0.20;
		    else if (pants.getType() == Material.DIAMOND_LEGGINGS)red = red + 0.24;
		    
		    if (chest.getType() == Material.LEATHER_CHESTPLATE)red = red + 0.12;
		    else if (chest.getType() == Material.GOLD_CHESTPLATE)red = red + 0.20;
		    else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE)red = red + 0.20;
		    else if (chest.getType() == Material.IRON_CHESTPLATE)red = red + 0.24;
		    else if (chest.getType() == Material.DIAMOND_CHESTPLATE)red = red + 0.32;
	   
		    
		}
	    
		return (int) (red + 0.5);
    
    }
	
}
