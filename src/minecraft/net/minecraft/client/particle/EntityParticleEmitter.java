package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityParticleEmitter extends EntityFX {

public static final int EaZy = 659;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Entity field_174851_a;
	private int field_174852_ax;
	private final int field_174850_ay;
	private final EnumParticleTypes field_174849_az;
	// private static final String __OBFID = "http://https://fuckuskid00002574";

	public EntityParticleEmitter(final World worldIn, final Entity p_i46279_2_, final EnumParticleTypes p_i46279_3_) {
		super(worldIn, p_i46279_2_.posX, p_i46279_2_.getEntityBoundingBox().minY + p_i46279_2_.height / 2.0F,
				p_i46279_2_.posZ, p_i46279_2_.motionX, p_i46279_2_.motionY, p_i46279_2_.motionZ);
		field_174851_a = p_i46279_2_;
		field_174850_ay = 3;
		field_174849_az = p_i46279_3_;
		onUpdate();
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		for (int var1 = 0; var1 < 16; ++var1) {
			final double var2 = rand.nextFloat() * 2.0F - 1.0F;
			final double var4 = rand.nextFloat() * 2.0F - 1.0F;
			final double var6 = rand.nextFloat() * 2.0F - 1.0F;

			if (var2 * var2 + var4 * var4 + var6 * var6 <= 1.0D) {
				final double var8 = field_174851_a.posX + var2 * field_174851_a.width / 4.0D;
				final double var10 = field_174851_a.getEntityBoundingBox().minY + field_174851_a.height / 2.0F
						+ var4 * field_174851_a.height / 4.0D;
				final double var12 = field_174851_a.posZ + var6 * field_174851_a.width / 4.0D;
				worldObj.spawnParticle(field_174849_az, false, var8, var10, var12, var2, var4 + 0.2D, var6, new int[0]);
			}
		}

		++field_174852_ax;

		if (field_174852_ax >= field_174850_ay) {
			setDead();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}
}
