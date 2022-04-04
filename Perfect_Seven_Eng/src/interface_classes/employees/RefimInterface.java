package interface_classes.employees;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import interface_classes.ControlledPanel;
import interface_classes.DefaultInterface;
import main.CalculoRefim;

@SuppressWarnings("serial")
public class RefimInterface extends ControlledPanel{

	JTextField input_prestacao;
	JTextField input_saldo;
	public static CalculoRefim calc = new CalculoRefim();

	public RefimInterface(ControlledPanel p, int X, int Y, int WIDTH, int HEIGHT)
	{
		super.controlled_inicialize(p,X,Y,WIDTH, HEIGHT);
		
		JTextField prestacao = DefaultInterface.make_default_area_text("prestação");
		prestacao.setBounds(rectangle_from_total_rect(new Rectangle(0,0, 10,10)));
		p.add(prestacao);
	
		
		input_prestacao = DefaultInterface.make_edittable_text();
		input_prestacao.setBounds(rectangle_from_total_rect(new Rectangle(0, 10, 10,10)));
		p.add(input_prestacao);

		
		JTextField saldo = DefaultInterface.make_default_area_text("saldo");
		saldo.setBounds(rectangle_from_total_rect(new Rectangle(0,20, 10,10)));
		p.add(saldo);
	
		
		input_saldo = DefaultInterface.make_edittable_text();
		input_saldo.setBounds(rectangle_from_total_rect(new Rectangle(0,30, 10,10)));
		p.add(input_saldo);
		
		JLabel resultado = new JLabel();
		
		
		JButton calcular = DefaultInterface.make_default_button("Confirm",WIDTH*14/100);
		calcular.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				criar_resultado(resultado);
			}
		});
	
	
		calcular.setBounds(rectangle_from_total_rect(new Rectangle(0,40, 10,10)));
		p.add(calcular);
	
		JButton apagar_conteudo = DefaultInterface.make_default_button("Erase Content",WIDTH*11/100);
		p.add(apagar_conteudo);
		apagar_conteudo.setBounds(rectangle_from_total_rect(new Rectangle(0,50, 10,10)));
		apagar_conteudo.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				input_saldo.setText("");
				input_prestacao.setText("");
			}
		});
		
		
		resultado.setHorizontalAlignment(SwingConstants.CENTER);
		resultado.setHorizontalTextPosition(SwingConstants.CENTER);
		resultado.setAlignmentX(Component.RIGHT_ALIGNMENT);
		resultado.setOpaque(true);
		
		resultado.setBackground(Color.WHITE);
		resultado.setBounds(rectangle_from_total_rect(new Rectangle(0,60, 10,10)));
		
		p.add(resultado);
	
	}
	
	private void criar_resultado(JLabel resultado) 
	{
		double prest =convert_input(input_prestacao.getText());
		double sald = convert_input(input_saldo.getText());
		double res = calc.get_valor_liberado(prest, sald);
		
		resultado.setText("R$"+mark_with_dots(Double.toString(res)));	
	}
	
	
	public static Double convert_input(String text)
	{
		return Double.parseDouble(text.replace(",", "."));
	}
	
	public static String remove_irrelevancies(String t)
	{
		if(t.indexOf(".") + 3 < t.length())
		{
		return t.substring(0, t.indexOf(".") + 3);
	
		}
	
	return t;
	}
	public String mark_with_dots(String t)
	{
		t = remove_irrelevancies(t);
		
		if(t.length() < 7)
		{
			return t;
		}
		
		int orientation = 0;
		String tr = "";
		
		orientation = t.indexOf(".")-1;
		int extra_dots = 0;
		
		for(int i = 0; i <= orientation; i++)	
		{
			tr = tr.concat(t.substring(orientation -i,orientation - i+1));
			if((i+1) % 3 == 0 && i + 1 <= orientation)
			{
				tr = tr.concat(".");
				extra_dots ++;
			}
		}
		
		String tres = "";
		
		for(int i = tr.length()-1; i >= 0; i--)
		{
			tres = tres.concat(tr.substring( i, i + 1));
		}
		
		tres = tres.concat(t.substring(orientation+1, t.length()));
		
		if(tres.length() - orientation - extra_dots == 3)
		{
			tres = tres.concat("0");
		}
		tres = tres.substring(0, orientation+ extra_dots+1).concat(",").concat(tres.substring(orientation+extra_dots + 2, tres.length()));
		return tres;
	}
}
