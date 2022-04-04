package interface_classes;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

import main.Interface;

public class ConfirmationScreen {

	
	ArrayList<Action> confirm_actions = new ArrayList<Action>();
	ArrayList<Action> cancel_actions = new ArrayList<Action>();
	
	public ConfirmationScreen(ControlledPanel p, String description)
	{
		p.removeAll();
		p.repaint();
		
		JTextField valor_text = DefaultInterface.make_default_area_text(description); 
		p.add(valor_text);
		valor_text.setBounds(p.rectangle_from_total_rect(new Rectangle(20,5,60,20)));
		

		JButton confirm = DefaultInterface.make_default_button("confirm",Interface.WIDTH*1.5f/100);
		p.add(confirm);
		confirm.setBounds(p.rectangle_from_total_rect(new Rectangle(25,40,10,10)));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				for(Action c : confirm_actions)
				{
					c.act();
				}
			}
			
		});
		
		
		JButton cancel = DefaultInterface.make_default_button("cancel",Interface.WIDTH*1.5f/100);
		p.add(cancel);
		cancel.setBounds(p.rectangle_from_total_rect(new Rectangle(65,40,10,10)));
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				for(Action c : cancel_actions)
				{
					c.act();
				}
			}
			
		});
		
		 
	}
	
	public void add_actions(boolean confirm, Action... actions)
	{
		if(confirm)
		{
			for(Action c : actions)
			{

				confirm_actions.add(c);
			}
			return;
		}
		
		
		for(Action c : actions)
		{

			cancel_actions.add(c);
		}
		
	}
	
	
	
}
