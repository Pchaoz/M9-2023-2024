package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketInterface {

	boolean verbose;
	PrintWriter out;
	BufferedReader inSocket;
	ObjectOutputStream objOut;
	ObjectInputStream objIn;

	public SocketInterface(Socket cSkt) throws IOException { // CONTRUCTOR - sin verbose
		
		out = new PrintWriter(cSkt.getOutputStream(), true);
		inSocket = new BufferedReader(new InputStreamReader(cSkt.getInputStream()));
		objOut = new ObjectOutputStream(cSkt.getOutputStream());
		objIn = new ObjectInputStream(cSkt.getInputStream());
		verbose = true;
		
	}

	public SocketInterface(Socket cSkt, boolean vrbs) throws IOException { // CONTRUCTOR - con verbose

		out = new PrintWriter(cSkt.getOutputStream(), true);
		inSocket = new BufferedReader(new InputStreamReader(cSkt.getInputStream()));
		objOut = new ObjectOutputStream(cSkt.getOutputStream());
		objIn = new ObjectInputStream(cSkt.getInputStream());
		verbose = vrbs;
	}
	

	public void send(String text) {
		out.println(text);
	}

	public String receive() throws WrongProtocolException, IOException{

		String rec = "";
		rec = inSocket.readLine();
		System.out.println("HE REBUT: " + rec);

		return rec;
	}
	
	public void receive(String text)  throws WrongProtocolException, IOException{
		String rec = inSocket.readLine();
		
		if (!rec.equals(text)) {
			throw new WrongProtocolException("ERROR, ELS VALORS NO COINCIDEIXEN");
		}
		System.out.println("HE REBUT: " + rec);
	}
	
	public Object recieveOject () throws IOException, ClassNotFoundException {
		Object o = objIn.readObject();
		return o;
	}
	
	public void sendObj( Object o ) throws IOException {
		
		objOut.writeObject(o);
	}

	public void close() {

		try {
			this.out.close();
			this.inSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String sendReceive(String text) throws WrongProtocolException, IOException {
		String res = "";

		send(text);
		res = receive();

		return res;
	}

	public void sendReceive(String send, String receive) throws WrongProtocolException, IOException {
		String res = "";

		send(send);
		receive(receive);
	}

}