package a7testers;
import a7adept.Belt;
import comp401.sushi.*;
import comp401.sushi.Nigiri.NigiriType;
public class a7adeptTester {
	public static void main(String[] args){
		try{
			Sushi sushi = new Nigiri(NigiriType.EEL);
			Plate blueplate = new BluePlate(sushi);
			Belt belt = new Belt(5);
			System.out.println("All 5 ages should be -1");
			for(int i = 0; i<belt.getSize();i++){
				System.out.println(belt.getAgeOfPlateAtPosition(i));
			}
			System.out.println("Adding blue plate at 0");
			belt.setPlateAtPosition(blueplate, 0);
			System.out.println("Age should be 0: "+belt.getAgeOfPlateAtPosition(0));
			System.out.println("Reference to the unwrapped BluePlate: "+belt.getPlateAtPosition(0));
			System.out.println("Rotate 5 times and watch age go up");
			for(int i = 0; i<belt.getSize();i++){
				belt.rotate();
				for(int j = 0; j<belt.getSize();j++){
					System.out.println(belt.getAgeOfPlateAtPosition(j));
					
				}
				System.out.println("Rotated");
			}
			
			System.out.println("Now let's add another Gold plate at position 1");
			Plate goldplate = new GoldPlate(sushi,15);
			belt.setPlateNearestToPosition(goldplate, 1);
			for(int i = 0; i<belt.getSize();i++){
				System.out.println(belt.getPlateAtPosition(i));
			}
			System.out.println("Now let's rotate again");
			for(int i = 0; i<belt.getSize();i++){
				belt.rotate();
				for(int j = 0; j<belt.getSize();j++){
					System.out.println(belt.getAgeOfPlateAtPosition(j));
					
				}
				System.out.println("Rotated");
			}
			System.out.println("Congrats on completing A7Adept!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
