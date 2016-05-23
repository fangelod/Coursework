package lec09.ex8.v5;

public class Main {

	public static void main(String[] args) {
		
		Animal b1 = new BlackBear(1, new Point3D(1, 1, 0));
		Animal b2 = new PolarBear(2, new Point3D(2, 2, 0));
		Animal py = new Python(3, new Point3D(3,3,0));
		Animal r = new Robin(4, new Point3D(4,4,0));
		
		b1.move(new Point3D(5, 5, 0));
		b2.move(new Point3D(100, 10, 0));
		py.move(new Point3D(-6, -5, 0));
		r.move(new Point3D(0, 0, 10));
		
		b1.speak();
		b2.speak();
		py.speak();
		r.speak();
	}

}
