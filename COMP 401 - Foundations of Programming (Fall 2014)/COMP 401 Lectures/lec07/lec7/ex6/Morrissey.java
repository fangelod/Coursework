package lec7.ex6;

public class Morrissey implements Smith {

	public String getName() {
		return "Morrissey";
	}

	@Override
	public String getParent() {
		return "Dad";
	}

	@Override
	public boolean isLoved() {
		return true;
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	@Override
	public String howSoon() {
		return "Now";
	}

}
