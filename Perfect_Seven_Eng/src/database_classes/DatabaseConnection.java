package database_classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import data_control.DataProvider;
import data_control.TableDataProvider;
import interface_classes.employees.RefimInterface;
import main.Main;

public class DatabaseConnection {

	public static MongoDatabase database;
	public DatabaseConnection()
	{
		MongoClientURI uri = new MongoClientURI( "mongodb+srv://tester:tester_password@cluster0.8eiss.mongodb.net/proj-database?retryWrites=true&w=majority");
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient(uri);	
		database = client.getDatabase("proj-database");
	}
	
	
	public static WorkerBehaviour worker_b = new WorkerBehaviour();
	
	public static ClientBehaviour client_b = new ClientBehaviour();
	

	public double get_fator() 
	{	

		MongoCollection<Document> adms = database.getCollection("Administrador");
		
		
		//return Double.parseDouble((String) adms.find().first().get("fator"));	
		String fator = (String) adms.find().first().get("fator");
		
		if(fator.isEmpty())
		{
			return 0;
		}
		return Double.parseDouble(EncryptionConversion.convert_to_text(fator));	
	}

	public void set_fator(double new_fator)
	{
		MongoCollection<Document> adms = database.getCollection("Administrador");
		adms.updateOne(new Document(), Updates.set("fator",  EncryptionConversion.encrypt_text(Double.toString(new_fator))));
	}


	
	
	public static void remove_selling(ObjectId client_id, boolean lost)
	{
		MongoCollection<Document> sells = database.getCollection("Venda");
		Document bO = new Document();
		
		bO.append("cliente_id", client_id);
		Document doc = sells.find(bO).first();
		if(doc == null)
		{
			return;
		}
		
		sells.deleteOne(bO);
		
	}
	
	public TableDataProvider getAdministrator() 
	{
		
		MongoCollection<Document> adm_collection = database.getCollection("Administrador");	
		TableDataProvider adm = new TableDataProvider(adm_collection.find().first());
		adm.data.add(new DataProvider(adm, "fator",true));
		
		return adm;
	}

	

	public Double get_sale_quantity(ArrayList<TableDataProvider> sales)
	{
		Double res = 0.0;
		
		for(TableDataProvider t : sales)
		{
			String venda = (String) t.getData("venda");
			if(venda.isEmpty())
			{
				continue;
			}
			res += Double.parseDouble(venda);
		}
		res = Double.parseDouble(RefimInterface.remove_irrelevancies(res.toString()));
		return res;
	}

	public ArrayList<TableDataProvider> get_sales_in_the_day(ObjectId id) 
	{
		

		MongoCollection<Document> selling = database.getCollection("Venda");	
		
		Calendar dc = Calendar.getInstance();
		dc.set(Calendar.HOUR_OF_DAY, 0);
		dc.set(Calendar.MINUTE, 0);
		
		Date date_from = dc.getTime();
		
		FindIterable<Document> sells = selling.find(Filters.eq("funcionario_id",id));
		ArrayList<TableDataProvider> sales = new ArrayList<TableDataProvider>();
		for(Document sell: sells)
		{
			Date  d = sell.getDate("data");
			
			boolean time_within_today = d.after(date_from);
			if(time_within_today)
			{
				TableDataProvider sale = new TableDataProvider(sell);	
				sale.data.add(new DataProvider(sale, "venda",true));
				sales.add(sale);
			}
		}
		
		return sales;
	}



	
	
	
	public ArrayList<TableDataProvider> get_sales_in_the_month(ObjectId id) 
	{
		MongoCollection<Document> selling = database.getCollection("Venda");	
		
		Calendar dc = Calendar.getInstance();
		dc.set(Calendar.DAY_OF_MONTH, 1);
		Date date_from = dc.getTime();
		FindIterable<Document> sells = selling.find(Filters.eq("funcionario_id",id));
		ArrayList<TableDataProvider> sales = new ArrayList<TableDataProvider>();
		for(Document sell: sells)
		{
			Date  d = sell.getDate("data");
			boolean time_within_month = d.after(date_from);
			if(time_within_month)
			{
				TableDataProvider sale = new TableDataProvider(sell);	

				sale.data.add(new DataProvider(sale, "venda",true));
				sales.add(sale);
			}
		}
		
		return sales;
	}
	
	
	public ArrayList<TableDataProvider> get_total_sales(ObjectId id) 
	{
		MongoCollection<Document> selling = database.getCollection("Venda");	
		
		
		FindIterable<Document> sells = selling.find(Filters.eq("funcionario_id",id));
		ArrayList<TableDataProvider> sales = new ArrayList<TableDataProvider>();
		for(Document sell: sells)
		{
				TableDataProvider sale = new TableDataProvider(sell);


				sale.data.add(new DataProvider(sale, "venda",true));
				sales.add(sale);
		}
		return sales;
	}

	public void save_selling(TableDataProvider c, String sellings) 
	{
		MongoCollection<Document> selling = database.getCollection("Venda");	
		Document sells = new Document();
		
		sells.append("funcionario_id", Main.current_user.data_document.getObjectId("_id"));
		sells.append("cliente_id", c.data_document.getObjectId("_id"));
		sells.append("venda", EncryptionConversion.encrypt_text(RefimInterface.convert_input(sellings).toString()));
		sells.append("data", new Date());
		
		
		
		
		selling.insertOne(sells);
		
		
		
		
	}

	public TableDataProvider get_sale_from(ObjectId clientId) 
	{
		
		MongoCollection<Document> selling = database.getCollection("Venda");	
		
		
		FindIterable<Document> sells = selling.find(Filters.eq("cliente_id",clientId));
		
		return sales_details(sells.first());
	
	}

	
	public TableDataProvider sales_details(Document d)
	{
		if(d == null)
		{
			return null;	
		}
		
		TableDataProvider s = new TableDataProvider(d);
		
		s.data.add(new DataProvider(s, "venda", true));
		
		return s;
		
	}
		
		
	
	
	
}
