package com.dailyp.inter.ZombieLore;

import java.io.IOException;

public class ZombieLore {

	public static void main(String args[]) throws InterruptedException, IOException{
		int totalmoves = 0;
		GameWorld world = new GameWorld(10,10);
		DisplayWorld display = new DisplayWorld(world);
		world.randomizeBoard();
		display.displayWorld();
		
		while(totalmoves < 50){// || (world.getNumberOfHumans() > 0 && world.getNumberOfZombies() > 0)){
			world.processAllMoves();
			display.displayWorld();
			Thread.sleep(3000);
			totalmoves++;
			System.out.println("Total Moves: " + totalmoves);
		}
	}

}
