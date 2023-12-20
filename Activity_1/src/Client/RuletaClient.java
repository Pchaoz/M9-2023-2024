package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import Util.SocketInterface;
import Util.WrongProtocolException;

public class RuletaClient {

	public static void main(String[] args) {
		
		SocketInterface skInterface;
		String userInp = "";
		Random rng = new Random();
		Scanner kyb = new Scanner(System.in);
		boolean game = true;
		boolean round = true;
		
		final String hostname = "10.1.86.25";
		final int port = 60047;
		
		try {
			System.out.println("CL => INTENTANDO CONECTAR AL SERVIDOR");
			Socket skt = new Socket(hostname, port);
			skInterface = new SocketInterface(skt);
			System.out.println("CL => RECIBIENDO LA BIENVENIDA");
			skInterface.receive("CYKA BLYATT");
			System.out.println("CL => CLIENTE CONECTADO CORRECTAMENTE");
			skInterface.send("ACK");
			
			while (game) {
				skInterface.receive("QUIERO JUGAR A UN JUEGO");
				skInterface.send("ACK");
				
				int [] deagle = (int[]) skInterface.recieveOject(); 
				skInterface.send("ACK");
				round = true;
				
				for (int i = 1; i < deagle.length; i++) {
					
					int blt = rng.nextInt(0, deagle.length);
					 
					if (deagle[blt] == 1) { 
						System.out.println("CL => HE MUERTO EN " + i + " RONDAS.. HACIENDOSELO SABER AL SERVIDOR!");
						skInterface.sendReceive("MUELTO VIVO " + i, "ACK");
						round = false;
						break;
					}
				}
				if (round) {	
					System.out.println("CL => HE SOBREVIVIO " + deagle.length + " RONDAS.. HACIENDOSELO SABER AL SERVIDOR!");
					skInterface.sendReceive("VIVO " + deagle.length, "ACK");
				}
				skInterface.receive("NO HAY HUEVOS?");
				System.out.print("EL SERIVODR TE PREGUNTA SI HAY HUEVOS DE SEGUIR JUGANDO -> ");
				userInp = kyb.nextLine();
				if (userInp.equalsIgnoreCase("EL QUE LA SACA LA USA")) {
					skInterface.send(userInp);
				}else if (userInp.equalsIgnoreCase("SOY UN PARGUELA")) {
					skInterface.sendReceive(userInp, "KURWA");
					skInterface.close();
					game = false;
				}
 			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch (WrongProtocolException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
