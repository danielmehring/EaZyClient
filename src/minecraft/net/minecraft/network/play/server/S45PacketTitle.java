package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

import java.io.IOException;

public class S45PacketTitle implements Packet {

public static final int EaZy = 1468;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private S45PacketTitle.Type field_179812_a;
	public IChatComponent textComponent;
	private int field_179811_c;
	private int field_179808_d;
	private int field_179809_e;
	// private static final String __OBFID = "http://https://fuckuskid00002287";

	public S45PacketTitle() {}

	public S45PacketTitle(final S45PacketTitle.Type p_i45953_1_, final IChatComponent p_i45953_2_) {
		this(p_i45953_1_, p_i45953_2_, -1, -1, -1);
	}

	public S45PacketTitle(final int p_i45954_1_, final int p_i45954_2_, final int p_i45954_3_) {
		this(S45PacketTitle.Type.TIMES, (IChatComponent) null, p_i45954_1_, p_i45954_2_, p_i45954_3_);
	}

	public S45PacketTitle(final S45PacketTitle.Type p_i45955_1_, final IChatComponent p_i45955_2_,
			final int p_i45955_3_, final int p_i45955_4_, final int p_i45955_5_) {
		field_179812_a = p_i45955_1_;
		textComponent = p_i45955_2_;
		field_179811_c = p_i45955_3_;
		field_179808_d = p_i45955_4_;
		field_179809_e = p_i45955_5_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179812_a = (S45PacketTitle.Type) data.readEnumValue(S45PacketTitle.Type.class);

		if (field_179812_a == S45PacketTitle.Type.TITLE || field_179812_a == S45PacketTitle.Type.SUBTITLE) {
			textComponent = data.readChatComponent();
		}

		if (field_179812_a == S45PacketTitle.Type.TIMES) {
			field_179811_c = data.readInt();
			field_179808_d = data.readInt();
			field_179809_e = data.readInt();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeEnumValue(field_179812_a);

		if (field_179812_a == S45PacketTitle.Type.TITLE || field_179812_a == S45PacketTitle.Type.SUBTITLE) {
			data.writeChatComponent(textComponent);
		}

		if (field_179812_a == S45PacketTitle.Type.TIMES) {
			data.writeInt(field_179811_c);
			data.writeInt(field_179808_d);
			data.writeInt(field_179809_e);
		}
	}

	public void func_179802_a(final INetHandlerPlayClient p_179802_1_) {
		p_179802_1_.func_175099_a(this);
	}

	public S45PacketTitle.Type func_179807_a() {
		return field_179812_a;
	}

	public IChatComponent func_179805_b() {
		return textComponent;
	}

	public int func_179806_c() {
		return field_179811_c;
	}

	public int func_179804_d() {
		return field_179808_d;
	}

	public int func_179803_e() {
		return field_179809_e;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179802_a((INetHandlerPlayClient) handler);
	}

	public static enum Type {
		TITLE("TITLE", 0), SUBTITLE("SUBTITLE", 1), TIMES("TIMES", 2), CLEAR("CLEAR", 3), RESET("RESET", 4);

		private Type(final String p_i45952_1_, final int p_i45952_2_) {}

		public static S45PacketTitle.Type func_179969_a(final String p_179969_0_) {
			final S45PacketTitle.Type[] var1 = values();
			final int var2 = var1.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				final S45PacketTitle.Type var4 = var1[var3];

				if (var4.name().equalsIgnoreCase(p_179969_0_)) {
					return var4;
				}
			}

			return TITLE;
		}

		public static String[] func_179971_a() {
			final String[] var0 = new String[values().length];
			int var1 = 0;
			final S45PacketTitle.Type[] var2 = values();
			final int var3 = var2.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				final S45PacketTitle.Type var5 = var2[var4];
				var0[var1++] = var5.name().toLowerCase();
			}

			return var0;
		}
	}
}
