package net.minecraft.client.gui.spectator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class SpectatorMenu {

public static final int EaZy = 558;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ISpectatorMenuObject field_178655_b = new SpectatorMenu.EndSpectatorObject(null);
	private static final ISpectatorMenuObject field_178656_c = new SpectatorMenu.MoveMenuObject(-1, true);
	private static final ISpectatorMenuObject field_178653_d = new SpectatorMenu.MoveMenuObject(1, true);
	private static final ISpectatorMenuObject field_178654_e = new SpectatorMenu.MoveMenuObject(1, false);
	public static final ISpectatorMenuObject field_178657_a = new ISpectatorMenuObject() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001926";
		@Override
		public void func_178661_a(final SpectatorMenu p_178661_1_) {}

		@Override
		public IChatComponent func_178664_z_() {
			return new ChatComponentText("");
		}

		@Override
		public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {}

		@Override
		public boolean func_178662_A_() {
			return false;
		}
	};
	private final ISpectatorMenuReciepient field_178651_f;
	private final List field_178652_g = Lists.newArrayList();
	private ISpectatorMenuView field_178659_h = new BaseSpectatorGroup();
	private int field_178660_i = -1;
	private int field_178658_j;
	// private static final String __OBFID = "http://https://fuckuskid00001927";

	public SpectatorMenu(final ISpectatorMenuReciepient p_i45497_1_) {
		field_178651_f = p_i45497_1_;
	}

	public ISpectatorMenuObject func_178643_a(final int p_178643_1_) {
		final int var2 = p_178643_1_ + field_178658_j * 6;
		return field_178658_j > 0 && p_178643_1_ == 0 ? field_178656_c
				: p_178643_1_ == 7 ? var2 < field_178659_h.func_178669_a().size() ? field_178653_d : field_178654_e
						: p_178643_1_ == 8 ? field_178655_b
								: var2 >= 0 && var2 < field_178659_h.func_178669_a().size()
										? (ISpectatorMenuObject) Objects
												.firstNonNull(field_178659_h.func_178669_a().get(var2), field_178657_a)
										: field_178657_a;
	}

	public List func_178642_a() {
		final ArrayList var1 = Lists.newArrayList();

		for (int var2 = 0; var2 <= 8; ++var2) {
			var1.add(func_178643_a(var2));
		}

		return var1;
	}

	public ISpectatorMenuObject func_178645_b() {
		return func_178643_a(field_178660_i);
	}

	public ISpectatorMenuView func_178650_c() {
		return field_178659_h;
	}

	public void func_178644_b(final int p_178644_1_) {
		final ISpectatorMenuObject var2 = func_178643_a(p_178644_1_);

		if (var2 != field_178657_a) {
			if (field_178660_i == p_178644_1_ && var2.func_178662_A_()) {
				var2.func_178661_a(this);
			} else {
				field_178660_i = p_178644_1_;
			}
		}
	}

	public void func_178641_d() {
		field_178651_f.func_175257_a(this);
	}

	public int func_178648_e() {
		return field_178660_i;
	}

	public void func_178647_a(final ISpectatorMenuView p_178647_1_) {
		field_178652_g.add(func_178646_f());
		field_178659_h = p_178647_1_;
		field_178660_i = -1;
		field_178658_j = 0;
	}

	public SpectatorDetails func_178646_f() {
		return new SpectatorDetails(field_178659_h, func_178642_a(), field_178660_i);
	}

	static class EndSpectatorObject implements ISpectatorMenuObject {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001925";

		private EndSpectatorObject() {}

		@Override
		public void func_178661_a(final SpectatorMenu p_178661_1_) {
			p_178661_1_.func_178641_d();
		}

		@Override
		public IChatComponent func_178664_z_() {
			return new ChatComponentText("Close menu");
		}

		@Override
		public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {

			Minecraft.getTextureManager().bindTexture(GuiSpectator.field_175269_a);
			Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
		}

		@Override
		public boolean func_178662_A_() {
			return true;
		}

		EndSpectatorObject(final Object p_i45496_1_) {
			this();
		}
	}

	static class MoveMenuObject implements ISpectatorMenuObject {
		private final int field_178666_a;
		private final boolean field_178665_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001924";

		public MoveMenuObject(final int p_i45495_1_, final boolean p_i45495_2_) {
			field_178666_a = p_i45495_1_;
			field_178665_b = p_i45495_2_;
		}

		@Override
		public void func_178661_a(final SpectatorMenu p_178661_1_) {
			p_178661_1_.field_178658_j = field_178666_a;
		}

		@Override
		public IChatComponent func_178664_z_() {
			return field_178666_a < 0 ? new ChatComponentText("Previous Page") : new ChatComponentText("Next Page");
		}

		@Override
		public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {

			Minecraft.getTextureManager().bindTexture(GuiSpectator.field_175269_a);

			if (field_178666_a < 0) {
				Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
			} else {
				Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
			}
		}

		@Override
		public boolean func_178662_A_() {
			return field_178665_b;
		}
	}
}
