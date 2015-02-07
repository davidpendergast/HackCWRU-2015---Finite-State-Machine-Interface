import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;


public class SettingsWindow extends Window {

	JCheckBox state_checkbox;
	JCheckBox link_checkbox;
	
	public SettingsWindow(int x_pos, int y_pos){
		super(320, 640);
		frame.setLocation(x_pos, y_pos);
		
		state_checkbox = new JCheckBox("State", true);
		panel.add(state_checkbox);
		link_checkbox = new JCheckBox("Link");
		panel.add(link_checkbox);
		
		setUpStateAndLink();
		
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
	
	public boolean stateIsSelected(){
		return state_checkbox.isSelected();
	}

}
