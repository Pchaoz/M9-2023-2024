package Util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketInterface {

	boolean verbose;
	DataOutputStream dataOut;
	DataInputStream dataIn;
	PrintWriter outText;
	BufferedReader inText;
	BinaryMessage bnMsg = new BinaryMessage();

	public SocketInterface(Socket cSkt) throws IOException { // CONTRUCTOR - sin verbose
		
		outText = new PrintWriter(cSkt.getOutputStream(), true);
		inText = new BufferedReader(new InputStreamReader(cSkt.getInputStream()));
		dataOut = new DataOutputStream(cSkt.getOutputStream());
		dataIn = new DataInputStream(cSkt.getInputStream());
		verbose = true;
	}

	public SocketInterface(Socket cSkt, boolean vrbs) throws IOException { // CONTRUCTOR - con verbose

		outText = new PrintWriter(cSkt.getOutputStream(), true);
		inText = new BufferedReader(new InputStreamReader(cSkt.getInputStream()));
		dataOut = new DataOutputStream(cSkt.getOutputStream());
		dataIn = new DataInputStream(cSkt.getInputStream());
		verbose = vrbs;
	}
	

	public void sendString(String text) throws IOException {
		
		outText.println(text);
	}
	
	public String reciveString() throws IOException {
		
		String str = inText.readLine();
		return str;
	}
	
	public void send(byte text) throws IOException{
		dataOut.writeByte(text);
	}

	public byte receive() throws WrongProtocolException, IOException{

		byte rec = dataIn.readByte();
		
		if(verbose)
			System.out.println("HE RECIVIDO: " + bnMsg.ByteToString(rec));
		
		return rec;
	}
	
	public void receive(byte text)  throws WrongProtocolException, IOException{
		byte rec = dataIn.readByte();
		
		if (!(text == rec)) {
			throw new WrongProtocolException("ERROR, ELS VALORS NO COINCIDEIXEN");
		}
		System.out.println("HE REBUT: " + rec);
	}

	public void close() {

		try {
			this.dataIn.close();
			this.dataOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte sendReceive(byte text) throws WrongProtocolException, IOException {
		

		send(text);
		byte res = receive();

		return res;
	}

	public void sendReceive(byte send, byte receive) throws WrongProtocolException, IOException {
		
		send(send);
		receive(receive);
	}

}