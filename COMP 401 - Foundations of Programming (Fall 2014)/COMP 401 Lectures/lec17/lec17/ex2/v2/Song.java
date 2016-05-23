package lec17.ex2.v2;

public class Song {

	private String title;
	private String artist;
	private int rating;
	
	public Song(String title, String artist, int rating) {
		this.title = title;
		this.artist = artist;
		this.rating = rating;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String toString() {
		return title + " by " + artist + " (" + rating + " stars)";
	}
}

