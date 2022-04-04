package interface_classes.table_classes;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;
//https://stackoverflow.com/questions/13833688/adding-jbutton-to-jtable
public class JTableButtonMouseListener extends MouseAdapter 
{
    private final JTable table;

    public JTableButtonMouseListener(JTable table)
    {
        this.table = table;
    }

    public void mouseClicked(MouseEvent e) 
    {
        int column = table.columnAtPoint(e.getPoint());
        int row = table.rowAtPoint(e.getPoint());
      
        if (row < table.getRowCount() && row >= 0  && column < table.getColumnCount() && column >= 0)  
        {
            Object value = table.getValueAt(row, column);
            
            if (value instanceof JButton) 
            {
                ((JButton)value).doClick();
            }
        }
    }
}