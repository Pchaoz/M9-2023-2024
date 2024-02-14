package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import Util.SocketInterface;

public class PlayerHandler implements Runnable {

	private SocketInterface skInterface;
	private CountDownLatch playersReadyCD;
	private CountDownLatch gameStartCD;
	private Set<String> nicknames;
	private Semaphore gameSemaphore;
	private boolean oneEliminated;

	public PlayerHandler(Socket clienteSocket, CountDownLatch playersreadycd, CountDownLatch gamestartcd,
			Set<String> nicknames, Semaphore gamesemaphore) throws IOException {

		this.skInterface = new SocketInterface(clienteSocket, true);
		this.playersReadyCD = playersreadycd;
		this.gameStartCD = gamestartcd;
		this.nicknames = nicknames;
		this.gameSemaphore = gamesemaphore;
	}

	@Override
	public void run() {
		try {
			playersReadyCD.await(); //ESPERA A QUE HAYA DOS CLIENTES CONECTADOS

			countdown(); //CREA UNA CUENTA ATRAS A TODOS LOS CLIENTES
			
			startGame(); //EMPIEZA LA PARTIDA

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void countdown() throws InterruptedException {

		for (int i = 5; i > 0; i--) {
			System.out.println("Iniciando partida en " + i + " segundos...");
			Thread.sleep(1000);
		}
		
		System.out.println("Empieza la partida");
		gameStartCD.countDown();

	}

	private void startGame() throws InterruptedException {

		playersReadyCD.await();

		oneEliminated = false;
		
	}
}
