package net.minecraft.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptManager {

public static final int EaZy = 1605;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_180198_a = LogManager.getLogger();
	// private static final String __OBFID = "http://https://fuckuskid00001483";

	/**
	 * Generate a new shared secret AES key from a secure random source
	 */
	public static SecretKey createNewSharedKey() {
		try {
			final KeyGenerator var0 = KeyGenerator.getInstance("AES");
			var0.init(128);
			return var0.generateKey();
		} catch (final NoSuchAlgorithmException var1) {
			throw new Error(var1);
		}
	}

	/**
	 * Generates RSA KeyPair
	 */
	public static KeyPair generateKeyPair() {
		try {
			final KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
			var0.initialize(1024);
			return var0.generateKeyPair();
		} catch (final NoSuchAlgorithmException var1) {
			var1.printStackTrace();
			field_180198_a.error("Key pair generation failed!");
			return null;
		}
	}

	/**
	 * Compute a serverId hash for use by sendSessionRequest()
	 */
	public static byte[] getServerIdHash(final String p_75895_0_, final PublicKey p_75895_1_,
			final SecretKey p_75895_2_) {
		try {
			return digestOperation("SHA-1", new byte[][] { p_75895_0_.getBytes("ISO_8859_1"), p_75895_2_.getEncoded(),
					p_75895_1_.getEncoded() });
		} catch (final UnsupportedEncodingException var4) {
			var4.printStackTrace();
			return null;
		}
	}

	/**
	 * Compute a message digest on arbitrary byte[] data
	 */
	private static byte[] digestOperation(final String p_75893_0_, final byte[]... p_75893_1_) {
		try {
			final MessageDigest var2 = MessageDigest.getInstance(p_75893_0_);
			final byte[][] var3 = p_75893_1_;
			final int var4 = p_75893_1_.length;

			for (int var5 = 0; var5 < var4; ++var5) {
				final byte[] var6 = var3[var5];
				var2.update(var6);
			}

			return var2.digest();
		} catch (final NoSuchAlgorithmException var7) {
			var7.printStackTrace();
			return null;
		}
	}

	/**
	 * Create a new PublicKey from encoded X.509 data
	 */
	public static PublicKey decodePublicKey(final byte[] p_75896_0_) {
		try {
			final X509EncodedKeySpec var1 = new X509EncodedKeySpec(p_75896_0_);
			final KeyFactory var2 = KeyFactory.getInstance("RSA");
			return var2.generatePublic(var1);
		} catch (final NoSuchAlgorithmException var3) {
		} catch (final InvalidKeySpecException var4) {
		}

		field_180198_a.error("Public key reconstitute failed!");
		return null;
	}

	/**
	 * Decrypt shared secret AES key using RSA private key
	 */
	public static SecretKey decryptSharedKey(final PrivateKey p_75887_0_, final byte[] p_75887_1_) {
		return new SecretKeySpec(decryptData(p_75887_0_, p_75887_1_), "AES");
	}

	/**
	 * Encrypt byte[] data with RSA public key
	 */
	public static byte[] encryptData(final Key p_75894_0_, final byte[] p_75894_1_) {
		return cipherOperation(1, p_75894_0_, p_75894_1_);
	}

	/**
	 * Decrypt byte[] data with RSA private key
	 */
	public static byte[] decryptData(final Key p_75889_0_, final byte[] p_75889_1_) {
		return cipherOperation(2, p_75889_0_, p_75889_1_);
	}

	/**
	 * Encrypt or decrypt byte[] data using the specified key
	 */
	private static byte[] cipherOperation(final int p_75885_0_, final Key p_75885_1_, final byte[] p_75885_2_) {
		try {
			return createTheCipherInstance(p_75885_0_, p_75885_1_.getAlgorithm(), p_75885_1_).doFinal(p_75885_2_);
		} catch (final IllegalBlockSizeException var4) {
			var4.printStackTrace();
		} catch (final BadPaddingException var5) {
			var5.printStackTrace();
		}

		field_180198_a.error("Cipher data failed!");
		return null;
	}

	/**
	 * Creates the Cipher Instance.
	 */
	private static Cipher createTheCipherInstance(final int p_75886_0_, final String p_75886_1_, final Key p_75886_2_) {
		try {
			final Cipher var3 = Cipher.getInstance(p_75886_1_);
			var3.init(p_75886_0_, p_75886_2_);
			return var3;
		} catch (final InvalidKeyException var4) {
			var4.printStackTrace();
		} catch (final NoSuchAlgorithmException var5) {
			var5.printStackTrace();
		} catch (final NoSuchPaddingException var6) {
			var6.printStackTrace();
		}

		field_180198_a.error("Cipher creation failed!");
		return null;
	}

	public static Cipher func_151229_a(final int p_151229_0_, final Key p_151229_1_) {
		try {
			final Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
			var2.init(p_151229_0_, p_151229_1_, new IvParameterSpec(p_151229_1_.getEncoded()));
			return var2;
		} catch (final GeneralSecurityException var3) {
			throw new RuntimeException(var3);
		}
	}
}
