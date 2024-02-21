package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Client.PlayerHandler;

public class RuletaServer {
	
	public static ArrayList<String> nicknames = new ArrayList<String>();

	public static void main(String[] args) {
		int port = 60047;

		try {

			ServerSocket sSkt = new ServerSocket(port);
			ExecutorService exe = Executors.newCachedThreadPool();
			GameHandler gh = new GameHandler();
			exe.execute(gh);  //EXECUTES A GAME
			while(true) {
				System.out.println("S -> WAITING PLAYERS TO CONNECT..");

				//A PLAYER CONNECTS
				Socket skt = sSkt.accept();
				PlayerHandler ph = new PlayerHandler(skt, gh);
				exe.execute(ph);
				gh.AddPlayer(ph);

			}

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * THIS METHOD CHECKS IS A NICKNAME IS ON THE SERVER NICKNAME LIST
	 * @return TRUE IF THE NICKNAME IS ON THE LIST IF NOT RETURNS FALSE
	 */
	public static Boolean CheckNickname(String name) {
		for (String n : nicknames) {
			if (n.equals(name)) return true;
		}
		nicknames.add(name); //ADDS NICKNAME IF AVAIBLE
		return false;
	}
	
}
