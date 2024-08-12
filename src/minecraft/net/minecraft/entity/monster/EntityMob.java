package net.minecraft.entity.monster;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public abstract class EntityMob extends EntityCreature implements IMob {

public static final int EaZy = 1162;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final EntityAIBase field_175455_a = new EntityAIAvoidEntity(this, new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002208";
		public boolean func_179911_a(final Entity p_179911_1_) {
			return p_179911_1_ instanceof EntityCreeper && ((EntityCreeper) p_179911_1_).getCreeperState() > 0;
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_179911_a((Entity) p_apply_1_);
		}
	}, 4.0F, 1.0D, 2.0D);
	// private static final String __OBFID = "http://https://fuckuskid00001692";

	public EntityMob(final World worldIn) {
		super(worldIn);
		experienceValue = 5;
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		updateArmSwingProgress();
		final float var1 = getBrightness(1.0F);

		if (var1 > 0.5F) {
			entityAge += 2;
		}

		super.onLivingUpdate();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote && worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
			setDead();
		}
	}

	@Override
	protected String getSwimSound() {
		return "game.hostile.swim";
	}

	@Override
	protected String getSplashSound() {
		return "game.hostile.swim.splash";
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else if (super.attackEntityFrom(source, amount)) {
			final Entity var3 = source.getEntity();
			return riddenByEntity != var3 && ridingEntity != var3 ? true : true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "game.hostile.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "game.hostile.die";
	}

	@Override
	protected String func_146067_o(final int p_146067_1_) {
		return p_146067_1_ > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
	}

	@Override
	public boolean attackEntityAsMob(final Entity p_70652_1_) {
		float var2 = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		int var3 = 0;

		if (p_70652_1_ instanceof EntityLivingBase) {
			var2 += EnchantmentHelper.func_152377_a(getHeldItem(),
					((EntityLivingBase) p_70652_1_).getCreatureAttribute());
			var3 += EnchantmentHelper.getRespiration(this);
		}

		final boolean var4 = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), var2);

		if (var4) {
			if (var3 > 0) {
				p_70652_1_.addVelocity(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F, 0.1D,
						MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F);
				motionX *= 0.6D;
				motionZ *= 0.6D;
			}

			final int var5 = EnchantmentHelper.getFireAspectModifier(this);

			if (var5 > 0) {
				p_70652_1_.setFire(var5 * 4);
			}

			func_174815_a(this, p_70652_1_);
		}

		return var4;
	}

	@Override
	public float func_180484_a(final BlockPos p_180484_1_) {
		return 0.5F - worldObj.getLightBrightness(p_180484_1_);
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	protected boolean isValidLightLevel() {
		final BlockPos var1 = new BlockPos(posX, getEntityBoundingBox().minY, posZ);

		if (worldObj.getLightFor(EnumSkyBlock.SKY, var1) > rand.nextInt(32)) {
			return false;
		} else {
			int var2 = worldObj.getLightFromNeighbors(var1);

			if (worldObj.isThundering()) {
				final int var3 = worldObj.getSkylightSubtracted();
				worldObj.setSkylightSubtracted(10);
				var2 = worldObj.getLightFromNeighbors(var1);
				worldObj.setSkylightSubtracted(var3);
			}

			return var2 <= rand.nextInt(8);
		}
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && isValidLightLevel() && super.getCanSpawnHere();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
	}

	@Override
	protected boolean func_146066_aG() {
		return true;
	}
}
