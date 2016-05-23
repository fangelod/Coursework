package ta.v10;

public class Triangle {

	Point a;
	Point b;
	Point c;
		
	Triangle(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	String category() {

		if ((side_ab() == side_bc()) && (side_bc() == side_ca())) {
			return "equilateral";
		} else if ((side_ab() == side_bc()) || (side_ab() == side_ca()) || (side_bc() == side_ca())) {
			return "isosceles";
		} else {
			return "scalene";
		}
	}

	double area() {
		double s = (side_ab()+side_bc()+side_ca())/2.0;

		return Math.sqrt(s*(s-side_ab())*(s-side_bc())*(s-side_ca()));		
	}

	double side_ab() {
		return a.distanceTo(b);
	}
	
	double side_bc() {
		return b.distanceTo(c);
	}
	
	double side_ca() {
		return c.distanceTo(a);
	}
}
