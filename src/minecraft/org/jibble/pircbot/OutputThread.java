package org.jibble.pircbot;

import java.io.BufferedWriter;

public class OutputThread extends Thread {

    public static final int EaZy = 1981;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private PircBot _bot = null;
    private Queue _outQueue = null;

    OutputThread(final PircBot bot, final Queue outQueue) {
        _bot = bot;
        _outQueue = outQueue;
        setName("EaZyChat");
    }

    static void sendRawLine(final PircBot bot, final BufferedWriter bwriter, String line) {
        if (line.length() > bot.getMaxLineLength() - 2) {
            line = line.substring(0, bot.getMaxLineLength() - 2);
        }
        final BufferedWriter bufferedWriter = bwriter;
        synchronized (bufferedWriter) {
            try {
                bwriter.write(String.valueOf(line) + "\r\n");
                bwriter.flush();
                bot.log(">>>" + line);
            } catch (final Exception var4_4) {
                // empty catch block
            }
        }
    }

    @Override
    public void run() {
        try {
            boolean running = true;
            while (running) {
                Thread.sleep(_bot.getMessageDelay());
                final String line = (String) _outQueue.next();
                if (line != null) {
                    _bot.sendRawLine(line);
                    continue;
                }
                running = false;
            }
        } catch (final InterruptedException running) {
            // empty catch block
        }
    }
}
