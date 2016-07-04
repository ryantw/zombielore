package com.dailyp.inter.ZombieLore;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameWorld {
	
	private Cell[][] cells;
	private int width;
	private int height;
	private int numberOfVictims;
	private int numberOfZombies;
	private int numberOfHunters;
	private int zombieMoveCount;
	private int victimMoveCount;
	private int hunterMoveCount;
	
	
	// Default constructor, assume 10x10.
	public GameWorld(int width, int height){
		this.width = width;
		this.height = height;
		
		cells = new Cell[width][height];
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				cells[x][y] = new Cell(this, x, y);
			}
		}
		
		System.out.println("World created with: " + width * height + " tiles.");
	}
	
	public Cell getTileAt(int x, int y){
		if(x >= width || x < 0 || y >= height || y < 0){
			//System.err.println("Tile is out of range at x:" + x + ", y:" + y);
			return null;
		}
		
		return cells[x][y];
	}
	
	public void processAllMoves(){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				Cell currentCell = getTileAt(x,y);
				
				if(currentCell != null && currentCell.getAlreadyMoved() == false){
					if(currentCell.type == Cell.CellType.Empty){
						continue;
					} else if(currentCell.type == Cell.CellType.Victim){
						if(processHumanMove(currentCell)){
							victimMoveCount++;
						}
					} else if(currentCell.type == Cell.CellType.Zombie){
						if(processZombieMove(currentCell)){
							zombieMoveCount++;
						}
					} else if(currentCell.type == Cell.CellType.Hunter){
						if(processHunterMove(currentCell)){
							hunterMoveCount++;
						}
					} else {
						System.err.println("Well this isn't good, error!");
					}
					
				}
				
			}
		}
		resetAlreadyMoved();
	}
	
	// Ewww, another iteration, but I dunno another way?
	private void resetAlreadyMoved(){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				Cell currentCell = getTileAt(x,y);
				currentCell.setAlreadyMoved(false);
							
			}
		}		
	}
	
	public boolean findValidMove(int x, int y){
		Cell possibleMove = getTileAt(x, y);
		if(possibleMove != null)
				return true;
		
		return false;
	}
	
	// Generate random # between 0 and max.
	public int generateRandomNumber(int max){
		Random random = new Random();
		return random.nextInt(max);
	}
		
	/*
	 * TODO: Add diagonal movement
	 *  Let Hunters kill 2 zombies at a time.
	 */
	public boolean processHunterMove(Cell cell){
		int x = cell.getX();
		int y = cell.getY();
		int zombieNeighbors = 0;
		
		Cell possibleMove = null;
		List<Cell> allPossibleMoves = new LinkedList<Cell>();
		
		// Check North
		possibleMove = getTileAt(x - 1, y);
		if(possibleMove != null && (possibleMove.getCellType() == Cell.CellType.Empty || possibleMove.getCellType() == Cell.CellType.Zombie))
			allPossibleMoves.add(possibleMove);
		
		// Check West
		possibleMove = getTileAt(x, y - 1);
		if(possibleMove != null && (possibleMove.getCellType() == Cell.CellType.Empty || possibleMove.getCellType() == Cell.CellType.Zombie))
			allPossibleMoves.add(possibleMove);
		
		// Check South
		possibleMove = getTileAt(x + 1, y);
		if(possibleMove != null && (possibleMove.getCellType() == Cell.CellType.Empty || possibleMove.getCellType() == Cell.CellType.Zombie))
			allPossibleMoves.add(possibleMove);		
		
		// Check East
		possibleMove = getTileAt(x, y + 1);
		if(possibleMove != null && (possibleMove.getCellType() == Cell.CellType.Empty || possibleMove.getCellType() == Cell.CellType.Zombie))
			allPossibleMoves.add(possibleMove);
		
		if(allPossibleMoves.isEmpty())
			return false;
		
		possibleMove = allPossibleMoves.get(generateRandomNumber(allPossibleMoves.size()));
		
		if(possibleMove.getCellType() == Cell.CellType.Empty){
			cell.setCellType(Cell.CellType.Empty);
			possibleMove.setCellType(Cell.CellType.Hunter);
		} else if(possibleMove.getCellType() == Cell.CellType.Zombie){
			cell.setCellType(Cell.CellType.Empty);
			possibleMove.setCellType(Cell.CellType.Hunter);
			numberOfZombies--;
		}
		
		possibleMove.setAlreadyMoved(true);
		
		return true;
	}
	
	public boolean processZombieMove(Cell cell){
		int x = cell.getX();
		int y = cell.getY();

		Cell possibleMove = null;
		List<Cell> allPossibleMoves = new LinkedList<Cell>();
		
		// Check North
		possibleMove = getTileAt(x - 1, y);
		if(possibleMove != null)
			allPossibleMoves.add(possibleMove);
		
		// Check West
		possibleMove = getTileAt(x, y - 1);
		if(possibleMove != null)
			allPossibleMoves.add(possibleMove);
		
		// Check South
		possibleMove = getTileAt(x + 1, y);
		if(possibleMove != null)
			allPossibleMoves.add(possibleMove);		
		
		// Check East
		possibleMove = getTileAt(x, y + 1);
		if(possibleMove != null)
			allPossibleMoves.add(possibleMove);
		
		if(allPossibleMoves.isEmpty())
			return false;
		
		possibleMove = allPossibleMoves.get(generateRandomNumber(allPossibleMoves.size()));
		
		if(possibleMove.getCellType() == Cell.CellType.Empty){
			cell.setCellType(Cell.CellType.Empty);
			possibleMove.setCellType(Cell.CellType.Zombie);
		} else if(possibleMove.getCellType() == Cell.CellType.Victim){
			possibleMove.setCellType(Cell.CellType.Zombie);
			numberOfVictims--;
			numberOfZombies++;
		} else if(possibleMove.getCellType() == Cell.CellType.Hunter){
			possibleMove.setCellType(Cell.CellType.Zombie);
			numberOfHunters--;
			numberOfZombies++;
		}
		
		possibleMove.setAlreadyMoved(true);
		
		return true;
	}
	
	
	public boolean processHumanMove(Cell cell){
		int x = cell.getX();
		int y = cell.getY();
		Cell possibleMove = null;
		List<Cell> allPossibleMoves = new LinkedList<Cell>();
		
		// Check North
		possibleMove = getTileAt(x - 1, y);
		if(possibleMove != null && possibleMove.getCellType() == Cell.CellType.Empty)
			allPossibleMoves.add(possibleMove);
		
		// Check West
		possibleMove = getTileAt(x, y - 1);
		if(possibleMove != null && possibleMove.getCellType() == Cell.CellType.Empty)
			allPossibleMoves.add(possibleMove);
		
		// Check South
		possibleMove = getTileAt(x + 1, y);
		if(possibleMove != null && possibleMove.getCellType() == Cell.CellType.Empty)
			allPossibleMoves.add(possibleMove);
		
		// Check East
		possibleMove = getTileAt(x, y + 1);
		if(possibleMove != null && possibleMove.getCellType() == Cell.CellType.Empty)
			allPossibleMoves.add(possibleMove);
		
		if(allPossibleMoves.isEmpty())
			return false;
		
		possibleMove = allPossibleMoves.get(generateRandomNumber(allPossibleMoves.size()));
		
		cell.setCellType(Cell.CellType.Empty);
		possibleMove.setCellType(Cell.CellType.Victim);
		possibleMove.setAlreadyMoved(true);
		
		return true;
	}
	
	
	public void randomizeBoard(){
		Random random = new Random();
		//cells[9][9].type = Cell.CellType.Victim;
		for(int x = 0; x < width ; x++){
			for(int y = 0; y < height; y++){
				
			
				int randomNumber = random.nextInt(4);
				//System.out.println(randomNumber);
				if(randomNumber == 0){
					cells[x][y].type = Cell.CellType.Empty;
				} else if (randomNumber == 1){
					cells[x][y].type = Cell.CellType.Victim;
					numberOfVictims++;
				} else if (randomNumber == 2){
					cells[x][y].type = Cell.CellType.Zombie;
					numberOfZombies++;
				} else {
					cells[x][y].type = Cell.CellType.Hunter;
					numberOfHunters++;
				}
				
			}
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNumberOfHumans() {
		return numberOfVictims;
	}

	public int getNumberOfZombies() {
		return numberOfZombies;
	}

	public int getNumberOfHunters() {
		return numberOfHunters;
	}

	public int getZombieMoveCount() {
		return zombieMoveCount;
	}

	public void setZombieMoveCount(int zombieMoveCount) {
		this.zombieMoveCount = zombieMoveCount;
	}

	public int getVictimMoveCount() {
		return victimMoveCount;
	}

	public void setVictimMoveCount(int victimMoveCount) {
		this.victimMoveCount = victimMoveCount;
	}

	public int getHunterMoveCount() {
		return hunterMoveCount;
	}

	public void setHunterMoveCount(int hunterMoveCount) {
		this.hunterMoveCount = hunterMoveCount;
	}

}
