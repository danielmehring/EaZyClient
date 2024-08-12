package net.minecraft.client.particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import optifine.Config;
import optifine.Reflector;

public class EffectRenderer {

	public static final int EaZy = 634;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");

	/** Reference to the World object. */
	protected World worldObj;
	private final List[][] fxLayers = new List[4][];
	private final List field_178933_d = Lists.newArrayList();
	private final TextureManager renderer;

	/** RNG. */
	private final Random rand = new Random();
	private final Map field_178932_g = Maps.newHashMap();

	public EffectRenderer(final World worldIn, final TextureManager p_i1220_2_) {
		worldObj = worldIn;
		renderer = p_i1220_2_;

		for (int var3 = 0; var3 < 4; ++var3) {
			fxLayers[var3] = new List[2];

			for (int var4 = 0; var4 < 2; ++var4) {
				fxLayers[var3][var4] = Lists.newArrayList();
			}
		}

		func_178930_c();
	}

	private void func_178930_c() {
		func_178929_a(EnumParticleTypes.EXPLOSION_NORMAL.func_179348_c(), new EntityExplodeFX.Factory());
		func_178929_a(EnumParticleTypes.WATER_BUBBLE.func_179348_c(), new EntityBubbleFX.Factory());
		func_178929_a(EnumParticleTypes.WATER_SPLASH.func_179348_c(), new EntitySplashFX.Factory());
		func_178929_a(EnumParticleTypes.WATER_WAKE.func_179348_c(), new EntityFishWakeFX.Factory());
		func_178929_a(EnumParticleTypes.WATER_DROP.func_179348_c(), new EntityRainFX.Factory());
		func_178929_a(EnumParticleTypes.SUSPENDED.func_179348_c(), new EntitySuspendFX.Factory());
		func_178929_a(EnumParticleTypes.SUSPENDED_DEPTH.func_179348_c(), new EntityAuraFX.Factory());
		func_178929_a(EnumParticleTypes.CRIT.func_179348_c(), new EntityCrit2FX.Factory());
		func_178929_a(EnumParticleTypes.CRIT_MAGIC.func_179348_c(), new EntityCrit2FX.MagicFactory());
		func_178929_a(EnumParticleTypes.SMOKE_NORMAL.func_179348_c(), new EntitySmokeFX.Factory());
		func_178929_a(EnumParticleTypes.SMOKE_LARGE.func_179348_c(), new EntityCritFX.Factory());
		func_178929_a(EnumParticleTypes.SPELL.func_179348_c(), new EntitySpellParticleFX.Factory());
		func_178929_a(EnumParticleTypes.SPELL_INSTANT.func_179348_c(), new EntitySpellParticleFX.InstantFactory());
		func_178929_a(EnumParticleTypes.SPELL_MOB.func_179348_c(), new EntitySpellParticleFX.MobFactory());
		func_178929_a(EnumParticleTypes.SPELL_MOB_AMBIENT.func_179348_c(),
				new EntitySpellParticleFX.AmbientMobFactory());
		func_178929_a(EnumParticleTypes.SPELL_WITCH.func_179348_c(), new EntitySpellParticleFX.WitchFactory());
		func_178929_a(EnumParticleTypes.DRIP_WATER.func_179348_c(), new EntityDropParticleFX.WaterFactory());
		func_178929_a(EnumParticleTypes.DRIP_LAVA.func_179348_c(), new EntityDropParticleFX.LavaFactory());
		func_178929_a(EnumParticleTypes.VILLAGER_ANGRY.func_179348_c(), new EntityHeartFX.AngryVillagerFactory());
		func_178929_a(EnumParticleTypes.VILLAGER_HAPPY.func_179348_c(), new EntityAuraFX.HappyVillagerFactory());
		func_178929_a(EnumParticleTypes.TOWN_AURA.func_179348_c(), new EntityAuraFX.Factory());
		func_178929_a(EnumParticleTypes.NOTE.func_179348_c(), new EntityNoteFX.Factory());
		func_178929_a(EnumParticleTypes.PORTAL.func_179348_c(), new EntityPortalFX.Factory());
		func_178929_a(EnumParticleTypes.ENCHANTMENT_TABLE.func_179348_c(),
				new EntityEnchantmentTableParticleFX.EnchantmentTable());
		func_178929_a(EnumParticleTypes.FLAME.func_179348_c(), new EntityFlameFX.Factory());
		func_178929_a(EnumParticleTypes.LAVA.func_179348_c(), new EntityLavaFX.Factory());
		func_178929_a(EnumParticleTypes.FOOTSTEP.func_179348_c(), new EntityFootStepFX.Factory());
		func_178929_a(EnumParticleTypes.CLOUD.func_179348_c(), new EntityCloudFX.Factory());
		func_178929_a(EnumParticleTypes.REDSTONE.func_179348_c(), new EntityReddustFX.Factory());
		func_178929_a(EnumParticleTypes.SNOWBALL.func_179348_c(), new EntityBreakingFX.SnowballFactory());
		func_178929_a(EnumParticleTypes.SNOW_SHOVEL.func_179348_c(), new EntitySnowShovelFX.Factory());
		func_178929_a(EnumParticleTypes.SLIME.func_179348_c(), new EntityBreakingFX.SlimeFactory());
		func_178929_a(EnumParticleTypes.HEART.func_179348_c(), new EntityHeartFX.Factory());
		func_178929_a(EnumParticleTypes.BARRIER.func_179348_c(), new Barrier.Factory());
		func_178929_a(EnumParticleTypes.ITEM_CRACK.func_179348_c(), new EntityBreakingFX.Factory());
		func_178929_a(EnumParticleTypes.BLOCK_CRACK.func_179348_c(), new EntityDiggingFX.Factory());
		func_178929_a(EnumParticleTypes.BLOCK_DUST.func_179348_c(), new EntityBlockDustFX.Factory());
		func_178929_a(EnumParticleTypes.EXPLOSION_HUGE.func_179348_c(), new EntityHugeExplodeFX.Factory());
		func_178929_a(EnumParticleTypes.EXPLOSION_LARGE.func_179348_c(), new EntityLargeExplodeFX.Factory());
		func_178929_a(EnumParticleTypes.FIREWORKS_SPARK.func_179348_c(), new EntityFireworkStarterFX_Factory());
		func_178929_a(EnumParticleTypes.MOB_APPEARANCE.func_179348_c(), new MobAppearance.Factory());
	}

