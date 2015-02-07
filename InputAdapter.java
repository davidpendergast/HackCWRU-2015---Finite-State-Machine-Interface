import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;


public class InputAdapter extends MouseAdapter implements  KeyListener {

	public World world;
	private boolean freeze = false;
	
	public MouseEvent last_click = null;
	public MouseEvent last_click_buffer = null;
	
	public MouseEvent last_drag = null;
	public MouseEvent last_drag_buffer = null;
	
	public Character last_char = null;
	public Character last_char_buffer = null;
	
	public HashSet<Integer> held_keys;

	//lazy concurrency safety ftw
	public CopyOnWriteArrayList<KeyEvent> typed_keys;
	
	public InputAdapter(World world) {
		this.world = world;
		held_keys = new HashSet<Integer>();
		typed_keys = new CopyOnWriteArrayList<KeyEvent>();
	}
	
	public void freeze(){
		freeze = true;
	}
	
	public void unfreeze(){
		last_click = last_click_buffer;
		last_click_buffer = null;
		
		last_drag = last_drag_buffer;
		last_drag_buffer = null;
		freeze = false;
	}
	
	/**
	 * Injects key inputs into the world.
	 */
	public void applyInputs(){
		freeze();
		
		//handling click event.
//		MouseEvent click = last_click;
//		if(click != null){
////			System.out.println("InputAdapter - "+held_keys);
//			world.click(click.getButton(), click.getX(), click.getY(), held_keys.contains(KeyEvent.VK_SHIFT));
//		}
		
		//handling drag event.
//		MouseEvent drag = last_drag;
//		if(drag != null){
//			world.moveSelected(drag.getX(), drag.getY());
//			drag = null;
//		}
		
		CopyOnWriteArrayList<KeyEvent> temp = typed_keys;
		typed_keys = new CopyOnWriteArrayList<KeyEvent>();
		
		//going through all key events
		for(KeyEvent ke : temp){
			char c = ke.getKeyChar();
			
//			System.out.println("ke = "+ke.getKeyCode() +", bs = "+KeyEvent.VK_BACK_SPACE+", del = "+KeyEvent.VK_DELETE);
			
			if(' ' <= c  && c <= '~'){
				//if c is an alphabetic char, it gets typed into the world
				world.typeChar(c);
			}
			else if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE){
				//if key is backspace, then a backspace event is shoved into world
				world.deleteChar();
			}
			else if(ke.getKeyCode() == KeyEvent.VK_DELETE){
				//if key is delete, then send delete event into world
				world.delete();
			}
		}
		unfreeze();
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		typed_keys.add(e);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		held_keys.add(e.getKeyCode());
		
		//non alpha-numeric keys cannot be processed as keyTyped events,
		//so they are handled here.
//		if(!Character.isAlphabetic(e.getKeyChar())){
			typed_keys.add(e);
//		}
		
//		System.out.println(held_keys);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		held_keys.remove(e.getKeyCode());
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
//		if(held_keys.contains(KeyEvent.VK_SHIFT))
//			return;
		System.out.println("Dragged!");
//		if(freeze)
//			last_drag_buffer = e;	
//		else{
//			last_drag = e;
//		}
		last_drag = e;
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		System.out.println("Pressed!");
		if(freeze)
			last_click_buffer = e;	
		else{
			last_click = e;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e){
		
	}
	
	public void mouseEntered(MouseEvent e){
		Component c = e.getComponent();
		System.out.println("entered");
		
	}

}
