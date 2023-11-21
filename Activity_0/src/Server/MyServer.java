package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Util.SocketInterface;

public class MyServer {

	public static void main(String[] args) {
		
		SocketInterface skInter;
		
		final String hostName = "127.0.0.1";
		final int port  = 5000;
		
		try {
			
			ServerSocket sktS = new ServerSocket(port);
			System.out.println("Esperant client");
			Socket cSkt = sktS.accept();
			skInter = new SocketInterface(cSkt);
			System.out.println("Entra el client");
			skInter.sendReceive("WELCOME", "OH HI");
			
		}catch (IOException e ) { 
			
		}
	}
}
