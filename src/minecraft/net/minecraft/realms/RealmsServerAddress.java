package net.minecraft.realms;

import net.minecraft.client.multiplayer.ServerAddress;

public class RealmsServerAddress {

public static final int EaZy = 1512;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String host;
	private final int port;
	// private static final String __OBFID = "http://https://fuckuskid00001864";

	protected RealmsServerAddress(final String p_i1121_1_, final int p_i1121_2_) {
		host = p_i1121_1_;
		port = p_i1121_2_;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public static RealmsServerAddress parseString(final String p_parseString_0_) {
		final ServerAddress var1 = ServerAddress.func_78860_a(p_parseString_0_);
		return new RealmsServerAddress(var1.getIP(), var1.getPort());
	}
}
