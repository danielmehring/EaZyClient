package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;

import java.util.Iterator;
import java.util.List;

public class EntityAIHurtByTarget extends EntityAITarget {

public static final int EaZy = 1060;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final boolean entityCallsForHelp;

	/** Store the previous revengeTimer value */
	private int revengeTimerOld;
	private final Class[] field_179447_c;
	// private static final String __OBFID = "http://https://fuckuskid00001619";

	public EntityAIHurtByTarget(final EntityCreature p_i45885_1_, final boolean p_i45885_2_,
			final Class... p_i45885_3_) {
		super(p_i45885_1_, false);
		entityCallsForHelp = p_i45885_2_;
		field_179447_c = p_i45885_3_;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final int var1 = taskOwner.getRevengeTimer();
		return var1 != revengeTimerOld && isSuitableTarget(taskOwner.getAITarget(), false);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		taskOwner.setAttackTarget(taskOwner.getAITarget());
		revengeTimerOld = taskOwner.getRevengeTimer();

		if (entityCallsForHelp) {
			final double var1 = getTargetDistance();
			final List var3 = taskOwner.worldObj.getEntitiesWithinAABB(taskOwner.getClass(),
					new AxisAlignedBB(taskOwner.posX, taskOwner.posY, taskOwner.posZ, taskOwner.posX + 1.0D,
							taskOwner.posY + 1.0D, taskOwner.posZ + 1.0D).expand(var1, 10.0D, var1));
			final Iterator var4 = var3.iterator();

			while (var4.hasNext()) {
				final EntityCreature var5 = (EntityCreature) var4.next();

				if (taskOwner != var5 && var5.getAttackTarget() == null
						&& !var5.isOnSameTeam(taskOwner.getAITarget())) {
					boolean var6 = false;
					final Class[] var7 = field_179447_c;
					final int var8 = var7.length;

					for (int var9 = 0; var9 < var8; ++var9) {
						final Class var10 = var7[var9];

						if (var5.getClass() == var10) {
							var6 = true;
							break;
						}
					}

					if (!var6) {
						func_179446_a(var5, taskOwner.getAITarget());
					}
				}
			}
		}

		super.startExecuting();
	}

	protected void func_179446_a(final EntityCreature p_179446_1_, final EntityLivingBase p_179446_2_) {
		p_179446_1_.setAttackTarget(p_179446_2_);
	}
}
