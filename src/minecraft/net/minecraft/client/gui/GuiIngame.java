package net.minecraft.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import de.Hero.clickgui.ClickGUI;
import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.NoScoreboard;
import me.EaZy.client.modules.hud.Appearance;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import optifine.Config;
import optifine.CustomColors;

public class GuiIngame extends Gui {

	public static final int EaZy = 479;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
	private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
	private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
	private final Random rand = new Random();
	private final Minecraft mc;
	private final RenderItem itemRenderer;

	/** ChatGUI instance that retains all previous chat data */
	private final GuiNewChat persistantChatGUI;
	private final GuiStreamIndicator streamIndicator;
	private int updateCounter;

	/** The string specifying which record music is playing */
	private String recordPlaying = "";

	// TODO

	int xxx;

	int xxx2;

	int yyy;

	int yyy2;

	/** How many ticks the record playing message will be displayed */
	private int recordPlayingUpFor;
	private boolean recordIsPlaying;

	/** Previous frame vignette brightness (slowly changes by 1% each frame) */
	public float prevVignetteBrightness = 1.0F;

	/** Remaining ticks the item highlight should be visible */
	private int remainingHighlightTicks;

	/** The ItemStack that is currently being highlighted */
	private ItemStack highlightingItemStack;
	private final GuiOverlayDebug overlayDebug;
	private final GuiSpectator field_175197_u;
	private final GuiPlayerTabOverlay overlayPlayerList;
	private int field_175195_w;
	private String field_175201_x = "";
	private String field_175200_y = "";
	private int field_175199_z;
	private int field_175192_A;
	private int field_175193_B;
	private int field_175194_C = 0;
	private int field_175189_D = 0;
	private long field_175190_E = 0L;
	private long field_175191_F = 0L;

	public GuiIngame(final Minecraft mcIn) {
		mc = mcIn;
		itemRenderer = mcIn.getRenderItem();
		overlayDebug = new GuiOverlayDebug(mcIn);
		field_175197_u = new GuiSpectator(mcIn);
		persistantChatGUI = new GuiNewChat(mcIn);
		streamIndicator = new GuiStreamIndicator(mcIn);
		overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
		func_175177_a();
	}

	public void func_175177_a() {
		field_175199_z = 10;
		field_175192_A = 70;
		field_175193_B = 20;
	}

