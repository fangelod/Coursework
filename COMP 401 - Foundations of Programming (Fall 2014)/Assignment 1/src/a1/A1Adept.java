package a1;

import java.util.Scanner;

public class A1Adept {
	public static void main (String[] args) {
		Scanner s = new Scanner(System.in);
		
		process(s);
	}
	
	public static void process(Scanner s) {
		int quantitymax = 0;
		int quantitymin = 0;
		int costmax = 0;
		int costmin = 0;
		
		int quantrack[] = new int[10];
		double costtrack[] = new double[10];
		
		int quanhigh = 0;
		int quanlow = Integer.MAX_VALUE;
		double costhigh = 0;
		double costlow = Double.MAX_VALUE;
		
		int stop = 100;
		String input[][] = new String[stop][4];
		double calccost[] = new double[stop];
				
		for (int i = 0; i < stop; i++) {
			System.out.println("Item Name?");
			input[i][0] = s.next();
			
			if(input[i][0].equalsIgnoreCase("end")) {
				stop = i;
			}
			
			else {
			System.out.println("Category?");
			input[i][1] = s.next();
			
			System.out.println("Quantity?");
			input[i][2] = s.next();
			
			System.out.println("Cost per Item?");
			double cost = s.nextDouble();
			input[i][3] = String.valueOf(cost); 
			
			double x = Double.parseDouble(input[i][2]);
			double y = Double.parseDouble(input[i][3]);
			double z = x * y;
			calccost[i] = z;		
			}
		}
		
		for (int i = 0; i < stop; i++) {
			for (int j = 0; j < 10; j++) {
				if (input[i][1].equals(String.valueOf(j))) {
					quantrack[j] += Integer.parseInt(input[i][2]);
				}
			}
		}
		
		for (int j = 0; j < 10; j++) {
			if (quantrack[j] > quanhigh) {
				quanhigh = quantrack[j];
				quantitymax = j;
			}
			if (quantrack[j] < quanlow) {
				if (quantrack[j] == 0) {	
				}
				
				else {
				quanlow = quantrack[j];
				quantitymin = j;
				}
			}
		}
		
		for (int i = 0; i < stop; i++) {
			for (int j = 0; j < 10; j++) {
				if(input[i][1].equals(String.valueOf(j))) {
					costtrack[j] += calccost[i];
				}
			}
		}
		
		for (int j = 0; j < 10; j++) {
			if (costtrack[j] > costhigh) {
				costhigh = costtrack[j];
				costmax = j;
			}
			if (costtrack[j] < costlow) {
				if (costtrack[j] == 0) {	
				}
				
				else {
				costlow = costtrack[j];
				costmin = j;
				}
			}
		}
					
		System.out.println("Category with most items: " + quantitymax);
		System.out.println("Category with least items: " + quantitymin);
		System.out.println("Category with largest cost: " + costmax);
		System.out.println("Category with least cost: " + costmin);
		
	}
}
