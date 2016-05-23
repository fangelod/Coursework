package lec11.ex5;

public class NegativeSongLengthException extends RuntimeException {

	private double illegal_length;
	
	public NegativeSongLengthException(double length) {
		super("Length cannot be negative.");
		illegal_length = length;
	}
	
	public double getIllegalLength() {
		return illegal_length;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + " (" + illegal_length + ")";
	}

}
