import java.awt.Color;
import java.util.ArrayList;

/**
 * Handles the Step and Reset functions of the FSM. 
 * @author dpendergast
 *
 */
public class LogicHandler {
	World world;
	
	State initial_state = null;
	
	public String current_string = "";
	public Color[] colors = new Color[1];
	public int index = 0;
	
	public LogicHandler(World w, State initial_state){
		this.world = w;
		this.initial_state = initial_state;
	}
	
	public void reset(){

		for(State s : world.states){
			s.setIsActive(false);
		}
		
		index = 0;

		world.activateETransitions(initial_state);
	}
	
	public void setString(String s){
		index = 0;
		current_string = s;
		Color[] temp = new Color[s.length()];
		
		colors = temp;
	}
	
	public boolean isInitialState(State s){
		return s == initial_state;
	}

	public void step() {
		if(index == current_string.length())
			return;
		
		ArrayList<State> active_states = new ArrayList<State>();
		
		for(State s : world.states){
			if(s.isActive()){
				active_states.add(s);
				s.setIsActive(false);
			}
		}
		
		char c = current_string.charAt(index);
		boolean found_fail = false;
		boolean found_succ = false;
		
		for(State s : active_states){
			for(Link l : world.linksFrom(s)){
				if(l.containsCondition(c)){
					
					//activating the state, and all E connected states.
					world.activateETransitions(l.to());
					
					if(l.to().isSuccessState()) found_succ = true;
					else found_fail = true;
				}
			}
		}
		
		if(found_succ){
			colors[index] = Constants.active_success_color;
		}
		else if(found_fail){
			colors[index] = Constants.active_fail_color;
		}
		else colors[index] = Constants.inactive_color;
		
		index++;
		
	}
	
	

}
