package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CalculoRefim {
	
	public double bruto = 0;
	public double liquido_total = 0;
	
	public double update_valor_bruto(double prestacao, double fator)
	{
		bruto = prestacao / fator;
		return bruto;
	}

	public double update_liquido_total(double saldo)
	{
		liquido_total = bruto - saldo;
		return liquido_total;
	}
	
	public double get_valor_liberado(double prestacao, double saldo, double fator)
	{
		update_valor_bruto(prestacao,fator);
		update_liquido_total(saldo);
		return liquido_total - liquido_total * 20/100;
	}
	
	public void update_fator(String convert_input) 
	{
		FileWriter writter = null;
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " ");
		
		File f = new File(new File(path).getParentFile().getPath() + File.separator+"res"+File.separator+ "fator.txt");
		
		
		try 
		{
			writter = new FileWriter(f);
			writter.write(convert_input);
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}
		finally
		{
            try 
            {
                writter.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
	}
	public double get_valor_liberado(double prest, double sald) 
	{
		
		return get_valor_liberado(prest, sald, Main.connection.get_fator());
	}
	
	
	
	
}
