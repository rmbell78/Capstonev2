package com.example.capstone.classes;

import java.util.ArrayList;

public class House implements Placeable {
    public static int HOUSE_CAPACITY = 3;
    private int[] location = new int[2];
    private ArrayList<Object> houseOccupants = new ArrayList<Object>();

    public House(int x, int y) {
        location[0] = x;
        location[1] = y;
    }

    public void addOcuppants(Object newOccupant) throws HouseException {
        if (houseOccupants.size() >= HOUSE_CAPACITY) {
            throw new HouseException("That house is already full");
        } else {
            houseOccupants.add(newOccupant);
        }
    }

    public boolean isFull(){
        if(houseOccupants.size() == HOUSE_CAPACITY){
            return true;
        } else{
            return false;
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
