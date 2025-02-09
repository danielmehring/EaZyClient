package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemMinecart extends Item {

public static final int EaZy = 1308;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final IBehaviorDispenseItem dispenserMinecartBehavior = new BehaviorDefaultDispenseItem() {
		private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

		// private static final String __OBFID =
		// "http://https://fuckuskid00000050";
		@Override
		public ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
			final EnumFacing var3 = BlockDispenser.getFacing(source.getBlockMetadata());
			final World var4 = source.getWorld();
			final double var5 = source.getX() + var3.getFrontOffsetX() * 1.125D;
			final double var7 = Math.floor(source.getY()) + var3.getFrontOffsetY();
			final double var9 = source.getZ() + var3.getFrontOffsetZ() * 1.125D;
			final BlockPos var11 = source.getBlockPos().offset(var3);
			final IBlockState var12 = var4.getBlockState(var11);
			final BlockRailBase.EnumRailDirection var13 = var12.getBlock() instanceof BlockRailBase
					? (BlockRailBase.EnumRailDirection) var12
							.getValue(((BlockRailBase) var12.getBlock()).func_176560_l())
					: BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			double var14;

			if (BlockRailBase.func_176563_d(var12)) {
				if (var13.func_177018_c()) {
					var14 = 0.6D;
				} else {
					var14 = 0.1D;
				}
			} else {
				if (var12.getBlock().getMaterial() != Material.air
						|| !BlockRailBase.func_176563_d(var4.getBlockState(var11.offsetDown()))) {
					return behaviourDefaultDispenseItem.dispense(source, stack);
				}

				final IBlockState var16 = var4.getBlockState(var11.offsetDown());
				final BlockRailBase.EnumRailDirection var17 = var16.getBlock() instanceof BlockRailBase
						? (BlockRailBase.EnumRailDirection) var16
								.getValue(((BlockRailBase) var16.getBlock()).func_176560_l())
						: BlockRailBase.EnumRailDirection.NORTH_SOUTH;

				if (var3 != EnumFacing.DOWN && var17.func_177018_c()) {
					var14 = -0.4D;
				} else {
					var14 = -0.9D;
				}
			}

			final EntityMinecart var18 = EntityMinecart.func_180458_a(var4, var5, var7 + var14, var9,
					((ItemMinecart) stack.getItem()).minecartType);

			if (stack.hasDisplayName()) {
				var18.setCustomNameTag(stack.getDisplayName());
			}

			var4.spawnEntityInWorld(var18);
			stack.splitStack(1);
			return stack;
		}

		@Override
		protected void playDispenseSound(final IBlockSource source) {
			source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
		}
	};
	private final EntityMinecart.EnumMinecartType minecartType;
	// private static final String __OBFID = "http://https://fuckuskid00000049";

	public ItemMinecart(final EntityMinecart.EnumMinecartType p_i45785_1_) {
		maxStackSize = 1;
		minecartType = p_i45785_1_;
		setCreativeTab(CreativeTabs.tabTransport);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 * 
	 * @param pos
	 *            The block being right-clicked
	 * @param side
	 *            The side being right-clicked
	 */
	@Override
	public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn,
			final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		final IBlockState var9 = worldIn.getBlockState(pos);

		if (BlockRailBase.func_176563_d(var9)) {
			if (!worldIn.isRemote) {
				final BlockRailBase.EnumRailDirection var10 = var9.getBlock() instanceof BlockRailBase
						? (BlockRailBase.EnumRailDirection) var9
								.getValue(((BlockRailBase) var9.getBlock()).func_176560_l())
						: BlockRailBase.EnumRailDirection.NORTH_SOUTH;
				double var11 = 0.0D;

				if (var10.func_177018_c()) {
					var11 = 0.5D;
				}

				final EntityMinecart var13 = EntityMinecart.func_180458_a(worldIn, pos.getX() + 0.5D,
						pos.getY() + 0.0625D + var11, pos.getZ() + 0.5D, minecartType);

				if (stack.hasDisplayName()) {
					var13.setCustomNameTag(stack.getDisplayName());
				}

				worldIn.spawnEntityInWorld(var13);
			}

			--stack.stackSize;
			return true;
		} else {
			return false;
		}
	}
}
