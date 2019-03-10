package Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.DefaultListModel;

import SharedFolder.Client;

/**
 * this class is responsible for interacting with the SQL database and knows what to do with the deserialized client
 * through communicating with the server controller class
 * @author Leon
 *
 */
public class DatabaseController{

	private Connection jdbc_connection;
	private Statement statement;
	private String databaseName = "ClientDB", tableName = "ClientTable", dataFile = "clients.txt";
	private int clientCounter = 1;
	
	public String connectionInfo = "jdbc:mysql://localhost:3306/ClientDB",
			  login          = "root",
			  password       = "rootroot";
	
	/**
	 * this is the default constructor for the class and connects to a SQL database called ClientDB
	 */
	public DatabaseController () 
	{
		try{
			// If this throws an error, make sure you have added the mySQL connector JAR to the project
			Class.forName("com.mysql.jdbc.Driver");
			
			// If this fails make sure your connectionInfo and login/password are correct
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
			
//			createDB();
//			createTable();
//			fillTable();
			

		}
		catch(SQLException e) { e.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
	}

	
	/**
	 * this returns the current client counter
	 * @return int, this client counter represents the client ID that new clients will be added with
	 */
	public int getClientCounter() { return clientCounter;}
	
	/**
	 * this method creates a SQL database 
	 */
	public void createDB()
	{
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + databaseName);
			System.out.println("Created Database " + databaseName);
		} 
		catch( SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method creates a table for the SQL database and should only be used to create a table only after a database has been created
	 */
	public void createTable()
	{
		String sql = "CREATE TABLE " + tableName + "(" +
				     "id NUMERIC(4) NOT NULL, " +
				     "firstname VARCHAR(20) NOT NULL, " + 
				     "lastname VARCHAR(20) NOT NULL, " + 
				     "address VARCHAR(50) NOT NULL, " + 
				     "postalCod CHAR(7) NOT NULL, " +
				     "phoneNumber CHAR(12) NOT NULL, " +
				     "clientType CHAR(1) NOT NULL, " +
				     "PRIMARY KEY ( id ))";
				     
		try{
			PreparedStatement pstatement = jdbc_connection.prepareStatement(sql);
			pstatement.executeUpdate();
//			statement = jdbc_connection.createStatement();
//			statement.executeUpdate(sql);
			System.out.println("Created Table " + tableName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * this method removes the table from the database
	 */
	public void removeTable()
	{
		String sql = "DROP TABLE " + tableName;
		try{
			PreparedStatement pstatement = jdbc_connection.prepareStatement(sql);
			pstatement.executeUpdate();
			System.out.println("Removed Table " + tableName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * this method prints out all of the entries in the table
	 */
	public void printTable()
	{
		try {
			String sql = "SELECT * FROM " + tableName;

			statement = jdbc_connection.createStatement();
			ResultSet clients = statement.executeQuery(sql);
			System.out.println("Clients:");
			while(clients.next())
			{
				System.out.println(clients.getInt("id") + " " + 
								   clients.getString("firstname") + " " + 
								   clients.getString("lastname") + " " + 
								   clients.getString("address") + " " + 
								   clients.getString("postalCod") + " " +
								   clients.getString("phoneNumber") + " " +
								   clients.getString("clientType"));
			}
			clients.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method fills the created database table with entries from a pre-populated data file of old clients
	 */
	public void fillTable()
	{
		try{
			Scanner sc = new Scanner(new FileReader(dataFile));
			while(sc.hasNext())
			{
				String clientInfo[] = sc.nextLine().split(";");
				addItem( new Client( clientCounter, clientInfo[0], clientInfo[1], clientInfo[2], clientInfo[3], clientInfo[4], clientInfo[5]));
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File " + dataFile + " Not Found!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * this method modifies a previous client and saves that new information into the database, this method is synchronized
	 * because we dont want multiple users being able to modify the same client at once because then the proper information might not be stored
	 * @param client this parameter is the client that will be saved onto the database
	 */
	synchronized public void modifyClient(Client client) {
		String sql = "DELETE FROM " + tableName + " WHERE ID = ?";
		int id = client.getID();
		try {
		
			PreparedStatement pStat = jdbc_connection.prepareStatement(sql);
			pStat.setInt(1, id);
			pStat.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		sql = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?, ?, ?)"; 
		try{
			
			//"INSERT INTO MyTable (StrCol1, StrCol2) VALUES (?, ?)"
			PreparedStatement pstatement = jdbc_connection.prepareStatement(sql);
			pstatement.setInt(1,client.getID());
			pstatement.setString(2, client.getFirstName());
			pstatement.setString(3, client.getLastName());
			pstatement.setString(4, client.getAddress());
			pstatement.setString(5, client.getPostalCode());
			pstatement.setString(6, client.getPhoneNumber());
			pstatement.setString(7, String.valueOf(client.getClientType()));
			pstatement.executeUpdate();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * this method adds a new client to the database, the reason it is synchronized is because you dont want to be adding multiple new clients at the same time
	 * because the client counter will not be incremented properly
	 * @param client, this parameter is broken down into its values and is added to the appropriate columns in the database as a new entry
	 */
	synchronized public void addItem(Client client)
	{
		String sql = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?, ?, ?)"; 
		try{
			
			//"INSERT INTO MyTable (StrCol1, StrCol2) VALUES (?, ?)"
			PreparedStatement pstatement = jdbc_connection.prepareStatement(sql);
			pstatement.setInt(1,client.getID());
			pstatement.setString(2, client.getFirstName());
			pstatement.setString(3, client.getLastName());
			pstatement.setString(4, client.getAddress());
			pstatement.setString(5, client.getPostalCode());
			pstatement.setString(6, client.getPhoneNumber());
			pstatement.setString(7, String.valueOf(client.getClientType()));
			pstatement.executeUpdate();
			clientCounter++;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * this method takes the client id that is passed in and deletes the entry of the appropriate client in the database,
	 * this is synchronized because we dont want to be deleting multiple clients at the same time at the risk of deleting something that is not
	 * there which will create an error
	 * @param clientID, this parameter is used to find the correct client in the database to delete
	 */
	synchronized public void deleteItem(int clientID)
	{
		String sql = "DELETE FROM " + tableName + " WHERE ID = ?";
		ResultSet client;
		try {
		
			PreparedStatement pStat = jdbc_connection.prepareStatement(sql);
			pStat.setInt(1, clientID);
			pStat.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	
	
	/**
	 * this method searches for the client with the id that is specified and returns it as a list
	 * @param clientID, this parameter is the id that is used to search in the database
	 * @return DefaultListModel, a default list model is returned with the appropriate client, if no client was found then an empty list is returned
	 */
	public DefaultListModel<Client> searchClientByID(int clientID)
	{
		String sql = "SELECT * FROM " + tableName + " WHERE ID= ?";
		ResultSet client;
		try {
//			statement = jdbc_connection.createStatement();
//			tool = statement.executeQuery(sql);
			PreparedStatement pStat = jdbc_connection.prepareStatement(sql);
			pStat.setInt(1, clientID);
			client = pStat.executeQuery();
			if(client.next())
			{
				
				DefaultListModel<Client> model = new DefaultListModel<>();
				Client temp =  new Client(client.getInt("id"),
								client.getString("firstname"), 
								client.getString("lastname"), 
								client.getString("address"), 
								client.getString("postalCod"),
								client.getString("phoneNumber"),
								client.getString("clientType"));
				
				model.addElement(temp);
				return model;
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new DefaultListModel<Client>();
	}
	
	/**
	 * this method searches for clients with the same last name in the database
	 * @param lastName, this is the last name that is used for searching in the database
	 * @return Default List Model, the method returns a list of all the entries with the last name specified and returns an empty list 
	 * no entry was found
	 */
	public DefaultListModel<Client> searchClientByLastName(String lastName) {
		String sql = "SELECT * FROM " + tableName + " WHERE lastname= ?";
		ResultSet client;

		DefaultListModel<Client> clientList = new DefaultListModel<>();
		try {
//			statement = jdbc_connection.createStatement();
//			tool = statement.executeQuery(sql);
			PreparedStatement pStat = jdbc_connection.prepareStatement(sql);
			pStat.setString(1, lastName);
			client = pStat.executeQuery();
			while(client.next())
			{
				Client temp =  new Client(client.getInt("id"),
						client.getString("firstname"), 
						client.getString("lastname"), 
						client.getString("address"), 
						client.getString("postalCod"),
						client.getString("phoneNumber"),
						client.getString("clientType"));
		
				clientList.addElement(temp);
			}
			client.close();			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return clientList;
		
	}
	
	/**
	 * this method searches for clients with the same client type
	 * @param clientType, this is the client type that is used for searching
	 * @return Default List Model, this method returns a list of all the different clients that share the same client type and returns an empthy list
	 * if nothing is found
	 */
	public DefaultListModel<Client> searchClientByType(String clientType)
	{
		String sql = "SELECT * FROM " + tableName + " WHERE clienttype= ?";
		ResultSet client;
		
		DefaultListModel<Client> clientList = new DefaultListModel<>();
		try {
//			statement = jdbc_connection.createStatement();
//			tool = statement.executeQuery(sql);
			PreparedStatement pStat = jdbc_connection.prepareStatement(sql);
			pStat.setString(1, clientType);
			client = pStat.executeQuery();
			while(client.next())
			{
				Client temp =  new Client(client.getInt("id"),
						client.getString("firstname"), 
						client.getString("lastname"), 
						client.getString("address"), 
						client.getString("postalCod"),
						client.getString("phoneNumber"),
						client.getString("clientType"));
		
				clientList.addElement(temp);
			}
			client.close();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return clientList;
	}
	

}
