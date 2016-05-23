package lec8.ex1.v3;

public class StudentImpl extends PersonImpl implements Student {

	private int credits;
	
	public StudentImpl(String first, String last, String address) {
		super(first, last, address);
		credits = 0;
	}
	
	public StudentImpl(String first, String last) {
		this(first, last, "Address Unknown");
	}

	public void addCredits(int num_credits) {
		credits += num_credits;
	}

	public Status getStatus() {
		if (credits < 30) {
			return Status.FRESHMAN;
		} else if (credits < 60) {
			return Status.SOPHOMORE;
		} else if (credits < 90) {
			return Status.JUNIOR;
		}
		return Status.SENIOR;
	}
}
