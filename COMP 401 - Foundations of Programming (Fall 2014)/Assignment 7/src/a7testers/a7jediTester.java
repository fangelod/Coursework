package a7testers;
import a7jedi.Belt;
import a7jedi.SpoilageCollector;
import comp401.sushi.*;
import comp401.sushi.Nigiri.NigiriType;
import comp401.sushi.Sashimi.SashimiType;
public class a7jediTester {
	public static void main(String[] args){
		try{
			Sushi seafood = new Nigiri(NigiriType.EEL);
			Sushi shellfish = new Sashimi(SashimiType.SHRIMP);
			Ingredient[] ingredients = new Ingredient[1];
			ingredients[0]=new Rice(1);
			Roll veggie = new Roll(ingredients);
			Plate shellplate = new BluePlate(shellfish);
			Belt belt = new Belt(5);
			Plate veggieplate = new GreenPlate(veggie);
			
			
			belt.setPlateAtPosition(shellplate, 0);
			
			
		
			Plate seaplate = new GoldPlate(seafood,15);
			belt.setPlateNearestToPosition(seaplate, 1);
			belt.setPlateNearestToPosition(veggieplate, 2);
			for(int i = 0; i<belt.getSize();i++){
				System.out.println(belt.getPlateAtPosition(i));
			}
			
			System.out.println("Belt initialized");
			
			System.out.println("Cost of plate shellfish plate: "+shellplate.getPrice());
			System.out.println("Cost of plate seafood plate: "+seaplate.getPrice());
			System.out.println("Cost of plate veggie plate: "+veggieplate.getPrice());
			
			Ingredient[] shellfishIngr = shellplate.getContents().getIngredients();
			Ingredient[] seafoodIngr = seaplate.getContents().getIngredients();
			Ingredient[] veggieIngr = veggieplate.getContents().getIngredients();
			
			double shellamount = 0;
			double seaamount=0;
			double veggieamount = 0;
			
			for(int i = 0; i<shellfishIngr.length;i++)
				shellamount+=shellfishIngr[i].getAmount();
			
			for(int i = 0; i<seafoodIngr.length;i++)
				seaamount+=seafoodIngr[i].getAmount();
			
			for(int i = 0; i<veggieIngr.length;i++)
				veggieamount+=veggieIngr[i].getAmount();
			
			System.out.println("Amount of shellfish: "+shellamount);
			System.out.println("Amount of seafood: "+seaamount);
			System.out.println("Amount of veggie: "+veggieamount);
			
			SpoilageCollector collector = new SpoilageCollector();
			System.out.println("Making spoilage collecter "+ collector);
			belt.addObserver(collector);
			System.out.println("Added spoilage collector to the belt");
			
			System.out.println("Should be 0: "+collector.getTotalSpoiledCost());
			System.out.println("Should be 0: "+collector.getTotalSpoiledFood());
			System.out.println("Should be 0: "+collector.getTotalSpoiledSeafood());
			System.out.println("Should be 0: "+collector.getTotalSpoiledShellfish());
			
			System.out.println("Beginning rotations...");
			
			for(int i = 0; i<belt.getSize(); i++)
				belt.rotate();
			
			System.out.println("After 1 full revolution of the belt...");
			System.out.println("The belt should have not have the blue plate in position 0");
			for(int i = 0; i<belt.getSize();i++){
				System.out.println(belt.getPlateAtPosition(i));
			}
			
			System.out.println("Total cost should be .86: "+collector.getTotalSpoiledCost());
			System.out.println("Total shellfish amount should be .75: "+ collector.getTotalSpoiledShellfish());
			System.out.println("Total sea food should be .75: "+collector.getTotalSpoiledSeafood());
			System.out.println("Total food should be .75: "+collector.getTotalSpoiledFood());
			
			System.out.println("After 2 full revolutions of the belt...");
			for(int i = 0; i<belt.getSize(); i++)
				belt.rotate();
			System.out.println("The belt should have not have the gold plate in position 1");
			for(int i = 0; i<belt.getSize();i++){
				System.out.println(belt.getPlateAtPosition(i));
			}
			System.out.println("Total cost should be 1.92: "+collector.getTotalSpoiledCost());
			System.out.println("Total shellfish amount should be .75: "+ collector.getTotalSpoiledShellfish());
			System.out.println("Total sea food should be 1.5: "+collector.getTotalSpoiledSeafood());
			System.out.println("Total food should be 2: "+collector.getTotalSpoiledFood());
			
			System.out.println("After 3 full revolutions of the belt...");
			for(int i = 0; i<belt.getSize(); i++)
				belt.rotate();
			System.out.println("The belt should have not any plates");
			for(int i = 0; i<belt.getSize();i++){
				System.out.println(belt.getPlateAtPosition(i));
			}
			System.out.println("Total cost should be 2.18: "+collector.getTotalSpoiledCost());
			System.out.println("Total shellfish amount should be .75: "+ collector.getTotalSpoiledShellfish());
			System.out.println("Total sea food should be 1.5: "+collector.getTotalSpoiledSeafood());
			System.out.println("Total food should be 3: "+collector.getTotalSpoiledFood());
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
