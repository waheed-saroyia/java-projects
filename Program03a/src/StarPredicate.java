

/**
 * A functional interface defining a method to indicate if the star meets some criteria
 */
public interface StarPredicate {

	/**
	 * Indicates if the stars meets the programmed criteria
	 * @param star - a star to be evaluated
	 * @return - true if criteria are met otherwise false
	 */
	boolean isOK(Star star);
	
}
