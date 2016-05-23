package lec11.ex8;

public class RatingOutOfRangeException extends Exception {

	private int out_of_range_rating;
	
	public RatingOutOfRangeException(int rating) {
		super("Rating out of range");
		
		out_of_range_rating = rating;
	}
	
	public int getOutOfRangeRating() {
		return out_of_range_rating;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + " (" + out_of_range_rating + ")";
	}


}
