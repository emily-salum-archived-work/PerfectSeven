package interface_classes;

import java.awt.Rectangle;

import javax.swing.JPanel;

import main.Interface;

/**
 *  defines methods for the standard of all interfaces in the application
 *   */
public class ControlledPanel extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Rectangle total_rect;
	protected ControlledPanel self;
	
	public void inicialize(JPanel controller, int WIDTH, int HEIGHT)
	{
		self = this;
		this.setBounds(controller.getBounds());
		controller.add(this);
		this.setLayout(null);
		total_rect = new Rectangle(WIDTH, HEIGHT,WIDTH,HEIGHT);
	}
	
	
	public void controlled_inicialize(ControlledPanel controller, int X, int Y, int WIDTH, int HEIGHT)
	{
		self = this;
		this.setBounds(controller.getBounds());
		//controller.add(this);
		//this.setLayout(null);
		b = new Rectangle(X*controller.total_rect.x/100,Y*controller.total_rect.y/100,0,0);
		total_rect = new Rectangle(WIDTH*controller.total_rect.width/100,HEIGHT*controller.total_rect.height/100,WIDTH*controller.total_rect.width/100,HEIGHT*controller.total_rect.height/100);
	}

	Rectangle b = null;
	public Rectangle rectangle_from_total_rect(Rectangle t)
	{
		Rectangle r = DefaultInterface.get_relative_rectangle(total_rect, t);
		
		if(b != null)
		{
			r.x += b.x;
			r.y += b.y;
			
		}
		return r;
	}
	
	
	public Action reopenView()
	{
		
		Class<?> panel = this.getClass();
		Action reopen_view = new Action() {

			@Override
			public void act() {
				try {
					Interface.change_display((ControlledPanel) panel.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		
		return reopen_view;
	}
	
	
}
