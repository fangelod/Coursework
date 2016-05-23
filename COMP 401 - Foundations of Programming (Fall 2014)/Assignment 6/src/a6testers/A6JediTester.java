package a6testers;

import static org.junit.Assert.*;

import org.junit.Test;

import comp401.sushi.*;
import a6jedi.*;

public class A6JediTester {

	@Test
	public void simpleCustomerTest() {
		try {
			Belt b = new Belt(3);
			Plate p = new RedPlate(new Sashimi(Sashimi.SashimiType.TUNA));
			TestCustomer c = new TestCustomer();

			b.setPlateAtPosition(p, 0);

			b.registerCustomerAtPosition(c, 1);
			assertNull(c.getLastBelt());
			assertNull(c.getLastPlate());
			assertEquals(-1, c.getLastPosition());

			b.rotate();
			assertEquals(b, c.getLastBelt());
			assertEquals(p, c.getLastPlate());
			assertEquals(1, c.getLastPosition());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}		
	}

	@Test
	public void positionAlreadyOccupiedTest() {
		try {
			Belt b = new Belt(3);
			TestCustomer c1 = new TestCustomer();
			TestCustomer c2 = new TestCustomer();
			b.registerCustomerAtPosition(c1, 0);
			b.registerCustomerAtPosition(c2, 0);
			fail("Should have thrown exception trying to register second customer in same place as first customer");
		} catch (RuntimeException e) {
		}
	}

	@Test
	public void customerAlreadyRegisteredTest() {
		try {
			Belt b = new Belt(3);
			TestCustomer c = new TestCustomer();
			b.registerCustomerAtPosition(c, 0);
			b.registerCustomerAtPosition(c, 1);
			fail("Should have thrown exception trying to register same customer twice");
		} catch (RuntimeException e) {
		}		
	}

	@Test
	public void customerUnregisterTest() {
		try {
			Belt b = new Belt(3);
			Plate p = new RedPlate(new Sashimi(Sashimi.SashimiType.TUNA));
			TestCustomer c = new TestCustomer();

			b.registerCustomerAtPosition(c, 1);
			b.setPlateAtPosition(p, 0);

			b.rotate();
			assertEquals(b, c.getLastBelt());
			assertEquals(p, c.getLastPlate());
			assertEquals(1, c.getLastPosition());			

			c.clear();
			b.rotate();
			assertNull(c.getLastBelt());
			assertNull(c.getLastPlate());
			assertEquals(-1, c.getLastPosition());			

			c.clear();
			b.rotate();
			assertNull(c.getLastBelt());
			assertNull(c.getLastPlate());
			assertEquals(-1, c.getLastPosition());			

			c.clear();
			b.rotate();
			assertEquals(b, c.getLastBelt());
			assertEquals(p, c.getLastPlate());
			assertEquals(1, c.getLastPosition());			

			c.clear();
			b.unregisterCustomerAtPosition(1);
			b.rotate();
			b.rotate();
			b.rotate();
			assertNull(c.getLastBelt());
			assertNull(c.getLastPlate());
			assertEquals(-1, c.getLastPosition());			

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}

	@Test
	public void twoCustomerTest() {
		try {
			Belt b = new Belt(4);
			Plate red_plate = new RedPlate(new Sashimi(Sashimi.SashimiType.TUNA));
			Plate blue_plate = new BluePlate(new Nigiri(Nigiri.NigiriType.CRAB));
			
			TestCustomer c1 = new TestCustomer();
			TestCustomer c2 = new TestCustomer();
			
			b.registerCustomerAtPosition(c1, 2);
			b.registerCustomerAtPosition(c2, 3);
			
			b.setPlateAtPosition(red_plate, 0);
			b.setPlateAtPosition(blue_plate, 1);

			b.rotate();
			assertEquals(blue_plate, c1.getLastPlate());
			assertEquals(b, c1.getLastBelt());
			assertEquals(2, c1.getLastPosition());
			assertNull(c2.getLastPlate());
			assertNull(c2.getLastBelt());
			assertEquals(-1, c2.getLastPosition());
			c1.clear();
			c2.clear();
			
			b.rotate();
			assertEquals(red_plate, c1.getLastPlate());
			assertEquals(b, c1.getLastBelt());
			assertEquals(2, c1.getLastPosition());			
			assertEquals(blue_plate, c2.getLastPlate());
			assertEquals(b, c2.getLastBelt());
			assertEquals(3, c2.getLastPosition());
			c1.clear();
			c2.clear();

			b.rotate();
			assertNull(c1.getLastPlate());
			assertNull(c1.getLastBelt());
			assertEquals(-1, c1.getLastPosition());
			assertEquals(red_plate, c2.getLastPlate());
			assertEquals(b, c2.getLastBelt());
			assertEquals(3, c2.getLastPosition());			
			c1.clear();
			c2.clear();

			b.rotate();
			assertNull(c1.getLastPlate());
			assertNull(c1.getLastBelt());
			assertEquals(-1, c1.getLastPosition());
			assertNull(c2.getLastPlate());
			assertNull(c2.getLastBelt());
			assertEquals(-1, c2.getLastPosition());
			c1.clear();
			c2.clear();

			b.unregisterCustomerAtPosition(2);
			b.rotate();
			assertNull(c1.getLastPlate());
			assertNull(c1.getLastBelt());
			assertEquals(-1, c1.getLastPosition());
			assertNull(c2.getLastPlate());
			assertNull(c2.getLastBelt());
			assertEquals(-1, c2.getLastPosition());
			c1.clear();
			c2.clear();

			b.rotate();
			assertNull(c1.getLastPlate());
			assertNull(c1.getLastBelt());
			assertEquals(-1, c1.getLastPosition());
			assertEquals(blue_plate, c2.getLastPlate());
			assertEquals(b, c2.getLastBelt());
			assertEquals(3, c2.getLastPosition());
			c1.clear();
			c2.clear();

			b.rotate();
			assertNull(c1.getLastPlate());
			assertNull(c1.getLastBelt());
			assertEquals(-1, c1.getLastPosition());
			assertEquals(red_plate, c2.getLastPlate());
			assertEquals(b, c2.getLastBelt());
			assertEquals(3, c2.getLastPosition());
			c1.clear();
			c2.clear();

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}

}

class TestCustomer implements Customer {

	private Belt last_belt;
	private Plate last_plate;
	private int last_position;

	public TestCustomer() {
		clear();
	}

	@Override
	public void observePlateOnBelt(Belt b, Plate p, int position) {
		last_belt = b;
		last_plate = p;
		last_position = position;
	}

	public Belt getLastBelt() {
		return last_belt;
	}

	public Plate getLastPlate() {
		return last_plate;
	}

	public int getLastPosition() {
		return last_position;
	}

	public void clear() {
		last_belt = null;
		last_plate = null;
		last_position = -1;
	}

}