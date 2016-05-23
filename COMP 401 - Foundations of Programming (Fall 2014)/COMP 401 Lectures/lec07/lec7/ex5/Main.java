package lec7.ex5;

public class Main {

	public static void main(String[] args) {
		
		Triangle t = new Triangle(1.5, 2.5, 3.4, 6.7, 5.1, 0.6);

		System.out.println("Barycenter is at: (" + 
						   t.getBarycenter().getX() + ", " +
						   t.getBarycenter().getY() + ")");

		t.move(-2.4, -6.1);

		System.out.println("Barycenter is at: (" + 
				   t.getBarycenter().getX() + ", " +
				   t.getBarycenter().getY() + ")");

		t.move(new Point(0,0));
		
		System.out.println("Barycenter is at: (" + 
				   t.getBarycenter().getX() + ", " +
				   t.getBarycenter().getY() + ")");

	}

}
