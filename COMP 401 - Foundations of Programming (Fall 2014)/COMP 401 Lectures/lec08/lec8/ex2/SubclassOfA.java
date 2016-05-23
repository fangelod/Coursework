package lec8.ex2;

public class SubclassOfA extends ClassA {

	SubclassOfA(int f1, int f2, int f3) {
		super(f1, f2, f3);
	}
	
	void methodOfSubclass() {
		// System.out.println(private_field);
		System.out.println(protected_field);
		System.out.println(public_field);
		
		System.out.println(getPrivateField());
		aProtectedMethod();
		// aPrivateMethod();
	}
}
