package lec11.ex3;

public class Song {

	private String name;
	private String artist;
	private double length;
	private int rating;

	public Song(String name, String artist, 
			double length, int rating) {

		if (length < 0) {
			throw new RuntimeException("Length can not be negative");
		}
		if (rating < 0 || rating > 5) {
			throw new RuntimeException("Rating out of range.");
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