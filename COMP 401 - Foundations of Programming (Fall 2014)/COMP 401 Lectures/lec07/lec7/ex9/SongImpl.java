package lec7.ex9;

public class SongImpl implements Song {


	private String name;
	private String artist;
	private Song.Genre genre;
	
	SongImpl(String name, String artist) {
		this(name, artist, Song.Genre.NONE);
	}
	
	SongImpl(String name, String artist, Song.Genre genre) {
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
	
	public Song.Genre getGenre() {
		return genre;
	}
	
}
