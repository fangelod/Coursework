package lec11.ex3;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		while(s.hasNextLine()) {
			String name = s.nextLine();
			String artist = s.nextLine();
			double length = s.nextDouble();
			int rating = s.nextInt();
			
			Song a_song = new Song(name, artist, length, rating);
			
			System.out.println("Created song: " + a_song.toString());
			s.nextLine();
		}
		s.close();
	}

}
