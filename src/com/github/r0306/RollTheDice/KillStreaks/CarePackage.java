package com.github.r0306.RollTheDice.KillStreaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.RollTheDice.DiceHandlers.Arena;
import com.github.r0306.RollTheDice.DiceHandlers.DelayCoolDown;
import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Plugin;

public class CarePackage extends Arena implements Listener, Colors
{
	
	static HashMap<Entity, Integer> ids = new HashMap<Entity, Integer>();
	static HashMap<Entity, Integer> ids2 = new HashMap<Entity, Integer>();
	static HashMap<Player, Float> exp = new HashMap<Player, Float>();
	static HashMap<Player, Location> openingPackage = new HashMap<Player, Location>();
	public static ArrayList<Location> packageDrops = new ArrayList<Location>();
	final long DELAY_TICKS = 100L;

	@EventHandler
	public void onThrow(ExpBottleEvent event)
	{
		
		ThrownExpBottle entity = event.getEntity();
		
		if (entity.getShooter() instanceof Player)
		{
			
			Player player = (Player) entity.getShooter();
			
			if (inMatch.contains(player))
			{

				carePackageSmoke(entity);
				
			}
			
		}
		
		event.setExperience(0);
		
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			
			if (event.getClickedBlock().getType() == Material.CHEST)
			{
				
				if (packageDrops.contains(event.getClickedBlock().getLocation()))
				{
	
					if (!exp.containsKey(player))
					{
					
						Inventory inventory = ((Chest) event.getClickedBlock().getState()).getBlockInventory();
						
						DelayCoolDown.scheduleDelayedChestOpen(player, DELAY_TICKS, inventory, false);
						
						player.sendMessage(gold + pluginName + daqua + "Opening care package...");
						
						registerOpeningChest(player, event.getClickedBlock().getLocation());
						
						registerOriginalExp(player);
						
						event.setCancelled(true);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (exp.containsKey(player))
			{
				
				DelayCoolDown.cancelChestOpen(player);
				restoreOriginalExp(player);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event)
	{

		if (event.getInventory().getHolder() instanceof Chest)
		{
			
			Chest chest = (Chest) event.getInventory().getHolder();
			
			if (chest.getInventory().firstEmpty() == 0)
			{
				
				chest.getLocation().getBlock().setType(Material.AIR);
				packageDrops.remove(chest.getLocation());
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (DelayCoolDown.isOpeningPackage(player))
		{
			
			if (playerIsFarAway(player, getPlayerOpeningChest(player)))
			{
			
				DelayCoolDown.cancelChestOpen(player);
				unRegisterOpeningPackage(player);
			
			}	
			
		}
		
	}
	
	public boolean playerIsFarAway(Player player, Location packageLocation)
	{
		
		Location location = player.getLocation();
		
		int diffX = Math.abs(location.getBlockX() - packageLocation.getBlockX());
		int diffY = Math.abs(location.getBlockY() - packageLocation.getBlockY());
		int diffZ = Math.abs(location.getBlockZ() - packageLocation.getBlockZ());
		
		return (diffX >= 3 && diffY >= 3 && diffZ >= 3);
		
	}
	
	public void carePackageSmoke(final ThrownExpBottle entity)
	{
		
		final Location location = entity.getLocation();
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{

			int counter = 0;
			
			@Override
			public void run() 
			{

				location.getWorld().playEffect(location.getBlock().getRelative(0, 8, 0).getLocation(), Effect.ENDER_SIGNAL, 500);
				
				location.getWorld().playEffect(location, Effect.EXTINGUISH, 30);
				
				for (int i = 0; i < 9; i ++)
				{
				
					for (int x = 0; x < 9; x ++)
					{

						Random rand = new Random();
						location.getWorld().playEffect(location.getBlock().getRelative(rand.nextInt(4) + 1, i, rand.nextInt(4) + 1).getLocation(), Effect.SMOKE, x, 500);
						location.getWorld().playEffect(location.getBlock().getRelative(rand.nextInt(4) + 1, i, rand.nextInt(4) + 1).getLocation(), Effect.SMOKE, x, 500);
						location.getWorld().playEffect(location.getBlock().getRelative(rand.nextInt(4) + 1, i, rand.nextInt(4) + 1).getLocation(), Effect.SMOKE, x, 500);
						location.getWorld().playEffect(location.getBlock().getRelative(rand.nextInt(4) + 1, i, rand.nextInt(4) + 1).getLocation(), Effect.SMOKE, x, 500);

					}
	
				}
								
				counter ++;
				
				if (counter == 20)
				{
					
					dropCarePackage(entity, location);
					
				}
				
				if (counter == 30)
				{
					
					cancelSmokeTask(entity);
					
				}
			
			}
			
		}, 10L, 10L);

		ids.put(entity, id);
		
	}
	
	public void dropCarePackage(final Entity entity, final Location location)
	{
		
		int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Plugin.getPlugin(), new Runnable()
		{
		
			Location loc = new Location(location.getWorld(), location.getBlockX(), 255, location.getBlockZ());
			Material material = loc.getBlock().getType();
			byte data = loc.getBlock().getData();

			@Override
			public void run() 
			{
			
				material = loc.getBlock().getType();
				data = loc.getBlock().getData();	
				loc.getBlock().setType(Material.CHEST);
				
				if (loc.getBlockY() != 255)
				{

					loc.getBlock().getRelative(0, 1, 0).setType(material);
					loc.getBlock().getRelative(0, 1, 0).setData(data);
					
				}

				loc = loc.subtract(0, 1, 0);
				
				if (loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.WATER && loc.getBlock().getType() != Material.STATIONARY_WATER && loc.getBlock().getType() != Material.LAVA && loc.getBlock().getType() != Material.STATIONARY_LAVA)
				{
					
					fillChest(loc.add(0, 1, 0));
					loc.getWorld().playEffect(loc, Effect.GHAST_SHOOT, 10);
					packageDrops.add(loc);
					cancelDropTask(entity);
					
				}
								
			}
			
		}, 1L, 1L);
		
		ids2.put(entity, id);
		
	}
	
	public void cancelSmokeTask(Entity entity)
	{
		
		Bukkit.getScheduler().cancelTask(ids.get(entity));
		ids.remove(entity);
		
	}
	
	public void cancelDropTask(Entity entity)
	{
		
		Bukkit.getScheduler().cancelTask(ids2.get(entity));
		ids2.remove(entity);
	
	}
	
	public void registerOpeningChest(Player player, Location chestLocation)
	{
		
		openingPackage.put(player, chestLocation);
		
	}
	
	public void unRegisterOpeningPackage(Player player)
	{
	
		if (openingPackage.containsKey(player))
		{
		
			openingPackage.remove(player);
		
		}
			
	}
	
	public Location getPlayerOpeningChest(Player player)
	{
	
		return (openingPackage.containsKey(player)) ? openingPackage.get(player) : null;
	
	}
	
	public void restoreOriginalExp(Player player)
	{
		
		if (exp.containsKey(player))
		{
			
			player.setExp(exp.get(player));
			exp.remove(player);
			
		}
		
	}
	
	public void registerOriginalExp(Player player)
	{
		
		if (!exp.containsKey(player))
		{
			
			exp.put(player, exp.get(player));
			
			player.setExp(0F);
			
		}
		
	}
	
	public void fillChest(Location location)
	{
		
		if (location.getBlock().getType() == Material.CHEST)
		{
			
			Chest chest = (Chest) location.getBlock().getState();
			
			chest.getBlockInventory().setContents(randomItemStack());
			
		}
		
	}
	
	public ItemStack[] randomItemStack()
	{
		
		Random random = new Random();
		
		ItemStack[] items = new ItemStack[random.nextInt(20) + 6];
		
		for (int i = 0; i < items.length; i ++)
		{
			
			int number = random.nextInt(100) + 1;
			
			if (number >= 1 && number < 55)
			{
				
				items[i] = new ItemStack(getRandomCarePackageDropsItems(), random.nextInt(30) + 1);
				
			}
			else if (number >= 55 && number < 65)
			{
				
				items[i] = new ItemStack(getRandomCarePackageDropsTool(), 1);
				
			}
			else if (number >= 65 && number < 75)
			{
				
				items[i] = new ItemStack(getRandomCarePackageDropsArmor(), 1);
				
			}
			else if (number >= 75 && number < 95)
			{
				
				items[i] = new ItemStack(getRandomCarePackageDropsFood(), random.nextInt(10) + 1);
				
			}
			else
			{
				
				items[i] = getRandomCarePackageDropsExclusive();
				
			}
			
		}
		
		return items;
		
	}
	
	public Material getRandomCarePackageDropsFood()
	{
		
		Material[] materials = {Material.APPLE, Material.BREAD, Material.CAKE, Material.COOKED_BEEF, Material.COOKED_CHICKEN,
								Material.COOKED_BEEF, Material.COOKED_FISH, Material.COOKIE, Material.MELON,
								Material.MUSHROOM_SOUP, Material.PORK, Material.RAW_BEEF, Material.RAW_CHICKEN, Material.RAW_FISH}; 
		
		Random random = new Random();
		
		return materials[random.nextInt(materials.length)];		
	
	}
	
	public Material getRandomCarePackageDropsTool()
	{
		
		Material[] materials = {Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD,
								Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD,
								Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_SWORD,
								Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD,
								Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD, Material.BOW};
		
		Random random = new Random();
		
		return materials[random.nextInt(materials.length)];	
		
	}
	
	public Material getRandomCarePackageDropsArmor()
	{
		
		Material[] materials = {Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS,
								Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS,
								Material.IRON_BOOTS, Material.IRON_CHESTPLATE, Material.IRON_HELMET, Material.IRON_LEGGINGS,
								Material.GOLD_BOOTS, Material.GOLD_CHESTPLATE, Material.GOLD_HELMET, Material.GOLD_LEGGINGS,
								Material.DIAMOND_BOOTS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET, Material.DIAMOND_LEGGINGS};
		
		Random random = new Random();
		
		return materials[random.nextInt(materials.length)];	
		
	}
	
	public Material getRandomCarePackageDropsItems()
	{
		
		Material[] materials = {Material.ARROW, Material.BLAZE_POWDER, Material.BONE, Material.BUCKET, Material.CAULDRON, Material.COAL, Material.DISPENSER,
								Material.ENCHANTMENT_TABLE, Material.EYE_OF_ENDER, Material.FEATHER, Material.FISHING_ROD, Material.FLINT_AND_STEEL, Material.FURNACE, Material.GHAST_TEAR,
								Material.GLOWSTONE_DUST, Material.INK_SACK, Material.LADDER, Material.LEVER, Material.LOG, Material.MAGMA_CREAM, Material.MILK_BUCKET,
								Material.PISTON_BASE, Material.POWERED_RAIL, Material.RAILS, Material.REDSTONE, Material.REDSTONE_TORCH_OFF, Material.SAPLING, Material.SHEARS,
								Material.SNOW_BALL, Material.STICK, Material.STONE_BUTTON, Material.SULPHUR, Material.STRING, Material.TORCH, Material.WATCH, Material.WEB,
								Material.WORKBENCH};
		
		Random random = new Random();
		
		return materials[random.nextInt(materials.length)];	
		
	}
	
	public ItemStack getRandomCarePackageDropsExclusive()
	{
		
		ItemStack item = null;
		
		Material[] materials = {Material.BEDROCK, Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_SPADE, Material.GOLDEN_APPLE,
								Material.DIAMOND_HELMET, Material.DIAMOND_BOOTS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS,
								Material.TNT, Material.EXP_BOTTLE, Material.OBSIDIAN, Material.ENDER_PORTAL};
		
		Random random = new Random();
		
		Material material = materials[random.nextInt(materials.length)];
		
		if (material == Material.DIAMOND_SWORD)
		{
			
			item = new ItemStack(material, 1);
			item.addEnchantment(Enchantment.KNOCKBACK, 2);
			item.addEnchantment(Enchantment.FIRE_ASPECT, 1);
			item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			
		}
		else if (material == Material.DIAMOND_AXE || material == Material.DIAMOND_PICKAXE || material == Material.DIAMOND_SPADE)
		{
			
			item = new ItemStack(material, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
			
		}
		else if (material == Material.DIAMOND_BOOTS || material == Material.DIAMOND_CHESTPLATE || material == Material.DIAMOND_HELMET || material == Material.DIAMOND_LEGGINGS)
		{
			
			item = new ItemStack(material, 1);
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 2);
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
			
		}
		else
		{
			
			item = new ItemStack(material, random.nextInt(5) + 1);
			
		}
		
		return item;		
		
	}
	
}
