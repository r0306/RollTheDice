package com.github.r0306.RollTheDice.DiceHandlers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.EntityFishingHook;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftArrow;
import org.bukkit.craftbukkit.entity.CraftFish;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.util.Vector;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _37 extends Arena implements Listener
{

	static HashMap<String, Entity> hooks = new HashMap<String, Entity>();
	
	@EventHandler
	public void onGrapple(PlayerFishEvent event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		
		final Player player = event.getPlayer();
		
		//if (isIn(player, 37))
		{
			
			if (event.getState() == State.IN_GROUND)
			{
				
				if (hooks.containsKey(player.getName()))
				{
					
					Block block = getHookBlock(hooks.get(player.getName()));
					
					System.out.println(block.getType());
					
					if (block.getType() == Material.AIR)
					{
						
						player.sendBlockChange(hooks.get(player.getName()).getLocation(), Material.LADDER.getId(), getFace(hooks.get(player.getName())));

						player.setVelocity(hooks.get(player.getName()).getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(new Vector(2, 2.5, 2)));
						
						//player.teleport(block.getLocation());
						
					}
					
				}
				
			}
			
		}
		
	}
		
	@EventHandler
	public void onHit(ProjectileLaunchEvent event)
	{
		
		if (event.getEntityType() == EntityType.FISHING_HOOK)
		{
			
			Projectile hook = (Projectile) event.getEntity();
						
			if (hook.getShooter() instanceof Player)
			{
				
				Player player = (Player) hook.getShooter();
				
				//if (isIn(player, 37))
				{
					
					hook.setVelocity(hook.getVelocity().normalize().multiply(2));
					hooks.put(player.getName(), hook);
					
				}
				
			}
			
		}
		
	}
		
	public byte getFace(Entity hook)
	{
		
		Block block = hook.getLocation().getBlock();
		
		for (BlockFace f : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH})
		{
			
			if (!getTransparentMaterials().contains(block.getRelative(f).getType()))
			{
				
				return getLadderFace(f);
				
			}
			
		}
		
		return (byte) 4;
		
	}
	
	public static List<Material> getTransparentMaterials()
	{
		
		Material[] materials = {Material.AIR, Material.BED, Material.BED_BLOCK, Material.BREWING_STAND, Material.BROWN_MUSHROOM,
				   				Material.BURNING_FURNACE, Material.CACTUS, Material.CAKE_BLOCK, Material.CAULDRON, Material.CHEST,
				   				Material.DEAD_BUSH, Material.DETECTOR_RAIL, Material.DIODE, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON,
				   				Material.DISPENSER, Material.DRAGON_EGG, Material.EGG, Material.ENCHANTMENT_TABLE, Material.ENDER_PORTAL, Material.ENDER_PORTAL_FRAME,
				   				Material.ENDER_STONE, Material.FENCE, Material.FENCE_GATE, Material.FIRE, Material.FURNACE, Material.GLASS, Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2,
				   				Material.IRON_DOOR_BLOCK, Material.IRON_FENCE, Material.JUKEBOX, Material.LADDER, Material.LAVA, Material.LEVER, Material.LONG_GRASS, Material.MELON_STEM,
				   				Material.MOB_SPAWNER, Material.NETHER_FENCE, Material.NETHER_STALK, Material.NETHER_WARTS, Material.PAINTING, Material.PISTON_BASE, Material.PISTON_EXTENSION,
				   				Material.PISTON_MOVING_PIECE, Material.PISTON_STICKY_BASE, Material.PORTAL, Material.PUMPKIN_STEM, Material.RED_ROSE, Material.RED_MUSHROOM, Material.SAPLING, Material.SIGN, Material.SIGN_POST, Material.STATIONARY_LAVA,
				   				Material.SNOW, Material.STATIONARY_WATER, Material.STONE_BUTTON, Material.SUGAR_CANE_BLOCK, Material.THIN_GLASS, Material.TNT, Material.TORCH, Material.TRAP_DOOR, Material.VINE, Material.WALL_SIGN,
				   				Material.WATER, Material.WEB, Material.WHEAT, Material.WOODEN_DOOR, Material.WORKBENCH, Material.YELLOW_FLOWER};
		
		return Arrays.asList(materials);
		
	}
	
	public byte getLadderFace(BlockFace face)
	{
		
		if (face == BlockFace.NORTH) return (byte) 4;
		else if (face == BlockFace.EAST) return (byte) 2;
		else if (face == BlockFace.WEST) return (byte) 3;
		else return (byte) 5;
		
	}
	
	public Block getHookBlock(final Entity hook) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
							
		World world = hook.getWorld();

        EntityFishingHook entityHook = ((CraftFish)hook).getHandle();

        Field fieldX = net.minecraft.server.EntityFishingHook.class.getDeclaredField("d");
        Field fieldY = net.minecraft.server.EntityFishingHook.class.getDeclaredField("e");
        Field fieldZ = net.minecraft.server.EntityFishingHook.class.getDeclaredField("f");

        fieldX.setAccessible(true);
        fieldY.setAccessible(true);
        fieldZ.setAccessible(true);

        int x = fieldX.getInt(entityHook);
        int y = fieldY.getInt(entityHook);
        int z = fieldZ.getInt(entityHook);

        return world.getBlockAt(x, y, z);
		
	}
	
}
