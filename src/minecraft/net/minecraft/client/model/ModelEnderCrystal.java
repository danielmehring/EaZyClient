package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelEnderCrystal extends ModelBase {

public static final int EaZy = 582;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The cube model for the Ender Crystal. */
	private final ModelRenderer cube;

	/** The glass model for the Ender Crystal. */
	private final ModelRenderer glass = new ModelRenderer(this, "glass");

	/** The base model for the Ender Crystal. */
	private ModelRenderer base;
	// private static final String __OBFID = "http://https://fuckuskid00000871";

	public ModelEnderCrystal(final float p_i1170_1_, final boolean p_i1170_2_) {
		glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
		cube = new ModelRenderer(this, "cube");
		cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);

		if (p_i1170_2_) {
			base = new ModelRenderer(this, "base");
			base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		GlStateManager.translate(0.0F, -0.5F, 0.0F);

		if (base != null) {
			base.render(p_78088_7_);
		}

		GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, 0.8F + p_78088_4_, 0.0F);
		GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
		glass.render(p_78088_7_);
		final float var8 = 0.875F;
		GlStateManager.scale(var8, var8, var8);
		GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
		GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
		glass.render(p_78088_7_);
		GlStateManager.scale(var8, var8, var8);
		GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
		GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
		cube.render(p_78088_7_);
		GlStateManager.popMatrix();
	}
}
