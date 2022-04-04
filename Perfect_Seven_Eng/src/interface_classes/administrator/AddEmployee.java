package interface_classes.administrator;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import database_classes.DatabaseConnection;
import interface_classes.Action;
import interface_classes.ConfirmationScreen;
import interface_classes.ControlledPanel;
import interface_classes.DefaultInterface;

@SuppressWarnings("serial")
public class AddEmployee extends ControlledPanel{

	public void controlled_inicialize(ControlledPanel p, int X, int Y, int WIDTH, int HEIGHT)
	{
		super.controlled_inicialize(p, X, Y, WIDTH, HEIGHT);
		
		JTextField worker_to_add = DefaultInterface.make_edittable_text();
		p.add(worker_to_add);
		worker_to_add.setBounds(rectangle_from_total_rect(new Rectangle(0,0,30,10)));
		
		JButton add_worker = DefaultInterface.make_default_button("add employee",WIDTH*25/100);
		p.add(add_worker);
		add_worker.setBounds(rectangle_from_total_rect(new Rectangle(0,10,30,20)));
		
		add_worker.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
				
				ConfirmationScreen c_add_worker = new ConfirmationScreen(p, "Are you sure you want to add " + worker_to_add.getText()+"?");
				
				
				Action r_view = p.reopenView();
				
				Action add_w = new Action() {

					@Override
					public void act() {
						// TODO Auto-generated method stub
						DatabaseConnection.worker_b.add_worker(worker_to_add.getText());
					}};
				
				c_add_worker.add_actions(true, add_w,r_view);
				c_add_worker.add_actions(false, r_view);
				
			}
		});
		
		
	}
}
