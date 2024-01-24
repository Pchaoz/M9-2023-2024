package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Util.BinaryMessage;
import Util.SocketInterface;
import Util.WrongProtocolException;

public class Server {
	
	public static void main(String[] args) {
		
		List<String> player_list = new ArrayList<String>();
		
		//SOCKETS
		ServerSocket ssk;
		Socket csk;
		
		//SOCKET INTERFACE
		SocketInterface skInter = null;
		
		//READERS
		DataOutputStream dataOut;
		DataInputStream dataIn;

		//HOSTNAME AND PORT
		final String hostName = "127.0.0.1";
		final int port = 5000;
		
		//OTHERS
		String userMsg = "";
		BinaryMessage bMsg = new BinaryMessage();
		boolean inGame = false;
		Random rng = new Random();
		
		
		try {
			ssk = new ServerSocket(port);
			
			while(true) {
				System.out.println("S -> WAITING FOR PLAYERS..");
				csk = ssk.accept(); //ESPERA Y CONECTA AL CLIENTE
				skInter = new SocketInterface(csk);
				System.out.println("S -> A CLIENT CONNECTED SUCCESEFULLY..");
				skInter.sendReceive(bMsg.S_BENVINGUT, bMsg.ACK);
				
				while (true) {
					System.out.println("S -> ASKING HIS NICKNAME..");
					userMsg = skInter.reciveString();
					if (checkIsAvaible(userMsg.toLowerCase(), player_list)) {
						player_list.add(userMsg.toLowerCase());
						skInter.send(bMsg.ACK);
						break;
					}else {
						skInter.send(bMsg.S_NICK_EN_US);
					}
				}
				
			} 
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}catch (WrongProtocolException e) {
			e.printStackTrace();
		}
		finally {
			skInter.close();
		}
	}
	/*
	 * RETURNS TRUE IF NOT IN THE LIST
	 */
	private static boolean checkIsAvaible(String n, List<String> player_list) {
		
		if (player_list.size() < 1) {
			return true;
		}
		for (String player : player_list) {
			if (player.equals(n)) {
				return false;
			}
		}
		return true;
	}
	
}