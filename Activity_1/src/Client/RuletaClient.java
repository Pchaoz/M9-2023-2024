package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import Util.SocketInterface;

public class RuletaClient {

	public static void main(String[] args) {
		SocketInterface skInter;
		String userInput = "";
		boolean session = false;
		
		final String hostName = "127.0.0.1";
		final int port = 5000;
		
		try {
			Socket skt = new Socket(hostName, port); //CREO EL SOCKET PARA CONECTARME AL SERVIDOR
			BufferedReader inConsola = new BufferedReader(new InputStreamReader(System.in)); //PARA ENVAR MENSAJES AL SERVIDOR POR CONSOLA
			
			skInter = new SocketInterface(skt);
			skInter.receive("ACK"); //CONFIRMO QUE HE RECIVIDO EL SALUDO
			
			
			
			
		}catch (IOException e) {
			
		}
	}
}
