package com.github.r0306.RollTheDice.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.print.attribute.standard.Sides;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.inventory.ItemStack;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.github.r0306.RollTheDice.RollTheDice;
import com.github.r0306.RollTheDice.Storage.Test;

public class XMLParser extends Util
{
	
	public static Document parseXML() throws SAXException, IOException, ParserConfigurationException
	{
		
		//InputStream input = XMLParser.class.getResourceAsStream("/lol.txt");
		File input = new File("C:\\Users\\MRT253\\Downloads\\Server\\plugins\\RollTheDice\\Sides.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		doc.getDocumentElement().normalize();
		
		return doc;
		
	}
	
	public static NodeList getSides() throws SAXException, IOException, ParserConfigurationException
	{
		System.out.println(parseXML().getElementsByTagName("Sides").item(0).getChildNodes().item(1).getChildNodes().item(1).getNodeValue());
		return parseXML().getElementsByTagName("Sides").item(0).getChildNodes();
		
	}
	
	public static NodeList getInventoryFromXML(Integer i) throws SAXException, IOException, ParserConfigurationException
	{

		return getSide(i).getElementsByTagName("Inventory");
		
	}
	
	public static ItemStack[] getInventory(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		for (int l = 0; l < getInventoryFromXML(i).getLength(); l ++)
		{
			
			
			
		}
		return null;
		
	}
	
	public static Element getSide(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
return (Element)getSides().item(++i);
	//	return (getSides().item(++i).getNodeType() == Node.ELEMENT_NODE) ? (Element) getSides().item(++i) : null;
		
	}
	
	public static String getName(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getValue(i, "Name", getSide(i));
		
	}
	
	public static String getUsageMessage(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getValue(i, "Usage", getSide(i));
		
	}
	
	public static String getClass(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getValue(i, "Classified", getSide(i));
		
	}
		
	public static String getValue(Integer i, String name, Element element) throws SAXException, IOException, ParserConfigurationException
	{

		System.out.println(getSide(++i).getElementsByTagName(name).item(1).getNodeName());
		return getSide(++i).getElementsByTagName(name).item(1).getNodeName();
		/*
		NodeList nlList = element.getElementsByTagName(name).item(0).getChildNodes();
		 
        Node nValue = (Node) nlList.item(0);
 
        return nValue.getNodeValue();
		*/
	}
	
}
