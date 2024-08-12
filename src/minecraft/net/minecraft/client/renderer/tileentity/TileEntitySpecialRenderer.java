package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class TileEntitySpecialRenderer {

public static final int EaZy = 844;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[] {
			new ResourceLocation("textures/blocks/destroy_stage_0.png"),
			new ResourceLocation("textures/blocks/destroy_stage_1.png"),
			new ResourceLocation("textures/blocks/destroy_stage_2.png"),
			new ResourceLocation("textures/blocks/destroy_stage_3.png"),
			new ResourceLocation("textures/blocks/destroy_stage_4.png"),
			new ResourceLocation("textures/blocks/destroy_stage_5.png"),
			new ResourceLocation("textures/blocks/destroy_stage_6.png"),
			new ResourceLocation("textures/blocks/destroy_stage_7.png"),
			new ResourceLocation("textures/blocks/destroy_stage_8.png"),
			new ResourceLocation("textures/blocks/destroy_stage_9.png") };
	protected TileEntityRendererDispatcher rendererDispatcher;
	// private static final String __OBFID = "http://https://fuckuskid00000964";

	public abstract void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8,
			int var9);

	protected void bindTexture(final ResourceLocation p_147499_1_) {
		final TextureManager var2 = rendererDispatcher.renderEngine;

		if (var2 != null) {
			var2.bindTexture(p_147499_1_);
		}
	}

	protected World getWorld() {
		return rendererDispatcher.worldObj;
	}

	public void setRendererDispatcher(final TileEntityRendererDispatcher p_147497_1_) {
		rendererDispatcher = p_147497_1_;
	}

	public FontRenderer getFontRenderer() {
		return rendererDispatcher.getFontRenderer();
	}
}
