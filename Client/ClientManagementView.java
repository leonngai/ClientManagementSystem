package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import SharedFolder.Client;

import javax.swing.JSplitPane;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import java.awt.Choice;
import java.awt.Checkbox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.SpinnerListModel;

/**
 * this class is the main screen that the user will generally see unless they want to add a new client
 * @author Leon
 *
 */
public class ClientManagementView extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JRadioButton rdbtnNewRadioButton = new JRadioButton("Client ID");
	private JRadioButton rdbtnLastName = new JRadioButton("Last Name");
	private JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Client Type");
	private JButton btnSearch = new JButton("Search");
	private JButton btnCancelSearch = new JButton("Clear Search");
	private JList list = new JList();
	private JSpinner spinner = new JSpinner();
	private JButton btnSave = new JButton("Save");
	private JButton btnDelete = new JButton("Delete");
	private JButton btnAdd = new JButton("Add");


	/**
	 * this is the default constructor for the class and creates a new frame
	 */
	public ClientManagementView() {
		setTitle("Client Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSearchClients = new JLabel("Search Clients");
		lblSearchClients.setBounds(161, 6, 89, 16);
		panel.add(lblSearchClients);
		
		JLabel lblNewLabel = new JLabel("Select type of search to be performed:");
		lblNewLabel.setBounds(6, 48, 244, 16);
		panel.add(lblNewLabel);
		
		
		rdbtnNewRadioButton.setBounds(0, 71, 141, 23);
		panel.add(rdbtnNewRadioButton);
		
		
		rdbtnLastName.setBounds(0, 94, 141, 23);
		panel.add(rdbtnLastName);
		
		
		rdbtnNewRadioButton_1.setBounds(0, 119, 141, 23);
		panel.add(rdbtnNewRadioButton_1);
		
		JLabel lblEnterTheSearch = new JLabel("Enter the search parameter below:");
		lblEnterTheSearch.setBounds(6, 166, 221, 16);
		panel.add(lblEnterTheSearch);
		
		textField = new JTextField();
		textField.setBounds(6, 194, 221, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		
		btnSearch.setBounds(227, 194, 76, 29);
		panel.add(btnSearch);
		
		
		btnCancelSearch.setBounds(297, 194, 117, 29);
		panel.add(btnCancelSearch);
		
		JLabel lblSearchResults = new JLabel("Search Results:");
		lblSearchResults.setBounds(6, 232, 95, 16);
		panel.add(lblSearchResults);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 260, 391, 204);
		panel.add(scrollPane);
		
		scrollPane.setViewportView(list);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblClientInformation = new JLabel("Client Information");
		lblClientInformation.setBounds(152, 6, 115, 16);
		panel_1.add(lblClientInformation);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(135, 42, 46, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(23, 104, 72, 16);
		panel_1.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(23, 164, 72, 16);
		panel_1.add(lblLastName);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(23, 224, 61, 16);
		panel_1.add(lblAddress);
		
		JLabel lblPostalCode = new JLabel("Postal Code:");
		lblPostalCode.setBounds(23, 284, 84, 16);
		panel_1.add(lblPostalCode);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setBounds(23, 344, 100, 16);
		panel_1.add(lblPhoneNumber);
		
		JLabel lblClientType = new JLabel("Client Type:");
		lblClientType.setBounds(23, 404, 84, 16);
		panel_1.add(lblClientType);
		
		JLabel lblClientId = new JLabel("Client ID:");
		lblClientId.setBounds(23, 44, 61, 16);
		panel_1.add(lblClientId);
		
		textField_2 = new JTextField();
		textField_2.setBounds(135, 99, 130, 26);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(135, 159, 130, 26);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(135, 219, 174, 26);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(135, 279, 115, 26);
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(135, 339, 130, 26);
		panel_1.add(textField_6);
		textField_6.setColumns(10);
		
		btnSave.setBounds(51, 448, 72, 29);
		panel_1.add(btnSave);
		
		btnAdd.setBounds(297, 448, 72, 29);
		panel_1.add(btnAdd);
		
		
		btnDelete.setBounds(174, 448, 72, 29);
		panel_1.add(btnDelete);
		
		spinner.setModel(new SpinnerListModel(new String[] {"R", "C"}));
		spinner.setBounds(135, 399, 34, 26);
		panel_1.add(spinner);

		setVisible(true);
	
		
	}
	
	public String getID() { return textField_1.getText();}
	public String getString () {return textField.getText();}
	public String getFirstName() {return textField_2.getText();}
	public String getLastName() {return textField_3.getText();}
	public String getAddress() {return textField_4.getText();}
	public String getPostal() {return textField_5.getText();}
	public String getNumber() {return textField_6.getText();}
	public String getClientType() { return spinner.getValue().toString();}
	
	public void setString(String str) {
		textField.setText(str);
	}
	
	public void setList(DefaultListModel<Client> clientList) {
		list.setModel(clientList);		
	}
	
	public void setSelectedClientFields(Client temp) {
		if (temp == null) {
			textField_1.setText("");
			textField_2.setText("");
			textField_3.setText("");
			textField_4.setText("");
			textField_5.setText("");
			textField_6.setText("");
			spinner.setValue("R");
		}
		else {
			textField_1.setText(String.valueOf(temp.getID()));
			textField_2.setText(temp.getFirstName());
			textField_3.setText(temp.getLastName());
			textField_4.setText(temp.getAddress());
			textField_5.setText(temp.getPostalCode());
			textField_6.setText(temp.getPhoneNumber());
			spinner.setValue(temp.getClientType());
		}
	}
	
	public Object getListSelection() {
		return list.getSelectedValue();
	}
	
	public void reset() {
		setString("");
		rdbtnNewRadioButton.setSelected(false);
		rdbtnLastName.setSelected(false);
		rdbtnNewRadioButton_1.setSelected(false);
		rdbtnNewRadioButton.setEnabled(true);
		rdbtnLastName.setEnabled(true);
		rdbtnNewRadioButton_1.setEnabled(true);
		
		DefaultListModel<Client> clientList = new DefaultListModel<>();
		list.setModel(clientList);
	}
	
	public void updateList() {
		btnSearch.doClick();
	}
	public boolean clientIDButtonStatus() {
		return rdbtnNewRadioButton.isSelected();
	}
	
	public boolean lastNameButtonStatus() {
		return rdbtnLastName.isSelected();
	}
	
	public boolean clientTypeButtonStatus() {
		return rdbtnNewRadioButton_1.isSelected();
	}
	
	public void disableForClientID() {
		rdbtnLastName.setEnabled(false);
		rdbtnNewRadioButton_1.setEnabled(false);
	}
	
	public void enableForClientID() {
		rdbtnLastName.setEnabled(true);
		rdbtnNewRadioButton_1.setEnabled(true);
	}
	
	public void disableForLastName() {
		rdbtnNewRadioButton.setEnabled(false);
		rdbtnNewRadioButton_1.setEnabled(false);
	}
	
	public void enableForLastName() {
		rdbtnNewRadioButton.setEnabled(true);
		rdbtnNewRadioButton_1.setEnabled(true);
	}
	
	public void disableForClientType() {
		rdbtnLastName.setEnabled(false);
		rdbtnNewRadioButton.setEnabled(false);
	}
	
	public void enableForClientType() {
		rdbtnLastName.setEnabled(true);
		rdbtnNewRadioButton.setEnabled(true);
	}
	
	public void registerSearchListener (ActionListener ac) {
		btnSearch.addActionListener(ac);
	}
	
	public void registerClearSearchListener (ActionListener ac){//ac is the listener
		//code here would register listener that exists in the controller 
		btnCancelSearch.addActionListener(ac);//we are registering ac to cancelButton
	}
	
	public void registerClientIDListener (ActionListener ac) {
		rdbtnNewRadioButton.addActionListener(ac);
	}
	
	public void registerLastNameListener (ActionListener ac) {
		rdbtnLastName.addActionListener(ac);
	}
	
	public void registerClientTypeListener (ActionListener ac) {
		rdbtnNewRadioButton_1.addActionListener(ac);
	}
	
	public void registerListListener (ListSelectionListener ac) {
		list.addListSelectionListener(ac);
	}
	
	public void registerDeleteListener (ActionListener ac) {
		btnDelete.addActionListener(ac);
	}
	
	public void registerSaveListener (ActionListener ac) {
		btnSave.addActionListener(ac);
	}
	
	public void registerAddListener(ActionListener ac) {
		btnAdd.addActionListener(ac);
	}
	
}
