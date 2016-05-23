/******************************************************************
 * Program or Assignment #: Lab 3
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
 * Description: This program concatenates two strings and
 * prints it twice, once after it's been concatenated and
 * twice being all lower-case. It also returns the size
 * of the concatenated string.
 *
 * Input: No user input
 *
 * Output: The program prints the length of the concatenated
 * string, the concatenated string, and the concatenated
 * string in all lower-case.
 *
 ******************************************************************/


public class Lab3
{
	public static void main (String [] args)
	{
		String s1 = "Computer Science";
		String s2 = "is fun!";
		
		String s3 = s1 + " " + s2;
		System.out.println(s3);
		
		System.out.println(s3.length());
		
		System.out.println(s3.charAt(9));
		
		System.out.println(s3.toLowerCase());
	}
}
