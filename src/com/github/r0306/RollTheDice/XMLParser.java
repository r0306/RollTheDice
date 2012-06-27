package com.github.r0306.RollTheDice;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser
{
	
	Document dom;

	private void parseXmlFile()
	{

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try 
		{

			DocumentBuilder db = dbf.newDocumentBuilder();

			dom = db.parse("employees.xml");


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	

	private void parseDocument(){
	
		Element docEle = dom.getDocumentElement();

		NodeList nl = docEle.getElementsByTagName("Employee");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				//get the employee element
				Element el = (Element)nl.item(i);

				//get the Employee object
				Employee e = getEmployee(el);

				//add it to list
				myEmpls.add(e);
			}
		}
	}
	

	private Employee getEmployee(Element empEl) {

		//for each <employee> element get text or int values of
		//name ,id, age and name
		String name = getTextValue(empEl,"Name");
		int id = getIntValue(empEl,"Id");
		int age = getIntValue(empEl,"Age");

		String type = empEl.getAttribute("type");

		//Create a new Employee with the value read from the xml nodes
		Employee e = new Employee(name,id,age,type);

		return e;
	}


	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is 'name' I will return John
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
}
