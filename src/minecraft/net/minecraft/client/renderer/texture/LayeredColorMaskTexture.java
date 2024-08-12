package net.minecraft.client.renderer.texture;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredColorMaskTexture extends AbstractTexture {

public static final int EaZy = 818;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_174947_f = LogManager.getLogger();
	private final ResourceLocation field_174948_g;
	private final List field_174949_h;
	private final List field_174950_i;
	// private static final String __OBFID = "http://https://fuckuskid00002404";

	public LayeredColorMaskTexture(final ResourceLocation p_i46101_1_, final List p_i46101_2_, final List p_i46101_3_) {
		field_174948_g = p_i46101_1_;
		field_174949_h = p_i46101_2_;
		field_174950_i = p_i46101_3_;
	}

	@Override
	public void loadTexture(final IResourceManager p_110551_1_) throws IOException {
		deleteGlTexture();
		BufferedImage var2;

		try {
			final BufferedImage var3 = TextureUtil
					.func_177053_a(p_110551_1_.getResource(field_174948_g).getInputStream());
			int var4 = var3.getType();

			if (var4 == 0) {
				var4 = 6;
			}

			var2 = new BufferedImage(var3.getWidth(), var3.getHeight(), var4);
			final Graphics var5 = var2.getGraphics();
			var5.drawImage(var3, 0, 0, (ImageObserver) null);

			for (int var6 = 0; var6 < field_174949_h.size() && var6 < field_174950_i.size(); ++var6) {
				final String var7 = (String) field_174949_h.get(var6);
				final MapColor var8 = ((EnumDyeColor) field_174950_i.get(var6)).func_176768_e();

				if (var7 != null) {
					final InputStream var9 = p_110551_1_.getResource(new ResourceLocation(var7)).getInputStream();
					final BufferedImage var10 = TextureUtil.func_177053_a(var9);

					if (var10.getWidth() == var2.getWidth() && var10.getHeight() == var2.getHeight()
							&& var10.getType() == 6) {
						for (int var11 = 0; var11 < var10.getHeight(); ++var11) {
							for (int var12 = 0; var12 < var10.getWidth(); ++var12) {
								final int var13 = var10.getRGB(var12, var11);

								if ((var13 & -16777216) != 0) {
									final int var14 = (var13 & 16711680) << 8 & -16777216;
									final int var15 = var3.getRGB(var12, var11);
									final int var16 = MathHelper.func_180188_d(var15, var8.colorValue) & 16777215;
									var10.setRGB(var12, var11, var14 | var16);
								}
							}
						}

						var2.getGraphics().drawImage(var10, 0, 0, (ImageObserver) null);
					}
				}
			}
		} catch (final IOException var17) {
			field_174947_f.error("Couldn\'t load layered image", var17);
			return;
		}

		TextureUtil.uploadTextureImage(getGlTextureId(), var2);
	}
}
