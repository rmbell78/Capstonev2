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
	}

	public void createMap() throws PawnException {
	}

	private void startGame() {
	}

	private void assignWarehouse(Pawn pawn) {
	}

	private void assignResource(Pawn pawn) {
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


	// Used for testing otherwise map is created from createMap()

}