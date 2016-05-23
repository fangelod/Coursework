package lec7.ex8;

public class Song {

	public enum Genre {NONE, POP, RAP, JAZZ, INDIE, CLASSICAL};

	private String name;
	private String artist;
	private Genre genre;
	
	Song(String name, String artist) {
		this(name, artist, Genre.NONE);
	}
	
	Song(String name, String artist, Genre genre) {
		this.name = name;
		this.artist = artist;
		this.genre = genre;
	}
	
	public String getName() {
		return name;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public Genre getGenre() {
		return genre;
	}
	
}
