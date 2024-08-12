package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {

public static final int EaZy = 1486;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private BlockPos field_179696_f;
	// private static final String __OBFID = "http://https://fuckuskid00002245";

	public PathNavigateClimber(final EntityLiving p_i45874_1_, final World worldIn) {
		super(p_i45874_1_, worldIn);
	}

	@Override
	public PathEntity func_179680_a(final BlockPos p_179680_1_) {
		field_179696_f = p_179680_1_;
		return super.func_179680_a(p_179680_1_);
	}

	/**
	 * Returns the path to the given EntityLiving. Args : entity
	 */
	@Override
	public PathEntity getPathToEntityLiving(final Entity p_75494_1_) {
		field_179696_f = new BlockPos(p_75494_1_);
		return super.getPathToEntityLiving(p_75494_1_);
	}

	/**
	 * Try to find and set a path to EntityLiving. Returns true if successful.
	 * Args : entity, speed
	 */
	@Override
	public boolean tryMoveToEntityLiving(final Entity p_75497_1_, final double p_75497_2_) {
		final PathEntity var4 = getPathToEntityLiving(p_75497_1_);

		if (var4 != null) {
			return setPath(var4, p_75497_2_);
		} else {
			field_179696_f = new BlockPos(p_75497_1_);
			speed = p_75497_2_;
			return true;
		}
	}

	@Override
	public void onUpdateNavigation() {
		if (!noPath()) {
			super.onUpdateNavigation();
		} else {
			if (field_179696_f != null) {
				final double var1 = theEntity.width * theEntity.width;

				if (theEntity.func_174831_c(field_179696_f) >= var1 && (theEntity.posY <= field_179696_f.getY()
						|| theEntity.func_174831_c(new BlockPos(field_179696_f.getX(),
								MathHelper.floor_double(theEntity.posY), field_179696_f.getZ())) >= var1)) {
					theEntity.getMoveHelper().setMoveTo(field_179696_f.getX(), field_179696_f.getY(),
							field_179696_f.getZ(), speed);
				} else {
					field_179696_f = null;
				}
			}
		}
	}
}
