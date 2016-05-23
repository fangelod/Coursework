package ta.v11;

public class Point {
	double x;
	double y;
	
	Point (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	double distanceTo(Point other_point) {
		return Math.sqrt((this.x - other_point.x)*(this.x - other_point.x) +
						 (this.y - other_point.y)*(this.y - other_point.y));
						
	}
}
