package net.minecraft.client.resources.data;

import java.util.Collections;
import java.util.List;

public class TextureMetadataSection implements IMetadataSection {

public static final int EaZy = 868;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final boolean textureBlur;
	private final boolean textureClamp;
	private final List listMipmaps;
	// private static final String __OBFID = "http://https://fuckuskid00001114";

	public TextureMetadataSection(final boolean p_i45102_1_, final boolean p_i45102_2_, final List p_i45102_3_) {
		textureBlur = p_i45102_1_;
		textureClamp = p_i45102_2_;
		listMipmaps = p_i45102_3_;
	}

	public boolean getTextureBlur() {
		return textureBlur;
	}

	public boolean getTextureClamp() {
		return textureClamp;
	}

	public List getListMipmaps() {
		return Collections.unmodifiableList(listMipmaps);
	}
}
