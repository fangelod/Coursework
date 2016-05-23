/******************************************************************
 * Program or Assignment #: Lab 1
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
 * Description: This program takes two predetermined numbers
 * and finds the sum and product of both. Then the program
 * prints the numbers and the corresponding sum and product.
 *
 * Input: No user input
 *
 * Output: Prints out what the two integers were, their sum
 * and their product.
 *
 ******************************************************************/


public class Lab1 
{
	public static void main (String[] args)
	{
		int num1;
		int num2;
		
		num1 = 5;
		num2 = 10;
		
		int sum = num1 + num2;
		int product = num1 * num2;
		
		System.out.println("The two integers are: " + num1 + " and " + num2);
		System.out.println("Their addition is: " + sum);
		System.out.println("Their product is: " + product);
	}
}
