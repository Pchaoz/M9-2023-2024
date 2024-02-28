package Client;

import java.io.IOException;
import java.net.Socket;
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
		boolean asking = true;
		boolean lobby = true;
		boolean game = true;
		boolean finished = false;
		byte response;
		
		//LOCALHOST
		final String hostname = "localhost";
		final int port = 60047;
		
		//EL SERVIDOR DEL DAVID
		//final String hostname = "10.1.86.25";
		//final int port = 60047;
		
		try {
			
			System.out.println("CL => INTENTANDO CONECTAR AL SERVIDOR");
			Socket skt = new Socket(hostname, port);
			skInterface = new SocketInterface(skt, true);
			System.out.println("CL => RECIBIENDO LA BIENVENIDA");
			skInterface.receive(bMsg.S_BENVINGUT);
			System.out.println("CL => CLIENTE CONECTADO CORRECTAMENTE");
			skInterface.send(bMsg.ACK);
			
			while(asking) {

				System.out.println("CL => PORFAVOR, ESCOJE UN NICKNAME");
				userInp = kyb.nextLine();
				skInterface.sendString(userInp);
				response = skInterface.receive();
				
				if (response == bMsg.S_NICK_EN_US)
					System.out.println("CL => NOMBRE ACTUALMENTE EN USO, PORFAVOR ESCRIBE OTRO NOMBRE");
				else if (response == bMsg.ACK) {
					System.out.println("CL => NICKNAME CREADO CORRECTAMENTE");
					asking = false;
				}else 
					throw new WrongProtocolException("SOMETHING WENT WRONG");				
			}
			while (lobby)  {
				response = skInterface.receive();
				
				if (response == bMsg.S_ESTAS_DINS) {
					System.out.println("CL => HAS ENTRADO EN LA PARTIDA");
					skInterface.send(bMsg.ACK);
					
					while (game) {
						//PARTIDA
						if (!finished) {
							
							response = skInterface.receive();
							
							if (response == bMsg.S_BALA) {
								System.out.println("CL => TE HAN PEGAO UN TIRO, PRINGAO");
								finished = true;
								skInterface.send(bMsg.ACK);
							}else if (response == bMsg.S_NO_BALA) {
								System.out.println("CL => ESQUIVO ESQUVIO");
								skInterface.send(bMsg.ACK);
							}else if (response == bMsg.S_FINALISTA) {
								System.out.println("CL => HAS GANADO, FACILITO FACILITO JAPONESITO");
								finished = true;
							}else {
								throw new WrongProtocolException("SOMETHING WENT WRONG");	
							}
						}
					}
					
					String[] results = (String[]) skInterface.reciveObject();
					skInterface.send(bMsg.ACK);
					System.out.println("CL -> IMPRIMIENDO RESULTADOS DE LA PARTIDA");
					for (int i = 0; i < results.length; i++) {
						if (i < results.length-1) {
							System.out.println(i + ": " + results[i]);
						}else {
							System.out.println("Ha ganado: " + results[i]);
						}
					}
					
					skInterface.receive(bMsg.S_CONTINUAR);
					skInterface.send(bMsg.ACK);	
					while (asking) {
						System.out.println("CL => QUIERES CONTIUAR? ( SI O NO )");
						userInp = kyb.nextLine().toLowerCase();
						
						switch (userInp) {
							case "si":
								asking = false;
								break;
							
							case "no":
								asking = false;
								break;
							
							default:
								System.out.println("ERES TONTO O QUE, SI O NO");
								break;
						}
					}
					
					if (userInp.equals("no")) {
						lobby = false;
						skInterface.send(bMsg.C_PLEGAR); 
					}else {
						skInterface.send(bMsg.C_SEGUIR);
					}
				}
				else if (response == bMsg.S_EN_CURS) {
					System.out.println("CL => LA PARTIDA YA ESTA EN CURSO");
					skInterface.send(bMsg.ACK);
				}
			}
			skInterface.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		} catch (WrongProtocolException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
