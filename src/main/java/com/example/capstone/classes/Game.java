package com.example.capstone.classes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

	/* Game.java provides the interface for the game to run, talking to the user. Accepting input and passing i
	*
	*
	*
	*
	*/

public class Game extends Map {
	private Map map = null;
	Scanner input = new Scanner(System.in);
	static int hours, day, minutes;

	public Game() {
		super();
		hours = 12;
		minutes = 0;
		day = 0;
	}

	public void mainMenu() throws PawnException {
		String mainMenuChoice = "";
		System.out.println(
				"Welcome to my simple game for cs112.\n\nYour goal is to survive for as long as possible and grow your population to the max possible. Eventually you will reach a point where the resources provided cannot support the population.\n");
		System.out.println("Main Menu: ");
		System.out.println(
				"start to begin game\ncontrols for game controls\ninfo for more information about the game\nexit to exit. You can type exit at any time to exit the game");
		do {
			mainMenuChoice = input.next();
			if (mainMenuChoice.equals("start")) {
				System.out.println("\nstart:\n");
				createMap();
				map.draw();
				startGame();
			} else if (mainMenuChoice.equals("controls")) {
				System.out.println("Info");
			} else if (mainMenuChoice.equals("info")) {
				System.out.println("Controls");
			} else if (mainMenuChoice.equals("exit")) {
				System.out.println("Thanks for playing!");
			}
		} while (!mainMenuChoice.equals("exit"));
		System.out.println("Thanks for playing!");

	}

	public void createMap() throws PawnException {
		int x, y;
		boolean goodMapGen = false;

		System.out.println(
				"First enter an x and a y value for the map size you wish, resources will be automatically placed across the map.");

		while (!goodMapGen) {
			try {
				System.out.print("\nX: ");
				x = input.nextInt();
				System.out.print("\nY: ");
				y = input.nextInt();
				map = new Map(x, y);
				goodMapGen = true;
			} catch (MapParametersException mpe) {
				System.out.println(mpe.getMessage());
			}
		}

		map.generateResources();
		map.draw();
		System.out.println("Good, now place your first warehouse somewhere on the map");
		goodMapGen = false;

		while (!goodMapGen) {
			try {
				System.out.print("\nX: ");
				x = input.nextInt();
				System.out.print("\nY: ");
				y = input.nextInt();
				map.createWarehouse(x, y);
				goodMapGen = true;
			} catch (MapParametersException mpe) {
				System.out.println(mpe.getMessage());
			}
		}

		map.draw();
		System.out.println("Great! now place your first house, your pawns will be placed near your house");
		goodMapGen = false;

		while (!goodMapGen) {
			try {
				System.out.print("\nX: ");
				x = input.nextInt();
				System.out.print("\nY: ");
				y = input.nextInt();
				map.createHouse(x, y);
				goodMapGen = true;
			} catch (MapParametersException mpe) {
				System.out.println(mpe.getMessage());
			}
		}
		map.generateFirstPawns();
		map.draw();
		System.out.println("Your first two pawns, aren't they cute. Why dont you give them some names");
		ArrayList<Object> pawns = getPawns();
		for (int i = 0; i < pawns.size(); i++) {
			Pawn pawn = (Pawn) pawns.get(i);
			System.out.printf("Pawn #%d: ", i + 1);
			pawn.setName(input.next());
		}
		System.out.println("Good your pawns now have names!");
	}

	private void startGame() {
		String usrInput = null;
		System.out.println("Alright lets get started, start by assigning your pawns a warehouse and a resource to gather");
		while (!Objects.equals(usrInput, "exit")) {
			map.draw();
			System.out.println("Menu: Type a pawns name to edit them or next to start the simulation.");
			usrInput = input.next();
			if (pawnExists(usrInput)) {
				Pawn pawn = (Pawn) map.getPawn(usrInput);
				System.out.println("What would you like to change, enter warehouse or resource");
				usrInput = input.next();
				if (usrInput.equals("warehouse")) {
					assignWarehouse(pawn);
				} else if (usrInput.equals("resource")) {
					assignResource(pawn);
				}
			} else if (usrInput.equals("next")) {
				play();

			}
		}
	}

	private void assignWarehouse(Pawn pawn) {
		while (true) {
			try {
				int x, y;
				System.out.printf("Enter X and Y coordinates for %ss warehouse.\n", pawn.getName());
				System.out.print("X: ");
				x = input.nextInt();
				System.out.print("Y: ");
				y = input.nextInt();
				pawn.assignWarehouse(x, y);
				break;
			} catch (MapParametersException mpe) {
				System.out.println(mpe.getMessage());
			}
		}
	}

	private void assignResource(Pawn pawn) {
		while (true) {
			try {
				int x, y;
				System.out.printf("Enter X and Y coordinates for %ss resource.\n", pawn.getName());
				System.out.print("X: ");
				x = input.nextInt();
				System.out.print("Y: ");
				y = input.nextInt();
				pawn.assignResource(x, y);
				break;
			} catch (MapParametersException mpe) {
				System.out.println(mpe.getMessage());
			}
		}
	}

	public void play() {
		minutes += 15;
		if (minutes == 60) {
			minutes = 0;
			hours++;
		}
		if (hours == 25) {
			hours = 0;
			day++;
		}

		map.tick();
	}

	static int getHours() {
		return hours;
	}

	static int getMinutes() {
		return minutes;
	}

	static int getDay() {
		return day;
	}

	public Map getMap() {
		return map;
	}

	// Used for testing otherwise map is created from createMap()
	public void createMap(int x, int y) {
		try {
			map = new Map(x, y);
		} catch (MapParametersException mpe) {
		}
	}

}