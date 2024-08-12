package net.minecraft.client.resources;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SimpleResource implements IResource {

public static final int EaZy = 900;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Map mapMetadataSections = Maps.newHashMap();
	private final String field_177242_b;
	private final ResourceLocation srResourceLocation;
	private final InputStream resourceInputStream;
	private final InputStream mcmetaInputStream;
	private final IMetadataSerializer srMetadataSerializer;
	private boolean mcmetaJsonChecked;
	private JsonObject mcmetaJson;
	// private static final String __OBFID = "http://https://fuckuskid00001093";

	public SimpleResource(final String p_i46090_1_, final ResourceLocation p_i46090_2_, final InputStream p_i46090_3_,
			final InputStream p_i46090_4_, final IMetadataSerializer p_i46090_5_) {
		field_177242_b = p_i46090_1_;
		srResourceLocation = p_i46090_2_;
		resourceInputStream = p_i46090_3_;
		mcmetaInputStream = p_i46090_4_;
		srMetadataSerializer = p_i46090_5_;
	}

	@Override
	public ResourceLocation func_177241_a() {
		return srResourceLocation;
	}

	@Override
	public InputStream getInputStream() {
		return resourceInputStream;
	}

	@Override
	public boolean hasMetadata() {
		return mcmetaInputStream != null;
	}

	@Override
	public IMetadataSection getMetadata(final String p_110526_1_) {
		if (!hasMetadata()) {
			return null;
		} else {
			if (mcmetaJson == null && !mcmetaJsonChecked) {
				mcmetaJsonChecked = true;
				BufferedReader var2 = null;

				try {
					var2 = new BufferedReader(new InputStreamReader(mcmetaInputStream));
					mcmetaJson = new JsonParser().parse(var2).getAsJsonObject();
				}
				finally {
					IOUtils.closeQuietly(var2);
				}
			}

			IMetadataSection var6 = (IMetadataSection) mapMetadataSections.get(p_110526_1_);

			if (var6 == null) {
				var6 = srMetadataSerializer.parseMetadataSection(p_110526_1_, mcmetaJson);
			}

			return var6;
		}
	}

	@Override
	public String func_177240_d() {
		return field_177242_b;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof SimpleResource)) {
			return false;
		} else {
			final SimpleResource var2 = (SimpleResource) p_equals_1_;

			if (srResourceLocation != null) {
				if (!srResourceLocation.equals(var2.srResourceLocation)) {
					return false;
				}
			} else if (var2.srResourceLocation != null) {
				return false;
			}

			if (field_177242_b != null) {
				if (!field_177242_b.equals(var2.field_177242_b)) {
					return false;
				}
			} else if (var2.field_177242_b != null) {
				return false;
			}

			return true;
		}
	}

	@Override
	public int hashCode() {
		int var1 = field_177242_b != null ? field_177242_b.hashCode() : 0;
		var1 = 31 * var1 + (srResourceLocation != null ? srResourceLocation.hashCode() : 0);
		return var1;
	}
}
