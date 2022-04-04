package interface_classes.table_classes;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import data_control.DataProvider;
import data_control.TableDataProvider;

@SuppressWarnings("serial")
public class DataTableModel extends AbstractTableModel              
{

	private ArrayList<TableDataProvider> workers;
	
	public DataTableModel(ArrayList<TableDataProvider> workers)
	{
		this.workers = workers;
		
	
	}
	
	public void inicialize(JTable table)
	{
		if(workers.size() == 0)
		{
			return;
		}
		for(DataProvider t : workers.get(0).data)
		{

			TableCellRenderer tableRenderer;
		    tableRenderer = table.getDefaultRenderer(t.getClassFromData());
			table.getColumn(t.getNameOfData()).setCellRenderer(new JTableButtonRenderer(tableRenderer));
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) 
	{
		if(workers.size() < columnIndex)
		{
			return null;
		}
		//return String.class;
		return workers.get(0).getClassFromData(columnIndex);
	}

	
	public void addWorker(TableDataProvider worker)
	{
		workers.add(worker);
	}
	
	@Override
	public int getColumnCount() 
	{
		if(workers.size() == 0)
		{
			return 0;
		}
		
		return workers.get(0).getAmmountOfValues();
	}

	@Override
	public String getColumnName(int columnIndex) 
	{
		if(workers.size() == 0)
		{
			return null;
		}
		
		return workers.get(0).getDataName(columnIndex);
	}

	@Override
	public int getRowCount() 
	{
		return workers.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		if(rowIndex > workers.size())
		{
			return null;
		}
		
		return workers.get(rowIndex).getData(columnIndex);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		workers.get(rowIndex).setData(columnIndex,aValue);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) 
	{
		return workers.get(rowIndex).isEditable(columnIndex);
	}

	
	

}
