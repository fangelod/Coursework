import java.util.Arrays;
import java.util.Scanner;

/* This program will only consider matrices with integers. It cannot find the determinant of matrices
 * 	that involve functions, such as the rotation matrix and the reflection matrix. The reason for
 * 	this is that the program can only take in integers as inputs from the user. This program could
 * 	potentially be able to compute any determinant for any matrix, but the length of the code would
 * 	be over triple the length of this current program. Also, being able to enter any input possible
 * 	would require knowledge of the java library and the various methods that would help compute things
 * 	such as sin(x)*cos(x).
 */
public class Determinant {
	public static void main(String[] args) {
		// x = # of rows
		// y = # of columns
		// m = matrix with x rows and y columns
		
		Scanner keyboard = new Scanner(System.in);
		
		// Asking user to define the dimensions of the matrix
		System.out.println("How many rows does the matrix have?");
		int x = keyboard.nextInt();
		
		System.out.println("The matrix will have the same number of columns.");
		int y = x;
		
		int[][] m = new int[x][y];
		
		// Asking user to input every element in the matrix
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				System.out.println("What integer is in a" + (i+1) + (j+1));
				m[i][j] = keyboard.nextInt();
			}	
		}
		
		// Prints the matrix back to the user
		System.out.println(Arrays.deepToString(m));
		
		// Calculating the determinant by using row combinations and expansion in algebraic complements
		
		// Adding
		int left_to_right;
		int add = 0;
		for (int i = 0; i < x; i++) {
			left_to_right = 1;
			int starting_row = i;
			int a = 0;
			for (int j = starting_row; j < x; j++) {
				left_to_right *= m[j][a];
				a++;
			}
			if (starting_row != 0) {
				for (int k = 0; k < starting_row; k++) {
					left_to_right *= m[k][a];
					a++;
				}
			}
			add += left_to_right;
		}
		
		// Subtracting
		int right_to_left;
		int subtract = 0;
		for (int i = 0; i < x; i++) {
			right_to_left = 1;
			int starting_row = i;
			int b = 4;
			for (int j = starting_row; j < x; j++) {
				right_to_left *= m[j][b];
				b--;
			}
			if (starting_row != 0) {
				for (int k = 0; k < starting_row; k++) {
					right_to_left *= m[k][b];
					b--;
				}
			}
			subtract += right_to_left;
		}
		
		int det = add - subtract;
		System.out.println("Determinant is: " + add + " - " + subtract + " = " + det);
	}
}
