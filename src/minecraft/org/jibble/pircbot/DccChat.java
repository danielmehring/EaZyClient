package org.jibble.pircbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class DccChat {

    public static final int EaZy = 1974;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final PircBot _bot;
    private final String _nick;
    private String _login = null;
    private String _hostname = null;
    private BufferedReader _reader;
    private BufferedWriter _writer;
    private Socket _socket;
    private boolean _acceptable;
    private long _address = 0;
    private int _port = 0;

    DccChat(final PircBot bot, final String nick, final String login, final String hostname, final long address,
            final int port) {
        _bot = bot;
        _address = address;
        _port = port;
        _nick = nick;
        _login = login;
        _hostname = hostname;
        _acceptable = true;
    }

    DccChat(final PircBot bot, final String nick, final Socket socket) throws IOException {
        _bot = bot;
        _nick = nick;
        _socket = socket;
        _reader = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        _writer = new BufferedWriter(new OutputStreamWriter(_socket.getOutputStream()));
        _acceptable = false;
    }

    public synchronized void accept() throws IOException {
        if (_acceptable) {
            _acceptable = false;
            final int[] ip = _bot.longToIp(_address);
            final String ipStr = String.valueOf(ip[0]) + "." + ip[1] + "." + ip[2] + "." + ip[3];
            _socket = new Socket(ipStr, _port);
            _reader = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            _writer = new BufferedWriter(new OutputStreamWriter(_socket.getOutputStream()));
        }
    }

    public String readLine() throws IOException {
        if (_acceptable) {
            throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
        }
        return _reader.readLine();
    }

    public void sendLine(final String line) throws IOException {
        if (_acceptable) {
            throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
        }
        _writer.write(String.valueOf(line) + "\r\n");
        _writer.flush();
    }

    public void close() throws IOException {
        if (_acceptable) {
            throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
        }
        _socket.close();
    }

    public String getNick() {
        return _nick;
    }

    public String getLogin() {
        return _login;
    }

    public String getHostname() {
        return _hostname;
    }

    public BufferedReader getBufferedReader() {
        return _reader;
    }

    public BufferedWriter getBufferedWriter() {
        return _writer;
    }

    public Socket getSocket() {
        return _socket;
    }

    public long getNumericalAddress() {
        return _address;
    }
}
