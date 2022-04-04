package database_classes;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import data_control.DataProvider;
import data_control.TableDataProvider;


import static database_classes.DatabaseConnection.*;

public class WorkerBehaviour {

	
	public ArrayList<TableDataProvider> latest_workers_retrieve;
	public ArrayList<TableDataProvider> getWorkers()
	{
		MongoCollection<Document> workers = database.getCollection("Funcionario");
		FindIterable<Document> iterable = workers.find();
		MongoCursor<Document> cursor = iterable.iterator();
		ArrayList<TableDataProvider> list_of_workers = new ArrayList<TableDataProvider>();
		
		try 
		{
			while(cursor.hasNext()) 
			{
				Document d = cursor.next();
		  		TableDataProvider w = worker_details(d);
		  		list_of_workers.add(w);
			}
		} 
		finally 
		{
		  cursor.close();
		}
		latest_workers_retrieve = list_of_workers;
		return list_of_workers;
	}
	
	public void save_password(ObjectId id, String password) 
	{
		MongoCollection<Document> workers = database.getCollection("Funcionario");
		workers.updateOne(Filters.eq("_id", id), Updates.set("password",  EncryptionConversion.encrypt_text(password)));
	}
	

	public void reset_worker(ObjectId id) 
	{
		MongoCollection<Document> workers = database.getCollection("Funcionario");
		workers.updateOne(Filters.eq("_id", id), Updates.set("password",  null));
	}
	
	public TableDataProvider worker_details(Document d)
	{
		TableDataProvider w = new TableDataProvider(d);
		ArrayList<DataProvider> data = new ArrayList<DataProvider>();
		data.add(new DataProvider(w, "name", true));
		
		data.add(new DataProvider()
		{
			@Override
			public Object getData()
			{	
				return w.data_document.getObjectId("_id");
			}
			
			@Override
			public String getNameOfData()
			{
				return "id";
			}

			@Override
			public Class<?> getClassFromData() 
			{
				return ObjectId.class;
			}
		});

		data.add(new DataProvider(w, "password", true));	
		
		w.data = data;
		return w;
	}
	
	public TableDataProvider getWorker(String name)
	{
		MongoCollection<Document> workers = database.getCollection("Funcionario");
		
		String name_account = EncryptionConversion.encrypt_text(name.trim());
		System.out.print(name_account);
		 FindIterable<Document> iterable = workers.find(Filters.eq("name", name_account)); // (1)
	
		
		  MongoCursor<Document> cursor = iterable.iterator(); // (2)
		  try {
		  while(cursor.hasNext()) {
			  Document d = cursor.next();
		  TableDataProvider w = worker_details(d);
		
		  cursor.close();
		  return w;
		  }
		  } finally {
		  cursor.close();
		  }
		  return null;		  
	}
	
	

	public TableDataProvider getWorker(ObjectId id)
	{

		MongoCollection<Document> workers = database.getCollection("Funcionario");
		
		 FindIterable<Document> iterable = workers.find(Filters.eq("_id", id));
		 
		  MongoCursor<Document> cursor = iterable.iterator(); 
		  try {
		  while(cursor.hasNext()) {
			  Document d = cursor.next();
		  TableDataProvider w = worker_details(d);
		
		  cursor.close();
		  return w;
		  }
		  } finally {
		  cursor.close();
		  }
		  return null;		  
	}
	
	public void add_worker(String worker_name)
	{
		MongoCollection<Document> workers = database.getCollection("Funcionario");
		Document bO = new Document();
		bO.append("name", EncryptionConversion.encrypt_text(worker_name.trim()));
		workers.insertOne(bO);
	}



	public void remove_worker(String worker_name) 
	{
		MongoCollection<Document> workers = database.getCollection("Funcionario");
		Document bO = new Document();
		bO.append("name", EncryptionConversion.encrypt_text(worker_name));
		ArrayList<TableDataProvider> c = DatabaseConnection.client_b.getClients(workers.find(bO).first().getObjectId("_id"));
		workers.findOneAndDelete(bO);
		
		DatabaseConnection.client_b.findOwnerTo(c);
	}
}
