/******************************************************************
 * Program or Assignment #: Assignment0
 *
 * Programmer: Franz Dominno
 *
 * Due Date: Tuesday, February 4, 2014
 *
 * COMP110-002, Spring 2014       Instructor: Prof. Jay Aikat
 *
 * Pledge: I have neither given nor received unauthorized aid
 *         on this program. 
 *
 * Description: This program add and multiplies numbers that the user
 *				inputs and outputs the answers in both the primitive
 *				type integer and the primitive type double.
 *
 * Input: A user will input a number of the primitive type integer
 * 			for variable x and a number of the primitive type
 * 			double for the variable y.
 *
 * Output: Prints out the integer sum and product of two numbers and
 * 			the double sum and product of the same two numbers.
 *
 ******************************************************************/
import java.util.Scanner;

public class Assignment1
{
	public static void main (String[] args)
	{		
		Scanner keyboard = new Scanner(System.in);
		
		
		System.out.println("Please input an integer for variable x: ");
		int x = keyboard.nextInt();
		
		System.out.println("Please input a double for variable y: ");
		double y = keyboard.nextDouble();
		
		
		int ya;
		ya = (int)y;
		int resultAdd = 0;
		int resultMult = 1;
		resultAdd = x + ya;
		resultMult = x * ya;
		
		double xa;
		xa = (double)x;
		double resultAdda = 0;
		double resultMulta = 1;
		resultAdda = xa + y;
		resultMulta = xa * y;
		
		
		System.out.println("The estimated sum or \"integer\" sum of x and y is " + resultAdd);
		System.out.println("The estimated product or \"integer\" product of x and y is " + resultMult);
		System.out.println("The exact sum or \"double\" sum of x and y is " + resultAdda);
		System.out.println("The exact product or \"double\" product of x and y is " + resultMulta);
	}
}
