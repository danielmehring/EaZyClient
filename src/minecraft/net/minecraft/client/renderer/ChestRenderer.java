package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.item.ItemStack;

public class ChestRenderer {

public static final int EaZy = 695;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public void func_178175_a(final Block p_178175_1_, final float p_178175_2_) {
		GlStateManager.color(p_178175_2_, p_178175_2_, p_178175_2_, 1.0F);
		GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
		TileEntityRendererChestHelper.instance.renderByItem(new ItemStack(p_178175_1_));
	}
}
