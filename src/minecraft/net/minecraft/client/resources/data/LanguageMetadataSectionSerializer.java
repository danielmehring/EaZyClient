package net.minecraft.client.resources.data;

import net.minecraft.client.resources.Language;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer {

public static final int EaZy = 865;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001111";

	@Override
	public LanguageMetadataSection deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
			final JsonDeserializationContext p_deserialize_3_) {
		final JsonObject var4 = p_deserialize_1_.getAsJsonObject();
		final HashSet var5 = Sets.newHashSet();
		final Iterator var6 = var4.entrySet().iterator();
		String var8;
		String var10;
		String var11;
		boolean var12;

		do {
			if (!var6.hasNext()) {
				return new LanguageMetadataSection(var5);
			}

			final Entry var7 = (Entry) var6.next();
			var8 = (String) var7.getKey();
			final JsonObject var9 = JsonUtils.getElementAsJsonObject((JsonElement) var7.getValue(), "language");
			var10 = JsonUtils.getJsonObjectStringFieldValue(var9, "region");
			var11 = JsonUtils.getJsonObjectStringFieldValue(var9, "name");
			var12 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var9, "bidirectional", false);

			if (var10.isEmpty()) {
				throw new JsonParseException("Invalid language->\'" + var8 + "\'->region: empty value");
			}

			if (var11.isEmpty()) {
				throw new JsonParseException("Invalid language->\'" + var8 + "\'->name: empty value");
			}
		}
		while (var5.add(new Language(var8, var10, var11, var12)));

		throw new JsonParseException("Duplicate language->\'" + var8 + "\' defined");
	}

	/**
	 * The name of this section type as it appears in JSON.
	 */
	@Override
	public String getSectionName() {
		return "language";
	}

}
