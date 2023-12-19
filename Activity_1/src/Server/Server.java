package Server;

import java.net.ServerSocket;
import java.net.Socket;

import Util.SocketInterface;

public class Server {
	public static void main(String[] args) {
		ServerSocket ssk;
		Socket csk;
		boolean session = false;

		final String hostName = "127.0.0.1";
		final int port = 5000;

		String clientMsg = "";
		
		try {
			ssk = new ServerSocket(port); //CREO EL SOCKET SERVIDOR
			
			System.out.println("Esperant client");
			csk = ssk.accept(); //ESPERA Y CONECTA AL CLIENTE
			
			System.out.println("CLIENTE CONECTADO");
			
			
		}catch (Exception e) {
			
		}
	}
}
