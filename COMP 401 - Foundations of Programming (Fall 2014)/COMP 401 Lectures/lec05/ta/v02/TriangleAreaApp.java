package ta.v02;

import java.util.Scanner;

public class TriangleAreaApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (sc.hasNext()) {
			String id = sc.next();
			if (id.equals("end")) {
				break;
			}
			double ax = sc.nextDouble();
			double ay = sc.nextDouble();
			double bx = sc.nextDouble();
			double by = sc.nextDouble();
			double cx = sc.nextDouble();
			double cy = sc.nextDouble();

			/*
			System.out.println("Triangle: " + id);
			System.out.println("A: (" + ax + ", " + ay + ")");
			System.out.println("B: (" + bx + ", " + by + ")");
			System.out.println("C: (" + cx + ", " + cy + ")");
			System.out.println("----");
			*/
			
			System.out.println("Triangle " + id + " is " + 
			                   triangle_category(ax, ay, bx, by, cx, cy));
		}
		
		sc.close();
	}
	
	static String triangle_category(double ax, double ay,
			double bx, double by,
			double cx, double cy) {

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

	static double point_distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
	}

}
