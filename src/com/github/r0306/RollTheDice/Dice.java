package com.github.r0306.RollTheDice;

import java.util.Random;

public class Dice 
{

	public static Integer roll()
	{
		
		Random rolling = new Random();
		int side = rolling.nextInt(100) + 1;
		
		return side;
	
	}
	
}
