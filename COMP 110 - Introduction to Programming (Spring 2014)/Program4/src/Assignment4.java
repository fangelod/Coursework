/******************************************************************
 * Program or Assignment #: Assignment4
 *
 * Programmer: Franz Dominno
 *
 * Due Date: Sunday, April 20, 2014 
 *
 * COMP110-002, Spring 2014       Instructor: Prof. Jay Aikat
 *
 * Pledge: I have neither given nor received unauthorized aid
 *         on this program. 
 *
 * Description: This program takes in user input to describe food
 * and gerbils used in a lab and using that entered information,
 * perform tasks that would output information to the user.
 *
 * Input: The user will input how many food types there are, the
 * name and maximum amount of each food type, how many gerbils
 * there are, the id, name, amount eaten of each food, bite and
 * flight risk, and what command they would like to compute. 
 *
 * Output: This program is capable of printing information about a
 * gerbil searched for and providing the average amount of food each
 * gerbil has consumed of the total maximum amount
 *
 ******************************************************************/

import java.util.Scanner;


public class Assignment4 
{
	public static Food[] foo;
	public static Gerbil[] gerb;
	public static int position;
	public static int types;
	public static void main (String [] args)
	{
		String response = "undecided";
		Scanner keyboard = new Scanner(System.in);
		
		while (!response.equalsIgnoreCase("quit"))
		{
			if (response == "undecided"
					|| response.equalsIgnoreCase("restart"))
			{
				System.out.println("Please enter how many types of "
						+ "food the gerbils eat");
				
				types = keyboard.nextInt();
				//System.out.println(types);
				
				foo = new Food[types];
				
				for (int i = 0; i < types; i++) 
				{
					foo[i] = new Food();
					
					int num = i + 1;
					
					keyboard.nextLine();
					
					System.out.println("What is the name of food "
							+ "item #" + num + "?");
					foo[i].setFoodName(keyboard.nextLine()); 
					//System.out.println(foo[i].getFoodName());
					
					System.out.println("What is the maximum amount "
							+ "of food item #" + num + " that a "
									+ "gerbil can consume?");
					foo[i].setMaxAmount(Integer.parseInt(keyboard.next())); 
					//System.out.println(foo[i].getMaxAmount());
				}
				
				//printfood();
				
				int gernum;
				
				System.out.println("How many gerbils are in the "
						+ "lab?");
				gernum = keyboard.nextInt();
				//System.out.println(gernum);
				
				gerb = new Gerbil[gernum];
				
				for(int i = 0; i < gernum; i++)
				{
					gerb[i] = new Gerbil();
					
					int num1 = i + 1;
					
					System.out.println("What is gerbil #" + num1 
							+ "'s" + " id?");
					gerb[i].setId(keyboard.next()); 
					//System.out.println(gerb[i].getId());
					
					for (int k = 0; k < i; k++)
					{
						while (gerb[i].getId().equals(gerb[k].getId()))
						{
							System.out.println("Error. You have "
									+ "already entered this ID. "
									+ "Gerbils cannot have the "
									+ "same ID.");
								
							System.out.println("What is gerbil #" 
									+ num1 + "'s" + " id?");
							gerb[i].setId(keyboard.next());
							//System.out.println(gerb[i].getId());
						}				
					}
					
					keyboard.nextLine();
					
					System.out.println("What name was gerbil #" 
							+ num1 + " given?");
					gerb[i].setName(keyboard.nextLine());
					//System.out.println(gerb[i].getName());
					
					int[] af = new int[types];
					Integer[] amFood = new Integer[types];
					
					for (int j = 0; j < types; j++)
					{
						System.out.println("How much of " 
								+ foo[j].getFoodName() + "s does "
								+ gerb[i].getId() + " eat daily?");
						af[j] = keyboard.nextInt();
						
						int max = foo[j].getMaxAmount();
						
						while (af[j] > max)
						{
							System.out.println("Error. The number "
									+ "cannot exceed the maximum "
									+ "allowed per day.");
							
							System.out.println("How much of " 
									+ foo[j].getFoodName() + "s does "
									+ gerb[i].getId() + " eat daily?");
							af[j] = keyboard.nextInt();
						}
						
						
						amFood[j] = af[j];
						gerb[i].setAmountFood(amFood);
						//System.out.println(gerb[i].getAmountFood(j));
						
						
					}
					
					/*System.out.println();
					for (int l = 0; l < types; l++){
						System.out.println("Food Name:" + foo[l].getFoodName());
						System.out.println("Max Allowed:" + foo[l].getMaxAmount());
					}
					System.out.println("# of Gerbils:" + gernum);
					System.out.println("ID:" + gerb[i].getId());
					System.out.println("Name:" + gerb[i].getName());
					for (int m = 0; m < types; m++){
						System.out.println("Amount Eaten:" + gerb[i].getAmountFood(m));
					}*/
					
					double x = 0;
					double y = 0;
					
					for (int l = 0; l < types; l++)
					{
						x += foo[l].getMaxAmount();
						y += gerb[i].getAmountFood(l);
					}
					
					//System.out.println("x=" + x);
					//System.out.println("y=" + y);
					//System.out.println(y/x);
					
					double z1 = (y/x) * 100;
					int z = (int)z1;
					
					//System.out.println(z1);
					//System.out.println(z);
					
					gerb[i].setPercentage(z);
					//System.out.println(gerb[i].getPercentage());
					
					String[] test1 = new String[gernum];
					
					System.out.println("Does " + gerb[i].getId() 
							+ " bite?");
					test1[i] = keyboard.next();
					
					if (!test1[i].equalsIgnoreCase("True") && 
							!test1[i].equalsIgnoreCase("False"))
					{
						System.out.println("Please enter a valid "
								+ "input. Enter \"True\" if the "
								+ "gerbil does bite. Enter \"False\""
								+ " if the gerbil does not.");
						
						System.out.println("Does " + gerb[i].getId() 
								+ " bite?");
						test1[i] = keyboard.next();
					}
					else
					{
						if (test1[i].equalsIgnoreCase("True"))
						{
							gerb[i].setBite(Boolean.parseBoolean("True"));
							gerb[i].setWillBite("will bite");
						}
						if (test1[i].equalsIgnoreCase("False"))
						{
							gerb[i].setBite(Boolean.parseBoolean("False"));
							gerb[i].setWillBite("will not bite");
						}
					}
					//System.out.println(gerb[i].getBite());
					
					String[] test2 = new String[gernum];
					
					System.out.println("Does " + gerb[i].getId() 
							+ " try to escape?");
					test2[i] = keyboard.next();
						
					if (!test2[i].equalsIgnoreCase("True") && 
							!test2[i].equalsIgnoreCase("False"))
					{
						System.out.println("Please enter a valid "
								+ "input. Enter \"True\" if the "
								+ "gerbil does try to escape. Enter "
								+ "\"False\" if the gerbil does not.");
						System.out.println("Does " + gerb[i].getId() 
								+ " try to escape?");
						test2[i] = keyboard.next();
					}
					else
					{
						if (test2[i].equalsIgnoreCase("True"))
						{
							gerb[i].setFlight(Boolean.parseBoolean("True"));
							gerb[i].setWillFlight("will escape");
						}
						if (test2[i].equalsIgnoreCase("False"))
						{
							gerb[i].setFlight(Boolean.parseBoolean("False"));
							gerb[i].setWillFlight("will not escape");
						}
					}
					//System.out.println(gerb[i].getFlight());
				}
				
									
			}
			else if (response.equalsIgnoreCase("average"))
			{
				System.out.println(averageFood());
			}
			else if (response.equalsIgnoreCase("search"))
			{
				System.out.println("Please enter a gerbil's lab id:");
				String enterid = keyboard.next();
				
				String check = null;
				
				while (check == null)
				{	
					for (int i = 0; i < gerb.length; i++)
					{
						if (!enterid.equals(gerb[i].getId()))
						{
							check += null;
						}
						if (enterid.equals(gerb[i].getId()))
						{
							position = i;
														
							String result = "";
							
							result += ("Name: " + searchForGerbil().getName() + " (" 
									+ searchForGerbil().getWillFlight() + ", "
									+ searchForGerbil().getWillBite() + "), Food: ");
							for (int j = 0; j < foo.length; j++)
							{
								if (j > 0)
								{
									result += (", ");
								}
								result += (foo[j].getFoodName() + " - " 
										+ searchForGerbil().getAmountFood(j) + "/" + foo[j].getMaxAmount());
							}
							
							System.out.println(result);
							
							check += ("true");
						}	
					}
					if (check == null)
					{
						System.out.println("ERROR. Gerbil not "
							+ "found");
					
						System.out.println("Please enter a "
							+ "gerbil's lab id:");
						enterid = keyboard.next();
					}
				
				
					
						
					
				
				}
			}
			else
			{
				
			}
			System.out.println("What information would you like to"
					+ " know?");	
			response = keyboard.next();
	       	while (!response.equalsIgnoreCase("average") 
	       			&& !response.equalsIgnoreCase("search")
	       			&& !response.equalsIgnoreCase("restart")
	       			&& !response.equalsIgnoreCase("quit"))
	       	{ 
	       		System.out.println("ERROR. \"" + response + "\" is not a "
	       				+ "valid input. Please enter either \"average\""
	       				+ ", \"search\", \"restart\", or \"quit\".");
	       		System.out.println("What information would you like to"
	    				+ " know?");
	       		response = keyboard.next();
	       	}	
		}
			
		
		
	}

/*	public static void printfood() {
		for(int i =0; i<foo.length; i++) {
			System.out.println(foo[i].getFoodName());
			System.out.println(foo[i].getMaxAmount());
		}
	}*/
	
