package lec11.ex10;

public class Main {

	public static void main(String[] args) {
		do_test();
	}
	
	public static void do_test() {
		for (int i=0; i<10; i++) {
			try {
				System.out.println(i);
				if (i==5) {
					throw new Exception("An exception");
				}
			} catch (Exception e) {
				System.out.println("Exception caught about to return.");
				return;
			} finally {
				System.out.println("Finally block runs");
			}
		}
		System.out.println("Outside of for loop.");		
	}
}
