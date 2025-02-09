package net.minecraft.client.resources;

import net.minecraft.util.JsonUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class ResourceIndex {

public static final int EaZy = 894;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_152783_a = LogManager.getLogger();
	private final Map field_152784_b = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00001831";

	public ResourceIndex(final File p_i1047_1_, final String p_i1047_2_) {
		if (p_i1047_2_ != null) {
			final File var3 = new File(p_i1047_1_, "objects");
			final File var4 = new File(p_i1047_1_, "indexes/" + p_i1047_2_ + ".json");
			BufferedReader var5 = null;

			try {
				var5 = Files.newReader(var4, Charsets.UTF_8);
				final JsonObject var6 = new JsonParser().parse(var5).getAsJsonObject();
				final JsonObject var7 = JsonUtils.getJsonObjectFieldOrDefault(var6, "objects", (JsonObject) null);

				if (var7 != null) {
					final Iterator var8 = var7.entrySet().iterator();

					while (var8.hasNext()) {
						final Entry var9 = (Entry) var8.next();
						final JsonObject var10 = (JsonObject) var9.getValue();
						final String var11 = (String) var9.getKey();
						final String[] var12 = var11.split("/", 2);
						final String var13 = var12.length == 1 ? var12[0] : var12[0] + ":" + var12[1];
						final String var14 = JsonUtils.getJsonObjectStringFieldValue(var10, "hash");
						final File var15 = new File(var3, var14.substring(0, 2) + "/" + var14);
						field_152784_b.put(var13, var15);
					}
				}
			} catch (final JsonParseException var20) {
				field_152783_a.error("Unable to parse resource index file: " + var4);
			} catch (final FileNotFoundException var21) {
				field_152783_a.error("Can\'t find the resource index file: " + var4);
			}
			finally {
				IOUtils.closeQuietly(var5);
			}
		}
	}

	public Map func_152782_a() {
		return field_152784_b;
	}
}
