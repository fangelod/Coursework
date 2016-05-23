package lec8.ex1.v2;

public class StudentImpl implements Student {

	private int credits;
	private String first;
	private String last;
	private String address;
	
	public StudentImpl(String first, String last, String address) {
		this.first = first;
		this.last = last;
		this.address = address;
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

	public String getFirstName() {
		return first;
	}

	public String getLastName() {
		return last;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String addr) {
		address = addr;
	}

}
