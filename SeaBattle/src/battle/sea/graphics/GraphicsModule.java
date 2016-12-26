package battle.sea.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import battle.sea.keyboard.KeyboardModule;
import battle.sea.logic.PlaceShips;
import battle.sea.logic.aim.AimCell;
import battle.sea.logic.sea.SeaCell;
import battle.sea.logic.ship.Ship;
import battle.sea.main.Constants;
import battle.sea.mouse.MouseModule;

@SuppressWarnings("serial")
public class GraphicsModule extends Canvas{

	private BufferStrategy bufferStrategy;
	private KeyboardModule kModule;
	private MouseModule mModule;
	
	private AimCell aim;
	private	PlaceShips psPlayer;
	private	PlaceShips psRobot;
	
	private Graphics g;
	
	public GraphicsModule(){	
		kModule = new KeyboardModule();
		this.addKeyListener(kModule);
		mModule = new MouseModule();
		this.addMouseListener(mModule);
		
		aim = new AimCell(0,0);
		psPlayer = new PlaceShips();
		psRobot = new PlaceShips();
	}
	
	public void render(){
		bufferStrategy = this.getBufferStrategy();
		if (bufferStrategy == null){ 
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		
		processKeyboardAdapter();
		processMouseAdapter();
		
		g = bufferStrategy.getDrawGraphics();
		draw();
		
		bufferStrategy.show();
	}
	
	private void draw(){
		drawGrid();
		drawText();
		drawShips();
		drawAim();
	}
	
	private void drawGrid(){
		g.setColor(Color.WHITE);
		g.fillRect(Constants.WIDTH_1, Constants.HEIGHT_1, Constants.WIDTH, Constants.HEIGHT);
		g.fillRect(Constants.WIDTH_3, Constants.HEIGHT_1, Constants.WIDTH, Constants.HEIGHT);
		
		g.setColor(Color.GREEN);
		for (int i=Constants.HEIGHT_1;i<=Constants.HEIGHT_2;i+=Constants.SCALE)
			g.drawLine(Constants.WIDTH_1, i, Constants.WIDTH_2, i);
		
		for (int i=Constants.WIDTH_1;i<=Constants.WIDTH_2;i+=Constants.SCALE)
			g.drawLine(i, Constants.HEIGHT_1, i, Constants.HEIGHT_2);
		
		for (int i=Constants.HEIGHT_1;i<=Constants.HEIGHT_2;i+=Constants.SCALE)
			g.drawLine(Constants.WIDTH_3, i, Constants.WIDTH_4, i);
		
		for (int i=Constants.WIDTH_3;i<=Constants.WIDTH_4;i+=Constants.SCALE)
			g.drawLine(i, Constants.HEIGHT_1, i, Constants.HEIGHT_2);
	}
	
	private void drawText(){
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		
		for (int i=0;i<Constants.char_arr.length;i++)
			g.drawString(Constants.char_arr[i], Constants.WIDTH_1+i*Constants.SCALE+8, Constants.TOP-2);
		
		for (int i=0;i<Constants.num_arr.length;i++)
			g.drawString(Constants.num_arr[i], Constants.WIDTH_1-Constants.LEFT, Constants.TOP*2+i*Constants.SCALE+4);
		
		for (int i=0;i<Constants.char_arr.length;i++)
			g.drawString(Constants.char_arr[i], Constants.WIDTH_3+i*Constants.SCALE+8, Constants.TOP-2);
		
		for (int i=0;i<Constants.num_arr.length;i++)
			g.drawString(Constants.num_arr[i], Constants.WIDTH_3-Constants.LEFT, Constants.TOP*2+i*Constants.SCALE+4);
	}
	
	private void drawShips(){
		for (SeaCell sc:psPlayer.getSeaArr()){
			if(sc.isPushed() || sc.isAroundDrowned()){
				g.setColor(new Color(200,200,200));
				g.fillRect(sc.getX()*Constants.SCALE+Constants.WIDTH_1, sc.getY()*Constants.SCALE+Constants.HEIGHT_1, Constants.SCALE, Constants.SCALE);
			} else if (sc.isShip()){
				g.setColor(Color.BLUE);
				g.fillRect(sc.getX()*Constants.SCALE+Constants.WIDTH_1, sc.getY()*Constants.SCALE+Constants.HEIGHT_1, Constants.SCALE, Constants.SCALE);
			}
		}
		
		for (Ship ship:psPlayer.getShips()){
			for (int i=0;i<ship.getLength();i++){
				if (ship.getShipCell(i).isHit()){
					g.setColor(Color.BLACK);
					g.fillRect(ship.getX(i)*Constants.SCALE+Constants.WIDTH_1, ship.getY(i)*Constants.SCALE+Constants.HEIGHT_1, Constants.SCALE, Constants.SCALE);
				}
			}
		}
		
		for (SeaCell sc:psRobot.getSeaArr()){
			if(sc.isPushed() || sc.isAroundDrowned()){
				g.setColor(new Color(200,200,200));
				g.fillRect(sc.getX()*Constants.SCALE+Constants.WIDTH_3, sc.getY()*Constants.SCALE+Constants.HEIGHT_1, Constants.SCALE, Constants.SCALE);
			}
		}
		
		for (Ship ship:psRobot.getShips()){
			for (int i=0;i<ship.getLength();i++){
				if (ship.getShipCell(i).isHit()){
					g.setColor(Color.BLACK);
					g.fillRect(ship.getX(i)*Constants.SCALE+Constants.WIDTH_3, ship.getY(i)*Constants.SCALE+Constants.HEIGHT_1, Constants.SCALE, Constants.SCALE);
				}
			}
		}
	}
	
	private void drawAim(){
		g.setColor(Color.RED);
		g.fillRect(aim.getX()*Constants.SCALE+Constants.WIDTH_3, aim.getY()*Constants.SCALE+Constants.HEIGHT_1, Constants.SCALE, Constants.SCALE);
	}
	
	private void processKeyboardAdapter() {
		if (!kModule.isEmpltyKeyPressed())
	        for (KeyEvent event : kModule.getKeyPressed())
	            switch (event.getKeyCode()) {
		            case KeyEvent.VK_UP:
		            	if (aim.getY() > 0)
		            		aim.setY(aim.getY()-1);
	                    break;
	                case KeyEvent.VK_RIGHT:
	                	if (aim.getX() < Constants.WIDTH_STEP-1)
	                		aim.setX(aim.getX()+1);
	                    break;
	                case KeyEvent.VK_DOWN:
	                	if (aim.getY() < Constants.HEIGHT_STEP-1)
	                		aim.setY(aim.getY()+1);
	                    break;
	                case KeyEvent.VK_LEFT:
	                	if (aim.getX() > 0)
	                		aim.setX(aim.getX()-1);
	                    break;
	                case KeyEvent.VK_ENTER:
	                	psPlayer.attack(aim.getX(), aim.getY());
	                	psRobot.attack(aim.getX(), aim.getY());
	                	
	                	if(psRobot.isGameOver()){
	                		System.out.println("Player Win!!!");
	                	}
	                	if(psPlayer.isGameOver()){
	                		System.out.println("Robot Win!!!");
	                	}
	                	
	                	break;
	            }
	}
	
	private void processMouseAdapter(){
		if (!mModule.isEmpltyMousePressed()) {
			for (Point p : mModule.getMousePressed()){
				int highX = (int)(p.getX()-Constants.WIDTH_3)/Constants.SCALE;
				int highY = (int)(p.getY()-Constants.HEIGHT_1)/Constants.SCALE;
				
				if (highX >= 0 && highX < Constants.WIDTH_STEP && highY >= 0 && highY < Constants.HEIGHT_STEP){
					aim.setX(highX);
					aim.setY(highY);
				}
			}
		}
	}
}