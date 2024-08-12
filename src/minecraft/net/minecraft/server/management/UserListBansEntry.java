package net.minecraft.server.management;

import java.util.Date;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListBansEntry extends BanEntry {

public static final int EaZy = 1545;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001872";

	public UserListBansEntry(final GameProfile p_i1134_1_) {
		this(p_i1134_1_, (Date) null, (String) null, (Date) null, (String) null);
	}

	public UserListBansEntry(final GameProfile p_i1135_1_, final Date p_i1135_2_, final String p_i1135_3_,
			final Date p_i1135_4_, final String p_i1135_5_) {
		super(p_i1135_1_, p_i1135_4_, p_i1135_3_, p_i1135_4_, p_i1135_5_);
	}

	public UserListBansEntry(final JsonObject p_i1136_1_) {
		super(func_152648_b(p_i1136_1_), p_i1136_1_);
	}

	@Override
	protected void onSerialization(final JsonObject data) {
		if (getValue() != null) {
			data.addProperty("uuid",
					((GameProfile) getValue()).getId() == null ? "" : ((GameProfile) getValue()).getId().toString());
			data.addProperty("name", ((GameProfile) getValue()).getName());
			super.onSerialization(data);
		}
	}

	private static GameProfile func_152648_b(final JsonObject p_152648_0_) {
		if (p_152648_0_.has("uuid") && p_152648_0_.has("name")) {
			final String var1 = p_152648_0_.get("uuid").getAsString();
			UUID var2;

			try {
				var2 = UUID.fromString(var1);
			} catch (final Throwable var4) {
				return null;
			}

			return new GameProfile(var2, p_152648_0_.get("name").getAsString());
		} else {
			return null;
		}
	}
}
