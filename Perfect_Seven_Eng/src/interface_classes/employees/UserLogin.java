package interface_classes.employees;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.bson.types.ObjectId;

import data_control.TableDataProvider;
import database_classes.DatabaseConnection;
import interface_classes.ControlledPanel;
import interface_classes.DefaultInterface;
import main.Main;
import main.Interface;
import java.awt.Image;
@SuppressWarnings("serial")
public class UserLogin extends ControlledPanel{

	public JTextField communication;
	public JTextField password_communication;

	JTextField username_input;
	JPasswordField password_input;
	JPasswordField password_confirmation_input;
	@Override
	public void inicialize(JPanel controller, int WIDTH, int HEIGHT) 
	{
		super.inicialize(controller, WIDTH, HEIGHT);
	
		communication = DefaultInterface.make_default_text("Insert account name.");
		communication.setBounds(rectangle_from_total_rect(new Rectangle(35,0,30,8)));
		add(communication);
		
		username_input = DefaultInterface.make_edittable_text();
		username_input.setBounds(rectangle_from_total_rect(new Rectangle(35,8,30,4)));
		add(username_input);
		
		
		
		password_communication = DefaultInterface.make_default_text("Insert password.");
		password_communication.setBounds(rectangle_from_total_rect(new Rectangle(35,12,30,8)));
		add(password_communication);
		
		password_input = DefaultInterface.make_password_text();
		password_input.setBounds(rectangle_from_total_rect(new Rectangle(35,20,30,4)));
		add(password_input);
	
		
		
		JTextField password_communication_repeat = DefaultInterface.make_default_text("Repeat password.");
		password_communication_repeat.setBounds(rectangle_from_total_rect(new Rectangle(35,24,30,8)));
		add(password_communication_repeat);
		
		
		password_confirmation_input = DefaultInterface.make_password_text();
		password_confirmation_input.setBounds(rectangle_from_total_rect(new Rectangle(35,32,30,4)));
		add(password_confirmation_input);
		ImageIcon c = new ImageIcon("res/closed_eye.png");
		Image dc = c.getImage().getScaledInstance(20, 20,  Image.SCALE_SMOOTH);
		c = new ImageIcon(dc);
	
		
		JButton show_hide_passwords = new JButton(c);
		add(show_hide_passwords);
		show_hide_passwords.setFocusable(false);
		show_hide_passwords.setBounds(rectangle_from_total_rect(new Rectangle(65,20,4,4)));
		show_hide_passwords.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(password_input.getEchoChar() == '*')
				{
					password_input.setEchoChar((char) 0);
					ImageIcon c = new ImageIcon("res/opened_eye.png");
					Image dc = c.getImage().getScaledInstance(20, 20,  Image.SCALE_SMOOTH);
					c = new ImageIcon(dc);
					show_hide_passwords.setIcon(c);
					return;
				}
				ImageIcon c = new ImageIcon("res/closed_eye.png");
				Image dc = c.getImage().getScaledInstance(20, 20,  Image.SCALE_SMOOTH);
				c = new ImageIcon(dc);
				show_hide_passwords.setIcon(c);
				password_input.setEchoChar('*');
			}
			
		});
		
			
		JButton show_hide_passwords2 = new JButton(c);
		add(show_hide_passwords2);
		show_hide_passwords2.setFocusable(false);
		show_hide_passwords2.setBounds(rectangle_from_total_rect(new Rectangle(65,32,4,4)));
		show_hide_passwords2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{

				if(password_confirmation_input.getEchoChar() == '*')
				{
					password_confirmation_input.setEchoChar((char) 0);
					ImageIcon c = new ImageIcon("res/opened_eye.png");
					Image dc = c.getImage().getScaledInstance(20, 20,  Image.SCALE_SMOOTH);
					c = new ImageIcon(dc);
					show_hide_passwords2.setIcon(c);
					return;
				}
				ImageIcon c = new ImageIcon("res/closed_eye.png");
				Image dc = c.getImage().getScaledInstance(20, 20,  Image.SCALE_SMOOTH);
				c = new ImageIcon(dc);
				show_hide_passwords2.setIcon(c);
				password_confirmation_input.setEchoChar('*');
			}
			
		});

		
		JButton submit = DefaultInterface.make_default_button("enter",WIDTH*2/100);
		add(submit);
		submit.setBounds(rectangle_from_total_rect(new Rectangle(35,37,30,4)));
		submit.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				attempt_login(username_input.getText(), password_input.getText(), password_confirmation_input.getText());
			}
		});
		
	
		
	}
	
	
	
	
	
	public void attempt_login(String name, String pw, String pwr)
	{
		TableDataProvider worker = DatabaseConnection.worker_b.getWorker(name);
		
		if(worker == null)
		{		
			communication.setText("name not found!");
			return;
		}
		
		if(pw.compareTo(pwr) != 0)
		{
			password_communication.setText("Passwords dont match");
			return;
		}
		
		if(pw.isEmpty())
		{
			password_communication.setText("Insert a password!");
			
			return;
		}
		
		Object pass = worker.getData("password");
		
		if(pass != null)
		{
			String password = (String) pass;
			if(password.compareTo(pw) != 0)
			{
				password_communication.setText("Incorrect password!");
				return;
			}
		}
		else
		{
			DatabaseConnection.worker_b.save_password((ObjectId)worker.getData("id"), pw);
		}

		Main.current_user = worker;
		finish_login();
	}

	public void finish_login()
	{
		Interface.change_display(new CustomerView());
	}

	
	
	
}
