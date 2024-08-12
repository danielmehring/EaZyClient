package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Iterator;
import java.util.Random;

public class BlockBed extends BlockDirectional {

public static final int EaZy = 260;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum PART_PROP = PropertyEnum.create("part", BlockBed.EnumPartType.class);
	public static final PropertyBool OCCUPIED_PROP = PropertyBool.create("occupied");
	// private static final String __OBFID = "http://https://fuckuskid00000198";

	public BlockBed() {
		super(Material.cloth);
		setDefaultState(blockState.getBaseState().withProperty(PART_PROP, BlockBed.EnumPartType.FOOT)
				.withProperty(OCCUPIED_PROP, false));
		setBedBounds();
	}

	@Override
	public boolean onBlockActivated(final World worldIn, BlockPos pos, IBlockState state, final EntityPlayer playerIn,
			final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			if (state.getValue(PART_PROP) != BlockBed.EnumPartType.HEAD) {
				pos = pos.offset((EnumFacing) state.getValue(AGE));
				state = worldIn.getBlockState(pos);

				if (state.getBlock() != this) {
					return true;
				}
			}

			if (worldIn.provider.canRespawnHere() && worldIn.getBiomeGenForCoords(pos) != BiomeGenBase.hell) {
				if (((Boolean) state.getValue(OCCUPIED_PROP))) {
					final EntityPlayer var10 = func_176470_e(worldIn, pos);

					if (var10 != null) {
						playerIn.addChatComponentMessage(
								new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
						return true;
					}

					state = state.withProperty(OCCUPIED_PROP, false);
					worldIn.setBlockState(pos, state, 4);
				}

				final EntityPlayer.EnumStatus var11 = playerIn.func_180469_a(pos);

				if (var11 == EntityPlayer.EnumStatus.OK) {
					state = state.withProperty(OCCUPIED_PROP, true);
					worldIn.setBlockState(pos, state, 4);
					return true;
				} else {
					if (var11 == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
						playerIn.addChatComponentMessage(
								new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
					} else if (var11 == EntityPlayer.EnumStatus.NOT_SAFE) {
						playerIn.addChatComponentMessage(
								new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
					}

					return true;
				}
			} else {
				worldIn.setBlockToAir(pos);
				final BlockPos var9 = pos.offset(((EnumFacing) state.getValue(AGE)).getOpposite());

				if (worldIn.getBlockState(var9).getBlock() == this) {
					worldIn.setBlockToAir(var9);
				}

				worldIn.newExplosion((Entity) null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, true,
						true);
				return true;
			}
		}
	}

	private EntityPlayer func_176470_e(final World worldIn, final BlockPos p_176470_2_) {
		final Iterator var3 = worldIn.playerEntities.iterator();
		EntityPlayer var4;

		do {
			if (!var3.hasNext()) {
				return null;
			}

			var4 = (EntityPlayer) var3.next();
		}
		while (!var4.isPlayerSleeping() || !var4.playerLocation.equals(p_176470_2_));

		return var4;
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
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		setBedBounds();
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		final EnumFacing var5 = (EnumFacing) state.getValue(AGE);

		if (state.getValue(PART_PROP) == BlockBed.EnumPartType.HEAD) {
			if (worldIn.getBlockState(pos.offset(var5.getOpposite())).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}
		} else if (worldIn.getBlockState(pos.offset(var5)).getBlock() != this) {
			worldIn.setBlockToAir(pos);

			if (!worldIn.isRemote) {
				dropBlockAsItem(worldIn, pos, state, 0);
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
		return state.getValue(PART_PROP) == BlockBed.EnumPartType.HEAD ? null : Items.bed;
	}

	private void setBedBounds() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
	}

	/**
	 * Returns a safe BlockPos to disembark the bed
	 */
	public static BlockPos getSafeExitLocation(final World worldIn, final BlockPos p_176468_1_, int p_176468_2_) {
		final EnumFacing var3 = (EnumFacing) worldIn.getBlockState(p_176468_1_).getValue(AGE);
		final int var4 = p_176468_1_.getX();
		final int var5 = p_176468_1_.getY();
		final int var6 = p_176468_1_.getZ();

		for (int var7 = 0; var7 <= 1; ++var7) {
			final int var8 = var4 - var3.getFrontOffsetX() * var7 - 1;
			final int var9 = var6 - var3.getFrontOffsetZ() * var7 - 1;
			final int var10 = var8 + 2;
			final int var11 = var9 + 2;

			for (int var12 = var8; var12 <= var10; ++var12) {
				for (int var13 = var9; var13 <= var11; ++var13) {
					final BlockPos var14 = new BlockPos(var12, var5, var13);

					if (func_176469_d(worldIn, var14)) {
						if (p_176468_2_ <= 0) {
							return var14;
						}

						--p_176468_2_;
					}
				}
			}
		}

		return null;
	}

	protected static boolean func_176469_d(final World worldIn, final BlockPos p_176469_1_) {
		return World.doesBlockHaveSolidTopSurface(worldIn, p_176469_1_.offsetDown())
				&& !worldIn.getBlockState(p_176469_1_).getBlock().getMaterial().isSolid()
				&& !worldIn.getBlockState(p_176469_1_.offsetUp()).getBlock().getMaterial().isSolid();
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
		if (state.getValue(PART_PROP) == BlockBed.EnumPartType.FOOT) {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
		}
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.bed;
	}

	@Override
	public void onBlockHarvested(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn) {
		if (playerIn.capabilities.isCreativeMode && state.getValue(PART_PROP) == BlockBed.EnumPartType.HEAD) {
			final BlockPos var5 = pos.offset(((EnumFacing) state.getValue(AGE)).getOpposite());

			if (worldIn.getBlockState(var5).getBlock() == this) {
				worldIn.setBlockToAir(var5);
			}
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		final EnumFacing var2 = EnumFacing.getHorizontal(meta);
		return (meta & 8) > 0
				? getDefaultState().withProperty(PART_PROP, BlockBed.EnumPartType.HEAD).withProperty(AGE, var2)
						.withProperty(OCCUPIED_PROP, (meta & 4) > 0)
				: getDefaultState().withProperty(PART_PROP, BlockBed.EnumPartType.FOOT).withProperty(AGE, var2);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		if (state.getValue(PART_PROP) == BlockBed.EnumPartType.FOOT) {
			final IBlockState var4 = worldIn.getBlockState(pos.offset((EnumFacing) state.getValue(AGE)));

			if (var4.getBlock() == this) {
				state = state.withProperty(OCCUPIED_PROP, var4.getValue(OCCUPIED_PROP));
			}
		}

		return state;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(AGE)).getHorizontalIndex();

		if (state.getValue(PART_PROP) == BlockBed.EnumPartType.HEAD) {
			var3 |= 8;

			if (((Boolean) state.getValue(OCCUPIED_PROP))) {
				var3 |= 4;
			}
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE, PART_PROP, OCCUPIED_PROP });
	}

	public static enum EnumPartType implements IStringSerializable {
		HEAD("HEAD", 0, "head"), FOOT("FOOT", 1, "foot");
		private final String field_177036_c;

		private EnumPartType(final String p_i45735_1_, final int p_i45735_2_, final String p_i45735_3_) {
			field_177036_c = p_i45735_3_;
		}

		@Override
		public String toString() {
			return field_177036_c;
		}

		@Override
		public String getName() {
			return field_177036_c;
		}
	}
}
