package battle.sea.main;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import battle.sea.graphics.GraphicsModule;

public class Game implements Runnable{
	private AtomicBoolean running;
	private GraphicsModule gModule;
	private JFrame frame;
	
	public Game(){
		running = new AtomicBoolean(false);
		gModule = new GraphicsModule();
	}
	
	public void start(){
		if (running.compareAndSet(false, true)){
			frame = new JFrame();
			frame.setSize(Constants.DIM);
			frame.setResizable(false);
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
			
			if (gModule.getWinner() != 0){
				running.set(false);
				String showMessage = gModule.getWinner()==1?"Congrats! You win!":"Unfortunately, you lose.";
				int res = JOptionPane.showConfirmDialog(frame, showMessage + "Continue game?");
				
				if(res==0){
					gModule.dispose();
					running.set(true);
				} else if(res==1 || res==2){
					System.exit(0);
				}
			}
		}
	}
}
