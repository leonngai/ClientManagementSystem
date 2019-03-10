package SharedFolder;

import java.io.Serializable;


/**
 * this class acts as the model for the client server system and is therefore a shared package between both
 * the client and server because they need to have the class in each for serialization to work properly
 *
 */
public class Client implements Serializable{
	
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String postalCode;
	private String phoneNumber;
	private String clientType;
	private static final long serialVersionUID = 1;

	
	/**
	 * this constructor is used when creating a new client on the add menu
	 * @param fn, this is the first name
	 * @param ln, this is the last name
	 * @param add, this is the address
	 * @param postalCod, this is the postal code
	 * @param num, this is the phone number
	 * @param type, this is the client type
	 */
	public Client(String fn, String ln, String add, String postalCod, String num, String type) {
		
		firstName = fn;
		lastName = ln;
		address = add;
		postalCode = postalCod;
		phoneNumber = num;
		clientType = type;
	}

	/** this constructor is used when modifying an existing client
	 * @param clientCounter, this is the client counter which represents the ID
	 * @param fn, this is the first name
	 * @param ln, this is the last name
	 * @param add, this is the address
	 * @param postalCod, this is the postal code
	 * @param num, this is the phone number
	 * @param type, this is the client type
	 */
	public Client(int clientCounter, String fn, String ln, String add, String postalCod, String num, String type) {
	
		id = clientCounter;
		firstName = fn;
		lastName = ln;
		address = add;
		postalCode = postalCod;
		phoneNumber = num;
		clientType = type;
	}

	public int getID() { return id;}
	public void setID(int id) { this.id = id;}
	public String getFirstName() { return firstName;}
	public String getLastName() { return lastName;}
	public String getAddress() { return address;}
	public String getPostalCode() { return postalCode;}
	public String getPhoneNumber() { return phoneNumber;}
	public String getClientType() { return clientType;}

	@Override
	public String toString()
	{
		String client = this.firstName + " " + this.lastName + " " + this.clientType + " " + this.address;
		return client;
	}
	
	
}
