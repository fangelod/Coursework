package lec8.ex1.v3;

public class PersonImpl implements Person {

	private String first;
	private String last;
	private String address;

	public PersonImpl(String first, String last, String address) {
		this.first = first;
		this.last = last;
		this.address = address;
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
