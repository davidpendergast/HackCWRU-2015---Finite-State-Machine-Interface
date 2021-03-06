import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Class which represents the States of the FSM. Deals with both the rendering as well as the logic components.
 * @author dpendergast
 *
 */
public class State {
	
	private int x,y;
	private boolean active;
	private boolean success_state;
	
	private String name;
	
	public State(int x, int y){
		this.x = x;
		this.y = y;
		
		active = false;
		success_state = false;
		name = "";
	}
	
	public int x(){ return x; }
	public int y(){ return y; }
	
	public void render(Graphics g, int x_offset, int y_offset){
		render(Constants.getColor(active,success_state), g, x_offset, y_offset);
	}
	
	public void render(Color c, Graphics g, int x_offset, int y_offset){
		g.setColor(c);
		int r = Constants.state_radius;
		g.drawOval(x - r - x_offset, y - r - y_offset, r*2, r*2);
		
		//rendering inner circle, if state is a success state
		if(success_state){
			r = Constants.inner_radius;
			g.drawOval(x - r - x_offset, y - r - y_offset, r*2, r*2);
		}
		
		//rendering name
		if(!name.equals("")){
			FontMetrics metrics = g.getFontMetrics();
			int height = metrics.getHeight();
			int width = metrics.stringWidth(name);
			g.drawChars(name.toCharArray(), 0, name.length(), x - width/2 - x_offset, y + height/4 - y_offset);
		}
		
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public String name(){
		return name;
	}
	
	public float distTo(int x, int y){
		int dx = this.x - x;
		int dy = this.y - y;
		
		return (float)Math.sqrt(dx*dx + dy*dy);
	}
	
	public void addLetter(char c){
		if(name.length() < Constants.state_name_max_length)
			name += c;
	}
	
	public void removeLetter(){
		if(name.equals("")){
			return;
		}
		
		name = name.substring(0, name.length()-1);
	}
	
	public boolean isSuccessState(){
		return success_state;
	}
	
	public void toggleIsSuccessState(){
		success_state = !success_state;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void setIsActive(boolean boo){
		active = boo;
	}
	
	public String toString(){
		String s = "State=("+x+","+y+")";
		
		return s;
	}

	public void setName(String string) {
		name = string;
	}

	public void setIsSuccessState(boolean is_success) {
		success_state = is_success;
	}
	
	

}
