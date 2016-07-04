package com.dailyp.inter.ZombieLore;

public class Cell {
	
	public enum CellType { Empty, Victim, Zombie, Hunter };
	
	protected CellType type = CellType.Empty;
	
	GameWorld world;
	private int x;
	private int y;
	private boolean alreadyMoved = false;
	
	public Cell(GameWorld world, int x, int y){
		this.world = world;
		this.setX(x);
		this.setY(y);
	}
	
	public void setCellType(CellType typeIn){
		//System.out.println("x:" + x + " y:" + y + " from: " + this.type + " to: " + typeIn);
		this.type = typeIn;
	}
	
	public void setAlreadyMoved(boolean moved){
		this.alreadyMoved = moved;
	}
	
	public boolean getAlreadyMoved(){
		return this.alreadyMoved;
	}
	
	public CellType getCellType(){
		return type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String displayType(){
		if(type == CellType.Empty){
			return("-");
		} else if(type == CellType.Victim){
			return("V");
		} else if(type == CellType.Zombie){
			return("Z");
		} else if(type == CellType.Hunter){
			return("H");
		}
		return null;
	}
}
