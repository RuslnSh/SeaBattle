package battle.sea.main;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import battle.sea.graphics.GraphicsModule;

public class Game implements Runnable{
	private AtomicBoolean running;
	GraphicsModule gModule;
	
	public Game(){
		running = new AtomicBoolean(false);
		gModule = new GraphicsModule();
	}
	
	public void start(){
		if (running.compareAndSet(false, true)){
			JFrame frame = new JFrame();
			frame.setSize(Constants.DIM);
			//frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setIgnoreRepaint(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(gModule);
			frame.setVisible(true);
						
			new Thread(this).start();
		}
	}
	
	public void run(){		
		while (running.get()){
			gModule.render();
		}
	}
}
