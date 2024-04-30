package com.example.capstone.classes;

/**
 * The {@code Inventory} class is used to manage inventory items, specifically
 * food and wood.
 * It allows for adding, using, and setting quantities of these items within
 * specified maximum limits.
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public class Inventory {
	private int wood;
	private int food;
	private int foodMax;
	private int woodMax;
	static final int DEFAULT_VALUE = 10;

	/**
	 * Default constructor that initializes the inventory with default values.
	 */
	public Inventory() {
		wood = DEFAULT_VALUE;
		food = DEFAULT_VALUE;
		woodMax = DEFAULT_VALUE;
		foodMax = DEFAULT_VALUE;
	}

	/**
	 * Constructor with parameters to set maximum values for wood and
	 * food. Wood and food are automatically set to zero.
	 *
	 * @param woodMax Maximum wood quantity.
	 * @param foodMax Maximum food quantity.
	 */
	public Inventory(int woodMax, int foodMax){
		setFoodMax(foodMax);
		setWoodMax(woodMax);
		food = 0;
		wood = 0;
	}

	/**
	 * Constructor with parameters to set initial and maximum values for wood and
	 * food.
	 *
	 * @param wood    Initial wood quantity.
	 * @param food    Initial food quantity.
	 * @param woodMax Maximum wood quantity.
	 * @param foodMax Maximum food quantity.
	 */
	public Inventory(int wood, int food, int woodMax, int foodMax) {
		setWoodMax(woodMax);
		setFoodMax(foodMax);
		setWood(wood);
		setFood(food);
	}

	/**
	 * Returns the current wood quantity.
	 *
	 * @return Current wood quantity.
	 */
	public int getWood() {
		return wood;
	}

	/**
	 * Sets the wood quantity within the maximum limit.
	 *
	 * @param wood The desired wood quantity.
	 */
	private void setWood(int wood) {
		if (isValidValue(wood) && isValidWoodMax(wood)) {
			this.wood = wood;
		} else {
			this.wood = woodMax;
		}
	}

	/**
	 * Returns the current food quantity.
	 *
	 * @return Current food quantity.
	 */
	public int getFood() {
		return food;
	}

	/**
	 * Sets the food quantity within the maximum limit.
	 *
	 * @param food The desired food quantity.
	 */
	private void setFood(int food) {
		if (isValidValue(food) && isValidFoodMax(food)) {
			this.food = food;
		} else {
			this.food = foodMax;
		}
	}

	/**
	 * Returns the maximum wood quantity.
	 *
	 * @return Maximum wood quantity.
	 */
	public int getWoodMax() {
		return woodMax;
	}

	/**
	 * Sets the maximum food quantity.
	 *
	 * @param foodMax The desired maximum food quantity.
	 */
	private void setFoodMax(int foodMax) {
		if (isValidValue(foodMax)) {
			this.foodMax = foodMax;
		} else {
			this.foodMax = DEFAULT_VALUE;
		}
	}

	/**
	 * Returns the maximum food quantity.
	 *
	 * @return Maximum food quantity.
	 */
	public int getFoodMax() {
		return foodMax;
	}

	/**
	 * Sets the maximum wood quantity.
	 *
	 * @param woodMax The desired maximum wood quantity.
	 */
	private void setWoodMax(int woodMax) {
		if (isValidValue(woodMax)) {
			this.woodMax = woodMax;
		} else {
			this.woodMax = DEFAULT_VALUE;
		}
	}

	/**
	 * Uses a specified quantity of wood if available.
	 *
	 * @param numUsed The quantity of wood to use.
	 * @return The quantity of wood used.
	 */
	public int useWood(int numUsed) {
		if (wood - numUsed >= 0 && numUsed > 0) {
			wood -= numUsed;
			return numUsed;
		} else if (numUsed <= 0) {
			return 0;
		} else {
			int returnValue = wood;
			wood = 0;
			return returnValue;
		}
	}

	/**
	 * Uses a specified quantity of food if available.
	 *
	 * @param numUsed The quantity of food to use.
	 * @return The quanity of food used.
	 */
	public int useFood(int numUsed) {
		if (food - numUsed >= 0 && numUsed > 0) {
			food -= numUsed;
			return numUsed;
		} else if (numUsed <= 0) {
			return 0;
		} else {
			int returnValue = food;
			food = 0;
			return returnValue;
		}
	}
	/**
	 * Adds a specified quantity of wood if it does not exceed the maximum limit.
	 *
	 * @param numAdd The quantity of wood to add.
	 * @return the quantity of wood used.
	 */
	public int addWood(int numAdd) {
		if (isValidValue(numAdd) && isValidWoodMax(numAdd)) {
			wood += numAdd;
			return numAdd;
		} else if (!isValidValue(numAdd)) {
			return 0;
		} else {
			int woodAdded = woodMax - wood;
			wood = woodMax;
			return woodAdded;
		}
	}

	/**
	 * Adds a specified quantity of food if it does not exceed the maximum limit.
	 *
	 * @param numAdd The quantity of food to add.
	 * @return the quantity of wood used
	 */
	public int addFood(int numAdd) {
		if (isValidValue(numAdd) && isValidFoodMax(numAdd)) {
			food += numAdd;
			return numAdd;
		} else if (!isValidValue(numAdd)) {
			return 0;
		} else {
			int foodAdded = foodMax - food;
			food = foodMax;
			return foodAdded;
		}
	}

	/**
	 * Validates if the specified value is non-negative.
	 *
	 * @param value The value to validate.
	 * @return {@code true} if the value is non-negative, {@code false} otherwise.
	 */
	private boolean isValidValue(int value) {
		return value >= 0;
	}

	/**
	 * Checks if adding the specified quantity of food does not exceed the maximum
	 * limit.
	 *
	 * @param foodAdd The quantity of food to add.
	 * @return {@code true} if the operation does not exceed the limit,
	 *         {@code false} otherwise.
	 */
	private boolean isValidFoodMax(int foodAdd) {
		return foodAdd + food <= foodMax;
	}

	/**
	 * Checks if adding the specified quantity of wood does not exceed the maximum
	 * limit.
	 *
	 * @param woodAdd The quantity of wood to add.
	 * @return {@code true} if the operation does not exceed the limit,
	 *         {@code false} otherwise.
	 */
	private boolean isValidWoodMax(int woodAdd) {
		return woodAdd + wood <= woodMax;
	}
}