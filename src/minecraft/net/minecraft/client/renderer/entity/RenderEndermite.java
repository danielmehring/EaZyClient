package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.util.ResourceLocation;

public class RenderEndermite extends RenderLiving {

public static final int EaZy = 750;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_177108_a = new ResourceLocation("textures/entity/endermite.png");
	// private static final String __OBFID = "http://https://fuckuskid00002445";

	public RenderEndermite(final RenderManager p_i46181_1_) {
		super(p_i46181_1_, new ModelEnderMite(), 0.3F);
	}

	protected float func_177107_a(final EntityEndermite p_177107_1_) {
		return 180.0F;
	}

	protected ResourceLocation func_177106_b(final EntityEndermite p_177106_1_) {
		return field_177108_a;
	}

	@Override
	protected float getDeathMaxRotation(final EntityLivingBase p_77037_1_) {
		return func_177107_a((EntityEndermite) p_77037_1_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_177106_b((EntityEndermite) p_110775_1_);
	}
}
