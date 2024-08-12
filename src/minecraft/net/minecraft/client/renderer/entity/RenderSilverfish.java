package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;

public class RenderSilverfish extends RenderLiving {

public static final int EaZy = 779;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
	// private static final String __OBFID = "http://https://fuckuskid00001022";

	public RenderSilverfish(final RenderManager p_i46144_1_) {
		super(p_i46144_1_, new ModelSilverfish(), 0.3F);
	}

	protected float func_180584_a(final EntitySilverfish p_180584_1_) {
		return 180.0F;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntitySilverfish p_110775_1_) {
		return silverfishTextures;
	}

	@Override
	protected float getDeathMaxRotation(final EntityLivingBase p_77037_1_) {
		return func_180584_a((EntitySilverfish) p_77037_1_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntitySilverfish) p_110775_1_);
	}
}
