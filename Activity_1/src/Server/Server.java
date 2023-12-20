package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

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

		final String hostName = "127.0.0.1";
		final int port = 5000;

		String clientMsg = "";
		
		try {
			ssk = new ServerSocket(port); //CREO EL SOCKET SERVIDOR
			
			System.out.println("Esperant client");
			csk = ssk.accept(); //ESPERA Y CONECTA AL CLIENTE
			skInter = new SocketInterface(csk);
			System.out.println("CLIENTE CONECTADO");
			
			skInter.sendReceive("CYKA BLYATT", "ACK");
			
			while (session) {
				skInter.sendReceive("QUIERO JUGAR A UN JUEGO", "ACK");
				
				int [] bullets = {rng.nextInt(0, 2), rng.nextInt(0, 2), rng.nextInt(0, 2), rng.nextInt(0, 2), rng.nextInt(0, 2)};
				
				skInter.sendObj(bullets); //LE ENVIO LA ARRAY AL CLIENTE
				skInter.receive("ACK");
				
				skInter.receive(); //RECIBO LAS RONDAS QUE HA DURAO EL CLIENTE
				skInter.send("ACK");
				
				skInter.send("NO HAY HUEVOS?");
				clientMsg = skInter.receive();
				
				if (clientMsg.equalsIgnoreCase("EL QUE LA SACA LA USA")) {
					//REINICIA EL BUCLE
					
				}else if (clientMsg.equalsIgnoreCase("SOY UN PARGUELA")) {
					skInter.send("KURWA");
					skInter.close();
					
				}else {
					skInter.send("ERROR");
					throw new WrongProtocolException("RECIBIDO RESPUESTA INVALIDA");
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		} catch (WrongProtocolException e) {
			e.printStackTrace();
		}finally {
			skInter.close();
		}
	}
}
