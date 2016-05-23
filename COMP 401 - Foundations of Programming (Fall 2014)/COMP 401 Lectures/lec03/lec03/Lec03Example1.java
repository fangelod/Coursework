package lec03;

public class Lec03Example1 {

	public static void main(String[] args) {
		int a = 1;
		int b = 2;
		int c = 3;
		
		int max_times_min = min(a, b, c) * max(a, b, c);
		
		System.out.println(max_times_min);
	}
	
	public static int min(int n1, int n2, int n3) {
		if ((n1 <= n2) && (n1 <= n3)) {
			return n1;
		} else if ((n2 <= n1) && (n2 <= n3)) {
			return n2;
		} else {
			return n3;
		}
	}
	
	public static int max(int n1, int n2, int n3) {
		if ((n1 >= n2) && (n1 >= n3)) {
			return n1;
		} else if ((n2 >= n1) && (n2 >= n3)) {
			return n2;
		} else {
			return n3;
		}
	}

}
