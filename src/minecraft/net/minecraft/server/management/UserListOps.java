package net.minecraft.server.management;

import java.io.File;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListOps extends UserList {

public static final int EaZy = 1547;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001879";

	public UserListOps(final File p_i1152_1_) {
		super(p_i1152_1_);
	}

	@Override
	protected UserListEntry createEntry(final JsonObject entryData) {
		return new UserListOpsEntry(entryData);
	}

	@Override
	public String[] getKeys() {
		final String[] var1 = new String[getValues().size()];
		int var2 = 0;
		UserListOpsEntry var4;

		for (final Iterator var3 = getValues().values().iterator(); var3
				.hasNext(); var1[var2++] = ((GameProfile) var4.getValue()).getName()) {
			var4 = (UserListOpsEntry) var3.next();
		}

		return var1;
	}

	protected String func_152699_b(final GameProfile p_152699_1_) {
		return p_152699_1_.getId().toString();
	}

	/**
	 * Gets the GameProfile of based on the provided username.
	 */
	public GameProfile getGameProfileFromName(final String p_152700_1_) {
		final Iterator var2 = getValues().values().iterator();
		UserListOpsEntry var3;

		do {
			if (!var2.hasNext()) {
				return null;
			}

			var3 = (UserListOpsEntry) var2.next();
		}
		while (!p_152700_1_.equalsIgnoreCase(((GameProfile) var3.getValue()).getName()));

		return (GameProfile) var3.getValue();
	}

	/**
	 * Gets the key value for the given object
	 */
	@Override
	protected String getObjectKey(final Object obj) {
		return func_152699_b((GameProfile) obj);
	}
}
