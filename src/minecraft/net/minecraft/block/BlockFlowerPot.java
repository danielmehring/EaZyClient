package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFlowerPot extends BlockContainer {

public static final int EaZy = 306;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger field_176444_a = PropertyInteger.create("legacy_data", 0, 15);
	public static final PropertyEnum field_176443_b = PropertyEnum.create("contents",
			BlockFlowerPot.EnumFlowerType.class);
	// private static final String __OBFID = "http://https://fuckuskid00000247";

	public BlockFlowerPot() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(field_176443_b, BlockFlowerPot.EnumFlowerType.EMPTY)
				.withProperty(field_176444_a, 0));
		setBlockBoundsForItemRender();
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		final float var1 = 0.375F;
		final float var2 = var1 / 2.0F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var1, 0.5F + var2);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
		final TileEntity var4 = worldIn.getTileEntity(pos);

		if (var4 instanceof TileEntityFlowerPot) {
			final Item var5 = ((TileEntityFlowerPot) var4).getFlowerPotItem();

			if (var5 instanceof ItemBlock) {
				return Block.getBlockFromItem(var5).colorMultiplier(worldIn, pos, renderPass);
			}
		}

		return 16777215;
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		final ItemStack var9 = playerIn.inventory.getCurrentItem();

		if (var9 != null && var9.getItem() instanceof ItemBlock) {
			final TileEntityFlowerPot var10 = func_176442_d(worldIn, pos);

			if (var10 == null) {
				return false;
			} else if (var10.getFlowerPotItem() != null) {
				return false;
			} else {
				final Block var11 = Block.getBlockFromItem(var9.getItem());

				if (!func_149928_a(var11, var9.getMetadata())) {
					return false;
				} else {
					var10.func_145964_a(var9.getItem(), var9.getMetadata());
					var10.markDirty();
					worldIn.markBlockForUpdate(pos);

					if (!playerIn.capabilities.isCreativeMode && --var9.stackSize <= 0) {
						playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
					}

					return true;
				}
			}
		} else {
			return false;
		}
	}

	private boolean func_149928_a(final Block p_149928_1_, final int p_149928_2_) {
		return p_149928_1_ != Blocks.yellow_flower && p_149928_1_ != Blocks.red_flower && p_149928_1_ != Blocks.cactus
				&& p_149928_1_ != Blocks.brown_mushroom && p_149928_1_ != Blocks.red_mushroom
				&& p_149928_1_ != Blocks.sapling && p_149928_1_ != Blocks.deadbush
						? p_149928_1_ == Blocks.tallgrass && p_149928_2_ == BlockTallGrass.EnumType.FERN.func_177044_a()
						: true;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		final TileEntityFlowerPot var3 = func_176442_d(worldIn, pos);
		return var3 != null && var3.getFlowerPotItem() != null ? var3.getFlowerPotItem() : Items.flower_pot;
	}

	@Override
	public int getDamageValue(final World worldIn, final BlockPos pos) {
		final TileEntityFlowerPot var3 = func_176442_d(worldIn, pos);
		return var3 != null && var3.getFlowerPotItem() != null ? var3.getFlowerPotData() : 0;
	}

	/**
	 * Returns true only if block is flowerPot
	 */
	@Override
	public boolean isFlowerPot() {
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown());
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown())) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		final TileEntityFlowerPot var4 = func_176442_d(worldIn, pos);

		if (var4 != null && var4.getFlowerPotItem() != null) {
			spawnAsEntity(worldIn, pos, new ItemStack(var4.getFlowerPotItem(), 1, var4.getFlowerPotData()));
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void onBlockHarvested(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn) {
		super.onBlockHarvested(worldIn, pos, state, playerIn);

		if (playerIn.capabilities.isCreativeMode) {
			final TileEntityFlowerPot var5 = func_176442_d(worldIn, pos);

			if (var5 != null) {
				var5.func_145964_a((Item) null, 0);
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
		return Items.flower_pot;
	}

	private TileEntityFlowerPot func_176442_d(final World worldIn, final BlockPos p_176442_2_) {
		final TileEntity var3 = worldIn.getTileEntity(p_176442_2_);
		return var3 instanceof TileEntityFlowerPot ? (TileEntityFlowerPot) var3 : null;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		Object var3 = null;
		int var4 = 0;

		switch (meta) {
			case 1:
				var3 = Blocks.red_flower;
				var4 = BlockFlower.EnumFlowerType.POPPY.func_176968_b();
				break;

			case 2:
				var3 = Blocks.yellow_flower;
				break;

			case 3:
				var3 = Blocks.sapling;
				var4 = BlockPlanks.EnumType.OAK.func_176839_a();
				break;

			case 4:
				var3 = Blocks.sapling;
				var4 = BlockPlanks.EnumType.SPRUCE.func_176839_a();
				break;

			case 5:
				var3 = Blocks.sapling;
				var4 = BlockPlanks.EnumType.BIRCH.func_176839_a();
				break;

			case 6:
				var3 = Blocks.sapling;
				var4 = BlockPlanks.EnumType.JUNGLE.func_176839_a();
				break;

			case 7:
				var3 = Blocks.red_mushroom;
				break;

			case 8:
				var3 = Blocks.brown_mushroom;
				break;

			case 9:
				var3 = Blocks.cactus;
				break;

			case 10:
				var3 = Blocks.deadbush;
				break;

			case 11:
				var3 = Blocks.tallgrass;
				var4 = BlockTallGrass.EnumType.FERN.func_177044_a();
				break;

			case 12:
				var3 = Blocks.sapling;
				var4 = BlockPlanks.EnumType.ACACIA.func_176839_a();
				break;

			case 13:
				var3 = Blocks.sapling;
				var4 = BlockPlanks.EnumType.DARK_OAK.func_176839_a();
		}

		return new TileEntityFlowerPot(Item.getItemFromBlock((Block) var3), var4);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176443_b, field_176444_a });
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(field_176444_a));
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		BlockFlowerPot.EnumFlowerType var4 = BlockFlowerPot.EnumFlowerType.EMPTY;
		final TileEntity var5 = worldIn.getTileEntity(pos);

		if (var5 instanceof TileEntityFlowerPot) {
			final TileEntityFlowerPot var6 = (TileEntityFlowerPot) var5;
			final Item var7 = var6.getFlowerPotItem();

			if (var7 instanceof ItemBlock) {
				final int var8 = var6.getFlowerPotData();
				final Block var9 = Block.getBlockFromItem(var7);

				if (var9 == Blocks.sapling) {
					switch (BlockFlowerPot.SwitchEnumType.field_180353_a[BlockPlanks.EnumType.func_176837_a(var8)
							.ordinal()]) {
						case 1:
							var4 = BlockFlowerPot.EnumFlowerType.OAK_SAPLING;
							break;

						case 2:
							var4 = BlockFlowerPot.EnumFlowerType.SPRUCE_SAPLING;
							break;

						case 3:
							var4 = BlockFlowerPot.EnumFlowerType.BIRCH_SAPLING;
							break;

						case 4:
							var4 = BlockFlowerPot.EnumFlowerType.JUNGLE_SAPLING;
							break;

						case 5:
							var4 = BlockFlowerPot.EnumFlowerType.ACACIA_SAPLING;
							break;

						case 6:
							var4 = BlockFlowerPot.EnumFlowerType.DARK_OAK_SAPLING;
							break;

						default:
							var4 = BlockFlowerPot.EnumFlowerType.EMPTY;
					}
				} else if (var9 == Blocks.tallgrass) {
					switch (var8) {
						case 0:
							var4 = BlockFlowerPot.EnumFlowerType.DEAD_BUSH;
							break;

						case 2:
							var4 = BlockFlowerPot.EnumFlowerType.FERN;
							break;

						default:
							var4 = BlockFlowerPot.EnumFlowerType.EMPTY;
					}
				} else if (var9 == Blocks.yellow_flower) {
					var4 = BlockFlowerPot.EnumFlowerType.DANDELION;
				} else if (var9 == Blocks.red_flower) {
					switch (BlockFlowerPot.SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType
							.func_176967_a(BlockFlower.EnumFlowerColor.RED, var8).ordinal()]) {
						case 1:
							var4 = BlockFlowerPot.EnumFlowerType.POPPY;
							break;

						case 2:
							var4 = BlockFlowerPot.EnumFlowerType.BLUE_ORCHID;
							break;

						case 3:
							var4 = BlockFlowerPot.EnumFlowerType.ALLIUM;
							break;

						case 4:
							var4 = BlockFlowerPot.EnumFlowerType.HOUSTONIA;
							break;

						case 5:
							var4 = BlockFlowerPot.EnumFlowerType.RED_TULIP;
							break;

						case 6:
							var4 = BlockFlowerPot.EnumFlowerType.ORANGE_TULIP;
							break;

						case 7:
							var4 = BlockFlowerPot.EnumFlowerType.WHITE_TULIP;
							break;

						case 8:
							var4 = BlockFlowerPot.EnumFlowerType.PINK_TULIP;
							break;

						case 9:
							var4 = BlockFlowerPot.EnumFlowerType.OXEYE_DAISY;
							break;

						default:
							var4 = BlockFlowerPot.EnumFlowerType.EMPTY;
					}
				} else if (var9 == Blocks.red_mushroom) {
					var4 = BlockFlowerPot.EnumFlowerType.MUSHROOM_RED;
				} else if (var9 == Blocks.brown_mushroom) {
					var4 = BlockFlowerPot.EnumFlowerType.MUSHROOM_BROWN;
				} else if (var9 == Blocks.deadbush) {
					var4 = BlockFlowerPot.EnumFlowerType.DEAD_BUSH;
				} else if (var9 == Blocks.cactus) {
					var4 = BlockFlowerPot.EnumFlowerType.CACTUS;
				}
			}
		}

		return state.withProperty(field_176443_b, var4);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	public static enum EnumFlowerType implements IStringSerializable {
		EMPTY("EMPTY", 0, "empty"), POPPY("POPPY", 1, "rose"), BLUE_ORCHID("BLUE_ORCHID", 2, "blue_orchid"), ALLIUM(
				"ALLIUM", 3,
				"allium"), HOUSTONIA("HOUSTONIA", 4, "houstonia"), RED_TULIP("RED_TULIP", 5, "red_tulip"), ORANGE_TULIP(
						"ORANGE_TULIP", 6,
						"orange_tulip"), WHITE_TULIP("WHITE_TULIP", 7, "white_tulip"), PINK_TULIP("PINK_TULIP", 8,
								"pink_tulip"), OXEYE_DAISY("OXEYE_DAISY", 9, "oxeye_daisy"), DANDELION("DANDELION", 10,
										"dandelion"), OAK_SAPLING("OAK_SAPLING", 11, "oak_sapling"), SPRUCE_SAPLING(
												"SPRUCE_SAPLING", 12, "spruce_sapling"), BIRCH_SAPLING("BIRCH_SAPLING",
														13, "birch_sapling"), JUNGLE_SAPLING("JUNGLE_SAPLING", 14,
																"jungle_sapling"), ACACIA_SAPLING("ACACIA_SAPLING", 15,
																		"acacia_sapling"), DARK_OAK_SAPLING(
																				"DARK_OAK_SAPLING", 16,
																				"dark_oak_sapling"), MUSHROOM_RED(
																						"MUSHROOM_RED", 17,
																						"mushroom_red"), MUSHROOM_BROWN(
																								"MUSHROOM_BROWN", 18,
																								"mushroom_brown"), DEAD_BUSH(
																										"DEAD_BUSH", 19,
																										"dead_bush"), FERN(
																												"FERN",
																												20,
																												"fern"), CACTUS(
																														"CACTUS",
																														21,
																														"cactus");
		private final String field_177006_w;

		private EnumFlowerType(final String p_i45715_1_, final int p_i45715_2_, final String p_i45715_3_) {
			field_177006_w = p_i45715_3_;
		}

		@Override
		public String toString() {
			return field_177006_w;
		}

		@Override
		public String getName() {
			return field_177006_w;
		}
	}

	static final class SwitchEnumType {
		static final int[] field_180353_a;

		static final int[] field_180352_b = new int[BlockFlower.EnumFlowerType.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002116";

		static {
			try {
				field_180352_b[BlockFlower.EnumFlowerType.POPPY.ordinal()] = 1;
			} catch (final NoSuchFieldError var15) {
			}

			try {
				field_180352_b[BlockFlower.EnumFlowerType.BLUE_ORCHID.ordinal()] = 2;
			} catch (final NoSuchFieldError var14) {
			}

			try {
				field_180352_b[BlockFlower.EnumFlowerType.ALLIUM.ordinal()] = 3;
			} catch (final NoSuchFieldError var13) {
			}

			try {
				field_180352_b[BlockFlower.EnumFlowerType.HOUSTONIA.ordinal()] = 4;
			} catch (final NoSuchFieldError var12) {
			}

			try {
				field_180352_b[BlockFlower.EnumFlowerType.RED_TULIP.ordinal()] = 5;
			} catch (final NoSuchFieldError var11) {
			}

			try {
				field_180352_b[BlockFlower.EnumFlowerType.ORANGE_TULIP.ordinal()] = 6;
			} catch (final NoSuchFieldError var10) {
			}

			try {
				field_180352_b[BlockFlower.EnumFlowerType.WHITE_TULIP.ordinal()] = 7;
			} catch (final NoSuchFieldError var9) {
			}

			try {
				field_180352_b[BlockFlower.EnumFlowerType.PINK_TULIP.ordinal()] = 8;
			} catch (final NoSuchFieldError var8) {
			}

			try {
				field_180352_b[BlockFlower.EnumFlowerType.OXEYE_DAISY.ordinal()] = 9;
			} catch (final NoSuchFieldError var7) {
			}

			field_180353_a = new int[BlockPlanks.EnumType.values().length];

			try {
				field_180353_a[BlockPlanks.EnumType.OAK.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_180353_a[BlockPlanks.EnumType.SPRUCE.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_180353_a[BlockPlanks.EnumType.BIRCH.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_180353_a[BlockPlanks.EnumType.JUNGLE.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_180353_a[BlockPlanks.EnumType.ACACIA.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_180353_a[BlockPlanks.EnumType.DARK_OAK.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
