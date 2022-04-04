package main;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interface_classes.ControlledPanel;
import interface_classes.DefaultInterface;

import java.awt.Dimension;
import java.awt.Color;
@SuppressWarnings("serial")
public class Interface extends JFrame implements KeyListener {

	private JPanel contentPane;
	public static int WIDTH;
	public static int HEIGHT;
	private static JPanel panel;
	
	
	public Interface() 
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
	
      	WIDTH = screenSize.width;
      	HEIGHT = screenSize.height;
      	
      	setExtendedState(JFrame.MAXIMIZED_BOTH);
      	
	    setBounds(0,0, WIDTH, HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		panel.setMinimumSize(new Dimension(15, 15));
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel);
		panel.setLayout(null);
		
		DefaultInterface.set_font_size(WIDTH * 2/100);
		
		addKeyListener(this);
	}
	
	public static void change_display(ControlledPanel new_display)
	{
		panel.removeAll();
		panel.repaint();
		new_display.inicialize(panel, WIDTH, HEIGHT);
		
	}

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
		
	}

}


