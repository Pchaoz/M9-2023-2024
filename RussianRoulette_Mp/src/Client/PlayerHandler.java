package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import Server.GameHandler;
import Util.BinaryMessage;
import Util.SocketInterface;
import Util.States;
import Util.WrongProtocolException;

public class PlayerHandler implements Runnable{

    Scanner kyb;
    BinaryMessage bMsg;
    
    String nickname;
    String input;
    States state;
    
    SocketInterface sktInter;
    Socket skt;
    RuletaClient rcl;

    GameHandler gh;

    String[] results;

    public PlayerHandler(Socket skt, GameHandler gh) {
        kyb = new Scanner(System.in);
        bMsg = new BinaryMessage();

        this.skt = skt;
        this.gh = gh;

        this.state = States.CREATED;
    }

    @Override
    public void run() {
        try {
            System.out.println(
                Thread.currentThread().getName() + ": Connection established with " + skt.getInetAddress()
                        + " at port " + skt.getPort() + " and local port " + skt.getLocalPort());

            sktInter = new SocketInterface(skt, false);
            sktInter.send(bMsg.S_BENVINGUT);
            sktInter.receive(bMsg.ACK);
            
            while(true) {
                input = sktInter.reciveString();
                if (!gh.CheckNickname(input)) {
                    nickname = input;
                    break;
                }else {
                    sktInter.send(bMsg.S_NICK_EN_US);
                }
            }
            sktInter.send(bMsg.ACK);
            while(true) {

                state = States.WAITING;
                if (gh.GetState() == States.WAITING_PLAYERS) {

                    gh.AddPlayer(this);

                    sktInter.send(bMsg.S_ESTAS_DINS);
                    sktInter.receive(bMsg.ACK);

                    state = States.INGAME;
                    synchronized (this) {
                        this.wait();
                    }
                    while(true) {
                        if (state == States.DEAD) {
                            //TE MUERES
                            System.out.println("CL -> " + Thread.currentThread().getName() +  " HAS RECIBIDO UN BALAZO CRACK");
                            sktInter.send(bMsg.S_BALA);
                            sktInter.receive();
                            break;
                        }else if (state == States.INGAME) {
                            //TE SALVAS
                            System.out.println("CL -> " + Thread.currentThread().getName() +  " TE SALVAS DE MOMENTO");
                            sktInter.send(bMsg.S_NO_BALA);
                            sktInter.receive();
                            synchronized (this) {
								this.wait();
							}

                        }else if (state == States.WINNER) {
                            //GANAS LA PARTIDA
                            System.out.println("CL -> " + Thread.currentThread().getName() +  " HAS GANADO!!");
                            sktInter.send(bMsg.S_FINALISTA);
                            sktInter.receive();
                            break;
                        }
                    }
                }
            }

        }catch(IOException e) {
            e.printStackTrace();
        }catch(WrongProtocolException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       
    }

    public States GetState() {
        return this.state;
    }
    public void SetState(States st) {
        this.state = st;
    }
    public String GetNickname() {
        return this.nickname;
    }
    public void SetNickname(String n) {
        this.nickname = n;
    }

    public void setResults(String[] r) {
        this.results = r;
    }
}
