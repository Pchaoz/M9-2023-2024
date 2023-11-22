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
			
			skInter.receive(); //TIENE QUE RECIBIR EL WELCOME DEL SERVIDOR SI NO PETA AL SEGUNDO MENSAJE XD
			System.out.println("SALUDANT AL SERVIDOR..");
			skInter.send("OH HI"); //SALUDO AL SERVIDOR
			
			while (!session) {
				
				System.out.println("ENVIA ALGO AL SERVIDOR: ");
				userInput = inConsola.readLine(); // ESCRIBO QUE QUIERO ENVIAR AL SERVIDOR

				if (userInput.equals("BBYE")) {
					skInter.sendReceive(userInput, "KTHXBYE");
					skInter.close();
					session = true;
					System.out.println("TANCANT SESSIO AMB EL SERVIDOR..");
				}else {
					skInter.sendReceive(userInput, userInput); //SI NO PILLA LA CONDICION DE ANTES LO ENVIA ESPERANDO A QUE EL SERVIDOR CONTESTE CON SU PROPIO MENSAJE
				}
			}

		} catch (IOException e) {

		}
	}
}
