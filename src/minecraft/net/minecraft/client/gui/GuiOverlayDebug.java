package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;

import me.EaZy.client.main.Client;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import optifine.Reflector;

public class GuiOverlayDebug extends Gui {

	public static final int EaZy = 496;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final Minecraft mc;
	private final FontRenderer fontRenderer;

	public GuiOverlayDebug(final Minecraft mc) {
		this.mc = mc;
		fontRenderer = mc.fontRendererObj;
	}

	public void func_175237_a(final ScaledResolution scaledResolutionIn) {
		Minecraft.mcProfiler.startSection("debug");
		GlStateManager.pushMatrix();
		func_180798_a();
		func_175239_b(scaledResolutionIn);
		GlStateManager.popMatrix();
		Minecraft.mcProfiler.endSection();
	}

	private boolean func_175236_d() {
		return Minecraft.thePlayer.func_175140_cp() || Minecraft.gameSettings.field_178879_v;
	}

	protected void func_180798_a() {
		final List var1 = call();

		for (int var2 = 0; var2 < var1.size(); ++var2) {
			final String var3 = (String) var1.get(var2);

			if (!Strings.isNullOrEmpty(var3)) {
				final int var4 = fontRenderer.FONT_HEIGHT;
				final int var5 = fontRenderer.getStringWidth(var3);
				final int var7 = 2 + var4 * var2;
				drawRect(1, var7 - 1, 2 + var5 + 1, var7 + var4 - 1, -1873784752);
				fontRenderer.drawString(var3, 2, var7, 14737632);
			}
		}
	}

	protected void func_175239_b(final ScaledResolution p_175239_1_) {
		final List var2 = func_175238_c();

		for (int var3 = 0; var3 < var2.size(); ++var3) {
			final String var4 = (String) var2.get(var3);

			if (!Strings.isNullOrEmpty(var4)) {
				final int var5 = fontRenderer.FONT_HEIGHT;
				final int var6 = fontRenderer.getStringWidth(var4);
				final int var7 = p_175239_1_.getScaledWidth() - 2 - var6;
				final int var8 = 2 + var5 * var3;
				drawRect(var7 - 1, var8 - 1, var7 + var6 + 1, var8 + var5 - 1, -1873784752);
				fontRenderer.drawString(var4, var7, var8, 14737632);
			}
		}
	}

	protected List call() {
		final BlockPos var1 = new BlockPos(mc.func_175606_aa().posX, mc.func_175606_aa().getEntityBoundingBox().minY,
				mc.func_175606_aa().posZ);

		if (func_175236_d()) {
			return Lists.newArrayList(new String[] {
					Client.isHidden ? "Minecraft 1.8 (1.8/vanilla)" : Client.version + " §6§l" + Client.EaZyVersion,
					mc.debug, mc.renderGlobal.getDebugInfoRenders(), mc.renderGlobal.getDebugInfoEntities(),
					"P: " + Minecraft.effectRenderer.getStatistics() + ". T: "
							+ Minecraft.theWorld.getDebugLoadedEntities(),
					Minecraft.theWorld.getProviderName(), "", String.format("Chunk-relative: %d %d %d",
							new Object[] { var1.getX() & 15, var1.getY() & 15, var1.getZ() & 15 }) });
		} else {
			final Entity var2 = mc.func_175606_aa();
			final EnumFacing var3 = var2.func_174811_aO();
			String var4 = "Invalid";

			switch (GuiOverlayDebug.SwitchEnumFacing.field_178907_a[var3.ordinal()]) {
			case 1:
				var4 = "Towards negative Z";
				break;

			case 2:
				var4 = "Towards positive Z";
				break;

			case 3:
				var4 = "Towards negative X";
				break;

			case 4:
				var4 = "Towards positive X";
			}

			final ArrayList var5 = Lists.newArrayList(new String[] {
					Client.isHidden ? "Minecraft 1.8 (1.8/vanilla)" : Client.version + " §6§l" + Client.EaZyVersion,
					mc.debug, mc.renderGlobal.getDebugInfoRenders(), mc.renderGlobal.getDebugInfoEntities(),
					"P: " + Minecraft.effectRenderer.getStatistics() + ". T: "
							+ Minecraft.theWorld.getDebugLoadedEntities(),
					Minecraft.theWorld.getProviderName(), "",
					String.format("XYZ: %.3f / %.5f / %.3f",
							new Object[] { mc.func_175606_aa().posX, mc.func_175606_aa().getEntityBoundingBox().minY,
									mc.func_175606_aa().posZ }),
					String.format("Block: %d %d %d", new Object[] { var1.getX(), var1.getY(), var1.getZ() }),
					String.format("Chunk: %d %d %d in %d %d %d",
							new Object[] { var1.getX() & 15, var1.getY() & 15, var1.getZ() & 15, var1.getX() >> 4,
									var1.getY() >> 4, var1.getZ() >> 4 }),
					String.format("Facing: %s (%s) (%.1f / %.1f)",
							new Object[] { var3, var4, MathHelper.wrapAngleTo180_float(var2.rotationYaw),
									MathHelper.wrapAngleTo180_float(var2.rotationPitch) }) });

			if (Minecraft.theWorld != null && Minecraft.theWorld.isBlockLoaded(var1)) {
				final Chunk var9 = Minecraft.theWorld.getChunkFromBlockCoords(var1);
				var5.add("Biome: " + var9.getBiome(var1, Minecraft.theWorld.getWorldChunkManager()).biomeName);
				var5.add("Light: " + var9.setLight(var1, 0) + " (" + var9.getLightFor(EnumSkyBlock.SKY, var1) + " sky, "
						+ var9.getLightFor(EnumSkyBlock.BLOCK, var1) + " block)");
				DifficultyInstance var7 = Minecraft.theWorld.getDifficultyForLocation(var1);

				if (mc.isIntegratedServerRunning() && mc.getIntegratedServer() != null) {
					final EntityPlayerMP var8 = mc.getIntegratedServer().getConfigurationManager()
							.func_177451_a(Minecraft.thePlayer.getUniqueID());

					if (var8 != null) {
						var7 = var8.worldObj.getDifficultyForLocation(new BlockPos(var8));
					}
				}

				var5.add(String.format("Local Difficulty: %.2f (Day %d)",
						new Object[] { var7.func_180168_b(), Minecraft.theWorld.getWorldTime() / 24000L }));
			}

			if (Minecraft.entityRenderer != null && Minecraft.entityRenderer.isShaderActive()) {
				var5.add("Shader: " + Minecraft.entityRenderer.getShaderGroup().getShaderGroupName());
			}

			if (mc.objectMouseOver != null
					&& mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
					&& mc.objectMouseOver.getBlockPos() != null) {
				final BlockPos var91 = mc.objectMouseOver.getBlockPos();
				var5.add(String.format("Looking at: %d %d %d",
						new Object[] { var91.getX(), var91.getY(), var91.getZ() }));
			}

			return var5;
		}
	}

