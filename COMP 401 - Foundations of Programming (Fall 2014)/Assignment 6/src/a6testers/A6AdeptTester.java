package a6testers;

import static org.junit.Assert.*;

import org.junit.Test;

import a6adept.Belt;
import a6adept.PlateCounter;
import a6adept.ProfitCounter;
import a6adept.BeltPlateException;
import a6adept.BeltFullException;

import comp401.sushi.*;

public class A6AdeptTester {

	@Test
	public void plateCounterTest() {
		/* This tests PlateCounter using only 
		 * setPlateAtPostion(), setPlateNearestToPostion(), and removePlateAtPosition()
		 */

		Belt b = new Belt(10);
		assertNotNull(b);

		PlateCounter counter = new PlateCounter();
		assertNotNull(counter);
		assertEquals(0, counter.getRedPlateCount());
		assertEquals(0, counter.getGreenPlateCount());
		assertEquals(0, counter.getBluePlateCount());
		assertEquals(0, counter.getGoldPlateCount());

		b.addObserver(counter);

		try {
			Plate red_plate = new RedPlate(new Nigiri(Nigiri.NigiriType.TUNA));
			Plate green_plate = new GreenPlate(new Sashimi(Sashimi.SashimiType.EEL));
			Plate blue_plate = new BluePlate(new Sashimi(Sashimi.SashimiType.TUNA));
			Plate gold_plate = new GoldPlate(new Sashimi(Sashimi.SashimiType.SALMON), 6.0);

			b.setPlateAtPosition(red_plate, 0);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateAtPosition(green_plate, 1);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateNearestToPosition(blue_plate, 0);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateNearestToPosition(gold_plate, 0);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(1, counter.getGoldPlateCount());

			b.removePlateAtPosition(1);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(1, counter.getGoldPlateCount());

			b.removePlateAtPosition(3);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(2);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			assertEquals(0, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());


			b.setPlateAtPosition(new RedPlate(new Nigiri(Nigiri.NigiriType.TUNA)), 8);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateAtPosition(new RedPlate(new Nigiri(Nigiri.NigiriType.TUNA)), 6);
			assertEquals(2, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateAtPosition(new RedPlate(new Nigiri(Nigiri.NigiriType.TUNA)), 4);
			assertEquals(3, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateAtPosition(new RedPlate(new Nigiri(Nigiri.NigiriType.TUNA)), 2);
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateNearestToPosition(new BluePlate(new Sashimi(Sashimi.SashimiType.TUNA)), 0);
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateNearestToPosition(new BluePlate(new Sashimi(Sashimi.SashimiType.TUNA)), 0);
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(2, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateNearestToPosition(new BluePlate(new Sashimi(Sashimi.SashimiType.TUNA)), 0);
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(3, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateNearestToPosition(new BluePlate(new Sashimi(Sashimi.SashimiType.TUNA)), 0);
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(4, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateNearestToPosition(new BluePlate(new Sashimi(Sashimi.SashimiType.TUNA)), 0);
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(5, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.setPlateNearestToPosition(new BluePlate(new Sashimi(Sashimi.SashimiType.TUNA)), 0);
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(6, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());


			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(5, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(4, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(4, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(3, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(4, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(3, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(3, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(2, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(3, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(2, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(2, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(2, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(0, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b.removePlateAtPosition(0);
			b.rotate();
			assertEquals(0, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

		} catch (Exception e) {
			fail("Unexpected exception");
		}		
	}

	@Test
	public void profitCounterTest() {
		/* This tests ProfitCounter using only 
		 * setPlateAtPostion(), setPlateNearestToPostion(), and removePlateAtPosition()
		 */

		try {
			Belt b = new Belt(5);

			Plate red1 = new RedPlate(new Sashimi(Sashimi.SashimiType.TUNA));
			Plate green1 = new GreenPlate(new Sashimi(Sashimi.SashimiType.TUNA));
			Plate green2 = new GreenPlate(new Nigiri(Nigiri.NigiriType.EEL));
			Plate blue1 = new BluePlate(new Sashimi(Sashimi.SashimiType.CRAB));
			Plate blue2 = new BluePlate(new Nigiri(Nigiri.NigiriType.CRAB));
			Plate gold1 = new GoldPlate(new Roll(new Ingredient[] {new Tuna(0.25), new Salmon(0.25), new Eel(0.25), new Seaweed(0.1)}), 7.50);

			double r1_profit = 0.5125;
			double gr1_profit = 1.5125;
			double gr2_profit = 0.9375;
			double b1_profit = 2.875;
			double b2_profit = 2.75;
			double go1_profit = 6.7975;
			
			
			assertEquals(r1_profit, red1.getProfit(), 0.01);
			assertEquals(gr1_profit, green1.getProfit(), 0.01);
			assertEquals(gr2_profit, green2.getProfit(), 0.01);
			assertEquals(b1_profit, blue1.getProfit(), 0.01);
			assertEquals(b2_profit, blue2.getProfit(), 0.01);
			assertEquals(go1_profit, gold1.getProfit(), 0.01);
			
			ProfitCounter p = new ProfitCounter();
			b.addObserver(p);
			double expected_total = 0.0;

			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);

			b.setPlateAtPosition(red1, 0);
			expected_total += r1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total, p.getAverageBeltProfit(), 0.01);
						
			b.setPlateAtPosition(green1, 2);
			expected_total += gr1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/2.0, p.getAverageBeltProfit(), 0.01);
			
			b.setPlateNearestToPosition(green2, 0);
			expected_total += gr2_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/3.0, p.getAverageBeltProfit(), 0.01);
			
			b.setPlateNearestToPosition(blue1, 2);
			expected_total += b1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/4.0, p.getAverageBeltProfit(), 0.01);

			b.setPlateAtPosition(blue2, 4);
			expected_total += b2_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/5.0, p.getAverageBeltProfit(), 0.01);

			b.removePlateAtPosition(4);
			expected_total -= b2_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/4.0, p.getAverageBeltProfit(), 0.01);

			b.removePlateAtPosition(3);
			expected_total -= b1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/3.0, p.getAverageBeltProfit(), 0.01);
			
			b.setPlateAtPosition(gold1, 3);
			expected_total += go1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/4.0, p.getAverageBeltProfit(), 0.01);
			
			b.removePlateAtPosition(2);
			expected_total -= gr1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/3.0, p.getAverageBeltProfit(), 0.01);

			b.removePlateAtPosition(1);
			expected_total -= gr2_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/2.0, p.getAverageBeltProfit(), 0.01);

			b.setPlateNearestToPosition(green1, 0);
			expected_total += gr1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/3.0, p.getAverageBeltProfit(), 0.01);
			
			b.removePlateAtPosition(0);
			expected_total -= r1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total/2.0, p.getAverageBeltProfit(), 0.01);

			b.removePlateAtPosition(1);
			expected_total -= gr1_profit;					
			assertEquals(expected_total, p.getTotalBeltProfit(), 0.01);
			assertEquals(expected_total, p.getAverageBeltProfit(), 0.01);

			b.removePlateAtPosition(3);
			assertEquals(0.0, p.getTotalBeltProfit(), 0.01);
			
		} catch (PlatePriceException e) {
			e.printStackTrace();
			fail("Unexpected plate price exception");
		} catch (BeltPlateException e) {
			e.printStackTrace();
			fail("Unexpected belt plate exception");
		} catch (BeltFullException e) {
			e.printStackTrace();
			fail("Unexpected belt full exception");
		}
	}

	@Test
	public void clearAndRemoveTest() {
		/* This test uses both removePlateAtPosition() and clearPosition().
		 * Profit and plate counters should be notified either way.
		 * This test also tests using two observers for the same belt.
		 */
		
		try {
			Belt b = new Belt(3);
			Plate r_plate = new RedPlate(new Sashimi(Sashimi.SashimiType.TUNA));
			Plate b_plate = new BluePlate(new Nigiri(Nigiri.NigiriType.SALMON));
			
			ProfitCounter profit_counter = new ProfitCounter();
			PlateCounter plate_counter = new PlateCounter();
			
			b.addObserver(profit_counter);
			b.addObserver(plate_counter);

			b.setPlateAtPosition(r_plate, 0);
			b.setPlateAtPosition(b_plate, 1);
			
			assertEquals(r_plate.getProfit()+b_plate.getProfit(), profit_counter.getTotalBeltProfit(), 0.01);
			assertEquals((r_plate.getProfit()+b_plate.getProfit())/2.0, profit_counter.getAverageBeltProfit(), 0.01);
			assertEquals(1, plate_counter.getRedPlateCount());
			assertEquals(1, plate_counter.getBluePlateCount());
			assertEquals(0, plate_counter.getGreenPlateCount());
			assertEquals(0, plate_counter.getGoldPlateCount());
			
			// The following should do nothing to affect profit/plate
			// counters because the position was already empty
			// before being cleared.
			b.clearPlateAtPosition(2);
			assertEquals(r_plate.getProfit()+b_plate.getProfit(), profit_counter.getTotalBeltProfit(), 0.01);
			assertEquals((r_plate.getProfit()+b_plate.getProfit())/2.0, profit_counter.getAverageBeltProfit(), 0.01);
			assertEquals(1, plate_counter.getRedPlateCount());
			assertEquals(1, plate_counter.getBluePlateCount());
			assertEquals(0, plate_counter.getGreenPlateCount());
			assertEquals(0, plate_counter.getGoldPlateCount());

			// Using clearPosition to remove plate at 1 (i.e., the blue plate)
			b.clearPlateAtPosition(1);
			assertEquals(r_plate.getProfit(), profit_counter.getTotalBeltProfit(), 0.01);
			assertEquals(r_plate.getProfit(), profit_counter.getAverageBeltProfit(), 0.01);
			assertEquals(1, plate_counter.getRedPlateCount());
			assertEquals(0, plate_counter.getBluePlateCount());
			assertEquals(0, plate_counter.getGreenPlateCount());
			assertEquals(0, plate_counter.getGoldPlateCount());
			
			// Using removePlateAtPosition to remove plate at 0 (i.e., the red plate)
			b.removePlateAtPosition(0);
			assertEquals(0.0, profit_counter.getTotalBeltProfit(), 0.01);
			assertEquals(0, plate_counter.getRedPlateCount());
			assertEquals(0, plate_counter.getBluePlateCount());
			assertEquals(0, plate_counter.getGreenPlateCount());
			assertEquals(0, plate_counter.getGoldPlateCount());
			
			// Trying to clear position 0 should do nothing now with regard
			// to plate/profit counters.
			b.clearPlateAtPosition(0);
			assertEquals(0.0, profit_counter.getTotalBeltProfit(), 0.01);
			assertEquals(0, plate_counter.getRedPlateCount());
			assertEquals(0, plate_counter.getBluePlateCount());
			assertEquals(0, plate_counter.getGreenPlateCount());
			assertEquals(0, plate_counter.getGoldPlateCount());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		
	}

	public void twoBeltsOneCounterTest() {
		/* This tests uses the same
		 * plate counter for two different belts
		 * at the same time.
		 */
		
		try {
			Belt b1 = new Belt(3);
			Belt b2 = new Belt(3);
			
			Plate red1 = new RedPlate(new Sashimi(Sashimi.SashimiType.TUNA));
			Plate green1 = new GreenPlate(new Nigiri(Nigiri.NigiriType.TUNA));
			Plate blue1 = new BluePlate(new Nigiri(Nigiri.NigiriType.EEL));
			Plate gold1 = new GoldPlate(new Sashimi(Sashimi.SashimiType.CRAB), 5.0);

			PlateCounter counter = new PlateCounter();
			b1.addObserver(counter);
			b2.addObserver(counter);
			
			b1.setPlateAtPosition(red1, 0);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(0, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b2.setPlateAtPosition(green1, 0);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(0, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b1.setPlateAtPosition(blue1, 1);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b1.removePlateAtPosition(0);
			assertEquals(0, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b2.setPlateAtPosition(gold1, 1);
			assertEquals(0, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(1, counter.getGoldPlateCount());

			b2.setPlateAtPosition(red1, 2);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(1, counter.getGoldPlateCount());

			b2.removePlateAtPosition(1);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(0, counter.getGoldPlateCount());

			b1.setPlateAtPosition(gold1, 0);
			assertEquals(1, counter.getRedPlateCount());
			assertEquals(1, counter.getGreenPlateCount());
			assertEquals(1, counter.getBluePlateCount());
			assertEquals(1, counter.getGoldPlateCount());

			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
}
