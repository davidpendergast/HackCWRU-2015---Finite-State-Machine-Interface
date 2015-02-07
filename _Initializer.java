import java.awt.Color;
import java.awt.Graphics;


public class _Initializer {
	
	public static void main(String[] cucumber){
		Window window = new Window(960,640);
		
		World world = new World();
		InputAdapter input_adapter = new InputAdapter(world);
		window.addListener(input_adapter);
		
		int x_pos = window.frame.getX() + window.frame.getWidth();
		int y_pos = window.frame.getY();
		SettingsWindow settings_window = new SettingsWindow(x_pos,y_pos);
		
		window.setName("FSM Interface");
		settings_window.setName("Settings");
		window.requestFocus();
		
		ClickHandler click_handler = new ClickHandler(world, input_adapter, settings_window);
		
		while(true){
			
			Graphics g = window.getGraphics();
			g.setColor(Constants.background_color);
			g.fillRect(0, 0, window.getSize().width, window.getSize().height);
			world.renderStates(g, 0, 0);	
			world.renderLinks(g, 0, 0);
			window.drawImage();
			
			input_adapter.freeze();
			click_handler.handleClicks();
			click_handler.HandleDrags();
			click_handler.HandleMouseMove();
			input_adapter.applyInputs();
			input_adapter.unfreeze();
			

			sleep(15);
//			System.out.println(world.states);
		}
	}
	
	public static void sleep(int ms){
		try {
			Thread.sleep((long)ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
