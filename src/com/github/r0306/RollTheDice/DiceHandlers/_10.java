package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.github.r0306.RollTheDice.Util.Plugin;

public class _10 extends Arena implements Listener
{
	
	static HashMap<Entity, Player> explosiveArrows = new HashMap<Entity, Player>();
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
					
					explosiveArrows.put(event.getProjectile(), player);
					scheduleDelayedExplosion(event.getProjectile());
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
	public void onContact(ProjectileHitEvent event)
	{
		
		if (event.getEntity() instanceof Arrow)
		{
			
			Entity arrow = event.getEntity();
			
			if (explosiveArrows.containsKey(arrow))
			{
				
				
				
			}
			
		}
	
	}
	
	public void scheduleDelayedExplosion(final Entity arrow)
	{
		
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{
					
			   public void run()
			   {
			    
				   arrow.getLocation().getWorld().createExplosion(arrow.getLocation(), 1F);
				   explosiveArrows.remove(arrow);
			
			   }
			
		}, 60L);
		
	}
	
}
