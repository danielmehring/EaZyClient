package net.minecraft.client.gui.achievement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Mouse;

import com.google.common.collect.Lists;

public class GuiStats extends GuiScreen implements IProgressMeter {

public static final int EaZy = 456;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected GuiScreen parentScreen;
	protected String screenTitle = "Select world";
	private GuiStats.StatsGeneral generalStats;
	private GuiStats.StatsItem itemStats;
	private GuiStats.StatsBlock blockStats;
	private GuiStats.StatsMobsList mobStats;
	private final StatFileWriter field_146546_t;
	private GuiSlot displaySlot;

	/** When true, the game will be paused when the gui is shown */
	private boolean doesGuiPauseGame = true;
	// private static final String __OBFID = "http://https://fuckuskid00000723";

	public GuiStats(final GuiScreen p_i1071_1_, final StatFileWriter p_i1071_2_) {
		parentScreen = p_i1071_1_;
		field_146546_t = p_i1071_2_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		screenTitle = I18n.format("gui.stats", new Object[0]);
		doesGuiPauseGame = true;
		Minecraft.getNetHandler()
				.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		if (displaySlot != null) {
			displaySlot.func_178039_p();
		}
	}

	public void func_175366_f() {
		generalStats = new GuiStats.StatsGeneral(mc);
		generalStats.registerScrollButtons(1, 1);
		itemStats = new GuiStats.StatsItem(mc);
		itemStats.registerScrollButtons(1, 1);
		blockStats = new GuiStats.StatsBlock(mc);
		blockStats.registerScrollButtons(1, 1);
		mobStats = new GuiStats.StatsMobsList(mc);
		mobStats.registerScrollButtons(1, 1);
	}

	public void createButtons() {
		buttonList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
		buttonList.add(new GuiButton(1, width / 2 - 160, height - 52, 80, 20,
				I18n.format("stat.generalButton", new Object[0])));
		GuiButton var1;
		GuiButton var2;
		GuiButton var3;
		buttonList.add(var1 = new GuiButton(2, width / 2 - 80, height - 52, 80, 20,
				I18n.format("stat.blocksButton", new Object[0])));
		buttonList.add(var2 = new GuiButton(3, width / 2, height - 52, 80, 20,
				I18n.format("stat.itemsButton", new Object[0])));
		buttonList.add(var3 = new GuiButton(4, width / 2 + 80, height - 52, 80, 20,
				I18n.format("stat.mobsButton", new Object[0])));

		if (blockStats.getSize() == 0) {
			var1.enabled = false;
		}

		if (itemStats.getSize() == 0) {
			var2.enabled = false;
		}

		if (mobStats.getSize() == 0) {
			var3.enabled = false;
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 0) {
				mc.displayGuiScreen(parentScreen);
			} else if (button.id == 1) {
				displaySlot = generalStats;
			} else if (button.id == 3) {
				displaySlot = itemStats;
			} else if (button.id == 2) {
				displaySlot = blockStats;
			} else if (button.id == 4) {
				displaySlot = mobStats;
			} else {
				displaySlot.actionPerformed(button);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		if (doesGuiPauseGame) {
			drawDefaultBackground(mouseX, mouseY);
			drawCenteredString(fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), width / 2,
					height / 2, 16777215);
			drawCenteredString(fontRendererObj,
					lanSearchStates[(int) (Minecraft.getSystemTime() / 150L % lanSearchStates.length)], width / 2,
					height / 2 + fontRendererObj.FONT_HEIGHT * 2, 16777215);
		} else {
			displaySlot.drawScreen(mouseX, mouseY, partialTicks);
			drawCenteredString(fontRendererObj, screenTitle, width / 2, 20, 16777215);
			super.drawScreen(mouseX, mouseY, partialTicks);
		}
	}

