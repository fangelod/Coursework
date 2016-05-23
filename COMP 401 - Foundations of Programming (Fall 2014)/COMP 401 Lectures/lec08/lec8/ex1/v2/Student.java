package lec8.ex1.v2;

public interface Student extends Person {
	enum Status {FRESHMAN, SOPHOMORE, JUNIOR, SENIOR}

	void addCredits(int num_credits);
	Status getStatus();
	
}
