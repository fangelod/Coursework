package lec09.ex8.v3;

public class BlackBear extends AnimalImpl implements Animal {

	
	BlackBear(int id, Point3D location) {
		this.id = id;
		this.location = location;
	}
	
	public int getID() {
		return id;
	}

	public Point3D getLocation() {
		return location;
	}
	
	private void lumber(Point3D destination) {
		System.out.println(getID() + " is lumbering to " + destination.toString());
	}

	private void trek(Point3D destination) {
		System.out.println(getID() + " is trekking to " + destination.toString());
	}

	public void move(Point3D destination) {
		if (destination.getZ() > 0) {
			throw new RuntimeException("Bears can't fly");
		}
		
		if (getLocation().distanceTo(destination) < 10) {
			lumber(destination);
		} else {
			trek(destination);
		}
		location = destination;
	}

	public void speak() {
		System.out.println(getID() + " says 'Growl!'");
	}
	
	public String getColor() {
		return "black";
	}

}
