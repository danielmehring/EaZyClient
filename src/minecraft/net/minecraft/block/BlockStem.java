package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

import com.google.common.base.Predicate;

public class BlockStem extends BlockBush implements IGrowable {

public static final int EaZy = 389;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger AGE_PROP = PropertyInteger.create("age", 0, 7);
	public static final PropertyDirection FACING_PROP = PropertyDirection.create("facing", new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002059";
		public boolean apply(final EnumFacing p_177218_1_) {
			return p_177218_1_ != EnumFacing.DOWN;
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return this.apply((EnumFacing) p_apply_1_);
		}
	});
	private final Block cropBlock;
	// private static final String __OBFID = "http://https://fuckuskid00000316";

	protected BlockStem(final Block p_i45430_1_) {
		setDefaultState(blockState.getBaseState().withProperty(AGE_PROP, 0).withProperty(FACING_PROP,
				EnumFacing.UP));
		cropBlock = p_i45430_1_;
		setTickRandomly(true);
		final float var2 = 0.125F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
		setCreativeTab((CreativeTabs) null);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		state = state.withProperty(FACING_PROP, EnumFacing.UP);
		final Iterator var4 = EnumFacing.Plane.HORIZONTAL.iterator();

		while (var4.hasNext()) {
			final EnumFacing var5 = (EnumFacing) var4.next();

			if (worldIn.getBlockState(pos.offset(var5)).getBlock() == cropBlock) {
				state = state.withProperty(FACING_PROP, var5);
				break;
			}
		}

		return state;
	}

	/**
	 * is the block grass, dirt or farmland
	 */
	@Override
	protected boolean canPlaceBlockOn(final Block ground) {
		return ground == Blocks.farmland;
	}

	@Override
	public void updateTick(final World worldIn, BlockPos pos, IBlockState state, final Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (worldIn.getLightFromNeighbors(pos.offsetUp()) >= 9) {
			final float var5 = BlockCrops.getGrowthChance(this, worldIn, pos);

			if (rand.nextInt((int) (25.0F / var5) + 1) == 0) {
				final int var6 = ((Integer) state.getValue(AGE_PROP));

				if (var6 < 7) {
					state = state.withProperty(AGE_PROP, var6 + 1);
					worldIn.setBlockState(pos, state, 2);
				} else {
					final Iterator var7 = EnumFacing.Plane.HORIZONTAL.iterator();

					while (var7.hasNext()) {
						final EnumFacing var8 = (EnumFacing) var7.next();

						if (worldIn.getBlockState(pos.offset(var8)).getBlock() == cropBlock) {
							return;
						}
					}

					pos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(rand));
					final Block var9 = worldIn.getBlockState(pos.offsetDown()).getBlock();

					if (worldIn.getBlockState(pos).getBlock().blockMaterial == Material.air
							&& (var9 == Blocks.farmland || var9 == Blocks.dirt || var9 == Blocks.grass)) {
						worldIn.setBlockState(pos, cropBlock.getDefaultState());
					}
				}
			}
		}
	}

	public void growStem(final World worldIn, final BlockPos p_176482_2_, final IBlockState p_176482_3_) {
		final int var4 = ((Integer) p_176482_3_.getValue(AGE_PROP))
				+ MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
		worldIn.setBlockState(p_176482_2_, p_176482_3_.withProperty(AGE_PROP, Math.min(7, var4)), 2);
	}

	@Override
	public int getRenderColor(final IBlockState state) {
		if (state.getBlock() != this) {
			return super.getRenderColor(state);
		} else {
			final int var2 = ((Integer) state.getValue(AGE_PROP));
			final int var3 = var2 * 32;
			final int var4 = 255 - var2 * 8;
			final int var5 = var2 * 4;
			return var3 << 16 | var4 << 8 | var5;
		}
	}

	@Override
	public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
		return getRenderColor(worldIn.getBlockState(pos));
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		final float var1 = 0.125F;
		setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		maxY = (((Integer) access.getBlockState(pos).getValue(AGE_PROP)) * 2 + 2) / 16.0F;
		final float var3 = 0.125F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, (float) maxY, 0.5F + var3);
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
			final Item var6 = getSeedItem();

			if (var6 != null) {
				final int var7 = ((Integer) state.getValue(AGE_PROP));

				for (int var8 = 0; var8 < 3; ++var8) {
					if (worldIn.rand.nextInt(15) <= var7) {
						spawnAsEntity(worldIn, pos, new ItemStack(var6));
					}
				}
			}
		}
	}

	protected Item getSeedItem() {
		return cropBlock == Blocks.pumpkin ? Items.pumpkin_seeds
				: cropBlock == Blocks.melon_block ? Items.melon_seeds : null;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return null;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		final Item var3 = getSeedItem();
		return var3 != null ? var3 : null;
	}

	@Override
	public boolean isStillGrowing(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_,
			final boolean p_176473_4_) {
		return ((Integer) p_176473_3_.getValue(AGE_PROP)) != 7;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_,
			final IBlockState p_180670_4_) {
		return true;
	}

	@Override
	public void grow(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_,
			final IBlockState p_176474_4_) {
		growStem(worldIn, p_176474_3_, p_176474_4_);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(AGE_PROP, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(AGE_PROP));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE_PROP, FACING_PROP });
	}
}
