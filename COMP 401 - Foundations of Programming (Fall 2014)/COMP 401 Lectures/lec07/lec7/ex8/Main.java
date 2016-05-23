package lec7.ex8;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		Song s1 = new Song("In Our Talons", "Bowerbirds", Song.Genre.INDIE);
		Song s2 = new Song("Poker Face", "Lady Gaga", Song.Genre.POP);
		Song s3 = new Song("Screenwriter's Blues", "Soul Coughing", Song.Genre.RAP);
		
		System.out.println(s1.getGenre());
		System.out.println(s2.getGenre());
		System.out.println(s3.getGenre());
	}

}
