package lec6.v7;

class CartesianPoint implements Point {

	private int x;
	private int y;
	
	CartesianPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double distanceTo(Point other) {
		return Math.sqrt((getX() - other.getX())*(getX() - other.getX()) +
				         (getY() - other.getY())*(getY() - other.getY()));
	}
	
	public int getX() {
		return x;
	}
		
	public int getY() {
		return y;
	}
	
	public boolean equals(Point other) {
		return ((getX() == other.getX()) && (getY() == other.getY()));
	}
}
