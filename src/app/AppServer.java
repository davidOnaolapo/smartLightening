package app;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServer {
	private ServerSocket s;
	private HueController hue;
	public AppServer() {
		try {
			hue = new HueController();
			s = new ServerSocket(9999);
			System.out.println("SVR: Listening on port 9999");
			Socket s2 = s.accept();

			System.out.println("SVR: Got connection to some IDIOT");
			BufferedReader clientIn = new BufferedReader(new InputStreamReader(s2.getInputStream())); //for reading from the socket
			PrintWriter clientOut = new PrintWriter(new OutputStreamWriter(s2.getOutputStream()));

			String line;
					
			clientOut.println("WELCOME TO MY SERVER");
			clientOut.flush();
			boolean exit = false;
			
			while((line = clientIn.readLine()) != null) {
				switch (line) {
				case "on": 
					System.out.println("SVR: Turned on the lights!");
					hue.turnLightOn();
					clientOut.println("OK");
					clientOut.flush();
					break;
				case "off":
					System.out.println("SVR: Turned off the lights!");
					hue.turnLightOff();
					clientOut.println("OK");
					clientOut.flush();
					break;
				case "bye":
					System.out.println("SVR: Client is going away :(");
					clientOut.println("bye");
					clientOut.flush();
//					hue.turnLightOff(); optional
					exit = true;
					break;
				}
				
				if (exit) {
					break;
				}
				
			}
			
			s2.close();
			
		} catch (Exception e) {
			System.err.println("Ran into some kind of exception..");
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		AppServer svr = new AppServer();
	}
}
