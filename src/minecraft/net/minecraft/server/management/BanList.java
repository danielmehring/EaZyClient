package net.minecraft.server.management;

import java.io.File;
import java.net.SocketAddress;

import com.google.gson.JsonObject;

public class BanList extends UserList {

public static final int EaZy = 1535;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001396";

	public BanList(final File bansFile) {
		super(bansFile);
	}

	@Override
	protected UserListEntry createEntry(final JsonObject entryData) {
		return new IPBanEntry(entryData);
	}

	public boolean isBanned(final SocketAddress address) {
		final String var2 = addressToString(address);
		return hasEntry(var2);
	}

	public IPBanEntry getBanEntry(final SocketAddress address) {
		final String var2 = addressToString(address);
		return (IPBanEntry) getEntry(var2);
	}

	private String addressToString(final SocketAddress address) {
		String var2 = address.toString();

		if (var2.contains("/")) {
			var2 = var2.substring(var2.indexOf(47) + 1);
		}

		if (var2.contains(":")) {
			var2 = var2.substring(0, var2.indexOf(58));
		}

		return var2;
	}
}
