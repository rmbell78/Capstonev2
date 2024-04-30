package com.example.capstone.classes;

public abstract class Resource implements Placeable {
	private int[] location = new int[2];
	private String resourceType;

	public Resource(int x, int y, String resourceType) {
		location[0] = x;
		location[1] = y;
		this.resourceType = resourceType;
	}

	public void setResourceType(String resourceType){
		this.resourceType = resourceType;
	}

	public String getResourceType(){
		return resourceType;
	}


	@Override
	public int getX() {
		return location[0];
	}
	@Override
	public int getY(){
		return location[1];
	}

}