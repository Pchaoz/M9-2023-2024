package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import Util.SocketInterface;

public class PlayerHandler implements Runnable{

	private SocketInterface skInterface;
    private CountDownLatch playersReadyCD;
    private CountDownLatch gameStartCD;
    private Set<String> nicknames;
    private Semaphore gameSemaphore;

	public PlayerHandler(Socket clienteSocket, CountDownLatch playersreadycd, CountDownLatch gamestartcd, Set<String> nicknames, Semaphore gamesemaphore) throws IOException {

		this.skInterface = new SocketInterface(clienteSocket, true);
        this.playersReadyCD = playersreadycd;
        this.gameStartCD = gamestartcd;
        this.nicknames = nicknames;
        this.gameSemaphore = gamesemaphore;
	}

	@Override
	public void run() {
		try {
			playersReadyCD.await();


		}catch (InterruptedException e) {
            e.printStackTrace();

		}
		
	}

}
