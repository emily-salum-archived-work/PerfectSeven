package interface_classes.administrator;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import interface_classes.ControlledPanel;
import interface_classes.DefaultInterface;
import main.Main;

@SuppressWarnings("serial")
public class RefimControl extends ControlledPanel{

	
	public RefimControl(ControlledPanel p, int X, int Y, int WIDTH, int HEIGHT)
	{
		super.controlled_inicialize(p,X,Y, WIDTH, HEIGHT);
		
		JTextField fator_text = DefaultInterface.make_default_area_text("Change Factor:");
		p.add(fator_text);
		fator_text.setBounds(rectangle_from_total_rect(new Rectangle(0,5,100,30)));
		
		
		JTextField fator_input = DefaultInterface.make_edittable_text();
		p.add(fator_input);
		fator_input.setBounds(rectangle_from_total_rect(new Rectangle(0,37,100,30)));
		fator_input.setText(Double.toString(Main.connection.get_fator()));
		
		
		JButton change_fator = DefaultInterface.make_default_button("change factor",WIDTH*25/100);
		p.add(change_fator);
		change_fator.setBounds(rectangle_from_total_rect(new Rectangle(0,67,100,50)));
		change_fator.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Main.connection.set_fator(Double.parseDouble(fator_input.getText()));
			}
			
		});
		
	}
	
	
}
