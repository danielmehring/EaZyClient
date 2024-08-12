package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockPotato extends BlockCrops {

public static final int EaZy = 349;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000286";

	@Override
	protected Item getSeed() {
		return Items.potato;
	}

	@Override
	protected Item getCrop() {
		return Items.potato;
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 * 
	 * @param chance
	 *            The chance that each Item is actually spawned (1.0 = always,
	 *            0.0 = never)
	 * @param fortune
	 *            The player's fortune level
	 */
	@Override
	public void dropBlockAsItemWithChance(final World worldIn, final BlockPos pos, final IBlockState state,
			final float chance, final int fortune) {
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);

		if (!worldIn.isRemote) {
			if (((Integer) state.getValue(AGE)) >= 7 && worldIn.rand.nextInt(50) == 0) {
				spawnAsEntity(worldIn, pos, new ItemStack(Items.poisonous_potato));
			}
		}
	}
}
