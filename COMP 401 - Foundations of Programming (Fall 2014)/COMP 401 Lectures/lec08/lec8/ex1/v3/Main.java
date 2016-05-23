package lec8.ex1.v3;

public class Main {

	public static void main(String[] args) {
		Student s1 = new StudentImpl("Joe", "Cool", "Ehringhaus");
	
		System.out.println(s1.getFirstName() + " " + 
	                       s1.getLastName() + " is a " + 
	                       s1.getStatus() + " who lives at " +
	                       s1.getAddress());
		
		s1.addCredits(45);
		
		System.out.println(s1.getFirstName() + " " + 
                s1.getLastName() + " is a " + 
                s1.getStatus() + " who lives at " +
                s1.getAddress());

		Professor p1 = new ProfessorImpl("Ketan", "Mayer-Patel", "Durham");
		
		System.out.println(p1.getFirstName() + " " + 
                p1.getLastName() + " has the rank " + 
                p1.getRank() + " and lives in " +
                p1.getAddress());
		
		p1.promote();
		
		System.out.println(p1.getFirstName() + " " + 
                p1.getLastName() + " has the rank " + 
                p1.getRank() + " and lives in " +
                p1.getAddress());
	}
}
