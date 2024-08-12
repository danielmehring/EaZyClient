package net.minecraft.util;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageSerializer extends MessageToByteEncoder {

public static final int EaZy = 1634;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT",
			NetworkManager.logMarkerPackets);
	private final EnumPacketDirection direction;
	// private static final String __OBFID = "http://https://fuckuskid00001253";

	public MessageSerializer(final EnumPacketDirection direction) {
		this.direction = direction;
	}

	protected void encode(final ChannelHandlerContext p_encode_1_, final Packet p_encode_2_, final ByteBuf p_encode_3_)
			throws IOException {
		final Integer packetID = ((EnumConnectionState) p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState)
				.get()).getPacketId(direction, p_encode_2_);

		if (logger.isDebugEnabled()) {
			logger.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}",
					new Object[] { p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), packetID,
							p_encode_2_.getClass().getName() });
		}

		if (packetID == null) {
			throw new IOException("Can\'t serialize unregistered packet");
		} else {
			final PacketBuffer packetBuf = new PacketBuffer(p_encode_3_);
			packetBuf.writeVarIntToBuffer(packetID);

			try {
				p_encode_2_.writePacketData(packetBuf);
			} catch (final Throwable var7) {
				logger.error(var7);
			}
		}
	}

	@Override
	protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_)
			throws IOException {
		this.encode(p_encode_1_, (Packet) p_encode_2_, p_encode_3_);
	}
}
