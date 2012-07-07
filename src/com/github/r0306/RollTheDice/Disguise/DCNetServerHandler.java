package com.github.r0306.RollTheDice.Disguise;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet7UseEntity;
import net.minecraft.server.WorldServer;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.plugin.PluginManager;

public class DCNetServerHandler extends NetServerHandler
{
  private MinecraftServer minecraftServer;
  private final CraftServer server;

  public DCNetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer)
  {
    super(minecraftserver, networkmanager, entityplayer);
    this.minecraftServer = minecraftserver;
    this.server = minecraftserver.server;
  }

  public void a(Packet7UseEntity packet7useentity)
  {
    super.a(packet7useentity);

    if (this.minecraftServer.getWorldServer(this.player.dimension).getEntity(packet7useentity.target) == null) {
      PlayerInvalidInteractEvent event = new PlayerInvalidInteractEvent(getPlayer(), packet7useentity.target, packet7useentity.action);
      this.server.getPluginManager().callEvent(event);
    }
  }
}