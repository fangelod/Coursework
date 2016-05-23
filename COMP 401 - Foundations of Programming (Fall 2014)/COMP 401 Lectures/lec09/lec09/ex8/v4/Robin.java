package lec09.ex8.v4;

public class Robin extends AnimalImpl implements Animal {

	Robin(int id, Point3D location) {
		super(id, location);
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

	@Override
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
		super.move(destination);
	}

	public void speak() {
		System.out.println(getID() + " says 'Chirp'");		
	}	
}
