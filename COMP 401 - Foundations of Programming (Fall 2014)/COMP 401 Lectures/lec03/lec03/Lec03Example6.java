package lec03;

public class Lec03Example6 {

	public static void main(String[] args) {
		
		int[] a1;
		
		// iarray is declared to be a 
		// variable that can reference an
		// array of int's, but it hasn't been made yet.
		
		a1 = new int[10];
		
		// Now iarray references (aka "points to")
		// a new array of 10 integers initially set to 0
		
		System.out.println("The length of iarray is: " + 
							a1.length);
		
		// Indexing is done with [] as in.
		
		a1[0] = 42;
		a1[1] = 2;
		a1[2] = 120;
		a1[3] = 5;
		
		// What is the value of iarray[4]?
		// Since it hasn't been set yet, it gets the
		// default value for the type. In this case 0.
		System.out.println("iarray[4] = " + a1[4]);
		
		// We can also create an array literally
		// specifying a sequence of values after the new
		// constructor
		
		a1 = new int[] {5, 6, 7};
		
		// Notice that I reused the iarray variable.
		// Same variable, but now a different array.
		// The old array will be "garbage collected"
		// if there isn't some other reference to it.
		
		System.out.println("The length of iarray is: " +
						   a1.length);
		
		// If you are declaring and initializing
		// an array variable with literals at the
		// same time, there is a shorthand for that as in:
		
		String[] sa1 = {"One fish", 
				        "Two fish", 
				        "Red fish", 
				        "Blue fish"};
		
		// A common operation is to run a for
		// loop across the elements of an array.
		// Here is one commonly used way to do that:
		
		int sum_of_lengths = 0;
		for (int i=0; i<sa1.length; i++) {
			sum_of_lengths += (sa1[i]).length();
		}
		
		System.out.println("The sum of string lengths is: " +
		                   sum_of_lengths);
		
	}
}
