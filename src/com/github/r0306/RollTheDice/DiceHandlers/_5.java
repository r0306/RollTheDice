package com.github.r0306.RollTheDice.DiceHandlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.r0306.RollTheDice.Disguise.HandleDisguise;
import com.github.r0306.RollTheDice.Util.Colors;

public class _5 extends Arena implements Listener, Colors
{

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 5))
		{
			
			if (HandleDisguise.getLivingEntities().contains(event.getRightClicked().getType()))
			{
				
				HandleDisguise.disguisePlayerAsEntity(player, event.getRightClicked().getType());
				
				player.sendMessage(gold + pluginName + daqua + "You have disguised as " + yellow + event.getRightClicked().getType().getName() + daqua + ".");
			
			}
	
		}
		
	}
	
	@EventHandler
	public void onInteractAir(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (isIn(player, 5))
		{
			
			if (event.getAction() == Action.RIGHT_CLICK_AIR)
			{
				
				if (player.getItemInHand().getType() == Material.AIR)
				{
					
					HandleDisguise.unDisguisePlayer(player);
					
					player.sendMessage(gold + pluginName + daqua + "You have undisguised.");
				
				}
				
			}
			
		}
		
	}
		
}
	

