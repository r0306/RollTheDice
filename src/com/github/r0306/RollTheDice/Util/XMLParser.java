package com.github.r0306.RollTheDice.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.github.r0306.RollTheDice.Storage.XMLAccessor;

public class XMLParser extends Util
{

	static Document doc;
	
	public static Document parseXML() throws SAXException, IOException, ParserConfigurationException
	{
		
		if (doc == null)
		{
		
			XMLAccessor xml = new XMLAccessor();
			InputStream input = xml.getXML();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(input);
			doc.getDocumentElement().normalize();
			XMLParser.doc = doc;
			input.close();
			
		}
		
		return doc;
		
	}
	
	public static NodeList getSides() throws SAXException, IOException, ParserConfigurationException
	{

		return parseXML().getElementsByTagName("Side");
		
	}
	
	public static NodeList getInventoryFromXML(Integer i) throws SAXException, IOException, ParserConfigurationException
	{

		return getSide(i).getElementsByTagName("Inventory");
		
	}
	
	public static NodeList getArmorFromXML(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getSide(i).getElementsByTagName("Armor");
		
	}
	
	public static NodeList getItemStacks(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getInventoryElement(i).getElementsByTagName("ItemStack");
		
	}
	
	public static NodeList getHelmet(Integer i) throws SAXException, IOException, ParserConfigurationException
	{

		return getArmorElement(i).getElementsByTagName("Head");
		
	}
	
	public static NodeList getChestpiece(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getArmorElement(i).getElementsByTagName("Body");
		
	}
	
	public static NodeList getLeggings(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getArmorElement(i).getElementsByTagName("Legs");
		
	}
	
	public static NodeList getBoots(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getArmorElement(i).getElementsByTagName("Boots");
		
	}
	
	public static ItemStack[] getInventory(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
	
		ItemStack[] items = new ItemStack[getItemStacks(i).getLength()];
		int itemCounter = 0;
		
		for (int l = 0; l < getItemStacks(i).getLength(); l ++)
		{
			
			Material item = StringToItemStack.toMaterial(getValueOfItemStack("Item", i, l));
			int amount = toInt(getValueOfItemStack("Amount", i, l));
			ItemStack itemstack = new ItemStack(item, amount);
			
			for (int x = 0; x < getItemStackNodeLength(i, l, x) ; x ++) {

				if (getEnchantment(i, l, x) != null)
				{

					itemstack.addEnchantment(getEnchantment(i, l, x), getEnchantmentLevel(i, l));		
					
				}
				
			}
			
			items[itemCounter] = itemstack;
			itemCounter++;
			
		}
		
		return items;
		
	}
	
	public static ItemStack[] getArmor(Integer i) throws DOMException, SAXException, IOException, ParserConfigurationException
	{
		
		Material headType = StringToItemStack.stringToArmorType(getValueOfArmor("Head", "Type", i), "Head");
		Material chestType = StringToItemStack.stringToArmorType(getValueOfArmor("Body", "Type", i), "Body");
		Material legType = StringToItemStack.stringToArmorType(getValueOfArmor("Legs", "Type", i), "Legs");
		Material footType = StringToItemStack.stringToArmorType(getValueOfArmor("Boots", "Type", i), "Boots");
		
		ItemStack headItem = null;
		ItemStack chestItem = null;
		ItemStack legItem = null;
		ItemStack footItem = null;
		
		if (headType != null)
			headItem = new ItemStack(headType, 1);
		if (chestType != null)
			chestItem = new ItemStack(chestType, 1);
		if (chestType != null)
			legItem = new ItemStack(legType, 1);
		if (footType != null)
			footItem = new ItemStack(footType, 1);

		ItemStack[] armor = new ItemStack[countItems(headItem, chestItem, legItem, footItem)];
		
		int counter = 0;
				
		if (headItem != null)
			armor[counter++] = addEnchantments(headItem, "Head", i);
		if (chestItem != null)
			armor[counter++] = addEnchantments(chestItem, "Body", i);
		if (legItem != null)
			armor[counter++] = addEnchantments(legItem, "Legs", i);
		if (footItem != null)
			armor[counter++] = addEnchantments(footItem, "Boots", i);
				
		return armor;
		
	}
		