	public static String averageFood() 
	{
		String output = "";
		
		output = compareTo();
		
		
		return output;
	}


	public static Gerbil searchForGerbil()
	{
		Gerbil found = new Gerbil();
		
		found = gerb[position];
							
		return found;
	}
	
	public static String compareTo()
	{
		String alpha = "";
		String sort = "";
		String[] retry = new String[gerb.length];
		int next = 0;
		int k = 0;
		int store = 0;
		
		for (int i = 0; i < gerb.length; i++)
		{
			for (int j = 0; j < gerb.length; j++)
			{
				String s1 = gerb[i].getId();
				String s2 = gerb[j].getId();
				if (s1.compareTo(s2) <= 0)
				{
					alpha = "yes";
					k = 1;
				}
				else if (s1.compareTo(s2) > 0)
				{
					alpha = "";
				}
				
			}
			if (alpha == "yes")
			{
				sort += (gerb[i].getId() + " (" + gerb[i].getName() + ") "
						+ gerb[i].getPercentage() + "%" + "\n");
			}
			else if (alpha == "")
			{
				retry[i] = (gerb[i].getId() + " (" + gerb[i].getName() + ") "
						+ gerb[i].getPercentage() + "%" + "\n");	
			}
			if (gerb.length < 2)
			{
				//sort += (gerb[i].getId() + " (" + gerb[i].getName() + ") "
						//+ gerb[i].getPercentage() + "%" + "\n");
			}	
		}
		
		
		while (store <= next)
		{	
			if (alpha == "yes")
			{	
				next = gerb.length - k;
			}
			for (int i = 0; i < next; i++)
			{
				for (int j = 0; j < next; j++)
				{
					String s1 = retry[i];
					if (j > next)
					{
						sort += retry[i];
					}
					else
					{	
						String s2 = retry[j];
						if (s1.compareTo(s2) <= 0)
						{
							alpha = "yes";
							k++;
						}
						else if (s1.compareTo(s2) > 0)
						{
							alpha = "";
						}
					}
					store = j;
				}
				if (alpha == "yes")
				{
					sort += retry[i];
				}
				else if (alpha == "")
				{
					retry[i] = (gerb[i].getId() + " (" + gerb[i].getName() + ") "
							+ gerb[i].getPercentage() + "%" + "\n");
				}
			}
		}
		sort += retry[0];
		
		return sort;
		
	}
}

