package com.github.r0306.RollTheDice.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser extends Util
{

	public static Document parseXML() throws SAXException, IOException, ParserConfigurationException
	{
		
		InputStream input = XMLParser.class.getResourceAsStream("/com/github/r0306/RollTheDice/Storage/Sides.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		doc.getDocumentElement().normalize();
		
		return doc;
		
	}
	
	public static NodeList getSides() throws SAXException, IOException, ParserConfigurationException
	{
		
		return parseXML().getElementsByTagName("Sides");
		
	}
	
	public static Element getSide(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return (getSides().item(++i).getNodeType() == Node.ELEMENT_NODE) ? (Element) getSides().item(++i) : null;
		
	}
	
	public static String getName(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getValue("Name", getSide(i));
		
	}
	
	public static String getUsageMessage(Integer i) throws SAXException, IOException, ParserConfigurationException
	{
		
		return getValue("Usage", getSide(i));
		
	}
	
	public static String getValue(String name, Element element)
	{
		
		NodeList nlList = element.getElementsByTagName(name).item(0).getChildNodes();
		 
        Node nValue = (Node) nlList.item(0);
 
        return nValue.getNodeValue();
		
	}
	
}
