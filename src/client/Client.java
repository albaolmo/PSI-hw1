package client;

/**
 * @author Alba Olmo
 *
 */


import java.io.*;
import java.net.*;
import java.util.Scanner;

import server.CommandProcessor;

// Client class
public class Client {

	public static void main(String[] args) throws IOException {
		try {
			// Scanner scn = new Scanner(System.in);

			//String server = args[0];
			//String server = "127.0.0.1";
			String server = "baryk.fit.cvut.cz";
			//Integer port = new Integer(args[1]);
			Integer port = new Integer(3998);
			
			// getting localhost ip
			InetAddress ip = InetAddress.getByName(server);

			// establish the connection with server port 1313
			Socket clientSocket = new Socket(ip, port);

			// obtaining input and out streams
			DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

			// Class with all the methods to process all the answers received
			// from the server
			AnswerProcessor answerProcessor = new AnswerProcessor();

			// The following loop performs the exchange of information between
			// client and client handler
			while (true) {
				String received = dis.readUTF();
				System.out.println(received);
				String tosend = null;

				if (received.substring(0, 9).equals("210 Hello")) {
					//System.out.println("Hola");
					tosend = answerProcessor.processGreeting("Magdalena");
					dos.writeUTF(tosend);
					System.out.println(tosend);

				} else if (received.substring(0, 6).equals("240 OK")) {// 240 OK (12,7)
					int comma = 0;
					int p1=0;
					int p2=0;
					for(int i=0;  i<received.length(); i++){
						if(received.charAt(i)==','){
							comma = i;
						}else if(received.charAt(i)=='('){
							p1= i;
						}else if(received.charAt(i)==')'){
							p2=i;
						}
					}
					Integer x = new Integer(received.substring(p1+1, comma));
					Integer y = new Integer(received.substring(comma+1, p2));
					tosend = answerProcessor.processOk(x, y);
					dos.writeUTF(tosend);
					System.out.println(tosend);

				} else if (received.equals("500 UNKNOWN COMMAND")) {
					tosend = answerProcessor.processUnknown();
					dos.writeUTF(tosend);
					System.out.println(tosend);

				} else if (received.substring(0, 20).equals("580 FAILURE OF BLOCK")) {
					tosend = answerProcessor.processFailure(new Integer(received.substring(21)));
					dos.writeUTF(tosend);
					System.out.println(tosend);

				} else if(received.substring(0, 11).equals("260 SUCCESS")){
					System.out.println(received.substring(12));
				}

			}

			// closing resources
			// s.close();
			// scn.close();
			// dis.close();
			//dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
