import java.util.Arrays;

public class Tester {
	public static void main(String[] args) {
		/*
		// Test generateRandomTape()
		System.out.println("Testing generateRandomTape() method");
		TapeDrive t1 = new TapeDrive(5);
		t1.generateRandomTape(6).printTape();
		System.out.println();
		
		// Test quicksort()
		System.out.println("Testing quicksort() method");
		TapeDrive t2 = t1.generateRandomTape(8);
		TapeSorter test = new TapeSorter(3, 8);
		int[] arrTest = new int[8];
		
		System.out.print("Unsorted data:     ");
		for (int i = 0; i < 8; i++) {
			arrTest[i] = t2.read();
			System.out.print(arrTest[i] + "  ");
		}
		System.out.println();
		System.out.print("Before quicksort:  ");
		test.memory = arrTest;
		for (int i = 0; i < 8; i++) {
			System.out.print(test.memory[i] + "  ");
		}
		
		test.quicksort(8);
		System.out.println();
		System.out.print("After quicksort:   ");
		for (int i = 0; i < 8; i++) {
			System.out.print(test.memory[i] + "  ");
		}
		
		System.out.println();
		boolean sorted = true;
		for(int i = 1; i < 8; i++) {
		    if(test.memory[i-1] > test.memory[i]){
		          sorted = false;
		          break;
		    }
		}

		System.out.println("Sorted: " + sorted);
		*/


		long epoch1 = System.currentTimeMillis();
        for(int i=50;i<1000;i++){
                for (int k=1;k<50;k++){
                        int cs = 57;
                    TapeSorter tapeSorter = new TapeSorter(k, i);
                    TapeDrive t1 = TapeDrive.generateRandomTape(i);
                    TapeDrive t2 = new TapeDrive(i);
                    TapeDrive t3 = new TapeDrive(i);
                    TapeDrive t4 = new TapeDrive(i);
                   
                    tapeSorter.sort(t1, t2, t3, t4);
                   
                    int last = Integer.MIN_VALUE;
                    boolean sorted = true;
                    for (int j = 0; j < i; j++) {
                        int val = t1.read();
                         sorted &= (last <= val);
                         if (!sorted){
                                 //System.out.println( val + " " + j);
                         }
                         last = val;
                     }
                     if (sorted){}
                         //System.out.println("Sorted!");
                     else
                         System.out.println("Not sorted! " + i);
                }
        }
        long epoch2 = System.currentTimeMillis();
        System.out.println("Runtime is " + ((epoch2 - epoch1)/1000) + " seconds.");
        System.out.println("Done");
		
	}
}
