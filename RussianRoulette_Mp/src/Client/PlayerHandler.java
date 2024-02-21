package Client;

import java.io.IOException;
import java.io.ObjectInputFilter.Status;
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

    GameHandler gh;

    public PlayerHandler(Socket skt, GameHandler gh) {
        kyb = new Scanner(System.in);
        bMsg = new BinaryMessage();

        this.skt = skt;
        this.gh = gh;
    }

    @Override
    public void run() {
        try {
            System.out.println(
                Thread.currentThread().getName() + ": Connection established with " + skt.getInetAddress()
                        + " at port " + skt.getPort() + " and local port " + skt.getLocalPort());

            sktInter = new SocketInterface(skt, false);

            sktInter.receive(bMsg.S_BENVINGUT);
            sktInter.send(bMsg.ACK);

            while(true) {
                input = kyb.nextLine();
                if (!gh.checkNickname()) {
                    nickname = input;
                    break;
                }else {
                    sktInter.send(bMsg.S_NICK_EN_US);
                }
            }
            sktInter.send(bMsg.ACK);
            while(true) {

                state = States.WAITING;
                gh.readPlayerStatus()

                if (gh.getState() == States.WAITING_PLAYERS) {
                    gh.addPlayer(this);
                    
                    sktInter.receive(bMsg.S_ESTAS_DINS);
                    sktInter.send(bMsg.ACK);
                    state = States.INGAME;
                    
                    synchronized (this) {
                        this.wait();
                    }
                    while(true) {
                        //LOGICA RECRIBIENDO BALAS
                    }
                }
            }

            

        }catch(IOException e) {
            e.printStackTrace();
        }catch(WrongProtocolException e) {
            e.printStackTrace();
        }
       
    }

    public States GetState() {
        return this.state;
    }
    
}
