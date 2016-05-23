package lec11.ex2;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		String haystack = "This is my haystack";
		
		Scanner s = new Scanner(System.in);
		while (s.hasNext()) {
			String needle = s.next();
			
			int position = haystack.indexOf(needle);
			
			if (position == -1) {
				System.out.println("Error: could not find needle in haystack");
			} else {
				System.out.println("Found needle at " + position);
			}
		}
		s.close();
	}
}
