package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import optifine.ReflectorForge;

public class DefaultResourcePack implements IResourcePack {

public static final int EaZy = 871;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final Set defaultResourceDomains = ImmutableSet.of("minecraft", "realms");
	private final Map field_152781_b;
	// private static final String __OBFID = "http://https://fuckuskid00001073";

	public DefaultResourcePack(final Map p_i46346_1_) {
		field_152781_b = p_i46346_1_;
	}

	@Override
	public InputStream getInputStream(final ResourceLocation p_110590_1_) throws IOException {
		final InputStream var2 = getResourceStream(p_110590_1_);

		if (var2 != null) {
			return var2;
		} else {
			final InputStream var3 = func_152780_c(p_110590_1_);

			if (var3 != null) {
				return var3;
			} else {
				throw new FileNotFoundException(p_110590_1_.getResourcePath());
			}
		}
	}

	public InputStream func_152780_c(final ResourceLocation p_152780_1_) throws IOException {
		final File var2 = (File) field_152781_b.get(p_152780_1_.toString());
		return var2 != null && var2.isFile() ? new FileInputStream(var2) : null;
	}

	private InputStream getResourceStream(final ResourceLocation p_110605_1_) {
		final String path = "/assets/" + p_110605_1_.getResourceDomain() + "/" + p_110605_1_.getResourcePath();
		final InputStream is = ReflectorForge.getOptiFineResourceStream(path);
		return is != null ? is
				: DefaultResourcePack.class.getResourceAsStream(
						"/assets/" + p_110605_1_.getResourceDomain() + "/" + p_110605_1_.getResourcePath());
	}

	@Override
	public boolean resourceExists(final ResourceLocation p_110589_1_) {
		return getResourceStream(p_110589_1_) != null || field_152781_b.containsKey(p_110589_1_.toString());
	}

	@Override
	public Set getResourceDomains() {
		return defaultResourceDomains;
	}

	@Override
	public IMetadataSection getPackMetadata(final IMetadataSerializer p_135058_1_, final String p_135058_2_)
			throws IOException {
		try {
			final FileInputStream var5 = new FileInputStream((File) field_152781_b.get("pack.mcmeta"));
			return AbstractResourcePack.readMetadata(p_135058_1_, var5, p_135058_2_);
		} catch (final RuntimeException var4) {
			return null;
		} catch (final FileNotFoundException var51) {
			return null;
		}
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		return TextureUtil.func_177053_a(DefaultResourcePack.class
				.getResourceAsStream("/" + new ResourceLocation("pack.png").getResourcePath()));
	}

	@Override
	public String getPackName() {
		return "Default";
	}
}
