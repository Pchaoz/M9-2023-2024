package Server;

import java.util.HashSet;
import java.util.Set;

import Client.PlayerHandler;
import Util.States;

public class GameHandler implements Runnable {

	private States state;

	private static final int MIN_PLAYERS = 2;
	private static final int TIME_TO_START = 5;
	
	private Set<PlayerHandler> playersReady;
	private Set<PlayerHandler> playersLobby;
	private Set<PlayerHandler> playersIngame;
	
	public GameHandler(){

		playersLobby = new HashSet<PlayerHandler>();
		playersReady = new HashSet<PlayerHandler>();
		playersIngame = new HashSet<PlayerHandler>();

		state = States.WAITING_PLAYERS;
	}
	

	@Override
	public void run() {
		while(true) {
			synchronized(playersReady) {
				playersReady.notify();
			}
			if (playersReady.size() >= MIN_PLAYERS) {
				System.out.println("TWO PLAYERS READY.. STARTING GAME IN 5 SECONDS!");
				try {
					Thread.sleep(TIME_TO_START * 1000); //5 SEGUNDOS
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("STARTING GAME..");
				playersIngame.addAll(playersReady);
				playersReady.clear();
				
			}
		}
	}

	public void AddPlayer(PlayerHandler ph) {

		if (ph.GetState() == States.CREATED) {
			playersLobby.add(ph);
		}else if(ph.GetState() == States.WAITING){
			playersReady.add(ph);
		}
	}
}
