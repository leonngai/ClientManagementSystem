package Client;

/**
 * this class has no outside functionality other than creating instances of other classes in its main method
 * @author Leon
 *
 */
public class ClientManagement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ClientManagementView view = new ClientManagementView ();
		ClientManagementAddView addView = new ClientManagementAddView();
		
		ClientManagementController controller = new ClientManagementController(view,addView);

	}

}
