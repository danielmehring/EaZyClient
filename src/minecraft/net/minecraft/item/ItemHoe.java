package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHoe extends Item {

public static final int EaZy = 1302;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected Item.ToolMaterial theToolMaterial;
	// private static final String __OBFID = "http://https://fuckuskid00000039";

	public ItemHoe(final Item.ToolMaterial p_i45343_1_) {
		theToolMaterial = p_i45343_1_;
		maxStackSize = 1;
		setMaxDamage(p_i45343_1_.getMaxUses());
		setCreativeTab(CreativeTabs.tabTools);
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
		if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
			return false;
		} else {
			final IBlockState var9 = worldIn.getBlockState(pos);
			final Block var10 = var9.getBlock();

			if (side != EnumFacing.DOWN
					&& worldIn.getBlockState(pos.offsetUp()).getBlock().getMaterial() == Material.air) {
				if (var10 == Blocks.grass) {
					return func_179232_a(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
				}

				if (var10 == Blocks.dirt) {
					switch (ItemHoe.SwitchDirtType.field_179590_a[((BlockDirt.DirtType) var9
							.getValue(BlockDirt.VARIANT)).ordinal()]) {
						case 1:
							return func_179232_a(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());

						case 2:
							return func_179232_a(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState()
									.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
					}
				}
			}

			return false;
		}
	}

	protected boolean func_179232_a(final ItemStack p_179232_1_, final EntityPlayer p_179232_2_, final World worldIn,
			final BlockPos p_179232_4_, final IBlockState p_179232_5_) {
		worldIn.playSoundEffect(p_179232_4_.getX() + 0.5F, p_179232_4_.getY() + 0.5F, p_179232_4_.getZ() + 0.5F,
				p_179232_5_.getBlock().stepSound.getStepSound(),
				(p_179232_5_.getBlock().stepSound.getVolume() + 1.0F) / 2.0F,
				p_179232_5_.getBlock().stepSound.getFrequency() * 0.8F);

		if (worldIn.isRemote) {
			return true;
		} else {
			worldIn.setBlockState(p_179232_4_, p_179232_5_);
			p_179232_1_.damageItem(1, p_179232_2_);
			return true;
		}
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@Override
	public boolean isFull3D() {
		return true;
	}

	/**
	 * Returns the name of the material this tool is made from as it is declared
	 * in EnumToolMaterial (meaning diamond would return "EMERALD")
	 */
	public String getMaterialName() {
		return theToolMaterial.toString();
	}

	static final class SwitchDirtType {
		static final int[] field_179590_a = new int[BlockDirt.DirtType.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002179";

		static {
			try {
				field_179590_a[BlockDirt.DirtType.DIRT.ordinal()] = 1;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_179590_a[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
