package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;

public class RenderVillager extends RenderLiving {

public static final int EaZy = 788;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation villagerTextures = new ResourceLocation(
			"textures/entity/villager/villager.png");
	private static final ResourceLocation farmerVillagerTextures = new ResourceLocation(
			"textures/entity/villager/farmer.png");
	private static final ResourceLocation librarianVillagerTextures = new ResourceLocation(
			"textures/entity/villager/librarian.png");
	private static final ResourceLocation priestVillagerTextures = new ResourceLocation(
			"textures/entity/villager/priest.png");
	private static final ResourceLocation smithVillagerTextures = new ResourceLocation(
			"textures/entity/villager/smith.png");
	private static final ResourceLocation butcherVillagerTextures = new ResourceLocation(
			"textures/entity/villager/butcher.png");
	// private static final String __OBFID = "http://https://fuckuskid00001032";

	public RenderVillager(final RenderManager p_i46132_1_) {
		super(p_i46132_1_, new ModelVillager(0.0F), 0.5F);
		addLayer(new LayerCustomHead(func_177134_g().villagerHead));
	}

	public ModelVillager func_177134_g() {
		return (ModelVillager) super.getMainModel();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityVillager p_110775_1_) {
		switch (p_110775_1_.getProfession()) {
			case 0:
				return farmerVillagerTextures;

			case 1:
				return librarianVillagerTextures;

			case 2:
				return priestVillagerTextures;

			case 3:
				return smithVillagerTextures;

			case 4:
				return butcherVillagerTextures;

			default:
				return villagerTextures;
		}
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(final EntityVillager p_77041_1_, final float p_77041_2_) {
		float var3 = 0.9375F;

		if (p_77041_1_.getGrowingAge() < 0) {
			var3 = (float) (var3 * 0.5D);
			shadowSize = 0.25F;
		} else {
			shadowSize = 0.5F;
		}

		GlStateManager.scale(var3, var3, var3);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		this.preRenderCallback((EntityVillager) p_77041_1_, p_77041_2_);
	}

	@Override
	public ModelBase getMainModel() {
		return func_177134_g();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityVillager) p_110775_1_);
	}
}
