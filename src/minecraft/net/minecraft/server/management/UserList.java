package net.minecraft.server.management;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UserList {

public static final int EaZy = 1543;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected static final Logger logger = LogManager.getLogger();
	protected final Gson gson;
	private final File saveFile;
	private final Map values = Maps.newHashMap();
	private boolean lanServer = true;
	private static final ParameterizedType saveFileFormat = new ParameterizedType() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001875";
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { UserListEntry.class };
		}

		@Override
		public Type getRawType() {
			return List.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	};
	// private static final String __OBFID = "http://https://fuckuskid00001876";

	public UserList(final File saveFile) {
		this.saveFile = saveFile;
		final GsonBuilder var2 = new GsonBuilder().setPrettyPrinting();
		var2.registerTypeHierarchyAdapter(UserListEntry.class, new UserList.Serializer(null));
		gson = var2.create();
	}

	public boolean isLanServer() {
		return lanServer;
	}

	public void setLanServer(final boolean state) {
		lanServer = state;
	}

	/**
	 * Adds an entry to the list
	 */
	public void addEntry(final UserListEntry entry) {
		values.put(getObjectKey(entry.getValue()), entry);

		try {
			writeChanges();
		} catch (final IOException var3) {
			logger.warn("Could not save the list after adding a user.", var3);
		}
	}

	public UserListEntry getEntry(final Object obj) {
		removeExpired();
		return (UserListEntry) values.get(getObjectKey(obj));
	}

	public void removeEntry(final Object p_152684_1_) {
		values.remove(getObjectKey(p_152684_1_));

		try {
			writeChanges();
		} catch (final IOException var3) {
			logger.warn("Could not save the list after removing a user.", var3);
		}
	}

	public String[] getKeys() {
		return (String[]) values.keySet().toArray(new String[values.size()]);
	}

	/**
	 * Gets the key value for the given object
	 */
	protected String getObjectKey(final Object obj) {
		return obj.toString();
	}

	protected boolean hasEntry(final Object entry) {
		return values.containsKey(getObjectKey(entry));
	}

	/**
	 * Removes expired bans from the list. Never actually does anything since
	 * UserListEntry#hasBanExpired always returns false. Appears to be an effort
	 * by Mojang to add temp ban functionality. (1.7.10)
	 */
	private void removeExpired() {
		final ArrayList var1 = Lists.newArrayList();
		Iterator var2 = values.values().iterator();

		while (var2.hasNext()) {
			final UserListEntry var3 = (UserListEntry) var2.next();

			if (var3.hasBanExpired()) {
				var1.add(var3.getValue());
			}
		}

		var2 = var1.iterator();

		while (var2.hasNext()) {
			final Object var4 = var2.next();
			values.remove(var4);
		}
	}

	protected UserListEntry createEntry(final JsonObject entryData) {
		return new UserListEntry((Object) null, entryData);
	}

	protected Map getValues() {
		return values;
	}

	public void writeChanges() throws IOException {
		final Collection var1 = values.values();
		final String var2 = gson.toJson(var1);
		BufferedWriter var3 = null;

		try {
			var3 = Files.newWriter(saveFile, Charsets.UTF_8);
			var3.write(var2);
		}
		finally {
			IOUtils.closeQuietly(var3);
		}
	}

	class Serializer implements JsonDeserializer, JsonSerializer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001874";

		private Serializer() {}

		public JsonElement serializeEntry(final UserListEntry p_152751_1_, final Type p_152751_2_,
				final JsonSerializationContext p_152751_3_) {
			final JsonObject var4 = new JsonObject();
			p_152751_1_.onSerialization(var4);
			return var4;
		}

		public UserListEntry deserializeEntry(final JsonElement p_152750_1_, final Type p_152750_2_,
				final JsonDeserializationContext p_152750_3_) {
			if (p_152750_1_.isJsonObject()) {
				final JsonObject var4 = p_152750_1_.getAsJsonObject();
				final UserListEntry var5 = createEntry(var4);
				return var5;
			} else {
				return null;
			}
		}

		@Override
		public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_,
				final JsonSerializationContext p_serialize_3_) {
			return serializeEntry((UserListEntry) p_serialize_1_, p_serialize_2_, p_serialize_3_);
		}

		@Override
		public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
				final JsonDeserializationContext p_deserialize_3_) {
			return deserializeEntry(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
		}

		Serializer(final Object p_i1141_2_) {
			this();
		}
	}
}
