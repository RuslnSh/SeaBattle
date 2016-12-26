package battle.sea.logic.sea;

import battle.sea.main.Cell;

public class SeaCell extends Cell{
	private boolean isShip;
	private boolean isAround;	// With this flag marked cells around ships
	private boolean isPushed;
	private boolean isAroundDrowned;	// Cells around DROWNED ships
	
	public SeaCell(int x, int y){
		super(x,y);
		setShip(false);
		setAround(false);
		setPushed(false);
		setAroundDrowned(false);
	}

	public boolean isShip() {
		return isShip;
	}

	public void setShip(boolean isShip) {
		this.isShip = isShip;
		if (isShip)
			this.isAround = false;
	}

	public boolean isAround() {
		return isAround;
	}

	public void setAround(boolean isAround) {
		if (!this.isShip)
			this.isAround = isAround;
	}
	
	public boolean isPushed() {
		return isPushed;
	}

	public void setPushed(boolean isPushed) {
		this.isPushed = isPushed;
	}
	
	public boolean nearShip(SeaCell ship){
		if (this.getX() == ship.getX())
			if (this.getY() == ship.getY()+1 || this.getY() == ship.getY()-1)
				return true;
		
		if (this.getY() == ship.getY())
			if (this.getX() == ship.getX()+1 || this.getX() == ship.getX()-1)
				return true;
		
		if ((this.getX() == ship.getX()+1 && this.getY() == ship.getY()+1) ||
			(this.getX() == ship.getX()-1 && this.getY() == ship.getY()-1) ||
			(this.getX() == ship.getX()+1 && this.getY() == ship.getY()-1) ||
			(this.getX() == ship.getX()-1 && this.getY() == ship.getY()+1))
				return true;
		
		return false;
	}

	public boolean isAroundDrowned() {
		return isAroundDrowned;
	}

	public void setAroundDrowned(boolean isAroundDrowned) {
		this.isAroundDrowned = isAroundDrowned;
	}

}
