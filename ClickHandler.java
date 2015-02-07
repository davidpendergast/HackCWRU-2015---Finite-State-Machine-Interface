import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;


public class ClickHandler {
	
	World world;
	InputAdapter input_adapter;
	SettingsWindow settings;
	
	public ClickHandler(World world, InputAdapter input_adapter, SettingsWindow settings){
		this.world = world;
		this.input_adapter = input_adapter;
		this.settings = settings;
	}
	
	public void handleClicks(){
		if(settings.stateIsSelected()){
			clickStates();
		}
		else{
			clickLinks();
		}
	
	}
	
	private void clickStates(){
		input_adapter.freeze();
		
		MouseEvent click = input_adapter.last_click;
		if(click == null)
			return;
		
		//geting some vars
		int x = click.getX();
		int y = click.getY();
		int button = click.getButton();

		State s = world.getStateAt(x,y);
		
		//if it's a right click, either toggle the success state of clicked state, or do nothing.
		if(button == 3){
			if(s != null){
				s.toggleIsSuccessState();
			}
			else{
				world.deselect();
			}
			return;
		}
		
		if(s != null){
			world.select(s);
			return;
		}
		
		boolean shift_held = input_adapter.held_keys.contains(KeyEvent.VK_SHIFT);
		
		if(s == null && shift_held){
			s = new State(x,y);
			world.addState(s);
			world.select(s);
			return;
		}
		
		if(s == null && !shift_held){
			world.deselect();
		}
		
		input_adapter.unfreeze();
	}
	
	private void clickLinks(){
		MouseEvent click = input_adapter.last_click;
		boolean holding_shift = input_adapter.held_keys.contains(KeyEvent.VK_SHIFT);
		if(click == null)
			return;
		
		State s = world.getStateAt(click.getX(), click.getY());
		Link l = world.getLinkAt(click.getX(), click.getY());
		
		if(world.setting_new_link){
			if(s != null){
				//if currently setting a new link, and a state has been clicked, complete the link
				world.selected_link.setReciever(s);
				world.setting_new_link = false;
				return;
			}
			else{
				//change nothing
			}
		}
		else{
			//not setting new link
			if(holding_shift){
				if(s != null){
					//holding shift, and clicked a state. start new link
					Link new_link = new Link(s, click.getX(), click.getY());
					world.addLink(new_link);
					world.select(new_link);
					world.setting_new_link = true;
				}
			}
			else{
				//not setting, or holding shift. just select whatever link is clicked.
				if(click.getButton() == 1)
					world.select(l);
				else{
					world.select(l);
					if(l != null){
						l.swapDirection();
					}
				}
				
			}
		}
		

		
	}
	
	public void HandleDrags(){
		if(settings.stateIsSelected()){
			dragStates();
		}
		else{
			dragLinks();
		}
	}
	
	private void dragStates(){
		
		MouseEvent drag = input_adapter.last_drag;
		
		if(drag != null && SwingUtilities.isLeftMouseButton(drag)){
			world.moveSelected(drag.getX(), drag.getY());
			drag = null;
		}
		
	}
	
	private void dragLinks(){
		if(world.setting_new_link){
			return;
		}
		
		MouseEvent drag = input_adapter.last_drag;
		Link select = world.selected_link;
		
		if(drag == null || select == null)
			return;
		
		select.setC(drag.getX(), drag.getY());
	}
	
	public void HandleMouseMove(){
		MouseEvent move = input_adapter.last_move;
		if(!world.setting_new_link || move == null){
			return;
		}
		
		world.selected_link.setLocation(move.getX(), move.getY());
		
		
		
		
	}
}
