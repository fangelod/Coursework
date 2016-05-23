package comp401.sushi;

/**
 * Common interface for sushi ingredients.
 * @author KMP
 *
 */
public interface Ingredient {
    /**
     * Standard getter for the amount of ingredient.
     * @return the amount of ingredient in the unit of ounce
     */
	double getAmount();
    /**
     * Standard getter for the cost of ingredient.
     * @return the amount of cost
     */
	double getCost();
	/**
	 * Standard boolean getter
	 * @return whether the ingredient is rice.
	 */
    boolean isRice();
    /**
     * Standard boolean getter
     * @return whether the ingredient is shellfish.
     */
    boolean isShellfish();
    /**
     * Standard boolean getter
     * @return whether the ingedrient is vegetarian.
     */
    boolean isVegetarian();
    /**
     * Standard getter
     * @return the name of the ingredient.
     */
    String getName();
}
