package org.jibble.pircbot;

import java.util.StringTokenizer;
import java.util.Vector;

public class DccManager {

    public static final int EaZy = 1976;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final PircBot _bot;
    private final Vector _awaitingResume = new Vector();

    DccManager(final PircBot bot) {
        _bot = bot;
    }

    boolean processRequest(final String nick, final String login, final String hostname, final String request) {
        final StringTokenizer tokenizer = new StringTokenizer(request);
        tokenizer.nextToken();
        final String type = tokenizer.nextToken();
        final String filename = tokenizer.nextToken();
        switch (type) {
            case "SEND": {
                final long address = Long.parseLong(tokenizer.nextToken());
                final int port = Integer.parseInt(tokenizer.nextToken());
                long size = -1;
                try {
                    size = Long.parseLong(tokenizer.nextToken());
                } catch (final Exception var13_18) {
                    // empty catch block
                }
                final DccFileTransfer transfer = new DccFileTransfer(_bot, this, nick, login, hostname, type, filename,
                        address, port, size);
                _bot.onIncomingFileTransfer(transfer);
                break;
            }
            case "RESUME": {
                final int port = Integer.parseInt(tokenizer.nextToken());
                final long progress = Long.parseLong(tokenizer.nextToken());
                DccFileTransfer transfer = null;
                final Vector vector = _awaitingResume;
                synchronized (vector) {
                    int i = 0;
                    while (i < _awaitingResume.size()) {
                        transfer = (DccFileTransfer) _awaitingResume.elementAt(i);
                        if (transfer.getNick().equals(nick) && transfer.getPort() == port) {
                            _awaitingResume.removeElementAt(i);
                            break;
                        }
                        ++i;
                    }
                }
                if (transfer != null) {
                    transfer.setProgress(progress);
                    _bot.sendCTCPCommand(nick, "DCC ACCEPT file.ext " + port + " " + progress);
                }
                break;
            }
            case "ACCEPT": {
                final int port = Integer.parseInt(tokenizer.nextToken());
                Long.parseLong(tokenizer.nextToken());
                DccFileTransfer transfer = null;
                final Vector vector = _awaitingResume;
                synchronized (vector) {
                    int i = 0;
                    while (i < _awaitingResume.size()) {
                        transfer = (DccFileTransfer) _awaitingResume.elementAt(i);
                        if (transfer.getNick().equals(nick) && transfer.getPort() == port) {
                            _awaitingResume.removeElementAt(i);
                            break;
                        }
                        ++i;
                    }
                }
                if (transfer != null) {
                    transfer.doReceive(transfer.getFile(), true);
                }
                break;
            }
            case "CHAT": {
                final long address = Long.parseLong(tokenizer.nextToken());
                final int port = Integer.parseInt(tokenizer.nextToken());
                final DccChat chat = new DccChat(_bot, nick, login, hostname, address, port);
                new Thread() {

                    @Override
                    public void run() {
                        _bot.onIncomingChatRequest(chat);
                    }
                }.start();
                break;
            }
            default:
                return false;
        }
        return true;
    }

    void addAwaitingResume(final DccFileTransfer transfer) {
        final Vector vector = _awaitingResume;
        synchronized (vector) {
            _awaitingResume.addElement(transfer);
        }
    }

    void removeAwaitingResume(final DccFileTransfer transfer) {
        _awaitingResume.removeElement(transfer);
    }

}
