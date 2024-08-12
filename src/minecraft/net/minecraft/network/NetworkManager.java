package net.minecraft.network;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.darkmagician6.eventapi.EventManager;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;

public class NetworkManager extends SimpleChannelInboundHandler {

	public static final int EaZy = 1371;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger logger = LogManager.getLogger();
	public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
	public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
	public static final AttributeKey attrKeyConnectionState = AttributeKey.valueOf("protocol");
	public static final LazyLoadBase CLIENT_NIO_EVENTLOOP = new LazyLoadBase() {
		protected NioEventLoopGroup genericLoad() {
			return new NioEventLoopGroup(0,
					new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
		}

		@Override
		protected Object load() {
			return genericLoad();
		}
	};
	public static final LazyLoadBase CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase() {
		protected LocalEventLoopGroup genericLoad() {
			return new LocalEventLoopGroup(0,
					new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
		}

		@Override
		protected Object load() {
			return genericLoad();
		}
	};
	/**
	 * The queue for packets that require transmission
	 */
	private final Queue outboundPacketsQueue = Queues.newConcurrentLinkedQueue();

	/**
	 * The active channel
	 */
	public Channel channel;

	/**
	 * The address of the remote party
	 */
	private SocketAddress socketAddress;

	/**
	 * The INetHandler instance responsible for processing received packets
	 */
	private INetHandler packetListener;

	/**
	 * A String indicating why the network has shutdown.
	 */
	private IChatComponent terminationReason;
	private boolean isEncrypted;
	private boolean disconnected;

	public NetworkManager(final EnumPacketDirection packetDirection) {}

	@Override
	public void channelActive(final ChannelHandlerContext p_channelActive_1_) throws Exception {
		super.channelActive(p_channelActive_1_);
		channel = p_channelActive_1_.channel();
		socketAddress = channel.remoteAddress();

		try {
			setConnectionState(EnumConnectionState.HANDSHAKING);
		} catch (final Throwable var3) {
			logger.fatal(var3);
		}
	}

	/**
	 * Sets the new connection state and registers which packets this channel may
	 * send and receive
	 */
	public void setConnectionState(final EnumConnectionState newState) {
		channel.attr(attrKeyConnectionState).set(newState);
		channel.config().setAutoRead(true);
		logger.debug("Enabled auto read");
	}

	@Override
	public void channelInactive(final ChannelHandlerContext p_channelInactive_1_) {
		closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_,
			final Throwable p_exceptionCaught_2_) {
		// EaZy Crash Fix
		logger.debug("Disconnecting " + getRemoteAddress(), p_exceptionCaught_2_);
		if (p_exceptionCaught_2_ instanceof DecoderException) {
			System.out.println("~~~DID NOT DISCONNECT~~~");
			System.out.println("Disconnecting " + getRemoteAddress() + ":");
			System.out.println(p_exceptionCaught_2_);
		} else {
			closeChannel(new ChatComponentTranslation("disconnect.genericReason",
					new Object[] { "Internal Exception: " + p_exceptionCaught_2_ }));
		}
	}

	protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final Packet p_channelRead0_2_) {
		if (channel.isOpen()) {
			try {

				// System.out.println(p_channelRead0_2_.getClass().getSimpleName());
				//
				// Class c = p_channelRead0_2_.getClass();
				// for (Field f : c.getDeclaredFields()) {
				// f.setAccessible(true);
				// try {
				// System.out.println(f.get(p_channelRead0_2_));
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// }

				// if (p_channelRead0_2_ instanceof S08PacketPlayerPosLook) {
				// final S08PacketPlayerPosLook p = (S08PacketPlayerPosLook)
				// p_channelRead0_2_;
				// if (p.x == 0 && p.y == 0 && p.z == 0 && p.rotationPitchPacket
				// == 0 && p.rotationYawPacket == 0) {
				// return;
				// }
				// }

				// if (p_channelRead0_2_ instanceof S02PacketChat) {
				// final S02PacketChat p = (S02PacketChat) p_channelRead0_2_;
				//
				// if (p.func_179841_c() == 0) {
				// final String asd =
				// p.func_148915_c().getFormattedText().replace("�r",
				// "").replace(" ", "");
				// if (MiscUtils.isInteger(asd)) {
				// // EaZy Gomme Flag
				// return;
				// }
				// }
				//
				// }

				// if (p_channelRead0_2_ instanceof S1FPacketSetExperience) {
				// final S1FPacketSetExperience p = (S1FPacketSetExperience)
				// p_channelRead0_2_;
				// // NoXP NoEXP fix
				// if (p.func_149397_c() == 0 && p.func_149396_d() == 0 &&
				// p.func_149395_e() != 0 && !p_channelRead0_1_
				// .channel().remoteAddress().toString().toLowerCase().contains("rewinside"))
				// {
				// return;
				// }
				//
				// }

				final EventReceivePacket event = new EventReceivePacket();
				event.setPacket(p_channelRead0_2_);
				EventManager.call(event);
				if (event.isCancelled()) {
					return;
				}
				event.getPacket().processPacket(packetListener);
			} catch (final ThreadQuickExitException var4) {

			}
		}
	}

	/**
	 * Sets the NetHandler for this NetworkManager, no checks are made if this
	 * handler is suitable for the particular connection state (protocol)
	 */
	public void setNetHandler(final INetHandler handler) {
		Validate.notNull(handler, "packetListener", new Object[0]);
		logger.debug("Set listener of {} to {}", new Object[] { this, handler });
		packetListener = handler;
	}

	public float yaw = Float.NaN;
	public float pitch = Float.NaN;
	public float prevYaw = Float.NaN;
	public float prevPitch = Float.NaN;
	private int C03Count = 0;
	private int C04Count = 0;

	public void sendPacket(Packet packetIn) {
		final EventSendPacket e = new EventSendPacket();
		e.setPacket(packetIn);
		EventManager.call(e);
		if (e.isCancelled()) {
			return;
		}
		if (e.getPacket() instanceof C03PacketPlayer) {
			final C03PacketPlayer p = (C03PacketPlayer) e.getPacket();
			final boolean b = (e.getPacket() instanceof C05PacketPlayerLook
					|| e.getPacket() instanceof C06PacketPlayerPosLook);
			final boolean a = (e.getPacket() instanceof C04PacketPlayerPosition);
			final boolean c = !(e.getPacket() instanceof C04PacketPlayerPosition)
					&& !(e.getPacket() instanceof C05PacketPlayerLook)
					&& !(e.getPacket() instanceof C06PacketPlayerPosLook);
			if (c) {
				C03Count++;
			} else {
				C03Count = 0;
			}
			if (a) {
				C04Count++;
			} else {
				C04Count = 0;
			}
			if (!Float.isNaN(yaw) && !Float.isNaN(pitch)) {
				if (e.getPacket() instanceof C05PacketPlayerLook) {
					if (yaw == p.getYaw() && pitch == p.getPitch()) {
						e.setPacket(new C03PacketPlayer(p.onGround));
					}
				} else if (e.getPacket() instanceof C06PacketPlayerPosLook) {
					if (yaw == p.getYaw() && pitch == p.getPitch()) {
						e.setPacket(new C03PacketPlayer.C04PacketPlayerPosition(p.getPositionX(), p.getPositionY(),
								p.getPositionZ(), p.onGround));
					}
				}
			}
			if (b) {
				prevYaw = yaw;
				prevPitch = pitch;
				yaw = p.getYaw();
				pitch = p.getPitch();
			} else if ((a && C04Count >= 2) || (c && C03Count >= 2)) {
				prevYaw = yaw;
				prevPitch = pitch;
				yaw = Minecraft.thePlayer.rotationYawHead;
				pitch = Minecraft.thePlayer.rotationPitch;
			}
		}
		if (channel != null && channel.isOpen()) {
			flushOutboundQueue();
			dispatchPacket(e.getPacket(), (GenericFutureListener[]) null);
		} else {
			outboundPacketsQueue.add(new NetworkManager.InboundHandlerTuplePacketListener(e.getPacket(),
					(GenericFutureListener[]) null));
		}
	}

	public void sendPacket(final Packet packetIn, final GenericFutureListener listener,
			final GenericFutureListener... listeners) {
		if (channel != null && channel.isOpen()) {
			flushOutboundQueue();
			dispatchPacket(packetIn, ArrayUtils.add(listeners, 0, listener));
		} else {
			outboundPacketsQueue.add(new NetworkManager.InboundHandlerTuplePacketListener(packetIn,
					ArrayUtils.add(listeners, 0, listener)));
		}
	}

	/**
	 * Will commit the packet to the channel. If the current thread 'owns' the
	 * channel it will write and flush the packet, otherwise it will add a task for
	 * the channel eventloop thread to do that.
	 */
	private void dispatchPacket(final Packet inPacket, final GenericFutureListener[] futureListeners) {
		final EnumConnectionState var3 = EnumConnectionState.getFromPacket(inPacket);
		final EnumConnectionState var4 = (EnumConnectionState) channel.attr(attrKeyConnectionState).get();

		if (var4 != var3) {
			logger.debug("Disabled auto read");
			channel.config().setAutoRead(false);
		}

		if (channel.eventLoop().inEventLoop()) {
			if (var3 != var4) {
				setConnectionState(var3);
			}

			final ChannelFuture var5 = channel.writeAndFlush(inPacket);

			if (futureListeners != null) {
				var5.addListeners(futureListeners);
			}

			var5.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		} else {
			channel.eventLoop().execute(() -> {
				if (var3 != var4) {
					NetworkManager.this.setConnectionState(var3);
				}

				final ChannelFuture var1 = channel.writeAndFlush(inPacket);

				if (futureListeners != null) {
					var1.addListeners(futureListeners);
				}

				var1.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
			});
		}
	}

	/**
	 * Will iterate through the outboundPacketQueue and dispatch all Packets
	 */
	private void flushOutboundQueue() {
		if (channel != null && channel.isOpen()) {
			while (!outboundPacketsQueue.isEmpty()) {
				final NetworkManager.InboundHandlerTuplePacketListener var1 = (NetworkManager.InboundHandlerTuplePacketListener) outboundPacketsQueue
						.poll();
				dispatchPacket(var1.packet, var1.futureListeners);
			}
		}
	}

	/**
	 * Checks timeouts and processes all packets received
	 */
	public void processReceivedPackets() {
		flushOutboundQueue();

		if (packetListener instanceof IUpdatePlayerListBox) {
			((IUpdatePlayerListBox) packetListener).update();
		}

		channel.flush();
	}

	/**
	 * Returns the socket address of the remote side. Server-only.
	 */
	public SocketAddress getRemoteAddress() {
		return socketAddress;
	}

	/**
	 * Closes the channel, the parameter can be used for an exit message (not
	 * certain how it gets sent)
	 */
	public void closeChannel(final IChatComponent message) {
		if (channel.isOpen()) {
			channel.close().awaitUninterruptibly();
			terminationReason = message;
		}
	}

	/**
	 * True if this NetworkManager uses a memory connection (single player game).
	 * False may imply both an active TCP connection or simply no active connection
	 * at all
	 */
	public boolean isLocalChannel() {
		return channel instanceof LocalChannel || channel instanceof LocalServerChannel;
	}

	/**
	 * Prepares a clientside NetworkManager: establishes a connection to the address
	 * and port supplied and configures the channel pipeline. Returns the newly
	 * created instance.
	 */
	public static NetworkManager provideLanClient(final InetAddress p_150726_0_, final int p_150726_1_) {
		final NetworkManager var2 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
		new Bootstrap().group((EventLoopGroup) CLIENT_NIO_EVENTLOOP.getValue()).handler(new ChannelInitializer() {
			@Override
			protected void initChannel(final Channel p_initChannel_1_) {
				try {
					p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, 24);
				} catch (final ChannelException var4) {}

				try {
					p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, false);
				} catch (final ChannelException var3) {}

				p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(20))
						.addLast("splitter", new MessageDeserializer2())
						.addLast("decoder", new MessageDeserializer(EnumPacketDirection.CLIENTBOUND))
						.addLast("prepender", new MessageSerializer2())
						.addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND))
						.addLast("packet_handler", var2);
			}
		}).channel(NioSocketChannel.class).connect(p_150726_0_, p_150726_1_).syncUninterruptibly();
		return var2;
	}

	/**
	 * Prepares a clientside NetworkManager: establishes a connection to the socket
	 * supplied and configures the channel pipeline. Returns the newly created
	 * instance.
	 */
	public static NetworkManager provideLocalClient(final SocketAddress p_150722_0_) {
		final NetworkManager var1 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
		new Bootstrap().group((EventLoopGroup) CLIENT_LOCAL_EVENTLOOP.getValue()).handler(new ChannelInitializer() {
			@Override
			protected void initChannel(final Channel p_initChannel_1_) {
				p_initChannel_1_.pipeline().addLast("packet_handler", var1);
			}
		}).channel(LocalChannel.class).connect(p_150722_0_).syncUninterruptibly();
		return var1;
	}

	/**
	 * Adds an encoder+decoder to the channel pipeline. The parameter is the secret
	 * key used for encrypted communication
	 */
	public void enableEncryption(final SecretKey key) {
		isEncrypted = true;
		channel.pipeline().addBefore("splitter", "decrypt",
				new NettyEncryptingDecoder(CryptManager.func_151229_a(2, key)));
		channel.pipeline().addBefore("prepender", "encrypt",
				new NettyEncryptingEncoder(CryptManager.func_151229_a(1, key)));
	}

	public boolean func_179292_f() {
		return isEncrypted;
	}

	/**
	 * Returns true if this NetworkManager has an active channel, false otherwise
	 */
	public boolean isChannelOpen() {
		return channel != null && channel.isOpen();
	}

	public boolean hasNoChannel() {
		return channel == null;
	}

	/**
	 * Gets the current handler for processing packets
	 */
	public INetHandler getNetHandler() {
		return packetListener;
	}

	/**
	 * If this channel is closed, returns the exit message, null otherwise.
	 */
	public IChatComponent getExitMessage() {
		return terminationReason;
	}

	/**
	 * Switches the channel to manual reading modus
	 */
	public void disableAutoRead() {
		channel.config().setAutoRead(false);
	}

	public void setCompressionTreshold(final int treshold) {
		if (treshold >= 0) {
			if (channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
				((NettyCompressionDecoder) channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
			} else {
				channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(treshold));
			}

			if (channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
				((NettyCompressionEncoder) channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
			} else {
				channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(treshold));
			}
		} else {
			if (channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
				channel.pipeline().remove("decompress");
			}

			if (channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
				channel.pipeline().remove("compress");
			}
		}
	}

	public void checkDisconnected() {
		if (!hasNoChannel() && !isChannelOpen() && !disconnected) {
			disconnected = true;

			if (getExitMessage() != null) {
				getNetHandler().onDisconnect(getExitMessage());
			} else if (getNetHandler() != null) {
				getNetHandler().onDisconnect(new ChatComponentText("Disconnected!"));
			}
		}
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final Object p_channelRead0_2_) {
		this.channelRead0(p_channelRead0_1_, (Packet) p_channelRead0_2_);
	}

	static class InboundHandlerTuplePacketListener {

		private final Packet packet;
		private final GenericFutureListener[] futureListeners;

		public InboundHandlerTuplePacketListener(final Packet inPacket,
				final GenericFutureListener... inFutureListeners) {
			packet = inPacket;
			futureListeners = inFutureListeners;
		}
	}
}
