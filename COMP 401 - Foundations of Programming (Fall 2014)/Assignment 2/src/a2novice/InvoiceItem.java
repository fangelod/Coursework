package a2novice;

public class InvoiceItem {
	private String name;
	private double price_per_unit;
	private int unit_count;
	private double cost;
	
	public InvoiceItem(String name, double price_per_unit, int unit_count) {
		this.name = name.trim();
		this.price_per_unit = price_per_unit;
		this.unit_count = unit_count;
		
		if (name.trim().equals("")) {
			throw new RuntimeException("Illegal invoice item");
		}
		if (!Character.isUpperCase(name.trim().charAt(0))) {
			throw new RuntimeException("Illegal invoice item");
		}
		for (int i = 0; i < name.trim().length(); i++) {
			if (!Character.isLetterOrDigit(name.trim().charAt(i))) {
				throw new RuntimeException("Illegal invoice item");
			}
		}
		
		if (price_per_unit <= 0) {
			throw new RuntimeException("Illegal invoice item");
		}
		
		if (unit_count <= 0) {
			throw new RuntimeException("Illegal invoice item");
		}
		
		cost = price_per_unit * unit_count;
		if (cost <= 0) {
			throw new RuntimeException("Illegal invoice item");
		}
	}
	public String getName() {
		return name;
	}
	public double getPricePerUnit() {
		return price_per_unit;
	}
	public int getUnitCount() {
		return unit_count;
	}
	public double getCost() {
		return cost;
	}
}



	
