package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import optifine.Config;
import shadersmod.client.ShadersTex;

public class TextureCompass extends TextureAtlasSprite {

public static final int EaZy = 824;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Current compass heading in radians */
	public double currentAngle;

	/** Speed and direction of compass rotation */
	public double angleDelta;
	public static String field_176608_l;
	// private static final String __OBFID = "http://https://fuckuskid00001071";

	public TextureCompass(final String p_i1286_1_) {
		super(p_i1286_1_);
		field_176608_l = p_i1286_1_;
	}

	@Override
	public void updateAnimation() {

		if (Minecraft.theWorld != null && Minecraft.thePlayer != null) {
			updateCompass(Minecraft.theWorld, Minecraft.thePlayer.posX, Minecraft.thePlayer.posZ,
					Minecraft.thePlayer.rotationYaw, false, false);
		} else {
			updateCompass((World) null, 0.0D, 0.0D, 0.0D, true, false);
		}
	}

	/**
	 * Updates the compass based on the given x,z coords and camera direction
	 */
	public void updateCompass(final World worldIn, final double p_94241_2_, final double p_94241_4_, double p_94241_6_,
			final boolean p_94241_8_, final boolean p_94241_9_) {
		if (!framesTextureData.isEmpty()) {
			double var10 = 0.0D;

			if (worldIn != null && !p_94241_8_) {
				final BlockPos var18 = worldIn.getSpawnPoint();
				final double var13 = var18.getX() - p_94241_2_;
				final double var15 = var18.getZ() - p_94241_4_;
				p_94241_6_ %= 360.0D;
				var10 = -((p_94241_6_ - 90.0D) * Math.PI / 180.0D - Math.atan2(var15, var13));

				if (!worldIn.provider.isSurfaceWorld()) {
					var10 = Math.random() * Math.PI * 2.0D;
				}
			}

			if (p_94241_9_) {
				currentAngle = var10;
			} else {
				double var181;

				for (var181 = var10 - currentAngle; var181 < -Math.PI; var181 += Math.PI * 2D) {
				}

				while (var181 >= Math.PI) {
					var181 -= Math.PI * 2D;
				}

				var181 = MathHelper.clamp_double(var181, -1.0D, 1.0D);
				angleDelta += var181 * 0.1D;
				angleDelta *= 0.8D;
				currentAngle += angleDelta;
			}

			int var182;

			for (var182 = (int) ((currentAngle / (Math.PI * 2D) + 1.0D) * framesTextureData.size()) % framesTextureData
					.size(); var182 < 0; var182 = (var182 + framesTextureData.size()) % framesTextureData.size()) {
			}

			if (var182 != frameCounter) {
				frameCounter = var182;

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
