package lec7.ex7;

public class Main {

	public static void main(String[] args) {

		Song s1 = new Song("In Our Talons", "Bowerbirds", Song.GENRE_INDIE);
		Song s2 = new Song("Poker Face", "Lady Gaga", Song.GENRE_POP);
		Song s3 = new Song("Screenwriter's Blues", "Soul Coughing", -123);
		
		System.out.println(s1.getGenre());
		System.out.println(s2.getGenre());
		System.out.println(s3.getGenre());
		
		System.out.println(s1.getGenre() + s2.getGenre() + s3.getGenre());
	}

}
