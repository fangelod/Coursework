package lec7.ex9;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		Song s1 = new SongImpl("In Our Talons", "Bowerbirds", Song.Genre.INDIE);
		Song s2 = new SongImpl("Poker Face", "Lady Gaga", Song.Genre.POP);
		Song s3 = new SongImpl("Screenwriter's Blues", "Soul Coughing", Song.Genre.RAP);
		
		System.out.println(s1.getGenre());
		System.out.println(s2.getGenre());
		System.out.println(s3.getGenre());
	
		Object o1 = (Object) s1;
		o1 = (Object) new Double(1.3);
		
		Song s5 = (SongImpl) o1;
	}

}
