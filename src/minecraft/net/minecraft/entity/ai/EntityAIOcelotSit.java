package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIOcelotSit extends EntityAIMoveToBlock {

public static final int EaZy = 1073;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityOcelot field_151493_a;
	// private static final String __OBFID = "http://https://fuckuskid00001601";

	public EntityAIOcelotSit(final EntityOcelot p_i45315_1_, final double p_i45315_2_) {
		super(p_i45315_1_, p_i45315_2_, 8);
		field_151493_a = p_i45315_1_;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		return field_151493_a.isTamed() && !field_151493_a.isSitting() && super.shouldExecute();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return super.continueExecuting();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		super.startExecuting();
		field_151493_a.getAISit().setSitting(false);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		super.resetTask();
		field_151493_a.setSitting(false);
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		super.updateTask();
		field_151493_a.getAISit().setSitting(false);

		if (!func_179487_f()) {
			field_151493_a.setSitting(false);
		} else if (!field_151493_a.isSitting()) {
			field_151493_a.setSitting(true);
		}
	}

	@Override
	protected boolean func_179488_a(final World worldIn, final BlockPos p_179488_2_) {
		if (!worldIn.isAirBlock(p_179488_2_.offsetUp())) {
			return false;
		} else {
			final IBlockState var3 = worldIn.getBlockState(p_179488_2_);
			final Block var4 = var3.getBlock();

			if (var4 == Blocks.chest) {
				final TileEntity var5 = worldIn.getTileEntity(p_179488_2_);

				if (var5 instanceof TileEntityChest && ((TileEntityChest) var5).numPlayersUsing < 1) {
					return true;
				}
			} else {
				if (var4 == Blocks.lit_furnace) {
					return true;
				}

				if (var4 == Blocks.bed && var3.getValue(BlockBed.PART_PROP) != BlockBed.EnumPartType.HEAD) {
					return true;
				}
			}

			return false;
		}
	}
}
