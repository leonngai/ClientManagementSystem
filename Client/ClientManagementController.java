package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import SharedFolder.Client;

/**
 * this class is the controller for the client package and communicates with the server controller
 * @author Leon
 *
 */

public class ClientManagementController {
	
	private ClientManagementView view;
	private ClientManagementAddView addView;
	
	private Socket aSocket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	/**
	 * this is the constructor for the class and it connects to a new socket while assigning the member variables of the client view to the controller
	 * @param view
	 * @param addView
	 */
	public ClientManagementController(ClientManagementView view, ClientManagementAddView addView) {
		// dont need model as model will be changed to DB controller
		
		try {
			aSocket = new Socket("localhost", 9898);
			socketOut = new PrintWriter((this.aSocket.getOutputStream()), true);
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.view = view;
		this.addView = addView;
		
		this.view.registerClearSearchListener(new ClearSearchListener());
		this.view.registerClientIDListener(new ClientIDListener());
		this.view.registerClientTypeListener(new ClientTypeListener());
		this.view.registerLastNameListener(new LastNameListener());
		this.view.registerSearchListener(new SearchListener());
		this.view.registerListListener(new ClientListListener());
		this.view.registerDeleteListener(new DeleteListener());
		this.view.registerSaveListener(new SaveListener());
		this.view.registerAddListener(new AddListener());
		
		this.addView.registerMainMenuListener(new MainMenuListener());
		this.addView.registerAddListener(new ClientMenuAddListener());
		
	}
	
	/**
	 * this action listener is responsible for sending a serialized client over to the server controller to be added to the database
	 *
	 */
	class ClientMenuAddListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
		
			try {
				if (clientMenuCheckForErrors()) {
					Client temp = new Client(addView.getFirstName(), addView.getLastName(), addView.getAddress(), addView.getPostalCode(), addView.getNumber(), addView.getClientType());
					output = new ObjectOutputStream (new FileOutputStream( "client.ser" ));
					output.writeObject(temp);
					socketOut.println("ADD");
					JOptionPane.showMessageDialog(null, "Client information has been saved.");
					resetAddMenuFields();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * this action listener is responsible for sending the serialized client to the server controller to be deleted
	 *
	 */
	class DeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Client temp = (Client) view.getListSelection();
				output = new ObjectOutputStream (new FileOutputStream ( "client.ser" ));
				output.writeObject(temp);
				socketOut.println("DELETE");
				
				if (socketIn.readLine().equals("DELETE MADE")) 
					view.updateList();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	

	
	/**
	 * this action listener is responsible for sending a seralized client over to the server controller to be modified in the database
	 *
	 */
	class SaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			if (checkForErrors()) {
				try {
					Client temp = new Client(Integer.parseInt(view.getID()),view.getFirstName(), view.getLastName(), view.getAddress(), view.getPostal(), view.getNumber(), view.getClientType());
					output = new ObjectOutputStream (new FileOutputStream ( "client.ser" ));
					output.writeObject(temp);
					socketOut.println("MODIFY");
					
					if (socketIn.readLine().equals("MODIFY MADE")) {
						JOptionPane.showMessageDialog(null, "Client information has been saved.");
						view.updateList();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}

	
	/**
	 * this list selection listener is responsible for updating the values in the right screen when a client has been selected in the
	 * bottom left portion of the screen after searching for them
	 *
	 */
	class ClientListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub
			Client temp = (Client) view.getListSelection();
			view.setSelectedClientFields(temp);
		}
		
	}
	
	/**
	 * this action listener is responsible for searching for numerous types of clients 
	 *
	 */
	class SearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (view.getString().equals("") || (!view.clientIDButtonStatus() && !view.lastNameButtonStatus() && !view.clientTypeButtonStatus())) {
				return; //do nothing since either no buttons pressed or text field is blank
			}
			else if (view.clientIDButtonStatus()) {
				String temp = view.getString();
				socketOut.println("SEARCH BY ID");
				socketOut.println(temp);
				
				try {
					temp = socketIn.readLine();
					if (temp.equals("ID OUTPUT")) {
						input = new ObjectInputStream(new FileInputStream( "client.ser" ));
						DefaultListModel<Client> clientlist = (DefaultListModel<Client>) input.readObject();
						view.setList(clientlist);
					}
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
			else if (view.lastNameButtonStatus()) {
				String temp = view.getString();
				socketOut.println("SEARCH BY NAME");
				socketOut.println(temp);
				
				try {
					temp = socketIn.readLine();
					if (temp.equals("NAME OUTPUT")) {
						input = new ObjectInputStream(new FileInputStream( "client.ser" ));
						DefaultListModel<Client> clientlist = (DefaultListModel<Client>) input.readObject();
						view.setList(clientlist);
					}
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			else {
				String temp = view.getString();
				socketOut.println("SEARCH BY TYPE");
				socketOut.println(temp);
				
				try {
					temp = socketIn.readLine();
					if (temp.equals("TYPE OUTPUT")) {
						input = new ObjectInputStream(new FileInputStream( "client.ser" ));
						DefaultListModel<Client> clientlist = (DefaultListModel<Client>) input.readObject();
						view.setList(clientlist);
					}
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
	}
	/**
	 * this action listener is responsible for clearing the search fields after it has been pressed
	 *
	 */
	class ClearSearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			view.reset();	
		}
	}
	
	/**
	 * this action listener is responsible for disabling the other search options when client id has been pressed
	 *
	 */
	class ClientIDListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (view.clientIDButtonStatus()) {
				view.disableForClientID();
			}
			else {
				view.enableForClientID();
			}
		}
	}
	
	/**
	 * this action listener is responsible for disabling the other search options when last name has been pressed
	 *
	 */
	class LastNameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (view.lastNameButtonStatus()) {
				view.disableForLastName();
			}
			else {
				view.enableForLastName();
			}
		}
	}
	
	/**
	 * this action listener is responsible for disabling the other search options when client type has been pressed
	 *
	 */
	class ClientTypeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (view.clientTypeButtonStatus()) {
				view.disableForClientType();
			}
			else {
				view.enableForClientType();
			}
		}
	}
	
