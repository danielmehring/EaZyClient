package net.minecraft.client.model;

import net.minecraft.client.renderer.WorldRenderer;

public class ModelBox {

public static final int EaZy = 576;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * The (x,y,z) vertex positions and (u,v) texture coordinates for each of
	 * the 8 points on a cube
	 */
	private final PositionTextureVertex[] vertexPositions;

	/** An array of 6 TexturedQuads, one for each face of a cube */
	private final TexturedQuad[] quadList;

	/** X vertex coordinate of lower box corner */
	public final float posX1;

	/** Y vertex coordinate of lower box corner */
	public final float posY1;

	/** Z vertex coordinate of lower box corner */
	public final float posZ1;

	/** X vertex coordinate of upper box corner */
	public final float posX2;

	/** Y vertex coordinate of upper box corner */
	public final float posY2;

	/** Z vertex coordinate of upper box corner */
	public final float posZ2;
	public String field_78247_g;
	// private static final String __OBFID = "http://https://fuckuskid00000872";

	public ModelBox(final ModelRenderer p_i46359_1_, final int p_i46359_2_, final int p_i46359_3_,
			final float p_i46359_4_, final float p_i46359_5_, final float p_i46359_6_, final int p_i46359_7_,
			final int p_i46359_8_, final int p_i46359_9_, final float p_i46359_10_) {
		this(p_i46359_1_, p_i46359_2_, p_i46359_3_, p_i46359_4_, p_i46359_5_, p_i46359_6_, p_i46359_7_, p_i46359_8_,
				p_i46359_9_, p_i46359_10_, p_i46359_1_.mirror);
	}

	public ModelBox(final ModelRenderer p_i46301_1_, final int p_i46301_2_, final int p_i46301_3_, float p_i46301_4_,
			float p_i46301_5_, float p_i46301_6_, final int p_i46301_7_, final int p_i46301_8_, final int p_i46301_9_,
			final float p_i46301_10_, final boolean p_i46301_11_) {
		posX1 = p_i46301_4_;
		posY1 = p_i46301_5_;
		posZ1 = p_i46301_6_;
		posX2 = p_i46301_4_ + p_i46301_7_;
		posY2 = p_i46301_5_ + p_i46301_8_;
		posZ2 = p_i46301_6_ + p_i46301_9_;
		vertexPositions = new PositionTextureVertex[8];
		quadList = new TexturedQuad[6];
		float var12 = p_i46301_4_ + p_i46301_7_;
		float var13 = p_i46301_5_ + p_i46301_8_;
		float var14 = p_i46301_6_ + p_i46301_9_;
		p_i46301_4_ -= p_i46301_10_;
		p_i46301_5_ -= p_i46301_10_;
		p_i46301_6_ -= p_i46301_10_;
		var12 += p_i46301_10_;
		var13 += p_i46301_10_;
		var14 += p_i46301_10_;

		if (p_i46301_11_) {
			final float var15 = var12;
			var12 = p_i46301_4_;
			p_i46301_4_ = var15;
		}

		final PositionTextureVertex var24 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, p_i46301_6_, 0.0F,
				0.0F);
		final PositionTextureVertex var16 = new PositionTextureVertex(var12, p_i46301_5_, p_i46301_6_, 0.0F, 8.0F);
		final PositionTextureVertex var17 = new PositionTextureVertex(var12, var13, p_i46301_6_, 8.0F, 8.0F);
		final PositionTextureVertex var18 = new PositionTextureVertex(p_i46301_4_, var13, p_i46301_6_, 8.0F, 0.0F);
		final PositionTextureVertex var19 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, var14, 0.0F, 0.0F);
		final PositionTextureVertex var20 = new PositionTextureVertex(var12, p_i46301_5_, var14, 0.0F, 8.0F);
		final PositionTextureVertex var21 = new PositionTextureVertex(var12, var13, var14, 8.0F, 8.0F);
		final PositionTextureVertex var22 = new PositionTextureVertex(p_i46301_4_, var13, var14, 8.0F, 0.0F);
		vertexPositions[0] = var24;
		vertexPositions[1] = var16;
		vertexPositions[2] = var17;
		vertexPositions[3] = var18;
		vertexPositions[4] = var19;
		vertexPositions[5] = var20;
		vertexPositions[6] = var21;
		vertexPositions[7] = var22;
		quadList[0] = new TexturedQuad(new PositionTextureVertex[] { var20, var16, var17, var21 },
				p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_,
				p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_,
				p_i46301_1_.textureWidth, p_i46301_1_.textureHeight);
		quadList[1] = new TexturedQuad(new PositionTextureVertex[] { var24, var19, var22, var18 }, p_i46301_2_,
				p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_,
				p_i46301_1_.textureWidth, p_i46301_1_.textureHeight);
		quadList[2] = new TexturedQuad(new PositionTextureVertex[] { var20, var19, var24, var16 },
				p_i46301_2_ + p_i46301_9_, p_i46301_3_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_,
				p_i46301_3_ + p_i46301_9_, p_i46301_1_.textureWidth, p_i46301_1_.textureHeight);
		quadList[3] = new TexturedQuad(new PositionTextureVertex[] { var17, var18, var22, var21 },
				p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_,
				p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_7_, p_i46301_3_, p_i46301_1_.textureWidth,
				p_i46301_1_.textureHeight);
		quadList[4] = new TexturedQuad(new PositionTextureVertex[] { var16, var24, var18, var17 },
				p_i46301_2_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_,
				p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.textureWidth, p_i46301_1_.textureHeight);
		quadList[5] = new TexturedQuad(new PositionTextureVertex[] { var19, var20, var21, var22 },
				p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_,
				p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_ + p_i46301_7_,
				p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.textureWidth, p_i46301_1_.textureHeight);

		if (p_i46301_11_) {
			for (final TexturedQuad element : quadList) {
				element.flipFace();
			}
		}
	}

	public void render(final WorldRenderer p_178780_1_, final float p_178780_2_) {
		for (final TexturedQuad element : quadList) {
			element.func_178765_a(p_178780_1_, p_178780_2_);
		}
	}

	public ModelBox func_78244_a(final String p_78244_1_) {
		field_78247_g = p_78244_1_;
		return this;
	}
}
