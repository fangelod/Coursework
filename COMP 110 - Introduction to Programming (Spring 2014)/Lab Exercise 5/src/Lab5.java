/******************************************************************
 * Program or Assignment #: Lab 5
 *
 * Programmer: Franz Dominno
 *
 * Due Date: Thursday, April 24, 2014 
 *
 * COMP110-002, Spring 2014       Instructor: Prof. Jay Aikat
 *
 * Pledge: I have neither given nor received unauthorized aid
 *         on this program. 
 *
 * Description: This program calculate and prints teh factorial
 * of numbers inputed by the user. The first time is prompts,
 * it completes the calculation with a for loop. The second
 * time, it completes it with a while loop
 *
 * Input: User inputs integers
 *
 * Output: The program prints the calculated factorial for each
 * inputed integer and an error message if the integer is not
 * greater than zero
 *
 ******************************************************************/
import java.util.Scanner;

public class Lab5
{
	public static void main (String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		
		int x = 1;
		int y = 1;
		int j = 0;
		
		System.out.println("Please enter a positive integer greater"
				+ " than zero: ");
		int input = keyboard.nextInt();
		
		if (input > 0)
		{
			for (int i = 0; i < input; i++)
			{
				int nextnum = input - i;
				x = x * nextnum;
			}
			
			System.out.println(input + "! = " + x);
		}
		else
		{	
			System.out.println("ERROR. The integer must be greater"
					+" than zero.");
		}
		
		System.out.println("Please enter another positive integer "
				+ "greater than zero: ");
		int input2 = keyboard.nextInt();
		
		if (input2 > 0)
		{
			while (j < input2)
			{
				int nextnum = input2 - j;
				y = y * nextnum;
				j++;
			}
			
			System.out.println(input2 + "! = " + y);
		}
		else
		{	
			System.out.println("ERROR. The integer must be greater"
					+" than zero.");
		}
	}
}
