package net.minecraft.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class EntityDragonPart extends Entity {

public static final int EaZy = 1102;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The dragon entity this dragon part belongs to */
	public final IEntityMultiPart entityDragonObj;
	public final String field_146032_b;
	// private static final String __OBFID = "http://https://fuckuskid00001657";

	public EntityDragonPart(final IEntityMultiPart p_i1697_1_, final String p_i1697_2_, final float p_i1697_3_,
			final float p_i1697_4_) {
		super(p_i1697_1_.func_82194_d());
		setSize(p_i1697_3_, p_i1697_4_);
		entityDragonObj = p_i1697_1_;
		field_146032_b = p_i1697_2_;
	}

	@Override
	protected void entityInit() {}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		return func_180431_b(source) ? false : entityDragonObj.attackEntityFromPart(this, source, amount);
	}

	/**
	 * Returns true if Entity argument is equal to this Entity
	 */
	@Override
	public boolean isEntityEqual(final Entity entityIn) {
		return this == entityIn || entityDragonObj == entityIn;
	}
}
