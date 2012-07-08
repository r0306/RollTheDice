package com.github.r0306.RollTheDice.Disguise;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerDisguiseHitEvent extends PlayerEvent
{
  private static final HandlerList handlers = new HandlerList();
  private final int target;
  private final boolean action;

  public PlayerDisguiseHitEvent(Player player, int target, int action)
  {
    super(player);
    this.target = target;
    this.action = (action == 1);
  }

  public int getTarget()
  {
    return this.target;
  }

  public boolean getAction()
  {
    return this.action;
  }

  public HandlerList getHandlers()
  {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}