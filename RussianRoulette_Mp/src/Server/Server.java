package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import Util.BinaryMessage;
import Util.SocketInterface;
import Util.WrongProtocolException;

public class Server {
	public static void main(String[] args) {
		
		ServerSocket ssk;
		Socket csk;
		ObjectOutputStream objOut;
		ObjectInputStream objIn;
		SocketInterface skInter = null;
		Random rng = new Random();
		boolean session = true;
		BinaryMessage bMsg = new BinaryMessage();

		final String hostName = "127.0.0.1";
		final int port = 5000;

		String clientMsg = "";
		
		try {
			ssk = new ServerSocket(port); //CREO EL SOCKET SERVIDOR
			
			System.out.println("S => ESPERANT CLIENTS...");
			csk = ssk.accept(); //ESPERA Y CONECTA AL CLIENTE
			skInter = new SocketInterface(csk, false);
			System.out.println("S => UN CLIENTE SE HA CONECTADO");
			skInter.send(bMsg.ACK);
			
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (WrongProtocolException e) {
			e.printStackTrace();
		}finally {
			skInter.close();
		}
	}
}
