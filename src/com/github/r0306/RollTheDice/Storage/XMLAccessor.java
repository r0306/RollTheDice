package com.github.r0306.RollTheDice.Storage;

import java.io.InputStream;

public class XMLAccessor
{

	public InputStream getXML()
	{
		
		return getClass().getClassLoader().getResourceAsStream("Sides.xml");
		
	}
	
}
