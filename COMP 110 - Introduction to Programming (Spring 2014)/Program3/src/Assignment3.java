/******************************************************************
 * Program or Assignment #: Assignment3
 *
 * Programmer: Franz Dominno
 *
 * Due Date: Tuesday, March 25, 2014
 *
 * COMP110-002, Spring 2014       Instructor: Prof. Jay Aikat
 *
 * Pledge: I have neither given nor received unauthorized aid
 *         on this program. 
 *
 * Description: This program will prompt the user to enter a line
 * of text. Then it will ask which of the three computations they
 * would like to perform. After that task has been performed, it
 * re-prompts the user to enter another line of text and it repeats
 * the process
 *
 * Input: The user will input a line of text and the number
 * corresponding to the desired computation
 *
 * Output: This program will output the result of the three possible
 * computations; being whether or not a string is a palindrome,
 * computing the rounded sum of a string of doubles, or counting the
 * number of unique characters in a string disregarding spaces and
 * punctuation.
 *
 ******************************************************************/
import java.util.Scanner;


public class Assignment3
{
	
	
	public static void main (String [] args)
	{
		
		int x = 1;
		int y = 0;
		
		while (x > y)
		{	
		
			Scanner keyboard = new Scanner(System.in);
		
			System.out.println("Please type in a line of text");
			String line = keyboard.nextLine();
			
			System.out.println("Which of the following computations "
					+ "should be performed?");
			System.out.println("(1) Check if the line is a palindrome");
			System.out.println("(2) Compute the rounded sum of the line");
			System.out.println("(3) Count unique characters");
			System.out.println("Enter the corresponding number next to"
					+ " the computation that is to be performed");
			int num = keyboard.nextInt();
			
			if (num == 1)
			{
				
				System.out.println(line + " will be checked if it "
						+ "is a palindrome");
			
			}
			
			if (num == 2)
			{
				
				System.out.println("The rounded sum of " + line + " will "
						+ "be computed");
			
			}
			
			if (num == 3)
			{
				
				System.out.println("The number of unique characters in "
						+ line + " will be counted");
			
			}
			
			if (num > 3)
			{
				
				System.out.println("ERROR. Please input one of the "
						+ "corresponding numbers");
			
			}
		
			isPalindrome(line, num);
			computeRoundedSum(line, num);
			countUniqueCharacters(line, num);
			
		}
			
			x++;
		
	}
		
		
		public static void isPalindrome(String line, int num)
		{
			
			if (num == 1)
			{
				
				int size = line.length();
				String pali = "";
				
				for(int letter = size - 1; letter >= 0; letter--)
					pali = pali + line.charAt(letter);
				
				//System.out.println(pali);
						
				if(line.equalsIgnoreCase(pali))
				{
					
					System.out.println("True. It is a palindrome");
				}
					
				else	
				{
					
					System.out.println("False. It is not a palindrome");
				}
			
			}	
			
			else
			{
				
				System.out.print("");
			
			}
		
		}
		
		
		public static void computeRoundedSum(String line, int num)
		{
			if (num == 2)
			{
				
				String[] sep = line.split(" ");
				
				double sum = 0;
				
				for (int i = 0; i < sep.length; i++)
				{
					
					double convert = Double.parseDouble(sep[i]);
					sum += convert;
				
				}
				
				System.out.println(Math.round(sum));	
			
			}	
			
			else
			{
				
				System.out.print("");
			
			}	
		
		}
		
		
		public static void countUniqueCharacters(String line, int num)
		{
			
			if (num == 3)
			{
				
				int count = 0;
				String nospace = line.replaceAll("\\W", "");
				int word = nospace.length();
							
				for (int chr = word - 1; chr >= 0; chr--) {
			        if (nospace.substring(0, chr).contains(nospace.charAt(chr) + ""))
			        {    
			        	
			        	System.out.print("");
			        
			        }
	
			        else
			        {
			        	
			        	count++;
			        
			        }
			    
				}
			    
				System.out.println("There are " + count + " unique characters");
			
			}
			
			else
			{
				
				System.out.print("");
			
			}
			
		}	

}
