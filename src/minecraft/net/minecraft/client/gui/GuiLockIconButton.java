package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLockIconButton extends GuiButton {

public static final int EaZy = 486;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private boolean field_175231_o = false;
	// private static final String __OBFID = "http://https://fuckuskid00001952";

	public GuiLockIconButton(final int p_i45538_1_, final int p_i45538_2_, final int p_i45538_3_) {
		super(p_i45538_1_, p_i45538_2_, p_i45538_3_, 20, 20, "");
	}

	public boolean func_175230_c() {
		return field_175231_o;
	}

	public void func_175229_b(final boolean p_175229_1_) {
		field_175231_o = p_175229_1_;
	}

	/**
	 * Draws this button to the screen.
	 */
	@Override
	public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
		if (visible) {
			Minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			final boolean var4 = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width
					&& mouseY < yPosition + height;
			GuiLockIconButton.Icon var5;

			if (field_175231_o) {
				if (!enabled) {
					var5 = GuiLockIconButton.Icon.LOCKED_DISABLED;
				} else if (var4) {
					var5 = GuiLockIconButton.Icon.LOCKED_HOVER;
				} else {
					var5 = GuiLockIconButton.Icon.LOCKED;
				}
			} else if (!enabled) {
				var5 = GuiLockIconButton.Icon.UNLOCKED_DISABLED;
			} else if (var4) {
				var5 = GuiLockIconButton.Icon.UNLOCKED_HOVER;
			} else {
				var5 = GuiLockIconButton.Icon.UNLOCKED;
			}

			drawTexturedModalRect(xPosition, yPosition, var5.func_178910_a(), var5.func_178912_b(), width, height);
		}
	}

	static enum Icon {
		LOCKED("LOCKED", 0, 0, 146), LOCKED_HOVER("LOCKED_HOVER", 1, 0, 166), LOCKED_DISABLED("LOCKED_DISABLED", 2, 0,
				186), UNLOCKED("UNLOCKED", 3, 20, 146), UNLOCKED_HOVER("UNLOCKED_HOVER", 4, 20,
						166), UNLOCKED_DISABLED("UNLOCKED_DISABLED", 5, 20, 186);
		private final int field_178914_g;
		private final int field_178920_h;

		private Icon(final String p_i45537_1_, final int p_i45537_2_, final int p_i45537_3_, final int p_i45537_4_) {
			field_178914_g = p_i45537_3_;
			field_178920_h = p_i45537_4_;
		}

		public int func_178910_a() {
			return field_178914_g;
		}

		public int func_178912_b() {
			return field_178920_h;
		}
	}
}
