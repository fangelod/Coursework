package lec8.ex1.v2;

public class ProfessorImpl implements Professor {

	private String first;
	private String last;
	private String address;
	private Rank rank;
	
	public ProfessorImpl(String first, String last, String address) {
		this.first = first;
		this.last = last;
		this.address = address;
		rank = Rank.ASSISTANT;
	}
	
	public ProfessorImpl(String first, String last) {
		this(first, last, "Address Unknown");
	}

	public Rank getRank() {
		return rank;
	}
	
	public void promote() {
		switch (rank) {
		case ASSISTANT:
			rank = Rank.ASSOCIATE;
			break;
		case ASSOCIATE:
			rank = Rank.FULL;
			break;
		case FULL:
			// Can't go any higher than full.
		}
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
