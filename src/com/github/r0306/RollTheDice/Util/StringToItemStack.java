package com.github.r0306.RollTheDice.Util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class StringToItemStack
{

	public static Material toMaterial(String s)
	{
		
		if (s.equalsIgnoreCase("apple")) return Material.APPLE;
		else if (s.equalsIgnoreCase("arrow")) return Material.ARROW;
		else if (s.equalsIgnoreCase("bed")) return Material.BED;
		else if (s.equalsIgnoreCase("bedrock")) return Material.BEDROCK;
		else if (s.equalsIgnoreCase("blaze powder")) return Material.BLAZE_POWDER;
		else if (s.equalsIgnoreCase("blaze rod")) return Material.BLAZE_ROD;
		else if (s.equalsIgnoreCase("boat")) return Material.BOAT;
		else if (s.equalsIgnoreCase("bone")) return Material.BONE;
		else if (s.equalsIgnoreCase("book")) return Material.BOOK;
		else if (s.equalsIgnoreCase("bookshelf")) return Material.BOOKSHELF;
		else if (s.equalsIgnoreCase("bow")) return Material.BOW;
		else if (s.equalsIgnoreCase("bowl")) return Material.BOWL;
		else if (s.equalsIgnoreCase("bread")) return Material.BREAD;
		else if (s.equalsIgnoreCase("brewing stand")) return Material.BREWING_STAND_ITEM;
		else if (s.equalsIgnoreCase("brick")) return Material.BRICK;
		else if (s.equalsIgnoreCase("brick stairs")) return Material.BRICK_STAIRS;
		else if (s.equalsIgnoreCase("brown mushroom")) return Material.BROWN_MUSHROOM;
		else if (s.equalsIgnoreCase("bucket")) return Material.BUCKET;
		else if (s.equalsIgnoreCase("cactus")) return Material.CACTUS;
		else if (s.equalsIgnoreCase("cake")) return Material.CAKE;
		else if (s.equalsIgnoreCase("cauldron")) return Material.CAULDRON_ITEM;
		else if (s.equalsIgnoreCase("chainmail boots")) return Material.CHAINMAIL_BOOTS;
		else if (s.equalsIgnoreCase("chainmail chestplate")) return Material.CHAINMAIL_CHESTPLATE;
		else if (s.equalsIgnoreCase("chainmail helmet")) return Material.CHAINMAIL_HELMET;
		else if (s.equalsIgnoreCase("chainmail leggings")) return Material.CHAINMAIL_LEGGINGS;
		else if (s.equalsIgnoreCase("chest")) return Material.CHEST;
		else if (s.equalsIgnoreCase("clay ball")) return Material.CLAY_BALL;
		else if (s.equalsIgnoreCase("clay brick")) return Material.CLAY_BRICK;
		else if (s.equalsIgnoreCase("coal")) return Material.COAL;
		else if (s.equalsIgnoreCase("cobblestone")) return Material.COBBLESTONE;
		else if (s.equalsIgnoreCase("cobblestone stairs")) return Material.COBBLESTONE_STAIRS;
		else if (s.equalsIgnoreCase("compass")) return Material.COMPASS;
		else if (s.equalsIgnoreCase("cooked beef")) return Material.COOKED_BEEF;
		else if (s.equalsIgnoreCase("cooked chicken")) return Material.COOKED_CHICKEN;
		else if (s.equalsIgnoreCase("cooked fish")) return Material.COOKED_FISH;
		else if (s.equalsIgnoreCase("cookie")) return Material.COOKIE;
		else if (s.equalsIgnoreCase("detector rail")) return Material.DETECTOR_RAIL;
		else if (s.equalsIgnoreCase("diamond")) return Material.DIAMOND;
		else if (s.equalsIgnoreCase("diamond axe")) return Material.DIAMOND_AXE;
		else if (s.equalsIgnoreCase("diamond block")) return Material.DIAMOND_BLOCK;
		else if (s.equalsIgnoreCase("diamond boots")) return Material.DIAMOND_BOOTS;
		else if (s.equalsIgnoreCase("diamond chestplate")) return Material.DIAMOND_CHESTPLATE;
		else if (s.equalsIgnoreCase("diamond helmet")) return Material.DIAMOND_HELMET;
		else if (s.equalsIgnoreCase("diamond hoe")) return Material.DIAMOND_HOE;
		else if (s.equalsIgnoreCase("diamond leggings")) return Material.DIAMOND_LEGGINGS;
		else if (s.equalsIgnoreCase("diamond pickaxe")) return Material.DIAMOND_PICKAXE;
		else if (s.equalsIgnoreCase("diamond shovel")) return Material.DIAMOND_SPADE;
		else if (s.equalsIgnoreCase("diamond sword")) return Material.DIAMOND_SWORD;
		else if (s.equalsIgnoreCase("dirt")) return Material.DIRT;
		else if (s.equalsIgnoreCase("dispenser")) return Material.DISPENSER;
		else if (s.equalsIgnoreCase("double step")) return Material.DOUBLE_STEP;
		else if (s.equalsIgnoreCase("dragon egg")) return Material.DRAGON_EGG;
		else if (s.equalsIgnoreCase("egg")) return Material.EGG;
		else if (s.equalsIgnoreCase("enchantment table")) return Material.ENCHANTMENT_TABLE;
		else if (s.equalsIgnoreCase("ender pearl")) return Material.ENDER_PEARL;
		else if (s.equalsIgnoreCase("exp bottle")) return Material.EXP_BOTTLE;
		else if (s.equalsIgnoreCase("eye of ender")) return Material.EYE_OF_ENDER;
		else if (s.equalsIgnoreCase("feather")) return Material.FEATHER;
		else if (s.equalsIgnoreCase("fence")) return Material.FENCE;
		else if (s.equalsIgnoreCase("fence gate")) return Material.FENCE_GATE;
		else if (s.equalsIgnoreCase("fermented spider eye")) return Material.FERMENTED_SPIDER_EYE;
		else if (s.equalsIgnoreCase("fire")) return Material.FIRE;
		else if (s.equalsIgnoreCase("fire charge")) return Material.FIREBALL;
		else if (s.equalsIgnoreCase("fishing rod")) return Material.FISHING_ROD;
		else if (s.equalsIgnoreCase("flint")) return Material.FLINT;
		else if (s.equalsIgnoreCase("flint and steel")) return Material.FLINT_AND_STEEL;
		else if (s.equalsIgnoreCase("furnace")) return Material.FURNACE;
		else if (s.equalsIgnoreCase("ghast tear")) return Material.GHAST_TEAR;
		else if (s.equalsIgnoreCase("glass block")) return Material.GLASS;
		else if (s.equalsIgnoreCase("glass bottle")) return Material.GLASS_BOTTLE;
		else if (s.equalsIgnoreCase("glowstone")) return Material.GLOWSTONE;
		else if (s.equalsIgnoreCase("glowstone dust")) return Material.GLOWSTONE_DUST;
		else if (s.equalsIgnoreCase("gold axe")) return Material.GOLD_AXE;
		else if (s.equalsIgnoreCase("gold block")) return Material.GOLD_BLOCK;
		else if (s.equalsIgnoreCase("gold boots")) return Material.GOLD_BOOTS;
		else if (s.equalsIgnoreCase("gold chestplate")) return Material.GOLD_CHESTPLATE;
		else if (s.equalsIgnoreCase("gold helmet")) return Material.GOLD_HELMET;
		else if (s.equalsIgnoreCase("gold hoe")) return Material.GOLD_HOE;
		else if (s.equalsIgnoreCase("gold ingot")) return Material.GOLD_INGOT;
		else if (s.equalsIgnoreCase("gold leggings")) return Material.GOLD_LEGGINGS;
		else if (s.equalsIgnoreCase("gold nugget")) return Material.GOLD_NUGGET;
		else if (s.equalsIgnoreCase("gold pickaxe")) return Material.GOLD_PICKAXE;
		else if (s.equalsIgnoreCase("gold record")) return Material.GOLD_RECORD;
		else if (s.equalsIgnoreCase("gold shovel")) return Material.GOLD_SPADE;
		else if (s.equalsIgnoreCase("gold sword")) return Material.GOLD_SWORD;
		else if (s.equalsIgnoreCase("golden apple")) return Material.GOLDEN_APPLE;
		else if (s.equalsIgnoreCase("grass")) return Material.GRASS;
		else if (s.equalsIgnoreCase("gravel")) return Material.GRAVEL;
		else if (s.equalsIgnoreCase("green record")) return Material.GREEN_RECORD;
		else if (s.equalsIgnoreCase("grilled pork")) return Material.GRILLED_PORK;
		else if (s.equalsIgnoreCase("ice")) return Material.ICE;
		else if (s.equalsIgnoreCase("ink sack")) return Material.INK_SACK;
		else if (s.equalsIgnoreCase("iron axe")) return Material.IRON_AXE;
		else if (s.equalsIgnoreCase("iron block")) return Material.IRON_BLOCK;
		else if (s.equalsIgnoreCase("iron boots")) return Material.IRON_BOOTS;
		else if (s.equalsIgnoreCase("iron chestplate")) return Material.IRON_CHESTPLATE;
		else if (s.equalsIgnoreCase("iron door")) return Material.IRON_DOOR;
		else if (s.equalsIgnoreCase("iron fence")) return Material.IRON_FENCE;
		else if (s.equalsIgnoreCase("iron helmet")) return Material.IRON_HELMET;
		else if (s.equalsIgnoreCase("iron hoe")) return Material.IRON_HOE;
		else if (s.equalsIgnoreCase("iron ingot")) return Material.IRON_INGOT;
		else if (s.equalsIgnoreCase("iron leggings")) return Material.IRON_LEGGINGS;
		else if (s.equalsIgnoreCase("iron pickaxe")) return Material.IRON_PICKAXE;
		else if (s.equalsIgnoreCase("iron spade")) return Material.IRON_SPADE;
		else if (s.equalsIgnoreCase("iron sword")) return Material.IRON_SWORD;
		else if (s.equalsIgnoreCase("jack-o-lantern")) return Material.JACK_O_LANTERN;
		else if (s.equalsIgnoreCase("jukebox")) return Material.JUKEBOX;
		else if (s.equalsIgnoreCase("ladder")) return Material.LADDER;
		else if (s.equalsIgnoreCase("lapis block")) return Material.LAPIS_BLOCK;
		else if (s.equalsIgnoreCase("lava")) return Material.LAVA;
		else if (s.equalsIgnoreCase("lava bucket")) return Material.LAVA_BUCKET;
		else if (s.equalsIgnoreCase("leather")) return Material.LEATHER;
		else if (s.equalsIgnoreCase("leather boots")) return Material.LEATHER_BOOTS;
		else if (s.equalsIgnoreCase("leather chestplate")) return Material.LEATHER_CHESTPLATE;
		else if (s.equalsIgnoreCase("leather helmet")) return Material.LEATHER_HELMET;
		else if (s.equalsIgnoreCase("leather leggings")) return Material.LEATHER_LEGGINGS;
		else if (s.equalsIgnoreCase("leaves")) return Material.LEAVES;
		else if (s.equalsIgnoreCase("lever")) return Material.LEVER;
		else if (s.equalsIgnoreCase("locked chest")) return Material.LOCKED_CHEST;
		else if (s.equalsIgnoreCase("log")) return Material.LOG;
		else if (s.equalsIgnoreCase("long grass")) return Material.LONG_GRASS;
		else if (s.equalsIgnoreCase("magma cream")) return Material.MAGMA_CREAM;
		else if (s.equalsIgnoreCase("map")) return Material.MAP;
		else if (s.equalsIgnoreCase("melon")) return Material.MELON;
		else if (s.equalsIgnoreCase("melon block")) return Material.MELON_BLOCK;
		else if (s.equalsIgnoreCase("melon seeds")) return Material.MELON_SEEDS;
		else if (s.equalsIgnoreCase("milk bucket")) return Material.MILK_BUCKET;
		else if (s.equalsIgnoreCase("minecart")) return Material.MINECART;
		else if (s.equalsIgnoreCase("mob spawner")) return Material.MOB_SPAWNER;
		else if (s.equalsIgnoreCase("monster egg")) return Material.MONSTER_EGG;
		else if (s.equalsIgnoreCase("mossy cobblestone")) return Material.MOSSY_COBBLESTONE;
		else if (s.equalsIgnoreCase("mushroom soup")) return Material.MUSHROOM_SOUP;
		else if (s.equalsIgnoreCase("netherbrick")) return Material.NETHER_BRICK;
		else if (s.equalsIgnoreCase("netherbrick stairs")) return Material.NETHER_BRICK_STAIRS;
		else if (s.equalsIgnoreCase("nether fence")) return Material.NETHER_FENCE;
		else if (s.equalsIgnoreCase("nether warts")) return Material.NETHER_WARTS;
		else if (s.equalsIgnoreCase("netherrack")) return Material.NETHERRACK;
		else if (s.equalsIgnoreCase("note block")) return Material.NOTE_BLOCK;
		else if (s.equalsIgnoreCase("obsidian")) return Material.OBSIDIAN;
		else if (s.equalsIgnoreCase("painting")) return Material.PAINTING;
		else if (s.equalsIgnoreCase("paper")) return Material.PAPER;
		else if (s.equalsIgnoreCase("pork")) return Material.PORK;
		else if (s.equalsIgnoreCase("potion")) return Material.POTION;
		else if (s.equalsIgnoreCase("powered rail")) return Material.POWERED_RAIL;
		else if (s.equalsIgnoreCase("pumpkin")) return Material.PUMPKIN;
		else if (s.equalsIgnoreCase("pumpkin seeds")) return Material.PUMPKIN_SEEDS;
		else if (s.equalsIgnoreCase("rails")) return Material.RAILS;
		else if (s.equalsIgnoreCase("raw beef")) return Material.RAW_BEEF;
		else if (s.equalsIgnoreCase("raw chicken")) return Material.RAW_CHICKEN;
		else if (s.equalsIgnoreCase("raw fish")) return Material.RAW_FISH;
		else if (s.equalsIgnoreCase("record 10")) return Material.RECORD_10;
		else if (s.equalsIgnoreCase("record 11")) return Material.RECORD_11;
		else if (s.equalsIgnoreCase("record 3")) return Material.RECORD_3;
		else if (s.equalsIgnoreCase("record 4")) return Material.RECORD_4;
		else if (s.equalsIgnoreCase("record 5")) return Material.RECORD_5;
		else if (s.equalsIgnoreCase("record 6")) return Material.RECORD_6;
		else if (s.equalsIgnoreCase("record 7")) return Material.RECORD_7;
		else if (s.equalsIgnoreCase("record 8")) return Material.RECORD_8;
		else if (s.equalsIgnoreCase("record 9")) return Material.RECORD_9;
		else if (s.equalsIgnoreCase("red mushroom")) return Material.RED_MUSHROOM;
		else if (s.equalsIgnoreCase("red rose")) return Material.RED_ROSE;
		else if (s.equalsIgnoreCase("redstone lamp")) return Material.REDSTONE_LAMP_OFF;
		else if (s.equalsIgnoreCase("redstone torch")) return Material.REDSTONE_TORCH_OFF;
		else if (s.equalsIgnoreCase("redstone wire")) return Material.REDSTONE_WIRE;
		else if (s.equalsIgnoreCase("rotten flesh")) return Material.ROTTEN_FLESH;
		else if (s.equalsIgnoreCase("saddle")) return Material.SADDLE;
		else if (s.equalsIgnoreCase("sand")) return Material.SAND;
		else if (s.equalsIgnoreCase("sandstone")) return Material.SANDSTONE;
		else if (s.equalsIgnoreCase("sapling")) return Material.SAPLING;
		else if (s.equalsIgnoreCase("seeds")) return Material.SEEDS;
		else if (s.equalsIgnoreCase("shears")) return Material.SHEARS;
		else if (s.equalsIgnoreCase("sign")) return Material.SIGN;
		else if (s.equalsIgnoreCase("slime ball")) return Material.SLIME_BALL;
		else if (s.equalsIgnoreCase("smooth brick")) return Material.SMOOTH_BRICK;
		else if (s.equalsIgnoreCase("smooth brick stiars")) return Material.SMOOTH_STAIRS;
		else if (s.equalsIgnoreCase("snow")) return Material.SNOW;
		else if (s.equalsIgnoreCase("snow ball")) return Material.SNOW_BALL;
		else if (s.equalsIgnoreCase("snow block")) return Material.SNOW_BLOCK;
		else if (s.equalsIgnoreCase("farmland")) return Material.SOIL;
		else if (s.equalsIgnoreCase("soul sand")) return Material.SOUL_SAND;
		else if (s.equalsIgnoreCase("spider eye")) return Material.SPIDER_EYE;
		else if (s.equalsIgnoreCase("sponge")) return Material.SPONGE;
		else if (s.equalsIgnoreCase("stationary lava")) return Material.STATIONARY_LAVA;
		else if (s.equalsIgnoreCase("stationary water")) return Material.STATIONARY_WATER;
		else if (s.equalsIgnoreCase("stone step")) return Material.STEP;
		else if (s.equalsIgnoreCase("stick")) return Material.STICK;
		else if (s.equalsIgnoreCase("stone")) return Material.STONE;
		else if (s.equalsIgnoreCase("stone axe")) return Material.STONE_AXE;
		else if (s.equalsIgnoreCase("button")) return Material.STONE_BUTTON;
		else if (s.equalsIgnoreCase("stone hoe")) return Material.STONE_HOE;
		else if (s.equalsIgnoreCase("stone pickaxe")) return Material.STONE_PICKAXE;
		else if (s.equalsIgnoreCase("stone plate")) return Material.STONE_PLATE;
		else if (s.equalsIgnoreCase("stone shovel")) return Material.STONE_SPADE;
		else if (s.equalsIgnoreCase("stone sword")) return Material.STONE_SWORD;
		else if (s.equalsIgnoreCase("storage minecart")) return Material.STORAGE_MINECART;
		else if (s.equalsIgnoreCase("string")) return Material.STRING;
		else if (s.equalsIgnoreCase("sugar")) return Material.SUGAR;
		else if (s.equalsIgnoreCase("sugar cane")) return Material.SUGAR_CANE;
		else if (s.equalsIgnoreCase("gunpowder")) return Material.SULPHUR;
		else if (s.equalsIgnoreCase("glass pane")) return Material.THIN_GLASS;
		else if (s.equalsIgnoreCase("tnt")) return Material.TNT;
		else if (s.equalsIgnoreCase("torch")) return Material.TORCH;
		else if (s.equalsIgnoreCase("trap door")) return Material.TRAP_DOOR;
		else if (s.equalsIgnoreCase("vine")) return Material.VINE;
		else if (s.equalsIgnoreCase("clock")) return Material.WATCH;
		else if (s.equalsIgnoreCase("water")) return Material.WATER;
		else if (s.equalsIgnoreCase("water bucket")) return Material.WATER_BUCKET;
		else if (s.equalsIgnoreCase("lily")) return Material.WATER_LILY;
		else if (s.equalsIgnoreCase("web")) return Material.WEB;
		else if (s.equalsIgnoreCase("wheat")) return Material.WHEAT;
		else if (s.equalsIgnoreCase("wood plank")) return Material.WOOD;
		else if (s.equalsIgnoreCase("wood axe")) return Material.WOOD_AXE;
		else if (s.equalsIgnoreCase("wood pickaxe")) return Material.WOOD_PICKAXE;
		else if (s.equalsIgnoreCase("wood plate")) return Material.WOOD_PLATE;
		else if (s.equalsIgnoreCase("wood stairs")) return Material.WOOD_STAIRS;
		else if (s.equalsIgnoreCase("wood sword")) return Material.WOOD_SWORD;
		else if (s.equalsIgnoreCase("wood door")) return Material.WOODEN_DOOR;
		else if (s.equalsIgnoreCase("wool")) return Material.WOOL;
		else if (s.equalsIgnoreCase("crafting table")) return Material.WORKBENCH;
		else if (s.equalsIgnoreCase("yellow flower")) return Material.YELLOW_FLOWER;

		return null;
		
	}
	
	public static Material stringToArmorType(String material, String type)
	{
		
		if (material.equalsIgnoreCase("leather"))
		{
			
			if (type.equalsIgnoreCase("head")) return Material.LEATHER_HELMET;
			else if (type.equalsIgnoreCase("body")) return Material.LEATHER_CHESTPLATE;
			else if (type.equalsIgnoreCase("legs")) return Material.LEATHER_LEGGINGS;
			else if (type.equalsIgnoreCase("boots")) return Material.LEATHER_BOOTS;
			
		}
		else if (material.equalsIgnoreCase("chainmail"))
		{
			
			if (type.equalsIgnoreCase("head")) return Material.CHAINMAIL_HELMET;
			else if (type.equalsIgnoreCase("body")) return Material.CHAINMAIL_CHESTPLATE;
			else if (type.equalsIgnoreCase("legs")) return Material.CHAINMAIL_LEGGINGS;
			else if (type.equalsIgnoreCase("boots")) return Material.CHAINMAIL_BOOTS;
			
		}
		else if (material.equalsIgnoreCase("iron"))
		{
			
			if (type.equalsIgnoreCase("head")) return Material.IRON_HELMET;
			else if (type.equalsIgnoreCase("body")) return Material.IRON_CHESTPLATE;
			else if (type.equalsIgnoreCase("legs")) return Material.IRON_LEGGINGS;
			else if (type.equalsIgnoreCase("boots")) return Material.IRON_BOOTS;
			
		}
		else if (material.equalsIgnoreCase("gold"))
		{
			
			if (type.equalsIgnoreCase("head")) return Material.GOLD_HELMET;
			else if (type.equalsIgnoreCase("body")) return Material.GOLD_CHESTPLATE;
			else if (type.equalsIgnoreCase("legs")) return Material.GOLD_LEGGINGS;
			else if (type.equalsIgnoreCase("boots")) return Material.GOLD_BOOTS;
			
		}
		else if (material.equalsIgnoreCase("diamond"))
		{
			
			if (type.equalsIgnoreCase("head")) return Material.DIAMOND_HELMET;
			else if (type.equalsIgnoreCase("body")) return Material.DIAMOND_CHESTPLATE;
			else if (type.equalsIgnoreCase("legs")) return Material.DIAMOND_LEGGINGS;
			else if (type.equalsIgnoreCase("boots")) return Material.DIAMOND_BOOTS;
			
		}

		return null;
		
	}
	
	public static Enchantment toEnchantment(String s)
	{
		
		if (s.equalsIgnoreCase("arrowdamage")) return Enchantment.ARROW_DAMAGE;
		else if (s.equalsIgnoreCase("firearrows")) return Enchantment.ARROW_FIRE;
		else if (s.equalsIgnoreCase("infinitearrows")) return Enchantment.ARROW_INFINITE;
		else if (s.equalsIgnoreCase("knockbackarrows")) return Enchantment.ARROW_KNOCKBACK;
		else if (s.equalsIgnoreCase("sharpness")) return Enchantment.DAMAGE_ALL;
		else if (s.equalsIgnoreCase("spiderdamage")) return Enchantment.DAMAGE_ARTHROPODS;
		else if (s.equalsIgnoreCase("undeaddamage")) return Enchantment.DAMAGE_UNDEAD;
		else if (s.equalsIgnoreCase("digspeed")) return Enchantment.DIG_SPEED;
		else if (s.equalsIgnoreCase("durability")) return Enchantment.DURABILITY;
		else if (s.equalsIgnoreCase("fireaspect")) return Enchantment.FIRE_ASPECT;
		else if (s.equalsIgnoreCase("knockback")) return Enchantment.KNOCKBACK;
		else if (s.equalsIgnoreCase("blockloot")) return Enchantment.LOOT_BONUS_BLOCKS;
		else if (s.equalsIgnoreCase("mobloot")) return Enchantment.LOOT_BONUS_MOBS;
		else if (s.equalsIgnoreCase("oxygen")) return Enchantment.OXYGEN;
		else if (s.equalsIgnoreCase("environmentprotection")) return Enchantment.PROTECTION_ENVIRONMENTAL;
		else if (s.equalsIgnoreCase("explosionprotection")) return Enchantment.PROTECTION_EXPLOSIONS;
		else if (s.equalsIgnoreCase("fallprotection")) return Enchantment.PROTECTION_FALL;
		else if (s.equalsIgnoreCase("fireproof")) return Enchantment.PROTECTION_FIRE;
		else if (s.equalsIgnoreCase("projectileprotection")) return Enchantment.PROTECTION_PROJECTILE;
		else if (s.equalsIgnoreCase("silktouch")) return Enchantment.SILK_TOUCH;
		else if (s.equalsIgnoreCase("waterworker")) return Enchantment.WATER_WORKER;
		
		return null;
		
	}
	
}
