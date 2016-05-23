package lec02.ex4;

public class Example4 {

	public static void main(String[] args) {
		
		// Any for loop can be written
		// as an equivalent while loop
		
		int sum = 0;
		
		for (int i=0; i<10; i++) {
			sum += i;
		}
		System.out.println(sum);
		
		// And it's equivalent while version:
		
		sum = 0;
		int i=0;
		while (i<10) {
			sum += i;
			i++;
		}
		System.out.println(sum);
		
		// Notice that in the above, variable i
		// in for loop is only in scope for the
		// body of the for loop. That is why
		// it has to be redeclared when used in the
		// while version. 
		
		// Break will terminate a loop.
		// Continue will skip rest of loop body
		// and go to next iteration.
		
		int num_tries;
		double dsum = 0.0;
		for (num_tries=0; num_tries < 10; num_tries++) {
			double r = Math.random();
			if ((r > 0.9) || (r < 0.1)) {
				continue;
			}
			dsum += r;
			if (dsum > 5.0) {
				break;
			}
		}
		System.out.println("Collected a sum of " + dsum +
				           " in " + num_tries + " tries");
		
	}

}
