package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.XRay;
import me.EaZy.client.utils.XRayUtils;
import optifine.BetterGrass;
import optifine.BetterSnow;
import optifine.Config;
import optifine.ConnectedTextures;
import optifine.CustomColors;
import optifine.NaturalTextures;
import optifine.Reflector;
import optifine.RenderEnv;
import optifine.SmartLeaves;

public class BlockModelRenderer {

	public static final int EaZy = 692;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static float aoLightValueOpaque = 0.2F;

	public static void updateAoLightValue() {
		aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
	}

	public BlockModelRenderer() {
		if (Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists()) {
			Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, false);
		}
	}

	public boolean func_178259_a(final IBlockAccess blockAccessIn, final IBakedModel modelIn,
			final IBlockState blockStateIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn) {
		final Block var6 = blockStateIn.getBlock();
		var6.setBlockBoundsBasedOnState(blockAccessIn, blockPosIn);
		return renderBlockModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, true);
	}

	public boolean renderBlockModel(final IBlockAccess blockAccessIn, IBakedModel modelIn,
			final IBlockState blockStateIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn,
			final boolean checkSides) {
		final boolean var7 = Minecraft.isAmbientOcclusionEnabled() && blockStateIn.getBlock().getLightValue() == 0
				&& modelIn.isGui3d();

		try {
			final Block var11 = blockStateIn.getBlock();

			if (Config.isTreesSmart() && blockStateIn.getBlock() instanceof BlockLeavesBase) {
				modelIn = SmartLeaves.getLeavesModel(modelIn);
			}

			return var7
					? renderModelAmbientOcclusion(blockAccessIn, modelIn, var11, blockStateIn, blockPosIn,
							worldRendererIn, checkSides)
					: renderModelStandard(blockAccessIn, modelIn, var11, blockStateIn, blockPosIn, worldRendererIn,
							checkSides);
		} catch (final Throwable var111) {
			final CrashReport var9 = CrashReport.makeCrashReport(var111, "Tesselating block model");
			final CrashReportCategory var10 = var9.makeCategory("Block model being tesselated");
			CrashReportCategory.addBlockInfo(var10, blockPosIn, blockStateIn);
			var10.addCrashSection("Using AO", var7);
			throw new ReportedException(var9);
		}
	}

	public boolean func_178265_a(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final Block blockIn,
			final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final boolean checkSides) {
		return renderModelAmbientOcclusion(blockAccessIn, modelIn, blockIn, blockAccessIn.getBlockState(blockPosIn),
				blockPosIn, worldRendererIn, checkSides);
	}

	public boolean renderModelAmbientOcclusion(final IBlockAccess blockAccessIn, final IBakedModel modelIn,
			final Block blockIn, final IBlockState blockStateIn, final BlockPos blockPosIn,
			final WorldRenderer worldRendererIn, final boolean checkSides) {
		boolean var7 = false;
		worldRendererIn.func_178963_b(983055);
		RenderEnv renderEnv = null;
		final EnumFacing[] var11 = EnumFacing.VALUES;
		final int var12 = var11.length;

		for (int var17 = 0; var17 < var12; ++var17) {
			final EnumFacing modelSnow = var11[var17];
			List stateSnow = modelIn.func_177551_a(modelSnow);

			if (!stateSnow.isEmpty()) {
				final BlockPos var16 = blockPosIn.offset(modelSnow);

				if (!checkSides || blockIn.shouldSideBeRendered(blockAccessIn, var16, modelSnow)) {
					if (renderEnv == null) {
						renderEnv = RenderEnv.getInstance(blockAccessIn, blockStateIn, blockPosIn);
					}

					if (!renderEnv.isBreakingAnimation(stateSnow) && Config.isBetterGrass()) {
						stateSnow = BetterGrass.getFaceQuads(blockAccessIn, blockIn, blockPosIn, modelSnow, stateSnow);
					}

					renderModelAmbientOcclusionQuads(blockAccessIn, blockIn, blockPosIn, worldRendererIn, stateSnow,
							renderEnv);
					var7 = true;
				}
			}
		}

		final List var161 = modelIn.func_177550_a();

		if (var161.size() > 0 && (!XRay.mod.isToggled() || XRayUtils.isXRayBlock(blockIn))) {
			if (renderEnv == null) {
				renderEnv = RenderEnv.getInstance(blockAccessIn, blockStateIn, blockPosIn);
			}

			renderModelAmbientOcclusionQuads(blockAccessIn, blockIn, blockPosIn, worldRendererIn, var161, renderEnv);
			var7 = true;
		}

		if (renderEnv != null && Config.isBetterSnow() && !renderEnv.isBreakingAnimation()
				&& BetterSnow.shouldRender(blockAccessIn, blockIn, blockStateIn, blockPosIn)) {
			final IBakedModel var171 = BetterSnow.getModelSnowLayer();
			final IBlockState var18 = BetterSnow.getStateSnowLayer();
			renderModelAmbientOcclusion(blockAccessIn, var171, var18.getBlock(), var18, blockPosIn, worldRendererIn,
					true);
		}

		return var7;
	}

	public boolean func_178258_b(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final Block blockIn,
			final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final boolean checkSides) {
		return renderModelStandard(blockAccessIn, modelIn, blockIn, blockAccessIn.getBlockState(blockPosIn), blockPosIn,
				worldRendererIn, checkSides);
	}

	public boolean renderModelStandard(final IBlockAccess blockAccessIn, final IBakedModel modelIn, final Block blockIn,
			final IBlockState blockStateIn, final BlockPos blockPosIn, final WorldRenderer worldRendererIn,
			final boolean checkSides) {
		boolean var7 = false;
		RenderEnv renderEnv = null;
		final EnumFacing[] var9 = EnumFacing.VALUES;
		final int var10 = var9.length;

		for (int var16 = 0; var16 < var10; ++var16) {
			final EnumFacing modelSnow = var9[var16];
			List stateSnow = modelIn.func_177551_a(modelSnow);

			if (!stateSnow.isEmpty()) {
				final BlockPos var14 = blockPosIn.offset(modelSnow);

				if (!checkSides || blockIn.shouldSideBeRendered(blockAccessIn, var14, modelSnow)) {
					if (renderEnv == null) {
						renderEnv = RenderEnv.getInstance(blockAccessIn, blockStateIn, blockPosIn);
					}

					if (!renderEnv.isBreakingAnimation(stateSnow) && Config.isBetterGrass()) {
						stateSnow = BetterGrass.getFaceQuads(blockAccessIn, blockIn, blockPosIn, modelSnow, stateSnow);
					}

					final int var15 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var14);
					renderModelStandardQuads(blockAccessIn, blockIn, blockPosIn, modelSnow, var15, false,
							worldRendererIn, stateSnow, renderEnv);
					var7 = true;
				}
			}
		}

		final List var17 = modelIn.func_177550_a();

		if (var17.size() > 0) {
			if (renderEnv == null) {
				renderEnv = RenderEnv.getInstance(blockAccessIn, blockStateIn, blockPosIn);
			}

			renderModelStandardQuads(blockAccessIn, blockIn, blockPosIn, (EnumFacing) null, -1, true, worldRendererIn,
					var17, renderEnv);
			var7 = true;
		}

		if (renderEnv != null && Config.isBetterSnow() && !renderEnv.isBreakingAnimation()
				&& BetterSnow.shouldRender(blockAccessIn, blockIn, blockStateIn, blockPosIn)
				&& BetterSnow.shouldRender(blockAccessIn, blockIn, blockStateIn, blockPosIn)) {
			final IBakedModel var18 = BetterSnow.getModelSnowLayer();
			final IBlockState var19 = BetterSnow.getStateSnowLayer();
			renderModelStandard(blockAccessIn, var18, var19.getBlock(), var19, blockPosIn, worldRendererIn, true);
		}

		return var7;
	}

	private void renderModelAmbientOcclusionQuads(final IBlockAccess blockAccessIn, final Block blockIn,
			final BlockPos blockPosIn, final WorldRenderer worldRendererIn, final List listQuadsIn,
			final RenderEnv renderEnv) {
		final float[] quadBounds = renderEnv.getQuadBounds();
		final BitSet boundsFlags = renderEnv.getBoundsFlags();
		final BlockModelRenderer.AmbientOcclusionFace aoFaceIn = renderEnv.getAoFace();
		final IBlockState blockStateIn = renderEnv.getBlockState();
		double var9 = blockPosIn.getX();
		double var11 = blockPosIn.getY();
		double var13 = blockPosIn.getZ();
		final Block.EnumOffsetType var15 = blockIn.getOffsetType();

		if (var15 != Block.EnumOffsetType.NONE) {
			final long var22 = MathHelper.func_180186_a(blockPosIn);
			var9 += ((var22 >> 16 & 15L) / 15.0F - 0.5D) * 0.5D;
			var13 += ((var22 >> 24 & 15L) / 15.0F - 0.5D) * 0.5D;

			if (var15 == Block.EnumOffsetType.XYZ) {
				var11 += ((var22 >> 20 & 15L) / 15.0F - 1.0D) * 0.2D;
			}
		}

		for (final Iterator var221 = listQuadsIn.iterator(); var221.hasNext(); worldRendererIn.func_178987_a(var9,
				var11, var13)) {
			BakedQuad var17 = (BakedQuad) var221.next();

			if (!renderEnv.isBreakingAnimation(var17)) {
				final BakedQuad colorMultiplier = var17;

				if (Config.isConnectedTextures()) {
					var17 = ConnectedTextures.getConnectedTexture(blockAccessIn, blockStateIn, blockPosIn, var17,
							renderEnv);
				}

				if (var17 == colorMultiplier && Config.isNaturalTextures()) {
					var17 = NaturalTextures.getNaturalTexture(blockPosIn, var17);
				}
			}

			func_178261_a(blockIn, var17.func_178209_a(), var17.getFace(), quadBounds, boundsFlags);
			aoFaceIn.func_178204_a(blockAccessIn, blockIn, blockPosIn, var17.getFace(), quadBounds, boundsFlags);

			if (worldRendererIn.isMultiTexture()) {
				worldRendererIn.func_178981_a(var17.getVertexDataSingle());
				worldRendererIn.putSprite(var17.getSprite());
			} else {
				worldRendererIn.func_178981_a(var17.func_178209_a());
			}

			worldRendererIn.func_178962_a(aoFaceIn.field_178207_c[0], aoFaceIn.field_178207_c[1],
					aoFaceIn.field_178207_c[2], aoFaceIn.field_178207_c[3]);
			final int colorMultiplier1 = CustomColors.getColorMultiplier(var17, blockIn, blockAccessIn, blockPosIn,
					renderEnv);

			if (!var17.func_178212_b() && colorMultiplier1 == -1) {
				worldRendererIn.func_178978_a(aoFaceIn.field_178206_b[0], aoFaceIn.field_178206_b[0],
						aoFaceIn.field_178206_b[0], 4);
				worldRendererIn.func_178978_a(aoFaceIn.field_178206_b[1], aoFaceIn.field_178206_b[1],
						aoFaceIn.field_178206_b[1], 3);
				worldRendererIn.func_178978_a(aoFaceIn.field_178206_b[2], aoFaceIn.field_178206_b[2],
						aoFaceIn.field_178206_b[2], 2);
				worldRendererIn.func_178978_a(aoFaceIn.field_178206_b[3], aoFaceIn.field_178206_b[3],
						aoFaceIn.field_178206_b[3], 1);
			} else {
				int var18;

				if (colorMultiplier1 != -1) {
					var18 = colorMultiplier1;
				} else {
					var18 = blockIn.colorMultiplier(blockAccessIn, blockPosIn, var17.func_178211_c());
				}

				if (EntityRenderer.anaglyphEnable) {
					var18 = TextureUtil.func_177054_c(var18);
				}

				final float var19 = (var18 >> 16 & 255) / 255.0F;
				final float var20 = (var18 >> 8 & 255) / 255.0F;
				final float var21 = (var18 & 255) / 255.0F;
				worldRendererIn.func_178978_a(aoFaceIn.field_178206_b[0] * var19, aoFaceIn.field_178206_b[0] * var20,
						aoFaceIn.field_178206_b[0] * var21, 4);
				worldRendererIn.func_178978_a(aoFaceIn.field_178206_b[1] * var19, aoFaceIn.field_178206_b[1] * var20,
						aoFaceIn.field_178206_b[1] * var21, 3);
				worldRendererIn.func_178978_a(aoFaceIn.field_178206_b[2] * var19, aoFaceIn.field_178206_b[2] * var20,
						aoFaceIn.field_178206_b[2] * var21, 2);
				worldRendererIn.func_178978_a(aoFaceIn.field_178206_b[3] * var19, aoFaceIn.field_178206_b[3] * var20,
						aoFaceIn.field_178206_b[3] * var21, 1);
			}
		}
	}

	private void func_178261_a(final Block blockIn, final int[] vertexData, final EnumFacing facingIn,
			final float[] quadBounds, final BitSet boundsFlags) {
		float var6 = 32.0F;
		float var7 = 32.0F;
		float var8 = 32.0F;
		float var9 = -32.0F;
		float var10 = -32.0F;
		float var11 = -32.0F;
		final int step = vertexData.length / 4;
		float var13;

		for (int var16 = 0; var16 < 4; ++var16) {
			var13 = Float.intBitsToFloat(vertexData[var16 * step]);
			final float var14 = Float.intBitsToFloat(vertexData[var16 * step + 1]);
			final float var15 = Float.intBitsToFloat(vertexData[var16 * step + 2]);
			var6 = Math.min(var6, var13);
			var7 = Math.min(var7, var14);
			var8 = Math.min(var8, var15);
			var9 = Math.max(var9, var13);
			var10 = Math.max(var10, var14);
			var11 = Math.max(var11, var15);
		}

		if (quadBounds != null) {
			quadBounds[EnumFacing.WEST.getIndex()] = var6;
			quadBounds[EnumFacing.EAST.getIndex()] = var9;
			quadBounds[EnumFacing.DOWN.getIndex()] = var7;
			quadBounds[EnumFacing.UP.getIndex()] = var10;
			quadBounds[EnumFacing.NORTH.getIndex()] = var8;
			quadBounds[EnumFacing.SOUTH.getIndex()] = var11;
			quadBounds[EnumFacing.WEST.getIndex() + EnumFacing.VALUES.length] = 1.0F - var6;
			quadBounds[EnumFacing.EAST.getIndex() + EnumFacing.VALUES.length] = 1.0F - var9;
			quadBounds[EnumFacing.DOWN.getIndex() + EnumFacing.VALUES.length] = 1.0F - var7;
			quadBounds[EnumFacing.UP.getIndex() + EnumFacing.VALUES.length] = 1.0F - var10;
			quadBounds[EnumFacing.NORTH.getIndex() + EnumFacing.VALUES.length] = 1.0F - var8;
			quadBounds[EnumFacing.SOUTH.getIndex() + EnumFacing.VALUES.length] = 1.0F - var11;
		}

		var13 = 0.9999F;

		switch (BlockModelRenderer.SwitchEnumFacing.field_178290_a[facingIn.ordinal()]) {
		case 1:
			boundsFlags.set(1, var6 >= 1.0E-4F || var8 >= 1.0E-4F || var9 <= 0.9999F || var11 <= 0.9999F);
			boundsFlags.set(0, (var7 < 1.0E-4F || blockIn.isFullCube()) && var7 == var10);
			break;

		case 2:
			boundsFlags.set(1, var6 >= 1.0E-4F || var8 >= 1.0E-4F || var9 <= 0.9999F || var11 <= 0.9999F);
			boundsFlags.set(0, (var10 > 0.9999F || blockIn.isFullCube()) && var7 == var10);
			break;

		case 3:
			boundsFlags.set(1, var6 >= 1.0E-4F || var7 >= 1.0E-4F || var9 <= 0.9999F || var10 <= 0.9999F);
			boundsFlags.set(0, (var8 < 1.0E-4F || blockIn.isFullCube()) && var8 == var11);
			break;

		case 4:
			boundsFlags.set(1, var6 >= 1.0E-4F || var7 >= 1.0E-4F || var9 <= 0.9999F || var10 <= 0.9999F);
			boundsFlags.set(0, (var11 > 0.9999F || blockIn.isFullCube()) && var8 == var11);
			break;

		case 5:
			boundsFlags.set(1, var7 >= 1.0E-4F || var8 >= 1.0E-4F || var10 <= 0.9999F || var11 <= 0.9999F);
			boundsFlags.set(0, (var6 < 1.0E-4F || blockIn.isFullCube()) && var6 == var9);
			break;

		case 6:
			boundsFlags.set(1, var7 >= 1.0E-4F || var8 >= 1.0E-4F || var10 <= 0.9999F || var11 <= 0.9999F);
			boundsFlags.set(0, (var9 > 0.9999F || blockIn.isFullCube()) && var6 == var9);
		}
	}

	private void renderModelStandardQuads(final IBlockAccess blockAccessIn, final Block blockIn,
			final BlockPos blockPosIn, final EnumFacing faceIn, int brightnessIn, final boolean ownBrightness,
			final WorldRenderer worldRendererIn, final List listQuadsIn, final RenderEnv renderEnv) {
		final BitSet boundsFlags = renderEnv.getBoundsFlags();
		final IBlockState blockStateIn = renderEnv.getBlockState();
		double var10 = blockPosIn.getX();
		double var12 = blockPosIn.getY();
		double var14 = blockPosIn.getZ();
		final Block.EnumOffsetType var16 = blockIn.getOffsetType();

		if (var16 != Block.EnumOffsetType.NONE) {
			final int var23 = blockPosIn.getX();
			final int var24 = blockPosIn.getZ();
			long colorMultiplier = var23 * 3129871 ^ var24 * 116129781L;
			colorMultiplier = colorMultiplier * colorMultiplier * 42317861L + colorMultiplier * 11L;
			var10 += ((colorMultiplier >> 16 & 15L) / 15.0F - 0.5D) * 0.5D;
			var14 += ((colorMultiplier >> 24 & 15L) / 15.0F - 0.5D) * 0.5D;

			if (var16 == Block.EnumOffsetType.XYZ) {
				var12 += ((colorMultiplier >> 20 & 15L) / 15.0F - 1.0D) * 0.2D;
			}
		}

		for (final Iterator var231 = listQuadsIn.iterator(); var231.hasNext(); worldRendererIn.func_178987_a(var10,
				var12, var14)) {
			BakedQuad var241 = (BakedQuad) var231.next();

			if (!renderEnv.isBreakingAnimation(var241)) {
				final BakedQuad colorMultiplier1 = var241;

				if (Config.isConnectedTextures()) {
					var241 = ConnectedTextures.getConnectedTexture(blockAccessIn, blockStateIn, blockPosIn, var241,
							renderEnv);
				}

				if (var241 == colorMultiplier1 && Config.isNaturalTextures()) {
					var241 = NaturalTextures.getNaturalTexture(blockPosIn, var241);
				}
			}

			if (ownBrightness) {
				func_178261_a(blockIn, var241.func_178209_a(), var241.getFace(), (float[]) null, boundsFlags);
				brightnessIn = boundsFlags.get(0)
						? blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn.offset(var241.getFace()))
						: blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn);
			}

			if (worldRendererIn.isMultiTexture()) {
				worldRendererIn.func_178981_a(var241.getVertexDataSingle());
				worldRendererIn.putSprite(var241.getSprite());
			} else {
				worldRendererIn.func_178981_a(var241.func_178209_a());
			}

			worldRendererIn.func_178962_a(brightnessIn, brightnessIn, brightnessIn, brightnessIn);
			final int colorMultiplier2 = CustomColors.getColorMultiplier(var241, blockIn, blockAccessIn, blockPosIn,
					renderEnv);

			if (var241.func_178212_b() || colorMultiplier2 != -1) {
				int var25;

				if (colorMultiplier2 != -1) {
					var25 = colorMultiplier2;
				} else {
					var25 = blockIn.colorMultiplier(blockAccessIn, blockPosIn, var241.func_178211_c());
				}

				if (EntityRenderer.anaglyphEnable) {
					var25 = TextureUtil.func_177054_c(var25);
				}

				final float var20 = (var25 >> 16 & 255) / 255.0F;
				final float var21 = (var25 >> 8 & 255) / 255.0F;
				final float var22 = (var25 & 255) / 255.0F;
				worldRendererIn.func_178978_a(var20, var21, var22, 4);
				worldRendererIn.func_178978_a(var20, var21, var22, 3);
				worldRendererIn.func_178978_a(var20, var21, var22, 2);
				worldRendererIn.func_178978_a(var20, var21, var22, 1);
			}
		}
	}

	public void func_178262_a(final IBakedModel p_178262_1_, final float p_178262_2_, final float p_178262_3_,
			final float p_178262_4_, final float p_178262_5_) {
		final EnumFacing[] var6 = EnumFacing.VALUES;
		final int var7 = var6.length;

		for (int var8 = 0; var8 < var7; ++var8) {
			final EnumFacing var9 = var6[var8];
			func_178264_a(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, p_178262_1_.func_177551_a(var9));
		}

		func_178264_a(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, p_178262_1_.func_177550_a());
	}

	public void func_178266_a(final IBakedModel p_178266_1_, final IBlockState p_178266_2_, final float p_178266_3_,
			final boolean p_178266_4_) {
		final Block var5 = p_178266_2_.getBlock();
		var5.setBlockBoundsForItemRender();
		GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
		int var6 = var5.getRenderColor(var5.getStateForEntityRender(p_178266_2_));

		if (EntityRenderer.anaglyphEnable) {
			var6 = TextureUtil.func_177054_c(var6);
		}

		final float var7 = (var6 >> 16 & 255) / 255.0F;
		final float var8 = (var6 >> 8 & 255) / 255.0F;
		final float var9 = (var6 & 255) / 255.0F;

		if (!p_178266_4_) {
			GlStateManager.color(p_178266_3_, p_178266_3_, p_178266_3_, 1.0F);
		}

		func_178262_a(p_178266_1_, p_178266_3_, var7, var8, var9);
	}

	private void func_178264_a(final float p_178264_1_, final float p_178264_2_, final float p_178264_3_,
			final float p_178264_4_, final List p_178264_5_) {
		final Tessellator var6 = Tessellator.getInstance();
		final WorldRenderer var7 = var6.getWorldRenderer();
		final Iterator var8 = p_178264_5_.iterator();

		while (var8.hasNext()) {
			final BakedQuad var9 = (BakedQuad) var8.next();
			var7.startDrawingQuads();
			var7.setVertexFormat(DefaultVertexFormats.field_176599_b);
			var7.func_178981_a(var9.func_178209_a());

			if (var9.func_178212_b()) {
				var7.func_178990_f(p_178264_2_ * p_178264_1_, p_178264_3_ * p_178264_1_, p_178264_4_ * p_178264_1_);
			} else {
				var7.func_178990_f(p_178264_1_, p_178264_1_, p_178264_1_);
			}

			final Vec3i var10 = var9.getFace().getDirectionVec();
			var7.func_178975_e(var10.getX(), var10.getY(), var10.getZ());
			var6.draw();
		}
	}

	public static float fixAoLightValue(final float val) {
		return val == 0.2F ? aoLightValueOpaque : val;
	}

	public static class AmbientOcclusionFace {
		private final float[] field_178206_b = new float[4];
		private final int[] field_178207_c = new int[4];

		public AmbientOcclusionFace(final BlockModelRenderer bmr) {}

		public AmbientOcclusionFace() {}

		public void func_178204_a(final IBlockAccess blockAccessIn, final Block blockIn, final BlockPos blockPosIn,
				final EnumFacing facingIn, final float[] quadBounds, final BitSet boundsFlags) {
			final BlockPos var7 = boundsFlags.get(0) ? blockPosIn.offset(facingIn) : blockPosIn;
			final BlockModelRenderer.EnumNeighborInfo var8 = BlockModelRenderer.EnumNeighborInfo
					.func_178273_a(facingIn);
			final BlockPos var9 = var7.offset(var8.field_178276_g[0]);
			final BlockPos var10 = var7.offset(var8.field_178276_g[1]);
			final BlockPos var11 = var7.offset(var8.field_178276_g[2]);
			final BlockPos var12 = var7.offset(var8.field_178276_g[3]);
			final int var13 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var9);
			final int var14 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var10);
			final int var15 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var11);
			final int var16 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var12);
			final float var17 = BlockModelRenderer
					.fixAoLightValue(blockAccessIn.getBlockState(var9).getBlock().getAmbientOcclusionLightValue());
			final float var18 = BlockModelRenderer
					.fixAoLightValue(blockAccessIn.getBlockState(var10).getBlock().getAmbientOcclusionLightValue());
			final float var19 = BlockModelRenderer
					.fixAoLightValue(blockAccessIn.getBlockState(var11).getBlock().getAmbientOcclusionLightValue());
			final float var20 = BlockModelRenderer
					.fixAoLightValue(blockAccessIn.getBlockState(var12).getBlock().getAmbientOcclusionLightValue());
			final boolean var21 = blockAccessIn.getBlockState(var9.offset(facingIn)).getBlock().isTranslucent();
			final boolean var22 = blockAccessIn.getBlockState(var10.offset(facingIn)).getBlock().isTranslucent();
			final boolean var23 = blockAccessIn.getBlockState(var11.offset(facingIn)).getBlock().isTranslucent();
			final boolean var24 = blockAccessIn.getBlockState(var12.offset(facingIn)).getBlock().isTranslucent();
			float var25;
			int var29;
			BlockPos var33;

			if (!var23 && !var21) {
				var25 = var17;
				var29 = var13;
			} else {
				var33 = var9.offset(var8.field_178276_g[2]);
				var25 = BlockModelRenderer
						.fixAoLightValue(blockAccessIn.getBlockState(var33).getBlock().getAmbientOcclusionLightValue());
				var29 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var33);
			}

			float var26;
			int var30;

			if (!var24 && !var21) {
				var26 = var17;
				var30 = var13;
			} else {
				var33 = var9.offset(var8.field_178276_g[3]);
				var26 = BlockModelRenderer
						.fixAoLightValue(blockAccessIn.getBlockState(var33).getBlock().getAmbientOcclusionLightValue());
				var30 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var33);
			}

			float var27;
			int var31;

			if (!var23 && !var22) {
				var27 = var18;
				var31 = var14;
			} else {
				var33 = var10.offset(var8.field_178276_g[2]);
				var27 = BlockModelRenderer
						.fixAoLightValue(blockAccessIn.getBlockState(var33).getBlock().getAmbientOcclusionLightValue());
				var31 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var33);
			}

			float var28;
			int var32;

			if (!var24 && !var22) {
				var28 = var18;
				var32 = var14;
			} else {
				var33 = var10.offset(var8.field_178276_g[3]);
				var28 = BlockModelRenderer
						.fixAoLightValue(blockAccessIn.getBlockState(var33).getBlock().getAmbientOcclusionLightValue());
				var32 = blockIn.getMixedBrightnessForBlock(blockAccessIn, var33);
			}

			int var60 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn);

			if (boundsFlags.get(0)
					|| !blockAccessIn.getBlockState(blockPosIn.offset(facingIn)).getBlock().isOpaqueCube()) {
				var60 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn.offset(facingIn));
			}

			float var34 = boundsFlags.get(0)
					? blockAccessIn.getBlockState(var7).getBlock().getAmbientOcclusionLightValue()
					: blockAccessIn.getBlockState(blockPosIn).getBlock().getAmbientOcclusionLightValue();
			var34 = BlockModelRenderer.fixAoLightValue(var34);
			final BlockModelRenderer.VertexTranslations var35 = BlockModelRenderer.VertexTranslations
					.func_178184_a(facingIn);
			float var36;
			float var37;
			float var38;
			float var39;

			if (boundsFlags.get(1) && var8.field_178289_i) {
				var36 = (var20 + var17 + var26 + var34) * 0.25F;
				var37 = (var19 + var17 + var25 + var34) * 0.25F;
				var38 = (var19 + var18 + var27 + var34) * 0.25F;
				var39 = (var20 + var18 + var28 + var34) * 0.25F;
				final float var40 = quadBounds[var8.field_178286_j[0].field_178229_m]
						* quadBounds[var8.field_178286_j[1].field_178229_m];
				final float var41 = quadBounds[var8.field_178286_j[2].field_178229_m]
						* quadBounds[var8.field_178286_j[3].field_178229_m];
				final float var42 = quadBounds[var8.field_178286_j[4].field_178229_m]
						* quadBounds[var8.field_178286_j[5].field_178229_m];
				final float var43 = quadBounds[var8.field_178286_j[6].field_178229_m]
						* quadBounds[var8.field_178286_j[7].field_178229_m];
				final float var44 = quadBounds[var8.field_178287_k[0].field_178229_m]
						* quadBounds[var8.field_178287_k[1].field_178229_m];
				final float var45 = quadBounds[var8.field_178287_k[2].field_178229_m]
						* quadBounds[var8.field_178287_k[3].field_178229_m];
				final float var46 = quadBounds[var8.field_178287_k[4].field_178229_m]
						* quadBounds[var8.field_178287_k[5].field_178229_m];
				final float var47 = quadBounds[var8.field_178287_k[6].field_178229_m]
						* quadBounds[var8.field_178287_k[7].field_178229_m];
				final float var48 = quadBounds[var8.field_178284_l[0].field_178229_m]
						* quadBounds[var8.field_178284_l[1].field_178229_m];
				final float var49 = quadBounds[var8.field_178284_l[2].field_178229_m]
						* quadBounds[var8.field_178284_l[3].field_178229_m];
				final float var50 = quadBounds[var8.field_178284_l[4].field_178229_m]
						* quadBounds[var8.field_178284_l[5].field_178229_m];
				final float var51 = quadBounds[var8.field_178284_l[6].field_178229_m]
						* quadBounds[var8.field_178284_l[7].field_178229_m];
				final float var52 = quadBounds[var8.field_178285_m[0].field_178229_m]
						* quadBounds[var8.field_178285_m[1].field_178229_m];
				final float var53 = quadBounds[var8.field_178285_m[2].field_178229_m]
						* quadBounds[var8.field_178285_m[3].field_178229_m];
				final float var54 = quadBounds[var8.field_178285_m[4].field_178229_m]
						* quadBounds[var8.field_178285_m[5].field_178229_m];
				final float var55 = quadBounds[var8.field_178285_m[6].field_178229_m]
						* quadBounds[var8.field_178285_m[7].field_178229_m];
				field_178206_b[var35.field_178191_g] = var36 * var40 + var37 * var41 + var38 * var42 + var39 * var43;
				field_178206_b[var35.field_178200_h] = var36 * var44 + var37 * var45 + var38 * var46 + var39 * var47;
				field_178206_b[var35.field_178201_i] = var36 * var48 + var37 * var49 + var38 * var50 + var39 * var51;
				field_178206_b[var35.field_178198_j] = var36 * var52 + var37 * var53 + var38 * var54 + var39 * var55;
				final int var56 = getAoBrightness(var16, var13, var30, var60);
				final int var57 = getAoBrightness(var15, var13, var29, var60);
				final int var58 = getAoBrightness(var15, var14, var31, var60);
				final int var59 = getAoBrightness(var16, var14, var32, var60);
				field_178207_c[var35.field_178191_g] = func_178203_a(var56, var57, var58, var59, var40, var41, var42,
						var43);
				field_178207_c[var35.field_178200_h] = func_178203_a(var56, var57, var58, var59, var44, var45, var46,
						var47);
				field_178207_c[var35.field_178201_i] = func_178203_a(var56, var57, var58, var59, var48, var49, var50,
						var51);
				field_178207_c[var35.field_178198_j] = func_178203_a(var56, var57, var58, var59, var52, var53, var54,
						var55);
			} else {
				var36 = (var20 + var17 + var26 + var34) * 0.25F;
				var37 = (var19 + var17 + var25 + var34) * 0.25F;
				var38 = (var19 + var18 + var27 + var34) * 0.25F;
				var39 = (var20 + var18 + var28 + var34) * 0.25F;
				field_178207_c[var35.field_178191_g] = getAoBrightness(var16, var13, var30, var60);
				field_178207_c[var35.field_178200_h] = getAoBrightness(var15, var13, var29, var60);
				field_178207_c[var35.field_178201_i] = getAoBrightness(var15, var14, var31, var60);
				field_178207_c[var35.field_178198_j] = getAoBrightness(var16, var14, var32, var60);
				field_178206_b[var35.field_178191_g] = var36;
				field_178206_b[var35.field_178200_h] = var37;
				field_178206_b[var35.field_178201_i] = var38;
				field_178206_b[var35.field_178198_j] = var39;
			}
		}

		private int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, final int p_147778_4_) {
			if (p_147778_1_ == 0) {
				p_147778_1_ = p_147778_4_;
			}

			if (p_147778_2_ == 0) {
				p_147778_2_ = p_147778_4_;
			}

			if (p_147778_3_ == 0) {
				p_147778_3_ = p_147778_4_;
			}

			return p_147778_1_ + p_147778_2_ + p_147778_3_ + p_147778_4_ >> 2 & 16711935;
		}

		private int func_178203_a(final int p_178203_1_, final int p_178203_2_, final int p_178203_3_,
				final int p_178203_4_, final float p_178203_5_, final float p_178203_6_, final float p_178203_7_,
				final float p_178203_8_) {
			final int var9 = (int) ((p_178203_1_ >> 16 & 255) * p_178203_5_ + (p_178203_2_ >> 16 & 255) * p_178203_6_
					+ (p_178203_3_ >> 16 & 255) * p_178203_7_ + (p_178203_4_ >> 16 & 255) * p_178203_8_) & 255;
			final int var10 = (int) ((p_178203_1_ & 255) * p_178203_5_ + (p_178203_2_ & 255) * p_178203_6_
					+ (p_178203_3_ & 255) * p_178203_7_ + (p_178203_4_ & 255) * p_178203_8_) & 255;
			return var9 << 16 | var10;
		}
	}

	public static enum EnumNeighborInfo {
		DOWN("DOWN", 0, "DOWN", 0,
				new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.5F, true,
				new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_WEST,
						BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_WEST,
						BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST,
						BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.WEST,
						BlockModelRenderer.Orientation.SOUTH },
				new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_WEST,
						BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_WEST,
						BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST,
						BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.WEST,
						BlockModelRenderer.Orientation.NORTH },
				new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_EAST,
						BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_EAST,
						BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST,
						BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.EAST,
						BlockModelRenderer.Orientation.NORTH },
				new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_EAST,
						BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_EAST,
						BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST,
						BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.EAST,
						BlockModelRenderer.Orientation.SOUTH }), UP("UP", 1, "UP",
								1,
								new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH,
										EnumFacing.SOUTH },
								1.0F, true,
								new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.EAST,
										BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.EAST,
										BlockModelRenderer.Orientation.FLIP_SOUTH,
										BlockModelRenderer.Orientation.FLIP_EAST,
										BlockModelRenderer.Orientation.FLIP_SOUTH,
										BlockModelRenderer.Orientation.FLIP_EAST,
										BlockModelRenderer.Orientation.SOUTH },
								new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.EAST,
										BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.EAST,
										BlockModelRenderer.Orientation.FLIP_NORTH,
										BlockModelRenderer.Orientation.FLIP_EAST,
										BlockModelRenderer.Orientation.FLIP_NORTH,
										BlockModelRenderer.Orientation.FLIP_EAST,
										BlockModelRenderer.Orientation.NORTH },
								new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.WEST,
										BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.WEST,
										BlockModelRenderer.Orientation.FLIP_NORTH,
										BlockModelRenderer.Orientation.FLIP_WEST,
										BlockModelRenderer.Orientation.FLIP_NORTH,
										BlockModelRenderer.Orientation.FLIP_WEST,
										BlockModelRenderer.Orientation.NORTH },
								new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.WEST,
										BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.WEST,
										BlockModelRenderer.Orientation.FLIP_SOUTH,
										BlockModelRenderer.Orientation.FLIP_WEST,
										BlockModelRenderer.Orientation.FLIP_SOUTH,
										BlockModelRenderer.Orientation.FLIP_WEST,
										BlockModelRenderer.Orientation.SOUTH }), NORTH("NORTH", 2, "NORTH",
												2,
												new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST,
														EnumFacing.WEST },
												0.8F, true,
												new BlockModelRenderer.Orientation[] {
														BlockModelRenderer.Orientation.UP,
														BlockModelRenderer.Orientation.FLIP_WEST,
														BlockModelRenderer.Orientation.UP,
														BlockModelRenderer.Orientation.WEST,
														BlockModelRenderer.Orientation.FLIP_UP,
														BlockModelRenderer.Orientation.WEST,
														BlockModelRenderer.Orientation.FLIP_UP,
														BlockModelRenderer.Orientation.FLIP_WEST },
												new BlockModelRenderer.Orientation[] {
														BlockModelRenderer.Orientation.UP,
														BlockModelRenderer.Orientation.FLIP_EAST,
														BlockModelRenderer.Orientation.UP,
														BlockModelRenderer.Orientation.EAST,
														BlockModelRenderer.Orientation.FLIP_UP,
														BlockModelRenderer.Orientation.EAST,
														BlockModelRenderer.Orientation.FLIP_UP,
														BlockModelRenderer.Orientation.FLIP_EAST },
												new BlockModelRenderer.Orientation[] {
														BlockModelRenderer.Orientation.DOWN,
														BlockModelRenderer.Orientation.FLIP_EAST,
														BlockModelRenderer.Orientation.DOWN,
														BlockModelRenderer.Orientation.EAST,
														BlockModelRenderer.Orientation.FLIP_DOWN,
														BlockModelRenderer.Orientation.EAST,
														BlockModelRenderer.Orientation.FLIP_DOWN,
														BlockModelRenderer.Orientation.FLIP_EAST },
												new BlockModelRenderer.Orientation[] {
														BlockModelRenderer.Orientation.DOWN,
														BlockModelRenderer.Orientation.FLIP_WEST,
														BlockModelRenderer.Orientation.DOWN,
														BlockModelRenderer.Orientation.WEST,
														BlockModelRenderer.Orientation.FLIP_DOWN,
														BlockModelRenderer.Orientation.WEST,
														BlockModelRenderer.Orientation.FLIP_DOWN,
														BlockModelRenderer.Orientation.FLIP_WEST }), SOUTH("SOUTH", 3,
																"SOUTH", 3,
																new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST,
																		EnumFacing.DOWN, EnumFacing.UP },
																0.8F, true,
																new BlockModelRenderer.Orientation[] {
																		BlockModelRenderer.Orientation.UP,
																		BlockModelRenderer.Orientation.FLIP_WEST,
																		BlockModelRenderer.Orientation.FLIP_UP,
																		BlockModelRenderer.Orientation.FLIP_WEST,
																		BlockModelRenderer.Orientation.FLIP_UP,
																		BlockModelRenderer.Orientation.WEST,
																		BlockModelRenderer.Orientation.UP,
																		BlockModelRenderer.Orientation.WEST },
																new BlockModelRenderer.Orientation[] {
																		BlockModelRenderer.Orientation.DOWN,
																		BlockModelRenderer.Orientation.FLIP_WEST,
																		BlockModelRenderer.Orientation.FLIP_DOWN,
																		BlockModelRenderer.Orientation.FLIP_WEST,
																		BlockModelRenderer.Orientation.FLIP_DOWN,
																		BlockModelRenderer.Orientation.WEST,
																		BlockModelRenderer.Orientation.DOWN,
																		BlockModelRenderer.Orientation.WEST },
																new BlockModelRenderer.Orientation[] {
																		BlockModelRenderer.Orientation.DOWN,
																		BlockModelRenderer.Orientation.FLIP_EAST,
																		BlockModelRenderer.Orientation.FLIP_DOWN,
																		BlockModelRenderer.Orientation.FLIP_EAST,
																		BlockModelRenderer.Orientation.FLIP_DOWN,
																		BlockModelRenderer.Orientation.EAST,
																		BlockModelRenderer.Orientation.DOWN,
																		BlockModelRenderer.Orientation.EAST },
																new BlockModelRenderer.Orientation[] {
																		BlockModelRenderer.Orientation.UP,
																		BlockModelRenderer.Orientation.FLIP_EAST,
																		BlockModelRenderer.Orientation.FLIP_UP,
																		BlockModelRenderer.Orientation.FLIP_EAST,
																		BlockModelRenderer.Orientation.FLIP_UP,
																		BlockModelRenderer.Orientation.EAST,
																		BlockModelRenderer.Orientation.UP,
																		BlockModelRenderer.Orientation.EAST }), WEST(
																				"WEST", 4, "WEST", 4,
																				new EnumFacing[] { EnumFacing.UP,
																						EnumFacing.DOWN,
																						EnumFacing.NORTH,
																						EnumFacing.SOUTH },
																				0.6F, true,
																				new BlockModelRenderer.Orientation[] {
																						BlockModelRenderer.Orientation.UP,
																						BlockModelRenderer.Orientation.SOUTH,
																						BlockModelRenderer.Orientation.UP,
																						BlockModelRenderer.Orientation.FLIP_SOUTH,
																						BlockModelRenderer.Orientation.FLIP_UP,
																						BlockModelRenderer.Orientation.FLIP_SOUTH,
																						BlockModelRenderer.Orientation.FLIP_UP,
																						BlockModelRenderer.Orientation.SOUTH },
																				new BlockModelRenderer.Orientation[] {
																						BlockModelRenderer.Orientation.UP,
																						BlockModelRenderer.Orientation.NORTH,
																						BlockModelRenderer.Orientation.UP,
																						BlockModelRenderer.Orientation.FLIP_NORTH,
																						BlockModelRenderer.Orientation.FLIP_UP,
																						BlockModelRenderer.Orientation.FLIP_NORTH,
																						BlockModelRenderer.Orientation.FLIP_UP,
																						BlockModelRenderer.Orientation.NORTH },
																				new BlockModelRenderer.Orientation[] {
																						BlockModelRenderer.Orientation.DOWN,
																						BlockModelRenderer.Orientation.NORTH,
																						BlockModelRenderer.Orientation.DOWN,
																						BlockModelRenderer.Orientation.FLIP_NORTH,
																						BlockModelRenderer.Orientation.FLIP_DOWN,
																						BlockModelRenderer.Orientation.FLIP_NORTH,
																						BlockModelRenderer.Orientation.FLIP_DOWN,
																						BlockModelRenderer.Orientation.NORTH },
																				new BlockModelRenderer.Orientation[] {
																						BlockModelRenderer.Orientation.DOWN,
																						BlockModelRenderer.Orientation.SOUTH,
																						BlockModelRenderer.Orientation.DOWN,
																						BlockModelRenderer.Orientation.FLIP_SOUTH,
																						BlockModelRenderer.Orientation.FLIP_DOWN,
																						BlockModelRenderer.Orientation.FLIP_SOUTH,
																						BlockModelRenderer.Orientation.FLIP_DOWN,
																						BlockModelRenderer.Orientation.SOUTH }), EAST(
																								"EAST", 5, "EAST", 5,
																								new EnumFacing[] {
																										EnumFacing.DOWN,
																										EnumFacing.UP,
																										EnumFacing.NORTH,
																										EnumFacing.SOUTH },
																								0.6F, true,
																								new BlockModelRenderer.Orientation[] {
																										BlockModelRenderer.Orientation.FLIP_DOWN,
																										BlockModelRenderer.Orientation.SOUTH,
																										BlockModelRenderer.Orientation.FLIP_DOWN,
																										BlockModelRenderer.Orientation.FLIP_SOUTH,
																										BlockModelRenderer.Orientation.DOWN,
																										BlockModelRenderer.Orientation.FLIP_SOUTH,
																										BlockModelRenderer.Orientation.DOWN,
																										BlockModelRenderer.Orientation.SOUTH },
																								new BlockModelRenderer.Orientation[] {
																										BlockModelRenderer.Orientation.FLIP_DOWN,
																										BlockModelRenderer.Orientation.NORTH,
																										BlockModelRenderer.Orientation.FLIP_DOWN,
																										BlockModelRenderer.Orientation.FLIP_NORTH,
																										BlockModelRenderer.Orientation.DOWN,
																										BlockModelRenderer.Orientation.FLIP_NORTH,
																										BlockModelRenderer.Orientation.DOWN,
																										BlockModelRenderer.Orientation.NORTH },
																								new BlockModelRenderer.Orientation[] {
																										BlockModelRenderer.Orientation.FLIP_UP,
																										BlockModelRenderer.Orientation.NORTH,
																										BlockModelRenderer.Orientation.FLIP_UP,
																										BlockModelRenderer.Orientation.FLIP_NORTH,
																										BlockModelRenderer.Orientation.UP,
																										BlockModelRenderer.Orientation.FLIP_NORTH,
																										BlockModelRenderer.Orientation.UP,
																										BlockModelRenderer.Orientation.NORTH },
																								new BlockModelRenderer.Orientation[] {
																										BlockModelRenderer.Orientation.FLIP_UP,
																										BlockModelRenderer.Orientation.SOUTH,
																										BlockModelRenderer.Orientation.FLIP_UP,
																										BlockModelRenderer.Orientation.FLIP_SOUTH,
																										BlockModelRenderer.Orientation.UP,
																										BlockModelRenderer.Orientation.FLIP_SOUTH,
																										BlockModelRenderer.Orientation.UP,
																										BlockModelRenderer.Orientation.SOUTH });
		protected final EnumFacing[] field_178276_g;
		protected final float field_178288_h;
		protected final boolean field_178289_i;
		protected final BlockModelRenderer.Orientation[] field_178286_j;
		protected final BlockModelRenderer.Orientation[] field_178287_k;
		protected final BlockModelRenderer.Orientation[] field_178284_l;
		protected final BlockModelRenderer.Orientation[] field_178285_m;
		private static final BlockModelRenderer.EnumNeighborInfo[] field_178282_n = new BlockModelRenderer.EnumNeighborInfo[6];

		private EnumNeighborInfo(final String p_i46381_1_, final int p_i46381_2_, final String p_i46236_1_,
				final int p_i46236_2_, final EnumFacing[] p_i46236_3_, final float p_i46236_4_,
				final boolean p_i46236_5_, final BlockModelRenderer.Orientation[] p_i46236_6_,
				final BlockModelRenderer.Orientation[] p_i46236_7_, final BlockModelRenderer.Orientation[] p_i46236_8_,
				final BlockModelRenderer.Orientation[] p_i46236_9_) {
			field_178276_g = p_i46236_3_;
			field_178288_h = p_i46236_4_;
			field_178289_i = p_i46236_5_;
			field_178286_j = p_i46236_6_;
			field_178287_k = p_i46236_7_;
			field_178284_l = p_i46236_8_;
			field_178285_m = p_i46236_9_;
		}

		public static BlockModelRenderer.EnumNeighborInfo func_178273_a(final EnumFacing p_178273_0_) {
			return field_178282_n[p_178273_0_.getIndex()];
		}

		static {
			field_178282_n[EnumFacing.DOWN.getIndex()] = DOWN;
			field_178282_n[EnumFacing.UP.getIndex()] = UP;
			field_178282_n[EnumFacing.NORTH.getIndex()] = NORTH;
			field_178282_n[EnumFacing.SOUTH.getIndex()] = SOUTH;
			field_178282_n[EnumFacing.WEST.getIndex()] = WEST;
			field_178282_n[EnumFacing.EAST.getIndex()] = EAST;
		}
	}

	public static enum Orientation {
		DOWN("DOWN", 0, "DOWN", 0, EnumFacing.DOWN, false), UP("UP", 1, "UP", 1, EnumFacing.UP, false), NORTH("NORTH",
				2, "NORTH", 2, EnumFacing.NORTH, false), SOUTH("SOUTH", 3, "SOUTH", 3, EnumFacing.SOUTH, false), WEST(
						"WEST", 4, "WEST", 4, EnumFacing.WEST,
						false), EAST("EAST", 5, "EAST", 5, EnumFacing.EAST, false), FLIP_DOWN("FLIP_DOWN", 6,
								"FLIP_DOWN", 6, EnumFacing.DOWN, true), FLIP_UP("FLIP_UP", 7, "FLIP_UP", 7,
										EnumFacing.UP, true), FLIP_NORTH("FLIP_NORTH", 8, "FLIP_NORTH", 8,
												EnumFacing.NORTH, true), FLIP_SOUTH("FLIP_SOUTH", 9, "FLIP_SOUTH", 9,
														EnumFacing.SOUTH, true), FLIP_WEST("FLIP_WEST", 10, "FLIP_WEST",
																10, EnumFacing.WEST, true), FLIP_EAST("FLIP_EAST", 11,
																		"FLIP_EAST", 11, EnumFacing.EAST, true);
		protected final int field_178229_m;

		private Orientation(final String p_i46383_1_, final int p_i46383_2_, final String p_i46233_1_,
				final int p_i46233_2_, final EnumFacing p_i46233_3_, final boolean p_i46233_4_) {
			field_178229_m = p_i46233_3_.getIndex() + (p_i46233_4_ ? EnumFacing.values().length : 0);
		}
	}

	static final class SwitchEnumFacing {
		static final int[] field_178290_a = new int[EnumFacing.values().length];
		static {
			try {
				field_178290_a[EnumFacing.DOWN.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {}

			try {
				field_178290_a[EnumFacing.UP.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {}

			try {
				field_178290_a[EnumFacing.NORTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {}

			try {
				field_178290_a[EnumFacing.SOUTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {}

			try {
				field_178290_a[EnumFacing.WEST.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {}

			try {
				field_178290_a[EnumFacing.EAST.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {}
		}
	}

	static enum VertexTranslations {
		DOWN("DOWN", 0, "DOWN", 0, 0, 1, 2, 3), UP("UP", 1, "UP", 1, 2, 3, 0, 1), NORTH("NORTH", 2, "NORTH", 2, 3, 0, 1,
				2), SOUTH("SOUTH", 3, "SOUTH", 3, 0, 1, 2,
						3), WEST("WEST", 4, "WEST", 4, 3, 0, 1, 2), EAST("EAST", 5, "EAST", 5, 1, 2, 3, 0);
		private final int field_178191_g;
		private final int field_178200_h;
		private final int field_178201_i;
		private final int field_178198_j;
		private static final BlockModelRenderer.VertexTranslations[] field_178199_k = new BlockModelRenderer.VertexTranslations[6];

		private VertexTranslations(final String p_i46382_1_, final int p_i46382_2_, final String p_i46234_1_,
				final int p_i46234_2_, final int p_i46234_3_, final int p_i46234_4_, final int p_i46234_5_,
				final int p_i46234_6_) {
			field_178191_g = p_i46234_3_;
			field_178200_h = p_i46234_4_;
			field_178201_i = p_i46234_5_;
			field_178198_j = p_i46234_6_;
		}

		public static BlockModelRenderer.VertexTranslations func_178184_a(final EnumFacing p_178184_0_) {
			return field_178199_k[p_178184_0_.getIndex()];
		}

		static {
			field_178199_k[EnumFacing.DOWN.getIndex()] = DOWN;
			field_178199_k[EnumFacing.UP.getIndex()] = UP;
			field_178199_k[EnumFacing.NORTH.getIndex()] = NORTH;
			field_178199_k[EnumFacing.SOUTH.getIndex()] = SOUTH;
			field_178199_k[EnumFacing.WEST.getIndex()] = WEST;
			field_178199_k[EnumFacing.EAST.getIndex()] = EAST;
		}
	}
}
