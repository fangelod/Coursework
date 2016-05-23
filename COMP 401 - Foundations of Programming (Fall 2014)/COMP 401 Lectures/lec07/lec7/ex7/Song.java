package lec7.ex7;

public class Song {
    public static final int GENRE_NONE = 0;
    public static final int GENRE_POP = 1;
    public static final int GENRE_RAP = 2;
    public static final int GENRE_JAZZ = 3;
    public static final int GENRE_INDIE = 4;
    public static final int GENRE_CLASSICAL = 5;

	private String name;
	private String artist;
	private int genre;
	
	Song(String name, String artist) {
		this(name, artist, GENRE_NONE);
	}
	
	Song(String name, String artist, int genre) {
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
	
	public int getGenre() {
		return genre;
	}
	
}
