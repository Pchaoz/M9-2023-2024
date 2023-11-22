package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import Util.SocketInterface;

public class MyClient {

	public static void main(String[] args) {

		SocketInterface skInter;
		String userInput = "";
		boolean session = false;

		final String hostName = "127.0.0.1";
		final int port = 5000;

		try {

			Socket skt = new Socket(hostName, port); //CREO EL SOCKET PARA CONECTARME AL SERVIDOR
			BufferedReader inConsola = new BufferedReader(new InputStreamReader(System.in)); //PARA ENVAR MENSAJES AL SERVIDOR POR CONSOLA
			
			skInter = new SocketInterface(skt); //ME CONECTO AL SERVIDOR MEDIANTE LA CLASE INTERMEDIA
			skInter.send("OH HI");
			
			while (!session) {
				skInter.receive(); //RECIBE EL MENSAJE DEL SERVIDOR
				
				System.out.println("ENVIA ALGO AL SERVIDOR: ");
				userInput = inConsola.readLine(); // ESCRIBO QUE QUIERO ENVIAR AL SERVIDOR

				if (userInput.equals("BYE")) {
					skInter.sendReceive(userInput, "KTHXBYE");
					skInter.close();
					session = true;
				}else {
					skInter.send(userInput); //SI NO PILLA LA CONDICION DE ANTES SIMPLEMENTE LO ENVIA
				}
			}

		} catch (IOException e) {

		}
	}
}
