package data_control;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import database_classes.EncryptionConversion;

/* This class purpose (with data provider)
 * is to serve for whatever data may be needed and as such there are many different ways
 *  of getting data from it  */
public class TableDataProvider {

	public Document data_document;
	public List<DataProvider> data = new ArrayList<DataProvider>();
	
	
	public TableDataProvider(Document worker_document)
	{
		this.data_document = worker_document;
	}
	
	public Object getData(String value_name)
	{
	
		for(DataProvider p : data)
		{
			if(p.getNameOfData().compareTo(value_name) == 0)
			{
				return p.getData();
			}
		}
		return null;
		
	}
	
	
	
	public Object getData(int value_name)
	{
		
		if(data.size() <= value_name)
		{
			return null;
		}
		
		return data.get(value_name).getData();
	}

	public String getDataName(int d) 
	{
		if(data.size() <= d)
		{
			return null;
		}
		
		return data.get(d).getNameOfData();
	}



	public int getAmmountOfValues() 
	{
		return data.size();
	}

	public Class<?> getClassFromData(int i) 
	{
		if(data.size() <= i)
		{
			return null;
		}
		return data.get(i).getClassFromData();
	}




	public boolean isEditable(int columnIndex) 
	{
		return data.get(columnIndex).isEditable();
	}




	public void setData(int columnIndex, Object dt) 
	{
		data.get(columnIndex).data = dt;
	}




	public void remove_data(String value_name) 
	{
		for(DataProvider p : data)
		{
			if(p.getNameOfData().compareTo(value_name) == 0)
			{
				data.remove(p);
				return;
			}
		}
	}




	public Object getEncryptedData(String string) 
	{
		if(getData(string) == null)	
		{
			return null;
		}
		return EncryptionConversion.encrypt_text( getData(string).toString());
	}
	
	
	
	
}
