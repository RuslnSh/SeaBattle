package battle.sea.logic.sea;

import java.util.ArrayList;

import battle.sea.main.Constants;

public class Sea {
	private ArrayList<SeaCell> sea;
	
	public Sea(){
		sea = new ArrayList<SeaCell>();
		fillSea();
	}
	
	private void fillSea(){
		for(int x=0;x<Constants.WIDTH_STEP;x++)
			for(int y=0;y<Constants.HEIGHT_STEP;y++)
				sea.add(new SeaCell(x,y));
	}
	
	public ArrayList<SeaCell> getSeaCellArr(){
		return sea;
	}
	
	public SeaCell getSeaCell(Object obj){
		for (SeaCell s:sea)
				if (s.equals(obj))
					return s;
		return null;
	}
	
	public SeaCell getSeaCell(int x, int y){
		for (SeaCell s:sea)
				if (s.equals(x, y))
					return s;
		return null;
	}
	
	public void surroundShip(Object obj){		
		SeaCell seacell = getSeaCell(obj);
		seacell.setShip(true);
		
		for (SeaCell sc:sea)
			if (sc.nearShip(seacell))
				sc.setAround(true);
	}
	
	public void surroundDrownedShip(Object obj){		
		SeaCell seacell = getSeaCell(obj);
		seacell.setShip(true);
		
		for (SeaCell sc:sea)
			if (sc.nearShip(seacell))
				sc.setAroundDrowned(true);
	}
	
	/*public int getSeaCellLength(){
	return sea.size();
	}
	
	public int getX(int i){
		return sea.get(i).getX();
	}
	
	public int getY(int i){
		return sea.get(i).getY();
	}*/
}
