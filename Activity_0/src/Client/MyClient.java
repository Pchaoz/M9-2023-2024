package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import Util.SocketInterface;

public class MyClient {

public static void main(String[] args) {
		
		SocketInterface skInter;
		String userInput;
		
		final String hostName = "127.0.0.1";
		final int port  = 5000;

		
		
		try {
			
			Socket skt = new Socket(hostName, port);
			skInter = new SocketInterface(skt);
			BufferedReader inConsola = new BufferedReader(new InputStreamReader(System.in));
			skInter.receive();
			
			while((userInput = inConsola.readLine())!= null) {
				System.out.println("Intentar enviar: "+ userInput);
				skInter.sendReceive(userInput, "KTHXBYE");
				
			}
			
		}catch (IOException e ) { 
			
		}
	}
}
