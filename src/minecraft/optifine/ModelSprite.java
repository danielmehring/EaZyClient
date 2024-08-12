package optifine;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;

public class ModelSprite {

public static final int EaZy = 1934;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ModelRenderer modelRenderer = null;
	private float posX = 0.0F;
	private float posY = 0.0F;
	private float posZ = 0.0F;
	private int sizeX = 0;
	private int sizeY = 0;
	private int sizeZ = 0;
	private float minU = 0.0F;
	private float minV = 0.0F;
	private float maxU = 0.0F;
	private float maxV = 0.0F;

	public ModelSprite(final ModelRenderer modelRenderer, final int textureOffsetX, final int textureOffsetY,
			final float posX, final float posY, final float posZ, final int sizeX, final int sizeY, final int sizeZ,
			final float sizeAdd) {
		this.modelRenderer = modelRenderer;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		minU = textureOffsetX / modelRenderer.textureWidth;
		minV = textureOffsetY / modelRenderer.textureHeight;
		maxU = (textureOffsetX + sizeX) / modelRenderer.textureWidth;
		maxV = (textureOffsetY + sizeY) / modelRenderer.textureHeight;
	}

	public void render(final Tessellator tessellator, final float scale) {
		GlStateManager.translate(posX * scale, posY * scale, posZ * scale);
		float rMinU = minU;
		float rMaxU = maxU;
		float rMinV = minV;
		float rMaxV = maxV;

		if (modelRenderer.mirror) {
			rMinU = maxU;
			rMaxU = minU;
		}

		if (modelRenderer.mirrorV) {
			rMinV = maxV;
			rMaxV = minV;
		}

		renderItemIn2D(tessellator, rMinU, rMinV, rMaxU, rMaxV, sizeX, sizeY, scale * sizeZ, modelRenderer.textureWidth,
				modelRenderer.textureHeight);
		GlStateManager.translate(-posX * scale, -posY * scale, -posZ * scale);
	}

	public static void renderItemIn2D(final Tessellator tess, final float minU, final float minV, final float maxU,
			final float maxV, final int sizeX, final int sizeY, float width, final float texWidth,
			final float texHeight) {
		if (width < 6.25E-4F) {
			width = 6.25E-4F;
		}

		final float dU = maxU - minU;
		final float dV = maxV - minV;
		final double dimX = MathHelper.abs(dU) * (texWidth / 16.0F);
		final double dimY = MathHelper.abs(dV) * (texHeight / 16.0F);
		final WorldRenderer tessellator = tess.getWorldRenderer();
		tessellator.startDrawingQuads();
		tessellator.func_178980_d(0.0F, 0.0F, -1.0F);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, minU, minV);
		tessellator.addVertexWithUV(dimX, 0.0D, 0.0D, maxU, minV);
		tessellator.addVertexWithUV(dimX, dimY, 0.0D, maxU, maxV);
		tessellator.addVertexWithUV(0.0D, dimY, 0.0D, minU, maxV);
		tess.draw();
		tessellator.startDrawingQuads();
		tessellator.func_178980_d(0.0F, 0.0F, 1.0F);
		tessellator.addVertexWithUV(0.0D, dimY, width, minU, maxV);
		tessellator.addVertexWithUV(dimX, dimY, width, maxU, maxV);
		tessellator.addVertexWithUV(dimX, 0.0D, width, maxU, minV);
		tessellator.addVertexWithUV(0.0D, 0.0D, width, minU, minV);
		tess.draw();
		final float var8 = 0.5F * dU / sizeX;
		final float var9 = 0.5F * dV / sizeY;
		tessellator.startDrawingQuads();
		tessellator.func_178980_d(-1.0F, 0.0F, 0.0F);
		int var10;
		float var11;
		float var12;

		for (var10 = 0; var10 < sizeX; ++var10) {
			var11 = (float) var10 / (float) sizeX;
			var12 = minU + dU * var11 + var8;
			tessellator.addVertexWithUV(var11 * dimX, 0.0D, width, var12, minV);
			tessellator.addVertexWithUV(var11 * dimX, 0.0D, 0.0D, var12, minV);
			tessellator.addVertexWithUV(var11 * dimX, dimY, 0.0D, var12, maxV);
			tessellator.addVertexWithUV(var11 * dimX, dimY, width, var12, maxV);
		}

		tess.draw();
		tessellator.startDrawingQuads();
		tessellator.func_178980_d(1.0F, 0.0F, 0.0F);
		float var13;

		for (var10 = 0; var10 < sizeX; ++var10) {
			var11 = (float) var10 / (float) sizeX;
			var12 = minU + dU * var11 + var8;
			var13 = var11 + 1.0F / sizeX;
			tessellator.addVertexWithUV(var13 * dimX, dimY, width, var12, maxV);
			tessellator.addVertexWithUV(var13 * dimX, dimY, 0.0D, var12, maxV);
			tessellator.addVertexWithUV(var13 * dimX, 0.0D, 0.0D, var12, minV);
			tessellator.addVertexWithUV(var13 * dimX, 0.0D, width, var12, minV);
		}

		tess.draw();
		tessellator.startDrawingQuads();
		tessellator.func_178980_d(0.0F, 1.0F, 0.0F);

		for (var10 = 0; var10 < sizeY; ++var10) {
			var11 = (float) var10 / (float) sizeY;
			var12 = minV + dV * var11 + var9;
			var13 = var11 + 1.0F / sizeY;
			tessellator.addVertexWithUV(0.0D, var13 * dimY, 0.0D, minU, var12);
			tessellator.addVertexWithUV(dimX, var13 * dimY, 0.0D, maxU, var12);
			tessellator.addVertexWithUV(dimX, var13 * dimY, width, maxU, var12);
			tessellator.addVertexWithUV(0.0D, var13 * dimY, width, minU, var12);
		}

		tess.draw();
		tessellator.startDrawingQuads();
		tessellator.func_178980_d(0.0F, -1.0F, 0.0F);

		for (var10 = 0; var10 < sizeY; ++var10) {
			var11 = (float) var10 / (float) sizeY;
			var12 = minV + dV * var11 + var9;
			tessellator.addVertexWithUV(dimX, var11 * dimY, 0.0D, maxU, var12);
			tessellator.addVertexWithUV(0.0D, var11 * dimY, 0.0D, minU, var12);
			tessellator.addVertexWithUV(0.0D, var11 * dimY, width, minU, var12);
			tessellator.addVertexWithUV(dimX, var11 * dimY, width, maxU, var12);
		}

		tess.draw();
	}
}
