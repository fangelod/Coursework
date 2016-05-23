package ta.v08;

public class Triangle {

	double ax;
	double ay;
	double bx;
	double by;
	double cx;
	double cy;
	
	Triangle(double x1, double y1,
			 double x2, double y2,
			 double x3, double y3) {
		this.ax = x1;
		this.ay = y1;
		this.bx = x2;
		this.by = y2;
		this.cx = x3;
		this.cy = y3;
	}
	
	String category() {
		double side_ab = point_distance(ax, ay, bx, by);
		double side_bc = point_distance(bx, by, cx, cy);
		double side_ca = point_distance(cx, cy, ax, ay);

		if ((side_ab == side_bc) && (side_bc == side_ca)) {
			return "equilateral";
		} else if ((side_ab == side_bc) || (side_ab == side_ca) || (side_bc == side_ca)) {
			return "isosceles";
		} else {
			return "scalene";
		}
	}

	double area() {
		double side_ab = point_distance(ax, ay, bx, by);
		double side_bc = point_distance(bx, by, cx, cy);
		double side_ca = point_distance(cx, cy, ax, ay);

		double s = (side_ab+side_bc+side_ca)/2.0;
		return Math.sqrt(s*(s-side_ab)*(s-side_bc)*(s-side_ca));		
	}

	static double point_distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
	}

}
