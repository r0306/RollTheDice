package com.github.r0306.RollTheDice.DiceHandlers;

import java.util.Random;

public class Dice
{

	Dice(String message, Integer side) {};
	
	public static Integer roll()
	{
		
		Random rolling = new Random();
		int side = rolling.nextInt(28) + 1;
		
		return side;
	
	}
	
}
