package interface_classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import main.Main;


/* I will use this class to make the interface have a standard model, just as you would with a css in html
 Also, copy pasting this stuff is stupid

*/
public class DefaultInterface {

	public static float font_size = 25;
public static InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("PoetsenOne-Regular.ttf");
    
    public static Font newFont;
	
	
	public static void set_font_size(int i) {
		// TODO Auto-generated method stub
		
		font_size = i;
		try {
  			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(font_size);
  		} catch (FontFormatException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
	}	
	public static JTextField make_default_text(String text, float font_size)
	{
		JTextField t = new JTextField();
		t.setForeground(Color.BLACK);
		t.setEditable(false);
		t.setColumns(10);
		t.setCaretColor(Color.WHITE);
		t.setBorder(null);
		t.setBackground(Color.LIGHT_GRAY);
		t.setFocusable(false);
		t.setText(text);
		t.setColumns(10);
		t.setHorizontalAlignment(0);
		t.setFont(newFont.deriveFont(font_size));
		return t;
	}
	
	public static JTextField make_edittable_text()
	{
		JTextField t = new JTextField();
		t.setForeground(Color.BLACK);
		t.setEditable(true);
		t.setColumns(10);
		t.setColumns(10);
		t.setHorizontalAlignment(0);
		t.setFont(newFont);
		
		
		t.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar() == ']')
				{
					if(Main.mode == 0)
					{
						Main.open_worker_view();
						Main.mode = 1;
						return;
					}
					Main.open_adm_view();
					Main.mode = 0;
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		return t;
	}

	
	public static JPasswordField make_password_text()
	{
		JPasswordField t = new JPasswordField();
		t.setEchoChar('*');
		t.setForeground(Color.BLACK);
		t.setEditable(true);
		t.setColumns(10);
		t.setColumns(10);
		t.setHorizontalAlignment(0);
		t.setFont(newFont);
		
		
		t.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar() == ']')
				{
					if(Main.mode == 0)
					{
						Main.open_worker_view();
						Main.mode = 1;
						return;
					}
					Main.open_adm_view();
					Main.mode = 0;
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		return t;
	}

	public static Rectangle get_relative_rectangle(Rectangle totalSize, Rectangle objectSize)
	{
		return new Rectangle(totalSize.x * objectSize.x/100, 
				totalSize.y * objectSize.y/100,
				totalSize.width * objectSize.width/100,
				totalSize.height * objectSize.height/100);
	}
	
	public static JButton make_default_button(String text, float font_size)
	{
		
		
		JButton b = new JButton(text);
		b.setBorder(new BevelBorder(0));
		b.setFocusable(false);
		b.setFont(newFont.deriveFont(font_size));
		
		return b;
	}
	
	public static JTextField make_default_area_text(String text)
	{
		JTextField t = new JTextField();
		t.setForeground(Color.BLACK);
		t.setEditable(false);
		t.setColumns(10);
		t.setCaretColor(Color.WHITE);
		t.setBorder(new LineBorder(Color.black));
		t.setBackground(Color.LIGHT_GRAY);
		t.setFocusable(false);
		t.setText(text);
		t.setColumns(10);
		t.setHorizontalAlignment(0);
		t.setFont(newFont);
		return t;
	}

	public static JTextField make_default_text(String text)
	{
		JTextField t = new JTextField();
		t.setForeground(Color.BLACK);
		t.setEditable(false);
		
		t.setFocusable(false);
		t.setText(text);
		t.setBorder (BorderFactory. createLineBorder (new Color (0, 0, 0, 0), 2));
		t.setHorizontalAlignment(0);
		t.setFont(newFont);
		return t;
	}
	
	
}
