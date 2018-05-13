package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Alba Olmo
 *
 */

class ClientHandler extends Thread 
{
	private static final String NAME = "Magdalena";
	
	final Robot robot;
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket clientSocket;
    
     
 
    // Constructor
    public ClientHandler(Socket clientSocket, DataInputStream dis, DataOutputStream dos, Robot robot) 
    {
        this.clientSocket = clientSocket;
        this.dis = dis;
        this.dos = dos;
        this.robot = robot;
    }
 
    @Override
    public void run() 
    {
        String received;
        String toreturn="";
        
        //First of all, send Robot's greeting
        toreturn = "210 Hello, here is Robot version 1.6. My name is "+ robot.getName() +".";
        try {
			dos.writeUTF(toreturn);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        //Class with all the methods to process all the commands received from the client
        CommandProcessor commandProcessor = new CommandProcessor(robot);
        
        
        while (true) 
        {
            try {
 
                
                // receive the answer from client
                received = dis.readUTF();
                
                
                
                 
                // write on output stream based on the answer from the client 
                if(received.equals(""+robot.getName()+" STEP")){
                    toreturn = commandProcessor.processStep();
                    dos.writeUTF(toreturn);
                
                }else if(received.equals(""+robot.getName()+" LEFT")){
                	toreturn = commandProcessor.processLeft();
                    dos.writeUTF(toreturn);
                
                }else if(received.substring(0,16).equals("Magdalena REPAIR")){
                	toreturn = commandProcessor.processRepair(new Integer(received.substring(17)));
                    dos.writeUTF(toreturn);
                
                }else if(received.equals(""+robot.getName()+" PICK UP")){
                	toreturn = commandProcessor.processPickUp();
                    dos.writeUTF(toreturn);
                
                }else{
                	 dos.writeUTF("500 UNKNOWN COMMAND");
                }
                
                //In those cases the server has to close the connection
                if(toreturn.substring(0,3).equals("260") || 
                		toreturn.substring(0,3).equals("530") ||
                		toreturn.substring(0,3).equals("550") ||
                		toreturn.substring(0,3).equals("571") ||
                		toreturn.substring(0,3).equals("572")){
                	 System.out.println("Closing this connection.");
                     this.clientSocket.close();
                     dos.close();
                     dis.close();
                     System.out.println("Connection closed");
                     break;
                	
                }
                 
               
                
                
     
                    
        
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
