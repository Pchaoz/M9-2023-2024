package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Util.SocketInterface;

public class MyServer {

	public static void main(String[] args) {

		SocketInterface skInter;
		boolean session = false;

		final String hostName = "127.0.0.1";
		final int port = 5000;

		String clientMsg = "";

		try {
			ServerSocket sktS = new ServerSocket(port); // CREA EL SERVER SOCKET

			System.out.println("Esperant client");
			Socket cSkt = sktS.accept(); // ESPERA AL CLIENTE PARA QUE SE CONECTE

			skInter = new SocketInterface(cSkt); // CREA EL INTERMEDIARIO CON EL SOCKET DEL CLIENTE
			System.out.println("Entra el client"); // AVISA QUE ENTRA EL CLIENTE

			skInter.sendReceive("WELCOME", "OH HI"); // LO SALUDA Y ESPERA QUE EL CLIENTE LO SALUDE DE VUELTA

			while (!session) {
				clientMsg = skInter.receive(); //RECIBO EL MENSAJE DEL JUGADOR
				
				if (clientMsg.equals("BBYE")) { //SI ES BYE PROCEDO A CONTESTARLE Y CERRAR LA CONEXION
					skInter.send("KTHXBYE");
					skInter.close();
					session = true;
				}else {
					skInter.send(clientMsg);
				}
			}

		} catch (IOException e) {

		}
	}
}
