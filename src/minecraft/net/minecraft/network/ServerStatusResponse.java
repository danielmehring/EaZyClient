package net.minecraft.network;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;

public class ServerStatusResponse {

public static final int EaZy = 1473;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private IChatComponent serverMotd;
	private ServerStatusResponse.PlayerCountData playerCount;
	private ServerStatusResponse.MinecraftProtocolVersionIdentifier protocolVersion;
	private String favicon;
	// private static final String __OBFID = "http://https://fuckuskid00001385";

	public IChatComponent getServerDescription() {
		return serverMotd;
	}

	public void setServerDescription(final IChatComponent motd) {
		serverMotd = motd;
	}

	public ServerStatusResponse.PlayerCountData getPlayerCountData() {
		return playerCount;
	}

	public void setPlayerCountData(final ServerStatusResponse.PlayerCountData countData) {
		playerCount = countData;
	}

	public ServerStatusResponse.MinecraftProtocolVersionIdentifier getProtocolVersionInfo() {
		return protocolVersion;
	}

	public void setProtocolVersionInfo(
			final ServerStatusResponse.MinecraftProtocolVersionIdentifier protocolVersionData) {
		protocolVersion = protocolVersionData;
	}

	public void setFavicon(final String faviconBlob) {
		favicon = faviconBlob;
	}

	public String getFavicon() {
		return favicon;
	}

	public static class MinecraftProtocolVersionIdentifier {
		private final String name;
		private final int protocol;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001389";

		public MinecraftProtocolVersionIdentifier(final String nameIn, final int protocolIn) {
			name = nameIn;
			protocol = protocolIn;
		}

		public String getName() {
			return name;
		}

		public int getProtocol() {
			return protocol;
		}

		public static class Serializer implements JsonDeserializer, JsonSerializer {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001390";

			public ServerStatusResponse.MinecraftProtocolVersionIdentifier deserialize1(
					final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
					final JsonDeserializationContext p_deserialize_3_) {
				final JsonObject var4 = JsonUtils.getElementAsJsonObject(p_deserialize_1_, "version");
				return new ServerStatusResponse.MinecraftProtocolVersionIdentifier(
						JsonUtils.getJsonObjectStringFieldValue(var4, "name"),
						JsonUtils.getJsonObjectIntegerFieldValue(var4, "protocol"));
			}

			public JsonElement serialize(final ServerStatusResponse.MinecraftProtocolVersionIdentifier p_serialize_1_,
					final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
				final JsonObject var4 = new JsonObject();
				var4.addProperty("name", p_serialize_1_.getName());
				var4.addProperty("protocol", p_serialize_1_.getProtocol());
				return var4;
			}

			@Override
			public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_,
					final JsonSerializationContext p_serialize_3_) {
				return this.serialize((ServerStatusResponse.MinecraftProtocolVersionIdentifier) p_serialize_1_,
						p_serialize_2_, p_serialize_3_);
			}

