package lec11.ex1;

import java.util.Scanner;

public class Main {

	public static int error_code;
	
	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		while (s.hasNextInt()) {
			do_something(s.nextInt());
			if (error_code != 0) {
				if (error_code == -1) {
					System.out.println("Negative number error.");
				} else if (error_code == -2) {
					System.out.println("Even number error.");
				}				
			}
		}
		s.close();
	}
	
	public static void do_something(int a) {
		// Clear out error code
		error_code = 0;

		if (a < 0) {
			// a should not be negative. Set error code and return.
			error_code = -1;
		} else if (a %2 == 0) {
			// a should not be even. Set error code and return.
			error_code = -2;
		} else {
			System.out.println("Successfully did something because a was odd!");
		}
	}
}
