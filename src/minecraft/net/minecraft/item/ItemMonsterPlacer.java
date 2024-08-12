package net.minecraft.item;

import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ItemMonsterPlacer extends Item {

public static final int EaZy = 1309;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000070";

	public ItemMonsterPlacer() {
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		String var2 = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		final String var3 = EntityList.getStringFromID(stack.getMetadata());

		if (var3 != null) {
			var2 = var2 + " " + StatCollector.translateToLocal("entity." + var3 + ".name");
		}

		return var2;
	}

	@Override
	public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
		final EntityList.EntityEggInfo var3 = (EntityList.EntityEggInfo) EntityList.entityEggs
				.get(stack.getMetadata());
		return var3 != null ? renderPass == 0 ? var3.primaryColor : var3.secondaryColor : 16777215;
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
	public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos,
			final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
			return false;
		} else {
			final IBlockState var9 = worldIn.getBlockState(pos);

			if (var9.getBlock() == Blocks.mob_spawner) {
				final TileEntity var10 = worldIn.getTileEntity(pos);

				if (var10 instanceof TileEntityMobSpawner) {
					final MobSpawnerBaseLogic var11 = ((TileEntityMobSpawner) var10).getSpawnerBaseLogic();
					var11.setEntityName(EntityList.getStringFromID(stack.getMetadata()));
					var10.markDirty();
					worldIn.markBlockForUpdate(pos);

					if (!playerIn.capabilities.isCreativeMode) {
						--stack.stackSize;
					}

					return true;
				}
			}

			pos = pos.offset(side);
			double var13 = 0.0D;

			if (side == EnumFacing.UP && var9 instanceof BlockFence) {
				var13 = 0.5D;
			}

			final Entity var12 = spawnCreature(worldIn, stack.getMetadata(), pos.getX() + 0.5D, pos.getY() + var13,
					pos.getZ() + 0.5D);

			if (var12 != null) {
				if (var12 instanceof EntityLivingBase && stack.hasDisplayName()) {
					var12.setCustomNameTag(stack.getDisplayName());
				}

				if (!playerIn.capabilities.isCreativeMode) {
					--stack.stackSize;
				}
			}

			return true;
		}
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		if (worldIn.isRemote) {
			return itemStackIn;
		} else {
			final MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);

			if (var4 == null) {
				return itemStackIn;
			} else {
				if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					final BlockPos var5 = var4.getBlockPos();

					if (!worldIn.isBlockModifiable(playerIn, var5)) {
						return itemStackIn;
					}

					if (!playerIn.func_175151_a(var5, var4.facing, itemStackIn)) {
						return itemStackIn;
					}

					if (worldIn.getBlockState(var5).getBlock() instanceof BlockLiquid) {
						final Entity var6 = spawnCreature(worldIn, itemStackIn.getMetadata(), var5.getX() + 0.5D,
								var5.getY() + 0.5D, var5.getZ() + 0.5D);

						if (var6 != null) {
							if (var6 instanceof EntityLivingBase && itemStackIn.hasDisplayName()) {
								((EntityLiving) var6).setCustomNameTag(itemStackIn.getDisplayName());
							}

							if (!playerIn.capabilities.isCreativeMode) {
								--itemStackIn.stackSize;
							}

							playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						}
					}
				}

				return itemStackIn;
			}
		}
	}

	/**
	 * Spawns the creature specified by the egg's type in the location specified
	 * by the last three parameters. Parameters: world, entityID, x, y, z.
	 */
	public static Entity spawnCreature(final World worldIn, final int p_77840_1_, final double p_77840_2_,
			final double p_77840_4_, final double p_77840_6_) {
		if (!EntityList.entityEggs.containsKey(p_77840_1_)) {
			return null;
		} else {
			Entity var8 = null;

			for (int var9 = 0; var9 < 1; ++var9) {
				var8 = EntityList.createEntityByID(p_77840_1_, worldIn);

				if (var8 instanceof EntityLivingBase) {
					final EntityLiving var10 = (EntityLiving) var8;
					var8.setLocationAndAngles(p_77840_2_, p_77840_4_, p_77840_6_,
							MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
					var10.rotationYawHead = var10.rotationYaw;
					var10.renderYawOffset = var10.rotationYaw;
					var10.func_180482_a(worldIn.getDifficultyForLocation(new BlockPos(var10)),
							(IEntityLivingData) null);
					worldIn.spawnEntityInWorld(var8);
					var10.playLivingSound();
				}
			}

			return var8;
		}
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 * 
	 * @param subItems
	 *            The List of sub-items. This is a List of ItemStacks.
	 */
	@Override
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final List subItems) {
		final Iterator var4 = EntityList.entityEggs.values().iterator();

		while (var4.hasNext()) {
			final EntityList.EntityEggInfo var5 = (EntityList.EntityEggInfo) var4.next();
			subItems.add(new ItemStack(itemIn, 1, var5.spawnedID));
		}
	}
}
