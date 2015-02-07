import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Set;


public class Link {
	
	//source and recieving state
	State s0 = null; 
	State s1 = null;
	
	//endpoints
	int x,y;
	
	int cx,cy;
	boolean custom_c = false;
	
	//how arky the arc should be
	float arkyness = 0;
	
	//transition inputs
	String conditions = "abc";
	int max_num_conditions = 8;
	
	public Link(State s0, int x, int y){
		this.s0 = s0;
		this.x = x;
		this.y = y;
	}
	
	public Link(State s0, State s1) {
		this(s0,0,0);
		s0.setIsActive(true);
		this.s1 = s1;
	}
	
	public void addChar(char c){
		if(conditions.length() < max_num_conditions){
			conditions += c;
		}
	}
	
	public void delChar(){
		if(conditions.length() != 0){
			conditions = conditions.substring(0,conditions.length()-1);
		}
	}
	
	private void setC(int x0, int y0, int x1, int y1){
		double dist = Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
		double angle0to1 = Math.atan2(y1-y0, x1-x0);
		double angle0toC = (angle0to1 + 1);
		
		cx = (int)(x0 + dist*Math.cos(angle0toC));
		cy = (int)(y0 + dist*Math.sin(angle0toC));
	}
	
	public void setC(int x, int y){
		cx = x;
		cy = y;
		custom_c = true;
	}
	
	public void setReciever(State s1){
		this.s1 = s1;
	}
	
	public float distTo(int x, int y){
		int dx = cx - x;
		int dy = cy - y;
		return (float)Math.sqrt(dx*dx + dy*dy);
	}

	public void render(Color c, Graphics g, int x_offset, int y_offset){
//		System.out.println("Link - rendering");
		
		int x0 = s0.x();
		int y0 = s0.y();
		
		int x1,y1;
		
		if(s1 == null){
			x1 = x;
			y1 = y;
		}
		else{
			x1 = s1.x();
			y1 = s1.y();
		}
		
		drawArcBetweenPoints(x0,y0,x1,y1,g,c);
		drawPointer(x1,y1,cx,cy,15f,g,c);
		drawText(cx, cy, g, c);
	}
	
	private void drawArcBetweenPoints(int x0, int y0, int x1, int y1, Graphics g, Color c){
		
		if(!custom_c)
			setC(x0,y0,x1,y1);
		
		QuadCurve2D.Double curve = new QuadCurve2D.Double(x0, y0, cx, cy, x1, y1);
		
		g.setColor(c);
		((Graphics2D)g).draw(curve);

		g.drawOval((int)cx, (int)cy, 5, 5);
	}
	
	/**
	 * Draws the point of the link.
	 * @param x1
	 * @param y1
	 * @param cx
	 * @param cy
	 */
	private void drawPointer(int x1, int y1, int cx, int cy, float length, Graphics g, Color c){
		float dx = cx - x1;
		float dy = cy - y1;
		float mag = (float)Math.sqrt(dx*dx + dy*dy);
		if(mag == 0)
			return;
		dx = length*dx / mag;
		dy = length*dy / mag;
		
		float cos = (float)Math.cos(Math.PI/6);
		float sin = (float)Math.sin(Math.PI/6);
		
		g.setColor(c);
		//doing a rotation linear transform
		g.drawLine(x1, y1, (int)(x1 + dx*cos - dy*sin), (int)(y1 + dx*sin + dy*cos));
		g.drawLine(x1, y1, (int)(x1 + dx*cos + dy*sin), (int)(y1 - dx*sin + dy*cos));
		g.drawLine((int)(x1 + dx*cos - dy*sin), (int)(y1 + dx*sin + dy*cos), (int)(x1 + dx*cos + dy*sin), (int)(y1 - dx*sin + dy*cos));
		
		
	}
	
	private void drawText(int cx, int cy, Graphics g, Color c){
			if(!conditions.equals("")){
				FontMetrics metrics = g.getFontMetrics();
				int height = metrics.getHeight();
				int width = metrics.stringWidth(conditions);
				g.setColor(c);
				g.drawChars(conditions.toCharArray(), 0, conditions.length(), cx - width/2, cy - height);
			}
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void swapDirection(){
		if(s1 == null)
			return;
		State temp = s0;
		s0 = s1;
		s1 = temp;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Link){
			Link l = (Link)obj;
			if(l.s1 == null || this.s1 == null)
				return false;
			else if(l.s0 == this.s0 && l.s1 == this.s1){
				return true;
			}
		}
		
		return false;
	}
	
	

}
