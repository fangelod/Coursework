package lec8.ex2;

public class ClassA {
	private int private_field;
	protected int protected_field;
	public int public_field;
	
	public ClassA(int f1, int f2, int f3) {
		private_field = f1;
		protected_field = f2;
		public_field = f3;
	}
	
	public int getPrivateField() {
		return private_field;
	}
	
	protected void aProtectedMethod() {
		System.out.println("This is a protected method.");
	}
	
	private void aPrivateMethod() {
		System.out.println("This is a private method");
	}
}

