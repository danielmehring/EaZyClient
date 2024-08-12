package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;

import me.EaZy.client.Configs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import optifine.Config;
import optifine.DynamicLights;
import optifine.Reflector;
import shadersmod.client.Shaders;

public class ItemRenderer {

public static final int EaZy = 803;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
	private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");

	/** A reference to the Minecraft object. */
	private final Minecraft mc;
	private ItemStack itemToRender;

	/**
	 * How far the current item has been equipped (0 disequipped and 1 fully up)
	 */
	private float equippedProgress;
	private float prevEquippedProgress;
	private final RenderManager field_178111_g;
	private final RenderItem itemRenderer;

	/** The index of the currently held item (0-8, or -1 if not yet updated) */
	private int equippedItemSlot = -1;

	public ItemRenderer(final Minecraft mcIn) {
		mc = mcIn;
		field_178111_g = Minecraft.getRenderManager();
		itemRenderer = mcIn.getRenderItem();
	}

	public void renderItem(final EntityLivingBase entity, final ItemStack item,
			final ItemCameraTransforms.TransformType mode) {
		if (item != null) {
			final Item var4 = item.getItem();
			final Block var5 = Block.getBlockFromItem(var4);
			GlStateManager.pushMatrix();

			if (itemRenderer.func_175050_a(item)) {
				GlStateManager.scale(2.0F, 2.0F, 2.0F);

				if (func_178107_a(var5)) {
					GlStateManager.depthMask(false);
				}
			}

			itemRenderer.func_175049_a(item, entity, mode);

			if (func_178107_a(var5)) {
				GlStateManager.depthMask(true);
			}

			GlStateManager.popMatrix();
		}
	}

	private boolean func_178107_a(final Block p_178107_1_) {
		return p_178107_1_ != null && p_178107_1_.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
	}

