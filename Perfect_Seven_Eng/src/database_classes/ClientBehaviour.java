package database_classes;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import data_control.DataProvider;
import data_control.TableDataProvider;
import static database_classes.DatabaseConnection.*;

public class ClientBehaviour {
	public void add_clients(ArrayList<TableDataProvider> clients) 
	{	
		MongoCollection<Document> client_collection = database.getCollection("Cliente");	
		ArrayList<TableDataProvider> workers = worker_b.getWorkers();
		
		int worker = 0;
		for(int i = 0; i < clients.size(); i++)
		{
			Document bO = new Document();
			TableDataProvider c = clients.get(i);
			
			bO.append("CPF", c.getEncryptedData("CPF"));
			bO.append("Telefone", c.getEncryptedData("Telefone"));
			bO.append("Estado", EncryptionConversion.encrypt_text("in wait"));
			bO.append("Cidade", c.getEncryptedData("Cidade"));
			if(workers.size() != 0)
			{
				bO.append("funcionario_id", workers.get(worker).getData("id"));
			}
			
			client_collection.insertOne(bO);
			
			worker += 1;
			if(worker >= workers.size())
			{
				worker = 0;
			}
			
		}
	}
	
	public void findOwnerTo(ArrayList<TableDataProvider> clients)
	{
		MongoCollection<Document> client_collection = database.getCollection("Cliente");
		ArrayList<TableDataProvider> workers = worker_b.getWorkers();
		
		int worker = 0;
		for(int i = 0; i < clients.size(); i++)
		{
			if(clients.get(i).getData("Estado") == "sold")
			{
				removeClient(clients.get(i));			
				continue;
			}
			client_collection.updateOne(Filters.eq("_id", clients.get(i).data_document.getObjectId("_id")), Updates.set(
					"funcionario_id", workers.get(worker).getData("id")));
			worker += 1;
			if(worker >= workers.size())
			{
				worker = 0;
			}
		}
	}
	
	public void removeClients(ArrayList<TableDataProvider> clients)
	{
		for(TableDataProvider c : clients)
		{
			removeClient(c);
		}
	}
	
	public void removeClient(TableDataProvider c)
	{
		MongoCollection<Document> client_collection = database.getCollection("Cliente");
		DatabaseConnection.remove_selling(c.data_document.getObjectId("_id"), false);
		client_collection.deleteOne(Filters.eq("_id", c.data_document.getObjectId("_id")));
		
	}
	
	public ArrayList<TableDataProvider> getClients(ObjectId worker_id)
	{
		MongoCollection<Document> clients = database.getCollection("Cliente");
		Document bO = new Document();
		bO.append("funcionario_id", worker_id);
		 
		return  get_clients_from_iterator(clients.find(bO));
		
	}
	
	private ArrayList<TableDataProvider> get_clients_from_iterator(FindIterable<Document> it)
	{
		
		
		  MongoCursor<Document> cursor = it.iterator();
		  ArrayList<TableDataProvider> list_of_clients = new ArrayList<TableDataProvider>();
		  try {
		  while(cursor.hasNext()) {
			    Document d = cursor.next();
		  		TableDataProvider w = client_details(d);
		  		list_of_clients.add(w);
		  }
		  } finally {
		  cursor.close();
		  }
		
		
		return list_of_clients;
	}
	
	public ArrayList<TableDataProvider> getClients()
	{
		MongoCollection<Document> clients = database.getCollection("Cliente");
		
		
		 FindIterable<Document> iterable = clients.find();
		  MongoCursor<Document> cursor = iterable.iterator();
		  ArrayList<TableDataProvider> list_of_clients = new ArrayList<TableDataProvider>();
		  try {
		  while(cursor.hasNext()) {
			    Document d = cursor.next();
		  		TableDataProvider w = client_details(d);
		  		list_of_clients.add(w);
		  }
		  } finally {
		  cursor.close();
		  }
		
		return list_of_clients;
		
	}
	
	public TableDataProvider getClient(ObjectId id)
	{
		MongoCollection<Document> clients = database.getCollection("Cliente");
		
		Document bO = new Document();
		bO.append("_id", id);
		 FindIterable<Document> iterable = clients.find(bO);
		  MongoCursor<Document> cursor = iterable.iterator();
		  try {
		  while(cursor.hasNext()) {
			    Document d = cursor.next();
		  		TableDataProvider w = client_details(d);
		  		return w;
		  }
		  } finally {
		  cursor.close();
		  }
		  
		  
		  return null;
		
		
	}
	

	public ArrayList<TableDataProvider> getClients(String state) {
		// TODO Auto-generated method stub
		
		 ArrayList<TableDataProvider> list_of_clients = getClients();
		 
		 for(int i = 0; i < list_of_clients.size(); i++)
		 {
			 TableDataProvider c = list_of_clients.get(i);
			 String c_state = (String) c.getData("Estado");
			 
			 if(c_state == null)
			 {
				 list_of_clients.remove(c);
				 i --;
				 continue;
			 }
			 if(state.compareTo(c_state) != 0)
			 {
				list_of_clients.remove(c);
				i--;
			 }
		 }
		
		return list_of_clients;
	}

	public ArrayList<TableDataProvider> getClients(ObjectId worker, String state) {
		// TODO Auto-generated method stub
		
		 ArrayList<TableDataProvider> list_of_clients = getClients(worker);
		 
		 for(int i = 0; i < list_of_clients.size(); i++)
		 {
			 TableDataProvider c = list_of_clients.get(i);
			 String c_state = (String) c.getData("Estado");
			 
			 if(c_state == null)
			 {
				 list_of_clients.remove(c);
				 i --;
				 continue;
			 }
			 if(state.compareTo(c_state) != 0)
			 {
				list_of_clients.remove(c);
				i--;
			 }
		 }
		
		return list_of_clients;
	}
	
	
	public TableDataProvider client_details(Document d)
	{
		
		TableDataProvider client = new TableDataProvider(d);
		
		
		client.data.add(new DataProvider() {

			@Override
			public Object getData() {
				// TODO Auto-generated method stub
				return client.data_document.getObjectId("_id");
			}

			@Override
			public String getNameOfData() {
				// TODO Auto-generated method stub
				return "id";
			}

			@Override
			public Class<?> getClassFromData() {
				// TODO Auto-generated method stub
				return ObjectId.class;
			}
			
		});
		
		
		client.data.add(new DataProvider(client, "Estado",true));
		client.data.add(new DataProvider(client, "CPF",true));
		client.data.add(new DataProvider(client, "Telefone",true));
		
		
		return client	;
	}

	public void change_client_state(ObjectId client_id, String new_state) 
	{
		MongoCollection<Document> clients = database.getCollection("Cliente");
		
		remove_selling(client_id,true);
		clients.updateOne(Filters.eq("_id", client_id), Updates.set("Estado",  
				EncryptionConversion.encrypt_text(new_state)));

	}

	public ArrayList<TableDataProvider> getRemainingClients(ObjectId workerId) {
		MongoCollection<Document> clients = database.getCollection("Cliente");
		
		Bson r = Filters.and(Filters.eq("funcionario_id", workerId), Filters.ne("Estado", EncryptionConversion.encrypt_text("sold")));
		
		return get_clients_from_iterator(clients.find(r));
	}

}