	public static ItemStack addEnchantments(ItemStack item, String name, Integer i) throws DOMException, SAXException, IOException, ParserConfigurationException
	{
		
		List<Enchantment> enchantments = getEnchantments(name, i);
		List<Integer> levels = getEnchantmentLevels(name, i);

		int index = 0;
		for (Enchantment e : enchantments)
		{
			
			if (levels.get(index) != -1)
			{
			
				item.addEnchantment(e, levels.get(index));
			
			}
			
			index ++;
			
		}
		
		return item;
		
	}
		
	public static List<Enchantment> getEnchantments(String name, Integer i) throws DOMException, SAXException, IOException, ParserConfigurationException
	{
		
		List<Enchantment> enchantments = new ArrayList<Enchantment>();
		
		for (int x = 0; x < getArmorNodeLength(name, i, x) ; x ++ )
		{

			String item = getArmorEnchantments(name, i, x);
			
			if (item != null)
			{
				
				String[] ench = item.split(" ");
				Enchantment e = StringToItemStack.toEnchantment(ench[0]);
				enchantments.add(e);
				
			}
			
		}
		
		return enchantments;
		
	}
	
	public static List<Integer> getEnchantmentLevels(String name, Integer i) throws DOMException, SAXException, IOException, ParserConfigurationException
	{
		
		List<Integer> levels = new ArrayList<Integer>();
		
		for (int x = 0; x < getArmorNodeLength(name, i, x) ; x ++ )
		{
			
			String item = getArmorEnchantments(name, i, x);
			
			if (item != null && !item.equalsIgnoreCase("none"))
			{
				
				String[] ench = item.split(" ");
				Integer e = toInt(ench[1]);
				levels.add(e);
			
			}
			else if (item.equalsIgnoreCase("none"))
			{
				
				levels.add(-1);
				
			}
			
		}
		
		return levels;
		
	}
	
	public static Enchantment getArmorEnchantment(String type, Integer i, Integer index) throws DOMException, SAXException, IOException, ParserConfigurationException
	{
		
		return StringToItemStack.toEnchantment(getValueOfArmor(type, "Enchantment", i));
		
	}
	
	public static Integer countItems(ItemStack head, ItemStack body, ItemStack leg, ItemStack foot)
	{
		
		int count = 0;
		
		if (head != null)
			count ++;
		if (body != null)
			count ++;
		if (leg != null)
			count ++;
		if (foot != null)
			count ++;
		
		return count;
		
	}
	
	public static Enchantment getEnchantment(Integer i, Integer l, Integer index) throws DOMException, SAXException, IOException, ParserConfigurationException
	{

		String s = getItemEnchantments("Enchantment", i, l, index);

		if (!s.equalsIgnoreCase("none"))
		{
			
			String[] ench = s.split(" ");
			Enchantment e = StringToItemStack.toEnchantment(ench[0]);
			
			return e;
		
		}
		
		return null;
		
	}
	
	public static Integer getEnchantmentLevel(Integer i, Integer l) throws DOMException, SAXException, IOException, ParserConfigurationException
	{
		
		String s = getValueOfItemStack("Enchantment", i, l);
		
		if (!s.equalsIgnoreCase("none"))
		{
			
			String[] lvl = s.split(" ");
			int level = toInt(lvl[1]);
			
			return level;
			
		}
		
		return null;
		
	}
	
	public static Element getInventoryElement(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
				
		return (getInventoryFromXML(i).item(0) instanceof Element) ? (Element) getInventoryFromXML(i).item(0) : null;
		
	}
	
