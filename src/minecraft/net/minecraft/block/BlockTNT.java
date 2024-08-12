package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTNT extends Block {

public static final int EaZy = 395;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyBool field_176246_a = PropertyBool.create("explode");
	// private static final String __OBFID = "http://https://fuckuskid00000324";

	public BlockTNT() {
		super(Material.tnt);
		setDefaultState(blockState.getBaseState().withProperty(field_176246_a, false));
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);

		if (worldIn.isBlockPowered(pos)) {
			onBlockDestroyedByPlayer(worldIn, pos, state.withProperty(field_176246_a, true));
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (worldIn.isBlockPowered(pos)) {
			onBlockDestroyedByPlayer(worldIn, pos, state.withProperty(field_176246_a, true));
			worldIn.setBlockToAir(pos);
		}
	}

	/**
	 * Called when this Block is destroyed by an Explosion
	 */
	@Override
	public void onBlockDestroyedByExplosion(final World worldIn, final BlockPos pos, final Explosion explosionIn) {
		if (!worldIn.isRemote) {
			final EntityTNTPrimed var4 = new EntityTNTPrimed(worldIn, pos.getX() + 0.5F, pos.getY() + 0.5F,
					pos.getZ() + 0.5F, explosionIn.getExplosivePlacedBy());
			var4.fuse = worldIn.rand.nextInt(var4.fuse / 4) + var4.fuse / 8;
			worldIn.spawnEntityInWorld(var4);
		}
	}

	/**
	 * Called when a player destroys this Block
	 */
	@Override
	public void onBlockDestroyedByPlayer(final World worldIn, final BlockPos pos, final IBlockState state) {
		func_180692_a(worldIn, pos, state, (EntityLivingBase) null);
	}

	public void func_180692_a(final World worldIn, final BlockPos p_180692_2_, final IBlockState p_180692_3_,
			final EntityLivingBase p_180692_4_) {
		if (!worldIn.isRemote) {
			if (((Boolean) p_180692_3_.getValue(field_176246_a))) {
				final EntityTNTPrimed var5 = new EntityTNTPrimed(worldIn, p_180692_2_.getX() + 0.5F,
						p_180692_2_.getY() + 0.5F, p_180692_2_.getZ() + 0.5F, p_180692_4_);
				worldIn.spawnEntityInWorld(var5);
				worldIn.playSoundAtEntity(var5, "game.tnt.primed", 1.0F, 1.0F);
			}
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (playerIn.getCurrentEquippedItem() != null) {
			final Item var9 = playerIn.getCurrentEquippedItem().getItem();

			if (var9 == Items.flint_and_steel || var9 == Items.fire_charge) {
				func_180692_a(worldIn, pos, state.withProperty(field_176246_a, true), playerIn);
				worldIn.setBlockToAir(pos);

				if (var9 == Items.flint_and_steel) {
					playerIn.getCurrentEquippedItem().damageItem(1, playerIn);
				} else if (!playerIn.capabilities.isCreativeMode) {
					--playerIn.getCurrentEquippedItem().stackSize;
				}

				return true;
			}
		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state,
			final Entity entityIn) {
		if (!worldIn.isRemote && entityIn instanceof EntityArrow) {
			final EntityArrow var5 = (EntityArrow) entityIn;

			if (var5.isBurning()) {
				func_180692_a(worldIn, pos,
						worldIn.getBlockState(pos).withProperty(field_176246_a, true),
						var5.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase) var5.shootingEntity
								: null);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	/**
	 * Return whether this block can drop from an explosion.
	 */
	@Override
	public boolean canDropFromExplosion(final Explosion explosionIn) {
		return false;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176246_a, (meta & 1) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Boolean) state.getValue(field_176246_a)) ? 1 : 0;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176246_a });
	}
}
