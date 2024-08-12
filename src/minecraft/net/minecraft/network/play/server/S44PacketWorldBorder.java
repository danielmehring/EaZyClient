package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.border.WorldBorder;

import java.io.IOException;

public class S44PacketWorldBorder implements Packet {

public static final int EaZy = 1467;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private S44PacketWorldBorder.Action field_179795_a;
	private int field_179793_b;
	private double field_179794_c;
	private double field_179791_d;
	private double field_179792_e;
	private double field_179789_f;
	private long field_179790_g;
	private int field_179796_h;
	private int field_179797_i;
	// private static final String __OBFID = "http://https://fuckuskid00002292";

	public S44PacketWorldBorder() {}

	public S44PacketWorldBorder(final WorldBorder p_i45962_1_, final S44PacketWorldBorder.Action p_i45962_2_) {
		field_179795_a = p_i45962_2_;
		field_179794_c = p_i45962_1_.getCenterX();
		field_179791_d = p_i45962_1_.getCenterZ();
		field_179789_f = p_i45962_1_.getDiameter();
		field_179792_e = p_i45962_1_.getTargetSize();
		field_179790_g = p_i45962_1_.getTimeUntilTarget();
		field_179793_b = p_i45962_1_.getSize();
		field_179797_i = p_i45962_1_.getWarningDistance();
		field_179796_h = p_i45962_1_.getWarningTime();
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179795_a = (S44PacketWorldBorder.Action) data.readEnumValue(S44PacketWorldBorder.Action.class);

		switch (S44PacketWorldBorder.SwitchAction.field_179947_a[field_179795_a.ordinal()]) {
			case 1:
				field_179792_e = data.readDouble();
				break;

			case 2:
				field_179789_f = data.readDouble();
				field_179792_e = data.readDouble();
				field_179790_g = data.readVarLong();
				break;

			case 3:
				field_179794_c = data.readDouble();
				field_179791_d = data.readDouble();
				break;

			case 4:
				field_179797_i = data.readVarIntFromBuffer();
				break;

			case 5:
				field_179796_h = data.readVarIntFromBuffer();
				break;

			case 6:
				field_179794_c = data.readDouble();
				field_179791_d = data.readDouble();
				field_179789_f = data.readDouble();
				field_179792_e = data.readDouble();
				field_179790_g = data.readVarLong();
				field_179793_b = data.readVarIntFromBuffer();
				field_179797_i = data.readVarIntFromBuffer();
				field_179796_h = data.readVarIntFromBuffer();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeEnumValue(field_179795_a);

		switch (S44PacketWorldBorder.SwitchAction.field_179947_a[field_179795_a.ordinal()]) {
			case 1:
				data.writeDouble(field_179792_e);
				break;

			case 2:
				data.writeDouble(field_179789_f);
				data.writeDouble(field_179792_e);
				data.writeVarLong(field_179790_g);
				break;

			case 3:
				data.writeDouble(field_179794_c);
				data.writeDouble(field_179791_d);
				break;

			case 4:
				data.writeVarIntToBuffer(field_179797_i);
				break;

			case 5:
				data.writeVarIntToBuffer(field_179796_h);
				break;

			case 6:
				data.writeDouble(field_179794_c);
				data.writeDouble(field_179791_d);
				data.writeDouble(field_179789_f);
				data.writeDouble(field_179792_e);
				data.writeVarLong(field_179790_g);
				data.writeVarIntToBuffer(field_179793_b);
				data.writeVarIntToBuffer(field_179797_i);
				data.writeVarIntToBuffer(field_179796_h);
		}
	}

	public void func_179787_a(final INetHandlerPlayClient p_179787_1_) {
		p_179787_1_.func_175093_a(this);
	}

	public void func_179788_a(final WorldBorder p_179788_1_) {
		switch (S44PacketWorldBorder.SwitchAction.field_179947_a[field_179795_a.ordinal()]) {
			case 1:
				p_179788_1_.setTransition(field_179792_e);
				break;

			case 2:
				p_179788_1_.setTransition(field_179789_f, field_179792_e, field_179790_g);
				break;

			case 3:
				p_179788_1_.setCenter(field_179794_c, field_179791_d);
				break;

			case 4:
				p_179788_1_.setWarningDistance(field_179797_i);
				break;

			case 5:
				p_179788_1_.setWarningTime(field_179796_h);
				break;

			case 6:
				p_179788_1_.setCenter(field_179794_c, field_179791_d);

				if (field_179790_g > 0L) {
					p_179788_1_.setTransition(field_179789_f, field_179792_e, field_179790_g);
				} else {
					p_179788_1_.setTransition(field_179792_e);
				}

				p_179788_1_.setSize(field_179793_b);
				p_179788_1_.setWarningDistance(field_179797_i);
				p_179788_1_.setWarningTime(field_179796_h);
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179787_a((INetHandlerPlayClient) handler);
	}

	public static enum Action {
		SET_SIZE("SET_SIZE", 0), LERP_SIZE("LERP_SIZE", 1), SET_CENTER("SET_CENTER", 2), INITIALIZE("INITIALIZE",
				3), SET_WARNING_TIME("SET_WARNING_TIME", 4), SET_WARNING_BLOCKS("SET_WARNING_BLOCKS", 5);

		private Action(final String p_i45961_1_, final int p_i45961_2_) {}
	}

	static final class SwitchAction {
		static final int[] field_179947_a = new int[S44PacketWorldBorder.Action.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002291";

		static {
			try {
				field_179947_a[S44PacketWorldBorder.Action.SET_SIZE.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_179947_a[S44PacketWorldBorder.Action.LERP_SIZE.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_179947_a[S44PacketWorldBorder.Action.SET_CENTER.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_179947_a[S44PacketWorldBorder.Action.SET_WARNING_BLOCKS.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_179947_a[S44PacketWorldBorder.Action.SET_WARNING_TIME.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_179947_a[S44PacketWorldBorder.Action.INITIALIZE.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
