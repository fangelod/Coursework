package lec09.ex3;

public class Main {

	public static void main(String[] args) {
		A a_obj = new A();
		SubA suba_obj = new SubA();
	
		System.out.println("a_obj.method(1,2): " + 
							a_obj.method(1, 2));
		
		System.out.println("suba_obj.method(1,2): " + 
							suba_obj.method(1, 2));
	}
}

class A {
	public int method(int x, int y) {
		return x+y;
	}
}

class SubA extends A {
	@Override
	public int method(int x, int y) {
		return x-y;
	}
}