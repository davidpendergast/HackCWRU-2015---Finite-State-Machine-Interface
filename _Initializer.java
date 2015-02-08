import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Runable class which creates and initializes the rest of the program.
 * @author dpendergast
 *
 */
public class _Initializer {
	
	public static void main(String[] cucumber){
		Window window = new Window(960,640);
		
		World world = new World();
		InputAdapter input_adapter = new InputAdapter(world);
		window.addListener(input_adapter);
		
		int x_pos = window.frame.getX() + window.frame.getWidth();
		int y_pos = window.frame.getY();
		SettingsWindow settings_window = new SettingsWindow(x_pos,y_pos, world);
		
		window.setName("FSM Interface");
		settings_window.setName("Settings");
		window.requestFocus();
		
		ClickHandler click_handler = new ClickHandler(world, input_adapter, settings_window);
		settings_window.giveLogicHandler(world.logic_handler);
		
		while(true){
			
			Graphics g = window.getGraphics();
			g.setColor(Constants.background_color);
			g.fillRect(0, 0, window.getSize().width, window.getSize().height);
			world.renderStates(g, 0, 0);	
			world.renderLinks(g, 0, 0);
			world.renderString(g,0,window.getSize().height-40);
			window.drawImage();
			
			input_adapter.freeze();
			click_handler.handleClicks();
			click_handler.HandleDrags();
			click_handler.HandleMouseMove();
			input_adapter.applyInputs();
			input_adapter.unfreeze();
			
			if(settings_window.clear_world){
				world.clear();
				settings_window.clear_world = false;
			}
			
			if(settings_window.save_world){
				try {
					FileSaver.saveFile("save_data/"+settings_window.getFilename()+".txt", world);
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					System.out.println("Error: could not save.");
				}
				settings_window.save_world = false;
			}
			
			if(settings_window.load_world){
				try{
					FileLoader.loadFile("save_data/"+settings_window.getFilename()+".txt", world);
				} catch (IOException e) {
					
					System.out.println("Error: could not load: "+e.getMessage());
				}
					settings_window.load_world = false;
			}
			
			sleep(15);
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
