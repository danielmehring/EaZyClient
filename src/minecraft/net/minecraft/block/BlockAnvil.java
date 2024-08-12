package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

import java.util.List;

public class BlockAnvil extends BlockFalling {

public static final int EaZy = 255;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 2);
	// private static final String __OBFID = "http://https://fuckuskid00000192";

	protected BlockAnvil() {
		super(Material.anvil);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DAMAGE, 0));
		setLightOpacity(0);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		final EnumFacing var9 = placer.func_174811_aO().rotateY();
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, var9)
				.withProperty(DAMAGE, meta >> 2);
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.displayGui(new BlockAnvil.Anvil(worldIn, pos));
		}

		return true;
	}

	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(final IBlockState state) {
		return ((Integer) state.getValue(DAMAGE));
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final EnumFacing var3 = (EnumFacing) access.getBlockState(pos).getValue(FACING);

		if (var3.getAxis() == EnumFacing.Axis.X) {
			setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
		} else {
			setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
		}
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
		list.add(new ItemStack(itemIn, 1, 2));
	}

	@Override
	protected void onStartFalling(final EntityFallingBlock fallingEntity) {
		fallingEntity.setHurtEntities(true);
	}

	@Override
	public void onEndFalling(final World worldIn, final BlockPos pos) {
		worldIn.playAuxSFX(1022, pos, 0);
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		return true;
	}

	/**
	 * Possibly modify the given BlockState before rendering it on an Entity
	 * (Minecarts, Endermen, ...)
	 */
	@Override
	public IBlockState getStateForEntityRender(final IBlockState state) {
		return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(DAMAGE, (meta & 15) >> 2);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		var3 |= ((Integer) state.getValue(DAMAGE)) << 2;
		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, DAMAGE });
	}

	public static class Anvil implements IInteractionObject {
		private final World world;
		private final BlockPos position;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002144";

		public Anvil(final World worldIn, final BlockPos pos) {
			world = worldIn;
			position = pos;
		}

		@Override
		public String getName() {
			return "anvil";
		}

		@Override
		public boolean hasCustomName() {
			return false;
		}

		@Override
		public IChatComponent getDisplayName() {
			return new ChatComponentTranslation(Blocks.anvil.getUnlocalizedName() + ".name", new Object[0]);
		}

		@Override
		public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
			return new ContainerRepair(playerInventory, world, position, playerIn);
		}

		@Override
		public String getGuiID() {
			return "minecraft:anvil";
		}
	}
}
