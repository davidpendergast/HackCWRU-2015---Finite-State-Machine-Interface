import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.event.ListSelectionEvent;


public class World {
	
	ArrayList<State> states;
	ArrayList<Link> links;
	
	State selected_state = null;
	Link selected_link = null;
	
	int state_num = 0;
	
	
	/**
	 * Constructs a new empty world object.
	 */
	public World(){
		states = new ArrayList<State>();
		links = new ArrayList<Link>();
	}
	
	/**
	 * Render every state in the world.
	 * @param g
	 * @param x_offset
	 * @param y_offset
	 */
	public void renderStates(Graphics g, int x_offset, int y_offset){		
		//rendering every state except the selected state
		for(State s : states){
			if(s != selected_state)
				s.render(g, x_offset, y_offset);
		}
		
		if(selected_state != null){
			selected_state.render(Constants.select_color, g, x_offset, y_offset);
		}
	}
	
	/**
	 * Add a new state to the world.
	 * @param state
	 */
	public void addState(State state) {
		states.add(state);	
	}

	/**
	 * Handling a non-dragging click event. Will either: deselect state/link, select state, select link, place new state, or start new link.
	 * @param button
	 * @param x
	 * @param y
	 * @param shift_held
	 */
	public void click(int button, int x, int y, boolean shift_held) {
//		System.out.println("World - clicking "+button);
		
		State s = getStateAt(x,y);
		
		//if it's a right click
		if(button == 3){
			if(s != null){
				s.toggleIsSuccessState();
			}
			else{
				deselect();
			}
			return;
		}
		
		if(s != null){
			select(s);
			return;
		}
		
		if(s == null && shift_held){
			s = new State(x,y);
			addState(s);
			select(s);
			return;
		}
		
		if(s == null && !shift_held){
			deselect();
		}
		
	}
	
	/**
	 * Move the selected object to given location.
	 * @param x
	 * @param y
	 */
	public void moveSelected(int x, int y){
		State s = selected_state;
		if(s != null){
			s.setPosition(x, y);
		}
	}
	
	/**
	 * Type a char into selected state or link. If nothing is selected, input is discarded.
	 * @param c
	 */
	public void typeChar(char c){
		State s = selected_state;
		
		if(s != null){
			s.addLetter(c);
		}
	}
	
	/**
	 * Deletes selected state or link.
	 */
	public void delete(){
		State s = selected_state;
		
		if(s != null){
			deselect();
			states.remove(s);
		}
	}
	
	/**
	 * Removes a char from the selected state or link.
	 */
	public void deleteChar(){
		State s = selected_state;
		
		if(s != null){
			s.removeLetter();
		}
	}
	
	/**
	 * Selects given state.
	 * @param s
	 */
	public void select(State s){
		selected_state = s;
		selected_link = null;
	}
	
	/**
	 * Selects given link.
	 * @param l
	 */
	public void select(Link l){
		selected_state = null;
		selected_link = l;
	}
	
	/**
	 * Deselects everything.
	 */
	public void deselect(){
		selected_state = null;
		selected_link = null;
	}
	
	/**
	 * Returns the state that contains given point, if one exists.
	 * @param x
	 * @param y
	 * @return
	 */
	public State getStateAt(int x, int y){
		for(State s : states){
			if(s.distTo(x, y) <= Constants.state_radius){
				return s;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the link at the given point, if one exists.
	 * @param x
	 * @param y
	 * @return
	 */
	public Link getLinkAt(int x, int y){
		for(Link l : links){
			//
		}
		
		return null;
	}

}
