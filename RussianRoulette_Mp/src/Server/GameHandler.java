package Server;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;

import Client.PlayerHandler;
import Util.States;

public class GameHandler implements Runnable {

	private States state;

	private static final int MIN_PLAYERS = 2; //MIN PLAYERS TO START THE GAME
	private static final int TIME_TO_START = 5000; //5 SECONDS
	
	private Set<PlayerHandler> playersReady;
	private Set<PlayerHandler> playersLobby;
	private Set<PlayerHandler> playersInGame;
	private Set<PlayerHandler> playersDead;
	
	public GameHandler(){

		playersLobby = new HashSet<PlayerHandler>();
		playersReady = new HashSet<PlayerHandler>();
		playersInGame = new HashSet<PlayerHandler>();
		playersDead = new HashSet<PlayerHandler>();

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
					Thread.sleep(TIME_TO_START);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("STARTING GAME..");
				playersInGame.addAll(playersReady);
				playersReady.clear();
				playGame();
			}
		}
	}

	private void playGame() {
		int playersLeft = playersInGame.size();
		int rounds = 0;
		int playerToDie;

		Boolean ActiveGame = true;
		Random rng = new Random();
		
		String[] results = new String[playersInGame.size()];

		while(ActiveGame) {
			if (playersInGame.size() > 1) {
				playerToDie = rng.nextInt(playersInGame.size()-1);

				Iterator<PlayerHandler> it = playersInGame.iterator();
				int iteratorCount = 0;
				while(it.hasNext()) {
					PlayerHandler ph = it.next();
					if (playersInGame.size() == 1) {
						//IF THERE'S JUST ONE PLAYER LEFT, HE ATOMATICALLY WINS
						ph.SetState(States.WINNER);
						results[rounds] = ph.GetNickname();
						ActiveGame = false;
						//NOTIFIES THE PLAYER AFFECTED
						synchronized(ph) {
							ph.notify();
						}
					}
					else {
						//GAME WORKS AS USUALLY
						if (iteratorCount == playerToDie) {
							//THIS PLAYER DIES
							ph.SetState(States.DEAD);
							playersLeft--;
							results[rounds] = ph.GetNickname();
							playersDead.add(ph);

							//NOTIFIES THE PLAYER AFFECTED
							synchronized(ph) {
								ph.notify();
							}
						}else {
							//NOTHING HAPPENS
							synchronized(ph) {
								ph.notify();
							}
						}
					}
				}
				iteratorCount++;
			}
			//METHOD TO CHECK IF SOMEWONE DC OR DIED
			roundFinished();
		}
		playersInGame.clear();

		Iterator<PlayerHandler> it = playersDead.iterator();
		while (it.hasNext()) {
			PlayerHandler ph = it.next();
			ph.setResults(results);
			synchronized(ph) {
				ph.notify();
			}
		}
		playersDead.clear();
	}

	/*
	 * IF THE PLAYER DESCONECTS OR DIES GETS REMOVED FROM THE LIST
	 */
	private void roundFinished() {
		Iterator<PlayerHandler> it = playersInGame.iterator();
		while(it.hasNext()) {
			PlayerHandler ph = it.next();
			if (ph.GetState() == States.DEAD || ph.GetState() == States.DISCONECTED) {
				playersInGame.remove(ph);
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

	public States GetState() {
		return this.state;
	}

	//TODO ESTO ESTA DE PRUEBA SE HA CAMBIAR DESPUES
	public Boolean CheckNickname(String n) {
		return true;
	}
}
