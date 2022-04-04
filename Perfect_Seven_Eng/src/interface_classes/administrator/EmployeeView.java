package interface_classes.administrator;

import static interface_classes.DefaultInterface.*;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class EmployeeView extends ControlledPanel{

	DataTableModel workers_table;
	JTable table;
	JButton send_to_database = null;
	int b;
	Object obj;
	
	@Override
	public void inicialize(JPanel controller, int WIDTH, int HEIGHT) 
	{
		super.inicialize(controller, WIDTH, HEIGHT);
		
		table = new JTable();
		update_workers(DatabaseConnection.worker_b.getWorkers());		

		JScrollPane scrollPane = new JScrollPane(table);		
		scrollPane.setBounds(WIDTH * 15/100, HEIGHT*35/100, WIDTH*70/100, HEIGHT*30/100);
		add(scrollPane);
		
		JButton read_excel = DefaultInterface.make_default_button("Read excel file" ,WIDTH*2/100);
		read_excel.setBounds(WIDTH * 30/100, HEIGHT*30/100, WIDTH*20/100, HEIGHT*5/100);
		add(read_excel);
		read_excel.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				read_excel();
			}
		});
		
		JButton realoc_workers = DefaultInterface.make_default_button("Realoc clients" ,WIDTH*2/100);
		realoc_workers.setBounds(WIDTH * 55/100, HEIGHT*30/100, WIDTH*20/100, HEIGHT*5/100);
		add(realoc_workers);
		realoc_workers.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				DatabaseConnection.client_b.findOwnerTo(DatabaseConnection.client_b.getClients());
				self.reopenView().act();
			}
		});
		
		AddEmployee add_worker_interface = new AddEmployee();
		add_worker_interface.controlled_inicialize(self, 2, 35,40,40);
		
		@SuppressWarnings("unused")
		RefimControl rc = new RefimControl(self,30,0,40,15);
	}
	
	public void read_excel()
	{	
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());

		jfc.addChoosableFileFilter(new FileFilter() 
		{
		    public String getDescription() 
		    {
		        return "Pasta de Trabalho do Excel (*.xlsx)";
		    }
		 
		    public boolean accept(File f) 
		    {
		        if(f.isDirectory()) 
		        {
		            return true;
		        } 
		        else 
		        {	
		            return f.getName().toLowerCase().endsWith(".xlsx");
		        }
		    }
		});
		
	    jfc.showDialog(null,"Select the file");
	    jfc.setVisible(true);
	    File excel = jfc.getSelectedFile();
	    
	    try 
	    {	
	    	FileInputStream fis = new FileInputStream(excel);
			
	    	XSSFWorkbook wb = null;
			try 
			{
				wb = new XSSFWorkbook(fis);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}   
	    	XSSFSheet sheet = wb.getSheetAt(0);       
	    	Iterator<Row> itr = sheet.iterator();     
	    	int i = 0; 
	    	Row row = itr.next();  
	    	String[] names = new String[row.getHeight()];
	    	itr = sheet.iterator();  
	    	ArrayList<TableDataProvider> clients = new ArrayList<TableDataProvider>();

	    	while (itr.hasNext())                 
	    	{  
		    	row = itr.next();  
		    	Iterator<Cell> cellIterator = row.cellIterator();  
		    	
		    	
		    	for(b = 0; cellIterator.hasNext(); b++)
		    	{
		    		Cell cell = cellIterator.next();  
		    		obj = null;
		    		switch(cell.getCellTypeEnum())
		    		{
						case BLANK:
							b -= 1;
							break;
						case NUMERIC:
							obj = cell.getNumericCellValue();
							
							if((Double)obj - ((Double) obj).intValue() == 0)
							{
								obj = ((Double) obj).intValue();
							}    
							
							obj = obj.toString();
							
							break;
						case STRING:
							obj = cell.getStringCellValue();
							break;
						default:
							break;
		    		}
		    		if(obj != null && i != 0 && b == 0)
			    	{
			    		TableDataProvider client = new TableDataProvider(null);
			    		clients.add(client);
			    	}
		    		if(obj == null)
		    		{
		    			break;
		    		}
			    	if(i == 0)
			    	{
			    		names[b] = cell.getStringCellValue();
			    	}
			    	else
			    	{
			    		DataProvider dp = new DataProvider() 
			    		{
	
							@Override
							public Object getData() 
							{
								return data;
							}
	
							@Override
							public String getNameOfData() 
							{
								return name;
							}
	
							@Override
							public Class<?> getClassFromData() 
							{
								return data.getClass();
							}
						};
						
			    		dp.data = obj;
			    		dp.name = names[b];
			    		clients.get(i-1).data.add(dp);
			    	}
			    	
		    	}
		    	i++;
	    		
	    	}
	    	
	  
	    	workers_table = new DataTableModel(clients);
			table.setModel(workers_table);
	    	workers_table.inicialize(table);
	    	if(send_to_database == null)
	    	{
	    		send_to_database = DefaultInterface.make_default_button("send clients to employees", Interface.WIDTH*2/100);
	    		send_to_database.setBounds(get_relative_rectangle(total_rect,new Rectangle(25,75,40,10)));
	    		send_to_database.addMouseListener(new MouseAdapter() 
	    		{
	    			@Override
	    			public void mouseClicked(MouseEvent e)
	    			{
	    				DatabaseConnection.client_b.add_clients(clients);
	    				update_workers(DatabaseConnection.worker_b.latest_workers_retrieve);
	    				self.remove(send_to_database);
	    				repaint();
	    			}
	    		});
	    		add(send_to_database);
	    		repaint();
	    	}
	    		
			
		} 
	    catch (FileNotFoundException e) 
	    {
			e.printStackTrace();
		} 
	    
	}
	
	
	public void update_workers(ArrayList<TableDataProvider> workers)
	{
		 
		for(TableDataProvider w : workers)
		{
			w.remove_data("id");
			w.remove_data("password");
			
			DataProvider remainings = new DataProvider(w, "remaining clients");
			remainings.data = Main.connection.client_b.getRemainingClients(w.data_document.getObjectId("_id")).size();
			w.data.add(remainings);
			
			DataProvider sales_in_the_day = new DataProvider(w,"sells of the day");
			
			sales_in_the_day.data = Main.connection.get_sale_quantity(Main.connection.get_sales_in_the_day((ObjectId)w.data_document.getObjectId("_id")));
			System.out.print(sales_in_the_day.getData());
			w.data.add(sales_in_the_day);
			
			
			
			DataProvider sales_in_the_month = new DataProvider(w, "sells of the month");
			sales_in_the_month.data = Main.connection.get_sale_quantity(Main.connection.get_sales_in_the_month((ObjectId)w.data_document.getObjectId("_id")));
			w.data.add(sales_in_the_month);
			
			DataProvider sales = new DataProvider(w, "total sells");
			sales.data = Main.connection.get_sale_quantity(Main.connection.get_total_sales((ObjectId)w.data_document.getObjectId("_id")));
			w.data.add(sales);
			w.data.add(new DataProvider() 
			{

				@Override
				public Object getData() 
				{
					JButton j = new  JButton("remove employee") 
					{		 
						 public void doClick()
						 {
							 confirm_action("remove employee", w);
						 }
					};
					return j;
				}

				@Override
				public String getNameOfData() 
				{
					return "Remove";
				}

				@Override
				public Class<?> getClassFromData() 
				{
					return JButton.class;
				}
			});
			
			w.data.add(new DataProvider() 
			{
				@Override
				public Object getData() 
				{
					JButton j = new  JButton("reset account") 
					{
						 public void doClick()
						 {
							 confirm_action("reset account", w);
						 }
					};
					return j;
				}

				@Override
				public String getNameOfData()
				{
					return "Reset";
				}

				@Override
				public Class<?> getClassFromData() 
				{
					return JButton.class;
				}
				
			});
			
		
			
		}
	
		workers_table = new DataTableModel(workers);
		table.setModel(workers_table);
	    table.addMouseListener(new JTableButtonMouseListener(table));
	    workers_table.inicialize(table);
	   
	}



	public void confirm_action(String action, TableDataProvider w)
	{
		removeAll();
		repaint();
		
		ConfirmationScreen c = new ConfirmationScreen(this,"Are you sure you want to " + action + "?");
		
		
		
	
		
		 
		Action reopen_view = reopenView();
		
		Action command = new Action() 
		{
			@Override
			public void act()
			{
				switch(action)
				{
				case "reset account":
						DatabaseConnection.worker_b.reset_worker(w.data_document.getObjectId("_id"));
					break;
					
				case "remove employee":
						DatabaseConnection.worker_b.remove_worker((String)w.getData("name"));
						
					break;
					
				
				}
			}
		};
		
		
		c.add_actions(true,command, reopen_view);
		c.add_actions(false, reopen_view);
		
		
		
		
		
		
	}
	
	
	
}