	public void func_178929_a(final int p_178929_1_, final IParticleFactory p_178929_2_) {
		field_178932_g.put(p_178929_1_, p_178929_2_);
	}

	public void spawnParticle(final Entity p_178926_1_, final EnumParticleTypes p_178926_2_) {
		field_178933_d.add(new EntityParticleEmitter(worldObj, p_178926_1_, p_178926_2_));
	}

	public EntityFX func_178927_a(final int p_178927_1_, final double p_178927_2_, final double p_178927_4_,
			final double p_178927_6_, final double p_178927_8_, final double p_178927_10_, final double p_178927_12_,
			final int... p_178927_14_) {
		final IParticleFactory var15 = (IParticleFactory) field_178932_g.get(p_178927_1_);

		if (var15 != null) {
			final EntityFX var16 = var15.func_178902_a(p_178927_1_, worldObj, p_178927_2_, p_178927_4_, p_178927_6_,
					p_178927_8_, p_178927_10_, p_178927_12_, p_178927_14_);

			if (var16 != null) {
				addEffect(var16);
				return var16;
			}
		}

		return null;
	}

	public void addEffect(final EntityFX p_78873_1_) {
		if (p_78873_1_ != null) {
			if (!(p_78873_1_ instanceof EntityFireworkSparkFX) || Config.isFireworkParticles()) {
				final int var2 = p_78873_1_.getFXLayer();
				final int var3 = p_78873_1_.func_174838_j() != 1.0F ? 0 : 1;

				if (fxLayers[var2][var3].size() >= 4000) {
					fxLayers[var2][var3].remove(0);
				}

				if (!(p_78873_1_ instanceof Barrier) || !reuseBarrierParticle(p_78873_1_, fxLayers[var2][var3])) {
					fxLayers[var2][var3].add(p_78873_1_);
				}
			}
		}
	}

