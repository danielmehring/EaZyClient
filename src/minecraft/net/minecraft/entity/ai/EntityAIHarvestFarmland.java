package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock {

public static final int EaZy = 1059;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityVillager field_179504_c;
	private boolean field_179502_d;
	private boolean field_179503_e;
	private int field_179501_f;
	// private static final String __OBFID = "http://https://fuckuskid00002253";

	public EntityAIHarvestFarmland(final EntityVillager p_i45889_1_, final double p_i45889_2_) {
		super(p_i45889_1_, p_i45889_2_, 16);
		field_179504_c = p_i45889_1_;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (field_179496_a <= 0) {
			if (!field_179504_c.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
				return false;
			}

			field_179501_f = -1;
			field_179502_d = field_179504_c.func_175556_cs();
			field_179503_e = field_179504_c.func_175557_cr();
		}

		return super.shouldExecute();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return field_179501_f >= 0 && super.continueExecuting();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		super.startExecuting();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		super.resetTask();
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		super.updateTask();
		field_179504_c.getLookHelper().setLookPosition(field_179494_b.getX() + 0.5D, field_179494_b.getY() + 1,
				field_179494_b.getZ() + 0.5D, 10.0F, field_179504_c.getVerticalFaceSpeed());

		if (func_179487_f()) {
			final World var1 = field_179504_c.worldObj;
			final BlockPos var2 = field_179494_b.offsetUp();
			final IBlockState var3 = var1.getBlockState(var2);
			final Block var4 = var3.getBlock();

			if (field_179501_f == 0 && var4 instanceof BlockCrops
					&& ((Integer) var3.getValue(BlockCrops.AGE)) == 7) {
				var1.destroyBlock(var2, true);
			} else if (field_179501_f == 1 && var4 == Blocks.air) {
				final InventoryBasic var5 = field_179504_c.func_175551_co();

				for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6) {
					final ItemStack var7 = var5.getStackInSlot(var6);
					boolean var8 = false;

					if (var7 != null) {
						if (var7.getItem() == Items.wheat_seeds) {
							var1.setBlockState(var2, Blocks.wheat.getDefaultState(), 3);
							var8 = true;
						} else if (var7.getItem() == Items.potato) {
							var1.setBlockState(var2, Blocks.potatoes.getDefaultState(), 3);
							var8 = true;
						} else if (var7.getItem() == Items.carrot) {
							var1.setBlockState(var2, Blocks.carrots.getDefaultState(), 3);
							var8 = true;
						}
					}

					if (var8) {
						--var7.stackSize;

						if (var7.stackSize <= 0) {
							var5.setInventorySlotContents(var6, (ItemStack) null);
						}

						break;
					}
				}
			}

			field_179501_f = -1;
			field_179496_a = 10;
		}
	}

	@Override
	protected boolean func_179488_a(final World worldIn, BlockPos p_179488_2_) {
		Block var3 = worldIn.getBlockState(p_179488_2_).getBlock();

		if (var3 == Blocks.farmland) {
			p_179488_2_ = p_179488_2_.offsetUp();
			final IBlockState var4 = worldIn.getBlockState(p_179488_2_);
			var3 = var4.getBlock();

			if (var3 instanceof BlockCrops && ((Integer) var4.getValue(BlockCrops.AGE)) == 7
					&& field_179503_e && (field_179501_f == 0 || field_179501_f < 0)) {
				field_179501_f = 0;
				return true;
			}

			if (var3 == Blocks.air && field_179502_d && (field_179501_f == 1 || field_179501_f < 0)) {
				field_179501_f = 1;
				return true;
			}
		}

		return false;
	}
}
