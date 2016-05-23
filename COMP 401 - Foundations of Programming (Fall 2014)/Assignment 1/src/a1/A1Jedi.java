package a1;

import java.util.Scanner;

public class A1Jedi
{
	public static void main (String[] args)
	{
		Scanner s = new Scanner(System.in);
		
		process(s);
	}
	
	public static void process(Scanner s)
	{
		int quanmax = 0;
		double costmax = 0;
		double avgcostmax = 0;
		String quanitem = "";
		String costitem = "";
		String avgcostitem = "";
		
		
		int stop = 100;
		String input[][] = new String[stop][3];
		double calccost[] = new double[stop];
		String quantrackname[] = new String[stop];
		int quantracknum[] = new int[stop];
		String costtrackname[] = new String[stop];
		double costtracknum[] = new double[stop];
		
		for (int i = 0; i < stop; i++) {
			System.out.println("Item Name?");
			input[i][0] = s.next();
			
			if(input[i][0].equalsIgnoreCase("end")) {
				stop = i;
			}
			
			else {
			System.out.println("Quantity?");
			input[i][1] = s.next();
			
			System.out.println("Cost per Item?");
			double cost = s.nextDouble();
			input[i][2] = String.valueOf(cost); 
			
			double x = Double.parseDouble(input[i][1]);
			double y = Double.parseDouble(input[i][2]);
			double z = x * y;
			calccost[i] = z;	
			}
		}
				
		quantrackname[0] = input[0][0];
		int k = 1;
		for (int i = 0; i < stop; i++) {
			for (int j = 0; j < stop; j++) {
				if (quantrackname[i].equalsIgnoreCase(input[j][0])) {
					quantracknum[i] += Integer.parseInt(input[j][1]);
				}
				else {
					quantrackname[k] = input[j][0];
					k++;
				}
			}
		}
		for (int i = 0; i < stop; i++) {
			if (quantracknum[i] > quanmax) {
				quanmax = quantracknum[i];
				quanitem = quantrackname[i];
			}
		}
		
		costtrackname[0] = input[0][0];
		int l = 1;
		for (int i = 0; i < stop; i++) {
			for (int j = 0; j < stop; j++) {
				if (costtrackname[i].equalsIgnoreCase(input[j][0])) {
					costtracknum[i] += calccost[j];
				}	
				else {
					costtrackname[l] = input[j][0];
					l++;
				}	
			}
		}
		for (int i = 0; i < stop; i++) {
			if (costtracknum[i] > costmax) {
				costmax = costtracknum[i];
				costitem = costtrackname[i];
			}
		}
		
		double avgtrack[] = new double[stop];
		for (int i = 0; i < stop; i++) {
			avgtrack[i] = costtracknum[i] / quantracknum[i];
		}
		for (int i = 0; i < stop; i++) {
			if (avgtrack[i] > avgcostmax) {
				avgcostmax = avgtrack[i];
				avgcostitem = costtrackname[i];
			}
		}
		
		System.out.println("The largest count item with " + quanmax + " was: " + quanitem);
		System.out.println("The largest total cost item at " + costmax + " was: " + costitem);
		System.out.println("The largest average cost item at " + avgcostmax + " was: " + avgcostitem);
		
	}
}
