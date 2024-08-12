package me.EaZy.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import me.EaZy.client.main.Client;
import me.EaZy.client.utils.HWID;
import net.minecraft.client.Minecraft;

public class Updater {

	public static final int EaZy = 204;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static boolean needsUpdate = false;
	public static boolean failed = false;

	public static void checkAndUpdate() throws Exception {

		// Check for Updates
		final URL url = new URL(new String(new byte[] { 120, 104, 109, 98, 119, 97 }).substring(1, 2)
				+ new String(new byte[] { 116, 119, 104, 110, 106, 108 }).substring(0, 1)
				+ new String(new byte[] { 102, 105, 116, 110, 112, 100 }).substring(2, 3)
				+ new String(new byte[] { 104, 112, 109, 102, 117, 104 }).substring(1, 2)
				+ new String(new byte[] { 99, 117, 112, 58, 97, 107 }).substring(3, 4)
				+ new String(new byte[] { 47, 109, 120, 105, 115, 118 }).substring(0, 1)
				+ new String(new byte[] { 121, 112, 105, 47, 118, 97 }).substring(3, 4)
				+ new String(new byte[] { 109, 118, 104, 110, 116, 101 }).substring(3, 4)
				+ new String(new byte[] { 106, 101, 105, 113, 109, 97 }).substring(2, 3)
				+ new String(new byte[] { 102, 97, 99, 51, 97, 118 }).substring(3, 4)
				+ new String(new byte[] { 115, 54, 105, 110, 112, 119 }).substring(1, 2)
				+ new String(new byte[] { 121, 113, 56, 114, 103, 121 }).substring(2, 3)
				+ new String(new byte[] { 116, 109, 50, 102, 104, 120 }).substring(2, 3)
				+ new String(new byte[] { 119, 99, 50, 112, 116, 119 }).substring(2, 3)
				+ new String(new byte[] { 119, 119, 114, 51, 97, 118 }).substring(3, 4)
				+ new String(new byte[] { 107, 95, 117, 116, 97, 102 }).substring(1, 2)
				+ new String(new byte[] { 115, 104, 50, 107, 109, 116 }).substring(2, 3)
				+ new String(new byte[] { 99, 103, 46, 118, 118, 118 }).substring(2, 3)
				+ new String(new byte[] { 111, 118, 115, 104, 119, 103 }).substring(1, 2)
				+ new String(new byte[] { 113, 107, 117, 119, 107, 108 }).substring(3, 4)
				+ new String(new byte[] { 115, 101, 104, 98, 111, 106 }).substring(1, 2)
				+ new String(new byte[] { 102, 98, 113, 120, 106, 107 }).substring(1, 2)
				+ new String(new byte[] { 49, 108, 115, 117, 106, 100 }).substring(0, 1)
				+ new String(new byte[] { 121, 112, 114, 109, 56, 112 }).substring(4, 5)
				+ new String(new byte[] { 46, 111, 119, 102, 110, 108 }).substring(0, 1)
				+ new String(new byte[] { 104, 110, 111, 121, 114, 106 }).substring(1, 2)
				+ new String(new byte[] { 105, 105, 100, 99, 121, 117 }).substring(0, 1)
				+ new String(new byte[] { 114, 105, 117, 108, 116, 118 }).substring(4, 5)
				+ new String(new byte[] { 99, 98, 116, 112, 114, 119 }).substring(4, 5)
				+ new String(new byte[] { 112, 101, 98, 107, 97, 105 }).substring(4, 5)
				+ new String(new byte[] { 99, 104, 100, 118, 107, 113 }).substring(2, 3)
				+ new String(new byte[] { 111, 120, 107, 112, 102, 111 }).substring(0, 1)
				+ new String(new byte[] { 108, 103, 97, 98, 46, 114 }).substring(4, 5)
				+ new String(new byte[] { 110, 112, 104, 110, 105, 120 }).substring(3, 4)
				+ new String(new byte[] { 100, 121, 101, 101, 109, 103 }).substring(2, 3)
				+ new String(new byte[] { 101, 112, 115, 116, 108, 113 }).substring(3, 4)
				+ new String(new byte[] { 108, 101, 108, 47, 107, 112 }).substring(3, 4)
				+ new String(new byte[] { 108, 113, 98, 102, 103, 106 }).substring(0, 1)
				+ new String(new byte[] { 113, 98, 97, 113, 110, 110 }).substring(2, 3)
				+ new String(new byte[] { 116, 108, 103, 100, 110, 103 }).substring(0, 1)
				+ new String(new byte[] { 109, 101, 105, 120, 105, 115 }).substring(1, 2)
				+ new String(new byte[] { 100, 115, 112, 103, 101, 107 }).substring(1, 2)
				+ new String(new byte[] { 107, 100, 110, 116, 110, 97 }).substring(3, 4)
				+ new String(new byte[] { 46, 105, 115, 109, 100, 111 }).substring(0, 1)
				+ new String(new byte[] { 104, 97, 117, 117, 118, 116 }).substring(0, 1)
				+ new String(new byte[] { 101, 116, 99, 112, 98, 117 }).substring(1, 2)
				+ new String(new byte[] { 121, 97, 109, 103, 111, 121 }).substring(2, 3)
				+ new String(new byte[] { 108, 114, 112, 98, 101, 120 }).substring(0, 1));
		final InputStream response = url.openStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
		Double newestVersion = -1.0;
		for (String line; (line = reader.readLine()) != null;) {
			try {
				newestVersion = Double.parseDouble(line);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		if (Client.currentVersionforUpdater < newestVersion) {
			needsUpdate = true;
		} else {
			System.out.println(new String(new byte[] { 118, 108, 91, 106, 105, 113 }).substring(2, 3)
					+ new String(new byte[] { 121, 116, 102, 69, 102, 97 }).substring(3, 4)
					+ new String(new byte[] { 108, 97, 115, 101, 120, 121 }).substring(1, 2)
					+ new String(new byte[] { 114, 90, 102, 119, 113, 107 }).substring(1, 2)
					+ new String(new byte[] { 115, 108, 121, 121, 111, 111 }).substring(3, 4)
					+ new String(new byte[] { 107, 108, 93, 111, 114, 104 }).substring(2, 3)
					+ new String(new byte[] { 32, 101, 112, 108, 104, 118 }).substring(0, 1)
					+ new String(new byte[] { 108, 121, 107, 78, 117, 114 }).substring(3, 4)
					+ new String(new byte[] { 106, 100, 117, 110, 111, 105 }).substring(4, 5)
					+ new String(new byte[] { 97, 112, 32, 117, 113, 116 }).substring(2, 3)
					+ new String(new byte[] { 108, 112, 99, 85, 108, 109 }).substring(3, 4)
					+ new String(new byte[] { 116, 99, 116, 112, 118, 103 }).substring(3, 4)
					+ new String(new byte[] { 100, 103, 113, 115, 108, 119 }).substring(0, 1)
					+ new String(new byte[] { 115, 97, 102, 101, 114, 102 }).substring(1, 2)
					+ new String(new byte[] { 116, 104, 97, 97, 121, 100 }).substring(0, 1)
					+ new String(new byte[] { 99, 104, 97, 103, 101, 117 }).substring(4, 5)
					+ new String(new byte[] { 108, 105, 115, 32, 112, 106 }).substring(3, 4)
					+ new String(new byte[] { 118, 102, 107, 115, 117, 120 }).substring(1, 2)
					+ new String(new byte[] { 115, 118, 97, 111, 105, 106 }).substring(3, 4)
					+ new String(new byte[] { 105, 102, 117, 113, 100, 101 }).substring(2, 3)
					+ new String(new byte[] { 103, 110, 108, 99, 117, 116 }).substring(1, 2)
					+ new String(new byte[] { 100, 117, 116, 111, 119, 98 }).substring(0, 1)
					+ new String(new byte[] { 115, 104, 108, 33, 119, 117 }).substring(3, 4));
		}

		if (needsUpdate) {

			new Thread(new String(new byte[] { 99, 69, 113, 99, 112, 119 }).substring(1, 2)
					+ new String(new byte[] { 105, 101, 97, 112, 120, 105 }).substring(2, 3)
					+ new String(new byte[] { 103, 99, 97, 90, 121, 106 }).substring(3, 4)
					+ new String(new byte[] { 109, 105, 109, 121, 114, 112 }).substring(3, 4)
					+ new String(new byte[] { 114, 85, 120, 100, 99, 117 }).substring(1, 2)
					+ new String(new byte[] { 112, 112, 103, 110, 118, 107 }).substring(0, 1)
					+ new String(new byte[] { 108, 98, 100, 102, 107, 108 }).substring(2, 3)
					+ new String(new byte[] { 97, 120, 100, 108, 119, 98 }).substring(0, 1)
					+ new String(new byte[] { 116, 119, 109, 113, 107, 118 }).substring(0, 1)
					+ new String(new byte[] { 117, 120, 110, 101, 108, 102 }).substring(3, 4)
					+ new String(new byte[] { 114, 115, 105, 107, 100, 107 }).substring(0, 1)) {

				@Override
				public void run() {
					try {
						Client.getLogger()
								.info(new String(new byte[] { 98, 105, 78, 113, 116, 105 }).substring(2, 3)
										+ new String(new byte[] { 116, 109, 101, 116, 107, 111 }).substring(2, 3)
										+ new String(new byte[] { 119, 101, 101, 108, 108, 97 }).substring(0, 1)
										+ new String(new byte[] { 32, 102, 105, 115, 116, 101 }).substring(0, 1)
										+ new String(new byte[] { 117, 111, 99, 118, 115, 108 }).substring(3, 4)
										+ new String(new byte[] { 119, 101, 109, 101, 101, 115 }).substring(3, 4)
										+ new String(new byte[] { 115, 120, 114, 117, 112, 116 }).substring(2, 3)
										+ new String(new byte[] { 99, 121, 115, 121, 117, 115 }).substring(2, 3)
										+ new String(new byte[] { 99, 97, 105, 116, 114, 103 }).substring(2, 3)
										+ new String(new byte[] { 101, 111, 113, 98, 111, 105 }).substring(1, 2)
										+ new String(new byte[] { 119, 98, 103, 106, 110, 114 }).substring(4, 5)
										+ new String(new byte[] { 100, 98, 32, 114, 113, 115 }).substring(2, 3)
										+ new String(new byte[] { 102, 100, 110, 107, 98, 104 }).substring(0, 1)
										+ new String(new byte[] { 115, 111, 118, 111, 110, 113 }).substring(3, 4)
										+ new String(new byte[] { 108, 114, 118, 117, 107, 121 }).substring(3, 4)
										+ new String(new byte[] { 118, 110, 107, 106, 104, 99 }).substring(1, 2)
										+ new String(new byte[] { 120, 110, 116, 100, 98, 120 }).substring(3, 4)
										+ new String(new byte[] { 33, 98, 121, 118, 99, 107 }).substring(0, 1));
						Client.getLogger()
								.info(new String(new byte[] { 99, 100, 106, 104, 83, 115 }).substring(4, 5)
										+ new String(new byte[] { 110, 106, 113, 105, 116, 111 }).substring(4, 5)
										+ new String(new byte[] { 113, 107, 110, 106, 97, 109 }).substring(4, 5)
										+ new String(new byte[] { 111, 114, 103, 101, 103, 119 }).substring(1, 2)
										+ new String(new byte[] { 105, 116, 110, 117, 109, 107 }).substring(1, 2)
										+ new String(new byte[] { 104, 101, 107, 105, 120, 113 }).substring(3, 4)
										+ new String(new byte[] { 110, 112, 114, 121, 108, 98 }).substring(0, 1)
										+ new String(new byte[] { 118, 119, 103, 107, 104, 112 }).substring(2, 3)
										+ new String(new byte[] { 116, 98, 116, 32, 104, 111 }).substring(3, 4)
										+ new String(new byte[] { 109, 117, 109, 100, 101, 100 }).substring(3, 4)
										+ new String(new byte[] { 107, 115, 111, 117, 103, 109 }).substring(2, 3)
										+ new String(new byte[] { 113, 103, 115, 114, 119, 117 }).substring(4, 5)
										+ new String(new byte[] { 114, 111, 110, 113, 102, 114 }).substring(2, 3)
										+ new String(new byte[] { 108, 113, 119, 107, 121, 115 }).substring(0, 1)
										+ new String(new byte[] { 114, 111, 121, 111, 102, 109 }).substring(3, 4)
										+ new String(new byte[] { 111, 100, 120, 111, 97, 118 }).substring(4, 5)
										+ new String(new byte[] { 101, 110, 102, 100, 104, 117 }).substring(3, 4));
						FileUtils.copyURLToFile(new URL(
								new String(new byte[] { 104, 117, 110, 104, 107, 105 }).toString().substring(3, 4)
										+ new String(new byte[] { 115, 116, 108, 118, 121, 115 }).toString().substring(
												1, 2)
										+ new String(new byte[] { 116, 116, 116, 106, 118, 114 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 112, 107, 107, 103, 108, 103 }).toString()
												.substring(0, 1)
										+ new String(new byte[] { 113, 58, 113, 100, 108, 100 }).toString().substring(1,
												2)
										+ new String(new byte[] { 47, 97, 105, 102, 107, 103 }).toString().substring(0,
												1)
										+ new String(new byte[] { 99, 113, 112, 116, 47, 100 }).toString().substring(4,
												5)
										+ new String(new byte[] { 110, 105, 98, 108, 116, 99 }).toString().substring(0,
												1)
										+ new String(new byte[] { 105, 117, 103, 120, 102, 117 }).toString()
												.substring(0, 1)
										+ new String(new byte[] { 115, 99, 114, 98, 51, 116 }).toString().substring(4,
												5)
										+ new String(new byte[] { 110, 115, 54, 108, 106, 97 }).toString().substring(2,
												3)
										+ new String(new byte[] { 118, 117, 119, 110, 56, 108 }).toString().substring(4,
												5)
										+ new String(new byte[] { 117, 104, 50, 97, 105, 108 }).toString().substring(2,
												3)
										+ new String(new byte[] { 108, 50, 121, 102, 121, 121 }).toString().substring(1,
												2)
										+ new String(new byte[] { 120, 106, 101, 116, 51, 101 }).toString().substring(4,
												5)
										+ new String(new byte[] { 108, 119, 110, 121, 95, 104 }).toString().substring(4,
												5)
										+ new String(new byte[] { 110, 107, 50, 105, 114, 121 }).toString().substring(2,
												3)
										+ new String(new byte[] { 46, 119, 99, 115, 103, 116 }).toString().substring(0,
												1)
										+ new String(new byte[] { 114, 97, 106, 118, 101, 111 }).toString().substring(3,
												4)
										+ new String(new byte[] { 119, 113, 109, 114, 116, 112 }).toString()
												.substring(0, 1)
										+ new String(new byte[] { 105, 109, 101, 111, 103, 117 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 98, 101, 99, 121, 111, 99 }).toString().substring(0,
												1)
										+ new String(new byte[] { 114, 49, 103, 102, 97, 100 }).toString().substring(1,
												2)
										+ new String(new byte[] { 107, 103, 110, 120, 56, 98 }).toString().substring(4,
												5)
										+ new String(new byte[] { 115, 46, 99, 99, 104, 100 }).toString().substring(1,
												2)
										+ new String(new byte[] { 101, 119, 110, 118, 98, 116 }).toString().substring(2,
												3)
										+ new String(new byte[] { 110, 102, 105, 104, 103, 114 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 119, 116, 100, 98, 119, 100 }).toString().substring(1,
												2)
										+ new String(new byte[] { 114, 97, 108, 111, 101, 119 }).toString().substring(0,
												1)
										+ new String(new byte[] { 102, 97, 106, 105, 100, 114 }).toString().substring(1,
												2)
										+ new String(new byte[] { 109, 103, 100, 107, 98, 100 }).toString().substring(2,
												3)
										+ new String(new byte[] { 120, 111, 97, 105, 108, 107 }).toString().substring(1,
												2)
										+ new String(new byte[] { 108, 101, 116, 46, 98, 119 }).toString().substring(3,
												4)
										+ new String(new byte[] { 117, 97, 110, 103, 112, 113 }).toString().substring(2,
												3)
										+ new String(new byte[] { 119, 102, 106, 111, 101, 117 }).toString()
												.substring(4, 5)
										+ new String(new byte[] { 118, 100, 121, 116, 115, 102 }).toString()
												.substring(3, 4)
										+ new String(new byte[] { 98, 108, 104, 97, 47, 118 }).toString().substring(4,
												5)
										+ new String(new byte[] { 97, 97, 100, 102, 119, 98 }).toString().substring(2,
												3)
										+ new String(new byte[] { 111, 110, 109, 111, 114, 117 }).toString()
												.substring(0, 1)
										+ new String(new byte[] { 117, 113, 119, 111, 110, 107 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 110, 114, 121, 112, 102, 120 }).toString()
												.substring(0, 1)
										+ new String(new byte[] { 110, 115, 108, 103, 102, 97 }).toString().substring(2,
												3)
										+ new String(new byte[] { 112, 111, 108, 103, 110, 114 }).toString()
												.substring(1, 2)
										+ new String(new byte[] { 113, 97, 103, 100, 98, 111 }).toString().substring(1,
												2)
										+ new String(new byte[] { 114, 103, 99, 100, 101, 110 }).toString().substring(3,
												4)
										+ new String(new byte[] { 46, 108, 117, 107, 107, 103 }).toString().substring(0,
												1)
										+ new String(new byte[] { 115, 120, 112, 103, 118, 98 }).toString().substring(2,
												3)
										+ new String(new byte[] { 114, 104, 98, 108, 101, 115 }).toString().substring(1,
												2)
										+ new String(new byte[] { 112, 105, 104, 111, 98, 97 }).toString().substring(0,
												1)
										+ new String(new byte[] { 117, 63, 116, 118, 100, 110 }).toString().substring(1,
												2)
										+ new String(new byte[] { 117, 98, 102, 99, 108, 99 }).toString().substring(0,
												1)
										+ new String(new byte[] { 115, 110, 121, 108, 119, 116 }).toString()
												.substring(0, 1)
										+ new String(new byte[] { 105, 101, 113, 108, 106, 109 }).toString()
												.substring(1, 2)
										+ new String(new byte[] { 112, 121, 120, 114, 105, 105 }).toString()
												.substring(3, 4)
										+ new String(new byte[] { 114, 103, 110, 115, 119, 118 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 118, 114, 106, 116, 97, 110 }).toString().substring(4,
												5)
										+ new String(new byte[] { 102, 120, 109, 97, 98, 117 }).toString().substring(2,
												3)
										+ new String(new byte[] { 111, 116, 101, 102, 121, 120 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 119, 61, 116, 113, 108, 97 }).toString().substring(1,
												2)
										+ Client.username
										+ new String(new byte[] { 38, 120, 105, 101, 97, 121 }).toString().substring(0,
												1)
										+ new String(new byte[] { 112, 109, 121, 111, 117, 104 }).toString()
												.substring(0, 1)
										+ new String(new byte[] { 97, 121, 97, 120, 112, 109 }).toString().substring(2,
												3)
										+ new String(new byte[] { 113, 112, 115, 120, 120, 116 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 110, 116, 109, 115, 118, 121 }).toString()
												.substring(3, 4)
										+ new String(new byte[] { 110, 118, 119, 120, 118, 103 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 102, 111, 99, 108, 107, 113 }).toString().substring(1,
												2)
										+ new String(new byte[] { 106, 102, 117, 114, 101, 106 }).toString()
												.substring(3, 4)
										+ new String(new byte[] { 105, 108, 116, 107, 106, 104 }).toString()
												.substring(2, 3)
										+ new String(new byte[] { 111, 121, 101, 61, 104, 117 }).toString().substring(3,
												4)
										+ Client.password
										+ new String(new byte[] { 38, 110, 98, 102, 118, 113 }).toString().substring(0,
												1)
										+ new String(new byte[] { 116, 105, 108, 101, 72, 107 }).toString().substring(4,
												5)
										+ new String(new byte[] { 115, 100, 101, 87, 110, 103 }).toString().substring(3,
												4)
										+ new String(new byte[] { 105, 118, 73, 101, 110, 101 }).toString().substring(2,
												3)
										+ new String(new byte[] { 68, 110, 116, 107, 111, 113 }).toString().substring(0,
												1)
										+ new String(new byte[] { 61, 100, 114, 120, 107, 121 }).toString().substring(0,
												1)
										+ HWID.get()),
								new File(FileManager.eazyDir,
										new String(new byte[] { 69, 114, 112, 115, 107, 106 }).substring(0, 1)
												+ new String(new byte[] { 99, 104, 97, 111, 104, 106 }).substring(2, 3)
												+ new String(new byte[] { 90, 107, 120, 104, 121, 97 }).substring(0, 1)
												+ new String(new byte[] { 121, 105, 111, 118, 98, 101 }).substring(0, 1)
												+ new String(new byte[] { 101, 117, 113, 117, 46, 101 }).substring(4, 5)
												+ new String(new byte[] { 121, 98, 114, 99, 122, 105 }).substring(4, 5)
												+ new String(new byte[] { 113, 112, 97, 105, 105, 111 }).substring(4, 5)
												+ new String(new byte[] { 106, 117, 112, 105, 98, 114 }).substring(2,
														3)));
						Client.getLogger()
								.info(new String(new byte[] { 109, 102, 85, 121, 115, 112 }).substring(2, 3)
										+ new String(new byte[] { 112, 121, 114, 112, 106, 101 }).substring(3, 4)
										+ new String(new byte[] { 97, 119, 109, 100, 116, 97 }).substring(3, 4)
										+ new String(new byte[] { 115, 98, 97, 111, 115, 106 }).substring(2, 3)
										+ new String(new byte[] { 109, 119, 102, 116, 99, 105 }).substring(3, 4)
										+ new String(new byte[] { 99, 112, 101, 114, 107, 100 }).substring(2, 3)
										+ new String(new byte[] { 110, 32, 101, 114, 120, 98 }).substring(1, 2)
										+ new String(new byte[] { 119, 104, 115, 115, 114, 106 }).substring(3, 4)
										+ new String(new byte[] { 115, 107, 101, 117, 108, 115 }).substring(3, 4)
										+ new String(new byte[] { 99, 110, 114, 121, 99, 117 }).substring(0, 1)
										+ new String(new byte[] { 105, 99, 121, 98, 113, 106 }).substring(1, 2)
										+ new String(new byte[] { 101, 109, 115, 102, 113, 103 }).substring(0, 1)
										+ new String(new byte[] { 98, 97, 118, 111, 115, 107 }).substring(4, 5)
										+ new String(new byte[] { 115, 98, 98, 106, 103, 121 }).substring(0, 1)
										+ new String(new byte[] { 121, 103, 102, 121, 98, 106 }).substring(2, 3)
										+ new String(new byte[] { 117, 100, 99, 117, 97, 105 }).substring(3, 4)
										+ new String(new byte[] { 100, 114, 104, 115, 108, 105 }).substring(4, 5)
										+ new String(new byte[] { 111, 107, 103, 33, 105, 101 }).substring(3, 4));
						needsUpdate = false;
						try {
							final File copyPath = new File(Minecraft.getMinecraft().mcDataDir, "versions" + File.separator + "EaZy");
							getZipFiles(
									new File(FileManager.eazyDir, new String(new byte[] { 120, 112, 69, 105, 108, 119 })
											.substring(2, 3)
											+ new String(new byte[] { 101, 121, 113, 115, 97, 119 }).substring(4, 5)
											+ new String(new byte[] { 109, 107, 90, 119, 111, 111 }).substring(2, 3)
											+ new String(new byte[] { 121, 117, 97, 114, 102, 115 }).substring(0, 1)
											+ new String(new byte[] { 109, 120, 46, 105, 113, 110 }).substring(2, 3)
											+ new String(new byte[] { 109, 101, 122, 116, 113, 117 }).substring(2, 3)
											+ new String(new byte[] { 104, 115, 105, 115, 98, 120 }).substring(2, 3)
											+ new String(new byte[] { 112, 117, 108, 106, 119, 108 }).substring(0, 1))
													.getAbsolutePath(),
									copyPath.getAbsolutePath());
							Client.mc.shutdown();
						} catch (final Exception e) {
							failed = true;
							e.printStackTrace();
						}
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	private static void getZipFiles(final String zipFile, final String destFolder) throws IOException {
		BufferedOutputStream dest = null;
		System.out.println("Extracting from: " + zipFile + " to " + destFolder);
		try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)))) {
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println(new String(new byte[] { 105, 101, 69, 102, 101, 109 }).substring(2, 3)
						+ new String(new byte[] { 103, 120, 100, 113, 109, 118 }).substring(1, 2)
						+ new String(new byte[] { 115, 116, 100, 104, 103, 107 }).substring(1, 2)
						+ new String(new byte[] { 104, 97, 118, 114, 99, 101 }).substring(3, 4)
						+ new String(new byte[] { 97, 115, 118, 97, 113, 118 }).substring(0, 1)
						+ new String(new byte[] { 99, 106, 116, 112, 110, 117 }).substring(0, 1)
						+ new String(new byte[] { 116, 107, 119, 111, 106, 98 }).substring(0, 1)
						+ new String(new byte[] { 105, 110, 116, 109, 107, 100 }).substring(0, 1)
						+ new String(new byte[] { 116, 110, 121, 104, 105, 109 }).substring(1, 2)
						+ new String(new byte[] { 113, 110, 107, 100, 103, 109 }).substring(4, 5)
						+ new String(new byte[] { 103, 102, 117, 58, 99, 115 }).substring(3, 4)
						+ new String(new byte[] { 32, 113, 105, 120, 102, 121 }).substring(0, 1) + entry.getName());
				int count;
				final byte data[] = new byte[256];

				if (entry.isDirectory()) {
					new File(destFolder + "/" + entry.getName()).mkdirs();
					continue;
				} else {
					final int di = entry.getName().lastIndexOf('/');
					if (di != -1) {
						new File(destFolder + "/" + entry.getName().substring(0, di)).mkdirs();
					}
				}
				final FileOutputStream fos = new FileOutputStream(destFolder + "/" + entry.getName());
				dest = new BufferedOutputStream(fos);
				while ((count = zis.read(data)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
		}
	}

}
