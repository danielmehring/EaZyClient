package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public abstract class AbstractResourcePack implements IResourcePack {

public static final int EaZy = 854;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger resourceLog = LogManager.getLogger();
	public final File resourcePackFile;
	// private static final String __OBFID = "http://https://fuckuskid00001072";

	public AbstractResourcePack(final File p_i1287_1_) {
		resourcePackFile = p_i1287_1_;
	}

	private static String locationToName(final ResourceLocation p_110592_0_) {
		return String.format("%s/%s/%s",
				new Object[] { "assets", p_110592_0_.getResourceDomain(), p_110592_0_.getResourcePath() });
	}

	protected static String getRelativeName(final File p_110595_0_, final File p_110595_1_) {
		return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
	}

	@Override
	public InputStream getInputStream(final ResourceLocation p_110590_1_) throws IOException {
		return getInputStreamByName(locationToName(p_110590_1_));
	}

	@Override
	public boolean resourceExists(final ResourceLocation p_110589_1_) {
		return hasResourceName(locationToName(p_110589_1_));
	}

	protected abstract InputStream getInputStreamByName(String var1) throws IOException;

	protected abstract boolean hasResourceName(String var1);

	protected void logNameNotLowercase(final String p_110594_1_) {
		resourceLog.warn("ResourcePack: ignored non-lowercase namespace: {} in {}",
				new Object[] { p_110594_1_, resourcePackFile });
	}

	@Override
	public IMetadataSection getPackMetadata(final IMetadataSerializer p_135058_1_, final String p_135058_2_)
			throws IOException {
		return readMetadata(p_135058_1_, getInputStreamByName("pack.mcmeta"), p_135058_2_);
	}

	static IMetadataSection readMetadata(final IMetadataSerializer p_110596_0_, final InputStream p_110596_1_,
			final String p_110596_2_) {
		JsonObject var3 = null;
		BufferedReader var4 = null;

		try {
			var4 = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
			var3 = new JsonParser().parse(var4).getAsJsonObject();
		} catch (final RuntimeException var9) {
			throw new JsonParseException(var9);
		}
		finally {
			IOUtils.closeQuietly(var4);
		}

		return p_110596_0_.parseMetadataSection(p_110596_2_, var3);
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		return TextureUtil.func_177053_a(getInputStreamByName("pack.png"));
	}

	@Override
	public String getPackName() {
		return resourcePackFile.getName();
	}
}