	private void func_178101_a(final float p_178101_1_, final float p_178101_2_) {
		GlStateManager.pushMatrix();
		GlStateManager.rotate(p_178101_1_, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(p_178101_2_, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
	}

	private void func_178109_a(final AbstractClientPlayer p_178109_1_) {
		int var2 = Minecraft.theWorld.getCombinedLight(
				new BlockPos(p_178109_1_.posX, p_178109_1_.posY + p_178109_1_.getEyeHeight(), p_178109_1_.posZ), 0);

		if (Config.isDynamicLights()) {
			var2 = DynamicLights.getCombinedLight(mc.func_175606_aa(), var2);
		}

		final float var3 = var2 & 65535;
		final float var4 = var2 >> 16;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var3, var4);
	}

	private void func_178110_a(final EntityPlayerSP p_178110_1_, final float p_178110_2_) {
		final float var3 = p_178110_1_.prevRenderArmPitch
				+ (p_178110_1_.renderArmPitch - p_178110_1_.prevRenderArmPitch) * p_178110_2_;
		final float var4 = p_178110_1_.prevRenderArmYaw
				+ (p_178110_1_.renderArmYaw - p_178110_1_.prevRenderArmYaw) * p_178110_2_;
		GlStateManager.rotate((p_178110_1_.rotationPitch - var3) * 0.1F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate((p_178110_1_.rotationYaw - var4) * 0.1F, 0.0F, 1.0F, 0.0F);
	}

	private float func_178100_c(final float p_178100_1_) {
		float var2 = 1.0F - p_178100_1_ / 45.0F + 0.1F;
		var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
		var2 = -MathHelper.cos(var2 * (float) Math.PI) * 0.5F + 0.5F;
		return var2;
	}

	private void func_180534_a(final RenderPlayer p_180534_1_) {
		GlStateManager.pushMatrix();
		GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.translate(0.25F, -0.85F, 0.75F);
		p_180534_1_.func_177138_b(Minecraft.thePlayer);
		GlStateManager.popMatrix();
	}

	private void func_178106_b(final RenderPlayer p_178106_1_) {
		GlStateManager.pushMatrix();
		GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.translate(-0.3F, -1.1F, 0.45F);
		p_178106_1_.func_177139_c(Minecraft.thePlayer);
		GlStateManager.popMatrix();
	}

	private void func_178102_b(final AbstractClientPlayer p_178102_1_) {
		Minecraft.getTextureManager().bindTexture(p_178102_1_.getLocationSkin());
		final Render var2 = field_178111_g.getEntityRenderObject(Minecraft.thePlayer);
		final RenderPlayer var3 = (RenderPlayer) var2;

		if (!p_178102_1_.isInvisible()) {
			func_180534_a(var3);
			func_178106_b(var3);
		}
	}

	private void func_178097_a(final AbstractClientPlayer p_178097_1_, final float p_178097_2_, final float p_178097_3_,
			final float p_178097_4_) {
		final float var5 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float) Math.PI);
		final float var6 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float) Math.PI * 2.0F);
		final float var7 = -0.2F * MathHelper.sin(p_178097_4_ * (float) Math.PI);
		GlStateManager.translate(var5, var6, var7);
		final float var8 = func_178100_c(p_178097_2_);
		GlStateManager.translate(0.0F, 0.04F, -0.72F);
		GlStateManager.translate(0.0F, p_178097_3_ * -1.2F, 0.0F);
		GlStateManager.translate(0.0F, var8 * -0.5F, 0.0F);
		GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(var8 * -85.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
		func_178102_b(p_178097_1_);
		final float var9 = MathHelper.sin(p_178097_4_ * p_178097_4_ * (float) Math.PI);
		final float var10 = MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float) Math.PI);
		GlStateManager.rotate(var9 * -20.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(var10 * -20.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(var10 * -80.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(0.38F, 0.38F, 0.38F);
		GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(-1.0F, -1.0F, 0.0F);
		GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
		Minecraft.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
		final Tessellator var11 = Tessellator.getInstance();
		final WorldRenderer var12 = var11.getWorldRenderer();
		GL11.glNormal3f(0.0F, 0.0F, -1.0F);
		var12.startDrawingQuads();
		var12.addVertexWithUV(-7.0D, 135.0D, 0.0D, 0.0D, 1.0D);
		var12.addVertexWithUV(135.0D, 135.0D, 0.0D, 1.0D, 1.0D);
		var12.addVertexWithUV(135.0D, -7.0D, 0.0D, 1.0D, 0.0D);
		var12.addVertexWithUV(-7.0D, -7.0D, 0.0D, 0.0D, 0.0D);
		var11.draw();
		final MapData var13 = Items.filled_map.getMapData(itemToRender, Minecraft.theWorld);

		if (var13 != null) {
			Minecraft.entityRenderer.getMapItemRenderer().func_148250_a(var13, false);
		}
	}

	private void func_178095_a(final AbstractClientPlayer p_178095_1_, final float p_178095_2_,
			final float p_178095_3_) {
		final float var4 = -0.3F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float) Math.PI);
		final float var5 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float) Math.PI * 2.0F);
		final float var6 = -0.4F * MathHelper.sin(p_178095_3_ * (float) Math.PI);
		GlStateManager.translate(var4, var5, var6);
		GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
		GlStateManager.translate(0.0F, p_178095_2_ * -0.6F, 0.0F);
		GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
		final float var7 = MathHelper.sin(p_178095_3_ * p_178095_3_ * (float) Math.PI);
		final float var8 = MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float) Math.PI);
		GlStateManager.rotate(var8 * 70.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(var7 * -20.0F, 0.0F, 0.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(p_178095_1_.getLocationSkin());
		GlStateManager.translate(-1.0F, 3.6F, 3.5F);
		GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(1.0F, 1.0F, 1.0F);
		GlStateManager.translate(5.6F, 0.0F, 0.0F);
		final Render var9 = field_178111_g.getEntityRenderObject(Minecraft.thePlayer);
		final RenderPlayer var10 = (RenderPlayer) var9;
		var10.func_177138_b(Minecraft.thePlayer);
	}

	private void func_178105_d(final float p_178105_1_) {
		final float var2 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float) Math.PI);
		final float var3 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float) Math.PI * 2.0F);
		final float var4 = -0.2F * MathHelper.sin(p_178105_1_ * (float) Math.PI);
		GlStateManager.translate(var2, var3, var4);
	}

	private void func_178104_a(final AbstractClientPlayer p_178104_1_, final float p_178104_2_) {
		final float var3 = p_178104_1_.getItemInUseCount() - p_178104_2_ + 1.0F;
		final float var4 = var3 / itemToRender.getMaxItemUseDuration();
		float var5 = MathHelper.abs(MathHelper.cos(var3 / 4.0F * (float) Math.PI) * 0.1F);

		if (var4 >= 0.8F) {
			var5 = 0.0F;
		}

		GlStateManager.translate(0.0F, var5, 0.0F);
		final float var6 = 1.0F - (float) Math.pow(var4, 27.0D);
		GlStateManager.translate(var6 * 0.6F, var6 * -0.5F, var6 * 0.0F);
		GlStateManager.rotate(var6 * 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(var6 * 30.0F, 0.0F, 0.0F, 1.0F);
	}

	private void func_178096_b(final float p_178096_1_, final float p_178096_2_) {
		GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
		GlStateManager.translate(0.0F, p_178096_1_ * -0.6F, 0.0F);
		GlStateManager.rotate(45.0f, 0.0F, 1.0F, 0.0F);
		final float var3 = MathHelper.sin(p_178096_2_ * p_178096_2_ * (float) Math.PI);
		final float var4 = MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * (float) Math.PI);
		GlStateManager.rotate(var3 * -20.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(0.4F, 0.4F, 0.4F);
	}

	private void func_178098_a(final float p_178098_1_, final AbstractClientPlayer p_178098_2_) {
		GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(-0.9F, 0.2F, 0.0F);
		final float var3 = itemToRender.getMaxItemUseDuration()
				- (p_178098_2_.getItemInUseCount() - p_178098_1_ + 1.0F);
		float var4 = var3 / 20.0F;
		var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;

		if (var4 > 1.0F) {
			var4 = 1.0F;
		}

		if (var4 > 0.1F) {
			final float var5 = MathHelper.sin((var3 - 0.1F) * 1.3F);
			final float var6 = var4 - 0.1F;
			final float var7 = var5 * var6;
			GlStateManager.translate(var7 * 0.0F, var7 * 0.01F, var7 * 0.0F);
		}

		GlStateManager.translate(var4 * 0.0F, var4 * 0.0F, var4 * 0.1F);
		GlStateManager.scale(1.0F, 1.0F, 1.0F + var4 * 0.2F);
	}

	private void func_178103_d() {
		GlStateManager.translate(-0.5F, 0.2F, 0.0F);
		GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
	}

	/**
	 * Renders the active item in the player's hand when in first person mode.
	 * Args: partialTickTime
	 */
	public void renderItemInFirstPerson(final float p_78440_1_) {
		final float var2 = 1.0F - (prevEquippedProgress + (equippedProgress - prevEquippedProgress) * p_78440_1_);
		final EntityPlayerSP var3 = Minecraft.thePlayer;
		final float var4 = var3.getSwingProgress(p_78440_1_);
		final float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * p_78440_1_;
		final float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * p_78440_1_;
		func_178101_a(var5, var6);
		func_178109_a(var3);
		func_178110_a(var3, p_78440_1_);
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();

		if (itemToRender != null) {
			if (Configs.otherSwing) {
				if (itemToRender.getItem() == Items.filled_map) {
					func_178097_a(var3, var5, var2, var4);
				} else {
					Minecraft.getMinecraft();
					if (itemToRender.getItem() == Items.wooden_sword && !Minecraft.thePlayer.isBlocking()) {
						func_178096_b(var2, var4);
					} else {
						Minecraft.getMinecraft();
						if (itemToRender.getItem() != null && !Minecraft.thePlayer.isBlocking()
								&& !Minecraft.gameSettings.keyBindUseItem.pressed) {
							func_178096_b(var2, var4);
						}

						else if (var3.getItemInUseCount() > 0) {
							final EnumAction var7 = itemToRender.getItemUseAction();

							switch (ItemRenderer.SwitchEnumAction.field_178094_a[var7.ordinal()]) {
							case 1:
								func_178096_b(var2, 0.0F);
								break;

							case 2:
							case 3:
								func_178104_a(var3, p_78440_1_);
								func_178096_b(var2, 0.0F);
								break;

							case 4:
								func_178096_b(var2, var4);
								func_178103_d();
								break;

							case 5:
								func_178096_b(var2, 0.0F);
								func_178098_a(p_78440_1_, var3);
							}
						} else {
							func_178105_d(var4);
							func_178096_b(var2, var4);
						}
					}
				}

				renderItem(var3, itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
			} else {

				if (itemToRender.getItem() instanceof ItemMap) {
					func_178097_a(var3, var5, var2, var4);
				} else if (var3.getItemInUseCount() > 0) {
					final EnumAction var7 = itemToRender.getItemUseAction();

					switch (ItemRenderer.SwitchEnumAction.field_178094_a[var7.ordinal()]) {
					case 1:
						// None
						func_178096_b(var2, 0.0F);
						break;

					case 2:
					case 3:
						// Eat, Drink
						func_178104_a(var3, p_78440_1_);
						func_178096_b(var2, 0.0F);
						GlStateManager.translate(-0.5F, 0.0F, 0.0F);
						break;

					case 4:
						// EaZy Client BlockHit Animation
						// BlockHit
						func_178096_b(0.2F, var4);
						func_178103_d();
						GlStateManager.translate(-0.5F, 0.2F, 0.0F);
						break;

					case 5:
						// Bow
						func_178096_b(var2, 0.0F);
						func_178098_a(p_78440_1_, var3);
					}
				} else {
					// Swing Item
					func_178105_d(var4);
					func_178096_b(var2, var4);
				}

				renderItem(var3, itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);

			}

		} else if (!var3.isInvisible()) {
			func_178095_a(var3, var2, var4);
		}

		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
	}

	/**
	 * Renders all the overlays that are in first person mode. Args:
	 * partialTickTime
	 */
	public void renderOverlays(final float p_78447_1_) {
		GlStateManager.disableAlpha();

		if (Minecraft.thePlayer.isEntityInsideOpaqueBlock()) {
			BlockPos blockPos = new BlockPos(Minecraft.thePlayer);
			IBlockState var2 = Minecraft.theWorld.getBlockState(blockPos);
			final EntityPlayerSP var3 = Minecraft.thePlayer;

			for (int overlayType = 0; overlayType < 8; ++overlayType) {
				final double var5 = var3.posX + ((overlayType >> 0) % 2 - 0.5F) * var3.width * 0.8F;
				final double var7 = var3.posY + ((overlayType >> 1) % 2 - 0.5F) * 0.1F;
				final double var9 = var3.posZ + ((overlayType >> 2) % 2 - 0.5F) * var3.width * 0.8F;
				blockPos = new BlockPos(var5, var7 + var3.getEyeHeight(), var9);
				final IBlockState var12 = Minecraft.theWorld.getBlockState(blockPos);

				if (var12.getBlock().isVisuallyOpaque()) {
					var2 = var12;
				}
			}

			if (var2.getBlock().getRenderType() != -1) {
				final Object var13 = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);

				if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay,
						new Object[] { Minecraft.thePlayer, p_78447_1_, var13, var2, blockPos })) {
					func_178108_a(p_78447_1_, mc.getBlockRendererDispatcher().func_175023_a().func_178122_a(var2));
				}
			}
		}

		if (!Minecraft.thePlayer.isSpectatorMode()) {
			if (Minecraft.thePlayer.isInsideOfMaterial(Material.water)
					&& !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay,
							new Object[] { Minecraft.thePlayer, p_78447_1_})) {
				renderWaterOverlayTexture(p_78447_1_);
			}

			if (Minecraft.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay,
					new Object[] { Minecraft.thePlayer, p_78447_1_})) {
				renderFireInFirstPerson(p_78447_1_);
			}
		}

		GlStateManager.enableAlpha();
	}

	private void func_178108_a(final float p_178108_1_, final TextureAtlasSprite p_178108_2_) {
		Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		final Tessellator var3 = Tessellator.getInstance();
		final WorldRenderer var4 = var3.getWorldRenderer();
		final float var5 = 0.1F;
		GlStateManager.color(var5, var5, var5, 0.5F);
		GlStateManager.pushMatrix();
		final float var6 = -1.0F;
		final float var7 = 1.0F;
		final float var8 = -1.0F;
		final float var9 = 1.0F;
		final float var10 = -0.5F;
		final float var11 = p_178108_2_.getMinU();
		final float var12 = p_178108_2_.getMaxU();
		final float var13 = p_178108_2_.getMinV();
		final float var14 = p_178108_2_.getMaxV();
		var4.startDrawingQuads();
		var4.addVertexWithUV(var6, var8, var10, var12, var14);
		var4.addVertexWithUV(var7, var8, var10, var11, var14);
		var4.addVertexWithUV(var7, var9, var10, var11, var13);
		var4.addVertexWithUV(var6, var9, var10, var12, var13);
		var3.draw();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Renders a texture that warps around based on the direction the player is
	 * looking. Texture needs to be bound before being called. Used for the
	 * water overlay. Args: parialTickTime
	 */
	private void renderWaterOverlayTexture(final float p_78448_1_) {
		Minecraft.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
		final Tessellator var2 = Tessellator.getInstance();
		final WorldRenderer var3 = var2.getWorldRenderer();
		final float var4 = Minecraft.thePlayer.getBrightness(p_78448_1_);
		GlStateManager.color(var4, var4, var4, 0.5F);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.pushMatrix();
		final float var5 = 4.0F;
		final float var6 = -1.0F;
		final float var7 = 1.0F;
		final float var8 = -1.0F;
		final float var9 = 1.0F;
		final float var10 = -0.5F;
		final float var11 = -Minecraft.thePlayer.rotationYaw / 64.0F;
		final float var12 = Minecraft.thePlayer.rotationPitch / 64.0F;
		var3.startDrawingQuads();
		var3.addVertexWithUV(var6, var8, var10, var5 + var11, var5 + var12);
		var3.addVertexWithUV(var7, var8, var10, 0.0F + var11, var5 + var12);
		var3.addVertexWithUV(var7, var9, var10, 0.0F + var11, 0.0F + var12);
		var3.addVertexWithUV(var6, var9, var10, var5 + var11, 0.0F + var12);
		var2.draw();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
	}

	/**
	 * Renders the fire on the screen for first person mode. Arg:
	 * partialTickTime
	 */
	private void renderFireInFirstPerson(final float p_78442_1_) {
		final Tessellator var2 = Tessellator.getInstance();
		final WorldRenderer var3 = var2.getWorldRenderer();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
		GlStateManager.depthFunc(519);
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		final float var4 = 1.0F;

		for (int var5 = 0; var5 < 2; ++var5) {
			GlStateManager.pushMatrix();
			final TextureAtlasSprite var6 = mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
			Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			final float var7 = var6.getMinU();
			final float var8 = var6.getMaxU();
			final float var9 = var6.getMinV();
			final float var10 = var6.getMaxV();
			final float var11 = (0.0F - var4) / 2.0F;
			final float var12 = var11 + var4;
			final float var13 = 0.0F - var4 / 2.0F;
			final float var14 = var13 + var4;
			final float var15 = -0.5F;
			GlStateManager.translate(-(var5 * 2 - 1) * 0.24F, -0.3F, 0.0F);
			GlStateManager.rotate((var5 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
			var3.startDrawingQuads();
			var3.addVertexWithUV(var11, var13, var15, var8, var10);
			var3.addVertexWithUV(var12, var13, var15, var7, var10);
			var3.addVertexWithUV(var12, var14, var15, var7, var9);
			var3.addVertexWithUV(var11, var14, var15, var8, var9);
			var2.draw();
			GlStateManager.popMatrix();
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.depthFunc(515);
	}

	public void updateEquippedItem() {
		prevEquippedProgress = equippedProgress;
		final EntityPlayerSP var1 = Minecraft.thePlayer;
		final ItemStack var2 = var1.inventory.getCurrentItem();
		boolean var3 = false;

		if (itemToRender != null && var2 != null) {
			if (!itemToRender.getIsItemStackEqual(var2)) {
				if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists()) {
					final boolean var4 = Reflector.callBoolean(itemToRender.getItem(),
							Reflector.ForgeItem_shouldCauseReequipAnimation, new Object[] { itemToRender, var2, equippedItemSlot != var1.inventory.currentItem});

					if (!var4) {
						itemToRender = var2;
						equippedItemSlot = var1.inventory.currentItem;
						return;
					}
				}

				var3 = true;
			}
		} else var3 = !(itemToRender == null && var2 == null);

		final float var41 = 0.4F;
		final float var5 = var3 ? 0.0F : 1.0F;
		final float var6 = MathHelper.clamp_float(var5 - equippedProgress, -var41, var41);
		equippedProgress += var6;

		if (equippedProgress < 0.1F) {
			if (Config.isShaders()) {
				Shaders.itemToRender = var2;
			}

			itemToRender = var2;
			equippedItemSlot = var1.inventory.currentItem;
		}
	}

	/**
	 * Resets equippedProgress
	 */
	public void resetEquippedProgress() {
		equippedProgress = 0.0F;
	}

	/**
	 * Resets equippedProgress
	 */
	public void resetEquippedProgress2() {
		equippedProgress = 0.0F;
	}

	static final class SwitchEnumAction {
		/**
		 * 1 = None 2 = Eat 3 = Drink 4 = Block 5 = Bow
		 */
		static final int[] field_178094_a = new int[EnumAction.values().length];

		static {
			try {
				field_178094_a[EnumAction.NONE.ordinal()] = 1;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_178094_a[EnumAction.EAT.ordinal()] = 2;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_178094_a[EnumAction.DRINK.ordinal()] = 3;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_178094_a[EnumAction.BLOCK.ordinal()] = 4;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_178094_a[EnumAction.BOW.ordinal()] = 5;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
