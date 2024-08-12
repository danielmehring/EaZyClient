package net.minecraft.server.management;

import java.io.File;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListBans extends UserList {

public static final int EaZy = 1544;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001873";

	public UserListBans(final File bansFile) {
		super(bansFile);
	}

	@Override
	protected UserListEntry createEntry(final JsonObject entryData) {
		return new UserListBansEntry(entryData);
	}

	public boolean isBanned(final GameProfile profile) {
		return hasEntry(profile);
	}

	@Override
	public String[] getKeys() {
		final String[] var1 = new String[getValues().size()];
		int var2 = 0;
		UserListBansEntry var4;

		for (final Iterator var3 = getValues().values().iterator(); var3
				.hasNext(); var1[var2++] = ((GameProfile) var4.getValue()).getName()) {
			var4 = (UserListBansEntry) var3.next();
		}

		return var1;
	}

	protected String getProfileId(final GameProfile profile) {
		return profile.getId().toString();
	}

	public GameProfile isUsernameBanned(final String username) {
		final Iterator var2 = getValues().values().iterator();
		UserListBansEntry var3;

		do {
			if (!var2.hasNext()) {
				return null;
			}

			var3 = (UserListBansEntry) var2.next();
		}
		while (!username.equalsIgnoreCase(((GameProfile) var3.getValue()).getName()));

		return (GameProfile) var3.getValue();
	}

	/**
	 * Gets the key value for the given object
	 */
	@Override
	protected String getObjectKey(final Object obj) {
		return getProfileId((GameProfile) obj);
	}
}
