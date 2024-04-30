package com.example.capstone.classes;

import java.util.ArrayList;
import java.lang.Math;

public class Map extends Display {
	static ArrayList<Object> mapObjects = new ArrayList<Object>();
	static int xWidth, yHeight;
	private final int MAX_TREE = 2;
	private final int MAX_BUSH = 2;
	static final int MAX_DIMENSION = 50;
	static final int MIN_DIMENSION = 4;
	private final int NUM_STARTING_PAWNS = 2;
	static final int DEFAULT_SIZE = 10;
	static int numHouses;

	/**
	 * Constructs a new Map with default size.
	 * 
	 * Initializes the map with a default size defined by DEFAULT_SIZE.
	 * If setting the map size fails, it catches the MapParametersException
	 * and prints the exception message along with a specific message for the
	 * default constructor.
	 *
	 * @throws MapParametersException if the map size parameters are out of bounds.
	 */
	public Map() {
		super();
		try {
			setMapSize(DEFAULT_SIZE, DEFAULT_SIZE);
		} catch (MapParametersException mpe) {
			System.out.println(mpe.getMessage() + " for default map constructor");
		}
	}

	/**
	 * Constructs a new Map with specified dimensions.
	 * 
	 * Initializes the map with the given dimensions. If setting the map size fails,
	 * it throws a MapParametersException.
	 *
	 * @param xWidth  the width of the map
	 * @param yHeight the height of the map
	 * @throws MapParametersException if the map size parameters are out of bounds.
	 */
	public Map(int xWidth, int yHeight) throws MapParametersException {
		super();
		setMapSize(xWidth, yHeight);
	}

	/**
	 * Sets the dimensions of the map.
	 * 
	 * Validates the provided dimensions against the maximum and minimum allowed
	 * dimensions.
	 * If the dimensions are out of the allowed range, it throws a
	 * MapParametersException.
	 * Otherwise, it sets the map's width and height to the provided dimensions.
	 *
	 * @param xWidth  the width of the map to set
	 * @param yHeight the height of the map to set
	 * @throws MapParametersException if the provided dimensions are out of bounds
	 */
	private void setMapSize(int xWidth, int yHeight) throws MapParametersException {
		if (xWidth > MAX_DIMENSION | xWidth <= MIN_DIMENSION) {
			throw new MapParametersException("X Parameters out of bounds " + MIN_DIMENSION + " < x < " + MAX_DIMENSION);
		} else if (yHeight > MAX_DIMENSION | yHeight < MIN_DIMENSION) {
			throw new MapParametersException("Y Parameters out of bounds " + MIN_DIMENSION + " < x < " + MAX_DIMENSION);
		} else {
			this.xWidth = xWidth;
			this.yHeight = yHeight;
		}
	}

