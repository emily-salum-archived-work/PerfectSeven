package main;

import java.awt.EventQueue;
import java.io.IOException;
import javax.imageio.ImageIO;
import data_control.TableDataProvider;
import database_classes.DatabaseConnection;
import interface_classes.administrator.EmployeeView;
import interface_classes.employees.UserLogin;

public class Main{

	
	
	public static Interface main_interface;
	public static DatabaseConnection connection;
	public static TableDataProvider current_user;
	public static int mode = 0;

	public static void main(String[] args) 
	{
		
		connection = new DatabaseConnection();
		EventQueue.invokeLater(new Runnable() 
		{
			
			public void run() 
			{
				try 
				{
					main_interface = new Interface();

					open_adm_view();
					main_interface.setVisible(true);
					
					try 
					{
						main_interface.setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					} 
					
					main_interface.setTitle("Perfect Seven");
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
	
	public static void open_worker_view()
	{
		UserLogin login = new UserLogin();
		Interface.change_display(login);
	}
	
	public static void open_adm_view()
	{
		EmployeeView vdf = new EmployeeView();
		Interface.change_display(vdf);
	}


}
