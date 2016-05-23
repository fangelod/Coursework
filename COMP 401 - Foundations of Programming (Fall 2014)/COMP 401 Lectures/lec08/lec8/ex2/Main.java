package lec8.ex2;

public class Main {

	public static void main(String[] args) {
		SubclassOfA obj = new SubclassOfA(1, 2, 3);
		
		System.out.println(obj.public_field);
		System.out.println(obj.getPrivateField());

		obj.methodOfSubclass();
	}
}
