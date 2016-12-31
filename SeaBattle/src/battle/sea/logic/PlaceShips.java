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
	private SeaCell attackedCell;
	private ArrayList<SeaCell> finishShip; // assigned in attack() method, used in finishShip() method following attack to drown ship
	
	public PlaceShips(int place_ships){
		ships = new ArrayList<Ship>();
		sea = new Sea();
		finishShip = new ArrayList<SeaCell>();
		
		switch(place_ships){
			case 1: 
				placeAlgorithmRandom(Constants.CNT_4,Constants.CNT_3,Constants.CNT_2,Constants.CNT_1);
				break;
		}
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
	
	private void placeAlgorithmRandom(int s4, int s3, int s2, int s1){
		int min = Constants.START_STEP;
		int maxX = Constants.WIDTH_STEP;
		int maxY = Constants.HEIGHT_STEP;
		// Directions
		int minDir = 1;
		int maxDir = 4;
		
		int x;
		int y;
		int dir;
		
		// 4x
		for (int i=0;i<s4;i++){
			do {
				x = min + (int)(Math.random() * (maxX - min));
				y = min + (int)(Math.random() * (maxY - min));
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
				x = min + (int)(Math.random() * (maxX - min));
				y = min + (int)(Math.random() * (maxY - min));
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
				x = min + (int)(Math.random() * (maxX - min));
				y = min + (int)(Math.random() * (maxY - min));
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
				x = min + (int)(Math.random() * (maxX - min));
				y = min + (int)(Math.random() * (maxY - min));
				dir = minDir + (int)(Math.random() * ((maxDir - minDir) + 1));
			} while (!checkShipLocation(1, x, y, dir));
			
			Ship ship = new Ship(1, x, y, dir);
			for (int sc=0;sc<ship.getLength();sc++)
				sea.surroundShip(ship.getShipCell(sc));
			
			ships.add(ship);
		}
	}
	
	public void attack(int x, int y){	
		SeaCell cell = sea.getSeaCell(x, y);
		cell.setPushed(true);
		
		if (cell.isShip()){
			finishShip.add(cell);
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
	}
	
	public boolean isGameOver(){
		for (Ship ship:ships)
			if (!ship.isDrownedProp())
				return false;
		
		return true;
	}
	
	public SeaCell getAttackCoordiantes(int type_attack){
		attackedCell = new SeaCell(-1, -1);
		
		if (!finishShip()){
			switch(type_attack){
				case 1:
					attackRandom();
					break;
				case 2:
					attackRandomCheat();
					break;
			}
		}
		
		return attackedCell;
	}
	
	private boolean finishShip(){
		if(!finishShip.isEmpty()){
			// If ship drowned, clear finishShip array and return
			for(Ship ship:ships)
				for(int i=0;i<ship.getLength();i++){
					ShipCell sc = ship.getShipCell(i);
					if (sc.equals(finishShip.get(0)) && ship.isDrownedProp()){
						finishShip.clear();
						return false;
					}
				}
			
			int x0 = finishShip.get(0).getX();
			int y0 = finishShip.get(0).getY();
			
			if(finishShip.size() == 1){
				int xL = x0-1;
				int xR = x0+1;
				int yU = y0-1;
				int yD = y0+1;

				if (yU>=Constants.START_STEP)
					if (!sea.getSeaCell(x0,yU).isPushed()){
						attackedCell = sea.getSeaCell(x0,yU);
						return true;
					}
				
				if (xR<Constants.WIDTH_STEP)
					if (!sea.getSeaCell(xR,y0).isPushed()){
						attackedCell = sea.getSeaCell(xR,y0);
						return true;
					}

				if (yD<Constants.HEIGHT_STEP)
					if (!sea.getSeaCell(x0,yD).isPushed()){
						attackedCell = sea.getSeaCell(x0,yD);
						return true;
					}
			
				if (xL>=Constants.START_STEP)
					if (!sea.getSeaCell(xL,y0).isPushed()){
						attackedCell = sea.getSeaCell(xL,y0);
						return true;
					}
				
				return false;
			} else {
				int x1 = finishShip.get(1).getX();
				if (x0==x1){
					while (true){
						for(SeaCell sc:finishShip){
							int yNext = sc.getY()+1;
							if (yNext>=Constants.START_STEP && yNext<Constants.HEIGHT_STEP){
								attackedCell = sea.getSeaCell(x0,yNext);
								if (!(attackedCell.isPushed() || attackedCell.isAroundDrowned()))
									return true;
							}
							
							yNext = sc.getY()-1;
							if (yNext>=Constants.START_STEP && yNext<Constants.HEIGHT_STEP){
								attackedCell = sea.getSeaCell(x0,yNext);
								if (!(attackedCell.isPushed() || attackedCell.isAroundDrowned()))
									return true;
							}
						}
					}
				}
				
				int y1 = finishShip.get(1).getY();
				if (y0==y1){
					while (true){
						for(SeaCell sc:finishShip){
							int xNext = sc.getX()+1;
							if (xNext>=Constants.START_STEP && xNext<Constants.WIDTH_STEP){
								attackedCell = sea.getSeaCell(xNext, y0);
								if (!(attackedCell.isPushed() || attackedCell.isAroundDrowned()))
									return true;
							}
							
							xNext = sc.getX()-1;
							if (xNext>=Constants.START_STEP && xNext<Constants.WIDTH_STEP){
								attackedCell = sea.getSeaCell(xNext, y0);
								if (!(attackedCell.isPushed() || attackedCell.isAroundDrowned()))
									return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private void attackRandom(){
		attackedCell.setX(Constants.START_STEP + (int)(Math.random() * (Constants.WIDTH_STEP - Constants.START_STEP)));
		attackedCell.setY(Constants.START_STEP + (int)(Math.random() * (Constants.HEIGHT_STEP - Constants.START_STEP)));
		
		SeaCell sc = sea.getSeaCell(attackedCell);
		if (sc.isAroundDrowned() || sc.isPushed()){
			attackRandom();
		}
	}
	
	// Do not shoot around the ships, not even knowing where ships
	private void attackRandomCheat(){	
		attackedCell.setX(Constants.START_STEP + (int)(Math.random() * (Constants.WIDTH_STEP - Constants.START_STEP)));
		attackedCell.setY(Constants.START_STEP + (int)(Math.random() * (Constants.HEIGHT_STEP - Constants.START_STEP)));
		
		SeaCell sc = sea.getSeaCell(attackedCell);
		if (sc.isAround() ||sc.isPushed()){
			attackRandomCheat();
		}
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
