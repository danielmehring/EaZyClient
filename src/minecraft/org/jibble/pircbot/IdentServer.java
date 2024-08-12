package org.jibble.pircbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class IdentServer extends Thread {

	public static final int EaZy = 1977;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	private PircBot _bot;
	private String _login;
	private ServerSocket _ss = null;

	IdentServer(final PircBot bot, final String login) {
		_bot = bot;
		_login = login;
		try {
			_ss = new ServerSocket(113);
			_ss.setSoTimeout(60000);
		} catch (final Exception e) {
			_bot.log("*** Could not start the ident server on port 113.");
			return;
		}
		_bot.log("*** Ident server running on port 113 for the next 60 seconds...");
		setName("EaZy-Chat");
		start();
	}

	@Override
	public void run() {
		try {
			final Socket socket = _ss.accept();
			socket.setSoTimeout(60000);
			final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String line = reader.readLine();
			if (line != null) {
				_bot.log("*** Ident request received: " + line);
				line = String.valueOf(line) + " : USERID : UNIX : " + _login;
				writer.write(String.valueOf(line) + "\r\n");
				writer.flush();
				_bot.log("*** Ident reply sent: " + line);
				writer.close();
			}
		} catch (final Exception socket) {
			// empty catch block
		}
		try {
			_ss.close();
		} catch (final Exception socket) {
			// empty catch block
		}
		_bot.log("*** The Ident server has been shut down.");
	}
}
