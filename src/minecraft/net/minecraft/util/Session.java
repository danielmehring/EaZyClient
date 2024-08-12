package net.minecraft.util;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;

public class Session {

public static final int EaZy = 1651;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public String username;
	public String playerID;
	public String token;
	public Session.Type sessionType;

	public Session(final String username, final String uuid, final String accessToken, final String type) {
		this.username = username;
		playerID = uuid;
		token = accessToken;
		sessionType = Session.Type.setSessionType(type);
	}

	public String getSessionID() {
		return "token:" + token + ":" + playerID;
	}

	public String getPlayerID() {
		return playerID;
	}

	public String getUsername() {
		return username;
	}

	public String getToken() {
		return token;
	}

	public GameProfile getProfile() {
		try {
			final UUID var1 = UUIDTypeAdapter.fromString(getPlayerID());
			return new GameProfile(var1, getUsername());
		} catch (final IllegalArgumentException var2) {
			return new GameProfile((UUID) null, getUsername());
		}
	}

	/**
	 * Returns either 'legacy' or 'mojang' whether the account is migrated or
	 * not
	 */
	public Session.Type getSessionType() {
		return sessionType;
	}

	public static enum Type {
		LEGACY("LEGACY", 0, "legacy"), MOJANG("MOJANG", 1, "mojang");
		private static final Map field_152425_c = Maps.newHashMap();
		private final String sessionType;

		private Type(final String p_i1096_1_, final int p_i1096_2_, final String p_i1096_3_) {
			sessionType = p_i1096_3_;
		}

		public static Session.Type setSessionType(final String p_152421_0_) {
			return (Session.Type) field_152425_c.get(p_152421_0_.toLowerCase());
		}

		static {
			final Session.Type[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final Session.Type var3 = var0[var2];
				field_152425_c.put(var3.sessionType, var3);
			}
		}
	}
}
