package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import com.google.common.collect.Lists;

public class S3EPacketTeams implements Packet {

public static final int EaZy = 1461;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String field_149320_a = "";
	private String field_149318_b = "";
	private String field_149319_c = "";
	private String field_149316_d = "";
	private String field_179816_e;
	private int field_179815_f;
	private final Collection field_149317_e;
	private int field_149314_f;
	private int field_149315_g;
	// private static final String __OBFID = "http://https://fuckuskid00001334";

	public S3EPacketTeams() {
		field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
		field_179815_f = -1;
		field_149317_e = Lists.newArrayList();
	}

	public S3EPacketTeams(final ScorePlayerTeam p_i45225_1_, final int p_i45225_2_) {
		field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
		field_179815_f = -1;
		field_149317_e = Lists.newArrayList();
		field_149320_a = p_i45225_1_.getRegisteredName();
		field_149314_f = p_i45225_2_;

		if (p_i45225_2_ == 0 || p_i45225_2_ == 2) {
			field_149318_b = p_i45225_1_.func_96669_c();
			field_149319_c = p_i45225_1_.getColorPrefix();
			field_149316_d = p_i45225_1_.getColorSuffix();
			field_149315_g = p_i45225_1_.func_98299_i();
			field_179816_e = p_i45225_1_.func_178770_i().field_178830_e;
			field_179815_f = p_i45225_1_.func_178775_l().func_175746_b();
		}

		if (p_i45225_2_ == 0) {
			field_149317_e.addAll(p_i45225_1_.getMembershipCollection());
		}
	}

	public S3EPacketTeams(final ScorePlayerTeam p_i45226_1_, final Collection p_i45226_2_, final int p_i45226_3_) {
		field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
		field_179815_f = -1;
		field_149317_e = Lists.newArrayList();

		if (p_i45226_3_ != 3 && p_i45226_3_ != 4) {
			throw new IllegalArgumentException("Method must be join or leave for player constructor");
		} else if (p_i45226_2_ != null && !p_i45226_2_.isEmpty()) {
			field_149314_f = p_i45226_3_;
			field_149320_a = p_i45226_1_.getRegisteredName();
			field_149317_e.addAll(p_i45226_2_);
		} else {
			throw new IllegalArgumentException("Players cannot be null/empty");
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149320_a = data.readStringFromBuffer(16);
		field_149314_f = data.readByte();

		if (field_149314_f == 0 || field_149314_f == 2) {
			field_149318_b = data.readStringFromBuffer(32);
			field_149319_c = data.readStringFromBuffer(16);
			field_149316_d = data.readStringFromBuffer(16);
			field_149315_g = data.readByte();
			field_179816_e = data.readStringFromBuffer(32);
			field_179815_f = data.readByte();
		}

		if (field_149314_f == 0 || field_149314_f == 3 || field_149314_f == 4) {
			final int var2 = data.readVarIntFromBuffer();

			for (int var3 = 0; var3 < var2; ++var3) {
				field_149317_e.add(data.readStringFromBuffer(40));
			}
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(field_149320_a);
		data.writeByte(field_149314_f);

		if (field_149314_f == 0 || field_149314_f == 2) {
			data.writeString(field_149318_b);
			data.writeString(field_149319_c);
			data.writeString(field_149316_d);
			data.writeByte(field_149315_g);
			data.writeString(field_179816_e);
			data.writeByte(field_179815_f);
		}

		if (field_149314_f == 0 || field_149314_f == 3 || field_149314_f == 4) {
			data.writeVarIntToBuffer(field_149317_e.size());
			final Iterator var2 = field_149317_e.iterator();

			while (var2.hasNext()) {
				final String var3 = (String) var2.next();
				data.writeString(var3);
			}
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleTeams(this);
	}

	public String func_149312_c() {
		return field_149320_a;
	}

	public String func_149306_d() {
		return field_149318_b;
	}

	public String func_149311_e() {
		return field_149319_c;
	}

	public String func_149309_f() {
		return field_149316_d;
	}

	public Collection func_149310_g() {
		return field_149317_e;
	}

	public int func_149307_h() {
		return field_149314_f;
	}

	public int func_149308_i() {
		return field_149315_g;
	}

	public int func_179813_h() {
		return field_179815_f;
	}

	public String func_179814_i() {
		return field_179816_e;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
