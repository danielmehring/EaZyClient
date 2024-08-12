package net.minecraft.client.resources.data;

import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class IMetadataSerializer {

public static final int EaZy = 863;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final IRegistry metadataSectionSerializerRegistry = new RegistrySimple();
	private final GsonBuilder gsonBuilder = new GsonBuilder();

	/**
	 * Cached Gson instance. Set to null when more sections are registered, and
	 * then re-created from the builder.
	 */
	private Gson gson;
	// private static final String __OBFID = "http://https://fuckuskid00001101";

	public IMetadataSerializer() {
		gsonBuilder.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
		gsonBuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
		gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
	}

	public void registerMetadataSectionType(final IMetadataSectionSerializer p_110504_1_, final Class p_110504_2_) {
		metadataSectionSerializerRegistry.putObject(p_110504_1_.getSectionName(),
				new IMetadataSerializer.Registration(p_110504_1_, p_110504_2_, null));
		gsonBuilder.registerTypeAdapter(p_110504_2_, p_110504_1_);
		gson = null;
	}

	public IMetadataSection parseMetadataSection(final String p_110503_1_, final JsonObject p_110503_2_) {
		if (p_110503_1_ == null) {
			throw new IllegalArgumentException("Metadata section name cannot be null");
		} else if (!p_110503_2_.has(p_110503_1_)) {
			return null;
		} else if (!p_110503_2_.get(p_110503_1_).isJsonObject()) {
			throw new IllegalArgumentException("Invalid metadata for \'" + p_110503_1_ + "\' - expected object, found "
					+ p_110503_2_.get(p_110503_1_));
		} else {
			final IMetadataSerializer.Registration var3 = (IMetadataSerializer.Registration) metadataSectionSerializerRegistry
					.getObject(p_110503_1_);

			if (var3 == null) {
				throw new IllegalArgumentException(
						"Don\'t know how to handle metadata section \'" + p_110503_1_ + "\'");
			} else {
				return (IMetadataSection) getGson().fromJson(p_110503_2_.getAsJsonObject(p_110503_1_),
						var3.field_110500_b);
			}
		}
	}

	/**
	 * Returns a Gson instance with type adapters registered for metadata
	 * sections.
	 */
	private Gson getGson() {
		if (gson == null) {
			gson = gsonBuilder.create();
		}

		return gson;
	}

	class Registration {
		final IMetadataSectionSerializer field_110502_a;
		final Class field_110500_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001103";

		private Registration(final IMetadataSectionSerializer p_i1305_2_, final Class p_i1305_3_) {
			field_110502_a = p_i1305_2_;
			field_110500_b = p_i1305_3_;
		}

		Registration(final IMetadataSectionSerializer p_i1306_2_, final Class p_i1306_3_, final Object p_i1306_4_) {
			this(p_i1306_2_, p_i1306_3_);
		}
	}
}
