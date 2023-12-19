package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketInterface {

	boolean verbose;
	PrintWriter out;
	BufferedReader inSocket;

	public SocketInterface(Socket cSkt) { // CONTRUCTOR - sin verbose
		try {
			this.out = new PrintWriter(cSkt.getOutputStream(), true);
			this.inSocket = new BufferedReader(new InputStreamReader(cSkt.getInputStream()));
			this.verbose = true;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SocketInterface(Socket cSkt, boolean vrbs) { // CONTRUCTOR - con verbose

		try {
			this.out = new PrintWriter(cSkt.getOutputStream(), true);
			this.inSocket = new BufferedReader(new InputStreamReader(cSkt.getInputStream()));
			this.verbose = vrbs;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void send(String text) {
		out.println(text);
	}

	public String receive() {

		String rec = "";

		try {
			rec = inSocket.readLine();
			System.out.println("HE REBUT: " + rec);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rec;
	}
	
	public void receive(String text) {
		String rec = "";

		try {
			rec = inSocket.readLine();
			CompareRecive(rec, text);
			System.out.println("HE REBUT: " + rec);

		} catch (IOException e) {
			e.printStackTrace();

		} catch (WrongProtocolException e) {
			e.printStackTrace();
		}
	}

	public void close() {

		try {
			this.out.close();
			this.inSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String sendReceive(String text) {
		String res = "";

		send(text);
		res = receive();

		return res;
	}

	public void sendReceive(String send, String receive) {
		String res = "";

		send(send);
		receive(receive);
	}

	private void CompareRecive(String rec, String excepted) throws WrongProtocolException {

		if (!rec.equals(excepted)) {
			throw new WrongProtocolException("ERROR, ELS VALORS NO COINCIDEIXEN");
		}
	}
}
