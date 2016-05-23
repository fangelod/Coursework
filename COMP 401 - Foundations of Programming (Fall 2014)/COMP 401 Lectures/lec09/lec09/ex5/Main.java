package lec09.ex5;

public class Main {


	public static void main(String[] args) {
		C c_obj = new C();
		B b_obj = (B) c_obj;
		A a_obj = (A) c_obj;

		System.out.println(c_obj.m());
		System.out.println(b_obj.m());
		System.out.println(a_obj.m());
	}

}

class A {
	public int m() {return 0;}
}

class B extends A {
	public int m() {return 1;}
}

class C extends B { 
	public int m() {return 2;}
}

