package comp401.sushi;

public class PlateImpl implements Plate {
	private Plate.Color color;
	private Sushi contents;
	private double price;
	
	//protected Sushi contents;
	//protected Sushi cont_ref;
	//protected double price;
	//protected Color color;
	//protected double profit;
	
	
	public PlateImpl(Plate.Color color, Sushi contents, double price) throws PlatePriceException {
		this.color = color;
		this.contents = null;
		this.price = price;
		
		if (contents != null) {
			setContents(contents);
		}
	}
	
	//public PlateImpl() {
	//}

	
	@Override
	public Sushi getContents() {
		return contents;
	}
	
	/* Input: none
	 * Output: Sushi
	 * This method returns what sushi on currently on the plate.
	 * 		If there is nothing on the plate, then this method
	 * 		returns null.
	 */
	//public Sushi getContents() {
	//	if (contents == null) {
	//		return null;
	//	}
	//	else {
	//		return contents;
	//	}
	//}

	
	public Sushi removeContents() {
		Sushi removed_contents = contents;
		contents = null;
		return removed_contents;
	}
	
	/* Input: none
	 * Output: Sushi
	 * This method sets the contents of the plate to be null.
	 * 		If the plate did have some sushi on the plate, this
	 * 		method returns a reference to the removed sushi.
	 * 		Otherwise, this method returns null.
	 */
	//public Sushi removeContents() {
	//	if (contents == null) {
	//		return null;
	//	}
	//	else {
	//		cont_ref = contents;
	//		contents = null;
	//	}
	//	return cont_ref;
	//}

	
	@Override
	public void setContents(Sushi s) throws PlatePriceException {
		if (s == null) {
			throw new IllegalArgumentException("Null passed to setContents");
		} else if (s.getCost() >= getPrice()) {
			throw new PlatePriceException();
		}
		contents = s;
	}
	
	/* Input: Sushi
	 * Output: none
	 * This method sets the contents of the plate to contain 
	 * 		the inputted sushi. If the sushi inputted is null, 
	 * 		then an IllegalArgumentException is thrown. If the
	 * 		sushi placed on the plate costs more than the plate
	 * 		itself, a PlatePriceException is thrown.
	 */
	//public void setContents(Sushi s) throws PlatePriceException {
	//	if (s == null) {
	//		throw new IllegalArgumentException("A valid sushi "
	//				+ "roll was not inputted");
	//	}
	//	else {
	//		if (price <= s.getCost()) {
	//			throw new PlatePriceException();
	//		}
	//		else {
	//			contents = s;
	//		}
	//	}
	//}

	
	@Override
	public boolean hasContents() {
		return (contents != null);
	}
	
	/* Input: none
	 * Output: boolean
	 * This method returns true when the plate has sushi on it 
	 * 		and it returns false when the plate is empty.
	 */
	//public boolean hasContents() {
	//	if (contents == null) {
	//		return false;
	//	}
	//	else {
	//		return true;
	//	}
	//}

	
	@Override
	public double getPrice() {
		return price;
	}
	
	/* Input: none
	 * Output: double
	 * This method returns the price of the plate.
	 */
	//public double getPrice() {
	//	return price;
	//}

	
	@Override
	public Plate.Color getColor() {
		return color;
	}
	
	/* Input: none
	 * Output: Color
	 * This method returns the color of the plate.
	 */
	//public Color getColor() {
	//	return color;
	//}

	
	@Override
	public double getProfit() {
		if (!hasContents()) {
			return 0.0;
		} else {
			return getPrice() - contents.getCost();
		}
	}
	
	/* Input: none
	 * Output: double
	 * This method returns the profit of the plate.
	 * 		i.e. the difference between the price of the plate 
	 * 		and the cost of the sushi on it.
	 * 		If there is no sushi on the plate, the profit is 
	 * 		defaulted to 0.
	 */
	//public double getProfit() {
	//	if (contents == null) {
	//		return 0.0;
	//	}
	//	else {
	//		profit = getPrice() - contents.getCost();
	//	}
	//	
	//	return profit;
	//}
}
