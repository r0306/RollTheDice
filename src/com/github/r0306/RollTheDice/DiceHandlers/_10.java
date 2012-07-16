package com.github.r0306.RollTheDice.DiceHandlers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.RollTheDice.Util.Colors;
import com.github.r0306.RollTheDice.Util.Plugin;

public class _10 extends Arena implements Listener, Colors
{
	
	static HashMap<Entity, String> explosiveArrows = new HashMap<Entity, String>();
	static List<Block> entityDamage = new ArrayList<Block>();
	final Long DELAY_TICKS = 120L;

	@EventHandler
	public void onShoot(EntityShootBowEvent event)
	{
		
		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (isIn(player, 10))
			{
				
				if (player.getExp() == 1F)
				{
					
					explosiveArrows.put(event.getProjectile(), player.getName());
					DelayCoolDown.scheduleDelayedCoolDown(player, DELAY_TICKS);
					
				}
				else
				{
					
					player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
					event.setCancelled(true);
			
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onContact(ProjectileHitEvent event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{

		if (event.getEntity() instanceof Arrow)
		{
			
			final Entity arrow = event.getEntity();
			
			if (explosiveArrows.containsKey(arrow))
			{
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
				{
							
					   public void run()
					   {
					    
						   try
						   {
								
							   if (getArrowBlock(arrow).getType() != Material.AIR)
							   {
									   
								   scheduleDelayedExplosionBlock(arrow);
									   
							   }
						
						   } catch (NoSuchFieldException e) {
	
							e.printStackTrace();
				
						   } catch (SecurityException e) {

							e.printStackTrace();
						
						   } catch (IllegalArgumentException e) {

							e.printStackTrace();
						
						   } catch (IllegalAccessException e) {

							e.printStackTrace();
						
						   }
					
					   }
					
				}, 0L);
				
				entityDamage.remove(arrow);
			}
			
		}
	
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{

		if (event.getEntity() instanceof Player)
		{
			
			Player player = (Player) event.getEntity();
			
			if (event.getDamager() instanceof Arrow)
			{
				
				Entity arrow = event.getDamager();
				
				if (explosiveArrows.containsKey(arrow))
				{
					
					event.setDamage(2);
					player.sendMessage(gold + pluginName + red + "STUCK!!!");
					((Player)((Arrow) arrow).getShooter()).sendMessage(gold + pluginName + green + "STUCK!!!");
					scheduleDelayedExplosionEntity(player, arrow);
					
				}
				
			}
			
		}
		else
		{
			
			if (event.getEntity() instanceof LivingEntity)
			{
				
				Entity entity = event.getEntity();
				
				if (event.getDamager() instanceof Arrow)
				{
					
					Entity arrow = event.getDamager();
					
					if (explosiveArrows.containsKey(arrow))
					{
						
						((Player)((Arrow) arrow).getShooter()).sendMessage(gold + pluginName + green + "STUCK!!!");
						scheduleDelayedExplosionEntity(entity, arrow);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public void scheduleDelayedExplosionBlock(final Entity arrow)
	{
		
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{
					
			   public void run()
			   {
			    
				   arrow.getLocation().getWorld().createExplosion(arrow.getLocation(), 1F);
				   arrow.remove();
				   explosiveArrows.remove(arrow);
			
			   }
			
		}, 60L);
		
	}
	
	public void scheduleDelayedExplosionEntity(final Entity entity, final Entity arrow)
	{
		
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{
					
			   public void run()
			   {
			    
				   if (!entity.isDead())
				   {
					
					   arrow.getLocation().getWorld().createExplosion(entity.getLocation(), 1F);
			
				   }
				   
				   arrow.remove();
				   explosiveArrows.remove(arrow);
					   
			   }
			
		}, 60L);
		
	}
	
	public Block getArrowBlock(final Entity arrow) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
							
		World world = arrow.getWorld();

        net.minecraft.server.EntityArrow entityArrow = ((CraftArrow)arrow).getHandle();

        Field fieldX = net.minecraft.server.EntityArrow.class.getDeclaredField("e");
        Field fieldY = net.minecraft.server.EntityArrow.class.getDeclaredField("f");
        Field fieldZ = net.minecraft.server.EntityArrow.class.getDeclaredField("g");

        fieldX.setAccessible(true);
        fieldY.setAccessible(true);
        fieldZ.setAccessible(true);

        int x = fieldX.getInt(entityArrow);
        int y = fieldY.getInt(entityArrow);
        int z = fieldZ.getInt(entityArrow);

        return world.getBlockAt(x, y, z);
		
	}
		
		
}
