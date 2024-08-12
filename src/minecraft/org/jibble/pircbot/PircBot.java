package org.jibble.pircbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

public abstract class PircBot implements ReplyConstants {

	public static final int EaZy = 1982;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	public static final String VERSION = "1.5.0";
	private InputThread _inputThread = null;
	private OutputThread _outputThread = null;
	private String _charset = null;
	private InetAddress _inetAddress = null;
	private String _server = null;
	private int _port = -1;
	private String _password = null;
	private final Queue _outQueue = new Queue();
	private long _messageDelay = 1000;
	private Hashtable _channels;
	private final Hashtable _topics;
	private final DccManager _dccManager;
	private int[] _dccPorts;
	private InetAddress _dccInetAddress;
	private boolean _autoNickChange;
	private boolean _verbose;
	private String _name;
	private String _nick;
	private String _login;
	private String _version;
	private String _finger;
	private final String _channelPrefixes;

	public PircBot() {
		this._topics = new Hashtable();
		this._channels = new Hashtable();
		_dccManager = new DccManager(this);
		_dccPorts = null;
		_dccInetAddress = null;
		_autoNickChange = false;
		_verbose = false;
		_nick = _name = "PircBot";
		_login = "PircBot";
		_version = "PircBot 1.5.0 Java IRC Bot - www.jibble.org";
		_finger = "You ought to be arrested for fingering a bot!";
		_channelPrefixes = "#&+!";
	}

	public final synchronized void connect(final String hostname)
			throws IOException, IrcException, NickAlreadyInUseException {
		this.connect(hostname, 6666, "asdf1234lolxD");
	}

	public final synchronized void connect(final String hostname, final int port)
			throws IOException, IrcException, NickAlreadyInUseException {
		this.connect(hostname, port, null);
	}

