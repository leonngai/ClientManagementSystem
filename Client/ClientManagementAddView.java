package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

/**
 * this class gives the user functionality to add new clients to the database
 * @author Leon
 *
 */
public class ClientManagementAddView extends JFrame {

	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JSpinner spinner = new JSpinner();
	private JButton btnAdd = new JButton("Add");
	private JButton btnMainMenu = new JButton("Main Menu");

	/**
	 * This is the default constructor for the class and creates a new frame
	 */
	public ClientManagementAddView() {
		setTitle("Add Client Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel.setBounds(0, 0, 405, 490);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_1 = new JLabel("First Name:");
		label_1.setBounds(10, 31, 72, 16);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Last Name:");
		label_2.setBounds(10, 103, 72, 16);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Address:");
		label_3.setBounds(10, 175, 61, 16);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Postal Code:");
		label_4.setBounds(10, 247, 84, 16);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("Phone Number:");
		label_5.setBounds(10, 319, 100, 16);
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("Client Type:");
		label_6.setBounds(10, 391, 84, 16);
		panel.add(label_6);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(81, 26, 130, 26);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(81, 98, 130, 26);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(81, 170, 174, 26);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(81, 242, 115, 26);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(93, 314, 130, 26);
		panel.add(textField_5);
		
		
		spinner.setModel(new SpinnerListModel(new String[] {"R", "C"}));
		spinner.setBounds(81, 386, 34, 26);
		panel.add(spinner);
		
		btnAdd.setBounds(166, 388, 89, 23);
		panel.add(btnAdd);
		
		btnMainMenu.setBounds(284, 388, 89, 23);
		panel.add(btnMainMenu);
	}
	
	public String getFirstName() { return textField_1.getText();}
	public String getLastName() {return textField_2.getText();}
	public String getAddress() { return textField_3.getText();}
	public String getPostalCode() {return textField_4.getText();}
	public String getNumber() { return textField_5.getText();}
	public String getClientType() { return spinner.getValue().toString();}
	
	public void setFirstName() {textField_1.setText("");}
	public void setLastName() {textField_2.setText("");}
	public void setAddress() {textField_3.setText("");}
	public void setPostalCode() {textField_4.setText("");}
	public void setNumber() {textField_5.setText("");}
	public void setClientType() {spinner.setValue("R");}
	
	public void registerMainMenuListener (ActionListener ac) {
		btnMainMenu.addActionListener(ac);
	}
	
	public void registerAddListener (ActionListener ac) {
		btnAdd.addActionListener(ac);
	}
	
}
