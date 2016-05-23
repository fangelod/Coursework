package lec11.ex6;

public class Song {

	private String name;
	private String artist;
	private double length;
	private int rating;

	public Song(String name, String artist, 
			double length, int rating) {

		if (length < 0) {
			throw new NegativeSongLengthException(length);
		}
		if (rating < 0 || rating > 5) {
			throw new RatingOutOfRangeException(rating);
		}
		if (name.equals("") || artist.equals("")) {
			throw new IllegalArgumentException("Name or Artist is empty string");
		}

		this.name = name;
		this.artist = artist;
		this.length = length;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public String getArtist() {
		return artist;
	}

	public double getLength() {
		return length;
	}

	public int getRating() {
		return rating;
	}

	public String toString() {
		return "\"" + getName() + "\"" + " by " + getArtist() + " (" + getLength() + ", " + getRating() + " stars)";
	}
}