	/*
	 * Enabled aggressive block sorting
	 */
	public final synchronized void connect(final String hostname, final int port, final String password)
			throws IOException, IrcException, NickAlreadyInUseException {
		_server = hostname;
		_port = port;
		_password = password;
		if (isConnected()) {
			throw new IOException("The PircBot is already connected to an IRC server.  Disconnect first.");
		}
		removeAllChannels();
		final Socket socket = new Socket(hostname, port);
		log("*** Connected to server.");
		_inetAddress = socket.getLocalAddress();
		InputStreamReader inputStreamReader = null;
		OutputStreamWriter outputStreamWriter = null;
		if (getEncoding() != null) {
			inputStreamReader = new InputStreamReader(socket.getInputStream(), getEncoding());
			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), getEncoding());
		} else {
			inputStreamReader = new InputStreamReader(socket.getInputStream());
			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
		}
		final BufferedReader breader = new BufferedReader(inputStreamReader);
		final BufferedWriter bwriter = new BufferedWriter(outputStreamWriter);
		if (password != null && !password.isEmpty()) {
			OutputThread.sendRawLine(this, bwriter, "PASS " + password);
		}
		String nick = getName();
		OutputThread.sendRawLine(this, bwriter, "NICK " + nick);
		OutputThread.sendRawLine(this, bwriter, "USER " + getLogin() + " 8 * :" + getVersion());
		_inputThread = new InputThread(this, socket, breader, bwriter);
		String line = null;
		int tries = 1;
		while ((line = breader.readLine()) != null) {
			handleLine(line);
			final int firstSpace = line.indexOf(' ');
			final int secondSpace = line.indexOf(' ', firstSpace + 1);
			if (secondSpace >= 0) {
				final String code = line.substring(firstSpace + 1, secondSpace);
				if (code.equals("004")) {
					break;
				}
				if (code.equals("433")) {
					if (!_autoNickChange) {
						socket.close();
						_inputThread = null;
						throw new NickAlreadyInUseException(line);
					}
					nick = String.valueOf(getName()) + ++tries;
					OutputThread.sendRawLine(this, bwriter, "NICK " + nick);
				} else if (!code.equals("439") && (code.startsWith("5") || code.startsWith("4"))) {
					socket.close();
					_inputThread = null;
					throw new IrcException("Could not log into the IRC server: " + line);
				}
			}
			setNick(nick);
		}
		log("*** Logged onto server.");
		socket.setSoTimeout(300000);
		_inputThread.start();
		if (_outputThread == null) {
			_outputThread = new OutputThread(this, _outQueue);
			_outputThread.start();
		}
		onConnect();
	}

	public final synchronized void reconnect() throws IOException, IrcException, NickAlreadyInUseException {
		if (getServer() == null) {
			throw new IrcException(
					"Cannot reconnect to an IRC server because we were never connected to one previously!");
		}
		this.connect(getServer(), getPort(), getPassword());
	}

	public final synchronized void disconnect() {
		this.quitServer();
	}

	public void setAutoNickChange(final boolean autoNickChange) {
		_autoNickChange = autoNickChange;
	}

	public final void startIdentServer() {
		new org.jibble.pircbot.IdentServer(this, getLogin());
	}

	public final void joinChannel(final String channel) {
		sendRawLine("JOIN " + channel);
	}

	public final void joinChannel(final String channel, final String key) {
		this.joinChannel(String.valueOf(channel) + " " + key);
	}

	public final void partChannel(final String channel) {
		sendRawLine("PART " + channel);
	}

	public final void partChannel(final String channel, final String reason) {
		sendRawLine("PART " + channel + " :" + reason);
	}

	public final void quitServer() {
		this.quitServer("");
	}

	public final void quitServer(final String reason) {
		sendRawLine("QUIT :" + reason);
	}

	public final synchronized void sendRawLine(final String line) {
		if (isConnected()) {
			_inputThread.sendRawLine(line);
		}
	}

	public final synchronized void sendRawLineViaQueue(final String line) {
		if (line == null) {
			throw new NullPointerException("Cannot send null messages to server");
		}
		if (isConnected()) {
			_outQueue.add(line);
		}
	}

	public final void sendMessage(final String target, final String message) {
		_outQueue.add("PRIVMSG " + target + " :" + message);
	}

	public final void sendAction(final String target, final String action) {
		sendCTCPCommand(target, "ACTION " + action);
	}

	public final void sendNotice(final String target, final String notice) {
		_outQueue.add("NOTICE " + target + " :" + notice);
	}

	public final void sendCTCPCommand(final String target, final String command) {
		_outQueue.add("PRIVMSG " + target + " :\u0001" + command + "\u0001");
	}

	public final void changeNick(final String newNick) {
		sendRawLine("NICK " + newNick);
	}

	public final void identify(final String password) {
		sendRawLine("NICKSERV IDENTIFY " + password);
	}

	public final void setMode(final String channel, final String mode) {
		sendRawLine("MODE " + channel + " " + mode);
	}

	public final void sendInvite(final String nick, final String channel) {
		sendRawLine("INVITE " + nick + " :" + channel);
	}

	public final void ban(final String channel, final String hostmask) {
		sendRawLine("MODE " + channel + " +b " + hostmask);
	}

	public final void unBan(final String channel, final String hostmask) {
		sendRawLine("MODE " + channel + " -b " + hostmask);
	}

	public final void op(final String channel, final String nick) {
		setMode(channel, "+o " + nick);
	}

	public final void deOp(final String channel, final String nick) {
		setMode(channel, "-o " + nick);
	}

	public final void voice(final String channel, final String nick) {
		setMode(channel, "+v " + nick);
	}

	public final void deVoice(final String channel, final String nick) {
		setMode(channel, "-v " + nick);
	}

	public final void setTopic(final String channel, final String topic) {
		sendRawLine("TOPIC " + channel + " :" + topic);
	}

	public final void kick(final String channel, final String nick) {
		this.kick(channel, nick, "");
	}

	public final void kick(final String channel, final String nick, final String reason) {
		sendRawLine("KICK " + channel + " " + nick + " :" + reason);
	}

	public final void listChannels() {
		this.listChannels(null);
	}

	public final void listChannels(final String parameters) {
		if (parameters == null) {
			sendRawLine("LIST");
		} else {
			sendRawLine("LIST " + parameters);
		}
	}

	public final DccFileTransfer dccSendFile(final File file, final String nick, final int timeout) {
		final DccFileTransfer transfer = new DccFileTransfer(this, _dccManager, file, nick, timeout);
		transfer.doSend(true);
		return transfer;
	}

	protected final void dccReceiveFile(final File file, final long address, final int port, final int size) {
		throw new RuntimeException("dccReceiveFile is deprecated, please use sendFile");
	}

	public final DccChat dccSendChatRequest(final String nick, final int timeout) {
		DccChat chat = null;
		try {
			ServerSocket ss = null;
			final int[] ports = getDccPorts();
			if (ports == null) {
				ss = new ServerSocket(0);
			} else {
				int i = 0;
				while (i < ports.length) {
					try {
						ss = new ServerSocket(ports[i]);
						break;
					} catch (final Exception var7_8) {
						++i;
					}
				}
				if (ss == null) {
					throw new IOException("All ports returned by getDccPorts() are in use.");
				}
			}
			ss.setSoTimeout(timeout);
			final int port = ss.getLocalPort();
			InetAddress inetAddress = getDccInetAddress();
			if (inetAddress == null) {
				inetAddress = getInetAddress();
			}
			final byte[] ip = inetAddress.getAddress();
			final long ipNum = ipToLong(ip);
			sendCTCPCommand(nick, "DCC CHAT chat " + ipNum + " " + port);
			final Socket socket = ss.accept();
			ss.close();
			chat = new DccChat(this, nick, socket);
		} catch (final Exception ss) {
			// empty catch block
		}
		return chat;
	}

	protected final DccChat dccAcceptChatRequest(final String sourceNick, final long address, final int port) {
		throw new RuntimeException("dccAcceptChatRequest is deprecated, please use onIncomingChatRequest");
	}

	public void log(final String line) {
		if (_verbose) {
			System.out.println(String.valueOf(System.currentTimeMillis()) + " " + line);
		}
	}

	protected void handleLine(final String line) {
		log(line);
		if (line.startsWith("PING ")) {
			onServerPing(line.substring(5));
			return;
		}
		String sourceNick = "";
		String sourceLogin = "";
		String sourceHostname = "";
		StringTokenizer tokenizer = new StringTokenizer(line);
		final String senderInfo = tokenizer.nextToken();
		String command = tokenizer.nextToken();
		String target = null;
		final int exclamation = senderInfo.indexOf('!');
		final int at = senderInfo.indexOf('@');
		if (senderInfo.startsWith(":")) {
			if (exclamation > 0 && at > 0 && exclamation < at) {
				sourceNick = senderInfo.substring(1, exclamation);
				sourceLogin = senderInfo.substring(exclamation + 1, at);
				sourceHostname = senderInfo.substring(at + 1);
			} else if (tokenizer.hasMoreTokens()) {
				final String token = command;
				int code = -1;
				try {
					code = Integer.parseInt(token);
				} catch (final NumberFormatException var13_13) {
					// empty catch block
				}
				if (code != -1) {
					final String errorStr = token;
					final String response = line.substring(line.indexOf(errorStr, senderInfo.length()) + 4,
							line.length());
					processServerResponse(code, response);
					return;
				}
				sourceNick = senderInfo;
				target = token;
			} else {
				onUnknown(line);
				return;
			}
		}
		command = command.toUpperCase();
		if (sourceNick.startsWith(":")) {
			sourceNick = sourceNick.substring(1);
		}
		if (target == null) {
			target = tokenizer.nextToken();
		}
		if (target.startsWith(":")) {
			target = target.substring(1);
		}
		if (command.equals("PRIVMSG") && line.indexOf(":\u0001") > 0 && line.endsWith("\u0001")) {
			final String request = line.substring(line.indexOf(":\u0001") + 2, line.length() - 1);
			if (request.equals("VERSION")) {
				onVersion(sourceNick, sourceLogin, sourceHostname, target);
			} else if (request.startsWith("ACTION ")) {
				onAction(sourceNick, sourceLogin, sourceHostname, target, request.substring(7));
			} else if (request.startsWith("PING ")) {
				onPing(sourceNick, sourceLogin, sourceHostname, target, request.substring(5));
			} else if (request.equals("TIME")) {
				onTime(sourceNick, sourceLogin, sourceHostname, target);
			} else if (request.equals("FINGER")) {
				onFinger(sourceNick, sourceLogin, sourceHostname, target);
			} else {
				tokenizer = new StringTokenizer(request);
				if (tokenizer.countTokens() >= 5 && tokenizer.nextToken().equals("DCC")) {
					final boolean success = _dccManager.processRequest(sourceNick, sourceLogin, sourceHostname,
							request);
					if (!success) {
						onUnknown(line);
					}
				} else {
					onUnknown(line);
				}
			}
		} else if (command.equals("PRIVMSG") && _channelPrefixes.indexOf(target.charAt(0)) >= 0) {
			onMessage(target, sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("PRIVMSG")) {
			onPrivateMessage(sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("JOIN")) {
			final String channel = target;
			addUser(channel, new User("", sourceNick));
			onJoin(channel, sourceNick, sourceLogin, sourceHostname);
		} else if (command.equals("PART")) {
			this.removeUser(target, sourceNick);
			if (sourceNick.equals(getNick())) {
				removeChannel(target);
			}
			onPart(target, sourceNick, sourceLogin, sourceHostname);
		} else if (command.equals("NICK")) {
			final String newNick = target;
			renameUser(sourceNick, newNick);
			if (sourceNick.equals(getNick())) {
				setNick(newNick);
			}
			onNickChange(sourceNick, sourceLogin, sourceHostname, newNick);
		} else if (command.equals("NOTICE")) {
			onNotice(sourceNick, sourceLogin, sourceHostname, target, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("QUIT")) {
			if (sourceNick.equals(getNick())) {
				removeAllChannels();
			} else {
				this.removeUser(sourceNick);
			}
			onQuit(sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("KICK")) {
			final String recipient = tokenizer.nextToken();
			if (recipient.equals(getNick())) {
				removeChannel(target);
			}
			this.removeUser(target, recipient);
			onKick(target, sourceNick, sourceLogin, sourceHostname, recipient, line.substring(line.indexOf(" :") + 2));
		} else if (command.equals("MODE")) {
			String mode = line.substring(line.indexOf(target, 2) + target.length() + 1);
			if (mode.startsWith(":")) {
				mode = mode.substring(1);
			}
			processMode(target, sourceNick, sourceLogin, sourceHostname, mode);
		} else if (command.equals("TOPIC")) {
			this.onTopic(target, line.substring(line.indexOf(" :") + 2), sourceNick, System.currentTimeMillis(), true);
		} else if (command.equals("INVITE")) {
			onInvite(target, sourceNick, sourceLogin, sourceHostname, line.substring(line.indexOf(" :") + 2));
		} else {
			onUnknown(line);
		}
	}

	protected void onConnect() {
	}

	protected void onDisconnect() {
	}

	private void processServerResponse(final int code, final String response) {
		switch (code) {
		case 322: {
			final int firstSpace = response.indexOf(32);
			final int secondSpace = response.indexOf(32, firstSpace + 1);
			final int thirdSpace = response.indexOf(32, secondSpace + 1);
			final int colon = response.indexOf(58);
			final String channel = response.substring(firstSpace + 1, secondSpace);
			int userCount = 0;
			try {
				userCount = Integer.parseInt(response.substring(secondSpace + 1, thirdSpace));
			} catch (final NumberFormatException var9_27) {
				// empty catch block
			}
			final String topic = response.substring(colon + 1);
			onChannelInfo(channel, userCount, topic);
			break;
		}
		case 332: {
			final int firstSpace = response.indexOf(32);
			final int secondSpace = response.indexOf(32, firstSpace + 1);
			final int colon = response.indexOf(58);
			final String channel = response.substring(firstSpace + 1, secondSpace);
			final String topic = response.substring(colon + 1);
			_topics.put(channel, topic);
			this.onTopic(channel, topic);
			break;
		}
		case 333: {
			final StringTokenizer tokenizer = new StringTokenizer(response);
			tokenizer.nextToken();
			final String channel = tokenizer.nextToken();
			final String setBy = tokenizer.nextToken();
			long date = 0;
			try {
				date = Long.parseLong(tokenizer.nextToken()) * 1000;
			} catch (final NumberFormatException userCount) {
				// empty catch block
			}
			final String topic = (String) _topics.get(channel);
			_topics.remove(channel);
			this.onTopic(channel, topic, setBy, date, false);
			break;
		}
		case 353: {
			final int channelEndIndex = response.indexOf(" :");
			final String channel = response.substring(response.lastIndexOf(32, channelEndIndex - 1) + 1,
					channelEndIndex);
			final StringTokenizer tokenizer = new StringTokenizer(response.substring(response.indexOf(" :") + 2));
			while (tokenizer.hasMoreTokens()) {
				String nick = tokenizer.nextToken();
				String prefix = "";
				if (nick.startsWith("@")) {
					prefix = "@";
				} else if (nick.startsWith("+")) {
					prefix = "+";
				} else if (nick.startsWith(".")) {
					prefix = ".";
				}
				nick = nick.substring(prefix.length());
				addUser(channel, new User(prefix, nick));
			}
			break;
		}
		case 366: {
			final String channel = response.substring(response.indexOf(32) + 1, response.indexOf(" :"));
			final User[] users = getUsers(channel);
			onUserList(channel, users);
			break;
		}
		default:
			break;
		}
		onServerResponse(code, response);
	}

	protected void onServerResponse(final int code, final String response) {
	}

	protected void onUserList(final String channel, final User[] users) {
	}

	protected void onMessage(final String channel, final String sender, final String login, final String hostname,
			final String message) {
	}

	protected void onPrivateMessage(final String sender, final String login, final String hostname,
			final String message) {
	}

	protected void onAction(final String sender, final String login, final String hostname, final String target,
			final String action) {
	}

	protected void onNotice(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target, final String notice) {
	}

	protected void onJoin(final String channel, final String sender, final String login, final String hostname) {
	}

	protected void onPart(final String channel, final String sender, final String login, final String hostname) {
	}

	protected void onNickChange(final String oldNick, final String login, final String hostname, final String newNick) {
	}

	protected void onKick(final String channel, final String kickerNick, final String kickerLogin,
			final String kickerHostname, final String recipientNick, final String reason) {
	}

	protected void onQuit(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String reason) {
	}

	protected void onTopic(final String channel, final String topic) {
	}

	protected void onTopic(final String channel, final String topic, final String setBy, final long date,
			final boolean changed) {
	}

	protected void onChannelInfo(final String channel, final int userCount, final String topic) {
	}

	private void processMode(final String target, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String mode) {
		if (_channelPrefixes.indexOf(target.charAt(0)) >= 0) {
			final String channel = target;
			final StringTokenizer tok = new StringTokenizer(mode);
			final String[] params = new String[tok.countTokens()];
			int t = 0;
			while (tok.hasMoreTokens()) {
				params[t] = tok.nextToken();
				++t;
			}
			int pn = 32;
			int p = 1;
			int i = 0;
			while (i < params[0].length()) {
				final char atPos = params[0].charAt(i);
				switch (atPos) {
				case '+':
				case '-':
					pn = atPos;
					break;
				case 'o':
					if (pn == 43) {
						updateUser(channel, 1, params[p]);
						onOp(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
					} else {
						updateUser(channel, 2, params[p]);
						onDeop(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
					}
					++p;
					break;
				case 'v':
					if (pn == 43) {
						updateUser(channel, 3, params[p]);
						onVoice(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
					} else {
						updateUser(channel, 4, params[p]);
						onDeVoice(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
					}
					++p;
					break;
				case 'k':
					if (pn == 43) {
						onSetChannelKey(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
					} else {
						onRemoveChannelKey(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
					}
					++p;
					break;
				case 'l':
					if (pn == 43) {
						onSetChannelLimit(channel, sourceNick, sourceLogin, sourceHostname,
								Integer.parseInt(params[p]));
						++p;
					} else {
						onRemoveChannelLimit(channel, sourceNick, sourceLogin, sourceHostname);
					}
					break;
				case 'b':
					if (pn == 43) {
						onSetChannelBan(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
					} else {
						onRemoveChannelBan(channel, sourceNick, sourceLogin, sourceHostname, params[p]);
					}
					++p;
					break;
				case 't':
					if (pn == 43) {
						onSetTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
					} else {
						onRemoveTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
					}
					break;
				case 'n':
					if (pn == 43) {
						onSetNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
					} else {
						onRemoveNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
					}
					break;
				case 'i':
					if (pn == 43) {
						onSetInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
					} else {
						onRemoveInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
					}
					break;
				case 'm':
					if (pn == 43) {
						onSetModerated(channel, sourceNick, sourceLogin, sourceHostname);
					} else {
						onRemoveModerated(channel, sourceNick, sourceLogin, sourceHostname);
					}
					break;
				case 'p':
					if (pn == 43) {
						onSetPrivate(channel, sourceNick, sourceLogin, sourceHostname);
					} else {
						onRemovePrivate(channel, sourceNick, sourceLogin, sourceHostname);
					}
					break;
				case 's':
					if (pn == 43) {
						onSetSecret(channel, sourceNick, sourceLogin, sourceHostname);
					} else {
						onRemoveSecret(channel, sourceNick, sourceLogin, sourceHostname);
					}
					break;
				default:
					break;
				}
				++i;
			}
			onMode(channel, sourceNick, sourceLogin, sourceHostname, mode);
		} else {
			final String nick = target;
			onUserMode(nick, sourceNick, sourceLogin, sourceHostname, mode);
		}
	}

	protected void onMode(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String mode) {
	}

	protected void onUserMode(final String targetNick, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String mode) {
	}

	protected void onOp(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String recipient) {
	}

	protected void onDeop(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String recipient) {
	}

	protected void onVoice(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String recipient) {
	}

	protected void onDeVoice(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String recipient) {
	}

	protected void onSetChannelKey(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String key) {
	}

	protected void onRemoveChannelKey(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String key) {
	}

	protected void onSetChannelLimit(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final int limit) {
	}

	protected void onRemoveChannelLimit(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetChannelBan(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String hostmask) {
	}

	protected void onRemoveChannelBan(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String hostmask) {
	}

	protected void onSetTopicProtection(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveTopicProtection(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetNoExternalMessages(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveNoExternalMessages(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetInviteOnly(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveInviteOnly(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetModerated(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveModerated(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetPrivate(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemovePrivate(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onSetSecret(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onRemoveSecret(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname) {
	}

	protected void onInvite(final String targetNick, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String channel) {
	}

	protected void onDccSendRequest(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String filename, final long address, final int port, final int size) {
	}

	protected void onDccChatRequest(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final long address, final int port) {
	}

	protected void onIncomingFileTransfer(final DccFileTransfer transfer) {
	}

	protected void onFileTransferFinished(final DccFileTransfer transfer, final Exception e) {
	}

	protected void onIncomingChatRequest(final DccChat chat) {
	}

	protected void onVersion(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target) {
		sendRawLine("NOTICE " + sourceNick + " :\u0001VERSION " + _version + "\u0001");
	}

	protected void onPing(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target, final String pingValue) {
		sendRawLine("NOTICE " + sourceNick + " :\u0001PING " + pingValue + "\u0001");
	}

	protected void onServerPing(final String response) {
		sendRawLine("PONG " + response);
	}

	protected void onTime(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target) {
		sendRawLine("NOTICE " + sourceNick + " :\u0001TIME " + new Date().toString() + "\u0001");
	}

	protected void onFinger(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target) {
		sendRawLine("NOTICE " + sourceNick + " :\u0001FINGER " + _finger + "\u0001");
	}

	protected void onUnknown(final String line) {
	}

	public final void setVerbose(final boolean verbose) {
		_verbose = verbose;
	}

	protected final void setName(final String name) {
		_name = name;
	}

	private void setNick(final String nick) {
		_nick = nick;
	}

	protected final void setLogin(final String login) {
		_login = login;
	}

	protected final void setVersion(final String version) {
		_version = version;
	}

	protected final void setFinger(final String finger) {
		_finger = finger;
	}

	public final String getName() {
		return _name;
	}

	public String getNick() {
		return _nick;
	}

	public final String getLogin() {
		return _login;
	}

	public final String getVersion() {
		return _version;
	}

	public final String getFinger() {
		return _finger;
	}

	public final synchronized boolean isConnected() {
		return _inputThread != null && _inputThread.isConnected();
	}

	public final void setMessageDelay(final long delay) {
		if (delay < 0) {
			throw new IllegalArgumentException("Cannot have a negative time.");
		}
		_messageDelay = delay;
	}

	public final long getMessageDelay() {
		return _messageDelay;
	}

	public final int getMaxLineLength() {
		return 512;
	}

	public final int getOutgoingQueueSize() {
		return _outQueue.size();
	}

	public final String getServer() {
		return _server;
	}

	public final int getPort() {
		return _port;
	}

	public final String getPassword() {
		return _password;
	}

	public int[] longToIp(long address) {
		final int[] ip = new int[4];
		int i = 3;
		while (i >= 0) {
			ip[i] = (int) (address % 256);
			address /= 256;
			--i;
		}
		return ip;
	}

	public long ipToLong(final byte[] address) {
		if (address.length != 4) {
			throw new IllegalArgumentException("byte array must be of length 4");
		}
		long ipNum = 0;
		long multiplier = 1;
		int i = 3;
		while (i >= 0) {
			final int byteVal = (address[i] + 256) % 256;
			ipNum += byteVal * multiplier;
			multiplier *= 256;
			--i;
		}
		return ipNum;
	}

	public void setEncoding(final String charset) throws UnsupportedEncodingException {
		"".getBytes(charset);
		_charset = charset;
	}

	public String getEncoding() {
		return _charset;
	}

	public InetAddress getInetAddress() {
		return _inetAddress;
	}

	public void setDccInetAddress(final InetAddress dccInetAddress) {
		_dccInetAddress = dccInetAddress;
	}

	public InetAddress getDccInetAddress() {
		return _dccInetAddress;
	}

	public int[] getDccPorts() {
		if (_dccPorts == null || _dccPorts.length == 0) {
			return null;
		}
		return _dccPorts.clone();
	}

	public void setDccPorts(final int[] ports) {
		_dccPorts = ports == null || ports.length == 0 ? null : (int[]) ports.clone();
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof PircBot) {
			final PircBot other = (PircBot) o;
			return other == this;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Version{" + _version + "}" + " Connected{" + isConnected() + "}" + " Server{" + _server + "}" + " Port{"
				+ _port + "}" + " Password{" + _password + "}";
	}

	public final User[] getUsers(String channel) {
		channel = channel.toLowerCase();
		User[] userArray = new User[] {};
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			final Hashtable users = (Hashtable) _channels.get(channel);
			if (users != null) {
				userArray = new User[users.size()];
				final Enumeration enumeration = users.elements();
				int i = 0;
				while (i < userArray.length) {
					userArray[i] = (User) enumeration.nextElement();
					++i;
				}
			}
		}
		return userArray;
	}

	public final String[] getChannels() {
		String[] channels = new String[] {};
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			channels = new String[_channels.size()];
			final Enumeration enumeration = _channels.keys();
			int i = 0;
			while (i < channels.length) {
				channels[i] = (String) enumeration.nextElement();
				++i;
			}
		}
		return channels;
	}

	public synchronized void dispose() {
		_outputThread.interrupt();
		_inputThread.dispose();
	}

	private void addUser(String channel, final User user) {
		channel = channel.toLowerCase();
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			Hashtable<User, User> users = (Hashtable<User, User>) _channels.get(channel);
			if (users == null) {
				users = new Hashtable<>();
				_channels.put(channel, users);
			}
			users.put(user, user);
		}
	}

	private User removeUser(String channel, final String nick) {
		channel = channel.toLowerCase();
		final User user = new User("", nick);
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			final Hashtable users = (Hashtable) _channels.get(channel);
			if (users != null) {
				return (User) users.remove(user);
			}
		}
		return null;
	}

	private void removeUser(final String nick) {
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			final Enumeration enumeration = _channels.keys();
			while (enumeration.hasMoreElements()) {
				final String channel = (String) enumeration.nextElement();
				this.removeUser(channel, nick);
			}
		}
	}

	private void renameUser(final String oldNick, final String newNick) {
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			final Enumeration enumeration = _channels.keys();
			while (enumeration.hasMoreElements()) {
				final String channel = (String) enumeration.nextElement();
				User user = this.removeUser(channel, oldNick);
				if (user == null) {
					continue;
				}
				user = new User(user.getPrefix(), newNick);
				addUser(channel, user);
			}
		}
	}

	private void removeChannel(String channel) {
		channel = channel.toLowerCase();
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			_channels.remove(channel);
		}
	}

	private void removeAllChannels() {
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			_channels = new Hashtable();
		}
	}

	private void updateUser(String channel, final int userMode, final String nick) {
		channel = channel.toLowerCase();
		final Hashtable hashtable = _channels;
		synchronized (hashtable) {
			final Hashtable users = (Hashtable) _channels.get(channel);
			User newUser = null;
			if (users != null) {
				final Enumeration enumeration = users.elements();
				while (enumeration.hasMoreElements()) {
					final User userObj = (User) enumeration.nextElement();
					if (!userObj.getNick().equalsIgnoreCase(nick)) {
						continue;
					}
					if (userMode == 1) {
						if (userObj.hasVoice()) {
							newUser = new User("@+", nick);
							continue;
						}
						newUser = new User("@", nick);
						continue;
					}
					if (userMode == 2) {
						if (userObj.hasVoice()) {
							newUser = new User("+", nick);
							continue;
						}
						newUser = new User("", nick);
						continue;
					}
					if (userMode == 3) {
						if (userObj.isOp()) {
							newUser = new User("@+", nick);
							continue;
						}
						newUser = new User("+", nick);
						continue;
					}
					if (userMode != 4) {
						continue;
					}
					newUser = userObj.isOp() ? new User("@", nick) : new User("", nick);
				}
			}
			if (newUser != null) {
				users.put(newUser, newUser);
			} else {
				newUser = new User("", nick);
				users.put(newUser, newUser);
			}
		}
	}
}
