package net.minecraft.entity;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityCreature extends EntityLiving {

public static final int EaZy = 1111;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final UUID field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
	public static final AttributeModifier field_110181_i = new AttributeModifier(field_110179_h, "Fleeing speed bonus",
			2.0D, 2).setSaved(false);
	private BlockPos homePosition;

	/** If -1 there is no maximum distance */
	private float maximumHomeDistance;
	private final EntityAIBase aiBase;
	private boolean field_110180_bt;
	// private static final String __OBFID = "http://https://fuckuskid00001558";

	public EntityCreature(final World worldIn) {
		super(worldIn);
		homePosition = BlockPos.ORIGIN;
		maximumHomeDistance = -1.0F;
		aiBase = new EntityAIMoveTowardsRestriction(this, 1.0D);
	}

	public float func_180484_a(final BlockPos p_180484_1_) {
		return 0.0F;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && func_180484_a(new BlockPos(posX, getEntityBoundingBox().minY, posZ)) >= 0.0F;
	}

	/**
	 * if the entity got a PathEntity it returns true, else false
	 */
	public boolean hasPath() {
		return !navigator.noPath();
	}

	public boolean isWithinHomeDistanceCurrentPosition() {
		return func_180485_d(new BlockPos(this));
	}

	public boolean func_180485_d(final BlockPos p_180485_1_) {
		return maximumHomeDistance == -1.0F ? true
				: homePosition.distanceSq(p_180485_1_) < maximumHomeDistance * maximumHomeDistance;
	}

	public void func_175449_a(final BlockPos p_175449_1_, final int p_175449_2_) {
		homePosition = p_175449_1_;
		maximumHomeDistance = p_175449_2_;
	}

	public BlockPos func_180486_cf() {
		return homePosition;
	}

	public float getMaximumHomeDistance() {
		return maximumHomeDistance;
	}

	public void detachHome() {
		maximumHomeDistance = -1.0F;
	}

	/**
	 * Returns whether a home area is defined for this entity.
	 */
	public boolean hasHome() {
		return maximumHomeDistance != -1.0F;
	}

	/**
	 * Applies logic related to leashes, for example dragging the entity or
	 * breaking the leash.
	 */
	@Override
	protected void updateLeashedState() {
		super.updateLeashedState();

		if (getLeashed() && getLeashedToEntity() != null && getLeashedToEntity().worldObj == worldObj) {
			final Entity var1 = getLeashedToEntity();
			func_175449_a(new BlockPos((int) var1.posX, (int) var1.posY, (int) var1.posZ), 5);
			final float var2 = getDistanceToEntity(var1);

			if (this instanceof EntityTameable && ((EntityTameable) this).isSitting()) {
				if (var2 > 10.0F) {
					clearLeashed(true, true);
				}

				return;
			}

			if (!field_110180_bt) {
				tasks.addTask(2, aiBase);

				if (getNavigator() instanceof PathNavigateGround) {
					((PathNavigateGround) getNavigator()).func_179690_a(false);
				}

				field_110180_bt = true;
			}

			func_142017_o(var2);

			if (var2 > 4.0F) {
				getNavigator().tryMoveToEntityLiving(var1, 1.0D);
			}

			if (var2 > 6.0F) {
				final double var3 = (var1.posX - posX) / var2;
				final double var5 = (var1.posY - posY) / var2;
				final double var7 = (var1.posZ - posZ) / var2;
				motionX += var3 * Math.abs(var3) * 0.4D;
				motionY += var5 * Math.abs(var5) * 0.4D;
				motionZ += var7 * Math.abs(var7) * 0.4D;
			}

			if (var2 > 10.0F) {
				clearLeashed(true, true);
			}
		} else if (!getLeashed() && field_110180_bt) {
			field_110180_bt = false;
			tasks.removeTask(aiBase);

			if (getNavigator() instanceof PathNavigateGround) {
				((PathNavigateGround) getNavigator()).func_179690_a(true);
			}

			detachHome();
		}
	}

	protected void func_142017_o(final float p_142017_1_) {}
}
