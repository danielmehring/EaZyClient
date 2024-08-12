package net.minecraft.network.play.server;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.CombatTracker;

import java.io.IOException;

public class S42PacketCombatEvent implements Packet {

public static final int EaZy = 1465;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public S42PacketCombatEvent.Event field_179776_a;
	public int field_179774_b;
	public int field_179775_c;
	public int field_179772_d;
	public String field_179773_e;
	// private static final String __OBFID = "http://https://fuckuskid00002299";

	public S42PacketCombatEvent() {}

	public S42PacketCombatEvent(final CombatTracker p_i45970_1_, final S42PacketCombatEvent.Event p_i45970_2_) {
		field_179776_a = p_i45970_2_;
		final EntityLivingBase var3 = p_i45970_1_.func_94550_c();

		switch (S42PacketCombatEvent.SwitchEvent.field_179944_a[p_i45970_2_.ordinal()]) {
			case 1:
				field_179772_d = p_i45970_1_.func_180134_f();
				field_179775_c = var3 == null ? -1 : var3.getEntityId();
				break;

			case 2:
				field_179774_b = p_i45970_1_.func_180135_h().getEntityId();
				field_179775_c = var3 == null ? -1 : var3.getEntityId();
				field_179773_e = p_i45970_1_.func_151521_b().getUnformattedText();
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179776_a = (S42PacketCombatEvent.Event) data.readEnumValue(S42PacketCombatEvent.Event.class);

		if (field_179776_a == S42PacketCombatEvent.Event.END_COMBAT) {
			field_179772_d = data.readVarIntFromBuffer();
			field_179775_c = data.readInt();
		} else if (field_179776_a == S42PacketCombatEvent.Event.ENTITY_DIED) {
			field_179774_b = data.readVarIntFromBuffer();
			field_179775_c = data.readInt();
			field_179773_e = data.readStringFromBuffer(32767);
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeEnumValue(field_179776_a);

		if (field_179776_a == S42PacketCombatEvent.Event.END_COMBAT) {
			data.writeVarIntToBuffer(field_179772_d);
			data.writeInt(field_179775_c);
		} else if (field_179776_a == S42PacketCombatEvent.Event.ENTITY_DIED) {
			data.writeVarIntToBuffer(field_179774_b);
			data.writeInt(field_179775_c);
			data.writeString(field_179773_e);
		}
	}

	public void func_179771_a(final INetHandlerPlayClient p_179771_1_) {
		p_179771_1_.func_175098_a(this);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179771_a((INetHandlerPlayClient) handler);
	}

	public static enum Event {
		ENTER_COMBAT("ENTER_COMBAT", 0), END_COMBAT("END_COMBAT", 1), ENTITY_DIED("ENTITY_DIED", 2);

		private Event(final String p_i45969_1_, final int p_i45969_2_) {}
	}

	static final class SwitchEvent {
		static final int[] field_179944_a = new int[S42PacketCombatEvent.Event.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002298";

		static {
			try {
				field_179944_a[S42PacketCombatEvent.Event.END_COMBAT.ordinal()] = 1;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_179944_a[S42PacketCombatEvent.Event.ENTITY_DIED.ordinal()] = 2;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
