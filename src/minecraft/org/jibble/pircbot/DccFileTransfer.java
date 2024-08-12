package org.jibble.pircbot;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DccFileTransfer {

    public static final int EaZy = 1975;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static final int BUFFER_SIZE = 1024;
    private final PircBot _bot;
    private final DccManager _manager;
    private final String _nick;
    private String _login = null;
    private String _hostname = null;
    private String _type;
    private long _address;
    private int _port;
    private final long _size;
    private boolean _received;
    private Socket _socket = null;
    private long _progress = 0;
    private File _file = null;
    private int _timeout = 0;
    private final boolean _incoming;
    private long _packetDelay = 0;
    private long _startTime = 0;

    DccFileTransfer(final PircBot bot, final DccManager manager, final String nick, final String login,
            final String hostname, final String type, final String filename, final long address, final int port,
            final long size) {
        _bot = bot;
        _manager = manager;
        _nick = nick;
        _login = login;
        _hostname = hostname;
        _type = type;
        _file = new File(filename);
        _address = address;
        _port = port;
        _size = size;
        _received = false;
        _incoming = true;
    }

    DccFileTransfer(final PircBot bot, final DccManager manager, final File file, final String nick,
            final int timeout) {
        _bot = bot;
        _manager = manager;
        _nick = nick;
        _file = file;
        _size = file.length();
        _timeout = timeout;
        _received = true;
        _incoming = false;
    }

    public synchronized void receive(final File file, final boolean resume) {
        if (!_received) {
            _received = true;
            _file = file;
            if (_type.equals("SEND") && resume) {
                _progress = file.length();
                if (_progress == 0) {
                    doReceive(file, false);
                } else {
                    _bot.sendCTCPCommand(_nick, "DCC RESUME file.ext " + _port + " " + _progress);
                    _manager.addAwaitingResume(this);
                }
            } else {
                _progress = file.length();
                doReceive(file, resume);
            }
        }
    }

    void doReceive(final File file, final boolean resume) {
        new Thread() {

            @Override
            public void run() {
                Exception exception;
                block12:
                {
                    FilterOutputStream foutput;
                    foutput = null;
                    exception = null;
                    try {
                        try {
                            final int[] ip = _bot.longToIp(_address);
                            final String ipStr = String.valueOf(ip[0]) + "." + ip[1] + "." + ip[2] + "." + ip[3];
                            DccFileTransfer.access$4(DccFileTransfer.this, new Socket(ipStr, _port));
                            _socket.setSoTimeout(30000);
                            DccFileTransfer.access$5(DccFileTransfer.this, System.currentTimeMillis());
                            _manager.removeAwaitingResume(DccFileTransfer.this);
                            final BufferedInputStream input = new BufferedInputStream(_socket.getInputStream());
                            final BufferedOutputStream output = new BufferedOutputStream(_socket.getOutputStream());
                            foutput = new BufferedOutputStream(new FileOutputStream(file.getCanonicalPath(), resume));
                            final byte[] inBuffer = new byte[1024];
                            final byte[] outBuffer = new byte[4];
                            int bytesRead = 0;
                            while ((bytesRead = input.read(inBuffer, 0, inBuffer.length)) != -1) {
                                foutput.write(inBuffer, 0, bytesRead);
                                final DccFileTransfer dccFileTransfer = DccFileTransfer.this;
                                DccFileTransfer.access$8(dccFileTransfer, dccFileTransfer._progress + bytesRead);
                                outBuffer[0] = (byte) (_progress >> 24 & 255);
                                outBuffer[1] = (byte) (_progress >> 16 & 255);
                                outBuffer[2] = (byte) (_progress >> 8 & 255);
                                outBuffer[3] = (byte) (_progress & 255);
                                output.write(outBuffer);
                                output.flush();
                                DccFileTransfer.this.delay();
                            }
                            foutput.flush();
                        } catch (final Exception e) {
                            exception = e;
                            try {
                                foutput.close();
                                _socket.close();
                            } catch (final Exception var11_11) {
                            }
                            break block12;
                        }
                    } catch (final Throwable var10_14) {
                        try {
                            foutput.close();
                            _socket.close();
                        } catch (final Exception var11_12) {
                            // empty catch block
                        }
                        throw var10_14;
                    }
                    try {
                        foutput.close();
                        _socket.close();
                    } catch (final Exception var11_13) {
                        // empty catch block
                    }
                }
                _bot.onFileTransferFinished(DccFileTransfer.this, exception);
            }
        }.start();
    }

    void doSend(final boolean allowResume) {
        new Thread() {

            @Override
            public void run() {
                Exception exception;
                block23:
                {
                    BufferedInputStream finput;
                    finput = null;
                    exception = null;
                    try {
                        try {
                            ServerSocket ss = null;
                            final int[] ports = _bot.getDccPorts();
                            if (ports == null) {
                                ss = new ServerSocket(0);
                            } else {
                                int i = 0;
                                while (i < ports.length) {
                                    try {
                                        ss = new ServerSocket(ports[i]);
                                        break;
                                    } catch (final Exception var6_8) {
                                        ++i;
                                    }
                                }
                                if (ss == null) {
                                    throw new IOException("All ports returned by getDccPorts() are in use.");
                                }
                            }
                            ss.setSoTimeout(_timeout);
                            DccFileTransfer.access$11(DccFileTransfer.this, ss.getLocalPort());
                            InetAddress inetAddress = _bot.getDccInetAddress();
                            if (inetAddress == null) {
                                inetAddress = _bot.getInetAddress();
                            }
                            final byte[] ip = inetAddress.getAddress();
                            final long ipNum = _bot.ipToLong(ip);
                            String safeFilename = _file.getName().replace(' ', '_');
                            safeFilename = safeFilename.replace('\t', '_');
                            if (allowResume) {
                                _manager.addAwaitingResume(DccFileTransfer.this);
                            }
                            _bot.sendCTCPCommand(_nick,
                                    "DCC SEND " + safeFilename + " " + ipNum + " " + _port + " " + _file.length());
                            DccFileTransfer.access$4(DccFileTransfer.this, ss.accept());
                            _socket.setSoTimeout(30000);
                            DccFileTransfer.access$5(DccFileTransfer.this, System.currentTimeMillis());
                            if (allowResume) {
                                _manager.removeAwaitingResume(DccFileTransfer.this);
                            }
                            ss.close();
                            final BufferedOutputStream output = new BufferedOutputStream(_socket.getOutputStream());
                            final BufferedInputStream input = new BufferedInputStream(_socket.getInputStream());
                            finput = new BufferedInputStream(new FileInputStream(_file));
                            if (_progress > 0) {
                                long bytesSkipped = 0;
                                while (bytesSkipped < _progress) {
                                    bytesSkipped += finput.skip(_progress - bytesSkipped);
                                }
                            }
                            final byte[] outBuffer = new byte[1024];
                            final byte[] inBuffer = new byte[4];
                            int bytesRead = 0;
                            while ((bytesRead = finput.read(outBuffer, 0, outBuffer.length)) != -1) {
                                output.write(outBuffer, 0, bytesRead);
                                output.flush();
                                input.read(inBuffer, 0, inBuffer.length);
                                final DccFileTransfer dccFileTransfer = DccFileTransfer.this;
                                DccFileTransfer.access$8(dccFileTransfer, dccFileTransfer._progress + bytesRead);
                                DccFileTransfer.this.delay();
                            }
                        } catch (final Exception e) {
                            exception = e;
                            try {
                                finput.close();
                                _socket.close();
                            } catch (final Exception var16_18) {
                            }
                            break block23;
                        }
                    } catch (final Throwable var15_21) {
                        try {
                            finput.close();
                            _socket.close();
                        } catch (final Exception var16_19) {
                            // empty catch block
                        }
                        throw var15_21;
                    }
                    try {
                        finput.close();
                        _socket.close();
                    } catch (final Exception var16_20) {
                        // empty catch block
                    }
                }
                _bot.onFileTransferFinished(DccFileTransfer.this, exception);
            }
        }.start();
    }

    void setProgress(final long progress) {
        _progress = progress;
    }

    private void delay() {
        if (_packetDelay > 0) {
            try {
                Thread.sleep(_packetDelay);
            } catch (final InterruptedException var1_1) {
                // empty catch block
            }
        }
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

    public File getFile() {
        return _file;
    }

    public int getPort() {
        return _port;
    }

    public boolean isIncoming() {
        return _incoming;
    }

    public boolean isOutgoing() {
        return !isIncoming();
    }

    public void setPacketDelay(final long millis) {
        _packetDelay = millis;
    }

    public long getPacketDelay() {
        return _packetDelay;
    }

    public long getSize() {
        return _size;
    }

    public long getProgress() {
        return _progress;
    }

    public double getProgressPercentage() {
        return 100.0 * ((double) getProgress() / (double) getSize());
    }

    public void close() {
        try {
            _socket.close();
        } catch (final Exception var1_1) {
            // empty catch block
        }
    }

    public long getTransferRate() {
        final long time = (System.currentTimeMillis() - _startTime) / 1000;
        if (time <= 0) {
            return 0;
        }
        return getProgress() / time;
    }

    public long getNumericalAddress() {
        return _address;
    }

    static /* synthetic */ void access$4(final DccFileTransfer dccFileTransfer, final Socket socket) {
        dccFileTransfer._socket = socket;
    }

    static /* synthetic */ void access$5(final DccFileTransfer dccFileTransfer, final long l) {
        dccFileTransfer._startTime = l;
    }

    static /* synthetic */ void access$8(final DccFileTransfer dccFileTransfer, final long l) {
        dccFileTransfer._progress = l;
    }

    static /* synthetic */ void access$11(final DccFileTransfer dccFileTransfer, final int n) {
        dccFileTransfer._port = n;
    }

}
