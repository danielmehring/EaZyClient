import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import net.minecraft.client.main.Main;
import net.minecraftforge.client.model.pipeline.Sfgbsaifgboi;

public class Start {
	public static void main(final String[] args) throws Exception {
		Main.start(concat(new String[] { "--version", "mcp", "--accessToken", "0", "--assetsDir", "assets",
				"--assetIndex", "1.8", "--userProperties", "{}" }, args));
	}

	public static <T> T[] concat(final T[] first, final T[] second) {
		final T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
