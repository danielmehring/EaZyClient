package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IntHashMap;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

public class GuiPageButtonList extends GuiListExtended {

public static final int EaZy = 497;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final List field_178074_u = Lists.newArrayList();
	private final IntHashMap field_178073_v = new IntHashMap();
	private final List field_178072_w = Lists.newArrayList();
	private final GuiPageButtonList.GuiListEntry[][] field_178078_x;
	private int field_178077_y;
	private final GuiPageButtonList.GuiResponder field_178076_z;
	private Gui field_178075_A;
	// private static final String __OBFID = "http://https://fuckuskid00001950";

	public GuiPageButtonList(final Minecraft mcIn, final int p_i45536_2_, final int p_i45536_3_, final int p_i45536_4_,
			final int p_i45536_5_, final int p_i45536_6_, final GuiPageButtonList.GuiResponder p_i45536_7_,
			final GuiPageButtonList.GuiListEntry[]... p_i45536_8_) {
		super(mcIn, p_i45536_2_, p_i45536_3_, p_i45536_4_, p_i45536_5_, p_i45536_6_);
		field_178076_z = p_i45536_7_;
		field_178078_x = p_i45536_8_;
		field_148163_i = false;
		func_178069_s();
		func_178055_t();
	}

	private void func_178069_s() {
		final GuiPageButtonList.GuiListEntry[][] var1 = field_178078_x;
		final int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			final GuiPageButtonList.GuiListEntry[] var4 = var1[var3];

			for (int var5 = 0; var5 < var4.length; var5 += 2) {
				final GuiPageButtonList.GuiListEntry var6 = var4[var5];
				final GuiPageButtonList.GuiListEntry var7 = var5 < var4.length - 1 ? var4[var5 + 1] : null;
				final Gui var8 = func_178058_a(var6, 0, var7 == null);
				final Gui var9 = func_178058_a(var7, 160, var6 == null);
				final GuiPageButtonList.GuiEntry var10 = new GuiPageButtonList.GuiEntry(var8, var9);
				field_178074_u.add(var10);

				if (var6 != null && var8 != null) {
					field_178073_v.addKey(var6.func_178935_b(), var8);

					if (var8 instanceof GuiTextField) {
						field_178072_w.add(var8);
					}
				}

				if (var7 != null && var9 != null) {
					field_178073_v.addKey(var7.func_178935_b(), var9);

					if (var9 instanceof GuiTextField) {
						field_178072_w.add(var9);
					}
				}
			}
		}
	}

	private void func_178055_t() {
		field_178074_u.clear();

		for (int var1 = 0; var1 < field_178078_x[field_178077_y].length; var1 += 2) {
			final GuiPageButtonList.GuiListEntry var2 = field_178078_x[field_178077_y][var1];
			final GuiPageButtonList.GuiListEntry var3 = var1 < field_178078_x[field_178077_y].length - 1
					? field_178078_x[field_178077_y][var1 + 1] : null;
			final Gui var4 = (Gui) field_178073_v.lookup(var2.func_178935_b());
			final Gui var5 = var3 != null ? (Gui) field_178073_v.lookup(var3.func_178935_b()) : null;
			final GuiPageButtonList.GuiEntry var6 = new GuiPageButtonList.GuiEntry(var4, var5);
			field_178074_u.add(var6);
		}
	}

	public int func_178059_e() {
		return field_178077_y;
	}

	public int func_178057_f() {
		return field_178078_x.length;
	}

	public Gui func_178056_g() {
		return field_178075_A;
	}

	public void func_178071_h() {
		if (field_178077_y > 0) {
			final int var1 = field_178077_y--;
			func_178055_t();
			func_178060_e(var1, field_178077_y);
			amountScrolled = 0.0F;
		}
	}

	public void func_178064_i() {
		if (field_178077_y < field_178078_x.length - 1) {
			final int var1 = field_178077_y++;
			func_178055_t();
			func_178060_e(var1, field_178077_y);
			amountScrolled = 0.0F;
		}
	}

	public Gui func_178061_c(final int p_178061_1_) {
		return (Gui) field_178073_v.lookup(p_178061_1_);
	}

	private void func_178060_e(final int p_178060_1_, final int p_178060_2_) {
		GuiPageButtonList.GuiListEntry[] var3 = field_178078_x[p_178060_1_];
		int var4 = var3.length;
		int var5;
		GuiPageButtonList.GuiListEntry var6;

		for (var5 = 0; var5 < var4; ++var5) {
			var6 = var3[var5];

			if (var6 != null) {
				func_178066_a((Gui) field_178073_v.lookup(var6.func_178935_b()), false);
			}
		}

		var3 = field_178078_x[p_178060_2_];
		var4 = var3.length;

		for (var5 = 0; var5 < var4; ++var5) {
			var6 = var3[var5];

			if (var6 != null) {
				func_178066_a((Gui) field_178073_v.lookup(var6.func_178935_b()), true);
			}
		}
	}

	private void func_178066_a(final Gui p_178066_1_, final boolean p_178066_2_) {
		if (p_178066_1_ instanceof GuiButton) {
			((GuiButton) p_178066_1_).visible = p_178066_2_;
		} else if (p_178066_1_ instanceof GuiTextField) {
			((GuiTextField) p_178066_1_).setVisible(p_178066_2_);
		} else if (p_178066_1_ instanceof GuiLabel) {
			((GuiLabel) p_178066_1_).visible = p_178066_2_;
		}
	}

	private Gui func_178058_a(final GuiPageButtonList.GuiListEntry p_178058_1_, final int p_178058_2_,
			final boolean p_178058_3_) {
		return p_178058_1_ instanceof GuiPageButtonList.GuiSlideEntry
				? func_178067_a(width / 2 - 155 + p_178058_2_, 0, (GuiPageButtonList.GuiSlideEntry) p_178058_1_)
				: p_178058_1_ instanceof GuiPageButtonList.GuiButtonEntry
						? func_178065_a(width / 2 - 155 + p_178058_2_, 0,
								(GuiPageButtonList.GuiButtonEntry) p_178058_1_)
						: p_178058_1_ instanceof GuiPageButtonList.EditBoxEntry
								? func_178068_a(width / 2 - 155 + p_178058_2_, 0,
										(GuiPageButtonList.EditBoxEntry) p_178058_1_)
								: p_178058_1_ instanceof GuiPageButtonList.GuiLabelEntry
										? func_178063_a(width / 2 - 155 + p_178058_2_, 0,
												(GuiPageButtonList.GuiLabelEntry) p_178058_1_, p_178058_3_)
										: null;
	}

	@Override
	public boolean func_148179_a(final int p_148179_1_, final int p_148179_2_, final int p_148179_3_) {
		final boolean var4 = super.func_148179_a(p_148179_1_, p_148179_2_, p_148179_3_);
		final int var5 = getSlotIndexFromScreenCoords(p_148179_1_, p_148179_2_);

		if (var5 >= 0) {
			final GuiPageButtonList.GuiEntry var6 = func_178070_d(var5);

			if (field_178075_A != var6.field_178028_d && field_178075_A != null
					&& field_178075_A instanceof GuiTextField) {
				((GuiTextField) field_178075_A).setFocused(false);
			}

			field_178075_A = var6.field_178028_d;
		}

		return var4;
	}

	private GuiSlider func_178067_a(final int p_178067_1_, final int p_178067_2_,
			final GuiPageButtonList.GuiSlideEntry p_178067_3_) {
		final GuiSlider var4 = new GuiSlider(field_178076_z, p_178067_3_.func_178935_b(), p_178067_1_, p_178067_2_,
				p_178067_3_.func_178936_c(), p_178067_3_.func_178943_e(), p_178067_3_.func_178944_f(),
				p_178067_3_.func_178942_g(), p_178067_3_.func_178945_a());
		var4.visible = p_178067_3_.func_178934_d();
		return var4;
	}

	private GuiListButton func_178065_a(final int p_178065_1_, final int p_178065_2_,
			final GuiPageButtonList.GuiButtonEntry p_178065_3_) {
		final GuiListButton var4 = new GuiListButton(field_178076_z, p_178065_3_.func_178935_b(), p_178065_1_,
				p_178065_2_, p_178065_3_.func_178936_c(), p_178065_3_.func_178940_a());
		var4.visible = p_178065_3_.func_178934_d();
		return var4;
	}

	private GuiTextField func_178068_a(final int p_178068_1_, final int p_178068_2_,
			final GuiPageButtonList.EditBoxEntry p_178068_3_) {
		final GuiTextField var4 = new GuiTextField(p_178068_3_.func_178935_b(), mc.fontRendererObj, p_178068_1_,
				p_178068_2_, 150, 20);
		var4.setText(p_178068_3_.func_178936_c());
		var4.func_175207_a(field_178076_z);
		var4.setVisible(p_178068_3_.func_178934_d());
		var4.func_175205_a(p_178068_3_.func_178950_a());
		return var4;
	}

	private GuiLabel func_178063_a(final int p_178063_1_, final int p_178063_2_,
			final GuiPageButtonList.GuiLabelEntry p_178063_3_, final boolean p_178063_4_) {
		GuiLabel var5;

		if (p_178063_4_) {
			var5 = new GuiLabel(mc.fontRendererObj, p_178063_3_.func_178935_b(), p_178063_1_, p_178063_2_,
					width - p_178063_1_ * 2, 20, -1);
		} else {
			var5 = new GuiLabel(mc.fontRendererObj, p_178063_3_.func_178935_b(), p_178063_1_, p_178063_2_, 150, 20, -1);
		}

		var5.visible = p_178063_3_.func_178934_d();
		var5.func_175202_a(p_178063_3_.func_178936_c());
		var5.func_175203_a();
		return var5;
	}

	public void func_178062_a(final char p_178062_1_, final int p_178062_2_) {
		if (field_178075_A instanceof GuiTextField) {
			GuiTextField var3 = (GuiTextField) field_178075_A;
			int var6;

			if (!GuiScreen.func_175279_e(p_178062_2_)) {
				if (p_178062_2_ == 15) {
					var3.setFocused(false);
					int var12 = field_178072_w.indexOf(field_178075_A);

					if (GuiScreen.isShiftKeyDown()) {
						if (var12 == 0) {
							var12 = field_178072_w.size() - 1;
						} else {
							--var12;
						}
					} else if (var12 == field_178072_w.size() - 1) {
						var12 = 0;
					} else {
						++var12;
					}

					field_178075_A = (Gui) field_178072_w.get(var12);
					var3 = (GuiTextField) field_178075_A;
					var3.setFocused(true);
					final int var13 = var3.yPosition + slotHeight;
					var6 = var3.yPosition;

					if (var13 > bottom) {
						amountScrolled += var13 - bottom;
					} else if (var6 < top) {
						amountScrolled = var6;
					}
				} else {
					var3.textboxKeyTyped(p_178062_1_, p_178062_2_);
				}
			} else {
				final String var4 = GuiScreen.getClipboardString();
				final String[] var5 = var4.split(";");
				var6 = field_178072_w.indexOf(field_178075_A);
				int var7 = var6;
				final String[] var8 = var5;
				final int var9 = var5.length;

				for (int var10 = 0; var10 < var9; ++var10) {
					final String var11 = var8[var10];
					((GuiTextField) field_178072_w.get(var7)).setText(var11);

					if (var7 == field_178072_w.size() - 1) {
						var7 = 0;
					} else {
						++var7;
					}

					if (var7 == var6) {
						break;
					}
				}
			}
		}
	}

	public GuiPageButtonList.GuiEntry func_178070_d(final int p_178070_1_) {
		return (GuiPageButtonList.GuiEntry) field_178074_u.get(p_178070_1_);
	}

	@Override
	public int getSize() {
		return field_178074_u.size();
	}

	/**
	 * Gets the width of the list
	 */
	@Override
	public int getListWidth() {
		return 400;
	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 32;
	}

	/**
	 * Gets the IGuiListEntry object for the given index
	 */
	@Override
	public GuiListExtended.IGuiListEntry getListEntry(final int p_148180_1_) {
		return func_178070_d(p_148180_1_);
	}

	public static class EditBoxEntry extends GuiPageButtonList.GuiListEntry {
		private final Predicate field_178951_a;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001948";

		public EditBoxEntry(final int p_i45534_1_, final String p_i45534_2_, final boolean p_i45534_3_,
				final Predicate p_i45534_4_) {
			super(p_i45534_1_, p_i45534_2_, p_i45534_3_);
			field_178951_a = Objects.firstNonNull(p_i45534_4_, Predicates.alwaysTrue());
		}

		public Predicate func_178950_a() {
			return field_178951_a;
		}
	}

	public static class GuiButtonEntry extends GuiPageButtonList.GuiListEntry {
		private final boolean field_178941_a;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001949";

		public GuiButtonEntry(final int p_i45535_1_, final String p_i45535_2_, final boolean p_i45535_3_,
				final boolean p_i45535_4_) {
			super(p_i45535_1_, p_i45535_2_, p_i45535_3_);
			field_178941_a = p_i45535_4_;
		}

		public boolean func_178940_a() {
			return field_178941_a;
		}
	}

	public static class GuiEntry implements GuiListExtended.IGuiListEntry {
		private final Minecraft field_178031_a = Minecraft.getMinecraft();
		private final Gui field_178029_b;
		private final Gui field_178030_c;
		private Gui field_178028_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001947";

		public GuiEntry(final Gui p_i45533_1_, final Gui p_i45533_2_) {
			field_178029_b = p_i45533_1_;
			field_178030_c = p_i45533_2_;
		}

		public Gui func_178022_a() {
			return field_178029_b;
		}

		public Gui func_178021_b() {
			return field_178030_c;
		}

		@Override
		public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight,
				final int mouseX, final int mouseY, final boolean isSelected) {
			func_178017_a(field_178029_b, y, mouseX, mouseY, false);
			func_178017_a(field_178030_c, y, mouseX, mouseY, false);
		}

		private void func_178017_a(final Gui p_178017_1_, final int p_178017_2_, final int p_178017_3_,
				final int p_178017_4_, final boolean p_178017_5_) {
			if (p_178017_1_ != null) {
				if (p_178017_1_ instanceof GuiButton) {
					func_178024_a((GuiButton) p_178017_1_, p_178017_2_, p_178017_3_, p_178017_4_, p_178017_5_);
				} else if (p_178017_1_ instanceof GuiTextField) {
					func_178027_a((GuiTextField) p_178017_1_, p_178017_2_, p_178017_5_);
				} else if (p_178017_1_ instanceof GuiLabel) {
					func_178025_a((GuiLabel) p_178017_1_, p_178017_2_, p_178017_3_, p_178017_4_, p_178017_5_);
				}
			}
		}

		private void func_178024_a(final GuiButton p_178024_1_, final int p_178024_2_, final int p_178024_3_,
				final int p_178024_4_, final boolean p_178024_5_) {
			p_178024_1_.yPosition = p_178024_2_;

			if (!p_178024_5_) {
				p_178024_1_.drawButton(field_178031_a, p_178024_3_, p_178024_4_);
			}
		}

		private void func_178027_a(final GuiTextField p_178027_1_, final int p_178027_2_, final boolean p_178027_3_) {
			p_178027_1_.yPosition = p_178027_2_;

			if (!p_178027_3_) {
				p_178027_1_.drawTextBox();
			}
		}

		private void func_178025_a(final GuiLabel p_178025_1_, final int p_178025_2_, final int p_178025_3_,
				final int p_178025_4_, final boolean p_178025_5_) {
			p_178025_1_.field_146174_h = p_178025_2_;

			if (!p_178025_5_) {
				p_178025_1_.drawLabel(field_178031_a, p_178025_3_, p_178025_4_);
			}
		}

		@Override
		public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
			func_178017_a(field_178029_b, p_178011_3_, 0, 0, true);
			func_178017_a(field_178030_c, p_178011_3_, 0, 0, true);
		}

		@Override
		public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_,
				final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
			final boolean var7 = func_178026_a(field_178029_b, p_148278_2_, p_148278_3_, p_148278_4_);
			final boolean var8 = func_178026_a(field_178030_c, p_148278_2_, p_148278_3_, p_148278_4_);
			return var7 || var8;
		}

		private boolean func_178026_a(final Gui p_178026_1_, final int p_178026_2_, final int p_178026_3_,
				final int p_178026_4_) {
			if (p_178026_1_ == null) {
				return false;
			} else if (p_178026_1_ instanceof GuiButton) {
				return func_178023_a((GuiButton) p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
			} else {
				if (p_178026_1_ instanceof GuiTextField) {
					func_178018_a((GuiTextField) p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
				}

				return false;
			}
		}

		private boolean func_178023_a(final GuiButton p_178023_1_, final int p_178023_2_, final int p_178023_3_,
				final int p_178023_4_) {
			final boolean var5 = p_178023_1_.mousePressed(field_178031_a, p_178023_2_, p_178023_3_);

			if (var5) {
				field_178028_d = p_178023_1_;
			}

			return var5;
		}

		private void func_178018_a(final GuiTextField p_178018_1_, final int p_178018_2_, final int p_178018_3_,
				final int p_178018_4_) {
			p_178018_1_.mouseClicked(p_178018_2_, p_178018_3_, p_178018_4_);

			if (p_178018_1_.isFocused()) {
				field_178028_d = p_178018_1_;
			}
		}

		@Override
		public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent,
				final int relativeX, final int relativeY) {
			func_178016_b(field_178029_b, x, y, mouseEvent);
			func_178016_b(field_178030_c, x, y, mouseEvent);
		}

		private void func_178016_b(final Gui p_178016_1_, final int p_178016_2_, final int p_178016_3_,
				final int p_178016_4_) {
			if (p_178016_1_ != null) {
				if (p_178016_1_ instanceof GuiButton) {
					func_178019_b((GuiButton) p_178016_1_, p_178016_2_, p_178016_3_, p_178016_4_);
				}
			}
		}

		private void func_178019_b(final GuiButton p_178019_1_, final int p_178019_2_, final int p_178019_3_,
				final int p_178019_4_) {
			p_178019_1_.mouseReleased(p_178019_2_, p_178019_3_);
		}
	}

	public static class GuiLabelEntry extends GuiPageButtonList.GuiListEntry {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001946";

		public GuiLabelEntry(final int p_i45532_1_, final String p_i45532_2_, final boolean p_i45532_3_) {
			super(p_i45532_1_, p_i45532_2_, p_i45532_3_);
		}
	}

	public static class GuiListEntry {
		private final int field_178939_a;
		private final String field_178937_b;
		private final boolean field_178938_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001945";

		public GuiListEntry(final int p_i45531_1_, final String p_i45531_2_, final boolean p_i45531_3_) {
			field_178939_a = p_i45531_1_;
			field_178937_b = p_i45531_2_;
			field_178938_c = p_i45531_3_;
		}

		public int func_178935_b() {
			return field_178939_a;
		}

		public String func_178936_c() {
			return field_178937_b;
		}

		public boolean func_178934_d() {
			return field_178938_c;
		}
	}

	public interface GuiResponder {
		void func_175321_a(int var1, boolean var2);

		void func_175320_a(int var1, float var2);

		void func_175319_a(int var1, String var2);
	}

	public static class GuiSlideEntry extends GuiPageButtonList.GuiListEntry {
		private final GuiSlider.FormatHelper field_178949_a;
		private final float field_178947_b;
		private final float field_178948_c;
		private final float field_178946_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001944";

		public GuiSlideEntry(final int p_i45530_1_, final String p_i45530_2_, final boolean p_i45530_3_,
				final GuiSlider.FormatHelper p_i45530_4_, final float p_i45530_5_, final float p_i45530_6_,
				final float p_i45530_7_) {
			super(p_i45530_1_, p_i45530_2_, p_i45530_3_);
			field_178949_a = p_i45530_4_;
			field_178947_b = p_i45530_5_;
			field_178948_c = p_i45530_6_;
			field_178946_d = p_i45530_7_;
		}

		public GuiSlider.FormatHelper func_178945_a() {
			return field_178949_a;
		}

		public float func_178943_e() {
			return field_178947_b;
		}

		public float func_178944_f() {
			return field_178948_c;
		}

		public float func_178942_g() {
			return field_178946_d;
		}
	}
}
