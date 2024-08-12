package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;

public class TileEntityRendererDispatcher {

public static final int EaZy = 841;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Map mapSpecialRenderers = Maps.newHashMap();
	public static TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
	private FontRenderer field_147557_n;

	/** The player's current X position (same as playerX) */
	public static double staticPlayerX;

	/** The player's current Y position (same as playerY) */
	public static double staticPlayerY;

	/** The player's current Z position (same as playerZ) */
	public static double staticPlayerZ;
	public TextureManager renderEngine;
	public World worldObj;
	public Entity field_147551_g;
	public float field_147562_h;
	public float field_147563_i;
	public double field_147560_j;
	public double field_147561_k;
	public double field_147558_l;
	// private static final String __OBFID = "http://https://fuckuskid00000963";

	private TileEntityRendererDispatcher() {
		mapSpecialRenderers.put(TileEntitySign.class, new TileEntitySignRenderer());
		mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
		mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
		mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
		mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
		mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
		mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
		mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
		mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
		mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
		final Iterator var1 = mapSpecialRenderers.values().iterator();

		while (var1.hasNext()) {
			final TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer) var1.next();
			var2.setRendererDispatcher(this);
		}
	}

	public TileEntitySpecialRenderer getSpecialRendererByClass(final Class p_147546_1_) {
		TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer) mapSpecialRenderers.get(p_147546_1_);

		if (var2 == null && p_147546_1_ != TileEntity.class) {
			var2 = getSpecialRendererByClass(p_147546_1_.getSuperclass());
			mapSpecialRenderers.put(p_147546_1_, var2);
		}

		return var2;
	}

	/**
	 * Returns true if this TileEntity instance has a TileEntitySpecialRenderer
	 * associated with it, false otherwise.
	 */
	public boolean hasSpecialRenderer(final TileEntity p_147545_1_) {
		return getSpecialRenderer(p_147545_1_) != null;
	}

	public TileEntitySpecialRenderer getSpecialRenderer(final TileEntity p_147547_1_) {
		return p_147547_1_ == null ? null : getSpecialRendererByClass(p_147547_1_.getClass());
	}

	public void func_178470_a(final World worldIn, final TextureManager p_178470_2_, final FontRenderer p_178470_3_,
			final Entity p_178470_4_, final float p_178470_5_) {
		if (worldObj != worldIn) {
			func_147543_a(worldIn);
		}

		renderEngine = p_178470_2_;
		field_147551_g = p_178470_4_;
		field_147557_n = p_178470_3_;
		field_147562_h = p_178470_4_.prevRotationYaw
				+ (p_178470_4_.rotationYaw - p_178470_4_.prevRotationYaw) * p_178470_5_;
		field_147563_i = p_178470_4_.prevRotationPitch
				+ (p_178470_4_.rotationPitch - p_178470_4_.prevRotationPitch) * p_178470_5_;
		field_147560_j = p_178470_4_.lastTickPosX + (p_178470_4_.posX - p_178470_4_.lastTickPosX) * p_178470_5_;
		field_147561_k = p_178470_4_.lastTickPosY + (p_178470_4_.posY - p_178470_4_.lastTickPosY) * p_178470_5_;
		field_147558_l = p_178470_4_.lastTickPosZ + (p_178470_4_.posZ - p_178470_4_.lastTickPosZ) * p_178470_5_;
	}

	public void func_180546_a(final TileEntity p_180546_1_, final float p_180546_2_, final int p_180546_3_) {
		if (p_180546_1_.getDistanceSq(field_147560_j, field_147561_k, field_147558_l) < p_180546_1_
				.getMaxRenderDistanceSquared()) {
			final int var4 = worldObj.getCombinedLight(p_180546_1_.getPos(), 0);
			final int var5 = var4 % 65536;
			final int var6 = var4 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0F, var6 / 1.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			final BlockPos var7 = p_180546_1_.getPos();
			func_178469_a(p_180546_1_, var7.getX() - staticPlayerX, var7.getY() - staticPlayerY,
					var7.getZ() - staticPlayerZ, p_180546_2_, p_180546_3_);
		}
	}

	/**
	 * Render this TileEntity at a given set of coordinates
	 */
	public void renderTileEntityAt(final TileEntity p_147549_1_, final double p_147549_2_, final double p_147549_4_,
			final double p_147549_6_, final float p_147549_8_) {
		func_178469_a(p_147549_1_, p_147549_2_, p_147549_4_, p_147549_6_, p_147549_8_, -1);
	}

	public void func_178469_a(final TileEntity p_178469_1_, final double p_178469_2_, final double p_178469_4_,
			final double p_178469_6_, final float p_178469_8_, final int p_178469_9_) {
		final TileEntitySpecialRenderer var10 = getSpecialRenderer(p_178469_1_);

		if (var10 != null) {
			try {
				var10.renderTileEntityAt(p_178469_1_, p_178469_2_, p_178469_4_, p_178469_6_, p_178469_8_, p_178469_9_);
			} catch (final Throwable var14) {
				final CrashReport var12 = CrashReport.makeCrashReport(var14, "Rendering Block Entity");
				final CrashReportCategory var13 = var12.makeCategory("Block Entity Details");
				p_178469_1_.addInfoToCrashReport(var13);
				throw new ReportedException(var12);
			}
		}
	}

	public void func_147543_a(final World worldIn) {
		worldObj = worldIn;
	}

	public FontRenderer getFontRenderer() {
		return field_147557_n;
	}
}
