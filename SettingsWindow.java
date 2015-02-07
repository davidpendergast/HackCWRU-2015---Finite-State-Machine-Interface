import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;


public class SettingsWindow extends Window {

	JCheckBox state_checkbox;
	JCheckBox link_checkbox;
	
	JTextField text_entry;
	
	JButton reset;
	
	String current_string;
	int index = 0;
	
	public SettingsWindow(int x_pos, int y_pos){
		super(280, 640);
		frame.setLocation(x_pos, y_pos);
		
		state_checkbox = new JCheckBox("State", true);
		panel.add(state_checkbox);
		link_checkbox = new JCheckBox("Link");
		panel.add(link_checkbox);
		
		setUpStateAndLink();
		
		text_entry = new JTextField("aaaabbbb", 20);
		setUpTextEntry();
		panel.add(text_entry, BorderLayout.SOUTH);
		
		reset = new JButton("Reset States");
		panel.add(reset, BorderLayout.SOUTH);
		
		panel.updateUI();
		panel.repaint();
		
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
	
	private void setUpTextEntry(){
		text_entry.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				current_string = text_entry.getText();
				index = 0;
				System.out.println("SettingsWindow = "+current_string);
				
			}
			
		});
	}
	
	public boolean stateIsSelected(){
		return state_checkbox.isSelected();
	}

}
