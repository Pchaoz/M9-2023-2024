package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import Util.SocketInterface;
import Util.WrongProtocolException;

public class RuletaClient {

	public static void main(String[] args) {
		
		SocketInterface skInterface;
		String userInp = "";
		boolean session = false;
		
		final String hostname = "10.1.86.25";
		final int port = 60047;
		
		try {
			System.out.println("CL => INTENTANDO CONECTAR AL SERVIDOR");
			Socket skt = new Socket(hostname, port);
			skInterface = new SocketInterface(skt);
			System.out.println("CL => RECIBIENDO LA BIENVENIDA");
			skInterface.receive("CYKA BLYATT");
			System.out.println("CL => CLIENTE CONECTADO CORRECTAMENTE");
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch (WrongProtocolException e) {
			e.printStackTrace();
		}
	}
}