	@Override
	public void doneLoading() {
		if (doesGuiPauseGame) {
			func_175366_f();
			createButtons();
			displaySlot = generalStats;
			doesGuiPauseGame = false;
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return !doesGuiPauseGame;
	}

	private void drawStatsScreen(final int p_146521_1_, final int p_146521_2_, final Item p_146521_3_) {
		drawButtonBackground(p_146521_1_ + 1, p_146521_2_ + 1);
		GlStateManager.enableRescaleNormal();
		RenderHelper.enableGUIStandardItemLighting();
		itemRender.func_175042_a(new ItemStack(p_146521_3_, 1, 0), p_146521_1_ + 2, p_146521_2_ + 2);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
	}

	/**
	 * Draws a gray box that serves as a button background.
	 */
	private void drawButtonBackground(final int p_146531_1_, final int p_146531_2_) {
		drawSprite(p_146531_1_, p_146531_2_, 0, 0);
	}

	/**
	 * Draws a sprite from assets/textures/gui/container/stats_icons.png
	 */
	private void drawSprite(final int p_146527_1_, final int p_146527_2_, final int p_146527_3_,
			final int p_146527_4_) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(statIcons);
		final Tessellator var9 = Tessellator.getInstance();
		final WorldRenderer var10 = var9.getWorldRenderer();
		var10.startDrawingQuads();
		var10.addVertexWithUV(p_146527_1_ + 0, p_146527_2_ + 18, zLevel, (p_146527_3_ + 0) * 0.0078125F,
				(p_146527_4_ + 18) * 0.0078125F);
		var10.addVertexWithUV(p_146527_1_ + 18, p_146527_2_ + 18, zLevel, (p_146527_3_ + 18) * 0.0078125F,
				(p_146527_4_ + 18) * 0.0078125F);
		var10.addVertexWithUV(p_146527_1_ + 18, p_146527_2_ + 0, zLevel, (p_146527_3_ + 18) * 0.0078125F,
				(p_146527_4_ + 0) * 0.0078125F);
		var10.addVertexWithUV(p_146527_1_ + 0, p_146527_2_ + 0, zLevel, (p_146527_3_ + 0) * 0.0078125F,
				(p_146527_4_ + 0) * 0.0078125F);
		var9.draw();
	}

	abstract class Stats extends GuiSlot {
		protected int field_148218_l = -1;
		protected List statsHolder;
		protected Comparator statSorter;
		protected int field_148217_o = -1;
		protected int field_148215_p;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000730";

		protected Stats(final Minecraft mcIn) {
			super(mcIn, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 20);
			setShowSelectionBox(false);
			setHasListHeader(true, 20);
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return false;
		}

		@Override
		protected void drawBackground() {
			drawDefaultBackground(mouseX, mouseY);
		}

		@Override
		protected void drawListHeader(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
			if (!Mouse.isButtonDown(0)) {
				field_148218_l = -1;
			}

			if (field_148218_l == 0) {
				drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 0);
			} else {
				drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 18);
			}

