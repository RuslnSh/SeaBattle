package battle.sea.logic;

import java.util.ArrayList;

import battle.sea.logic.sea.Sea;
import battle.sea.logic.sea.SeaCell;
import battle.sea.logic.ship.Ship;
import battle.sea.logic.ship.ShipCell;
import battle.sea.main.Constants;

public class PlaceShips {
	private ArrayList<Ship> ships;
	private Sea sea;
	
	public PlaceShips(){
		ships = new ArrayList<Ship>();
		sea = new Sea();
		
		placeAlgorithm_1(1,2,3,4);
	}
	
	private boolean checkShipLocation(int count, int x, int y, int dir){
		
		// Check boundary
		switch(dir){
			case 1:
				if (y-count+1 < Constants.START_STEP)
					return false;
				break;
			case 2:
				if (count+x > Constants.WIDTH_STEP)
					return false;
				break;
			case 3:
				if (y+count > Constants.HEIGHT_STEP)
					return false;				
				break;
			case 4:
				if (x-count+1 < Constants.START_STEP)
					return false;
				break;
		}
		
		// Check surrounded ships
		Ship ship = new Ship(count, x, y, dir);
		for (int i=0;i<ship.getLength();i++){
			if (sea.getSeaCell(ship.getShipCell(i)).isAround() 
					|| sea.getSeaCell(ship.getShipCell(i)).isShip())
				return false;
		}
		
		return true;
	}
	
	/*
	 * Random Algorithm
	 * */
	private void placeAlgorithm_1(int s4, int s3, int s2, int s1){
		int min = 0;
		int maxX = Constants.WIDTH_STEP-1;
		int maxY = Constants.HEIGHT_STEP-1;
		int minDir = 1;
		int maxDir = 4;
		
		int x;
		int y;
		int dir;
		
		// 4x
		for (int i=0;i<s4;i++){
			do {
				x = min + (int)(Math.random() * ((maxX - min) + 1));
				y = min + (int)(Math.random() * ((maxY - min) + 1));
				dir = minDir + (int)(Math.random() * ((maxDir - minDir) + 1));
			} while (!checkShipLocation(4, x, y, dir));
			
			Ship ship = new Ship(4, x, y, dir);
			for (int sc=0;sc<ship.getLength();sc++)
				sea.surroundShip(ship.getShipCell(sc));
			
			ships.add(ship);
		}
		
		// 3x
		for (int i=0;i<s3;i++){
			do {
				x = min + (int)(Math.random() * ((maxX - min) + 1));
				y = min + (int)(Math.random() * ((maxY - min) + 1));
				dir = minDir + (int)(Math.random() * ((maxDir - minDir) + 1));				
			} while (!checkShipLocation(3, x, y, dir));
			
			Ship ship = new Ship(3, x, y, dir);
			for (int sc=0;sc<ship.getLength();sc++)
				sea.surroundShip(ship.getShipCell(sc));
			
			ships.add(ship);
		}
		
		// 2x
		for (int i=0;i<s2;i++){
			do {
				x = min + (int)(Math.random() * ((maxX - min) + 1));
				y = min + (int)(Math.random() * ((maxY - min) + 1));
				dir = minDir + (int)(Math.random() * ((maxDir - minDir) + 1));
			} while (!checkShipLocation(2, x, y, dir));
			
			Ship ship = new Ship(2, x, y, dir);
			for (int sc=0;sc<ship.getLength();sc++)
				sea.surroundShip(ship.getShipCell(sc));
			
			ships.add(ship);
		}
		
		// 1x
		for (int i=0;i<s1;i++){
			do {
				x = min + (int)(Math.random() * ((maxX - min) + 1));
				y = min + (int)(Math.random() * ((maxY - min) + 1));
				dir = minDir + (int)(Math.random() * ((maxDir - minDir) + 1));
			} while (!checkShipLocation(1, x, y, dir));
			
			Ship ship = new Ship(1, x, y, dir);
			for (int sc=0;sc<ship.getLength();sc++)
				sea.surroundShip(ship.getShipCell(sc));
			
			ships.add(ship);
		}
	}
		
	public void attack(int x, int y){
		System.out.println(x + " " + y);
		
		SeaCell cell = sea.getSeaCell(x, y);
		cell.setPushed(true);
		
		if (cell.isShip())
			for (Ship ship:getShips())
				for(int i=0;i<ship.getLength();i++){
					ShipCell sc = ship.getShipCell(i);
					if (sc.equals(x, y)){
						sc.setHit(true);
						
						if (ship.isDrowned())
							for (int c=0;c<ship.getLength();c++)
								sea.surroundDrownedShip(ship.getShipCell(c));
						
						return;
					}
				}
	}
	
	public boolean isGameOver(){
		for (Ship ship:ships)
			if (!ship.isDrownedProp())
				return false;
		
		return true;
	}
	
	public ArrayList<Ship> getShips(){
		return ships;
	}
	
	public ArrayList<SeaCell> getSeaArr(){
		return sea.getSeaCellArr();
	}
	
	/*public int getShipLength(){
		return ships.size();
	}
	
	public Ship getShip(int i){
		return ships.get(i);
	}

	public Sea getSea() {
		return sea;
	}
	
	public SeaCell getSeaCell(Object obj){
		return sea.getSeaCell(obj);
	}
	
	public int getSeaLength(){
		return sea.getSeaCellLength();
	}
	
	public int getSeaCellX(int i){
		return sea.getX(i);
	}
	
	public int getSeaCellY(int i){
		return sea.getY(i);
	}*/
}
