package net.minecraft.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

public class OldServerPinger {

public static final int EaZy = 632;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Splitter PING_RESPONSE_SPLITTER = Splitter.on('\u0000').limit(6);
	private static final Logger logger = LogManager.getLogger();

	/** A list of NetworkManagers that have pending pings */
	private final List pingDestinations = Collections.synchronizedList(Lists.newArrayList());

	public void ping(final ServerData server) throws UnknownHostException {
		final ServerAddress var2 = ServerAddress.func_78860_a(server.serverIP);
		final NetworkManager var3 = NetworkManager.provideLanClient(InetAddress.getByName(var2.getIP()),
				var2.getPort());
		pingDestinations.add(var3);
		server.serverMOTD = "Pinging...";
		server.pingToServer = -1L;
		server.playerList = null;
		var3.setNetHandler(new INetHandlerStatusClient() {
			private boolean field_147403_d = false;
			private long field_175092_e = 0L;

			@Override
			public void handleServerInfo(final S00PacketServerInfo packetIn) {
				final ServerStatusResponse var2 = packetIn.func_149294_c();

				if (var2.getServerDescription() != null) {
					server.serverMOTD = var2.getServerDescription().getFormattedText();
				} else {
					server.serverMOTD = "";
				}

				if (var2.getProtocolVersionInfo() != null) {
					server.gameVersion = var2.getProtocolVersionInfo().getName();
					server.version = var2.getProtocolVersionInfo().getProtocol();
				} else {
					server.gameVersion = "Old";
					server.version = 0;
				}

				if (var2.getPlayerCountData() != null) {
					server.populationInfo = EnumChatFormatting.GRAY + ""
							+ var2.getPlayerCountData().getOnlinePlayerCount() + "" + EnumChatFormatting.DARK_GRAY + "/"
							+ EnumChatFormatting.GRAY + var2.getPlayerCountData().getMaxPlayers();

					if (ArrayUtils.isNotEmpty(var2.getPlayerCountData().getPlayers())) {
						final StringBuilder var3x = new StringBuilder();
						final GameProfile[] var4 = var2.getPlayerCountData().getPlayers();
						final int var5 = var4.length;

						for (int var6 = 0; var6 < var5; ++var6) {
							final GameProfile var7 = var4[var6];

							if (var3x.length() > 0) {
								var3x.append("\n");
							}

							var3x.append(var7.getName());
						}

						if (var2.getPlayerCountData().getPlayers().length < var2.getPlayerCountData()
								.getOnlinePlayerCount()) {
							if (var3x.length() > 0) {
								var3x.append("\n");
							}

							var3x.append("... and ").append(var2.getPlayerCountData().getOnlinePlayerCount()
									- var2.getPlayerCountData().getPlayers().length).append(" more ...");
						}

						server.playerList = var3x.toString();
					}
				} else {
					server.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
				}

				if (var2.getFavicon() != null) {
					final String var8 = var2.getFavicon();

					if (var8.startsWith("data:image/png;base64,")) {
						server.setBase64EncodedIconData(var8.substring("data:image/png;base64,".length()));
					} else {
						OldServerPinger.logger.error("Invalid server icon (unknown format)");
					}
				} else {
					server.setBase64EncodedIconData((String) null);
				}

				field_175092_e = Minecraft.getSystemTime();
				var3.sendPacket(new C01PacketPing(field_175092_e));
				field_147403_d = true;
			}

			@Override
			public void handlePong(final S01PacketPong packetIn) {
				final long var2 = field_175092_e;
				final long var4 = Minecraft.getSystemTime();
				server.pingToServer = var4 - var2;
				var3.closeChannel(new ChatComponentText("Finished"));
			}

			@Override
			public void onDisconnect(final IChatComponent reason) {
				if (!field_147403_d) {
					OldServerPinger.logger.error("Can\'t ping " + server.serverIP + ": " + reason.getUnformattedText());
					server.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t connect to server.";
					server.populationInfo = "";
					OldServerPinger.this.tryCompatibilityPing(server);
				}
			}
		});

		try {
			var3.sendPacket(new C00Handshake(47, var2.getIP(), var2.getPort(), EnumConnectionState.STATUS));
			var3.sendPacket(new C00PacketServerQuery());
		} catch (final Throwable var5) {
			logger.error(var5);
		}
	}

