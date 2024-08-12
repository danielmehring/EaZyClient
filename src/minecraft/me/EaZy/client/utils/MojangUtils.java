package me.EaZy.client.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MojangUtils {

	public static final int EaZy = 226;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static UUID getUUID(final String name) {
		try {
			final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			final InputStream response = url.openStream();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
			String uuidStr = "";
			for (String line; (line = reader.readLine()) != null;) {
				uuidStr = line.toLowerCase().replace("{\"id\":\"", "")
						.replace("\",\"name\":\"" + name.toLowerCase(), "").replace("\",\"legacy\":true}", "")
						.replace("\",\"legacy\":false}", "").replace("\"}", "");
			}
			final String a = uuidStr.substring(0, 8);
			final String b = uuidStr.substring(8, 12);
			final String c = uuidStr.substring(12, 16);
			final String d = uuidStr.substring(16, 20);
			final String e = uuidStr.substring(20, 32);
			return UUID.fromString(a + "-" + b + "-" + c + "-" + d + "-" + e);
		} catch (final Exception e) {
			return null;
		}
	}

}
