package battle.sea.logic.ship;

import battle.sea.main.Cell;

public class ShipCell extends Cell{
	private boolean isHit;
	
	public ShipCell(int x, int y) {
		super(x, y);
		setHit(false);
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean hit) {
		this.isHit = hit;
	}
	
}