	public void updateEffects() {
		if (Minecraft.antiLag && Minecraft.theWorld != null) {
			return;
		}
		for (int var4 = 0; var4 < 4; ++var4) {
			func_178922_a(var4);
		}
		

		final ArrayList var41 = Lists.newArrayList();
		final Iterator var2 = field_178933_d.iterator();

		while (var2.hasNext()) {
			final EntityParticleEmitter var3 = (EntityParticleEmitter) var2.next();
			var3.onUpdate();

			if (var3.isDead) {
				var41.add(var3);
			}
		}

		field_178933_d.removeAll(var41);
	}

	private void func_178922_a(final int p_178922_1_) {
		for (int var2 = 0; var2 < 2; ++var2) {
			func_178925_a(fxLayers[p_178922_1_][var2]);
		}
	}

	private void func_178925_a(final List p_178925_1_) {
		final ArrayList var2 = Lists.newArrayList();

		for (int var3 = 0; var3 < p_178925_1_.size(); ++var3) {
			final EntityFX var4 = (EntityFX) p_178925_1_.get(var3);
			func_178923_d(var4);

			if (var4.isDead) {
				var2.add(var4);
			}
		}

		p_178925_1_.removeAll(var2);
	}

	private void func_178923_d(final EntityFX p_178923_1_) {
		try {
			p_178923_1_.onUpdate();
		} catch (final Throwable var6) {
			final CrashReport var3 = CrashReport.makeCrashReport(var6, "Ticking Particle");
			final CrashReportCategory var4 = var3.makeCategory("Particle being ticked");
			final int var5 = p_178923_1_.getFXLayer();
			var4.addCrashSectionCallable("Particle", new Callable() {
				@Override
				public String call() {
					return p_178923_1_.toString();
				}
			});
			var4.addCrashSectionCallable("Particle Type", new Callable() {
				@Override
				public String call() {
					return var5 == 0 ? "MISC_TEXTURE"
							: var5 == 1 ? "TERRAIN_TEXTURE"
									: var5 == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + var5;
				}
			});
			throw new ReportedException(var3);
		}
	}

	/**
	 * Renders all current particles. Args player, partialTickTime
	 */
	public void renderParticles(final Entity p_78874_1_, final float partialTicks) {
		if (Minecraft.antiLag && Minecraft.theWorld != null) {
			clearEffects(worldObj);
			return;
		}
		final float var3 = ActiveRenderInfo.func_178808_b();
		final float var4 = ActiveRenderInfo.func_178803_d();
		final float var5 = ActiveRenderInfo.func_178805_e();
		final float var6 = ActiveRenderInfo.func_178807_f();
		final float var7 = ActiveRenderInfo.func_178809_c();
		EntityFX.interpPosX = p_78874_1_.lastTickPosX + (p_78874_1_.posX - p_78874_1_.lastTickPosX) * partialTicks;
		EntityFX.interpPosY = p_78874_1_.lastTickPosY + (p_78874_1_.posY - p_78874_1_.lastTickPosY) * partialTicks;
		EntityFX.interpPosZ = p_78874_1_.lastTickPosZ + (p_78874_1_.posZ - p_78874_1_.lastTickPosZ) * partialTicks;
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);

