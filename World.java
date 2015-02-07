import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	
	public LogicHandler logic_handler;
	
	public boolean setting_new_link = false;
	
	
	/**
	 * Constructs a new empty world object.
	 */
	public World(){
		states = new ArrayList<State>();
		links = new ArrayList<Link>();
		
//		addState(new State(0,0));
//		addState(new State(50,50));
//		links.add(new Link(states.get(0),states.get(1)));
		
		addState(new State(960/2,640/2));
		states.get(0).setName("INIT");
		
		logic_handler = new LogicHandler(this, states.get(0));
	}
	
	public void clear(){
		deselect();
		ArrayList<State> new_states = new ArrayList<State>();
		new_states.add(states.get(0));
		states = new_states;
		links = new ArrayList<Link>();
		setting_new_link = false;
		
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
	
	public void renderLinks(Graphics g, int x_offset, int y_offset){
		for(Link l : links){
			if(l != selected_link){
				l.render(Constants.link_color,g, x_offset, y_offset);
			}
		}
		
		if(selected_link != null){
			selected_link.render(Constants.select_color, g, x_offset, y_offset);
		}
	}
	
	public void renderString(Graphics g, int x, int y){
		char[] chars = logic_handler.current_string.toCharArray();
		Color[] c = logic_handler.colors;
		if(chars.length != c.length) return;
		
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
		Graphics2D g2 = (Graphics2D)g;
		for(int i = 0; i < chars.length; i++){
			if(c[i] == null){
				g2.setColor(Constants.inactive_color);
			}
			else
				g2.setColor(c[i]);
			g2.drawChars(chars,i,1, x+i*30, y);	
		}
	}
	
	public ArrayList<Link> linksFrom(State s){
		ArrayList<Link> result = new ArrayList<Link>();
		for(Link l : links){
			if(l.from() == s){
				result.add(l);
			}
		}
		return result;
	}
	
	public ArrayList<Link> linksTo(State s){
		ArrayList<Link> result = new ArrayList<Link>();
		for(Link l : links){
			if(l.to() == s){
				result.add(l);
			}
		}
		return result;
	}
	
	/**
	 * Add a new state to the world.
	 * @param state
	 */
	public boolean addState(State state) {
		//dont want duplicate states.
		if(!states.contains(state)){
			states.add(state);
			return true;
		}
		
		return false;
			
	}
	
	public boolean addLink(Link link){
		//dont want duplicate links
		if(!links.contains(link)){
			links.add(link);
			return true;
		}
		
		return false;
		
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
		
		Link l = selected_link;
		if(l != null){
			l.addChar(c);
		}
	}
	
	/**
	 * Deletes selected state or link.
	 */
	public void delete(){
		State s = selected_state;
		
		if(s != null && !logic_handler.isInitialState(s)){
			deselect();
			
			//remove all links to deleted node
			for(int i = links.size()-1; i >= 0; i--){
				if(links.get(i).containsState(s)){
					links.remove(i);
				}
			}
			
			states.remove(s);
		}
		
		Link l = selected_link;
		if(l != null){
			deselect();
			links.remove(l);
			setting_new_link = false;	
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
		
		Link l = selected_link;
		
		if(l != null){
			l.delChar();
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
		int dist = 10;
		Link closest = null;
		for(Link l : links){
			if(l.distTo(x, y) < dist){
				closest = l;
				dist = (int)l.distTo(x, y);
			}
		}
		return closest;
		
	}

}
