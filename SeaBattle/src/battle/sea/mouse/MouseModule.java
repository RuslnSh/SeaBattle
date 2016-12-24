package battle.sea.mouse;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;

public class MouseModule extends MouseAdapter{
	private final Collection<Point> mousePoints;
	
	public MouseModule(){
		mousePoints = new ArrayList<Point>();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		mousePoints.add(e.getPoint());
	}
	
	public boolean isEmpltyMousePressed(){
		return mousePoints.size()==0?true:false;
	}
	
	public Collection<Point> getMousePressed(){
		Collection<Point> tmpPoints = null;
		
		if (!isEmpltyMousePressed()){
			tmpPoints = new ArrayList<Point>(mousePoints);
			mousePoints.clear();
		}
		
		return tmpPoints;
	}
}
