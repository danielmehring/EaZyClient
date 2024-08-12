package net.minecraft.network;

import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {

public static final int EaZy = 1375;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002306";

	public static void handlePacketWhileBaum(final Packet p_180031_0_, final INetHandler p_180031_1_,
			final IThreadListener p_180031_2_) {
		if (!p_180031_2_.isCallingFromMinecraftThread()) {
			p_180031_2_.addScheduledTask(new Runnable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002305";
				@Override
				public void run() {
					p_180031_0_.processPacket(p_180031_1_);
				}
			});
			throw ThreadQuickExitException.field_179886_a;
		}
	}
}
