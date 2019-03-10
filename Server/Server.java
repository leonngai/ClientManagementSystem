package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this is the server class which hosts the server and the thread pool for server controllers; when a client has connected to the server socket, 
 * the server will create an instance of a server controller and pass that socket to the server controller for 
 * further communications between the server controller and client controller
 * @author Leon
 */
public class Server {

	private ExecutorService pool;
	
	private PrintWriter out;
	private Socket aSocket;
	private ServerSocket serverSocket;
	
	/**
	 * this is the default constructor for the class Server and creates a new server socket at port 9898 as well as
	 * a fixed thread pool size of 2
	 */
	public Server() {
		try {
			serverSocket = new ServerSocket(9898);
			pool = Executors.newFixedThreadPool(2);
		} catch (IOException e) {
			System.out.println("Create new socket error");
			System.out.println(e.getMessage());
		}
		
		System.out.println("Server is running");
		
	}
	
	/**
	 * this method is what runs the server; it will create a new instance of a database controller and server controller and then pass 
	 * the socket connection and database controller to the server controller. The process is repeated until all threads have been used up in the thread pool 
	 */
	public void runServer() {

		while (true) {
			try {
				aSocket = serverSocket.accept();
				DatabaseController dbController = new DatabaseController();
				ServerController serverController = new ServerController(aSocket, dbController);
				pool.execute(serverController);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.runServer();
	}


}
