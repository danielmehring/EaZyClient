package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneLight extends Block {

public static final int EaZy = 363;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final boolean isOn;
	// private static final String __OBFID = "http://https://fuckuskid00000297";

	public BlockRedstoneLight(final boolean p_i45421_1_) {
		super(Material.redstoneLight);
		isOn = p_i45421_1_;

		if (p_i45421_1_) {
			setLightLevel(1.0F);
		}
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (!worldIn.isRemote) {
			if (isOn && !worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, Blocks.redstone_lamp.getDefaultState(), 2);
			} else if (!isOn && worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!worldIn.isRemote) {
			if (isOn && !worldIn.isBlockPowered(pos)) {
				worldIn.scheduleUpdate(pos, this, 4);
			} else if (!isOn && worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, Blocks.lit_redstone_lamp.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (!worldIn.isRemote) {
			if (isOn && !worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, Blocks.redstone_lamp.getDefaultState(), 2);
			}
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Item.getItemFromBlock(Blocks.redstone_lamp);
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Item.getItemFromBlock(Blocks.redstone_lamp);
	}

	@Override
	protected ItemStack createStackedBlock(final IBlockState state) {
		return new ItemStack(Blocks.redstone_lamp);
	}
}
