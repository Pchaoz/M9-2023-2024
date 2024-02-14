package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import Util.BinaryMessage;
import Util.SocketInterface;
import Util.WrongProtocolException;

public class Server {

	//EL NUMERO DE PUERTO DEL SERVER
	private static final int PUERTO = 60047;

	//EL NUMERO MINIMO DE JUGADORERS PARA EMEPZAR LA PARTIDA
    private static final int MIN_PLAYERS = 2;
    private static final CountDownLatch playersReadyCD = new CountDownLatch(MIN_PLAYERS);
    private static final CountDownLatch gameStartCD = new CountDownLatch(1);
    private static final Set<String> nicknames = new HashSet<>();
	private static final Semaphore gameSemaphore = new Semaphore(1);
	


	public static void main(String[] args){

		BinaryMessage bMsg = new BinaryMessage();

		try {
			ServerSocket serverSocket = new ServerSocket(PUERTO);
			System.out.println("S => Esperando jugadores...");

			List<SocketInterface> clientes = new ArrayList<>();

			while (true) {
				serverSocket.setSoTimeout(0);
				Socket clienteSocket = serverSocket.accept();

				gameSemaphore.acquire();
				System.out.println("S => Jugador conectado.");
				Thread playerHandler = new Thread(new PlayerHandler(clienteSocket, playersReadyCD, gameStartCD, nicknames, gameSemaphore));
				playerHandler.start();
				clientes.add(new SocketInterface(clienteSocket, true));

				clientes.get(clientes.size()-1).send(bMsg.S_BENVINGUT);
				clientes.get(clientes.size() - 1).receive(bMsg.ACK);

				String newNickname;

				while(true) {
					newNickname = clientes.get(clientes.size()-1).reciveString();

					if (!nicknames.contains(newNickname)) {
						nicknames.add(newNickname);
						break;
					}else {
						clientes.get(clientes.size()-1).send(bMsg.S_NICK_EN_US);
					}
				}
				clientes.get(clientes.size()-1).send(bMsg.ACK);

				playersReadyCD.countDown(); //AVISA DE QUE EL CLIENT ESTA PREPARAT
			}

			
		}catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		} catch (WrongProtocolException e) {
			e.printStackTrace();
		}
	}
}
