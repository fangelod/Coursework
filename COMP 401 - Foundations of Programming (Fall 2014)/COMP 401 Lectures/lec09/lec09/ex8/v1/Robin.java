package lec09.ex8.v1;

public class Robin {

	private Point3D location;
	private int id;
	
	Robin(int id, Point3D location) {
		this.id = id;
		this.location = location;
	}
	
	public int getID() {
		return id;
	}

	public Point3D getLocation() {
		return location;
	}

	private void hop(Point3D destination) {
		System.out.println(getID() + " is hopping to " + destination.toString());		
	}

	private void walk(Point3D destination) {
		System.out.println(getID() + " is walking to " + destination.toString());		
	}

	private void fly(Point3D destination) {
		System.out.println(getID() + " is flying to " + destination.toString());		
	}

	public void move(Point3D destination) {
		double distance = getLocation().distanceTo(destination);
		
		if ((distance > 10) || (destination.getZ() > 0)) {
			fly(destination);
		} else {
			if (distance < 5) {
				walk(destination);
			} else {
				hop(destination);
			}
		}
		location = destination;
	}

	public void speak() {
		System.out.println(getID() + " says 'Chirp'");		
	}	
}