			@Override
			public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
					final JsonDeserializationContext p_deserialize_3_) {
				return deserialize1(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
			}
		}
	}

	public static class PlayerCountData {
		private final int maxPlayers;
		private final int onlinePlayerCount;
		private GameProfile[] players;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001386";

		public PlayerCountData(final int p_i45274_1_, final int p_i45274_2_) {
			maxPlayers = p_i45274_1_;
			onlinePlayerCount = p_i45274_2_;
		}

		public int getMaxPlayers() {
			return maxPlayers;
		}

		public int getOnlinePlayerCount() {
			return onlinePlayerCount;
		}

		public GameProfile[] getPlayers() {
			return players;
		}

		public void setPlayers(final GameProfile[] playersIn) {
			players = playersIn;
		}

		public static class Serializer implements JsonDeserializer, JsonSerializer {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001387";

			public ServerStatusResponse.PlayerCountData deserialize1(final JsonElement p_deserialize_1_,
					final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
				final JsonObject var4 = JsonUtils.getElementAsJsonObject(p_deserialize_1_, "players");
				final ServerStatusResponse.PlayerCountData var5 = new ServerStatusResponse.PlayerCountData(
						JsonUtils.getJsonObjectIntegerFieldValue(var4, "max"),
						JsonUtils.getJsonObjectIntegerFieldValue(var4, "online"));

				if (JsonUtils.jsonObjectFieldTypeIsArray(var4, "sample")) {
					final JsonArray var6 = JsonUtils.getJsonObjectJsonArrayField(var4, "sample");

					if (var6.size() > 0) {
						final GameProfile[] var7 = new GameProfile[var6.size()];

						for (int var8 = 0; var8 < var7.length; ++var8) {
							final JsonObject var9 = JsonUtils.getElementAsJsonObject(var6.get(var8),
									"player[" + var8 + "]");
							final String var10 = JsonUtils.getJsonObjectStringFieldValue(var9, "id");
							var7[var8] = new GameProfile(UUID.fromString(var10),
									JsonUtils.getJsonObjectStringFieldValue(var9, "name"));
						}

						var5.setPlayers(var7);
					}
				}

				return var5;
			}

			public JsonElement serialize(final ServerStatusResponse.PlayerCountData p_serialize_1_,
					final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
				final JsonObject var4 = new JsonObject();
				var4.addProperty("max", p_serialize_1_.getMaxPlayers());
				var4.addProperty("online", p_serialize_1_.getOnlinePlayerCount());

				if (p_serialize_1_.getPlayers() != null && p_serialize_1_.getPlayers().length > 0) {
					final JsonArray var5 = new JsonArray();

					for (int var6 = 0; var6 < p_serialize_1_.getPlayers().length; ++var6) {
						final JsonObject var7 = new JsonObject();
						final UUID var8 = p_serialize_1_.getPlayers()[var6].getId();
						var7.addProperty("id", var8 == null ? "" : var8.toString());
						var7.addProperty("name", p_serialize_1_.getPlayers()[var6].getName());
						var5.add(var7);
					}

					var4.add("sample", var5);
				}

				return var4;
			}

			@Override
			public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_,
					final JsonSerializationContext p_serialize_3_) {
				return this.serialize((ServerStatusResponse.PlayerCountData) p_serialize_1_, p_serialize_2_,
						p_serialize_3_);
			}

			@Override
			public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
					final JsonDeserializationContext p_deserialize_3_) {
				return deserialize1(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
			}
		}
	}

	public static class Serializer implements JsonDeserializer, JsonSerializer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001388";

		public ServerStatusResponse deserialize1(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
				final JsonDeserializationContext p_deserialize_3_) {
			final JsonObject var4 = JsonUtils.getElementAsJsonObject(p_deserialize_1_, "status");
			final ServerStatusResponse var5 = new ServerStatusResponse();

			if (var4.has("description")) {
				var5.setServerDescription(
						(IChatComponent) p_deserialize_3_.deserialize(var4.get("description"), IChatComponent.class));
			}

			if (var4.has("players")) {
				var5.setPlayerCountData((ServerStatusResponse.PlayerCountData) p_deserialize_3_
						.deserialize(var4.get("players"), ServerStatusResponse.PlayerCountData.class));
			}

			if (var4.has("version")) {
				var5.setProtocolVersionInfo(
						(ServerStatusResponse.MinecraftProtocolVersionIdentifier) p_deserialize_3_.deserialize(
								var4.get("version"), ServerStatusResponse.MinecraftProtocolVersionIdentifier.class));
			}

			if (var4.has("favicon")) {
				var5.setFavicon(JsonUtils.getJsonObjectStringFieldValue(var4, "favicon"));
			}

			return var5;
		}

		public JsonElement serialize(final ServerStatusResponse p_serialize_1_, final Type p_serialize_2_,
				final JsonSerializationContext p_serialize_3_) {
			final JsonObject var4 = new JsonObject();

			if (p_serialize_1_.getServerDescription() != null) {
				var4.add("description", p_serialize_3_.serialize(p_serialize_1_.getServerDescription()));
			}

			if (p_serialize_1_.getPlayerCountData() != null) {
				var4.add("players", p_serialize_3_.serialize(p_serialize_1_.getPlayerCountData()));
			}

			if (p_serialize_1_.getProtocolVersionInfo() != null) {
				var4.add("version", p_serialize_3_.serialize(p_serialize_1_.getProtocolVersionInfo()));
			}

			if (p_serialize_1_.getFavicon() != null) {
				var4.addProperty("favicon", p_serialize_1_.getFavicon());
			}

			return var4;
		}

		@Override
		public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_,
				final JsonSerializationContext p_serialize_3_) {
			return this.serialize((ServerStatusResponse) p_serialize_1_, p_serialize_2_, p_serialize_3_);
		}

		@Override
		public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
				final JsonDeserializationContext p_deserialize_3_) {
			return deserialize1(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
		}
	}
}
