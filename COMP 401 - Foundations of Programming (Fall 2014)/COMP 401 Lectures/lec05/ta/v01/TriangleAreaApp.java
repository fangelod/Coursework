package ta.v01;

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
		
			System.out.println("Triangle: " + id);
			System.out.println("A: (" + ax + ", " + ay + ")");
			System.out.println("B: (" + bx + ", " + by + ")");
			System.out.println("C: (" + cx + ", " + cy + ")");
			System.out.println("----");
		}
		
		sc.close();
	}

}
