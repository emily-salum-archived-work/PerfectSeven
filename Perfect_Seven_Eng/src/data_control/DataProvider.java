package data_control;

import database_classes.EncryptionConversion;

public class DataProvider {
	

	public Object data;
	public String name;
	public boolean convert = false;
	public TableDataProvider owner;

	public DataProvider(TableDataProvider adm, String string, boolean convert) 
	{
		owner = adm;
		name = string;
		this.convert = convert;
		data = getData();
	}
	
	public DataProvider(TableDataProvider adm, String string) 
	{
		owner = adm;
		name = string;
	}

	public DataProvider()
	{
		
	}
	
	public Object getData()
	{
		if(data != null)
		{
			return data;
		}
		
		Object obj = owner.data_document.get(name);	
		
		if(obj == null)
		{
			return null;
		}
		
		if(convert)
		{
			obj = EncryptionConversion.convert_to_text(obj.toString());
		}
		
		return obj;
	}
	
	public String getNameOfData()
	{
		return name;
	}

	public Class<?> getClassFromData()
	{
		if(data != null)
		{
			return data.getClass();
		}
		
		if(convert)
		{
			return String.class;
		}
		return owner.data_document.get(name).getClass();
	}
	
	
	public boolean isEditable()
	{
		return false;
	}
	
}