	/**
	 * Creates a pawn on the map with the specified name and location.
	 * 
	 * Validates the location of the pawn to ensure it is within the map boundaries
	 * and not on the same location as another object. If the pawn's name already
	 * exists,
	 * a PawnException is thrown. If the location is invalid, a
	 * MapParametersException is thrown.
	 * 
	 * @param name the name of the pawn to create
	 * @param x    the x-coordinate of the pawn's location
	 * @param y    the y-coordinate of the pawn's location
	 * @throws MapParametersException if the pawn's location is out of map
	 *                                boundaries or occupied
	 * @throws PawnException          if a pawn with the given name already exists
	 */
	public void createPawn(String name, int x, int y) throws MapParametersException, PawnException {
		if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
			throw new MapParametersException("Pawn is not on map.");
		} else if (isObjectAt(x, y)) {
			throw new MapParametersException("There is already something at that location.");
		} else if (pawnExists(name)) {
			throw new PawnException("There is already a pawn with that name.");
		} else {
			mapObjects.add(new Pawn(name, x, y));
		}
	}

	/**
	 * Creates a pawn on the map with the specified name and location and assigns it
	 * to a house.
	 * 
	 * Validates the location of the pawn to ensure it is within the map boundaries
	 * and not on the same location as another object. If the pawn's name already
	 * exists,
	 * a PawnException is thrown. If the location is invalid, a
	 * MapParametersException is thrown.
	 * Upon successful creation, the pawn is added to the specified house.
	 * 
	 * @param name  the name of the pawn to create
	 * @param x     the x-coordinate of the pawn's location
	 * @param y     the y-coordinate of the pawn's location
	 * @param house the house to assign the pawn to
	 * @throws MapParametersException if the pawn's location is out of map
	 *                                boundaries or occupied
	 * @throws PawnException          if a pawn with the given name already exists
	 * @throws HouseException         if adding the pawn to the house fails
	 */
	public void createPawnAssignHouse(String name, int x, int y, House house)
			throws MapParametersException, PawnException, HouseException {
		if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
			throw new MapParametersException("Pawn is not on map");
		} else if (isObjectAt(x, y)) {
			throw new MapParametersException("There is already something at that location");
		} else if (pawnExists(name)) {
			throw new PawnException("There is already a pawn with that name.");
		} else {
			mapObjects.add(new Pawn(name, x, y));
            house.addOcuppants(getObjectAt(x, y));
        }
	}

	/**
	 * Creates a warehouse on the map at the specified location.
	 * 
	 * Validates the location of the warehouse to ensure it is within the map
	 * boundaries
	 * and not on the same location as another object. If the location is invalid,
	 * a MapParametersException is thrown.
	 * 
	 * @param x the x-coordinate of the warehouse's location
	 * @param y the y-coordinate of the warehouse's location
	 * @throws MapParametersException if the warehouse's location is out of map
	 *                                boundaries or occupied
	 */
	public void createWarehouse(int x, int y) throws MapParametersException {
		if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
			throw new MapParametersException("Warehouse is not on map");
		} else if (isObjectAt(x, y)) {
			throw new MapParametersException("There is already something at that location");
		} else {
			mapObjects.add(new Warehouse(x, y));
		}
	}

	public void createHouse(int x, int y) throws MapParametersException {
		if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
			throw new MapParametersException("House is not on map");
		} else if (isObjectAt(x, y)) {
			throw new MapParametersException("There is already something at that location");
		} else {
			mapObjects.add(new House(x, y));
			numHouses++;
		}
	}

	public void createBush(int x, int y) throws MapParametersException {
		if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
			throw new MapParametersException("Bush is not on map");
		} else if (isObjectAt(x, y)) {
			throw new MapParametersException("There is already something at that location");
		} else {
			mapObjects.add(new Bush(x, y));
		}
	}

	public void createTree(int x, int y) throws MapParametersException {
		if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
			throw new MapParametersException("Tree is not on map");
		} else if (isObjectAt(x, y)) {
			throw new MapParametersException("There is already something at that location");
		} else {
			mapObjects.add(new Tree(x, y));
		}
	}

	public void generateFirstPawns() {
		int houseX = 0, houseY = 0;
		House house = null;
		int nameIndex = 0;
		String[] firstNames = { "Bob", "Amy" };
		// Maybe a while loop here instead, avoid going through the whole array list
		// everytime
		for (int i = 0; i < mapObjects.size(); i++) {
			if (mapObjects.get(i) instanceof House) {
				house = (House) mapObjects.get(i);
				houseX = house.getX();
				houseY = house.getX();
			}
		}
		// I still think there is a better way to do this
		for (int i = 0; i < NUM_STARTING_PAWNS; i++) {
			if (!isObjectAt(houseX + 1, houseY)) {
				try {
					createPawnAssignHouse(firstNames[nameIndex], houseX + 1, houseY, house);
					nameIndex++;
				} catch (MapParametersException | PawnException | HouseException mpe) {
					System.out.println(mpe.getMessage());
				}
            } else if (!isObjectAt(houseX, houseY + 1)) {
				try {
					createPawnAssignHouse(firstNames[nameIndex], houseX, houseY + 1, house);
					nameIndex++;
				} catch (MapParametersException | PawnException | HouseException mpe) {
					System.out.println(mpe.getMessage());
				}
            } else if (houseX - 1 != 0 && !isObjectAt(houseX - 1, houseY)) {
				try {
					createPawnAssignHouse(firstNames[nameIndex], houseX - 1, houseY, house);
					nameIndex++;
				} catch (MapParametersException | PawnException | HouseException mpe) {
					System.out.println(mpe.getMessage());
				}
            } else if (houseY - 1 != 0 && !isObjectAt(houseX, houseY - 1)) {
				try {
					createPawnAssignHouse(firstNames[nameIndex], houseX, houseY - 1, house);
					nameIndex++;
				} catch (MapParametersException | PawnException | HouseException mpe) {
					System.out.println(mpe.getMessage());
				}
            }
		}

	}

	static Object getObjectAt(int x, int y) {
		Object returnObject = null;
		for (int i = 0; i < mapObjects.size(); i++) {
			if (mapObjects.get(i) instanceof Pawn) {
				Pawn pawn = (Pawn) mapObjects.get(i);
				if (pawn.getX() == x && pawn.getY() == y) {
					returnObject = pawn;
				}
			} else if (mapObjects.get(i) instanceof Warehouse) {
				Warehouse wareHouse = (Warehouse) mapObjects.get(i);
				if (wareHouse.getX() == x && wareHouse.getY() == y) {
					returnObject = wareHouse;
				}
			} else if (mapObjects.get(i) instanceof Tree) {
				Tree tree = (Tree) mapObjects.get(i);
				if (tree.getX() == x && tree.getY() == y) {
					returnObject = tree;
				}
			} else if (mapObjects.get(i) instanceof Bush) {
				Bush bush = (Bush) mapObjects.get(i);
				if (bush.getX() == x && bush.getY() == y) {
					returnObject = bush;
				}
			} else if (mapObjects.get(i) instanceof House) {
				House house = (House) mapObjects.get(i);
				if (house.getX() == x && house.getY() == y) {
					returnObject = house;
				}
			}
		}
		return returnObject;

	}

	static boolean isObjectAt(int x, int y) {
		boolean isObjectAt = false;
		for (int i = 0; i < mapObjects.size(); i++) {
			if (mapObjects.get(i) instanceof Warehouse) {
				Warehouse wareHouse = (Warehouse) mapObjects.get(i);
				if (wareHouse.getX() == x && wareHouse.getY() == y) {
					isObjectAt = true;
				}
			} else if (mapObjects.get(i) instanceof Tree) {
				Tree tree = (Tree) mapObjects.get(i);
				if (tree.getX() == x && tree.getY() == y) {
					isObjectAt = true;
				}
			} else if (mapObjects.get(i) instanceof Bush) {
				Bush bush = (Bush) mapObjects.get(i);
				if (bush.getX() == x && bush.getY() == y) {
					isObjectAt = true;
				}
			} else if (mapObjects.get(i) instanceof House) {
				House house = (House) mapObjects.get(i);
				if (house.getX() == x && house.getY() == y) {
					isObjectAt = true;
				}
			} else if (mapObjects.get(i) instanceof Pawn) {
				Pawn pawn = (Pawn) mapObjects.get(i);
				if (pawn.getX() == x && pawn.getY() == y) {
					isObjectAt = true;
				}
			}
		}
		return isObjectAt;

	}

	public void generateResources() {
		for (int i = 1; i <= MAX_BUSH; i++) {
			boolean goodGen = false;
			int x, y;
			do {
				x = (int) (Math.random() * xWidth);
				y = (int) (Math.random() * yHeight);
				if (x != 0 && y != 0 && isObjectAt(x, y) == false) {
					goodGen = true;
				}
			} while (!goodGen);
			try{
				createBush(x, y);
			} catch (MapParametersException mpe){
				System.out.println(mpe.getMessage());
			}
		}
		for (int i = 1; i <= MAX_TREE; i++) {
			boolean goodGen = false;
			int x, y;
			do {
				x = (int) (Math.random() * xWidth);
				y = (int) (Math.random() * yHeight);
				if (x != 0 && y != 0 && isObjectAt(x, y) == false) {
					goodGen = true;
				}
			} while (!goodGen);
			try{
				createTree(x, y);
			} catch(MapParametersException mpe){
				System.out.println(mpe.getMessage());
			}

		}

	}

	public int getXWidth() {
		return xWidth;
	}

	public int getYHeight() {
		return yHeight;
	}

	public void draw() {
		Display.draw(this, xWidth, yHeight);
	}

	public static ArrayList<Object> getPawns() {
		ArrayList<Object> pawnList = new ArrayList<Object>();
		for (int i = 0; i < mapObjects.size(); i++) {
			if (mapObjects.get(i) instanceof Pawn) {
				pawnList.add(mapObjects.get(i));
			}
		}
		return pawnList;
	}

	public ArrayList<Object> getWarehouses() {
		ArrayList<Object> warehouseList = new ArrayList<Object>();
		for (int i = 0; i < mapObjects.size(); i++) {
			if (mapObjects.get(i) instanceof Warehouse) {
				warehouseList.add(mapObjects.get(i));
			}
		}
		return warehouseList;
	}

	public Object getPawn(String name) {
		ArrayList<Object> pawnList = new ArrayList<Object>();
		pawnList = getPawns();
		Pawn returnPawn = null;
		for (int i = 0; i < pawnList.size(); i++) {
			Pawn pawn = (Pawn) pawnList.get(i);
			if (pawn.getName().equals(name)) {
				returnPawn = pawn;
			}
		}
		return returnPawn;
	}

	static boolean pawnExists(String name) {
		ArrayList<Object> pawnList = new ArrayList<Object>();
		Boolean pawnExists = false;
		System.out.println("PawnCheck");
		pawnList = getPawns();
		for (int i = 0; i < pawnList.size(); i++) {
			Pawn pawn = (Pawn) pawnList.get(i);
			if (pawn.getName().equals(name)) {
				pawnExists = true;
				System.out.println("PawnTrue");
			}
		}
		return pawnExists;
	}

	public void tick() {
		ArrayList<Object> pawnList = new ArrayList<Object>();
		pawnList = getPawns();
		for (int i = 0; i < pawnList.size(); i++) {
			Pawn pawn = (Pawn) pawnList.get(i);
			pawn.tick();
		}
	}
}
