/******************************************************************
 * Program or Assignment #: Lab 2
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
 * Description: This program takes two numbers of type double
 * from the user and displays the first as an interger and
 * the second as a double.
 *
 * Input: Two numbers are enterd by the user
 *
 * Output: The program prints two prompts asking the user to
 * insert two numbers, the first number as an integer, and
 * the second number as a double
 *
 ******************************************************************/
import java.util.Scanner;

public class Lab2
{
	public static void main (String [] args)
	{
		Scanner keyboard = new Scanner(System.in);
		
		double input1 = 0;
		double input2 = 0;
		
		System.out.println("Please enter a number:");
		input1 = keyboard.nextDouble();
		int convert1 = (int)input1;
		
		System.out.println("Please enter another number:");
		input2 = keyboard.nextDouble();
		
		System.out.println();
		System.out.println("Displayed as an integer, the first number was: " + convert1);
		
		System.out.println("Displayed as a double, the second number was: " + input2);
	}
}
