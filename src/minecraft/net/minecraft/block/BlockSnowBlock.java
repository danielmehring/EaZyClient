package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSnowBlock extends Block {

public static final int EaZy = 380;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000308";

	protected BlockSnowBlock() {
		super(Material.craftedSnow);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Items.snowball;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 4;
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11) {
			dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
			worldIn.setBlockToAir(pos);
		}
	}
}
