package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import Util.SocketInterface;
import Util.WrongProtocolException;

public class MyClient {

	public static void main(String[] args) {

		SocketInterface skInter;
		String userOption = "";
		String userMsg = "";
		boolean session = true;

		final String hostName = "127.0.0.1";
		final int port = 5000;

		try {

			Socket skt = new Socket(hostName, port); //CREO EL SOCKET PARA CONECTARME AL SERVIDOR
			BufferedReader inConsola = new BufferedReader(new InputStreamReader(System.in)); //PARA ENVAR MENSAJES AL SERVIDOR POR CONSOLA
			
			skInter = new SocketInterface(skt); //ME CONECTO AL SERVIDOR MEDIANTE LA CLASE INTERMEDIA
			
			skInter.receive(); //TIENE QUE RECIBIR EL WELCOME DEL SERVIDOR SI NO PETA AL SEGUNDO MENSAJE XD
			System.out.println("SALUDANT AL SERVIDOR..");
			skInter.send("OH HI"); //SALUDO AL SERVIDOR
			
			while (session) {
				
				System.out.println("ESCOJE OPCION 1-2: ");
				userOption = inConsola.readLine(); // ESCRIBO QUE OPCION QUIERO AL SERVIDOR
				
				switch(userOption) {
					case "1":
						userMsg = inConsola.readLine();
						if (userMsg.equals("BBYE"))
							throw new WrongProtocolException("NO SE PERMITE EL BYE COMO PARABLA NORMAL");
						skInter.sendReceive(userMsg, userMsg);
						break;
					case "2":
						skInter.sendReceive("BBYE", "KTHXBYE");
						skInter.close();
						session = false;
						break;
						
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}catch (WrongProtocolException e) {
			e.printStackTrace();
		}
	}
}
