package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class GuiPlayerTabOverlay extends Gui {

public static final int EaZy = 498;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Ordering field_175252_a = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator(null));
	private final Minecraft field_175250_f;
	private final GuiIngame field_175251_g;
	private IChatComponent footer;
	private IChatComponent header;
	private long field_175253_j;
	private boolean field_175254_k;
	// private static final String __OBFID = "http://https://fuckuskid00001943";

	public GuiPlayerTabOverlay(final Minecraft mcIn, final GuiIngame p_i45529_2_) {
		field_175250_f = mcIn;
		field_175251_g = p_i45529_2_;
	}

	public String func_175243_a(final NetworkPlayerInfo p_175243_1_) {
		return p_175243_1_.func_178854_k() != null ? p_175243_1_.func_178854_k().getFormattedText()
				: ScorePlayerTeam.formatPlayerName(p_175243_1_.func_178850_i(), p_175243_1_.func_178845_a().getName());
	}

	public void func_175246_a(final boolean p_175246_1_) {
		if (p_175246_1_ && !field_175254_k) {
			field_175253_j = Minecraft.getSystemTime();
		}

		field_175254_k = p_175246_1_;
	}

	public void func_175249_a(final int p_175249_1_, final Scoreboard p_175249_2_, final ScoreObjective p_175249_3_) {
		final NetHandlerPlayClient var4 = Minecraft.thePlayer.sendQueue;
		List var5 = field_175252_a.sortedCopy(var4.getPlayerInfo());
		int var6 = 0;
		int var7 = 0;
		final Iterator var8 = var5.iterator();
		int var10;

		while (var8.hasNext()) {
			final NetworkPlayerInfo var9 = (NetworkPlayerInfo) var8.next();
			var10 = field_175250_f.fontRendererObj.getStringWidth(func_175243_a(var9));
			var6 = Math.max(var6, var10);

			if (p_175249_3_ != null && p_175249_3_.func_178766_e() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
				var10 = field_175250_f.fontRendererObj.getStringWidth(" " + p_175249_2_
						.getValueFromObjective(var9.func_178845_a().getName(), p_175249_3_).getScorePoints());
				var7 = Math.max(var7, var10);
			}
		}

		var5 = var5.subList(0, Math.min(var5.size(), 80));
		final int var28 = var5.size();
		int var29 = var28;

		for (var10 = 1; var29 > 20; var29 = (var28 + var10 - 1) / var10) {
			++var10;
		}

		final boolean var11 = field_175250_f.isIntegratedServerRunning()
				|| Minecraft.getNetHandler().getNetworkManager().func_179292_f();
		int var12;

		if (p_175249_3_ != null) {
			if (p_175249_3_.func_178766_e() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
				var12 = 90;
			} else {
				var12 = var7;
			}
		} else {
			var12 = 0;
		}

		final int var13 = Math.min(var10 * ((var11 ? 9 : 0) + var6 + var12 + 13), p_175249_1_ - 50) / var10;
		final int var14 = p_175249_1_ / 2 - (var13 * var10 + (var10 - 1) * 5) / 2;
		int var15 = 10;
		int var16 = var13 * var10 + (var10 - 1) * 5;
		List var17 = null;
		List var18 = null;
		Iterator var19;
		String var20;

		if (header != null) {
			var17 = field_175250_f.fontRendererObj.listFormattedStringToWidth(header.getFormattedText(),
					p_175249_1_ - 50);

			for (var19 = var17.iterator(); var19
					.hasNext(); var16 = Math.max(var16, field_175250_f.fontRendererObj.getStringWidth(var20))) {
				var20 = (String) var19.next();
			}
		}

		if (footer != null) {
			var18 = field_175250_f.fontRendererObj.listFormattedStringToWidth(footer.getFormattedText(),
					p_175249_1_ - 50);

			for (var19 = var18.iterator(); var19
					.hasNext(); var16 = Math.max(var16, field_175250_f.fontRendererObj.getStringWidth(var20))) {
				var20 = (String) var19.next();
			}
		}

		int var21;

		if (var17 != null) {
			drawRect(p_175249_1_ / 2 - var16 / 2 - 1, var15 - 1, p_175249_1_ / 2 + var16 / 2 + 1,
					var15 + var17.size() * field_175250_f.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

			for (var19 = var17.iterator(); var19.hasNext(); var15 += field_175250_f.fontRendererObj.FONT_HEIGHT) {
				var20 = (String) var19.next();
				var21 = field_175250_f.fontRendererObj.getStringWidth(var20);
				field_175250_f.fontRendererObj.drawStringWithShadow(var20, p_175249_1_ / 2 - var21 / 2, var15, -1);
			}

			++var15;
		}

		drawRect(p_175249_1_ / 2 - var16 / 2 - 1, var15 - 1, p_175249_1_ / 2 + var16 / 2 + 1, var15 + var29 * 9,
				Integer.MIN_VALUE);

		for (int var30 = 0; var30 < var28; ++var30) {
			final int var31 = var30 / var29;
			var21 = var30 % var29;
			int var22 = var14 + var31 * var13 + var31 * 5;
			final int var23 = var15 + var21 * 9;
			drawRect(var22, var23, var22 + var13, var23 + 8, 553648127);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

			if (var30 < var5.size()) {
				final NetworkPlayerInfo var24 = (NetworkPlayerInfo) var5.get(var30);
				String var25 = func_175243_a(var24);

				if (var11) {
					Minecraft.getTextureManager().bindTexture(var24.func_178837_g());
					Gui.drawScaledCustomSizeModalRect(var22, var23, 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
					final EntityPlayer var26 = Minecraft.theWorld.getPlayerEntityByUUID(var24.func_178845_a().getId());

					if (var26 != null && var26.func_175148_a(EnumPlayerModelParts.HAT)) {
						Gui.drawScaledCustomSizeModalRect(var22, var23, 40.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
					}

					var22 += 9;
				}

				if (var24.getGameType() == WorldSettings.GameType.SPECTATOR) {
					var25 = EnumChatFormatting.ITALIC + var25;
					field_175250_f.fontRendererObj.drawStringWithShadow(var25, var22, var23, -1862270977);
				} else {
					field_175250_f.fontRendererObj.drawStringWithShadow(var25, var22, var23, -1);
				}

				if (p_175249_3_ != null && var24.getGameType() != WorldSettings.GameType.SPECTATOR) {
					final int var32 = var22 + var6 + 1;
					final int var27 = var32 + var12;

					if (var27 - var32 > 5) {
						func_175247_a(p_175249_3_, var23, var24.func_178845_a().getName(), var32, var27, var24);
					}
				}

				func_175245_a(var13, var22 - (var11 ? 9 : 0), var23, var24);
			}
		}

		if (var18 != null) {
			var15 += var29 * 9 + 1;
			drawRect(p_175249_1_ / 2 - var16 / 2 - 1, var15 - 1, p_175249_1_ / 2 + var16 / 2 + 1,
					var15 + var18.size() * field_175250_f.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

			for (var19 = var18.iterator(); var19.hasNext(); var15 += field_175250_f.fontRendererObj.FONT_HEIGHT) {
				var20 = (String) var19.next();
				var21 = field_175250_f.fontRendererObj.getStringWidth(var20);
				field_175250_f.fontRendererObj.drawStringWithShadow(var20, p_175249_1_ / 2 - var21 / 2, var15, -1);
			}
		}
	}

	protected void func_175245_a(final int p_175245_1_, final int p_175245_2_, final int p_175245_3_,
			final NetworkPlayerInfo p_175245_4_) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(icons);
		final byte var5 = 0;
		byte var7;

		if (p_175245_4_.getResponseTime() < 0) {
			var7 = 5;
		} else if (p_175245_4_.getResponseTime() < 150) {
			var7 = 0;
		} else if (p_175245_4_.getResponseTime() < 300) {
			var7 = 1;
		} else if (p_175245_4_.getResponseTime() < 600) {
			var7 = 2;
		} else if (p_175245_4_.getResponseTime() < 1000) {
			var7 = 3;
		} else {
			var7 = 4;
		}

		zLevel += 100.0F;
		drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + var5 * 10, 176 + var7 * 8, 10, 8);
		zLevel -= 100.0F;
	}

	private void func_175247_a(final ScoreObjective p_175247_1_, final int p_175247_2_, final String p_175247_3_,
			final int p_175247_4_, final int p_175247_5_, final NetworkPlayerInfo p_175247_6_) {
		final int var7 = p_175247_1_.getScoreboard().getValueFromObjective(p_175247_3_, p_175247_1_).getScorePoints();

		if (p_175247_1_.func_178766_e() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
			Minecraft.getTextureManager().bindTexture(icons);

			if (field_175253_j == p_175247_6_.func_178855_p()) {
				if (var7 < p_175247_6_.func_178835_l()) {
					p_175247_6_.func_178846_a(Minecraft.getSystemTime());
					p_175247_6_.func_178844_b(field_175251_g.getUpdateCounter() + 20);
				} else if (var7 > p_175247_6_.func_178835_l()) {
					p_175247_6_.func_178846_a(Minecraft.getSystemTime());
					p_175247_6_.func_178844_b(field_175251_g.getUpdateCounter() + 10);
				}
			}

			if (Minecraft.getSystemTime() - p_175247_6_.func_178847_n() > 1000L
					|| field_175253_j != p_175247_6_.func_178855_p()) {
				p_175247_6_.func_178836_b(var7);
				p_175247_6_.func_178857_c(var7);
				p_175247_6_.func_178846_a(Minecraft.getSystemTime());
			}

			p_175247_6_.func_178843_c(field_175253_j);
			p_175247_6_.func_178836_b(var7);
			final int var8 = MathHelper.ceiling_float_int(Math.max(var7, p_175247_6_.func_178860_m()) / 2.0F);
			final int var9 = Math.max(MathHelper.ceiling_float_int(var7 / 2),
					Math.max(MathHelper.ceiling_float_int(p_175247_6_.func_178860_m() / 2), 10));
			final boolean var10 = p_175247_6_.func_178858_o() > field_175251_g.getUpdateCounter()
					&& (p_175247_6_.func_178858_o() - field_175251_g.getUpdateCounter()) / 3L % 2L == 1L;

			if (var8 > 0) {
				final float var11 = Math.min((float) (p_175247_5_ - p_175247_4_ - 4) / (float) var9, 9.0F);

				if (var11 > 3.0F) {
					int var12;

					for (var12 = var8; var12 < var9; ++var12) {
						func_175174_a(p_175247_4_ + var12 * var11, p_175247_2_, var10 ? 25 : 16, 0, 9, 9);
					}

					for (var12 = 0; var12 < var8; ++var12) {
						func_175174_a(p_175247_4_ + var12 * var11, p_175247_2_, var10 ? 25 : 16, 0, 9, 9);

						if (var10) {
							if (var12 * 2 + 1 < p_175247_6_.func_178860_m()) {
								func_175174_a(p_175247_4_ + var12 * var11, p_175247_2_, 70, 0, 9, 9);
							}

							if (var12 * 2 + 1 == p_175247_6_.func_178860_m()) {
								func_175174_a(p_175247_4_ + var12 * var11, p_175247_2_, 79, 0, 9, 9);
							}
						}

						if (var12 * 2 + 1 < var7) {
							func_175174_a(p_175247_4_ + var12 * var11, p_175247_2_, var12 >= 10 ? 160 : 52, 0, 9, 9);
						}

						if (var12 * 2 + 1 == var7) {
							func_175174_a(p_175247_4_ + var12 * var11, p_175247_2_, var12 >= 10 ? 169 : 61, 0, 9, 9);
						}
					}
				} else {
					final float var16 = MathHelper.clamp_float(var7 / 20.0F, 0.0F, 1.0F);
					final int var13 = (int) ((1.0F - var16) * 255.0F) << 16 | (int) (var16 * 255.0F) << 8;
					String var14 = "" + var7 / 2.0F;

					if (p_175247_5_ - field_175250_f.fontRendererObj.getStringWidth(var14 + "hp") >= p_175247_4_) {
						var14 = var14 + "hp";
					}

					field_175250_f.fontRendererObj.drawStringWithShadow(var14,
							(p_175247_5_ + p_175247_4_) / 2 - field_175250_f.fontRendererObj.getStringWidth(var14) / 2,
							p_175247_2_, var13);
				}
			}
		} else {
			final String var15 = EnumChatFormatting.YELLOW + "" + var7;
			field_175250_f.fontRendererObj.drawStringWithShadow(var15,
					p_175247_5_ - field_175250_f.fontRendererObj.getStringWidth(var15), p_175247_2_, 16777215);
		}
	}

	public void setFooter(final IChatComponent p_175248_1_) {
		footer = p_175248_1_;
	}

	public void setHeader(final IChatComponent p_175244_1_) {
		header = p_175244_1_;
	}

	static class PlayerComparator implements Comparator {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001941";

		private PlayerComparator() {}

		public int func_178952_a(final NetworkPlayerInfo p_178952_1_, final NetworkPlayerInfo p_178952_2_) {
			final ScorePlayerTeam var3 = p_178952_1_.func_178850_i();
			final ScorePlayerTeam var4 = p_178952_2_.func_178850_i();
			return ComparisonChain.start()
					.compareTrueFirst(p_178952_1_.getGameType() != WorldSettings.GameType.SPECTATOR,
							p_178952_2_.getGameType() != WorldSettings.GameType.SPECTATOR)
					.compare(var3 != null ? var3.getRegisteredName() : "", var4 != null ? var4.getRegisteredName() : "")
					.compare(p_178952_1_.func_178845_a().getName(), p_178952_2_.func_178845_a().getName()).result();
		}

		@Override
		public int compare(final Object p_compare_1_, final Object p_compare_2_) {
			return func_178952_a((NetworkPlayerInfo) p_compare_1_, (NetworkPlayerInfo) p_compare_2_);
		}

		PlayerComparator(final Object p_i45528_1_) {
			this();
		}
	}
}
