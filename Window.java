import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window {
	
	JFrame frame;
	JPanel panel;
	Image img;
	
	public Window(int x_size, int y_size){
		frame = new JFrame();
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(x_size, y_size));
		frame.add(panel);
		frame.pack();
		
		//listener for resize events
		frame.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
		        panel.setPreferredSize(frame.getSize());   
		        img = panel.createVolatileImage(panel.getWidth(), panel.getHeight());
		    }

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		img = panel.createVolatileImage(x_size, y_size);
		
		frame.setVisible(true);
	}
	
	public void requestFocus(){
		frame.requestFocus();
	}
	
	public void setName(String name){
		frame.setTitle(name);
	}
	
	public ComponentListener getComponentListener(){
		return frame.getComponentListeners()[0];
	}
	
	public void setResizable(boolean boo){
		frame.setResizable(boo);
	}
	
	public Dimension getSize(){
		return frame.getSize();
	}
	
	public void drawImage(){
		panel.getGraphics().drawImage(img, 0, 0, null);
	}
	
	public Graphics getGraphics(){
		return img.getGraphics();
	}
	
	public void addListener(InputAdapter input_adapter){
		frame.addKeyListener(input_adapter);
		panel.addMouseListener(input_adapter);
		panel.addMouseMotionListener(input_adapter);
	}
	

}
