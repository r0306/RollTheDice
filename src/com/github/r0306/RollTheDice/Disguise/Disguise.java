package com.github.r0306.RollTheDice.Disguise;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.server.DataWatcher;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet18ArmAnimation;
import net.minecraft.server.Packet201PlayerInfo;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet24MobSpawn;
import net.minecraft.server.Packet29DestroyEntity;
import net.minecraft.server.Packet32EntityLook;
import net.minecraft.server.Packet33RelEntityMoveLook;
import net.minecraft.server.Packet34EntityTeleport;
import net.minecraft.server.Packet35EntityHeadRotation;
import net.minecraft.server.Packet40EntityMetadata;
import net.minecraft.server.Packet5EntityEquipment;
import org.bukkit.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Disguise
{
	
	  public int entityID;
	  public LinkedList<String> data;
	  public MobType mob;
	  public DataWatcher metadata;
	  private int encposX;
	  private int encposY;
	  private int encposZ;
	  private boolean firstpos = true;
	  protected int nextID = -1947483648;
	  
	  public static ConcurrentHashMap<String, Disguise> disguiseDB = new ConcurrentHashMap<String, Disguise>();
	  public LinkedList<String> disguiseQuitters = new LinkedList<String>();
	  public static ConcurrentHashMap<Integer, String> disguiseIDs = new ConcurrentHashMap<Integer, String>();
	  public HashMap<String, String> customNick = new HashMap<String, String>();
	  
	  public Disguise()
	  {
		  
	  }
	  
	  public Disguise(int entityID, LinkedList<String> data, MobType mob)
	  {
	    this.entityID = entityID;
	    this.data = data;
	    this.mob = mob;

	    initializeData();

	  }
	  
	  public int getNextAvailableID()
	  {
	    
		  return this.nextID++;
	  
	  }

	  public Disguise(int entityID, String data, MobType mob)
	  {
	    this.entityID = entityID;
	    LinkedList<String> dt = new LinkedList<String>();
	    dt.addFirst(data);
	    this.data = dt;
	    this.mob = mob;

	    initializeData();

	  }

	  public Disguise(int entityID, MobType mob)
	  {
	    this.entityID = entityID;
	    this.data = null;
	    this.mob = mob;

	    initializeData();
	  }

	  public Disguise setEntityID(int entityID)
	  {
	    this.entityID = entityID;
	    return this;
	  }

	  public Disguise setData(LinkedList<String> data)
	  {
	    this.data = data;
	    initializeData();
	    return this;
	  }

	  public Disguise setSingleData(String data)
	  {
	    if (this.data == null) {
	      this.data = new LinkedList<String>();
	    }
	    this.data.clear();
	    this.data.addFirst(data);
	    this.metadata = new DataWatcher();
	    initializeData();

	    return this;
	  }

	  public Disguise addSingleData(String data)
	  {
	    if (this.data == null) {
	      this.data = new LinkedList<String>();
	    }
	    if (!this.data.contains(data)) {
	      this.data.add(data);
	    }
	    initializeData();

	    return this;
	  }

	  public Disguise setMob(MobType mob)
	  {
	    this.mob = mob;
	    return this;
	  }

	  public void initializeData() {
	    this.metadata = new DataWatcher();
	    this.metadata.a(0, Byte.valueOf((byte) 0));
	    this.metadata.a(12, Integer.valueOf(0));
	    if ((this.mob == MobType.Sheep) || (this.mob == MobType.Pig) || (this.mob == MobType.Ghast) || (this.mob == MobType.Enderman))
	      this.metadata.a(16, Byte.valueOf((byte) 0));
	    else if ((this.mob == MobType.Slime) || (this.mob == MobType.MagmaCube))
	      this.metadata.a(16, Byte.valueOf((byte) 3));
	    else if (this.mob == MobType.Villager) {
	      this.metadata.a(16, Integer.valueOf(0));
	    }

	    if ((this.mob == MobType.Creeper) || (this.mob == MobType.Enderman)) {
	      this.metadata.a(17, Byte.valueOf((byte) 0));
	    }
	    if (this.mob == MobType.Ocelot)
	      this.metadata.a(18, Byte.valueOf((byte) 0));
	  }

	  public Disguise clone()
	  {
	    return new Disguise(this.entityID, this.data, this.mob);
	  }

	  public boolean equals(Disguise other)
	  {
	    return (this.entityID == other.entityID) && (this.data.equals(other.data)) && (this.mob == other.mob);
	  }

	  public boolean isPlayer()
	  {
	    return (this.mob == null) && (!this.data.equals("$"));
	  }
	  
	  public void sendMovement(Player disguised, Player observer, Vector vector, Location to)
	  {

	      Disguise disguise = (Disguise)disguiseDB.get(disguised.getName());
	      MovementValues movement = disguise.getMovement(to);
	      
	      if ((movement.x < -128) || (movement.x > 128) || (movement.y < -128) || (movement.y > 128) || (movement.z < -128) || (movement.z > 128))
	      {

		    	Packet packet = disguise.getEntityTeleportPacket(to);
		       
		        if (observer == null)
		        {
		        	
		        	sendPacketToWorld(disguised.getWorld(), new Packet[] { packet });
		        	        
		        }
		        
		        else
		        {
		          
		        	((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet);
		        
		        }
		      
	      }
	      
	      else if ((movement.x == 0) && (movement.y == 0) && (movement.z == 0))
		  {
		  
	    	  Packet packet = disguise.getEntityLookPacket(to);
	          Packet packet2 = disguise.getHeadRotatePacket(to);
	        
	          if (observer == null)
	          {
	        	  
	        	  sendPacketToWorld(disguised.getWorld(), new Packet[] { packet, packet2 });
	          }
	          
	          else
	          {
	          
	        	  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet);  
	        	  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet2);
	          
	          }
	      
		  }
	      
	      else
	      {
	       
	    	  Packet packet = disguise.getEntityMoveLookPacket(to);
	          Packet packet2 = disguise.getHeadRotatePacket(to);
	       
	          if (observer == null)
	          {
	          
	        	  sendPacketToWorld(disguised.getWorld(), new Packet[] { packet, packet2 });
	        
	          } 
	          else 
	          {
	          
	        	  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet);
	          
	        	  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet2);
	        
	          }
		        
		  }
		      
	  }

	  public void sendPacketToWorld(World world, Packet[] packet)
	  {
	  
		  for (Player observer : world.getPlayers())
	      {  
	    
			  for (Packet p : packet)
	    	  {
	    		
				  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(p);
	    	  
	    	  }
	    
	   	  }

	  }   	
	  public String getColor()
	  {
	   
		  String[] colors = { "black", "blue", "brown", "cyan", "gray", "green", 
	      "lightblue", "lime", "magenta", "orange", "pink", "purple", "red", 
	      "silver", "white", "yellow", "sheared" };
	   
		  if (this.data != null)
		  {
	     
			  for (String color : colors)
			  {
	       
				  if (this.data.contains(color))
				  {
	        
					  return color;
	       
				  }
	     
			  }
	   
		  }
	   
		  return null;
	  
	  }

	  public String getSize()
	  {
	   
		  String[] sizes = { "tiny", "small", "big" };
	   
		  if (this.data != null)
		  {
	    
			  for (String size : sizes)
			  {
	      
				  if (this.data.contains(size))
				  {
	        
					  return size;
	       
				  }
	     
			  }
	  
		  }
	  
		  return null;
	  
	  }

	  public void setCrouch(boolean crouched)
	  {
	   
		  if (crouched)
		  {
	     
			  if (!this.data.contains("crouched"))
			  {
	       
				  this.data.add("crouched");
	     
			  }
	     
			  this.metadata.watch(0, Byte.valueOf((byte) 2));
	   
		  }
		  else
		  {
	      
			  if (this.data.contains("crouched"))
			  {
	       
				  this.data.remove("crouched");
	     
			  }
	     
			  this.metadata.watch(0, Byte.valueOf((byte) 0));
	   
		  }
	 
	  }
	 
	  public Player getPlayerFromDisguiseID(int id)
	  {
	    
		  if (disguiseIDs.containsKey(Integer.valueOf(id)))
		  {
	     
			  return Bukkit.getServer().getPlayer((String)disguiseIDs.get(Integer.valueOf(id)));
	   
		  }
	    
		  return null;
	 
	  }

	  public Byte getHolding()
	  {
	   
		  if (this.data != null)
		  {
	      
			  for (String one : this.data)
			  {
	       
				  if (one.startsWith("holding"))
				  {
	        
					  String[] parts = one.split(":");
	       
					  try 
					  {
	          
						  return Byte.valueOf(parts[1]);
	          
					  } catch (NumberFormatException e) {
	           
						  System.out.println("DisguiseCraft could not parse the byte of an Enderman holding block!");
	         
					  }
	       
				  }
	      
			  }
	   
		  }
	   
		  return null;
	 
	  }
	  
	  public byte degreeToByte(float degree)
	  {
	   
		  return (byte)(int)((int)degree * 256.0F / 360.0F);
	  
	  }
	  
	  public void unDisguisePlayer(Player player)
	  {
		   
		  String name = player.getName();
		   
		  if (disguiseDB.containsKey(name))
		  {
		   
			  if (this.customNick.containsKey(name)) 
			  {
		       
				  player.setDisplayName((String)this.customNick.get(name));
				  this.customNick.remove(name);
		     
			  }
			  else
			  {
		       
				  player.setDisplayName(name);
		     
			  }
		      
			  sendUnDisguise(player, null);
		      disguiseIDs.remove(Integer.valueOf(((Disguise)disguiseDB.get(player.getName())).entityID));
		      disguiseDB.remove(name);
		    
		  }
		  
	  }
	  
	  public void disguisePlayer(Player player, Disguise disguise)
	  {
		    
		  if (disguise.isPlayer())
		  {
		   
			  if ((!this.customNick.containsKey(player.getName())) && (!player.getName().equals(player.getDisplayName()))) 
			  {
		       
				  this.customNick.put(player.getName(), player.getDisplayName());
		      
			  }
		      
			  player.setDisplayName((String)disguise.data.getFirst());
		    
		  }
		 
		  disguiseDB.put(player.getName(), disguise); 
		  disguiseIDs.put(Integer.valueOf(disguise.entityID), player.getName());
		    
		  sendDisguise(player, null);
		 
	  }
	    
	  public void sendUnDisguise(Player disguised, Player observer)
	  {
		  
		  if (disguiseDB.containsKey(disguised.getName()))
		  {
		     
			  Disguise disguise = (Disguise)disguiseDB.get(disguised.getName());
		      Packet packet = disguise.getEntityDestroyPacket();
		      Packet packet2 = disguise.getPlayerInfoPacket(disguised, false);
		      if (observer == null)
		      {
		        
		    	  if (packet2 == null)
		    	  {
		          
		    		  undisguiseToWorld(disguised.getWorld(), disguised, new Packet[] { packet });
		    	  }
		    	  else
		    	  {
		          
		    		  undisguiseToWorld(disguised.getWorld(), disguised, new Packet[] { packet, packet2 });
		      
		    	  }
		      
		      }
		      else
		      {
		       
		    	  if (packet2 != null) 
		    	  {
		          
		    		  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet2);
		        
		    	  }
		        
		    	  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet);
		        
		    	  observer.showPlayer(disguised);
		      
		      }
		  
		  }
		
	  }
	  
	  public void halfUndisguiseAllToPlayer(Player observer)
	  {
		    
		  World world = observer.getWorld();
		    
		  for (String name : disguiseDB.keySet())
		  {
		    
			  Player disguised = Bukkit.getServer().getPlayer(name);
		    
			  if ((disguised == null) || (world != disguised.getWorld())) continue;
		      
			  observer.showPlayer(disguised);
		   
		  }
		  
	  }
	  
	  public void sendDisguise(Player disguised, Player observer)
	  {
		    
		  if (disguiseDB.containsKey(disguised.getName()))
		  {
		     
			  Disguise disguise = (Disguise)disguiseDB.get(disguised.getName());
		     
			  if (disguise.mob == null)
			  {
		       
				  if (disguise.data.equals("$"))
				  {
		          
					  if (observer == null)
					  {
		            
						  disguiseToWorld(disguised.getWorld(), disguised, null);
		          
					  }
					  else
					  {
						  
						  observer.hidePlayer(disguised);
		        
					  }
				  
				  }
		          else
		          {
		          
		        	  Packet packet = disguise.getPlayerSpawnPacket(disguised.getLocation(), (short)disguised.getItemInHand().getTypeId());
		        
		        	  Packet packet2 = disguise.getPlayerInfoPacket(disguised, true);
		         
		        	  if (observer == null)
		        	  {
		         
		        		  if (packet2 == null)
		        		  {
		              
		        			  disguiseToWorld(disguised.getWorld(), disguised, new Packet[] { packet });
		            
		        		  }
		        		  else
		        		  {
		              
		        			  disguiseToWorld(disguised.getWorld(), disguised, new Packet[] { packet, packet2 });
		          
		        		  }
		        	  
		        	  }
		        	  else
		        	  {
		             
		        		  observer.hidePlayer(disguised);
		           
		        		  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet);
		           
		        		  if (packet2 != null)
		        		  {
		        			  
		        			  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet2);
		        
		        		  }
		        
		        	  }
		      
		          }
				  
			  }
		      else
		      {
		      
		    	  Packet packet = disguise.getMobSpawnPacket(disguised.getLocation());
		       
		    	  if (observer == null)
		    	  {
		         
		    		  disguiseToWorld(disguised.getWorld(), disguised, new Packet[] { packet });
		        
		    	  }
		    	  else
		    	  {
		          
		    		  observer.hidePlayer(disguised);
			    	  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(packet);
		    		  
		    	  }
		        
		      }
		     
		  }
		   
	  }
	  
	  public void disguiseToWorld(World world, Player player, Packet[] packet)
	  {
	    
		  for (Player observer : world.getPlayers())
		  {
	      
			  if (observer != player)
			  {
	         
				  observer.hidePlayer(player);
	    	  
	    	      for (Packet p : packet)
	    	      {
	    		  
	    	    	  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(p);
	      
	    	      }
			  
			  }
	      
	      }
	  
	  }

	  public void undisguiseToWorld(World world, Player player, Packet[] packet)
	  {
	   
		  for (Player observer : world.getPlayers())
		  {
			
			  if (observer != player)
			  {
	       
				  for (Packet p : packet)
				  {
	         
					  ((CraftPlayer)observer).getHandle().netServerHandler.sendPacket(p);
	       
				  }
	       
				  observer.showPlayer(player);
	     
			  }
	
		  }
	  
	  }
	  
	  public void showWorldDisguises(Player observer)
	  {
	    
		  for (String disguisedName : disguiseDB.keySet())
		  {
	     
			  Player disguised = Bukkit.getServer().getPlayer(disguisedName);
	     
			  if ((disguised == null) || (disguised == observer) || (disguised.getWorld() != observer.getWorld())) continue;
	      	
			  this.sendDisguise(disguised, observer);
	   
		  }
	 
	  }


	  /*
	  public boolean hasPermission(Player player)
	  {
	    DisguiseCraft plugin = (DisguiseCraft)Bukkit.getServer().getPluginManager().getPlugin("DisguiseCraft");
	    if ((this.data != null) && (this.data.contains("burning")) && (!plugin.hasPermissions(player, "disguisecraft.burning"))) {
	      return false;
	    }
	    if (isPlayer()) {
	      if (!plugin.hasPermissions(player, "disguisecraft.player"))
	        return false;
	    }
	    else {
	      if (!plugin.hasPermissions(player, "disguisecraft.mob." + this.mob.name().toLowerCase())) {
	        return false;
	      }
	      if (this.data != null) {
	        for (String dat : this.data) {
	          if (dat.equalsIgnoreCase("crouched")) {
	            continue;
	          }
	          if (dat.startsWith("holding")) {
	            if (!plugin.hasPermissions(player, "disguisecraft.mob.enderman.hold")) {
	              return false;
	            }

	          }
	          else if ((getSize() != null) && (dat.equals(getSize()))) {
	            if (!plugin.hasPermissions(player, "disguisecraft.mob." + this.mob.name().toLowerCase() + ".size." + dat)) {
	              return false;
	            }

	          }
	          else if ((getColor() != null) && (dat.equals(getColor()))) {
	            if (!plugin.hasPermissions(player, "disguisecraft.mob." + this.mob.name().toLowerCase() + ".color." + dat)) {
	              return false;
	            }

	          }
	          else if ((dat.equalsIgnoreCase("tabby")) || (dat.equalsIgnoreCase("tuxedo")) || (dat.equalsIgnoreCase("siamese"))) {
	            if (!plugin.hasPermissions(player, "disguisecraft.mob." + this.mob.name().toLowerCase() + ".cat." + dat)) {
	              return false;
	            }

	          }
	          else if ((dat.equalsIgnoreCase("librarian")) || (dat.equalsIgnoreCase("priest")) || (dat.equalsIgnoreCase("blacksmith")) || (dat.equalsIgnoreCase("butcher"))) {
	            if (!plugin.hasPermissions(player, "disguisecraft.mob." + this.mob.name().toLowerCase() + ".occupation." + dat)) {
	              return false;
	            }

	          }
	          else if (!plugin.hasPermissions(player, "disguisecraft.mob." + this.mob.name().toLowerCase() + "." + dat)) {
	            return false;
	          }
	        }
	      }
	    }
	    return true;
	  }
	  */

	  public Packet24MobSpawn getMobSpawnPacket(Location loc)
	  {
	    if (this.mob != null) {
	      int x = MathHelper.floor(loc.getX() * 32.0D);
	      int y = MathHelper.floor(loc.getY() * 32.0D);
	      int z = MathHelper.floor(loc.getZ() * 32.0D);
	      if (this.firstpos) {
	        this.encposX = x;
	        this.encposY = y;
	        this.encposZ = z;
	        this.firstpos = false;
	      }
	      Packet24MobSpawn packet = new Packet24MobSpawn();
	      packet.a = this.entityID;
	      packet.b = this.mob.id;
	      packet.c = x;
	      packet.d = y;
	      packet.e = z;
	      packet.f = degreeToByte(loc.getYaw());
	      packet.g = degreeToByte(loc.getPitch());
	      packet.h = packet.f;
	      try {
	        Field metadataField = packet.getClass().getDeclaredField("i");
	        metadataField.setAccessible(true);
	        metadataField.set(packet, this.metadata);
	      } catch (Exception e) {
	        System.out.println("DisguiseCraft was unable to set the metadata for a " + this.mob.name() + " disguise!");
	        e.printStackTrace();
	      }

	      if (this.mob == MobType.EnderDragon) {
	        packet.f = (byte)(packet.f - 128);
	      }

	      if (this.mob == MobType.Chicken) {
	        packet.g = (byte)(packet.g * -1);
	      }
	      return packet;
	    }
	    return null;
	  }

	  public Packet20NamedEntitySpawn getPlayerSpawnPacket(Location loc, short item)
	  {
	    if ((this.mob == null) && (!this.data.equals("$"))) {
	      Packet20NamedEntitySpawn packet = new Packet20NamedEntitySpawn();
	      packet.a = this.entityID;
	      packet.b = ((String)this.data.getFirst());
	      int x = MathHelper.floor(loc.getX() * 32.0D);
	      int y = MathHelper.floor(loc.getY() * 32.0D);
	      int z = MathHelper.floor(loc.getZ() * 32.0D);
	      if (this.firstpos) {
	        this.encposX = x;
	        this.encposY = y;
	        this.encposZ = z;
	        this.firstpos = false;
	      }
	      packet.c = x;
	      packet.d = y;
	      packet.e = z;
	      packet.f = degreeToByte(loc.getYaw());
	      packet.g = degreeToByte(loc.getPitch());
	      packet.h = item;
	      return packet;
	    }
	    return null;
	  }

	  public Packet29DestroyEntity getEntityDestroyPacket()
	  {
	    return new Packet29DestroyEntity(this.entityID);
	  }

	  public Packet5EntityEquipment getEquipmentChangePacket(short slot, ItemStack item) {
	    if (isPlayer())
	    {
	      Packet5EntityEquipment packet;
	      if (item == null) {
	        packet = new Packet5EntityEquipment();
	        packet.a = this.entityID;
	        packet.b = slot;
	        packet.c = -1;
	        packet.d = 0;
	      } else {
	        packet = new Packet5EntityEquipment(this.entityID, slot, ((CraftItemStack)item).getHandle());
	      }
	      return packet;
	    }
	    return null;
	  }

	  public Packet32EntityLook getEntityLookPacket(Location loc)
	  {
	    Packet32EntityLook packet = new Packet32EntityLook();
	    packet.a = this.entityID;
	    packet.b = 0;
	    packet.c = 0;
	    packet.d = 0;
	    packet.e = degreeToByte(loc.getYaw());
	    packet.f = degreeToByte(loc.getPitch());

	    if (this.mob == MobType.EnderDragon) {
	      packet.e = (byte)(packet.e - 128);
	    }

	    if (this.mob == MobType.Chicken) {
	      packet.f = (byte)(packet.f * -1);
	    }
	    return packet;
	  }

	  public Packet33RelEntityMoveLook getEntityMoveLookPacket(Location look) {
	    Packet33RelEntityMoveLook packet = new Packet33RelEntityMoveLook();
	    packet.a = this.entityID;
	    MovementValues movement = getMovement(look);
	    this.encposX += movement.x;
	    this.encposY += movement.y;
	    this.encposZ += movement.z;
	    packet.b = (byte)movement.x;
	    packet.c = (byte)movement.y;
	    packet.d = (byte)movement.z;
	    packet.e = degreeToByte(look.getYaw());
	    packet.f = degreeToByte(look.getPitch());

	    if (this.mob == MobType.EnderDragon) {
	      packet.e = (byte)(packet.e - 128);
	    }

	    if (this.mob == MobType.Chicken) {
	      packet.f = (byte)(packet.f * -1);
	    }
	    return packet;
	  }

	  public Packet34EntityTeleport getEntityTeleportPacket(Location loc) {
	    Packet34EntityTeleport packet = new Packet34EntityTeleport();
	    packet.a = this.entityID;
	    int x = MathHelper.floor(32.0D * loc.getX());
	    int y = MathHelper.floor(32.0D * loc.getY());
	    int z = MathHelper.floor(32.0D * loc.getZ());
	    packet.b = x;
	    packet.c = y;
	    packet.d = z;
	    this.encposX = x;
	    this.encposY = y;
	    this.encposZ = z;
	    packet.e = degreeToByte(loc.getYaw());
	    packet.f = degreeToByte(loc.getPitch());

	    if (this.mob == MobType.EnderDragon) {
	      packet.e = (byte)(packet.e - 128);
	    }

	    if (this.mob == MobType.Chicken) {
	      packet.f = (byte)(packet.f * -1);
	    }
	    return packet;
	  }

	  public Packet40EntityMetadata getEntityMetadataPacket() {
	    Packet40EntityMetadata packet = new Packet40EntityMetadata();
	    packet.a = this.entityID;
	    try {
	      Field metadataField = packet.getClass().getDeclaredField("b");
	      metadataField.setAccessible(true);
	      metadataField.set(packet, this.metadata);
	    } catch (Exception e) {
	      System.out.println("DisguiseCraft was unable to set the metadata for a " + this.mob.name() + " disguise!");
	      e.printStackTrace();
	    }
	    return packet;
	  }
	  
	  public Packet201PlayerInfo getPlayerInfoPacket(Player player, boolean show) {
	    Packet201PlayerInfo packet = null;
	    if (isPlayer())
	    {
	      int ping;
	      if (show)
	        ping = ((CraftPlayer)player).getHandle().ping;
	      else {
	        ping = 9999;
	      }
	      packet = new Packet201PlayerInfo((String)this.data.getFirst(), show, ping);
	    }
	    return packet;
	  }

	  public MovementValues getMovement(Location to) {
	    int x = MathHelper.floor(to.getX() * 32.0D);
	    int y = MathHelper.floor(to.getY() * 32.0D);
	    int z = MathHelper.floor(to.getZ() * 32.0D);
	    int diffx = x - this.encposX;
	    int diffy = y - this.encposY;
	    int diffz = z - this.encposZ;
	    return new MovementValues(diffx, diffy, diffz, degreeToByte(to.getYaw()), degreeToByte(to.getPitch()));
	  }

	  public Packet35EntityHeadRotation getHeadRotatePacket(Location loc) {
	    return new Packet35EntityHeadRotation(this.entityID, degreeToByte(loc.getYaw()));
	  }

	  public Packet18ArmAnimation getAnimationPacket(int animation)
	  {
	    Packet18ArmAnimation packet = new Packet18ArmAnimation();
	    packet.a = this.entityID;
	    packet.b = (byte)animation;
	    return packet;
	  }

	  public Packet40EntityMetadata getMetadataPacket() {
	    return new Packet40EntityMetadata(this.entityID, this.metadata);
	  }

	  public static enum MobType
	  {
	    Blaze(61), 
	    CaveSpider(59), 
	    Chicken(93), 
	    Cow(92), 
	    Creeper(50), 
	    EnderDragon(63), 
	    Enderman(58), 
	    Ghast(56), 
	    Giant(53), 
	    IronGolem(99), 
	    MagmaCube(62), 
	    MushroomCow(96), 
	    Ocelot(98), 
	    Pig(90), 
	    PigZombie(57), 
	    Sheep(91), 
	    Silverfish(60), 
	    Skeleton(51), 
	    Slime(55), 
	    Snowman(97), 
	    Spider(52), 
	    Squid(94), 
	    Villager(120), 
	    Wolf(95), 
	    Zombie(54);

	    public final byte id;
	    public static String subTypes;

	    static
	    {
	      subTypes = "player, baby, black, blue, brown, cyan, gray, green, lightblue, lime, magenta, orange, pink, purple, red, silver, white, yellow, sheared, charged, tiny, small, big, tamed, aggressive, tabby, tuxedo, siamese, burning, saddled, librarian, priest, blacksmith, butcher, nopickup";
	    }

	    private MobType(int i)
	    {
	      this.id = (byte)i;
	    }

	    public boolean isSubclass(Class<?> cls)
	    {
	      try
	      {
	        return cls.isAssignableFrom(Class.forName("org.bukkit.entity." + name()));
	      } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	      }
	      return false;
	    }

	    public static MobType fromString(String text)
	    {
	      for (MobType m : values()) {
	        if (text.equalsIgnoreCase(m.name())) {
	          return m;
	        }
	      }
	      return null;
	    }
	  }
}
