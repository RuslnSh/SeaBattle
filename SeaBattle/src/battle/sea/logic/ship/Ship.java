package battle.sea.logic.ship;

import battle.sea.logic.ship.ShipCell;

public class Ship {
	private ShipCell[] ship;
	private boolean isDrowned;
	
	public Ship(int length, int x, int y, int direction){
		ship = new ShipCell[length];
		setDrowned(false);
		
		switch(direction){
		case 1: 
			for (int i=0;i<length;i++)
				ship[i] = new ShipCell(x,y-i);
			break;
		case 2:
			for (int i=0;i<length;i++)
				ship[i] = new ShipCell(x+i,y);
			break;
		case 3:
			for (int i=0;i<length;i++)
				ship[i] = new ShipCell(x,y+i);
			break;
		case 4:
			for (int i=0;i<length;i++)
				ship[i] = new ShipCell(x-i,y);
			break;
		default:
			ship[0] = new ShipCell(x,y);
			break;
		}
	}
	
	public ShipCell[] getShip(){
		return ship;
	}
	
	public ShipCell getShipCell(int i){
		return getShip()[i];
	}
	
	public int getX(int i){
		return getShip()[i].getX();
	}
	
	public int getY(int i){
		return getShip()[i].getY();
	}
	
	public int getLength(){
		return getShip().length;
	}

	public boolean isDrowned() {
		for(int i=0;i<ship.length;i++)
			if (!ship[i].isHit())
				return false;
		
		setDrowned(true);
		return true;
	}

	public boolean isDrownedProp() {
		return isDrowned;
	}

	public void setDrowned(boolean isDrowned) {
		this.isDrowned = isDrowned;
	}
}
