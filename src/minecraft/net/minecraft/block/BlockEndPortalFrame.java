package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockEndPortalFrame extends Block {

public static final int EaZy = 298;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection field_176508_a = PropertyDirection.create("facing",
			EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool field_176507_b = PropertyBool.create("eye");
	// private static final String __OBFID = "http://https://fuckuskid00000237";

	public BlockEndPortalFrame() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(field_176508_a, EnumFacing.NORTH)
				.withProperty(field_176507_b, false));
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
	}

	/**
	 * Add all collision boxes of this Block to the list that intersect with the
	 * given mask.
	 * 
	 * @param collidingEntity
	 *            the Entity colliding with this Block
	 */
	@Override
	public void addCollisionBoxesToList(final World worldIn, final BlockPos pos, final IBlockState state,
			final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

		if (((Boolean) worldIn.getBlockState(pos).getValue(field_176507_b))) {
			setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}

		setBlockBoundsForItemRender();
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
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return getDefaultState().withProperty(field_176508_a, placer.func_174811_aO().getOpposite())
				.withProperty(field_176507_b, false);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
		return ((Boolean) worldIn.getBlockState(pos).getValue(field_176507_b)) ? 15 : 0;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176507_b, (meta & 4) != 0)
				.withProperty(field_176508_a, EnumFacing.getHorizontal(meta & 3));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(field_176508_a)).getHorizontalIndex();

		if (((Boolean) state.getValue(field_176507_b))) {
			var3 |= 4;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176508_a, field_176507_b });
	}
}