	protected List func_175238_c() {
		final long var1 = Runtime.getRuntime().maxMemory();
		final long var3 = Runtime.getRuntime().totalMemory();
		final long var5 = Runtime.getRuntime().freeMemory();
		final long var7 = var3 - var5;
		final ArrayList var9 = Lists
				.newArrayList(
						new String[] {
								String.format("Java: %s %dbit",
										new Object[] { System.getProperty("java.version"),
												mc.isJava64bit() ? 64 : 32 }),
								String.format(
										"Mem: % 2d%% %03d/%03dMB", new Object[] { var7 * 100L / var1,
												func_175240_a(var7), func_175240_a(var1) }),
								String.format("Allocated: % 2d%% %03dMB",
										new Object[] { var3 * 100L / var1, func_175240_a(var3) }),
								"",
								String.format("Display: %dx%d (%s)",
										new Object[] { Display.getWidth(), Display.getHeight(),
												GL11.glGetString(GL11.GL_VENDOR) }),
								GL11.glGetString(GL11.GL_RENDERER), GL11.glGetString(GL11.GL_VERSION) });

		if (Reflector.FMLCommonHandler_getBrandings.exists()) {
			final Object var10 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
			var9.add("");
			var9.addAll((Collection) Reflector.call(var10, Reflector.FMLCommonHandler_getBrandings,
					new Object[] { false }));
		}

		if (func_175236_d()) {
			return var9;
		} else {
			if (mc.objectMouseOver != null
					&& mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
					&& mc.objectMouseOver.getBlockPos() != null) {
				final BlockPos var101 = mc.objectMouseOver.getBlockPos();
				IBlockState var11 = Minecraft.theWorld.getBlockState(var101);

				if (Minecraft.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
					var11 = var11.getBlock().getActualState(var11, Minecraft.theWorld, var101);
				}

				var9.add("");
				var9.add(String.valueOf(Block.blockRegistry.getNameForObject(var11.getBlock())));
				Entry var13;
				String var14;

				for (final UnmodifiableIterator var12 = var11.getProperties().entrySet().iterator(); var12
						.hasNext(); var9.add(((IProperty) var13.getKey()).getName() + ": " + var14)) {
					var13 = (Entry) var12.next();
					var14 = ((Comparable) var13.getValue()).toString();

					if (var13.getValue() == Boolean.TRUE) {
						var14 = EnumChatFormatting.GREEN + var14;
					} else if (var13.getValue() == Boolean.FALSE) {
						var14 = EnumChatFormatting.RED + var14;
					}
				}
				if (var11.getBlock() instanceof BlockSkull
						&& ((BlockSkull) var11.getBlock()).getDamageValue(mc.theWorld, var101) == 3) {
					try {
						var9.add("Owner: "
								+ ((TileEntitySkull) mc.theWorld.getTileEntity(var101)).getPlayerProfile().getName());
					} catch (Exception e) {
						// Skull hat keinen Owner
					}
				}
			}

			return var9;
		}
	}

	private static long func_175240_a(final long p_175240_0_) {
		return p_175240_0_ / 1024L / 1024L;
	}

	static final class SwitchEnumFacing {
		static final int[] field_178907_a = new int[EnumFacing.values().length];
		static {
			try {
				field_178907_a[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {}

			try {
				field_178907_a[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {}

			try {
				field_178907_a[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {}

			try {
				field_178907_a[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {}
		}
	}
}
