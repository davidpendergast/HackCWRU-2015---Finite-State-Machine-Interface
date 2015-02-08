import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class which recieves and stores the Keyboard and Mouse events detected by the JFrame. Sends these events 
 * directly to the world when applyInputs is called.
 * @author dpendergast
 *
 */
public class InputAdapter extends MouseAdapter implements  KeyListener {

	public World world;
	private boolean freeze = false;
	
	public MouseEvent last_click = null;
	public MouseEvent last_click_buffer = null;
	
	public MouseEvent last_drag = null;
	public MouseEvent last_drag_buffer = null;
	
	public MouseEvent last_move = null;
	public MouseEvent last_move_buffer = null;
	
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
		
		last_move = last_move_buffer;
		last_move_buffer = null;
		
		last_drag = last_drag_buffer;
		last_drag_buffer = null;
		freeze = false;
	}
	
	/**
	 * Injects key inputs into the world.
	 */
	public void applyInputs(){
		
		CopyOnWriteArrayList<KeyEvent> temp = typed_keys;
		typed_keys = new CopyOnWriteArrayList<KeyEvent>();
		
		//going through all key events
		for(KeyEvent ke : temp){
			char c = ke.getKeyChar();
			
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
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		held_keys.add(e.getKeyCode());
		typed_keys.add(e);
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
		last_drag = e;
	}
	
	@Override
	public void mousePressed(MouseEvent e){
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
		if(freeze)
			last_move_buffer = e;	
		else{
			last_move = e;
		}
	}
	
	public void mouseEntered(MouseEvent e){
		
	}

}
