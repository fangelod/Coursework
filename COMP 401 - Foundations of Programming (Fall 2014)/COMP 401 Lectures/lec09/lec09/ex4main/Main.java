package lec09.ex4main;

import lec09.ex4.A;
import lec09.ex4.SubA;
import lec09.ex4.SubB;

public class Main {

	public static void main(String[] args) {
		
		// Can't create A objects since
		// constructor is not visible here.
	    // A a_obj = new A(1, 2);

		SubA suba1 = new SubA(1, 2, 3);
		SubA suba2 = new SubA(4, 5, 6);
		SubB subb1 = new SubB(7, 8, 9);
		SubB subb2 = new SubB(10, 11, 12);
		
		A[] a_array = new A[4];
		a_array[0] = suba1;
		a_array[1] = suba2;
		a_array[2] = subb1;
		a_array[3] = subb2;
		
		covariant_demo(a_array);
		contravariant_demo(a_array);		
	}
	
	static void covariant_demo(A[] objs) {
		for (int i=0; i<4; i++) {
			System.out.println("methodInA on objs[" + i + "]: " +
		                       objs[i].methodInA());
		}
	}
	
	static void contravariant_demo(A[] objs) {
		SubA sa;
		SubB sb;
		
		sa = (SubA) objs[0];		
		System.out.println("methodInSubA: " + 
							sa.methodInSubA());
		
		sa = (SubA) objs[1];
		System.out.println("methodInSubA: " + 
						   sa.methodInSubA());

		sb = (SubB) objs[2];
		System.out.println("methodInSubB: " +
		                   sb.methodInSubB());
		
		sb = (SubB) objs[3];
		System.out.println("methodInSubB: " +
		                   sb.methodInSubB());

	}
}

