package me.EaZy.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DecoderDingsDa {

	public static final int EaZy = 77;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	public static String get(final String s, String key) {
		try {
			return rot13(new String(Base64.decodeBase64(rot13(decrypt(pack(key).getBytes(), s)))));
		} catch (GeneralSecurityException e) {
			return null;
		}
	}

	public static String pack(String hex) {
		String input = hex.length() % 2 == 0 ? hex : hex + "0";
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < input.length(); i += 2) {
			String str = input.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	public static String decrypt(byte[] key, String encrypted) throws GeneralSecurityException {
		if (key.length != 16) {
			throw new IllegalArgumentException("Invalid key size.");
		}

		byte[] ciphertextBytes = Base64.decodeBase64(encrypted.getBytes());
		IvParameterSpec iv = new IvParameterSpec(ciphertextBytes, 0, 16);
		ciphertextBytes = Arrays.copyOfRange(ciphertextBytes, 16, ciphertextBytes.length);

		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] original = cipher.doFinal(ciphertextBytes);

		// remove zero bytes at the end
		int lastLength = original.length;
		for (int i = original.length - 1; i > original.length - 16; i--) {
			if (original[i] == (byte) 0) {
				lastLength--;
			} else {
				break;
			}
		}

		return new String(original, 0, lastLength);
	}

	public static String rot13(final String value) {

		final char[] values = value.toCharArray();
		for (int i = 0; i < values.length; i++) {
			char letter = values[i];

			if (letter >= 'a' && letter <= 'z') {
				// Rotate lowercase letters.

				if (letter > 'm') {
					letter -= 13;
				} else {
					letter += 13;
				}
			} else if (letter >= 'A' && letter <= 'Z') {
				// Rotate uppercase letters.

				if (letter > 'M') {
					letter -= 13;
				} else {
					letter += 13;
				}
			}
			values[i] = letter;
		}
		// Convert array to a new String.
		return new String(values);
	}
}
