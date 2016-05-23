/******************************************************************
 * Program or Assignment #: Lab 4
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
 * Description: This program takes a string input, or a word,
 * from the user and tells the user whether they entered a
 * word started with "A" or "B", "C", or neither. It also
 * tells the user whether they entered a short or long word.
 *
 * Input: User inputs a string of any size without spaces
 *
 * Output: The program prints out a message to the user
 * depending on the user input and if the word they
 * inputed is a short or long word
 *
 ******************************************************************/
import java.util.Scanner;


public class Lab4
{
	public static void main (String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Please enter one word that starts with the letter \"A\" or the letter \"B\".");
		
		String input = keyboard.next();
		
		if (input.charAt(0) == 'A' || input.charAt(0) == 'B')
		{
			System.out.println("You successfully entered a word");
			
			if (input.length() <= 5)
			{
				System.out.println("You have entered a short word");
			}
			else
			{
				System.out.println("You have entered a long word");
			}
		}
		else if (input.charAt(0) == 'C')
		{
			System.out.println("Close, but no cigar");
			
			if (input.length() <= 5)
			{
				System.out.println("You have entered a short word");
			}
			else
			{
				System.out.println("You have entered a long word");
			}
		}
		else
		{
			System.out.println("You incorrectly entered a word");
			
			if (input.length() <= 5)
			{
				System.out.println("You have entered a short word");
			}
			else
			{
				System.out.println("You have entered a long word");
			}
		}
	}
}
