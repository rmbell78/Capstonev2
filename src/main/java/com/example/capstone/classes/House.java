package com.example.capstone.classes;

import java.util.ArrayList;

public class House implements Placeable {
    public static int HOUSE_CAPACITY = 3;
    private final int[] location = new int[2];
    private final ArrayList<Object> houseOccupants = new ArrayList<>();

    public House(int x, int y) {
        location[0] = x;
        location[1] = y;
    }

    public void addOccupants(Object newOccupant) throws HouseException {
        if (houseOccupants.size() >= HOUSE_CAPACITY) {
            throw new HouseException("That house is already full");
        } else {
            houseOccupants.add(newOccupant);
        }
    }

    public void removeOccupant(Object oldOccupant){
        houseOccupants.remove(oldOccupant);
    }

    public boolean isFull(){
        return houseOccupants.size() == HOUSE_CAPACITY;
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
