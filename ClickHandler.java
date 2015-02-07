import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


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
	
	public void HandleDrags(){
		
	}

}