	/**
	 * this action listener switches from the main menu to the add client menu
	 *
	 */
	class MainMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			view.setVisible(true);
			addView.setVisible(false);
		}
	}
	
	/**
	 * this action listener switches from the add client menu to the main menu
	 *
	 */
	class AddListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.setVisible(false);
			addView.setVisible(true);
		}
		
	}

	/**
	 * this method resets all the add menu fields to blank
	 */
	public void resetAddMenuFields() {
		// TODO Auto-generated method stub
		addView.setAddress();
		addView.setFirstName();
		addView.setLastName();
		addView.setPostalCode();
		addView.setNumber();
		addView.setClientType();
	}
	

	/**
	 * this method checks for appropriate values when modifying a client
	 * @return boolean, returns true if values are appropriate else it will return false
	 */
	public boolean checkForErrors() {
		if (view.getFirstName().length() == 0 || view.getLastName().length() == 0 || view.getAddress().length() == 0 || view.getNumber().length() == 0 || view.getPostal().length() == 0) {
			JOptionPane.showMessageDialog(null, "Error occurred: All fields must be filled in.");
			return false;
		}
		else if (view.getFirstName().length() > 20 || view.getLastName().length() > 20 || view.getAddress().length() > 50) {
			 JOptionPane.showMessageDialog(null, "Error occurred: The maximum length of the first name, last name, and address are 20, 20, 50 respecively.");
			 return false;
		}
		else if (!view.getNumber().matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d")){
			JOptionPane.showMessageDialog(null, "Error occurred: The phone number entered must be in 111-111-1111 format.");
			return false;
		}
		else if (!view.getPostal().matches("[A-Z]\\d[A-Z] \\d[A-Z]\\d")) {
			JOptionPane.showMessageDialog(null, "Error occurred: The postal code entered must be in A1A 1A1 format.");
			return false;
		}
		else if (view.getID() == null || view.getID().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error occurred: To add new clients you must use the add client menu");
			return false;
		}
		else
			return true;
	}
	
	/**
	 * this method checks for appropriate values in the add client menu
	 * @return boolean, returns true if all values are appropriate else it will return false
	 */
	public boolean clientMenuCheckForErrors() {
		if (addView.getFirstName().length() == 0 || addView.getLastName().length() == 0 || addView.getAddress().length() == 0 || addView.getNumber().length() == 0 || addView.getPostalCode().length() == 0) {
			JOptionPane.showMessageDialog(null, "Error occurred: All fields must be filled in.");
			return false;
		}
		else if (addView.getFirstName().length() > 20 || addView.getLastName().length() > 20 || addView.getAddress().length() > 50) {
			 JOptionPane.showMessageDialog(null, "Error occurred: The maximum length of the first name, last name, and address are 20, 20, 50 respecively.");
			 return false;
		}
		else if (!addView.getNumber().matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d")){
			JOptionPane.showMessageDialog(null, "Error occurred: The phone number entered must be in 111-111-1111 format.");
			return false;
		}
		else if (!addView.getPostalCode().matches("[A-Z]\\d[A-Z] \\d[A-Z]\\d")) {
			JOptionPane.showMessageDialog(null, "Error occurred: The postal code entered must be in A1A 1A1 format.");
			return false;
		}
		else
			return true;
	}

}
