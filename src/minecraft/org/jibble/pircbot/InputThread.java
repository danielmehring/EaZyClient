package org.jibble.pircbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class InputThread extends Thread {

    public static final int EaZy = 1978;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private PircBot _bot = null;
    private Socket _socket = null;
    private BufferedReader _breader = null;
    private BufferedWriter _bwriter = null;
    private boolean _isConnected = true;
    private boolean _disposed = false;
    public static final int MAX_LINE_LENGTH = 512;

    InputThread(final PircBot bot, final Socket socket, final BufferedReader breader, final BufferedWriter bwriter) {
        _bot = bot;
        _socket = socket;
        _breader = breader;
        _bwriter = bwriter;
        setName("IRC-Thread");
    }

    void sendRawLine(final String line) {
        OutputThread.sendRawLine(_bot, _bwriter, line);
    }

    boolean isConnected() {
        return _isConnected;
    }

    @SuppressWarnings("unused")
    @Override
    public void run() {
        try {
            boolean running = true;
            while (running) {
                try {
                    String line = null;
                    while ((line = _breader.readLine()) != null) {
                        try {
                            _bot.handleLine(line);
                        } catch (final Throwable t) {
                            final StringWriter sw = new StringWriter();
                            final PrintWriter pw = new PrintWriter(sw);
                            t.printStackTrace(pw);
                            pw.flush();
                            final StringTokenizer tokenizer = new StringTokenizer(sw.toString(), "\r\n");
                            final PircBot pircBot = _bot;
                            synchronized (pircBot) {
                                _bot.log("### Your implementation of PircBot is faulty and you have");
                                _bot.log("### allowed an uncaught Exception or Error to propagate in your");
                                _bot.log("### code. It may be possible for PircBot to continue operating");
                                _bot.log("### normally. Here is the stack trace that was produced: -");
                                _bot.log("### ");
                                while (tokenizer.hasMoreTokens()) {
                                    _bot.log("### " + tokenizer.nextToken());
                                }
                            }
                        }
                    }
                    if (line != null) {
                        continue;
                    }
                    running = false;
                } catch (final InterruptedIOException iioe) {
                    sendRawLine("PING " + System.currentTimeMillis() / 1000);
                }
            }
        } catch (final Exception running) {
            // empty catch block
        }
        try {
            _socket.close();
        } catch (final Exception running) {
            // empty catch block
        }
        if (!_disposed) {
            _bot.log("*** Disconnected.");
            _isConnected = false;
            _bot.onDisconnect();
        }
    }

    public void dispose() {
        try {
            _disposed = true;
            _socket.close();
        } catch (final Exception var1_1) {
            // empty catch block
        }
    }
}
