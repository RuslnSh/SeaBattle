package battle.sea.main;

public class Cell {
	private int x;
	private int y;
	
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
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
	
	public boolean equals(Object obj) {
		Cell cell = (Cell) obj;
		return this.x == cell.getX() && this.y == cell.getY();
	}
 
	public int hashCode() {
		return x + y;
	}
}
