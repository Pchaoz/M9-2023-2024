package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import Util.BinaryMessage;
import Util.SocketInterface;
import Util.WrongProtocolException;

public class RuletaClient {

	public static void main(String[] args) {
		
		SocketInterface skInterface;
		String userInp = "";
		Scanner kyb = new Scanner(System.in);
		BinaryMessage bMsg = new BinaryMessage();
		boolean askNickname = true;
		boolean game = true;
		byte response;
		
		//LOCALHOST
		final String hostname = "127.0.0.1";
		final int port = 5000;
		
		//EL SERVIDOR DEL DAVID
		//final String hostname = "10.1.86.25";
		//final int port = 60047;
		
		try {
			
			System.out.println("CL => INTENTANDO CONECTAR AL SERVIDOR");
			Socket skt = new Socket(hostname, port);
			skInterface = new SocketInterface(skt);
			System.out.println("CL => RECIBIENDO LA BIENVENIDA");
			skInterface.receive(bMsg.S_BENVINGUT);
			System.out.println("CL => CLIENTE CONECTADO CORRECTAMENTE");
			skInterface.send(bMsg.ACK);
			
			while(askNickname) {
				System.out.println("CL => PORFAVOR, ESCOJE UN NICKNAME");
				userInp = kyb.nextLine();
				skInterface.sendString(userInp);
				response = skInterface.receive();
				
				if (response == bMsg.S_NICK_EN_US)
					System.out.println("CL => NOMBRE ACTUALMENTE EN USO, PORFAVOR ESCRIBE OTRO NOMBRE");
				else if (response == bMsg.ACK) {
					System.out.println("CL => NICKNAME CREADO CORRECTAMENTE");
					askNickname = false;
				}else 
					throw new WrongProtocolException("SOMETHING WENT WRONG");				
			}
			while (game) {
				response = skInterface.receive();
				
				if (response == bMsg.S_ESTAS_DINS) {
					System.out.println("CL => HAS ENTRADO EN LA PARTIDA");
					skInterface.send(bMsg.ACK);
					
					//PARTIDA
					response = skInterface.receive();
				}
				else if (response == bMsg.S_EN_CURS) {
					System.out.println("CL => LA PARTIDA YA ESTA EN CURSO");
					skInterface.send(bMsg.ACK);
				}
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		} catch (WrongProtocolException e) {
			e.printStackTrace();
		}
	}
}