			if (field_148218_l == 1) {
				drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 0);
			} else {
				drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 18);
			}

			if (field_148218_l == 2) {
				drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 0);
			} else {
				drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 18);
			}

			if (field_148217_o != -1) {
				short var4 = 79;
				byte var5 = 18;

				if (field_148217_o == 1) {
					var4 = 129;
				} else if (field_148217_o == 2) {
					var4 = 179;
				}

				if (field_148215_p == 1) {
					var5 = 36;
				}

				drawSprite(p_148129_1_ + var4, p_148129_2_ + 1, var5, 0);
			}
		}

		@Override
		protected void func_148132_a(final int p_148132_1_, final int p_148132_2_) {
			field_148218_l = -1;

			if (p_148132_1_ >= 79 && p_148132_1_ < 115) {
				field_148218_l = 0;
			} else if (p_148132_1_ >= 129 && p_148132_1_ < 165) {
				field_148218_l = 1;
			} else if (p_148132_1_ >= 179 && p_148132_1_ < 215) {
				field_148218_l = 2;
			}

			if (field_148218_l >= 0) {
				func_148212_h(field_148218_l);
				mc.getSoundHandler().playSound(PositionedSoundRecord
						.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
			}
		}

		@Override
		protected final int getSize() {
			return statsHolder.size();
		}

		protected final StatCrafting func_148211_c(final int p_148211_1_) {
			return (StatCrafting) statsHolder.get(p_148211_1_);
		}

		protected abstract String func_148210_b(int var1);

		protected void func_148209_a(final StatBase p_148209_1_, final int p_148209_2_, final int p_148209_3_,
				final boolean p_148209_4_) {
			String var5;

			if (p_148209_1_ != null) {
				var5 = p_148209_1_.func_75968_a(field_146546_t.writeStat(p_148209_1_));
				drawString(GuiStats.this.fontRendererObj, var5,
						p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(var5), p_148209_3_ + 5,
						p_148209_4_ ? 16777215 : 9474192);
			} else {
				var5 = "-";
				drawString(GuiStats.this.fontRendererObj, var5,
						p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(var5), p_148209_3_ + 5,
						p_148209_4_ ? 16777215 : 9474192);
			}
		}

		@Override
		protected void func_148142_b(final int p_148142_1_, final int p_148142_2_) {
			if (p_148142_2_ >= top && p_148142_2_ <= bottom) {
				final int var3 = getSlotIndexFromScreenCoords(p_148142_1_, p_148142_2_);
				final int var4 = width / 2 - 92 - 16;

				if (var3 >= 0) {
					if (p_148142_1_ < var4 + 40 || p_148142_1_ > var4 + 40 + 20) {
						return;
					}

					final StatCrafting var5 = func_148211_c(var3);
					func_148213_a(var5, p_148142_1_, p_148142_2_);
				} else {
					String var9 = "";

					if (p_148142_1_ >= var4 + 115 - 18 && p_148142_1_ <= var4 + 115) {
						var9 = func_148210_b(0);
					} else if (p_148142_1_ >= var4 + 165 - 18 && p_148142_1_ <= var4 + 165) {
						var9 = func_148210_b(1);
					} else {
						if (p_148142_1_ < var4 + 215 - 18 || p_148142_1_ > var4 + 215) {
							return;
						}

						var9 = func_148210_b(2);
					}

					var9 = ("" + I18n.format(var9, new Object[0])).trim();

					if (var9.length() > 0) {
						final int var6 = p_148142_1_ + 12;
						final int var7 = p_148142_2_ - 12;
						final int var8 = GuiStats.this.fontRendererObj.getStringWidth(var9);
						drawGradientRect(var6 - 3, var7 - 3, var6 + var8 + 3, var7 + 8 + 3, -1073741824, -1073741824);
						GuiStats.this.fontRendererObj.drawStringWithShadow(var9, var6, var7, -1);
					}
				}
			}
		}

		protected void func_148213_a(final StatCrafting p_148213_1_, final int p_148213_2_, final int p_148213_3_) {
			if (p_148213_1_ != null) {
				final Item var4 = p_148213_1_.func_150959_a();
				final ItemStack var5 = new ItemStack(var4);
				final String var6 = var5.getUnlocalizedName();
				final String var7 = ("" + I18n.format(var6 + ".name", new Object[0])).trim();

				if (var7.length() > 0) {
					final int var8 = p_148213_2_ + 12;
					final int var9 = p_148213_3_ - 12;
					final int var10 = GuiStats.this.fontRendererObj.getStringWidth(var7);
					drawGradientRect(var8 - 3, var9 - 3, var8 + var10 + 3, var9 + 8 + 3, -1073741824, -1073741824);
					GuiStats.this.fontRendererObj.drawStringWithShadow(var7, var8, var9, -1);
				}
			}
		}

		protected void func_148212_h(final int p_148212_1_) {
			if (p_148212_1_ != field_148217_o) {
				field_148217_o = p_148212_1_;
				field_148215_p = -1;
			} else if (field_148215_p == -1) {
				field_148215_p = 1;
			} else {
				field_148217_o = -1;
				field_148215_p = 0;
			}

			Collections.sort(statsHolder, statSorter);
		}
	}

	class StatsBlock extends GuiStats.Stats {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000724";

		public StatsBlock(final Minecraft mcIn) {
			super(mcIn);
			statsHolder = Lists.newArrayList();
			final Iterator var3 = StatList.objectMineStats.iterator();

			while (var3.hasNext()) {
				final StatCrafting var4 = (StatCrafting) var3.next();
				boolean var5 = false;
				final int var6 = Item.getIdFromItem(var4.func_150959_a());

				if (field_146546_t.writeStat(var4) > 0) {
					var5 = true;
				} else if (StatList.objectUseStats[var6] != null
						&& field_146546_t.writeStat(StatList.objectUseStats[var6]) > 0) {
					var5 = true;
				} else if (StatList.objectCraftStats[var6] != null
						&& field_146546_t.writeStat(StatList.objectCraftStats[var6]) > 0) {
					var5 = true;
				}

				if (var5) {
					statsHolder.add(var4);
				}
			}

			statSorter = new Comparator() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00000725";
				public int compare(final StatCrafting p_compare_1_, final StatCrafting p_compare_2_) {
					final int var3 = Item.getIdFromItem(p_compare_1_.func_150959_a());
					final int var4 = Item.getIdFromItem(p_compare_2_.func_150959_a());
					StatBase var5 = null;
					StatBase var6 = null;

					if (StatsBlock.this.field_148217_o == 2) {
						var5 = StatList.mineBlockStatArray[var3];
						var6 = StatList.mineBlockStatArray[var4];
					} else if (StatsBlock.this.field_148217_o == 0) {
						var5 = StatList.objectCraftStats[var3];
						var6 = StatList.objectCraftStats[var4];
					} else if (StatsBlock.this.field_148217_o == 1) {
						var5 = StatList.objectUseStats[var3];
						var6 = StatList.objectUseStats[var4];
					}

					if (var5 != null || var6 != null) {
						if (var5 == null) {
							return 1;
						}

						if (var6 == null) {
							return -1;
						}

						final int var7 = field_146546_t.writeStat(var5);
						final int var8 = field_146546_t.writeStat(var6);

						if (var7 != var8) {
							return (var7 - var8) * StatsBlock.this.field_148215_p;
						}
					}

					return var3 - var4;
				}

				@Override
				public int compare(final Object p_compare_1_, final Object p_compare_2_) {
					return this.compare((StatCrafting) p_compare_1_, (StatCrafting) p_compare_2_);
				}
			};
		}

		@Override
		protected void drawListHeader(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
			super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);

			if (field_148218_l == 0) {
				drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
			} else {
				drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
			}

			if (field_148218_l == 1) {
				drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
			} else {
				drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
			}

			if (field_148218_l == 2) {
				drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
			} else {
				drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
			}
		}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
			final StatCrafting var7 = func_148211_c(p_180791_1_);
			final Item var8 = var7.func_150959_a();
			drawStatsScreen(p_180791_2_ + 40, p_180791_3_, var8);
			final int var9 = Item.getIdFromItem(var8);
			func_148209_a(StatList.objectCraftStats[var9], p_180791_2_ + 115, p_180791_3_, p_180791_1_ % 2 == 0);
			func_148209_a(StatList.objectUseStats[var9], p_180791_2_ + 165, p_180791_3_, p_180791_1_ % 2 == 0);
			func_148209_a(var7, p_180791_2_ + 215, p_180791_3_, p_180791_1_ % 2 == 0);
		}

		@Override
		protected String func_148210_b(final int p_148210_1_) {
			return p_148210_1_ == 0 ? "stat.crafted" : p_148210_1_ == 1 ? "stat.used" : "stat.mined";
		}
	}

	class StatsGeneral extends GuiSlot {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000726";

		public StatsGeneral(final Minecraft mcIn) {
			super(mcIn, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 10);
			setShowSelectionBox(false);
		}

		@Override
		protected int getSize() {
			return StatList.generalStats.size();
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return false;
		}

		@Override
		protected int getContentHeight() {
			return getSize() * 10;
		}

		@Override
		protected void drawBackground() {
			drawDefaultBackground(mouseX, mouseY);
		}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
			final StatBase var7 = (StatBase) StatList.generalStats.get(p_180791_1_);
			drawString(GuiStats.this.fontRendererObj, var7.getStatName().getUnformattedText(), p_180791_2_ + 2,
					p_180791_3_ + 1, p_180791_1_ % 2 == 0 ? 16777215 : 9474192);
			final String var8 = var7.func_75968_a(field_146546_t.writeStat(var7));
			drawString(GuiStats.this.fontRendererObj, var8,
					p_180791_2_ + 2 + 213 - GuiStats.this.fontRendererObj.getStringWidth(var8), p_180791_3_ + 1,
					p_180791_1_ % 2 == 0 ? 16777215 : 9474192);
		}
	}

	class StatsItem extends GuiStats.Stats {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000727";

		public StatsItem(final Minecraft mcIn) {
			super(mcIn);
			statsHolder = Lists.newArrayList();
			final Iterator var3 = StatList.itemStats.iterator();

			while (var3.hasNext()) {
				final StatCrafting var4 = (StatCrafting) var3.next();
				boolean var5 = false;
				final int var6 = Item.getIdFromItem(var4.func_150959_a());

				if (field_146546_t.writeStat(var4) > 0) {
					var5 = true;
				} else if (StatList.objectBreakStats[var6] != null
						&& field_146546_t.writeStat(StatList.objectBreakStats[var6]) > 0) {
					var5 = true;
				} else if (StatList.objectCraftStats[var6] != null
						&& field_146546_t.writeStat(StatList.objectCraftStats[var6]) > 0) {
					var5 = true;
				}

				if (var5) {
					statsHolder.add(var4);
				}
			}

			statSorter = new Comparator() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00000728";
				public int compare(final StatCrafting p_compare_1_, final StatCrafting p_compare_2_) {
					final int var3 = Item.getIdFromItem(p_compare_1_.func_150959_a());
					final int var4 = Item.getIdFromItem(p_compare_2_.func_150959_a());
					StatBase var5 = null;
					StatBase var6 = null;

					if (StatsItem.this.field_148217_o == 0) {
						var5 = StatList.objectBreakStats[var3];
						var6 = StatList.objectBreakStats[var4];
					} else if (StatsItem.this.field_148217_o == 1) {
						var5 = StatList.objectCraftStats[var3];
						var6 = StatList.objectCraftStats[var4];
					} else if (StatsItem.this.field_148217_o == 2) {
						var5 = StatList.objectUseStats[var3];
						var6 = StatList.objectUseStats[var4];
					}

					if (var5 != null || var6 != null) {
						if (var5 == null) {
							return 1;
						}

						if (var6 == null) {
							return -1;
						}

						final int var7 = field_146546_t.writeStat(var5);
						final int var8 = field_146546_t.writeStat(var6);

						if (var7 != var8) {
							return (var7 - var8) * StatsItem.this.field_148215_p;
						}
					}

					return var3 - var4;
				}

				@Override
				public int compare(final Object p_compare_1_, final Object p_compare_2_) {
					return this.compare((StatCrafting) p_compare_1_, (StatCrafting) p_compare_2_);
				}
			};
		}

		@Override
		protected void drawListHeader(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
			super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);

			if (field_148218_l == 0) {
				drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 72, 18);
			} else {
				drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 72, 18);
			}

			if (field_148218_l == 1) {
				drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
			} else {
				drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 18, 18);
			}

			if (field_148218_l == 2) {
				drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
			} else {
				drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 36, 18);
			}
		}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
			final StatCrafting var7 = func_148211_c(p_180791_1_);
			final Item var8 = var7.func_150959_a();
			drawStatsScreen(p_180791_2_ + 40, p_180791_3_, var8);
			final int var9 = Item.getIdFromItem(var8);
			func_148209_a(StatList.objectBreakStats[var9], p_180791_2_ + 115, p_180791_3_, p_180791_1_ % 2 == 0);
			func_148209_a(StatList.objectCraftStats[var9], p_180791_2_ + 165, p_180791_3_, p_180791_1_ % 2 == 0);
			func_148209_a(var7, p_180791_2_ + 215, p_180791_3_, p_180791_1_ % 2 == 0);
		}

		@Override
		protected String func_148210_b(final int p_148210_1_) {
			return p_148210_1_ == 1 ? "stat.crafted" : p_148210_1_ == 2 ? "stat.used" : "stat.depleted";
		}
	}

	class StatsMobsList extends GuiSlot {
		private final List field_148222_l = Lists.newArrayList();
		// private static final String __OBFID =
		// "http://https://fuckuskid00000729";

		public StatsMobsList(final Minecraft mcIn) {
			super(mcIn, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64,
					GuiStats.this.fontRendererObj.FONT_HEIGHT * 4);
			setShowSelectionBox(false);
			final Iterator var3 = EntityList.entityEggs.values().iterator();

			while (var3.hasNext()) {
				final EntityList.EntityEggInfo var4 = (EntityList.EntityEggInfo) var3.next();

				if (field_146546_t.writeStat(var4.field_151512_d) > 0
						|| field_146546_t.writeStat(var4.field_151513_e) > 0) {
					field_148222_l.add(var4);
				}
			}
		}

		@Override
		protected int getSize() {
			return field_148222_l.size();
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return false;
		}

		@Override
		protected int getContentHeight() {
			return getSize() * GuiStats.this.fontRendererObj.FONT_HEIGHT * 4;
		}

		@Override
		protected void drawBackground() {
			drawDefaultBackground(mouseX, mouseY);
		}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
			final EntityList.EntityEggInfo var7 = (EntityList.EntityEggInfo) field_148222_l.get(p_180791_1_);
			final String var8 = I18n.format("entity." + EntityList.getStringFromID(var7.spawnedID) + ".name",
					new Object[0]);
			final int var9 = field_146546_t.writeStat(var7.field_151512_d);
			final int var10 = field_146546_t.writeStat(var7.field_151513_e);
			String var11 = I18n.format("stat.entityKills", new Object[] { var9, var8 });
			String var12 = I18n.format("stat.entityKilledBy", new Object[] { var8, var10});

			if (var9 == 0) {
				var11 = I18n.format("stat.entityKills.none", new Object[] { var8 });
			}

			if (var10 == 0) {
				var12 = I18n.format("stat.entityKilledBy.none", new Object[] { var8 });
			}

			drawString(GuiStats.this.fontRendererObj, var8, p_180791_2_ + 2 - 10, p_180791_3_ + 1, 16777215);
			drawString(GuiStats.this.fontRendererObj, var11, p_180791_2_ + 2,
					p_180791_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT, var9 == 0 ? 6316128 : 9474192);
			drawString(GuiStats.this.fontRendererObj, var12, p_180791_2_ + 2,
					p_180791_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT * 2, var10 == 0 ? 6316128 : 9474192);
		}
	}
}
