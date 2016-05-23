package lec17.ex2.v7;

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
		String rating_string = "";
		
		for (int i=0; i<5; i++) {
			if (rating > i) {
				rating_string += "\u2605";
			} else {
				rating_string += "\u2606";
			}
		}
		return title + " by " + artist + " (" + rating_string + ")";
	}
}