	public static Element getArmorElement(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return (getArmorFromXML(i).item(0) instanceof Element) ? (Element) getArmorFromXML(i).item(0) : null;
		
	}
	
	public static Element getItemStackElement(Integer i, Integer number) throws SAXException, IOException, ParserConfigurationException
	{

		return (getItemStacks(i).item(number) instanceof Element) ? (Element) getItemStacks(i).item(number) : null;
		
	}
	
	public static Element getArmorElementByType(String name, Integer i) throws SAXException, IOException, ParserConfigurationException
	{

		if (name.equalsIgnoreCase("Head"))
		{

			return (getHelmet(i).item(0) instanceof Element) ? (Element) getHelmet(i).item(0) : null;
			
		}
		else if (name.equalsIgnoreCase("Body"))
		{
			
			return (getChestpiece(i).item(0) instanceof Element) ? (Element) getChestpiece(i).item(0) : null;
			
		}
		else if (name.equalsIgnoreCase("Legs"))
		{
			
			return (getLeggings(i).item(0) instanceof Element) ? (Element) getLeggings(i).item(0) : null;
			
		}
		else if (name.equalsIgnoreCase("Boots"))
		{
			
			return (getBoots(i).item(0) instanceof Element) ? (Element) getBoots(i).item(0) : null;
			
		}

		return null;
		
	}
	
	public static Element getSide(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		for (int l = 0; l < getSides().getLength(); l ++)
		{

			if (getSides().item(l).getAttributes().item(0).toString().equalsIgnoreCase("number=\"" + toString(i) + "\"") && getSides().item(l) instanceof Element)
			{
		
				return (Element) getSides().item(l);
				
			}
			
		}
		
		return null;
		
	}
	
	public static String getName(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getValue("Name", i);
		
	}
	
	public static String getUsageMessage(Integer i) throws SAXException, IOException, ParserConfigurationException
	{

		return getValue("Usage", i);
		
	}
	
	public static String getClass(Integer i) throws SAXException, IOException, ParserConfigurationException
	{

		return getValue("Classified", i);
		
	}
		
	public static String getValue(String name, Integer i) throws SAXException, IOException, ParserConfigurationException
	{

		return getSide(i).getElementsByTagName(name).item(0).getChildNodes().item(0).getNodeValue();

	}
	
	public static String getValueOfItemStack(String name, Integer i, Integer number) throws DOMException, SAXException, IOException, ParserConfigurationException
	{

		return getItemStackElement(i, number).getElementsByTagName(name).item(0).getChildNodes().item(0).getNodeValue();
		
	}
	
	public static String getValueOfArmor(String type, String name, Integer i) throws DOMException, SAXException, IOException, ParserConfigurationException
	{

		return getArmorElementByType(type, i).getElementsByTagName(name).item(0).getChildNodes().item(0).getNodeValue();
				
	}
	
	public static String getItemEnchantments(String type, Integer i, Integer num, Integer number) throws DOMException, SAXException, IOException, ParserConfigurationException
	{
		
		return getItemStackElement(i, num).getElementsByTagName("Enchantment").item(number).getChildNodes().item(0).getNodeValue();
		
	}
	
	public static Integer getArmorNodeLength(String type, Integer i, Integer number) throws DOMException, SAXException, IOException, ParserConfigurationException
	{

		return getArmorElementByType(type, i).getElementsByTagName("Enchantment").getLength();
		
	}
	
	public static Integer getItemStackNodeLength(Integer i, Integer num, Integer number) throws SAXException, IOException, ParserConfigurationException
	{

		return getItemStackElement(i, num).getElementsByTagName("Enchantment").getLength();
		
	}
	
	public static String getArmorEnchantments(String type, Integer i, Integer number) throws DOMException, SAXException, IOException, ParserConfigurationException
	{
		
		return getArmorElementByType(type, i).getElementsByTagName("Enchantment").item(number).getChildNodes().item(0).getNodeValue();
		
	}
	
}