	public void func_175180_a(final float p_175180_1_) {
		final ScaledResolution var2 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
		final int var3 = var2.getScaledWidth();
		final int var4 = var2.getScaledHeight();
		Minecraft.entityRenderer.setupOverlayRendering();
		GlStateManager.enableBlend();

		if (Config.isVignetteEnabled()) {
			func_180480_a(Minecraft.thePlayer.getBrightness(p_175180_1_), var2);
		} else {
			GlStateManager.enableDepth();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		}

		final ItemStack var5 = Minecraft.thePlayer.inventory.armorItemInSlot(3);

		if (Minecraft.gameSettings.thirdPersonView == 0 && var5 != null
				&& var5.getItem() == Item.getItemFromBlock(Blocks.pumpkin)
				&& !(Minecraft.currentScreen instanceof ClickGUI)) {
			func_180476_e(var2);
		}

		float var7;

		if (!Minecraft.thePlayer.isPotionActive(Potion.confusion)) {
			var7 = Minecraft.thePlayer.prevTimeInPortal
					+ (Minecraft.thePlayer.timeInPortal - Minecraft.thePlayer.prevTimeInPortal) * p_175180_1_;

			if (var7 > 0.0F) {
				func_180474_b(var7, var2);
			}
		}

		if (Minecraft.playerController.enableEverythingIsScrewedUpMode()) {
			field_175197_u.func_175264_a(var2, p_175180_1_);
		} else {
			func_180479_a(var2, p_175180_1_);
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(icons);
		GlStateManager.enableBlend();

		if (func_175183_b() && Minecraft.gameSettings.thirdPersonView < 1
				&& !(Minecraft.currentScreen instanceof ClickGUI)) {
			GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
			GlStateManager.enableAlpha();
			drawTexturedModalRect(var3 / 2 - 7, var4 / 2 - 7, 0, 0, 16, 16);
		}

		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		Minecraft.mcProfiler.startSection("bossHealth");
		renderBossHealth();
		Minecraft.mcProfiler.endSection();

		if (Minecraft.playerController.shouldDrawHUD()) {
			func_180477_d(var2);
		}

		GlStateManager.disableBlend();
		int var8;
		int var11;

		if (Minecraft.thePlayer.getSleepTimer() > 0) {
			Minecraft.mcProfiler.startSection("sleep");
			GlStateManager.disableDepth();
			GlStateManager.disableAlpha();
			var11 = Minecraft.thePlayer.getSleepTimer();
			var7 = var11 / 100.0F;

			if (var7 > 1.0F) {
				var7 = 1.0F - (var11 - 100) / 10.0F;
			}

			var8 = (int) (220.0F * var7) << 24 | 1052704;
			drawRect(0, 0, var3, var4, var8);
			GlStateManager.enableAlpha();
			GlStateManager.enableDepth();
			Minecraft.mcProfiler.endSection();
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		var11 = var3 / 2 - 91;

		if (Minecraft.thePlayer.isRidingHorse()) {
			func_175186_a(var2, var11);
		} else if (Minecraft.playerController.gameIsSurvivalOrAdventure()) {
			func_175176_b(var2, var11);
		}

		if (Minecraft.gameSettings.heldItemTooltips && !Minecraft.playerController.enableEverythingIsScrewedUpMode()) {
			func_175182_a(var2);
		} else if (Minecraft.thePlayer.isSpectatorMode()) {
			field_175197_u.func_175263_a(var2);
		}

		if (mc.isDemo()) {
			func_175185_b(var2);
		}

		if (Minecraft.gameSettings.showDebugInfo) {
			overlayDebug.func_175237_a(var2);
		}

		int var9;

		if (recordPlayingUpFor > 0) {
			Minecraft.mcProfiler.startSection("overlayMessage");
			var7 = recordPlayingUpFor - p_175180_1_;
			var8 = (int) (var7 * 255.0F / 20.0F);

			if (var8 > 255) {
				var8 = 255;
			}

			if (var8 > 8) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(var3 / 2, var4 - 68, 0.0F);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				var9 = 16777215;

				if (recordIsPlaying) {
					var9 = Color.HSBtoRGB(var7 / 50.0F, 0.7F, 0.6F) & 16777215;
				}

				func_175179_f().drawString(recordPlaying, -func_175179_f().getStringWidth(recordPlaying) / 2, -4,
						var9 + (var8 << 24 & -16777216));
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}

			Minecraft.mcProfiler.endSection();
		}

		if (field_175195_w > 0) {
			Minecraft.mcProfiler.startSection("titleAndSubtitle");
			var7 = field_175195_w - p_175180_1_;
			var8 = 255;

			if (field_175195_w > field_175193_B + field_175192_A) {
				final float var12 = field_175199_z + field_175192_A + field_175193_B - var7;
				var8 = (int) (var12 * 255.0F / field_175199_z);
			}

			if (field_175195_w <= field_175193_B) {
				var8 = (int) (var7 * 255.0F / field_175193_B);
			}

			var8 = MathHelper.clamp_int(var8, 0, 255);

			if (var8 > 8) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(var3 / 2, var4 / 2, 0.0F);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.pushMatrix();
				GlStateManager.scale(4.0F, 4.0F, 4.0F);
				var9 = var8 << 24 & -16777216;
				func_175179_f().drawString(field_175201_x, -func_175179_f().getStringWidth(field_175201_x) / 2, -10.0F,
						16777215 | var9, true);
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				GlStateManager.scale(2.0F, 2.0F, 2.0F);
				func_175179_f().drawString(field_175200_y, -func_175179_f().getStringWidth(field_175200_y) / 2, 5.0F,
						16777215 | var9, true);
				GlStateManager.popMatrix();
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}

			Minecraft.mcProfiler.endSection();
		}

		final Scoreboard var121 = Minecraft.theWorld.getScoreboard();
		ScoreObjective var13 = null;
		final ScorePlayerTeam var15 = var121.getPlayersTeam(Minecraft.thePlayer.getName());

		if (var15 != null) {
			final int var16 = var15.func_178775_l().func_175746_b();

			if (var16 >= 0) {
				var13 = var121.getObjectiveInDisplaySlot(3 + var16);
			}
		}

		ScoreObjective var161 = var13 != null ? var13 : var121.getObjectiveInDisplaySlot(1);

		if (var161 != null && !NoScoreboard.mod.isToggled()) {
			func_180475_a(var161, var2);
		}

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.disableAlpha();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, var4 - 48, 0.0F);
		Minecraft.mcProfiler.startSection("chat");
		persistantChatGUI.drawChat(updateCounter);
		Minecraft.mcProfiler.endSection();
		GlStateManager.popMatrix();
		var161 = var121.getObjectiveInDisplaySlot(0);

		if (Minecraft.gameSettings.keyBindPlayerList.getIsKeyPressed() && (!mc.isIntegratedServerRunning()
				|| Minecraft.thePlayer.sendQueue.getPlayerInfo().size() > 1 || var161 != null)) {
			overlayPlayerList.func_175246_a(true);
			overlayPlayerList.func_175249_a(var3, var121, var161);
		} else {
			overlayPlayerList.func_175246_a(false);
		}

