package Server;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.DefaultListModel;

import SharedFolder.Client;

/**
 * this class is responsible for communicating with the client side and the database controller to tell the database controller which changes
 * need to be made
 *
 */
public class ServerController implements Runnable{
	
	private DatabaseController dbC;
	
	private Socket aSocket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	/**
	 * this is the constructor of the server controller
	 * @param socketm, this is the socket that the server controller is connected to 
	 * @param dbController, this is the database controller that will be assigned to the member variable
	 * @throws IOException
	 */
	public ServerController(Socket socket, DatabaseController dbController) throws IOException {
		
		dbC = dbController;
		this.aSocket = socket;
		socketOut = new PrintWriter((this.aSocket.getOutputStream()), true);
		socketIn = new BufferedReader(new InputStreamReader(this.aSocket.getInputStream()));
	}
	
	
	
	
	// this is the run method of the class and it will keep asking for inputs from the client controller which will tell the 
	//server controller what to do from there(ie: add, delete, modify clients, search for clients by type)
	@Override
	public void run() {
		while(true) {
			try {
				String in = socketIn.readLine();
				if (in.equals("ADD")) {
					input = new ObjectInputStream(new FileInputStream( "client.ser" ));
					Client temp = (Client) input.readObject();
					temp.setID(dbC.getClientCounter());
					dbC.addItem(temp);
					dbC.printTable();
				}
				else if (in.equals("DELETE")) {
					input = new ObjectInputStream(new FileInputStream( "client.ser" ));
					Client temp = (Client) input.readObject();
					dbC.deleteItem(temp.getID());
					
					socketOut.println("DELETE MADE");
				}
				else if (in.equals("MODIFY")) {
					input = new ObjectInputStream(new FileInputStream( "client.ser" ));
					Client temp = (Client) input.readObject();
					dbC.modifyClient(temp);
					
					socketOut.println("MODIFY MADE");
				}
				else if(in.equals("SEARCH BY ID")) {
					String id = socketIn.readLine();
					
					DefaultListModel<Client> clientlist = dbC.searchClientByID(Integer.parseInt(id));
					output = new ObjectOutputStream (new FileOutputStream( "client.ser" ));
					output.writeObject(clientlist);
					socketOut.println("ID OUTPUT");
				}
				else if (in.equals("SEARCH BY NAME")) {
					String name = socketIn.readLine();
					
					DefaultListModel<Client> clientlist = dbC.searchClientByLastName(name);
					output = new ObjectOutputStream (new FileOutputStream( "client.ser" ));
					output.writeObject(clientlist);
					socketOut.println("NAME OUTPUT");
				}
				else if (in.equals("SEARCH BY TYPE")) {
					String type = socketIn.readLine();

					DefaultListModel<Client> clientlist = dbC.searchClientByType(type);
					output = new ObjectOutputStream (new FileOutputStream( "client.ser" ));
					output.writeObject(clientlist);
					socketOut.println("TYPE OUTPUT");
				}
				
			} catch (ClassNotFoundException | ClassCastException | IOException e) {
				e.printStackTrace();
			} catch(NullPointerException e) {
				//if catches null pointer exception, then it means the client has exited and the server controller can close as well
				break;
			}
			
		}
		
	}
	
	

}
