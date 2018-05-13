package server;

import java.io.*;
import java.net.*;
 

/**
 * @author Alba Olmo
 *
 */

// Server class
public class Server 
{
	
	private static final String NAME = "Magdalena";
	
    public static void main(String[] args) throws IOException 
    {
        
    	//Integer port = new Integer(args[0]);
    	Integer port = new Integer(3998);
		
		// server is listening on port 1313
		ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1313.");
            System.exit(1);
        }
        
        while (true) 
        {
            Socket clientSocket = null;
             
            try
            {
                // socket object to receive incoming client requests
                clientSocket = serverSocket.accept();
                
                
                
                //print info about the client
                System.out.println("A new client is connected from "+ clientSocket.getInetAddress() 
        		+ ":" + clientSocket.getPort()); 
                
              //create a new Robot for the new client
                Robot robot = new Robot(NAME);
                
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                 
                System.out.println("Assigning new thread for this client");
 
                // create a new thread object
                Thread t = new ClientHandler(clientSocket, dis, dos,robot);
 
                // Invoking the start() method
                //t.start();
                t.run();
                 
            }
            catch (Exception e){
            	System.err.println("Accept failed.");
            	clientSocket.close();
                e.printStackTrace();
            }
        }
    }
}
 

