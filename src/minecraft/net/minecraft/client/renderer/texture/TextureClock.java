package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import optifine.Config;
import shadersmod.client.ShadersTex;

public class TextureClock extends TextureAtlasSprite {

public static final int EaZy = 823;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private double field_94239_h;
	private double field_94240_i;
	// private static final String __OBFID = "http://https://fuckuskid00001070";

	public TextureClock(final String p_i1285_1_) {
		super(p_i1285_1_);
	}

	@Override
	public void updateAnimation() {
		if (!framesTextureData.isEmpty()) {

			double var2 = 0.0D;

			if (Minecraft.theWorld != null && Minecraft.thePlayer != null) {
				final float var7 = Minecraft.theWorld.getCelestialAngle(1.0F);
				var2 = var7;

				if (!Minecraft.theWorld.provider.isSurfaceWorld()) {
					var2 = Math.random();
				}
			}

			double var71;

			for (var71 = var2 - field_94239_h; var71 < -0.5D; ++var71) {
			}

			while (var71 >= 0.5D) {
				--var71;
			}

			var71 = MathHelper.clamp_double(var71, -1.0D, 1.0D);
			field_94240_i += var71 * 0.1D;
			field_94240_i *= 0.8D;
			field_94239_h += field_94240_i;
			int var6;

			for (var6 = (int) ((field_94239_h + 1.0D) * framesTextureData.size()) % framesTextureData
					.size(); var6 < 0; var6 = (var6 + framesTextureData.size()) % framesTextureData.size()) {
			}

			if (var6 != frameCounter) {
				frameCounter = var6;

				if (Config.isShaders()) {
					ShadersTex.uploadTexSub((int[][]) framesTextureData.get(frameCounter), width, height, originX,
							originY, false, false);
				} else {
					TextureUtil.uploadTextureMipmap((int[][]) framesTextureData.get(frameCounter), width, height,
							originX, originY, false, false);
				}
			}
		}
	}
}
