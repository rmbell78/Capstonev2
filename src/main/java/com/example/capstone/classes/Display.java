package com.example.capstone.classes;

import java.util.ArrayList;

public class Display {

	public Display() {
	}

	static void draw(Map map, int xWidth, int yHeight) {
		ArrayList<Object> warehouses = map.getWarehouses();
		/* need a better way to clear the screen
		System.out.print("\033[H\033[2J");
		System.out.flush();
		*/

		System.out.printf("Time: %d:%d, Day: %d\n", Game.getHours(), Game.getMinutes(), Game.getDay());
		for (int i = 0; i < warehouses.size(); i++){
			Warehouse warehouse = (Warehouse) warehouses.get(i);
			System.out.printf("Warehouse #%d, Food: %d, Wood: %d\n", i + 1, warehouse.getFood(), warehouse.getWood());
		}
		ArrayList<Object> pawns = map.getPawns();
		for (int i = 0; i < pawns.size(); i++){
			Pawn pawn = (Pawn) pawns.get(i);
			System.out.printf("%s: Health: %d, Hunger: %d, Food: %d, Wood: %d\n", pawn.getName(), pawn.getHealth(), pawn.getHunger(), pawn.getFood(), pawn.getWood());
		}
		
		
		for (int i = 1; i <= xWidth; i++) {
			System.out.print("____");
		}
		//Can change static access to non
		System.out.print("_\n");
		for (int y = 1; y <= yHeight; y++) {
			System.out.print("|");
			for (int x = 1; x <= xWidth; x++) {
				Object objectAt = Map.getObjectAt(x, y);
				//Need way to get/display multiple objects at the same place, waiting for JavaFx
				//Need to have getObjectat return multiple
				if (objectAt != null) {
					if (objectAt instanceof Warehouse) {
						System.out.printf(" %c |", Warehouse.DISPLAY_CHAR);
					}else if (objectAt instanceof Tree) {
						System.out.printf(" %c |", Tree.DISPLAY_CHAR);
					}else if (objectAt instanceof Bush) {
						System.out.printf(" %c |", Bush.DISPLAY_CHAR);
					}else if (objectAt instanceof House) {
						System.out.printf(" %c |", House.DISPLAY_CHAR);
					} else if (objectAt instanceof Pawn) {
						System.out.printf(" %c |", Pawn.DISPLAY_CHAR);
					}
				}
				else {
					System.out.print("   |");
				}
			}
			System.out.print("\n-");
			for (int i = 1; i <= xWidth; i++) {
				System.out.print("----");
			}
			System.out.print("\n");

		}
	}
}