		for (int var8_nf = 0; var8_nf < 3; ++var8_nf) {
			final int var8 = var8_nf;

			for (int var9 = 0; var9 < 2; ++var9) {
				if (!fxLayers[var8][var9].isEmpty()) {
					switch (var9) {
					case 0:
						GlStateManager.depthMask(false);
						break;

					case 1:
						GlStateManager.depthMask(true);
					}

					switch (var8) {
					case 0:
					default:
						renderer.bindTexture(particleTextures);
						break;

					case 1:
						renderer.bindTexture(TextureMap.locationBlocksTexture);
					}

					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					final Tessellator var10 = Tessellator.getInstance();
					final WorldRenderer var11 = var10.getWorldRenderer();
					var11.startDrawingQuads();

					for (int var12 = 0; var12 < fxLayers[var8][var9].size(); ++var12) {
						final EntityFX var13 = (EntityFX) fxLayers[var8][var9].get(var12);
						var11.func_178963_b(var13.getBrightnessForRender(partialTicks));

						try {
							var13.func_180434_a(var11, p_78874_1_, partialTicks, var3, var7, var4, var5, var6);
						} catch (final Throwable var18) {
							final CrashReport var15 = CrashReport.makeCrashReport(var18, "Rendering Particle");
							final CrashReportCategory var16 = var15.makeCategory("Particle being rendered");
							var16.addCrashSectionCallable("Particle", new Callable() {
								@Override
								public String call() {
									return var13.toString();
								}
							});
							var16.addCrashSectionCallable("Particle Type", new Callable() {
								@Override
								public String call() {
									return var8 == 0 ? "MISC_TEXTURE"
											: var8 == 1 ? "TERRAIN_TEXTURE"
													: var8 == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + var8;
								}
							});
							throw new ReportedException(var15);
						}
					}

					var10.draw();
				}
			}
		}

		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
	}

	public void renderLitParticles(final Entity p_78872_1_, final float p_78872_2_) {
		if (Minecraft.antiLag && Minecraft.theWorld != null) {
			clearEffects(worldObj);
			return;
		}
		final float var4 = MathHelper.cos(p_78872_1_.rotationYaw * 0.017453292F);
		final float var5 = MathHelper.sin(p_78872_1_.rotationYaw * 0.017453292F);
		final float var6 = -var5 * MathHelper.sin(p_78872_1_.rotationPitch * 0.017453292F);
		final float var7 = var4 * MathHelper.sin(p_78872_1_.rotationPitch * 0.017453292F);
		final float var8 = MathHelper.cos(p_78872_1_.rotationPitch * 0.017453292F);

		for (int var9 = 0; var9 < 2; ++var9) {
			final List var10 = fxLayers[3][var9];

			if (!var10.isEmpty()) {
				final Tessellator var11 = Tessellator.getInstance();
				final WorldRenderer var12 = var11.getWorldRenderer();

				for (int var13 = 0; var13 < var10.size(); ++var13) {
					final EntityFX var14 = (EntityFX) var10.get(var13);
					var12.func_178963_b(var14.getBrightnessForRender(p_78872_2_));
					var14.func_180434_a(var12, p_78872_1_, p_78872_2_, var4, var8, var5, var6, var7);
				}
			}
		}
	}

	public void clearEffects(final World worldIn) {
		worldObj = worldIn;

		for (int var2 = 0; var2 < 4; ++var2) {
			for (int var3 = 0; var3 < 2; ++var3) {
				fxLayers[var2][var3].clear();
			}
		}

		field_178933_d.clear();
	}

	public void func_180533_a(final BlockPos p_180533_1_, IBlockState p_180533_2_) {
		boolean notAir;

		if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
			final Block var3 = p_180533_2_.getBlock();
			Reflector.callBoolean(var3, Reflector.ForgeBlock_isAir, new Object[] { worldObj, p_180533_1_ });
			notAir = !Reflector.callBoolean(var3, Reflector.ForgeBlock_isAir, new Object[] { worldObj, p_180533_1_ })
					&& !Reflector.callBoolean(var3, Reflector.ForgeBlock_addDestroyEffects,
							new Object[] { worldObj, p_180533_1_, this });
		} else {
			notAir = p_180533_2_.getBlock().getMaterial() != Material.air;
		}

		if (notAir) {
			p_180533_2_ = p_180533_2_.getBlock().getActualState(p_180533_2_, worldObj, p_180533_1_);
			final byte var14 = 4;

			for (int var4 = 0; var4 < var14; ++var4) {
				for (int var5 = 0; var5 < var14; ++var5) {
					for (int var6 = 0; var6 < var14; ++var6) {
						final double var7 = p_180533_1_.getX() + (var4 + 0.5D) / var14;
						final double var9 = p_180533_1_.getY() + (var5 + 0.5D) / var14;
						final double var11 = p_180533_1_.getZ() + (var6 + 0.5D) / var14;
						addEffect(new EntityDiggingFX(worldObj, var7, var9, var11, var7 - p_180533_1_.getX() - 0.5D,
								var9 - p_180533_1_.getY() - 0.5D, var11 - p_180533_1_.getZ() - 0.5D, p_180533_2_)
										.func_174846_a(p_180533_1_));
					}
				}
			}
		}
	}

	public void func_180532_a(final BlockPos p_180532_1_, final EnumFacing p_180532_2_) {
		final IBlockState var3 = worldObj.getBlockState(p_180532_1_);
		final Block var4 = var3.getBlock();

		if (var4.getRenderType() != -1) {
			final int var5 = p_180532_1_.getX();
			final int var6 = p_180532_1_.getY();
			final int var7 = p_180532_1_.getZ();
			final float var8 = 0.1F;
			double var9 = var5
					+ rand.nextDouble() * (var4.getBlockBoundsMaxX() - var4.getBlockBoundsMinX() - var8 * 2.0F) + var8
					+ var4.getBlockBoundsMinX();
			double var11 = var6
					+ rand.nextDouble() * (var4.getBlockBoundsMaxY() - var4.getBlockBoundsMinY() - var8 * 2.0F) + var8
					+ var4.getBlockBoundsMinY();
			double var13 = var7
					+ rand.nextDouble() * (var4.getBlockBoundsMaxZ() - var4.getBlockBoundsMinZ() - var8 * 2.0F) + var8
					+ var4.getBlockBoundsMinZ();

			if (p_180532_2_ == EnumFacing.DOWN) {
				var11 = var6 + var4.getBlockBoundsMinY() - var8;
			}

			if (p_180532_2_ == EnumFacing.UP) {
				var11 = var6 + var4.getBlockBoundsMaxY() + var8;
			}

			if (p_180532_2_ == EnumFacing.NORTH) {
				var13 = var7 + var4.getBlockBoundsMinZ() - var8;
			}

			if (p_180532_2_ == EnumFacing.SOUTH) {
				var13 = var7 + var4.getBlockBoundsMaxZ() + var8;
			}

			if (p_180532_2_ == EnumFacing.WEST) {
				var9 = var5 + var4.getBlockBoundsMinX() - var8;
			}

			if (p_180532_2_ == EnumFacing.EAST) {
				var9 = var5 + var4.getBlockBoundsMaxX() + var8;
			}

			addEffect(new EntityDiggingFX(worldObj, var9, var11, var13, 0.0D, 0.0D, 0.0D, var3)
					.func_174846_a(p_180532_1_).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
		}
	}

	public void func_178928_b(final EntityFX p_178928_1_) {
		func_178924_a(p_178928_1_, 1, 0);
	}

	public void func_178931_c(final EntityFX p_178931_1_) {
		func_178924_a(p_178931_1_, 0, 1);
	}

	private void func_178924_a(final EntityFX p_178924_1_, final int p_178924_2_, final int p_178924_3_) {
		for (int var4 = 0; var4 < 4; ++var4) {
			if (fxLayers[var4][p_178924_2_].contains(p_178924_1_)) {
				fxLayers[var4][p_178924_2_].remove(p_178924_1_);
				fxLayers[var4][p_178924_3_].add(p_178924_1_);
			}
		}
	}

	public String getStatistics() {
		int var1 = 0;

		for (int var2 = 0; var2 < 4; ++var2) {
			for (int var3 = 0; var3 < 2; ++var3) {
				var1 += fxLayers[var2][var3].size();
			}
		}

		return "" + var1;
	}

	private boolean reuseBarrierParticle(final EntityFX entityfx, final List<EntityFX> list) {
		final Iterator it = list.iterator();
		EntityFX efx;

		do {
			if (!it.hasNext()) {
				return false;
			}

			efx = (EntityFX) it.next();
		}
		while (!(efx instanceof Barrier) || entityfx.posX != efx.posX || entityfx.posY != efx.posY
				|| entityfx.posZ != efx.posZ);

		efx.particleAge = 0;
		return true;
	}

	public void addBlockHitEffects(final BlockPos pos, final MovingObjectPosition target) {
		final Block block = worldObj.getBlockState(pos).getBlock();
		final boolean blockAddHitEffects = Reflector.callBoolean(block, Reflector.ForgeBlock_addHitEffects,
				new Object[] { worldObj, target, this });

		if (block != null && !blockAddHitEffects) {
			func_180532_a(pos, target.facing);
		}
	}
}
