package battle.sea.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import battle.sea.keyboard.KeyboardModule;
import battle.sea.main.Cell;
import battle.sea.main.Constants;
import battle.sea.mouse.MouseModule;

@SuppressWarnings("serial")
public class GraphicsModule extends Canvas{

	private BufferStrategy bufferStrategy;
	private Graphics g;
	private Cell highlight;
	private KeyboardModule kModule;
	private MouseModule mModule;
	
	public GraphicsModule(){	
		kModule = new KeyboardModule();
		this.addKeyListener(kModule);
		mModule = new MouseModule();
		this.addMouseListener(mModule);
		
		highlight = new Cell(0,0);
	}
	
	public void render(){
		bufferStrategy = this.getBufferStrategy();
		if (bufferStrategy == null){ 
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		
		draw();
		processKeyboardAdapter();
		processMouseAdapter();
		
		bufferStrategy.show();
	}
	
	private void draw(){
		g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		
		g.setColor(Color.GREEN);
		for (int i=0;i<=Constants.HEIGHT;i+=Constants.SCALE)
			g.drawLine(0, i, Constants.WIDTH, i);
		
		for (int i=0;i<=Constants.WIDTH;i+=Constants.SCALE)
			g.drawLine(i, 0, i, Constants.HEIGHT);
		
		g.setColor(Color.RED);
		g.fillRect(highlight.getX()*Constants.SCALE, highlight.getY()*Constants.SCALE, Constants.SCALE, Constants.SCALE);
		
	}
	
	private void processKeyboardAdapter() {
		if (!kModule.isEmpltyKeyPressed())
	        for (KeyEvent event : kModule.getKeyPressed())
	            switch (event.getKeyCode()) {
		            case KeyEvent.VK_UP:
		            	if (highlight.getY() > 0)
		            		highlight.setY(highlight.getY()-1);
	                    break;
	                case KeyEvent.VK_RIGHT:
	                	if (highlight.getX() < Constants.WIDTH_STEP-1)
	                		highlight.setX(highlight.getX()+1);
	                    break;
	                case KeyEvent.VK_DOWN:
	                	if (highlight.getY() < Constants.HEIGHT_STEP-1)
	                		highlight.setY(highlight.getY()+1);
	                    break;
	                case KeyEvent.VK_LEFT:
	                	if (highlight.getX() > 0)
	                		highlight.setX(highlight.getX()-1);
	                    break;
	            }
	}
	
	private void processMouseAdapter(){
		if (!mModule.isEmpltyMousePressed()) {
			for (Point p : mModule.getMousePressed()){
				int highX = (int)p.getX()/Constants.SCALE;
				int highY = (int)p.getY()/Constants.SCALE;
				
				if (highX >= 0 && highX < Constants.WIDTH_STEP && highY >= 0 && highY < Constants.HEIGHT_STEP){
					highlight.setX(highX);
					highlight.setY(highY);
				}
			}
		}
	}
}
