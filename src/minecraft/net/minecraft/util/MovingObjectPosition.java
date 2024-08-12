package net.minecraft.util;

import net.minecraft.entity.Entity;

public class MovingObjectPosition {

public static final int EaZy = 1641;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private BlockPos field_178783_e;

	/** What type of ray trace hit was this? 0 = block, 1 = entity */
	public MovingObjectPosition.MovingObjectType typeOfHit;
	public EnumFacing facing;

	/** The vector position of the hit */
	public Vec3 hitVec;

	/** The hit entity */
	public Entity entityHit;
	// private static final String __OBFID = "http://https://fuckuskid00000610";

	public MovingObjectPosition(final Vec3 p_i45551_1_, final EnumFacing p_i45551_2_, final BlockPos p_i45551_3_) {
		this(MovingObjectPosition.MovingObjectType.BLOCK, p_i45551_1_, p_i45551_2_, p_i45551_3_);
	}

	public MovingObjectPosition(final Vec3 p_i45552_1_, final EnumFacing p_i45552_2_) {
		this(MovingObjectPosition.MovingObjectType.BLOCK, p_i45552_1_, p_i45552_2_, BlockPos.ORIGIN);
	}

	public MovingObjectPosition(final Entity p_i2304_1_) {
		this(p_i2304_1_, new Vec3(p_i2304_1_.posX, p_i2304_1_.posY, p_i2304_1_.posZ));
	}

	public MovingObjectPosition(final MovingObjectPosition.MovingObjectType p_i45553_1_, final Vec3 p_i45553_2_,
			final EnumFacing p_i45553_3_, final BlockPos p_i45553_4_) {
		typeOfHit = p_i45553_1_;
		field_178783_e = p_i45553_4_;
		facing = p_i45553_3_;
		hitVec = new Vec3(p_i45553_2_.xCoord, p_i45553_2_.yCoord, p_i45553_2_.zCoord);
	}

	public MovingObjectPosition(final Entity p_i45482_1_, final Vec3 p_i45482_2_) {
		typeOfHit = MovingObjectPosition.MovingObjectType.ENTITY;
		entityHit = p_i45482_1_;
		hitVec = p_i45482_2_;
	}

	public BlockPos getBlockPos() {
		return field_178783_e;
	}

	@Override
	public String toString() {
		return "HitResult{type=" + typeOfHit + ", blockpos=" + field_178783_e + ", f=" + facing + ", pos="
				+ hitVec + ", entity=" + entityHit + '}';
	}

	public static enum MovingObjectType {
		MISS("MISS", 0), BLOCK("BLOCK", 1), ENTITY("ENTITY", 2);

		private MovingObjectType(final String p_i2302_1_, final int p_i2302_2_) {}
	}
}
