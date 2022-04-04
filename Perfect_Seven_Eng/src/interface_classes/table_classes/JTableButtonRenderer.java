package interface_classes.table_classes;
// https://stackoverflow.com/questions/13833688/adding-jbutton-to-jtable
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JTableButtonRenderer implements TableCellRenderer 
{
	   private TableCellRenderer defaultRenderer;
	   public JTableButtonRenderer(TableCellRenderer renderer) 
	   {
	      defaultRenderer = renderer;
	   }
	   
	   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	   {
	      if(value instanceof Component)
	      {   
	    	  return (Component)value;
	      }
	      return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	   }
}