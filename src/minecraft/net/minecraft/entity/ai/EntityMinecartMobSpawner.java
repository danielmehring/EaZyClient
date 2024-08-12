package net.minecraft.entity.ai;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart {

public static final int EaZy = 1096;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Mob spawner logic for this spawner minecart. */
	private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001679";
		@Override
		public void func_98267_a(final int p_98267_1_) {
			EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte) p_98267_1_);
		}

		@Override
		public World getSpawnerWorld() {
			return EntityMinecartMobSpawner.this.worldObj;
		}

		@Override
		public BlockPos func_177221_b() {
			return new BlockPos(EntityMinecartMobSpawner.this);
		}
	};
	// private static final String __OBFID = "http://https://fuckuskid00001678";

	public EntityMinecartMobSpawner(final World worldIn) {
		super(worldIn);
	}

	public EntityMinecartMobSpawner(final World worldIn, final double p_i1726_2_, final double p_i1726_4_,
			final double p_i1726_6_) {
		super(worldIn, p_i1726_2_, p_i1726_4_, p_i1726_6_);
	}

	@Override
	public EntityMinecart.EnumMinecartType func_180456_s() {
		return EntityMinecart.EnumMinecartType.SPAWNER;
	}

	@Override
	public IBlockState func_180457_u() {
		return Blocks.mob_spawner.getDefaultState();
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		mobSpawnerLogic.readFromNBT(tagCompund);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		mobSpawnerLogic.writeToNBT(tagCompound);
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		mobSpawnerLogic.setDelayToMin(p_70103_1_);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();
		mobSpawnerLogic.updateSpawner();
	}

	public MobSpawnerBaseLogic func_98039_d() {
		return mobSpawnerLogic;
	}
}
