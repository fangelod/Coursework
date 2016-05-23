package a1;

import java.util.Scanner;

public class A1Novice {
	public static void main (String[] args) {
		Scanner s = new Scanner(System.in);
		
		process(s);
	}
	
	public static void process(Scanner s) {
		System.out.println("How many items?");
		int item = s.nextInt();
		
		String quanitem = "";
		int quannum = 0;
		String costitem = "";
		double costnum = 0;
		double sumcost = 0;
		double avgcost;
		
		for (int i=0; i<item; i++) {
			System.out.println("Item?");
			String name = s.next();
			
			System.out.println("Quantity?");
			int quan = s.nextInt();
			
			System.out.println("Cost per item?");
			double cost = s.nextDouble();
			
			double calccost = quan * cost;
			
			if (quan >= quannum) {
				quannum = quan;
				quanitem = name;
			}
			if (calccost >= costnum) {
				costitem = name;
			}
			sumcost += calccost;
		}
		
		avgcost = sumcost / item;
		
		System.out.println("The largest quantity item was: " + quanitem);
		System.out.println("The largest cost item was: " + costitem);
		System.out.println("Total cost: " + sumcost);
		System.out.println("Average cost: " + avgcost);
	}
}
