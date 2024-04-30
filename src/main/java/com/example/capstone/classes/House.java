package com.example.capstone.classes;

import java.util.ArrayList;

public class House implements Placeable {
    private final int HOUSE_CAPACITY = 3;
    private int[] location = new int[2];
    private ArrayList<Object> houseOccupants = new ArrayList<Object>();
    static final char DISPLAY_CHAR = 'A';

    public House(int x, int y) {
        location[0] = x;
        location[1] = y;
    }

    public void addOcuppants(Object newOccupant) throws HouseException {
        if (houseOccupants.size() >= HOUSE_CAPACITY) {
            throw new HouseException("That house is already full");
        } else {
            houseOccupants.add(newOccupant);
            Pawn pawn = (Pawn) newOccupant;
            pawn.assignHouse(this);
        }
    }

    @Override
    public int getX() {
        return location[0];
    }

    @Override
    public int getY() {
        return location[1];
    }
}
