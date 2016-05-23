/******************************************************************
 * Program or Assignment #: Assignment2
 *
 * Programmer: Franz Dominno
 *
 * Due Date: Friday, February 21, 2014
 *
 * COMP110-002, Spring 2014       Instructor: Prof. Jay Aikat
 *
 * Pledge: I have neither given nor received unauthorized aid
 *         on this program. 
 *
 * Description: This program takes an input from the user that is a
 * string of size 1-8 and determines whether or not it is a
 * palindrome or not
 *
 * Input: The user will input a string of size 1-8
 *
 * Output: The output will tell the user if the string they have
 * entered is a palindrome. If the user inputed a string of size
 * greater than 8, the output will return an error message telling
 * the user to input an appropriate string
 *
 ******************************************************************/
import java.util.Scanner;

public class Assignment2 
{
	
	public static void main (String[] args)
	{
		
		Scanner keyboard = new Scanner(System.in);
		
		
		System.out.println("Please input a string of size 1-8");
		String userinput = keyboard.next();
		
		
		int size = userinput.length();
		String pali = "";
		
		for(int letter = size - 1; letter >= 0; letter--)
			pali = pali + userinput.charAt(letter);
		
		
		// System.out.println(pali);
		
		
		if (size <= 8)
		{
			
			if(userinput.equalsIgnoreCase(pali))
			{
				
				System.out.println("The string you have entered" + " " + "is a palindrome");
			}
			
			else	
			{
				
				System.out.println("The string you have entered" + " " + "is not a palindrome");
			}
		}
		
		else 
		{
			
			System.out.println("Error. The string you have entered has too many characters. "
					+ "Please input a string of appropriate size");
		
		}
	
	}
}
