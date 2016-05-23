package lec8.ex1.v1;

public class Main {

	public static void main(String[] args) {
		Student s1 = new StudentImpl("Joe", "Cool", "Ehringhaus");
	
		System.out.println(((Person) s1).getFirstName() + " " + 
	                       ((Person) s1).getLastName() + " is a " + 
	                       s1.getStatus() + " who lives at " +
	                       ((Person) s1).getAddress());
		
		s1.addCredits(45);
		
		System.out.println(((Person) s1).getFirstName() + " " + 
                ((Person) s1).getLastName() + " is a " + 
                s1.getStatus() + " who lives at " +
                ((Person) s1).getAddress());

		Professor p1 = new ProfessorImpl("Ketan", "Mayer-Patel", "Durham");
		
		System.out.println(((Person) p1).getFirstName() + " " + 
                ((Person) p1).getLastName() + " has the rank " + 
                p1.getRank() + " and lives in " +
                ((Person) p1).getAddress());
		
		p1.promote();
		
		System.out.println(((Person) p1).getFirstName() + " " + 
                ((Person) p1).getLastName() + " has the rank " + 
                p1.getRank() + " and lives in " +
                ((Person) p1).getAddress());
	}
}