	private void tryCompatibilityPing(final ServerData server) {
		final ServerAddress var2 = ServerAddress.func_78860_a(server.serverIP);
		new Bootstrap().group((EventLoopGroup) NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())
				.handler(new ChannelInitializer() {
					@Override
					protected void initChannel(final Channel p_initChannel_1_) {
						try {
							p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, 24);
						} catch (final ChannelException var4) {
						}

						try {
							p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, false);
						} catch (final ChannelException var3) {
						}

						p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { new SimpleChannelInboundHandler() {
							@Override
							public void channelActive(final ChannelHandlerContext p_channelActive_1_) throws Exception {
								super.channelActive(p_channelActive_1_);
								final ByteBuf var2x = Unpooled.buffer();

								try {
									var2x.writeByte(254);
									var2x.writeByte(1);
									var2x.writeByte(250);
									char[] var3 = "MC|PingHost".toCharArray();
									var2x.writeShort(var3.length);
									char[] var4 = var3;
									int var5 = var3.length;
									int var6;
									char var7;

									for (var6 = 0; var6 < var5; ++var6) {
										var7 = var4[var6];
										var2x.writeChar(var7);
									}

									var2x.writeShort(7 + 2 * var2.getIP().length());
									var2x.writeByte(127);
									var3 = var2.getIP().toCharArray();
									var2x.writeShort(var3.length);
									var4 = var3;
									var5 = var3.length;

									for (var6 = 0; var6 < var5; ++var6) {
										var7 = var4[var6];
										var2x.writeChar(var7);
									}

									var2x.writeInt(var2.getPort());
									p_channelActive_1_.channel().writeAndFlush(var2x)
											.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
								}
								finally {
									var2x.release();
								}
							}

							protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_,
									final ByteBuf p_channelRead0_2_) {
								final short var3 = p_channelRead0_2_.readUnsignedByte();

								if (var3 == 255) {
									final String var4 = new String(
											p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(),
											Charsets.UTF_16BE);
									final String[] var5 = Iterables
											.toArray(OldServerPinger.PING_RESPONSE_SPLITTER.split(var4), String.class);

									if ("\u00a71".equals(var5[0])) {
										MathHelper.parseIntWithDefault(var5[1], 0);
										final String var7 = var5[2];
										final String var8 = var5[3];
										final int var9 = MathHelper.parseIntWithDefault(var5[4], -1);
										final int var10 = MathHelper.parseIntWithDefault(var5[5], -1);
										server.version = -1;
										server.gameVersion = var7;
										server.serverMOTD = var8;
										server.populationInfo = EnumChatFormatting.GRAY + "" + var9 + ""
												+ EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10;
									}
								}

								p_channelRead0_1_.close();
							}

							@Override
							public void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_,
									final Throwable p_exceptionCaught_2_) {
								p_exceptionCaught_1_.close();
							}

							@Override
							protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_,
									final Object p_channelRead0_2_) {
								this.channelRead0(p_channelRead0_1_, (ByteBuf) p_channelRead0_2_);
							}
						} });
					}
				}).channel(NioSocketChannel.class).connect(var2.getIP(), var2.getPort());
	}

	public void pingPendingNetworks() {
		synchronized (pingDestinations) {
			final Iterator var2 = pingDestinations.iterator();

			while (var2.hasNext()) {
				final NetworkManager var3 = (NetworkManager) var2.next();

				if (var3.isChannelOpen()) {
					var3.processReceivedPackets();
				} else {
					var2.remove();
					var3.checkDisconnected();
				}
			}
		}
	}

	public void clearPendingNetworks() {
		synchronized (pingDestinations) {
			final Iterator var2 = pingDestinations.iterator();

			while (var2.hasNext()) {
				final NetworkManager var3 = (NetworkManager) var2.next();

				if (var3.isChannelOpen()) {
					var2.remove();
					var3.closeChannel(new ChatComponentText("Cancelled"));
				}
			}
		}
	}
}
