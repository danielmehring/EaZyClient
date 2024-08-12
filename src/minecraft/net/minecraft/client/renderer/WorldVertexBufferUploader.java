package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import optifine.Config;
import optifine.Reflector;
import shadersmod.client.SVertexBuilder;

public class WorldVertexBufferUploader {

public static final int EaZy = 853;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }


	public int draw(final WorldRenderer p_178177_1_, final int p_178177_2_) {
		if (p_178177_2_ > 0) {
			final VertexFormat var3 = p_178177_1_.func_178973_g();
			final int var4 = var3.func_177338_f();
			final ByteBuffer var5 = p_178177_1_.func_178966_f();
			final List var6 = var3.func_177343_g();
			Iterator var7 = var6.iterator();
			final boolean forgePreDrawExists = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
			final boolean forgePostDrawExists = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();
			VertexFormatElement var8;
			VertexFormatElement.EnumUseage var9;
			int var10;

			while (var7.hasNext()) {
				var8 = (VertexFormatElement) var7.next();
				var9 = var8.func_177375_c();

				if (forgePreDrawExists) {
					Reflector.callVoid(var9, Reflector.ForgeVertexFormatElementEnumUseage_preDraw,
							new Object[] { var8, var4, var5 });
				} else {
					var10 = var8.func_177367_b().func_177397_c();
					final int wr = var8.func_177369_e();

					switch (WorldVertexBufferUploader.SwitchEnumUseage.field_178958_a[var9.ordinal()]) {
						case 1:
							var5.position(var8.func_177373_a());
							GL11.glVertexPointer(var8.func_177370_d(), var10, var4, var5);
							GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
							break;

						case 2:
							var5.position(var8.func_177373_a());
							OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + wr);
							GL11.glTexCoordPointer(var8.func_177370_d(), var10, var4, var5);
							GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
							OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
							break;

						case 3:
							var5.position(var8.func_177373_a());
							GL11.glColorPointer(var8.func_177370_d(), var10, var4, var5);
							GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
							break;

						case 4:
							var5.position(var8.func_177373_a());
							GL11.glNormalPointer(var10, var4, var5);
							GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
					}
				}
			}

			if (p_178177_1_.isMultiTexture()) {
				p_178177_1_.drawMultiTexture();
			} else if (Config.isShaders()) {
				SVertexBuilder.drawArrays(p_178177_1_.getDrawMode(), 0, p_178177_1_.func_178989_h(), p_178177_1_);
			} else {
				GL11.glDrawArrays(p_178177_1_.getDrawMode(), 0, p_178177_1_.func_178989_h());
			}

			var7 = var6.iterator();

			while (var7.hasNext()) {
				var8 = (VertexFormatElement) var7.next();
				var9 = var8.func_177375_c();

				if (forgePostDrawExists) {
					Reflector.callVoid(var9, Reflector.ForgeVertexFormatElementEnumUseage_postDraw,
							new Object[] { var8, var4, var5 });
				} else {
					var10 = var8.func_177369_e();

					switch (WorldVertexBufferUploader.SwitchEnumUseage.field_178958_a[var9.ordinal()]) {
						case 1:
							GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
							break;

						case 2:
							OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var10);
							GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
							OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
							break;

						case 3:
							GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
							GlStateManager.func_179117_G();
							break;

						case 4:
							GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
					}
				}
			}
		}

		p_178177_1_.reset();
		return p_178177_2_;
	}

	static final class SwitchEnumUseage {
		static final int[] field_178958_a = new int[VertexFormatElement.EnumUseage.values().length];

		static {
			try {
				field_178958_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_178958_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_178958_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_178958_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
