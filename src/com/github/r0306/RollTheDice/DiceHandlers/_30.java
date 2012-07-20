package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class _30 extends Arena implements Listener
{

	static HashMap<String, Material> weapons = new HashMap<String, Material>();
	
	public static ItemStack assignWeapon(Player player)
	{
		
		
		Material material = getRandomWeapon();
		
		weapons.put(player.getName(), material);

		return new ItemStack(material, 1);
		
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent event)
	{
		
		if (event.getEntity().getKiller() != null)
		{
			
			Player player = event.getEntity().getKiller();
			
			if (isIn(player, 30))
			{
				
				changeWeapon(player);
				
			}
					
		}
		
	}
	
	public static void changeWeapon(Player player)
	{
		
		removeOldWeapons(player);
		
		ItemStack itemstack = assignWeapon(player);
		
		player.getInventory().addItem(itemstack);
		
		if (itemstack.getType() == Material.BOW)
		{
			
			player.getInventory().addItem(new ItemStack(Material.ARROW, 30));
			
		}
		
	}
	
	public static void removeOldWeapons(Player player)
	{
		
		if (weapons.containsKey(player.getName()))
		{
		
			Material material = weapons.get(player.getName());
			
			if (player.getInventory().contains(material))
			{
				
				player.getInventory().remove(material);
				
				if (material == Material.BOW)
				{
					
					player.getInventory().remove(new ItemStack(Material.ARROW, 30));
					
				}
				
			}
			
		}
		
	}
	
	public static Material getRandomWeapon()
	{
		
		Material[] materials = {Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD,
								Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD,
								Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD,
								Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD,
								Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD, Material.BOW};
		
		Random random = new Random();
		
		return materials[random.nextInt(materials.length)];	
		
	}

}
