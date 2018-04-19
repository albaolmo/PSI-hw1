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

			String server = args[0];
			Integer port = new Integer(args[1]);
			
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

				if (received.substring(0, 10).equals("My name is")) {
					tosend = answerProcessor.processGreeting(received.substring(11));
					dos.writeUTF(tosend);
					System.out.println(tosend);

				} else if (received.substring(0, 6).equals("240 OK")) {// 240 OK (12,7)
					Integer x = new Integer(received.substring(8, 10));
					Integer y = new Integer(received.substring(11, 12));
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
			// dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
