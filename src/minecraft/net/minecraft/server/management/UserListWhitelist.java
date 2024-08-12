package net.minecraft.server.management;

import java.io.File;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListWhitelist extends UserList {

public static final int EaZy = 1549;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001871";

	public UserListWhitelist(final File p_i1132_1_) {
		super(p_i1132_1_);
	}

	@Override
	protected UserListEntry createEntry(final JsonObject entryData) {
		return new UserListWhitelistEntry(entryData);
	}

	@Override
	public String[] getKeys() {
		final String[] var1 = new String[getValues().size()];
		int var2 = 0;
		UserListWhitelistEntry var4;

		for (final Iterator var3 = getValues().values().iterator(); var3
				.hasNext(); var1[var2++] = ((GameProfile) var4.getValue()).getName()) {
			var4 = (UserListWhitelistEntry) var3.next();
		}

		return var1;
	}

	protected String func_152704_b(final GameProfile p_152704_1_) {
		return p_152704_1_.getId().toString();
	}

	public GameProfile func_152706_a(final String p_152706_1_) {
		final Iterator var2 = getValues().values().iterator();
		UserListWhitelistEntry var3;

		do {
			if (!var2.hasNext()) {
				return null;
			}

			var3 = (UserListWhitelistEntry) var2.next();
		}
		while (!p_152706_1_.equalsIgnoreCase(((GameProfile) var3.getValue()).getName()));

		return (GameProfile) var3.getValue();
	}

	/**
	 * Gets the key value for the given object
	 */
	@Override
	protected String getObjectKey(final Object obj) {
		return func_152704_b((GameProfile) obj);
	}
}
