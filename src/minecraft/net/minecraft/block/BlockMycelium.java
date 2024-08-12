package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMycelium extends Block {

public static final int EaZy = 331;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyBool SNOWY_PROP = PropertyBool.create("snowy");
	// private static final String __OBFID = "http://https://fuckuskid00000273";

	protected BlockMycelium() {
		super(Material.grass);
		setDefaultState(blockState.getBaseState().withProperty(SNOWY_PROP, false));
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		final Block var4 = worldIn.getBlockState(pos.offsetUp()).getBlock();
		return state.withProperty(SNOWY_PROP, var4 == Blocks.snow || var4 == Blocks.snow_layer);
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (!worldIn.isRemote) {
			if (worldIn.getLightFromNeighbors(pos.offsetUp()) < 4
					&& worldIn.getBlockState(pos.offsetUp()).getBlock().getLightOpacity() > 2) {
				worldIn.setBlockState(pos,
						Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
			} else {
				if (worldIn.getLightFromNeighbors(pos.offsetUp()) >= 9) {
					for (int var5 = 0; var5 < 4; ++var5) {
						final BlockPos var6 = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						final IBlockState var7 = worldIn.getBlockState(var6);
						final Block var8 = worldIn.getBlockState(var6.offsetUp()).getBlock();

						if (var7.getBlock() == Blocks.dirt
								&& var7.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT
								&& worldIn.getLightFromNeighbors(var6.offsetUp()) >= 4 && var8.getLightOpacity() <= 2) {
							worldIn.setBlockState(var6, getDefaultState());
						}
					}
				}
			}
		}
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		super.randomDisplayTick(worldIn, pos, state, rand);

		if (rand.nextInt(10) == 0) {
			worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextFloat(), pos.getY() + 1.1F,
					pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D, new int[0]);
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
		return Blocks.dirt.getItemDropped(
				Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return 0;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { SNOWY_PROP });
	}
}