		if (!Client.isHidden) {
			Client.getInGameGUI().renderScreen();
		}
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
	}

	protected void func_180479_a(final ScaledResolution p_180479_1_, final float p_180479_2_) {
		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);
		if (mc.func_175606_aa() instanceof EntityPlayer && !(Minecraft.currentScreen instanceof ClickGUI)) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getTextureManager().bindTexture(widgetsTexPath);
			final EntityPlayer var3 = (EntityPlayer) mc.func_175606_aa();
			final int var4 = p_180479_1_.getScaledWidth() / 2;
			final float var5 = zLevel;
			zLevel = -90.0F;

			// TODO

			// EaZy Client

			if (Configs.advancedHotbar && !Client.isHidden) {

				Gui.drawRect(0, p_180479_1_.getScaledHeight(), p_180479_1_.getScaledWidth(),
						p_180479_1_.getScaledHeight() - 22, new Color(0.1f, 0.1f, 0.1f, 0.6f).getRGB());
				GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
				GL11.glClearStencil(0);
				GL11.glPopAttrib();
				Gui.drawRect(var4 - 91 + var3.inventory.currentItem * 20, p_180479_1_.getScaledHeight() - 1,
						var4 - 91 + var3.inventory.currentItem * 20 + 22, p_180479_1_.getScaledHeight() - 21,
						new Color(0.5f, 0.5f, 0.5f, 0.4f).getRGB());

			} else {

				drawTexturedModalRect(var4 - 91, p_180479_1_.getScaledHeight() - 22, 0, 0, 182, 22);
				drawTexturedModalRect(var4 - 91 - 1 + var3.inventory.currentItem * 20,
						p_180479_1_.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
			}

			zLevel = var5;
			GlStateManager.enableRescaleNormal();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			RenderHelper.enableGUIStandardItemLighting();

			for (int var6 = 0; var6 < 9; ++var6) {
				final int var7 = p_180479_1_.getScaledWidth() / 2 - 90 + var6 * 20 + 2;
				final int var8 = p_180479_1_.getScaledHeight() - 16 - 3;
				func_175184_a(var6, var7, var8, p_180479_2_, var3);
			}

			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		}
	}

	public void func_175186_a(final ScaledResolution p_175186_1_, final int p_175186_2_) {
		Minecraft.mcProfiler.startSection("jumpBar");
		Minecraft.getTextureManager().bindTexture(Gui.icons);
		final float var3 = Minecraft.thePlayer.getHorseJumpPower();
		final short var4 = 182;
		final int var5 = (int) (var3 * (var4 + 1));
		final int var6 = p_175186_1_.getScaledHeight() - 32 + 3;
		drawTexturedModalRect(p_175186_2_, var6, 0, 84, var4, 5);

		if (var5 > 0 && !(Minecraft.currentScreen instanceof ClickGUI)) {
			drawTexturedModalRect(p_175186_2_, var6, 0, 89, var5, 5);
		}

		Minecraft.mcProfiler.endSection();
	}

	public void func_175176_b(final ScaledResolution p_175176_1_, final int p_175176_2_) {
		Minecraft.mcProfiler.startSection("expBar");
		Minecraft.getTextureManager().bindTexture(Gui.icons);
		final int var3 = Minecraft.thePlayer.xpBarCap();
		int var6;

		if (var3 > 0 && !(Minecraft.currentScreen instanceof ClickGUI)) {
			final short var9 = 182;
			final int var10 = (int) (Minecraft.thePlayer.experience * (var9 + 1));
			var6 = p_175176_1_.getScaledHeight() - 32 + 3;
			drawTexturedModalRect(p_175176_2_, var6, 0, 64, var9, 5);

			if (var10 > 0) {
				drawTexturedModalRect(p_175176_2_, var6, 0, 69, var10, 5);
			}
		}

		Minecraft.mcProfiler.endSection();

		if (Minecraft.thePlayer.experienceLevel > 0 && !(Minecraft.currentScreen instanceof ClickGUI)) {
			Minecraft.mcProfiler.startSection("expLevel");
			int var91 = 8453920;

			if (Config.isCustomColors()) {
				var91 = CustomColors.getExpBarTextColor(var91);
			}

			final String var101 = "" + Minecraft.thePlayer.experienceLevel;
			var6 = (p_175176_1_.getScaledWidth() - func_175179_f().getStringWidth(var101)) / 2;
			final int var7 = p_175176_1_.getScaledHeight() - 31 - 4;
			func_175179_f().drawString(var101, var6 + 1, var7, 0);
			func_175179_f().drawString(var101, var6 - 1, var7, 0);
			func_175179_f().drawString(var101, var6, var7 + 1, 0);
			func_175179_f().drawString(var101, var6, var7 - 1, 0);
			func_175179_f().drawString(var101, var6, var7, var91);
			Minecraft.mcProfiler.endSection();
		}
	}

	public void func_175182_a(final ScaledResolution p_175182_1_) {
		Minecraft.mcProfiler.startSection("toolHighlight");

		if (remainingHighlightTicks > 0 && highlightingItemStack != null) {
			String var2 = highlightingItemStack.getDisplayName();

			if (highlightingItemStack.hasDisplayName()) {
				var2 = EnumChatFormatting.ITALIC + var2;
			}

			final int var3 = (p_175182_1_.getScaledWidth() - func_175179_f().getStringWidth(var2)) / 2;
			int var4 = p_175182_1_.getScaledHeight() - 59;

			if (!Minecraft.playerController.shouldDrawHUD()) {
				var4 += 14;
			}

			int var5 = (int) (remainingHighlightTicks * 256.0F / 10.0F);

			if (var5 > 255) {
				var5 = 255;
			}

			if (var5 > 0 && !(Minecraft.currentScreen instanceof ClickGUI)) {
				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				func_175179_f().drawStringWithShadow(var2, var3, var4, 16777215 + (var5 << 24));
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}
		}

		Minecraft.mcProfiler.endSection();
	}

	public void func_175185_b(final ScaledResolution p_175185_1_) {
		Minecraft.mcProfiler.startSection("demo");
		String var2 = "";

		if (Minecraft.theWorld.getTotalWorldTime() >= 120500L) {
			var2 = I18n.format("demo.demoExpired", new Object[0]);
		} else {
			var2 = I18n.format("demo.remainingTime", new Object[] {
					StringUtils.ticksToElapsedTime((int) (120500L - Minecraft.theWorld.getTotalWorldTime())) });
		}

		final int var3 = func_175179_f().getStringWidth(var2);
		if (!(Minecraft.currentScreen instanceof ClickGUI)) {
			func_175179_f().drawStringWithShadow(var2, p_175185_1_.getScaledWidth() - var3 - 10, 5.0F, 16777215);
		}
		Minecraft.mcProfiler.endSection();
	}

	protected boolean func_175183_b() {
		if (Minecraft.gameSettings.showDebugInfo && !Minecraft.thePlayer.func_175140_cp()
				&& !Minecraft.gameSettings.field_178879_v) {
			return false;
		} else if (Minecraft.playerController.enableEverythingIsScrewedUpMode()) {
			if (mc.pointedEntity != null) {
				return true;
			} else {
				if (mc.objectMouseOver != null
						&& mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					final BlockPos var1 = mc.objectMouseOver.getBlockPos();

					if (Minecraft.theWorld.getTileEntity(var1) instanceof IInventory) {
						return true;
					}
				}

				return false;
			}
		} else {
			return true;
		}
	}

	public void func_180478_c(final ScaledResolution p_180478_1_) {
		streamIndicator.render(p_180478_1_.getScaledWidth() - 10, 10);
	}

	private void func_180475_a(final ScoreObjective p_180475_1_, final ScaledResolution p_180475_2_) {
		final Scoreboard var3 = p_180475_1_.getScoreboard();
		final Collection var4 = var3.getSortedScores(p_180475_1_);
		final ArrayList var5 = Lists.newArrayList(Iterables.filter(var4, new Predicate() {
			public boolean func_178903_a(final Score p_178903_1_) {
				return p_178903_1_.getPlayerName() != null && !p_178903_1_.getPlayerName().startsWith("#");
			}

			@Override
			public boolean apply(final Object p_apply_1_) {
				return func_178903_a((Score) p_apply_1_);
			}
		}));
		ArrayList var21;

		if (var5.size() > 15) {
			var21 = Lists.newArrayList(Iterables.skip(var5, var4.size() - 15));
		} else {
			var21 = var5;
		}

		int var6 = func_175179_f().getStringWidth(p_180475_1_.getDisplayName());
		String var10;

		for (final Iterator var22 = var21.iterator(); var22
				.hasNext(); var6 = Math.max(var6, func_175179_f().getStringWidth(var10))) {
			final Score var23 = (Score) var22.next();
			final ScorePlayerTeam var24 = var3.getPlayersTeam(var23.getPlayerName());
			var10 = ScorePlayerTeam.formatPlayerName(var24, var23.getPlayerName()) + ": " + EnumChatFormatting.RED
					+ var23.getScorePoints();
		}

		final int var221 = var21.size() * func_175179_f().FONT_HEIGHT;
		final int var231 = p_180475_2_.getScaledHeight() / 2 + var221 / 3;
		final byte var241 = 3;
		final int var25 = p_180475_2_.getScaledWidth() - var6 - var241;
		int var11 = 0;
		final Iterator var12 = var21.iterator();

		while (var12.hasNext()) {
			final Score var13 = (Score) var12.next();
			++var11;
			final ScorePlayerTeam var14 = var3.getPlayersTeam(var13.getPlayerName());
			final String var15 = ScorePlayerTeam.formatPlayerName(var14, var13.getPlayerName());
			final String var16 = EnumChatFormatting.RED + "" + var13.getScorePoints();
			final int var18 = var231 - var11 * func_175179_f().FONT_HEIGHT;
			final int var19 = p_180475_2_.getScaledWidth() - var241 + 2;
			drawRect(var25 - 2, var18, var19, var18 + func_175179_f().FONT_HEIGHT, 1342177280);
			func_175179_f().drawString(var15, var25, var18, 553648127);
			func_175179_f().drawString(var16, var19 - func_175179_f().getStringWidth(var16), var18, 553648127);

			if (var11 == var21.size()) {
				final String var20 = p_180475_1_.getDisplayName();
				drawRect(var25 - 2, var18 - func_175179_f().FONT_HEIGHT - 1, var19, var18 - 1, 1610612736);
				drawRect(var25 - 2, var18 - 1, var19, var18, 1342177280);
				func_175179_f().drawString(var20, var25 + var6 / 2 - func_175179_f().getStringWidth(var20) / 2,
						var18 - func_175179_f().FONT_HEIGHT, 553648127);
			}
		}
	}

	private void func_180477_d(final ScaledResolution p_180477_1_) {
		if (mc.func_175606_aa() instanceof EntityPlayer) {
			final EntityPlayer var2 = (EntityPlayer) mc.func_175606_aa();
			final int var3 = MathHelper.ceiling_float_int(var2.getHealth());
			final boolean var4 = field_175191_F > updateCounter && (field_175191_F - updateCounter) / 3L % 2L == 1L;

			if (var3 < field_175194_C && var2.hurtResistantTime > 0) {
				field_175190_E = Minecraft.getSystemTime();
				field_175191_F = updateCounter + 20;
			} else if (var3 > field_175194_C && var2.hurtResistantTime > 0) {
				field_175190_E = Minecraft.getSystemTime();
				field_175191_F = updateCounter + 10;
			}

			if (Minecraft.getSystemTime() - field_175190_E > 1000L) {
				field_175194_C = var3;
				field_175189_D = var3;
				field_175190_E = Minecraft.getSystemTime();
			}

			field_175194_C = var3;
			final int var5 = field_175189_D;
			rand.setSeed(updateCounter * 312871);
			final boolean var6 = false;
			final FoodStats var7 = var2.getFoodStats();
			final int var8 = var7.getFoodLevel();
			final int var9 = var7.getPrevFoodLevel();
			final IAttributeInstance var10 = var2.getEntityAttribute(SharedMonsterAttributes.maxHealth);
			final int var11 = p_180477_1_.getScaledWidth() / 2 - 91;
			final int var12 = p_180477_1_.getScaledWidth() / 2 + 91;
			final int var13 = p_180477_1_.getScaledHeight() - 39;
			final float var14 = (float) var10.getAttributeValue();
			final float var15 = var2.getAbsorptionAmount();
			final int var16 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F / 10.0F);
			final int var17 = Math.max(10 - (var16 - 2), 3);
			final int var18 = var13 - (var16 - 1) * var17 - 10;
			float var19 = var15;
			final int var20 = var2.getTotalArmorValue();
			int var21 = -1;

			if (var2.isPotionActive(Potion.regeneration)) {
				var21 = updateCounter % MathHelper.ceiling_float_int(var14 + 5.0F);
			}

			Minecraft.mcProfiler.startSection("armor");
			int var22;
			int var23;

			for (var22 = 0; var22 < 10; ++var22) {
				if (var20 > 0) {
					var23 = var11 + var22 * 8;

					if (var22 * 2 + 1 < var20 && !(Minecraft.currentScreen instanceof ClickGUI)) {
						drawTexturedModalRect(var23, var18, 34, 9, 9, 9);
					}

					if (var22 * 2 + 1 == var20 && !(Minecraft.currentScreen instanceof ClickGUI)) {
						drawTexturedModalRect(var23, var18, 25, 9, 9, 9);
					}

					if (var22 * 2 + 1 > var20 && !(Minecraft.currentScreen instanceof ClickGUI)) {
						drawTexturedModalRect(var23, var18, 16, 9, 9, 9);
					}
				}
			}

			Minecraft.mcProfiler.endStartSection("health");
			int var25;
			int var26;
			int var27;

			for (var22 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F) - 1; var22 >= 0; --var22) {
				var23 = 16;

				if (var2.isPotionActive(Potion.poison)) {
					var23 += 36;
				} else if (var2.isPotionActive(Potion.wither)) {
					var23 += 72;
				}

				byte var34 = 0;

				if (var4) {
					var34 = 1;
				}

				var25 = MathHelper.ceiling_float_int((var22 + 1) / 10.0F) - 1;
				var26 = var11 + var22 % 10 * 8;
				var27 = var13 - var25 * var17;

				if (var3 <= 4) {
					var27 += rand.nextInt(2);
				}

				if (var22 == var21) {
					var27 -= 2;
				}

				byte var36 = 0;

				if (var2.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
					var36 = 5;
				}

				if (!(Minecraft.currentScreen instanceof ClickGUI)) {

					drawTexturedModalRect(var26, var27, 16 + var34 * 9, 9 * var36, 9, 9);

					if (var4) {
						if (var22 * 2 + 1 < var5) {
							drawTexturedModalRect(var26, var27, var23 + 54, 9 * var36, 9, 9);
						}

						if (var22 * 2 + 1 == var5) {
							drawTexturedModalRect(var26, var27, var23 + 63, 9 * var36, 9, 9);
						}
					}

					if (var19 > 0.0F) {
						if (var19 == var15 && var15 % 2.0F == 1.0F) {
							drawTexturedModalRect(var26, var27, var23 + 153, 9 * var36, 9, 9);
						} else {
							drawTexturedModalRect(var26, var27, var23 + 144, 9 * var36, 9, 9);
						}

						var19 -= 2.0F;
					} else {
						if (var22 * 2 + 1 < var3) {
							drawTexturedModalRect(var26, var27, var23 + 36, 9 * var36, 9, 9);
						}

						if (var22 * 2 + 1 == var3) {
							drawTexturedModalRect(var26, var27, var23 + 45, 9 * var36, 9, 9);
						}
					}
				}
			}

			final Entity var371 = var2.ridingEntity;
			int var38;

			if (var371 == null && !(Minecraft.currentScreen instanceof ClickGUI)) {
				Minecraft.mcProfiler.endStartSection("food");

				for (var23 = 0; var23 < 10; ++var23) {
					var38 = var13;
					var25 = 16;
					byte var35 = 0;

					if (var2.isPotionActive(Potion.hunger)) {
						var25 += 36;
						var35 = 13;
					}

					if (var2.getFoodStats().getSaturationLevel() <= 0.0F && updateCounter % (var8 * 3 + 1) == 0) {
						var38 = var13 + rand.nextInt(3) - 1;
					}

					if (var6) {
						var35 = 1;
					}

					var27 = var12 - var23 * 8 - 9;
					drawTexturedModalRect(var27, var38, 16 + var35 * 9, 27, 9, 9);

					if (var6) {
						if (var23 * 2 + 1 < var9) {
							drawTexturedModalRect(var27, var38, var25 + 54, 27, 9, 9);
						}

						if (var23 * 2 + 1 == var9) {
							drawTexturedModalRect(var27, var38, var25 + 63, 27, 9, 9);
						}
					}

					if (var23 * 2 + 1 < var8) {
						drawTexturedModalRect(var27, var38, var25 + 36, 27, 9, 9);
					}

					if (var23 * 2 + 1 == var8) {
						drawTexturedModalRect(var27, var38, var25 + 45, 27, 9, 9);
					}
				}
			} else if (var371 instanceof EntityLivingBase) {
				Minecraft.mcProfiler.endStartSection("mountHealth");
				final EntityLivingBase var391 = (EntityLivingBase) var371;
				var38 = (int) Math.ceil(var391.getHealth());
				final float var37 = var391.getMaxHealth();
				var26 = (int) (var37 + 0.5F) / 2;

				if (var26 > 30) {
					var26 = 30;
				}

				var27 = var13;

				for (int var39 = 0; var26 > 0; var39 += 20) {
					final int var29 = Math.min(var26, 10);
					var26 -= var29;

					for (int var30 = 0; var30 < var29; ++var30) {
						final byte var31 = 52;
						byte var32 = 0;

						if (var6) {
							var32 = 1;
						}

						final int var33 = var12 - var30 * 8 - 9;
						drawTexturedModalRect(var33, var27, var31 + var32 * 9, 9, 9, 9);

						if (var30 * 2 + 1 + var39 < var38) {
							drawTexturedModalRect(var33, var27, var31 + 36, 9, 9, 9);
						}

						if (var30 * 2 + 1 + var39 == var38) {
							drawTexturedModalRect(var33, var27, var31 + 45, 9, 9, 9);
						}
					}

					var27 -= 10;
				}
			}

			Minecraft.mcProfiler.endStartSection("air");

			if (var2.isInsideOfMaterial(Material.water)) {
				var23 = Minecraft.thePlayer.getAir();
				var38 = MathHelper.ceiling_double_int((var23 - 2) * 10.0D / 300.0D);
				var25 = MathHelper.ceiling_double_int(var23 * 10.0D / 300.0D) - var38;

				for (var26 = 0; var26 < var38 + var25; ++var26) {
					if (var26 < var38) {
						drawTexturedModalRect(var12 - var26 * 8 - 9, var18, 16, 18, 9, 9);
					} else {
						drawTexturedModalRect(var12 - var26 * 8 - 9, var18, 25, 18, 9, 9);
					}
				}
			}

			Minecraft.mcProfiler.endSection();
		}
	}

	/**
	 * Renders dragon's (boss) health on the HUD
	 */
	private void renderBossHealth() {
		if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
			--BossStatus.statusBarTime;
			final ScaledResolution var2 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
			final int var3 = var2.getScaledWidth();
			final short var4 = 182;
			final int var5 = var3 / 2 - var4 / 2;
			final int var6 = (int) (BossStatus.healthScale * (var4 + 1));
			final byte var7 = 12;
			drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
			drawTexturedModalRect(var5, var7, 0, 74, var4, 5);

			if (var6 > 0) {
				drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
			}

			final String var8 = BossStatus.bossName;
			int bossTextColor = 16777215;

			if (Config.isCustomColors()) {
				bossTextColor = CustomColors.getBossTextColor(bossTextColor);
			}

			func_175179_f().drawStringWithShadow(var8, var3 / 2 - func_175179_f().getStringWidth(var8) / 2, var7 - 10,
					bossTextColor);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getTextureManager().bindTexture(icons);
		}
	}

	private void func_180476_e(final ScaledResolution p_180476_1_) {
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
		Minecraft.getTextureManager().bindTexture(pumpkinBlurTexPath);
		final Tessellator var2 = Tessellator.getInstance();
		final WorldRenderer var3 = var2.getWorldRenderer();
		var3.startDrawingQuads();
		var3.addVertexWithUV(0.0D, p_180476_1_.getScaledHeight(), -90.0D, 0.0D, 1.0D);
		var3.addVertexWithUV(p_180476_1_.getScaledWidth(), p_180476_1_.getScaledHeight(), -90.0D, 1.0D, 1.0D);
		var3.addVertexWithUV(p_180476_1_.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
		var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var2.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void func_180480_a(float p_180480_1_, final ScaledResolution p_180480_2_) {
		if (Config.isVignetteEnabled()) {
			p_180480_1_ = 1.0F - p_180480_1_;
			p_180480_1_ = MathHelper.clamp_float(p_180480_1_, 0.0F, 1.0F);
			final WorldBorder var3 = Minecraft.theWorld.getWorldBorder();
			float var4 = (float) var3.getClosestDistance(Minecraft.thePlayer);
			final double var5 = Math.min(var3.func_177749_o() * var3.getWarningTime() * 1000.0D,
					Math.abs(var3.getTargetSize() - var3.getDiameter()));
			final double var7 = Math.max(var3.getWarningDistance(), var5);

			if (var4 < var7) {
				var4 = 1.0F - (float) (var4 / var7);
			} else {
				var4 = 0.0F;
			}

			prevVignetteBrightness = (float) (prevVignetteBrightness + (p_180480_1_ - prevVignetteBrightness) * 0.01D);
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);

			if (var4 > 0.0F) {
				GlStateManager.color(0.0F, var4, var4, 1.0F);
			} else {
				GlStateManager.color(prevVignetteBrightness, prevVignetteBrightness, prevVignetteBrightness, 1.0F);
			}

			Minecraft.getTextureManager().bindTexture(vignetteTexPath);
			final Tessellator var9 = Tessellator.getInstance();
			final WorldRenderer var10 = var9.getWorldRenderer();
			var10.startDrawingQuads();
			var10.addVertexWithUV(0.0D, p_180480_2_.getScaledHeight(), -90.0D, 0.0D, 1.0D);
			var10.addVertexWithUV(p_180480_2_.getScaledWidth(), p_180480_2_.getScaledHeight(), -90.0D, 1.0D, 1.0D);
			var10.addVertexWithUV(p_180480_2_.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
			var10.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
			var9.draw();
			GlStateManager.depthMask(true);
			GlStateManager.enableDepth();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		}
	}

	private void func_180474_b(float p_180474_1_, final ScaledResolution p_180474_2_) {
		if (p_180474_1_ < 1.0F) {
			p_180474_1_ *= p_180474_1_;
			p_180474_1_ *= p_180474_1_;
			p_180474_1_ = p_180474_1_ * 0.8F + 0.2F;
		}

		GlStateManager.disableAlpha();
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F, p_180474_1_);
		Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		final TextureAtlasSprite var3 = mc.getBlockRendererDispatcher().func_175023_a()
				.func_178122_a(Blocks.portal.getDefaultState());
		final float var4 = var3.getMinU();
		final float var5 = var3.getMinV();
		final float var6 = var3.getMaxU();
		final float var7 = var3.getMaxV();
		final Tessellator var8 = Tessellator.getInstance();
		final WorldRenderer var9 = var8.getWorldRenderer();
		var9.startDrawingQuads();
		var9.addVertexWithUV(0.0D, p_180474_2_.getScaledHeight(), -90.0D, var4, var7);
		var9.addVertexWithUV(p_180474_2_.getScaledWidth(), p_180474_2_.getScaledHeight(), -90.0D, var6, var7);
		var9.addVertexWithUV(p_180474_2_.getScaledWidth(), 0.0D, -90.0D, var6, var5);
		var9.addVertexWithUV(0.0D, 0.0D, -90.0D, var4, var5);
		var8.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void func_175184_a(final int p_175184_1_, final int p_175184_2_, final int p_175184_3_,
			final float p_175184_4_, final EntityPlayer p_175184_5_) {
		final ItemStack var6 = p_175184_5_.inventory.mainInventory[p_175184_1_];

		if (var6 != null) {
			final float var7 = var6.animationsToGo - p_175184_4_;

			if (var7 > 0.0F) {
				GlStateManager.pushMatrix();
				final float var8 = 1.0F + var7 / 5.0F;
				GlStateManager.translate(p_175184_2_ + 8, p_175184_3_ + 12, 0.0F);
				GlStateManager.scale(1.0F / var8, (var8 + 1.0F) / 2.0F, 1.0F);
				GlStateManager.translate(-(p_175184_2_ + 8), -(p_175184_3_ + 12), 0.0F);
			}

			itemRenderer.renderItemAndEffectIntoGUI(var6, p_175184_2_, p_175184_3_);

			if (var7 > 0.0F) {
				GlStateManager.popMatrix();
			}

			itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, var6, p_175184_2_, p_175184_3_);
		}
	}

	/**
	 * The update tick for the ingame UI
	 */
	public void updateTick() {
		if (recordPlayingUpFor > 0) {
			--recordPlayingUpFor;
		}

		if (field_175195_w > 0) {
			--field_175195_w;

			if (field_175195_w <= 0) {
				field_175201_x = "";
				field_175200_y = "";
			}
		}

		++updateCounter;
		streamIndicator.func_152439_a();

		if (Minecraft.thePlayer != null) {
			final ItemStack var1 = Minecraft.thePlayer.inventory.getCurrentItem();

			if (var1 == null) {
				remainingHighlightTicks = 0;
			} else if (highlightingItemStack != null && var1.getItem() == highlightingItemStack.getItem()
					&& ItemStack.areItemStackTagsEqual(var1, highlightingItemStack)
					&& (var1.isItemStackDamageable() || var1.getMetadata() == highlightingItemStack.getMetadata())) {
				if (remainingHighlightTicks > 0) {
					--remainingHighlightTicks;
				}
			} else {
				remainingHighlightTicks = 40;
			}

			highlightingItemStack = var1;
		}
	}

	public void setRecordPlayingMessage(final String p_73833_1_) {
		setRecordPlaying(I18n.format("record.nowPlaying", new Object[] { p_73833_1_ }), true);
	}

	public void setRecordPlaying(final String p_110326_1_, final boolean p_110326_2_) {
		recordPlaying = p_110326_1_;
		recordPlayingUpFor = 60;
		recordIsPlaying = p_110326_2_;
	}

	public void func_175178_a(final String p_175178_1_, final String p_175178_2_, final int p_175178_3_,
			final int p_175178_4_, final int p_175178_5_) {
		if (p_175178_1_ == null && p_175178_2_ == null && p_175178_3_ < 0 && p_175178_4_ < 0 && p_175178_5_ < 0) {
			field_175201_x = "";
			field_175200_y = "";
			field_175195_w = 0;
		} else if (p_175178_1_ != null) {
			field_175201_x = p_175178_1_;
			field_175195_w = field_175199_z + field_175192_A + field_175193_B;
		} else if (p_175178_2_ != null) {
			field_175200_y = p_175178_2_;
		} else {
			if (p_175178_3_ >= 0) {
				field_175199_z = p_175178_3_;
			}

			if (p_175178_4_ >= 0) {
				field_175192_A = p_175178_4_;
			}

			if (p_175178_5_ >= 0) {
				field_175193_B = p_175178_5_;
			}

			if (field_175195_w > 0) {
				field_175195_w = field_175199_z + field_175192_A + field_175193_B;
			}
		}
	}

	public void func_175188_a(final IChatComponent p_175188_1_, final boolean p_175188_2_) {
		setRecordPlaying(p_175188_1_.getUnformattedText(), p_175188_2_);
	}

	/**
	 * returns a pointer to the persistant Chat GUI, containing all previous
	 * chat messages and such
	 */
	public GuiNewChat getChatGUI() {
		return persistantChatGUI;
	}

	public int getUpdateCounter() {
		return updateCounter;
	}

	public FontRenderer func_175179_f() {
		return mc.fontRendererObj;
	}

	public GuiSpectator func_175187_g() {
		return field_175197_u;
	}

	public GuiPlayerTabOverlay getTabList() {
		return overlayPlayerList;
	}
}
