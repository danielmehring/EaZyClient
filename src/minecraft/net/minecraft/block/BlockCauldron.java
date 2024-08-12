package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockCauldron extends Block {

public static final int EaZy = 272;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger field_176591_a = PropertyInteger.create("level", 0, 3);
	// private static final String __OBFID = "http://https://fuckuskid00000213";

	public BlockCauldron() {
		super(Material.iron);
		setDefaultState(blockState.getBaseState().withProperty(field_176591_a, 0));
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
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		final float var7 = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0F, var7, 1.0F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var7);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBounds(1.0F - var7, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBounds(0.0F, 0.0F, 1.0F - var7, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBoundsForItemRender();
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state,
			final Entity entityIn) {
		final int var5 = ((Integer) state.getValue(field_176591_a));
		final float var6 = pos.getY() + (6.0F + 3 * var5) / 16.0F;

		if (!worldIn.isRemote && entityIn.isBurning() && var5 > 0 && entityIn.getEntityBoundingBox().minY <= var6) {
			entityIn.extinguish();
			func_176590_a(worldIn, pos, state, var5 - 1);
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			final ItemStack var9 = playerIn.inventory.getCurrentItem();

			if (var9 == null) {
				return true;
			} else {
				final int var10 = ((Integer) state.getValue(field_176591_a));
				final Item var11 = var9.getItem();

				if (var11 == Items.water_bucket) {
					if (var10 < 3) {
						if (!playerIn.capabilities.isCreativeMode) {
							playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,
									new ItemStack(Items.bucket));
						}

						func_176590_a(worldIn, pos, state, 3);
					}

					return true;
				} else {
					ItemStack var13;

					if (var11 == Items.glass_bottle) {
						if (var10 > 0) {
							if (!playerIn.capabilities.isCreativeMode) {
								var13 = new ItemStack(Items.potionitem, 1, 0);

								if (!playerIn.inventory.addItemStackToInventory(var13)) {
									worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D,
											pos.getY() + 1.5D, pos.getZ() + 0.5D, var13));
								} else if (playerIn instanceof EntityPlayerMP) {
									((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
								}

								--var9.stackSize;

								if (var9.stackSize <= 0) {
									playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,
											(ItemStack) null);
								}
							}

							func_176590_a(worldIn, pos, state, var10 - 1);
						}

						return true;
					} else {
						if (var10 > 0 && var11 instanceof ItemArmor) {
							final ItemArmor var12 = (ItemArmor) var11;

							if (var12.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && var12.hasColor(var9)) {
								var12.removeColor(var9);
								func_176590_a(worldIn, pos, state, var10 - 1);
								return true;
							}
						}

						if (var10 > 0 && var11 instanceof ItemBanner && TileEntityBanner.func_175113_c(var9) > 0) {
							var13 = var9.copy();
							var13.stackSize = 1;
							TileEntityBanner.func_175117_e(var13);

							if (var9.stackSize <= 1 && !playerIn.capabilities.isCreativeMode) {
								playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, var13);
							} else {
								if (!playerIn.inventory.addItemStackToInventory(var13)) {
									worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D,
											pos.getY() + 1.5D, pos.getZ() + 0.5D, var13));
								} else if (playerIn instanceof EntityPlayerMP) {
									((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
								}

								if (!playerIn.capabilities.isCreativeMode) {
									--var9.stackSize;
								}
							}

							if (!playerIn.capabilities.isCreativeMode) {
								func_176590_a(worldIn, pos, state, var10 - 1);
							}

							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
	}

	public void func_176590_a(final World worldIn, final BlockPos p_176590_2_, final IBlockState p_176590_3_,
			final int p_176590_4_) {
		worldIn.setBlockState(p_176590_2_,
				p_176590_3_.withProperty(field_176591_a, MathHelper.clamp_int(p_176590_4_, 0, 3)), 2);
		worldIn.updateComparatorOutputLevel(p_176590_2_, this);
	}

	/**
	 * Called similar to random ticks, but only when it is raining.
	 */
	@Override
	public void fillWithRain(final World worldIn, final BlockPos pos) {
		if (worldIn.rand.nextInt(20) == 1) {
			final IBlockState var3 = worldIn.getBlockState(pos);

			if (((Integer) var3.getValue(field_176591_a)) < 3) {
				worldIn.setBlockState(pos, var3.cycleProperty(field_176591_a), 2);
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
		return Items.cauldron;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.cauldron;
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
		return ((Integer) worldIn.getBlockState(pos).getValue(field_176591_a));
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176591_a, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(field_176591_a));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176591_a });
	}
}
