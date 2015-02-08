import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 * Class which handles everything in the small, input-taking window.
 * @author dpendergast
 *
 */
public class SettingsWindow extends Window {

	JCheckBox state_checkbox;
	JCheckBox link_checkbox;
	
	JTextField text_entry;
	
	JButton reset;
	JButton step;
	JButton clear;
	
	String current_string;
	
	boolean clear_world = false;
	boolean save_world = false;
	boolean load_world = false;
	
	JTextField file_name;
	JButton save;
	JButton load;
	
	World w;
	
	public SettingsWindow(int x_pos, int y_pos, World w){
		super(280, 640);
		this.w = w;
		
		frame.setLocation(x_pos, y_pos);
		
		state_checkbox = new JCheckBox("State", true);
		panel.add(state_checkbox);
		link_checkbox = new JCheckBox("Link");
		panel.add(link_checkbox);
		
		setUpStateAndLink();
		
		text_entry = new JTextField("aaaabbbb", 20);
		file_name = new JTextField("", 20);
		setUpTextEntries();
		panel.add(text_entry, BorderLayout.SOUTH);
		
		reset = new JButton("RESTART");
		panel.add(reset, BorderLayout.SOUTH);
		step = new JButton("STEP");
		panel.add(step, BorderLayout.SOUTH);
		clear = new JButton("CLEAR");
		panel.add(clear, BorderLayout.SOUTH);
		
		setUpButtons();
		
		panel.add(file_name);
		
		save = new JButton("SAVE");
		load = new JButton("LOAD");
		
		setUpSaveLoad();
		panel.add(save);
		panel.add(load);
		
		panel.updateUI();
		panel.repaint();
		
	}
	
	public String getFilename(){
		return file_name.getText();
	}
	
	public void giveLogicHandler(LogicHandler lh){
		w.logic_handler = lh;
		w.logic_handler.reset();
	}
	
	private void setUpStateAndLink(){
		state_checkbox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(link_checkbox.isSelected() == state_checkbox.isSelected())
				link_checkbox.setSelected(!link_checkbox.isSelected());
				
			}
			
		});
		
		link_checkbox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(state_checkbox.isSelected() == link_checkbox.isSelected())
				state_checkbox.setSelected(!state_checkbox.isSelected());
				
			}
		});
		
	}
	
	private void setUpTextEntries(){
		text_entry.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(w.logic_handler != null){
					w.logic_handler.setString(text_entry.getText());
				}
			}
			
		});
		
		file_name.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void setUpButtons(){
		reset.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(w.logic_handler != null){
					w.logic_handler.setString(text_entry.getText());
					w.logic_handler.reset();
				}
			}
			
		});
		
		step.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(w.logic_handler != null){
					w.logic_handler.step();
				}
			}
			
		});
		
		clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clear_world = true;
				
			}
			
		});
		
		
	}
	
	private void setUpSaveLoad(){
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				save_world = true;
			}
			
		});
		
		load.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				load_world = true;
			}
			
		});
	}
	
	public boolean stateIsSelected(){
		return state_checkbox.isSelected();
	}

}
