package battle.sea.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;

public class KeyboardModule implements KeyListener {
	private final Collection<KeyEvent> keyPressedEvents;
	
	public KeyboardModule(){
		keyPressedEvents = new ArrayList<KeyEvent>();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyPressedEvents.add(e);
	}
	
	public boolean isEmpltyKeyPressed(){
		return keyPressedEvents.size()==0?true:false;
	}
	
	public Collection<KeyEvent> getKeyPressed(){
		Collection<KeyEvent> tmpKey = null;
		
		if (!isEmpltyKeyPressed()){
			tmpKey = new ArrayList<KeyEvent>(keyPressedEvents);
			keyPressedEvents.clear();
		}
		
		return tmpKey;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
