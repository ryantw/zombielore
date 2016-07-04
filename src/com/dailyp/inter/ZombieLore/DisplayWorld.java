package com.dailyp.inter.ZombieLore;
/*
 * Maybe have Gameworld with a false flag and have DisplayWorld check for it to turn true
 * when it does, display world.
 * 
 */
public class DisplayWorld {
	private GameWorld world;
	
	public DisplayWorld(GameWorld world){
		this.world = world;
	}
	
	public void displayWorld(){
		StringBuilder display = new StringBuilder();
		int width = world.getWidth();
		int height = world.getHeight();
		
		String gates;
		gates = "|";
		
		for(int x = 0; x < width; x++){
			gates = gates + "+";
		}
		gates = gates + "|\n";
		display.append(gates);
		
		for(int x = 0; x < width; x++){
			display.append("|");
			for(int y = 0; y < height; y++){
				Cell cell = world.getTileAt(x, y);
				display.append(cell.displayType());
			}
			display.append("|\n");
		}
		display.append(gates);
		
		display.append("\n");
		display.append("Humans: " + world.getNumberOfHumans() + "\n");
		display.append("Zombies: " + world.getNumberOfZombies() + "\n");
		display.append("Hunters: " + world.getNumberOfHunters() + "\n");
		
		System.out.println(display.toString());
	}
}
