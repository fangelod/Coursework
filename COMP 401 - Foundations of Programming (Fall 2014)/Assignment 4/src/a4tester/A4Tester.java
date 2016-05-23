package a4tester;

import static org.junit.Assert.*;

import org.junit.Test;

import a4.*;
import a4.Nigiri.NigiriType;
import a4.Sashimi.SashimiType;

public class A4Tester {

	@Test
	public void testRedPlate() {
		Plate red_plate = null;
		try {
			red_plate = new RedPlate(null);
			assertNotNull(red_plate);
			assertEquals(Plate.Color.RED, red_plate.getColor());
			assertEquals(1.0, red_plate.getPrice(), 0.01);
			assertEquals(0.0, red_plate.getProfit(), 0.01);
			assertNull(red_plate.getContents());
			assertNull(red_plate.removeContents());
			assertFalse(red_plate.hasContents());
			
		} catch (Exception e) {
			fail("Constructor with null argument should not fail. Should create an empty red plate.");
		}

		Sushi tuna_sashimi = new Sashimi(SashimiType.TUNA);
		Sushi eel_sashimi = new Sashimi(SashimiType.EEL);
		Sushi crab_nigiri = new Nigiri(NigiriType.CRAB);
		
		try {
			red_plate.setContents(tuna_sashimi);
			assertEquals(tuna_sashimi, red_plate.getContents());
			assertEquals(1.0-tuna_sashimi.getCost(), red_plate.getProfit(), 0.01);
			assertTrue(red_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Setting contents to tuna sashimi should not cause plate price exception");
		}
		
		try {
			red_plate.setContents(eel_sashimi);
			assertEquals(eel_sashimi, red_plate.getContents());
			assertEquals(1.0-eel_sashimi.getCost(), red_plate.getProfit(), 0.01);
			assertTrue(red_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Setting contents to eel sashimi should not cause plate price exception");
		}
		
		try {
			red_plate.setContents(null);
			fail("Should not be able to pass null to setContents");
		} catch (IllegalArgumentException e) {
			assertEquals(eel_sashimi, red_plate.getContents());
			assertTrue(red_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Expected IllegalArgumentException. Got PlatePriceException");
		}
		
		assertEquals(eel_sashimi, red_plate.removeContents());
		assertFalse(red_plate.hasContents());
		assertNull(red_plate.getContents());
		assertEquals(0.0, red_plate.getProfit(), 0.01);
		
		try {
			red_plate.setContents(crab_nigiri);
			fail("Should have cause plate price exception.");
		} catch (PlatePriceException e) {
		}
		
		try {
			red_plate = new RedPlate(crab_nigiri);
			fail("Should have caused plate price exception");
		} catch (PlatePriceException e) {
		}
	}

	
	@Test
	public void testGreenPlate() {
		Plate green_plate = null;
		try {
			green_plate = new GreenPlate(null);
			assertNotNull(green_plate);
			assertEquals(Plate.Color.GREEN, green_plate.getColor());
			assertEquals(2.0, green_plate.getPrice(), 0.01);
			assertEquals(0.0, green_plate.getProfit(), 0.01);
			assertNull(green_plate.getContents());
			assertNull(green_plate.removeContents());
			assertFalse(green_plate.hasContents());
			
		} catch (Exception e) {
			fail("Constructor with null argument should not fail. Should create an empty green plate.");
		}

		Sushi tuna_sashimi = new Sashimi(SashimiType.TUNA);
		Sushi eel_nigiri = new Nigiri(NigiriType.EEL);
		Sushi roll = new Roll(new Ingredient[] {new Salmon(1.0), new Crab(1.0), new Rice(1.0), new Seaweed(0.5), new Eel(1.0)});
		
		try {
			green_plate.setContents(tuna_sashimi);
			assertEquals(tuna_sashimi, green_plate.getContents());
			assertEquals(2.0-tuna_sashimi.getCost(), green_plate.getProfit(), 0.01);
			assertTrue(green_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Setting contents to tuna sashimi should not cause plate price exception");
		}
		
		try {
			green_plate.setContents(eel_nigiri);
			assertEquals(eel_nigiri, green_plate.getContents());
			assertEquals(2.0-eel_nigiri.getCost(), green_plate.getProfit(), 0.01);
			assertTrue(green_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Setting contents to crab nigiri should not cause plate price exception");
		}
		
		try {
			green_plate.setContents(null);
			fail("Should not be able to pass null to setContents");
		} catch (IllegalArgumentException e) {
			assertEquals(eel_nigiri, green_plate.getContents());
			assertTrue(green_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Expected IllegalArgumentException. Got PlatePriceException");
		}
		
		assertEquals(eel_nigiri, green_plate.removeContents());
		assertFalse(green_plate.hasContents());
		assertNull(green_plate.getContents());
		assertEquals(0.0, green_plate.getProfit(), 0.01);
		
		try {
			green_plate.setContents(roll);
			fail("Should have cause plate price exception.");
		} catch (PlatePriceException e) {
		}
		
		try {
			green_plate = new GreenPlate(roll);
			fail("Should have caused plate price exception");
		} catch (PlatePriceException e) {
		}
	}
	
	@Test
	public void testBluePlate() {
		Plate blue_plate = null;
		try {
			blue_plate = new BluePlate(null);
			assertNotNull(blue_plate);
			assertEquals(Plate.Color.BLUE, blue_plate.getColor());
			assertEquals(4.0, blue_plate.getPrice(), 0.01);
			assertEquals(0.0, blue_plate.getProfit(), 0.01);
			assertNull(blue_plate.getContents());
			assertNull(blue_plate.removeContents());
			assertFalse(blue_plate.hasContents());
			
		} catch (Exception e) {
			fail("Constructor with null argument should not fail. Should create an empty blue plate.");
		}

		Sushi tuna_sashimi = new Sashimi(SashimiType.TUNA);
		Sushi roll1 = new Roll(new Ingredient[] {new Salmon(1.0), new Crab(1.0), new Rice(1.0), new Seaweed(0.5), new Eel(1.0)});
		Sushi roll2 = new Roll(new Ingredient[] {new Salmon(2.0), new Crab(1.0), new Rice(1.0), new Seaweed(0.5), new Eel(1.0)});
		
		try {
			blue_plate.setContents(tuna_sashimi);
			assertEquals(tuna_sashimi, blue_plate.getContents());
			assertEquals(4.0-tuna_sashimi.getCost(), blue_plate.getProfit(), 0.01);
			assertTrue(blue_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Setting contents to tuna sashimi should not cause plate price exception");
		}
		
		try {
			blue_plate.setContents(roll1);
			assertEquals(roll1, blue_plate.getContents());
			assertEquals(4.0-roll1.getCost(), blue_plate.getProfit(), 0.01);
			assertTrue(blue_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Setting contents to roll with cost" + roll1.getCost() + " should not cause plate price exception");
		}
		
		try {
			blue_plate.setContents(null);
			fail("Should not be able to pass null to setContents");
		} catch (IllegalArgumentException e) {
			assertEquals(roll1, blue_plate.getContents());
			assertTrue(blue_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Expected IllegalArgumentException. Got PlatePriceException");
		}
		
		assertEquals(roll1, blue_plate.removeContents());
		assertFalse(blue_plate.hasContents());
		assertNull(blue_plate.getContents());
		assertEquals(0.0, blue_plate.getProfit(), 0.01);
		
		try {
			blue_plate.setContents(roll2);
			fail("Should have cause plate price exception.");
		} catch (PlatePriceException e) {
		}
		
		try {
			blue_plate = new BluePlate(roll2);
			fail("Should have caused plate price exception");
		} catch (PlatePriceException e) {
		}
	}

	@Test
	public void testGoldPlate() {
		Plate gold_plate = null;
		try {
			gold_plate = new GoldPlate(null, 6.50);
			assertNotNull(gold_plate);
			assertEquals(Plate.Color.GOLD, gold_plate.getColor());
			assertEquals(6.5, gold_plate.getPrice(), 0.01);
			assertEquals(0.0, gold_plate.getProfit(), 0.01);
			assertNull(gold_plate.getContents());
			assertNull(gold_plate.removeContents());
			assertFalse(gold_plate.hasContents());
			
		} catch (Exception e) {
			fail("Constructor with null argument should not fail. Should create an empty gold plate.");
		}

		Sushi tuna_sashimi = new Sashimi(SashimiType.TUNA);
		Sushi roll1 = new Roll(new Ingredient[] {new Salmon(2.0), new Crab(1.0), new Rice(1.0), new Seaweed(1.5), new Eel(1.0)});
		Sushi roll2 = new Roll(new Ingredient[] {new Salmon(2.0), new Crab(2.0), new Rice(1.0), new Seaweed(0.5), new Eel(3.0)});
		
		try {
			gold_plate.setContents(tuna_sashimi);
			assertEquals(tuna_sashimi, gold_plate.getContents());
			assertEquals(6.5-tuna_sashimi.getCost(), gold_plate.getProfit(), 0.01);
			assertTrue(gold_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Setting contents to tuna sashimi should not cause plate price exception");
		}
		
		try {
			gold_plate.setContents(roll1);
			assertEquals(roll1, gold_plate.getContents());
			assertEquals(6.5-roll1.getCost(), gold_plate.getProfit(), 0.01);
			assertTrue(gold_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Setting contents to roll with cost" + roll1.getCost() + " should not cause plate price exception");
		}
		
		try {
			gold_plate.setContents(null);
			fail("Should not be able to pass null to setContents");
		} catch (IllegalArgumentException e) {
			assertEquals(roll1, gold_plate.getContents());
			assertTrue(gold_plate.hasContents());
		} catch (PlatePriceException e) {
			fail("Expected IllegalArgumentException. Got PlatePriceException");
		}
		
		assertEquals(roll1, gold_plate.removeContents());
		assertFalse(gold_plate.hasContents());
		assertNull(gold_plate.getContents());
		assertEquals(0.0, gold_plate.getProfit(), 0.01);
		
		try {
			gold_plate.setContents(roll2);
			fail("Should have cause plate price exception.");
		} catch (PlatePriceException e) {
		}
		
		try {
			gold_plate = new GoldPlate(roll2, 6.5);
			fail("Should have caused plate price exception");
		} catch (PlatePriceException e) {
		}
		
		try {
			gold_plate = new GoldPlate(null, 3.5);
			fail("Should have caused IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		} catch (PlatePriceException e) {
			fail("Should not have caused PlatePriceException. Expected IllegalArgumentException");
		}
	}
}
