package a9;

import java.math.BigDecimal;
import java.util.Random;

public class TestRandNumGen {

	public TestRandNumGen() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		double test = 0;
		while (test != 1.0) {
			Random r = new Random();
			//int temp = r.nextInt(1001);
			test = ((double)r.nextInt(1001)) / 1000;
			System.out.println(test);
		}
		
	}

}
