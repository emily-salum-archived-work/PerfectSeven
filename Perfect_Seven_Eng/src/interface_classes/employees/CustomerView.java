package interface_classes.employees;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.bson.types.ObjectId;
import data_control.DataProvider;
import data_control.TableDataProvider;
import database_classes.DatabaseConnection;
import interface_classes.Action;
import interface_classes.ConfirmationScreen;
import interface_classes.ControlledPanel;
import interface_classes.DefaultInterface;
import interface_classes.table_classes.DataTableModel;
import interface_classes.table_classes.JTableButtonMouseListener;
import main.Interface;
import main.Main;

@SuppressWarnings("serial")
public class CustomerView extends ControlledPanel{

	DataTableModel clients_table;
	JTable table;
	ControlledPanel self;
	@Override
	public void inicialize(JPanel controller, int WIDTH, int HEIGHT) 
	{
		self = this;
		super.inicialize(controller, WIDTH, HEIGHT);
		
		table = new JTable();
		update_clients("in wait");		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(WIDTH * 15/100, HEIGHT*20/100, WIDTH*65/100, HEIGHT*50/100);
		add(scrollPane);
		
		
		JButton em_espera = change_mode_button("in wait");
		em_espera.setBounds(rectangle_from_total_rect(new Rectangle(16,12,14,8)));
		add(em_espera);
		
		
		JButton atendeu = change_mode_button("in process");
		atendeu.setBounds(rectangle_from_total_rect(new Rectangle(30,12,14,8)));
		add(atendeu);
		
		JButton não_atendeu = change_mode_button("not answered");
		não_atendeu.setBounds(rectangle_from_total_rect(new Rectangle(44,12,14,8)));
		add(não_atendeu);
		
		JButton vendido = change_mode_button("sold");
		vendido.setBounds(rectangle_from_total_rect(new Rectangle(58,12,14,8)));
		add(vendido);
		 
		@SuppressWarnings("unused")
		RefimInterface refim_panel = new RefimInterface(this, 2,12,100,70);
		
	}
	
	public JButton change_mode_button(String mode)
	{
		JButton button_mode = DefaultInterface.make_default_button(mode, total_rect.width*2/100);
		button_mode.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				update_clients(mode);
			}
		});
		return button_mode;
	}
	
	ArrayList<TableDataProvider> clients;
	public void update_clients(String state)
	{
		clients = DatabaseConnection.client_b.getClients((ObjectId) Main.current_user.getData("id"),state);
		
		for(TableDataProvider c : clients)
		{
			
			if(state == "sold")
			{
				DataProvider sells = new DataProvider();
				sells.data = Main.connection.get_sale_from(c.data_document.getObjectId("_id")).getData("venda");
				sells.name = "venda";
				c.data.add(sells);
				
				DataProvider change_value = new DataProvider(c, "change value");
				JButton change_value_button = new JButton("change value") 
				{
					public void doClick()
					{
						changeClientSellings(c);                
					}
					
				};
				change_value.data = change_value_button;
				change_value.name = "change value";
				c.data.add(change_value);
				
			}
			DataProvider d = new DataProvider(c, "change state");
			JButton change_state = new JButton("change state") 
			{
				public void doClick()
				{
					openChangeOfState(c,state);                
				}
				
			};

			d.data = change_state;
			c.data.add(d);
			c.remove_data("id");
			c.remove_data("Estado");
			
		
			
		}
		

		clients_table = new DataTableModel(clients);
		
		table.setModel(clients_table);
		clients_table.inicialize(table);
	    table.addMouseListener(new JTableButtonMouseListener(table));
		
	}
	
	

	public void openChangeOfState(TableDataProvider c, String state)
	{
		self.removeAll();
		
		JTextField t = DefaultInterface.make_default_area_text("Change Client State to:");
		add(t);
		t.setBounds(rectangle_from_total_rect(new Rectangle(20, 20, 60, 15)));
		
		
		String[] modes = new String[]{"in wait","in process","not answered"};
		int bx = 10;
		int by = 40;
		ObjectId id = c.data_document.getObjectId("_id");
		for(String mode : modes)
		{
			JButton j = DefaultInterface.make_default_button(mode, Interface.WIDTH*2/100);

			j.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e)
				{
					DatabaseConnection.client_b.change_client_state(id, mode);
					Interface.change_display(new CustomerView());
				}
				
			});

			
			add(j);
			j.setBounds(rectangle_from_total_rect(new Rectangle(bx, by, 15, 8)));
			bx += 16;
		}
		


				JButton j = DefaultInterface.make_default_button("sold", Interface.WIDTH*2/100);
				add(j);
				j.setBounds(rectangle_from_total_rect(new Rectangle(bx, by, 15, 8)));
				bx += 16;
				j.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e)
					 {
							
							changeClientSellings(c);
							
					 }


				});
				
		JButton cancel = DefaultInterface.make_default_button("cancel", Interface.WIDTH*2/100);
		cancel.setForeground(Color.red);
		add(cancel);
		cancel.setBounds(rectangle_from_total_rect(new Rectangle(bx, by, 15, 8)));
		
		cancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				Interface.change_display(new CustomerView());
			}
			
		});
		
		self.repaint();
		
		
	}

	
	private void changeClientSellings(TableDataProvider c) 
	{
		ObjectId id = c.data_document.getObjectId("_id");
		
		ConfirmationScreen c_venda = new ConfirmationScreen(self,"Type sell cost:");
		
		JTextField valor_input = DefaultInterface.make_edittable_text(); 
		self.add(valor_input);
		valor_input.setBounds(self.rectangle_from_total_rect(new Rectangle(40,30,20,10)));
		
		Action change_interface = reopenView();
		
		Action add_records = new Action() 
		{
			public void act()
			{
				if(valor_input.getText().isEmpty())
				{
					return;
				}
				DatabaseConnection.client_b.change_client_state((ObjectId) id, "sold");
				Main.connection.save_selling(c, valor_input.getText());
				
			}
		};
		
		c_venda.add_actions(true, add_records,change_interface);
		c_venda.add_actions(false, change_interface);
	}
	
	
	
	
	
	
	
}
