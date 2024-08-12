package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class StructureStrongholdPieces {

public static final int EaZy = 1824;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final StructureStrongholdPieces.PieceWeight[] pieceWeightArray = new StructureStrongholdPieces.PieceWeight[] {
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Straight.class, 40, 0),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Prison.class, 5, 5),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.LeftTurn.class, 20, 0),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RightTurn.class, 20, 0),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RoomCrossing.class, 10, 6),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.StairsStraight.class, 5, 5),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Stairs.class, 5, 5),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Crossing.class, 5, 4),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.ChestCorridor.class, 5, 4),
			new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Library.class, 10, 2) {
				// private static final String __OBFID =
				// "http://https://fuckuskid00000484";
				@Override
				public boolean canSpawnMoreStructuresOfType(final int p_75189_1_) {
					return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4;
				}
			}, new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.PortalRoom.class, 20, 1) {
				// private static final String __OBFID =
				// "http://https://fuckuskid00000485";
				@Override
				public boolean canSpawnMoreStructuresOfType(final int p_75189_1_) {
					return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5;
				}
			} };
	private static List structurePieceList;
	private static Class strongComponentType;
	static int totalWeight;
	private static final StructureStrongholdPieces.Stones strongholdStones = new StructureStrongholdPieces.Stones(null);
	// private static final String __OBFID = "http://https://fuckuskid00000483";

	public static void registerStrongholdPieces() {
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.ChestCorridor.class, "SHCC");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Corridor.class, "SHFC");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Crossing.class, "SH5C");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.LeftTurn.class, "SHLT");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Library.class, "SHLi");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.PortalRoom.class, "SHPR");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Prison.class, "SHPH");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.RightTurn.class, "SHRT");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.RoomCrossing.class, "SHRC");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Stairs.class, "SHSD");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Stairs2.class, "SHStart");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Straight.class, "SHS");
		MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.StairsStraight.class, "SHSSD");
	}

	/**
	 * sets up Arrays with the Structure pieces and their weights
	 */
	public static void prepareStructurePieces() {
		structurePieceList = Lists.newArrayList();
		final StructureStrongholdPieces.PieceWeight[] var0 = pieceWeightArray;
		final int var1 = var0.length;

		for (int var2 = 0; var2 < var1; ++var2) {
			final StructureStrongholdPieces.PieceWeight var3 = var0[var2];
			var3.instancesSpawned = 0;
			structurePieceList.add(var3);
		}

		strongComponentType = null;
	}

	private static boolean canAddStructurePieces() {
		boolean var0 = false;
		totalWeight = 0;
		StructureStrongholdPieces.PieceWeight var2;

		for (final Iterator var1 = structurePieceList.iterator(); var1.hasNext(); totalWeight += var2.pieceWeight) {
			var2 = (StructureStrongholdPieces.PieceWeight) var1.next();

			if (var2.instancesLimit > 0 && var2.instancesSpawned < var2.instancesLimit) {
				var0 = true;
			}
		}

		return var0;
	}

	private static StructureStrongholdPieces.Stronghold func_175954_a(final Class p_175954_0_, final List p_175954_1_,
			final Random p_175954_2_, final int p_175954_3_, final int p_175954_4_, final int p_175954_5_,
			final EnumFacing p_175954_6_, final int p_175954_7_) {
		Object var8 = null;

		if (p_175954_0_ == StructureStrongholdPieces.Straight.class) {
			var8 = StructureStrongholdPieces.Straight.func_175862_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_,
					p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.Prison.class) {
			var8 = StructureStrongholdPieces.Prison.func_175860_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_,
					p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.LeftTurn.class) {
			var8 = StructureStrongholdPieces.LeftTurn.func_175867_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_,
					p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.RightTurn.class) {
			var8 = LeftTurn.func_175867_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_,
					p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.RoomCrossing.class) {
			var8 = StructureStrongholdPieces.RoomCrossing.func_175859_a(p_175954_1_, p_175954_2_, p_175954_3_,
					p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.StairsStraight.class) {
			var8 = StructureStrongholdPieces.StairsStraight.func_175861_a(p_175954_1_, p_175954_2_, p_175954_3_,
					p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.Stairs.class) {
			var8 = StructureStrongholdPieces.Stairs.func_175863_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_,
					p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.Crossing.class) {
			var8 = StructureStrongholdPieces.Crossing.func_175866_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_,
					p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.ChestCorridor.class) {
			var8 = StructureStrongholdPieces.ChestCorridor.func_175868_a(p_175954_1_, p_175954_2_, p_175954_3_,
					p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.Library.class) {
			var8 = StructureStrongholdPieces.Library.func_175864_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_,
					p_175954_5_, p_175954_6_, p_175954_7_);
		} else if (p_175954_0_ == StructureStrongholdPieces.PortalRoom.class) {
			var8 = StructureStrongholdPieces.PortalRoom.func_175865_a(p_175954_1_, p_175954_2_, p_175954_3_,
					p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
		}

		return (StructureStrongholdPieces.Stronghold) var8;
	}

	private static StructureStrongholdPieces.Stronghold func_175955_b(
			final StructureStrongholdPieces.Stairs2 p_175955_0_, final List p_175955_1_, final Random p_175955_2_,
			final int p_175955_3_, final int p_175955_4_, final int p_175955_5_, final EnumFacing p_175955_6_,
			final int p_175955_7_) {
		if (!canAddStructurePieces()) {
			return null;
		} else {
			if (strongComponentType != null) {
				final StructureStrongholdPieces.Stronghold var8 = func_175954_a(strongComponentType, p_175955_1_,
						p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
				strongComponentType = null;

				if (var8 != null) {
					return var8;
				}
			}

			int var13 = 0;

			while (var13 < 5) {
				++var13;
				int var9 = p_175955_2_.nextInt(totalWeight);
				final Iterator var10 = structurePieceList.iterator();

				while (var10.hasNext()) {
					final StructureStrongholdPieces.PieceWeight var11 = (StructureStrongholdPieces.PieceWeight) var10
							.next();
					var9 -= var11.pieceWeight;

					if (var9 < 0) {
						if (!var11.canSpawnMoreStructuresOfType(p_175955_7_)
								|| var11 == p_175955_0_.strongholdPieceWeight) {
							break;
						}

						final StructureStrongholdPieces.Stronghold var12 = func_175954_a(var11.pieceClass, p_175955_1_,
								p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);

						if (var12 != null) {
							++var11.instancesSpawned;
							p_175955_0_.strongholdPieceWeight = var11;

							if (!var11.canSpawnMoreStructures()) {
								structurePieceList.remove(var11);
							}

							return var12;
						}
					}
				}
			}

			final StructureBoundingBox var14 = StructureStrongholdPieces.Corridor.func_175869_a(p_175955_1_,
					p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);

			if (var14 != null && var14.minY > 1) {
				return new StructureStrongholdPieces.Corridor(p_175955_7_, p_175955_2_, var14, p_175955_6_);
			} else {
				return null;
			}
		}
	}

	private static StructureComponent func_175953_c(final StructureStrongholdPieces.Stairs2 p_175953_0_,
			final List p_175953_1_, final Random p_175953_2_, final int p_175953_3_, final int p_175953_4_,
			final int p_175953_5_, final EnumFacing p_175953_6_, final int p_175953_7_) {
		if (p_175953_7_ > 50) {
			return null;
		} else if (Math.abs(p_175953_3_ - p_175953_0_.getBoundingBox().minX) <= 112
				&& Math.abs(p_175953_5_ - p_175953_0_.getBoundingBox().minZ) <= 112) {
			final StructureStrongholdPieces.Stronghold var8 = func_175955_b(p_175953_0_, p_175953_1_, p_175953_2_,
					p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);

			if (var8 != null) {
				p_175953_1_.add(var8);
				p_175953_0_.field_75026_c.add(var8);
			}

			return var8;
		} else {
			return null;
		}
	}

	public static class ChestCorridor extends StructureStrongholdPieces.Stronghold {
		private static final List strongholdChestContents = Lists.newArrayList(
				new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10),
						new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
						new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
						new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
						new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
						new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
						new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
						new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
		private boolean hasMadeChest;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000487";

		public ChestCorridor() {}

		public ChestCorridor(final int p_i45582_1_, final Random p_i45582_2_, final StructureBoundingBox p_i45582_3_,
				final EnumFacing p_i45582_4_) {
			super(p_i45582_1_);
			coordBaseMode = p_i45582_4_;
			field_143013_d = getRandomDoor(p_i45582_2_);
			boundingBox = p_i45582_3_;
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Chest", hasMadeChest);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			hasMadeChest = p_143011_1_.getBoolean("Chest");
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		}

		public static StructureStrongholdPieces.ChestCorridor func_175868_a(final List p_175868_0_,
				final Random p_175868_1_, final int p_175868_2_, final int p_175868_3_, final int p_175868_4_,
				final EnumFacing p_175868_5_, final int p_175868_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175868_2_, p_175868_3_, p_175868_4_,
					-1, -1, 0, 5, 5, 7, p_175868_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175868_0_, var7) == null
					? new StructureStrongholdPieces.ChestCorridor(p_175868_6_, p_175868_1_, var7, p_175868_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 1, 1, 0);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
				func_175804_a(worldIn, p_74875_3_, 3, 1, 2, 3, 1, 4, Blocks.stonebrick.getDefaultState(),
						Blocks.stonebrick.getDefaultState(), false);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 1,
						1, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 1,
						5, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 2,
						2, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 3, 2,
						4, p_74875_3_);

				for (int var4 = 2; var4 <= 4; ++var4) {
					func_175811_a(worldIn,
							Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()), 2,
							1, var4, p_74875_3_);
				}

				if (!hasMadeChest && p_74875_3_
						.func_175898_b(new BlockPos(getXWithOffset(3, 3), getYWithOffset(2), getZWithOffset(3, 3)))) {
					hasMadeChest = true;
					func_180778_a(worldIn, p_74875_3_, p_74875_2_, 3, 2, 3,
							WeightedRandomChestContent.func_177629_a(strongholdChestContents,
									new WeightedRandomChestContent[] {
											Items.enchanted_book.getRandomEnchantedBook(p_74875_2_) }),
							2 + p_74875_2_.nextInt(2));
				}

				return true;
			}
		}
	}

	public static class Corridor extends StructureStrongholdPieces.Stronghold {
		private int field_74993_a;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000488";

		public Corridor() {}

		public Corridor(final int p_i45581_1_, final Random p_i45581_2_, final StructureBoundingBox p_i45581_3_,
				final EnumFacing p_i45581_4_) {
			super(p_i45581_1_);
			coordBaseMode = p_i45581_4_;
			boundingBox = p_i45581_3_;
			field_74993_a = p_i45581_4_ != EnumFacing.NORTH && p_i45581_4_ != EnumFacing.SOUTH ? p_i45581_3_.getXSize()
					: p_i45581_3_.getZSize();
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setInteger("Steps", field_74993_a);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			field_74993_a = p_143011_1_.getInteger("Steps");
		}

		public static StructureBoundingBox func_175869_a(final List p_175869_0_, final Random p_175869_1_,
				final int p_175869_2_, final int p_175869_3_, final int p_175869_4_, final EnumFacing p_175869_5_) {
			StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1,
					-1, 0, 5, 5, 4, p_175869_5_);
			final StructureComponent var8 = StructureComponent.findIntersecting(p_175869_0_, var7);

			if (var8 == null) {
				return null;
			} else {
				if (var8.getBoundingBox().minY == var7.minY) {
					for (int var9 = 3; var9 >= 1; --var9) {
						var7 = StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5,
								5, var9 - 1, p_175869_5_);

						if (!var8.getBoundingBox().intersectsWith(var7)) {
							return StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0,
									5, 5, var9, p_175869_5_);
						}
					}
				}

				return null;
			}
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				for (int var4 = 0; var4 < field_74993_a; ++var4) {
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 0, 0, var4, p_74875_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 0, var4, p_74875_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 0, var4, p_74875_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 0, var4, p_74875_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 4, 0, var4, p_74875_3_);

					for (int var5 = 1; var5 <= 3; ++var5) {
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 0, var5, var4, p_74875_3_);
						func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, var5, var4, p_74875_3_);
						func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, var5, var4, p_74875_3_);
						func_175811_a(worldIn, Blocks.air.getDefaultState(), 3, var5, var4, p_74875_3_);
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 4, var5, var4, p_74875_3_);
					}

					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 0, 4, var4, p_74875_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, var4, p_74875_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, var4, p_74875_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 4, var4, p_74875_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 4, 4, var4, p_74875_3_);
				}

				return true;
			}
		}
	}

	public static class Crossing extends StructureStrongholdPieces.Stronghold {
		private boolean field_74996_b;
		private boolean field_74997_c;
		private boolean field_74995_d;
		private boolean field_74999_h;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000489";

		public Crossing() {}

		public Crossing(final int p_i45580_1_, final Random p_i45580_2_, final StructureBoundingBox p_i45580_3_,
				final EnumFacing p_i45580_4_) {
			super(p_i45580_1_);
			coordBaseMode = p_i45580_4_;
			field_143013_d = getRandomDoor(p_i45580_2_);
			boundingBox = p_i45580_3_;
			field_74996_b = p_i45580_2_.nextBoolean();
			field_74997_c = p_i45580_2_.nextBoolean();
			field_74995_d = p_i45580_2_.nextBoolean();
			field_74999_h = p_i45580_2_.nextInt(3) > 0;
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("leftLow", field_74996_b);
			p_143012_1_.setBoolean("leftHigh", field_74997_c);
			p_143012_1_.setBoolean("rightLow", field_74995_d);
			p_143012_1_.setBoolean("rightHigh", field_74999_h);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			field_74996_b = p_143011_1_.getBoolean("leftLow");
			field_74997_c = p_143011_1_.getBoolean("leftHigh");
			field_74995_d = p_143011_1_.getBoolean("rightLow");
			field_74999_h = p_143011_1_.getBoolean("rightHigh");
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			int var4 = 3;
			int var5 = 5;

			if (coordBaseMode == EnumFacing.WEST || coordBaseMode == EnumFacing.NORTH) {
				var4 = 8 - var4;
				var5 = 8 - var5;
			}

			getNextComponentNormal((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 5, 1);

			if (field_74996_b) {
				getNextComponentX((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
			}

			if (field_74997_c) {
				getNextComponentX((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
			}

			if (field_74995_d) {
				getNextComponentZ((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
			}

			if (field_74999_h) {
				getNextComponentZ((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
			}
		}

		public static StructureStrongholdPieces.Crossing func_175866_a(final List p_175866_0_, final Random p_175866_1_,
				final int p_175866_2_, final int p_175866_3_, final int p_175866_4_, final EnumFacing p_175866_5_,
				final int p_175866_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175866_2_, p_175866_3_, p_175866_4_,
					-4, -3, 0, 10, 9, 11, p_175866_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175866_0_, var7) == null
					? new StructureStrongholdPieces.Crossing(p_175866_6_, p_175866_1_, var7, p_175866_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 9, 8, 10, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 4, 3, 0);

				if (field_74996_b) {
					func_175804_a(worldIn, p_74875_3_, 0, 3, 1, 0, 5, 3, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				if (field_74995_d) {
					func_175804_a(worldIn, p_74875_3_, 9, 3, 1, 9, 5, 3, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				if (field_74997_c) {
					func_175804_a(worldIn, p_74875_3_, 0, 5, 7, 0, 7, 9, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				if (field_74999_h) {
					func_175804_a(worldIn, p_74875_3_, 9, 5, 7, 9, 7, 9, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				func_175804_a(worldIn, p_74875_3_, 5, 1, 10, 7, 3, 10, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 2, 1, 8, 2, 6, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 5, 4, 4, 9, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 8, 1, 5, 8, 4, 9, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 4, 7, 3, 4, 9, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 3, 5, 3, 3, 6, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				func_175804_a(worldIn, p_74875_3_, 1, 3, 4, 3, 3, 4, Blocks.stone_slab.getDefaultState(),
						Blocks.stone_slab.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 1, 4, 6, 3, 4, 6, Blocks.stone_slab.getDefaultState(),
						Blocks.stone_slab.getDefaultState(), false);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 5, 1, 7, 7, 1, 8, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				func_175804_a(worldIn, p_74875_3_, 5, 1, 9, 7, 1, 9, Blocks.stone_slab.getDefaultState(),
						Blocks.stone_slab.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 5, 2, 7, 7, 2, 7, Blocks.stone_slab.getDefaultState(),
						Blocks.stone_slab.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 4, 5, 7, 4, 5, 9, Blocks.stone_slab.getDefaultState(),
						Blocks.stone_slab.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 8, 5, 7, 8, 5, 9, Blocks.stone_slab.getDefaultState(),
						Blocks.stone_slab.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab.getDefaultState(),
						Blocks.double_stone_slab.getDefaultState(), false);
				func_175811_a(worldIn, Blocks.torch.getDefaultState(), 6, 5, 6, p_74875_3_);
				return true;
			}
		}
	}

	public static class LeftTurn extends StructureStrongholdPieces.Stronghold {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000490";

		public LeftTurn() {}

		public LeftTurn(final int p_i45579_1_, final Random p_i45579_2_, final StructureBoundingBox p_i45579_3_,
				final EnumFacing p_i45579_4_) {
			super(p_i45579_1_);
			coordBaseMode = p_i45579_4_;
			field_143013_d = getRandomDoor(p_i45579_2_);
			boundingBox = p_i45579_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.EAST) {
				getNextComponentZ((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
			} else {
				getNextComponentX((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
			}
		}

		public static StructureStrongholdPieces.LeftTurn func_175867_a(final List p_175867_0_, final Random p_175867_1_,
				final int p_175867_2_, final int p_175867_3_, final int p_175867_4_, final EnumFacing p_175867_5_,
				final int p_175867_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175867_2_, p_175867_3_, p_175867_4_,
					-1, -1, 0, 5, 5, 5, p_175867_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175867_0_, var7) == null
					? new StructureStrongholdPieces.LeftTurn(p_175867_6_, p_175867_1_, var7, p_175867_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 1, 1, 0);

				if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.EAST) {
					func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				} else {
					func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				return true;
			}
		}
	}

	public static class Library extends StructureStrongholdPieces.Stronghold {
		private static final List strongholdLibraryChestContents = Lists.newArrayList(
				new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.book, 0, 1, 3, 20),
						new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20),
						new WeightedRandomChestContent(Items.map, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1) });
		private boolean isLargeRoom;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000491";

		public Library() {}

		public Library(final int p_i45578_1_, final Random p_i45578_2_, final StructureBoundingBox p_i45578_3_,
				final EnumFacing p_i45578_4_) {
			super(p_i45578_1_);
			coordBaseMode = p_i45578_4_;
			field_143013_d = getRandomDoor(p_i45578_2_);
			boundingBox = p_i45578_3_;
			isLargeRoom = p_i45578_3_.getYSize() > 6;
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Tall", isLargeRoom);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			isLargeRoom = p_143011_1_.getBoolean("Tall");
		}

		public static StructureStrongholdPieces.Library func_175864_a(final List p_175864_0_, final Random p_175864_1_,
				final int p_175864_2_, final int p_175864_3_, final int p_175864_4_, final EnumFacing p_175864_5_,
				final int p_175864_6_) {
			StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175864_2_, p_175864_3_, p_175864_4_, -4,
					-1, 0, 14, 11, 15, p_175864_5_);

			if (!canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(p_175864_0_, var7) != null) {
				var7 = StructureBoundingBox.func_175897_a(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15,
						p_175864_5_);

				if (!canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(p_175864_0_, var7) != null) {
					return null;
				}
			}

			return new StructureStrongholdPieces.Library(p_175864_6_, p_175864_1_, var7, p_175864_5_);
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				byte var4 = 11;

				if (!isLargeRoom) {
					var4 = 6;
				}

				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 13, var4 - 1, 14, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 4, 1, 0);
				func_175805_a(worldIn, p_74875_3_, p_74875_2_, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.web.getDefaultState(),
						Blocks.web.getDefaultState(), false);
				int var7;

				for (var7 = 1; var7 <= 13; ++var7) {
					if ((var7 - 1) % 4 == 0) {
						func_175804_a(worldIn, p_74875_3_, 1, 1, var7, 1, 4, var7, Blocks.planks.getDefaultState(),
								Blocks.planks.getDefaultState(), false);
						func_175804_a(worldIn, p_74875_3_, 12, 1, var7, 12, 4, var7, Blocks.planks.getDefaultState(),
								Blocks.planks.getDefaultState(), false);
						func_175811_a(worldIn, Blocks.torch.getDefaultState(), 2, 3, var7, p_74875_3_);
						func_175811_a(worldIn, Blocks.torch.getDefaultState(), 11, 3, var7, p_74875_3_);

						if (isLargeRoom) {
							func_175804_a(worldIn, p_74875_3_, 1, 6, var7, 1, 9, var7, Blocks.planks.getDefaultState(),
									Blocks.planks.getDefaultState(), false);
							func_175804_a(worldIn, p_74875_3_, 12, 6, var7, 12, 9, var7,
									Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
						}
					} else {
						func_175804_a(worldIn, p_74875_3_, 1, 1, var7, 1, 4, var7, Blocks.bookshelf.getDefaultState(),
								Blocks.bookshelf.getDefaultState(), false);
						func_175804_a(worldIn, p_74875_3_, 12, 1, var7, 12, 4, var7, Blocks.bookshelf.getDefaultState(),
								Blocks.bookshelf.getDefaultState(), false);

						if (isLargeRoom) {
							func_175804_a(worldIn, p_74875_3_, 1, 6, var7, 1, 9, var7,
									Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
							func_175804_a(worldIn, p_74875_3_, 12, 6, var7, 12, 9, var7,
									Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
						}
					}
				}

				for (var7 = 3; var7 < 12; var7 += 2) {
					func_175804_a(worldIn, p_74875_3_, 3, 1, var7, 4, 3, var7, Blocks.bookshelf.getDefaultState(),
							Blocks.bookshelf.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 6, 1, var7, 7, 3, var7, Blocks.bookshelf.getDefaultState(),
							Blocks.bookshelf.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 9, 1, var7, 10, 3, var7, Blocks.bookshelf.getDefaultState(),
							Blocks.bookshelf.getDefaultState(), false);
				}

				if (isLargeRoom) {
					func_175804_a(worldIn, p_74875_3_, 1, 5, 1, 3, 5, 13, Blocks.planks.getDefaultState(),
							Blocks.planks.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 10, 5, 1, 12, 5, 13, Blocks.planks.getDefaultState(),
							Blocks.planks.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 4, 5, 1, 9, 5, 2, Blocks.planks.getDefaultState(),
							Blocks.planks.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 4, 5, 12, 9, 5, 13, Blocks.planks.getDefaultState(),
							Blocks.planks.getDefaultState(), false);
					func_175811_a(worldIn, Blocks.planks.getDefaultState(), 9, 5, 11, p_74875_3_);
					func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 5, 11, p_74875_3_);
					func_175811_a(worldIn, Blocks.planks.getDefaultState(), 9, 5, 10, p_74875_3_);
					func_175804_a(worldIn, p_74875_3_, 3, 6, 2, 3, 6, 12, Blocks.oak_fence.getDefaultState(),
							Blocks.oak_fence.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 10, 6, 2, 10, 6, 10, Blocks.oak_fence.getDefaultState(),
							Blocks.oak_fence.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 4, 6, 2, 9, 6, 2, Blocks.oak_fence.getDefaultState(),
							Blocks.oak_fence.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 4, 6, 12, 8, 6, 12, Blocks.oak_fence.getDefaultState(),
							Blocks.oak_fence.getDefaultState(), false);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 11, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 8, 6, 11, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 10, p_74875_3_);
					var7 = getMetadataWithOffset(Blocks.ladder, 3);
					func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 1, 13, p_74875_3_);
					func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 2, 13, p_74875_3_);
					func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 3, 13, p_74875_3_);
					func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 4, 13, p_74875_3_);
					func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 5, 13, p_74875_3_);
					func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 6, 13, p_74875_3_);
					func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var7), 10, 7, 13, p_74875_3_);
					final byte var8 = 7;
					final byte var9 = 7;
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 9, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 9, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 8, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 8, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 7, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 2, 7, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 + 1, 7, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9 - 1, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8 - 1, 7, var9 + 1, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 7, var9 - 1, p_74875_3_);
					func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), var8, 7, var9 + 1, p_74875_3_);
					func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8 - 2, 8, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8 + 1, 8, var9, p_74875_3_);
					func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8 - 1, 8, var9 - 1, p_74875_3_);
					func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8 - 1, 8, var9 + 1, p_74875_3_);
					func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8, 8, var9 - 1, p_74875_3_);
					func_175811_a(worldIn, Blocks.torch.getDefaultState(), var8, 8, var9 + 1, p_74875_3_);
				}

				func_180778_a(worldIn, p_74875_3_, p_74875_2_, 3, 3, 5,
						WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents,
								new WeightedRandomChestContent[] {
										Items.enchanted_book.func_92112_a(p_74875_2_, 1, 5, 2) }),
						1 + p_74875_2_.nextInt(4));

				if (isLargeRoom) {
					func_175811_a(worldIn, Blocks.air.getDefaultState(), 12, 9, 1, p_74875_3_);
					func_180778_a(worldIn, p_74875_3_, p_74875_2_, 12, 8, 1,
							WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents,
									new WeightedRandomChestContent[] {
											Items.enchanted_book.func_92112_a(p_74875_2_, 1, 5, 2) }),
							1 + p_74875_2_.nextInt(4));
				}

				return true;
			}
		}
	}

	static class PieceWeight {
		public Class pieceClass;
		public final int pieceWeight;
		public int instancesSpawned;
		public int instancesLimit;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000492";

		public PieceWeight(final Class p_i2076_1_, final int p_i2076_2_, final int p_i2076_3_) {
			pieceClass = p_i2076_1_;
			pieceWeight = p_i2076_2_;
			instancesLimit = p_i2076_3_;
		}

		public boolean canSpawnMoreStructuresOfType(final int p_75189_1_) {
			return instancesLimit == 0 || instancesSpawned < instancesLimit;
		}

		public boolean canSpawnMoreStructures() {
			return instancesLimit == 0 || instancesSpawned < instancesLimit;
		}
	}

	public static class PortalRoom extends StructureStrongholdPieces.Stronghold {
		private boolean hasSpawner;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000493";

		public PortalRoom() {}

		public PortalRoom(final int p_i45577_1_, final Random p_i45577_2_, final StructureBoundingBox p_i45577_3_,
				final EnumFacing p_i45577_4_) {
			super(p_i45577_1_);
			coordBaseMode = p_i45577_4_;
			boundingBox = p_i45577_3_;
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Mob", hasSpawner);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			hasSpawner = p_143011_1_.getBoolean("Mob");
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			if (p_74861_1_ != null) {
				((StructureStrongholdPieces.Stairs2) p_74861_1_).strongholdPortalRoom = this;
			}
		}

		public static StructureStrongholdPieces.PortalRoom func_175865_a(final List p_175865_0_,
				final Random p_175865_1_, final int p_175865_2_, final int p_175865_3_, final int p_175865_4_,
				final EnumFacing p_175865_5_, final int p_175865_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175865_2_, p_175865_3_, p_175865_4_,
					-4, -1, 0, 11, 8, 16, p_175865_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175865_0_, var7) == null
					? new StructureStrongholdPieces.PortalRoom(p_175865_6_, p_175865_1_, var7, p_175865_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 10, 7, 15, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			placeDoor(worldIn, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
			final byte var4 = 6;
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, var4, 1, 1, var4, 14, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, var4, 1, 9, var4, 14, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, var4, 1, 8, var4, 2, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, var4, 14, 8, var4, 14, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 1, 1, 2, 1, 4, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 8, 1, 1, 9, 1, 4, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava.getDefaultState(),
					Blocks.flowing_lava.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava.getDefaultState(),
					Blocks.flowing_lava.getDefaultState(), false);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 3, 1, 8, 7, 1, 12, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			func_175804_a(worldIn, p_74875_3_, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava.getDefaultState(),
					Blocks.flowing_lava.getDefaultState(), false);
			int var5;

			for (var5 = 3; var5 < 14; var5 += 2) {
				func_175804_a(worldIn, p_74875_3_, 0, 3, var5, 0, 4, var5, Blocks.iron_bars.getDefaultState(),
						Blocks.iron_bars.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 10, 3, var5, 10, 4, var5, Blocks.iron_bars.getDefaultState(),
						Blocks.iron_bars.getDefaultState(), false);
			}

			for (var5 = 2; var5 < 9; var5 += 2) {
				func_175804_a(worldIn, p_74875_3_, var5, 3, 15, var5, 4, 15, Blocks.iron_bars.getDefaultState(),
						Blocks.iron_bars.getDefaultState(), false);
			}

			var5 = getMetadataWithOffset(Blocks.stone_brick_stairs, 3);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 5, 6, 1, 7, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 2, 6, 6, 2, 7, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 3, 7, 6, 3, 7, false, p_74875_2_,
					StructureStrongholdPieces.strongholdStones);
			int var6;

			for (var6 = 4; var6 <= 6; ++var6) {
				func_175811_a(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(var5), var6, 1, 4, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(var5), var6, 2, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(var5), var6, 3, 6, p_74875_3_);
			}

			var6 = EnumFacing.NORTH.getHorizontalIndex();
			int var7 = EnumFacing.SOUTH.getHorizontalIndex();
			int var8 = EnumFacing.EAST.getHorizontalIndex();
			int var9 = EnumFacing.WEST.getHorizontalIndex();

			if (coordBaseMode != null) {
				switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[coordBaseMode.ordinal()]) {
					case 2:
						var6 = EnumFacing.SOUTH.getHorizontalIndex();
						var7 = EnumFacing.NORTH.getHorizontalIndex();
						break;

					case 3:
						var6 = EnumFacing.WEST.getHorizontalIndex();
						var7 = EnumFacing.EAST.getHorizontalIndex();
						var8 = EnumFacing.SOUTH.getHorizontalIndex();
						var9 = EnumFacing.NORTH.getHorizontalIndex();
						break;

					case 4:
						var6 = EnumFacing.EAST.getHorizontalIndex();
						var7 = EnumFacing.WEST.getHorizontalIndex();
						var8 = EnumFacing.SOUTH.getHorizontalIndex();
						var9 = EnumFacing.NORTH.getHorizontalIndex();
				}
			}

			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var6)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					4, 3, 8, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var6)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					5, 3, 8, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var6)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					6, 3, 8, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var7)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					4, 3, 12, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var7)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					5, 3, 12, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var7)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					6, 3, 12, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var8)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					3, 3, 9, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var8)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					3, 3, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var8)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					3, 3, 11, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var9)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					7, 3, 9, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var9)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					7, 3, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.end_portal_frame.getStateFromMeta(var9)
					.withProperty(BlockEndPortalFrame.field_176507_b, p_74875_2_.nextFloat() > 0.9F),
					7, 3, 11, p_74875_3_);

			if (!hasSpawner) {
				final int var12 = getYWithOffset(3);
				final BlockPos var10 = new BlockPos(getXWithOffset(5, 6), var12, getZWithOffset(5, 6));

				if (p_74875_3_.func_175898_b(var10)) {
					hasSpawner = true;
					worldIn.setBlockState(var10, Blocks.mob_spawner.getDefaultState(), 2);
					final TileEntity var11 = worldIn.getTileEntity(var10);

					if (var11 instanceof TileEntityMobSpawner) {
						((TileEntityMobSpawner) var11).getSpawnerBaseLogic().setEntityName("Silverfish");
					}
				}
			}

			return true;
		}
	}

	public static class Prison extends StructureStrongholdPieces.Stronghold {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000494";

		public Prison() {}

		public Prison(final int p_i45576_1_, final Random p_i45576_2_, final StructureBoundingBox p_i45576_3_,
				final EnumFacing p_i45576_4_) {
			super(p_i45576_1_);
			coordBaseMode = p_i45576_4_;
			field_143013_d = getRandomDoor(p_i45576_2_);
			boundingBox = p_i45576_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		}

		public static StructureStrongholdPieces.Prison func_175860_a(final List p_175860_0_, final Random p_175860_1_,
				final int p_175860_2_, final int p_175860_3_, final int p_175860_4_, final EnumFacing p_175860_5_,
				final int p_175860_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175860_2_, p_175860_3_, p_175860_4_,
					-1, -1, 0, 9, 5, 11, p_175860_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175860_0_, var7) == null
					? new StructureStrongholdPieces.Prison(p_175860_6_, p_175860_1_, var7, p_175860_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 8, 4, 10, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 1, 1, 0);
				func_175804_a(worldIn, p_74875_3_, 1, 1, 10, 3, 3, 10, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 1, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 3, 4, 3, 3, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 7, 4, 3, 7, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 9, 4, 3, 9, false, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				func_175804_a(worldIn, p_74875_3_, 4, 1, 4, 4, 3, 6, Blocks.iron_bars.getDefaultState(),
						Blocks.iron_bars.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 5, 1, 5, 7, 3, 5, Blocks.iron_bars.getDefaultState(),
						Blocks.iron_bars.getDefaultState(), false);
				func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3)), 4,
						1, 2, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 2,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3)), 4,
						1, 8, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 8,
						p_74875_3_);
				return true;
			}
		}
	}

	public static class RightTurn extends StructureStrongholdPieces.LeftTurn {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000495";

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.EAST) {
				getNextComponentX((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
			} else {
				getNextComponentZ((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
			}
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 1, 1, 0);

				if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.EAST) {
					func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				} else {
					func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				return true;
			}
		}
	}

	public static class RoomCrossing extends StructureStrongholdPieces.Stronghold {
		private static final List strongholdRoomCrossingChestContents = Lists.newArrayList(
				new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
						new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
						new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
						new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10),
						new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
						new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
						new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1) });
		protected int roomType;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000496";

		public RoomCrossing() {}

		public RoomCrossing(final int p_i45575_1_, final Random p_i45575_2_, final StructureBoundingBox p_i45575_3_,
				final EnumFacing p_i45575_4_) {
			super(p_i45575_1_);
			coordBaseMode = p_i45575_4_;
			field_143013_d = getRandomDoor(p_i45575_2_);
			boundingBox = p_i45575_3_;
			roomType = p_i45575_2_.nextInt(5);
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setInteger("Type", roomType);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			roomType = p_143011_1_.getInteger("Type");
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 4, 1);
			getNextComponentX((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
			getNextComponentZ((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
		}

		public static StructureStrongholdPieces.RoomCrossing func_175859_a(final List p_175859_0_,
				final Random p_175859_1_, final int p_175859_2_, final int p_175859_3_, final int p_175859_4_,
				final EnumFacing p_175859_5_, final int p_175859_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175859_2_, p_175859_3_, p_175859_4_,
					-4, -1, 0, 11, 7, 11, p_175859_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175859_0_, var7) == null
					? new StructureStrongholdPieces.RoomCrossing(p_175859_6_, p_175859_1_, var7, p_175859_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 10, 6, 10, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 4, 1, 0);
				func_175804_a(worldIn, p_74875_3_, 4, 1, 10, 6, 3, 10, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 0, 1, 4, 0, 3, 6, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 10, 1, 4, 10, 3, 6, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				int var4;

				switch (roomType) {
					case 0:
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.torch.getDefaultState(), 4, 3, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.torch.getDefaultState(), 6, 3, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.torch.getDefaultState(), 5, 3, 4, p_74875_3_);
						func_175811_a(worldIn, Blocks.torch.getDefaultState(), 5, 3, 6, p_74875_3_);
						func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 4, p_74875_3_);
						func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 6, p_74875_3_);
						func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 4, p_74875_3_);
						func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 6, p_74875_3_);
						func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 4, p_74875_3_);
						func_175811_a(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 6, p_74875_3_);
						break;

					case 1:
						for (var4 = 0; var4 < 5; ++var4) {
							func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 1, 3 + var4, p_74875_3_);
							func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 7, 1, 3 + var4, p_74875_3_);
							func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3 + var4, 1, 3, p_74875_3_);
							func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3 + var4, 1, 7, p_74875_3_);
						}

						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.flowing_water.getDefaultState(), 5, 4, 5, p_74875_3_);
						break;

					case 2:
						for (var4 = 1; var4 <= 9; ++var4) {
							func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 1, 3, var4, p_74875_3_);
							func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 9, 3, var4, p_74875_3_);
						}

						for (var4 = 1; var4 <= 9; ++var4) {
							func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), var4, 3, 1, p_74875_3_);
							func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), var4, 3, 9, p_74875_3_);
						}

						func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 4, p_74875_3_);
						func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 6, p_74875_3_);
						func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 4, p_74875_3_);
						func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 6, p_74875_3_);
						func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 5, p_74875_3_);
						func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, 3, 5, p_74875_3_);

						for (var4 = 1; var4 <= 3; ++var4) {
							func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, var4, 4, p_74875_3_);
							func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, var4, 4, p_74875_3_);
							func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, var4, 6, p_74875_3_);
							func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, var4, 6, p_74875_3_);
						}

						func_175811_a(worldIn, Blocks.torch.getDefaultState(), 5, 3, 5, p_74875_3_);

						for (var4 = 2; var4 <= 8; ++var4) {
							func_175811_a(worldIn, Blocks.planks.getDefaultState(), 2, 3, var4, p_74875_3_);
							func_175811_a(worldIn, Blocks.planks.getDefaultState(), 3, 3, var4, p_74875_3_);

							if (var4 <= 3 || var4 >= 7) {
								func_175811_a(worldIn, Blocks.planks.getDefaultState(), 4, 3, var4, p_74875_3_);
								func_175811_a(worldIn, Blocks.planks.getDefaultState(), 5, 3, var4, p_74875_3_);
								func_175811_a(worldIn, Blocks.planks.getDefaultState(), 6, 3, var4, p_74875_3_);
							}

							func_175811_a(worldIn, Blocks.planks.getDefaultState(), 7, 3, var4, p_74875_3_);
							func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 3, var4, p_74875_3_);
						}

						func_175811_a(worldIn,
								Blocks.ladder.getStateFromMeta(
										getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())),
								9, 1, 3, p_74875_3_);
						func_175811_a(worldIn,
								Blocks.ladder.getStateFromMeta(
										getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())),
								9, 2, 3, p_74875_3_);
						func_175811_a(worldIn,
								Blocks.ladder.getStateFromMeta(
										getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())),
								9, 3, 3, p_74875_3_);
						func_180778_a(worldIn, p_74875_3_, p_74875_2_, 3, 4, 8,
								WeightedRandomChestContent.func_177629_a(strongholdRoomCrossingChestContents,
										new WeightedRandomChestContent[] {
												Items.enchanted_book.getRandomEnchantedBook(p_74875_2_) }),
								1 + p_74875_2_.nextInt(4));
				}

				return true;
			}
		}
	}

	public static class Stairs extends StructureStrongholdPieces.Stronghold {
		private boolean field_75024_a;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000498";

		public Stairs() {}

		public Stairs(final int p_i2081_1_, final Random p_i2081_2_, final int p_i2081_3_, final int p_i2081_4_) {
			super(p_i2081_1_);
			field_75024_a = true;
			coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2081_2_);
			field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;

			switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[coordBaseMode.ordinal()]) {
				case 1:
				case 2:
					boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74,
							p_i2081_4_ + 5 - 1);
					break;

				default:
					boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74,
							p_i2081_4_ + 5 - 1);
			}
		}

		public Stairs(final int p_i45574_1_, final Random p_i45574_2_, final StructureBoundingBox p_i45574_3_,
				final EnumFacing p_i45574_4_) {
			super(p_i45574_1_);
			field_75024_a = false;
			coordBaseMode = p_i45574_4_;
			field_143013_d = getRandomDoor(p_i45574_2_);
			boundingBox = p_i45574_3_;
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Source", field_75024_a);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			field_75024_a = p_143011_1_.getBoolean("Source");
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			if (field_75024_a) {
				StructureStrongholdPieces.strongComponentType = StructureStrongholdPieces.Crossing.class;
			}

			getNextComponentNormal((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		}

		public static StructureStrongholdPieces.Stairs func_175863_a(final List p_175863_0_, final Random p_175863_1_,
				final int p_175863_2_, final int p_175863_3_, final int p_175863_4_, final EnumFacing p_175863_5_,
				final int p_175863_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175863_2_, p_175863_3_, p_175863_4_,
					-1, -7, 0, 5, 11, 5, p_175863_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175863_0_, var7) == null
					? new StructureStrongholdPieces.Stairs(p_175863_6_, p_175863_1_, var7, p_175863_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 10, 4, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 1, 7, 0);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 6, 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 1, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 6, 1,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, 3, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 5, 3,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, 3, p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 3, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 3, 4, 3,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 2, 1, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 3, 3, 1,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 2, 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 1, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 2, 1,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 2, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 1, 3,
						p_74875_3_);
				return true;
			}
		}
	}

	public static class Stairs2 extends StructureStrongholdPieces.Stairs {
		public StructureStrongholdPieces.PieceWeight strongholdPieceWeight;
		public StructureStrongholdPieces.PortalRoom strongholdPortalRoom;
		public List field_75026_c = Lists.newArrayList();
		// private static final String __OBFID =
		// "http://https://fuckuskid00000499";

		public Stairs2() {}

		public Stairs2(final int p_i2083_1_, final Random p_i2083_2_, final int p_i2083_3_, final int p_i2083_4_) {
			super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
		}

		@Override
		public BlockPos func_180776_a() {
			return strongholdPortalRoom != null ? strongholdPortalRoom.func_180776_a() : super.func_180776_a();
		}
	}

	public static class StairsStraight extends StructureStrongholdPieces.Stronghold {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000501";

		public StairsStraight() {}

		public StairsStraight(final int p_i45572_1_, final Random p_i45572_2_, final StructureBoundingBox p_i45572_3_,
				final EnumFacing p_i45572_4_) {
			super(p_i45572_1_);
			coordBaseMode = p_i45572_4_;
			field_143013_d = getRandomDoor(p_i45572_2_);
			boundingBox = p_i45572_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		}

		public static StructureStrongholdPieces.StairsStraight func_175861_a(final List p_175861_0_,
				final Random p_175861_1_, final int p_175861_2_, final int p_175861_3_, final int p_175861_4_,
				final EnumFacing p_175861_5_, final int p_175861_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175861_2_, p_175861_3_, p_175861_4_,
					-1, -7, 0, 5, 11, 8, p_175861_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175861_0_, var7) == null
					? new StructureStrongholdPieces.StairsStraight(p_175861_6_, p_175861_1_, var7, p_175861_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 10, 7, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 1, 7, 0);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
				final int var4 = getMetadataWithOffset(Blocks.stone_stairs, 2);

				for (int var5 = 0; var5 < 6; ++var5) {
					func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 1, 6 - var5, 1 + var5,
							p_74875_3_);
					func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 2, 6 - var5, 1 + var5,
							p_74875_3_);
					func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 3, 6 - var5, 1 + var5,
							p_74875_3_);

					if (var5 < 5) {
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5 - var5, 1 + var5, p_74875_3_);
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 5 - var5, 1 + var5, p_74875_3_);
						func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 5 - var5, 1 + var5, p_74875_3_);
					}
				}

				return true;
			}
		}
	}

	static class Stones extends StructureComponent.BlockSelector {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000497";

		private Stones() {}

		@Override
		public void selectBlocks(final Random p_75062_1_, final int p_75062_2_, final int p_75062_3_,
				final int p_75062_4_, final boolean p_75062_5_) {
			if (p_75062_5_) {
				final float var6 = p_75062_1_.nextFloat();

				if (var6 < 0.2F) {
					field_151562_a = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
				} else if (var6 < 0.5F) {
					field_151562_a = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
				} else if (var6 < 0.55F) {
					field_151562_a = Blocks.monster_egg
							.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.func_176881_a());
				} else {
					field_151562_a = Blocks.stonebrick.getDefaultState();
				}
			} else {
				field_151562_a = Blocks.air.getDefaultState();
			}
		}

		Stones(final Object p_i2080_1_) {
			this();
		}
	}

	public static class Straight extends StructureStrongholdPieces.Stronghold {
		private boolean expandsX;
		private boolean expandsZ;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000500";

		public Straight() {}

		public Straight(final int p_i45573_1_, final Random p_i45573_2_, final StructureBoundingBox p_i45573_3_,
				final EnumFacing p_i45573_4_) {
			super(p_i45573_1_);
			coordBaseMode = p_i45573_4_;
			field_143013_d = getRandomDoor(p_i45573_2_);
			boundingBox = p_i45573_3_;
			expandsX = p_i45573_2_.nextInt(2) == 0;
			expandsZ = p_i45573_2_.nextInt(2) == 0;
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Left", expandsX);
			p_143012_1_.setBoolean("Right", expandsZ);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			expandsX = p_143011_1_.getBoolean("Left");
			expandsZ = p_143011_1_.getBoolean("Right");
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);

			if (expandsX) {
				getNextComponentX((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
			}

			if (expandsZ) {
				getNextComponentZ((StructureStrongholdPieces.Stairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
			}
		}

		public static StructureStrongholdPieces.Straight func_175862_a(final List p_175862_0_, final Random p_175862_1_,
				final int p_175862_2_, final int p_175862_3_, final int p_175862_4_, final EnumFacing p_175862_5_,
				final int p_175862_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175862_2_, p_175862_3_, p_175862_4_,
					-1, -1, 0, 5, 5, 7, p_175862_5_);
			return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175862_0_, var7) == null
					? new StructureStrongholdPieces.Straight(p_175862_6_, p_175862_1_, var7, p_175862_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_,
						StructureStrongholdPieces.strongholdStones);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, field_143013_d, 1, 1, 0);
				placeDoor(worldIn, p_74875_2_, p_74875_3_, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
				func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1F, 1, 2, 1, Blocks.torch.getDefaultState());
				func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1F, 3, 2, 1, Blocks.torch.getDefaultState());
				func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1F, 1, 2, 5, Blocks.torch.getDefaultState());
				func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1F, 3, 2, 5, Blocks.torch.getDefaultState());

				if (expandsX) {
					func_175804_a(worldIn, p_74875_3_, 0, 1, 2, 0, 3, 4, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				if (expandsZ) {
					func_175804_a(worldIn, p_74875_3_, 4, 1, 2, 4, 3, 4, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				return true;
			}
		}
	}

	abstract static class Stronghold extends StructureComponent {
		protected StructureStrongholdPieces.Stronghold.Door field_143013_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000503";

		public Stronghold() {
			field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;
		}

		protected Stronghold(final int p_i2087_1_) {
			super(p_i2087_1_);
			field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			p_143012_1_.setString("EntryDoor", field_143013_d.name());
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			field_143013_d = StructureStrongholdPieces.Stronghold.Door.valueOf(p_143011_1_.getString("EntryDoor"));
		}

		protected void placeDoor(final World worldIn, final Random p_74990_2_, final StructureBoundingBox p_74990_3_,
				final StructureStrongholdPieces.Stronghold.Door p_74990_4_, final int p_74990_5_, final int p_74990_6_,
				final int p_74990_7_) {
			switch (StructureStrongholdPieces.SwitchEnumFacing.doorEnum[p_74990_4_.ordinal()]) {
				case 1:
				default:
					func_175804_a(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1,
							p_74990_6_ + 3 - 1, p_74990_7_, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(),
							false);
					break;

				case 2:
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.oak_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.oak_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1,
							p_74990_7_, p_74990_3_);
					break;

				case 3:
					func_175811_a(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_,
							p_74990_3_);
					break;

				case 4:
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_,
							p_74990_3_);
					func_175811_a(worldIn, Blocks.iron_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1,
							p_74990_7_, p_74990_3_);
					func_175811_a(worldIn,
							Blocks.stone_button.getStateFromMeta(getMetadataWithOffset(Blocks.stone_button, 4)),
							p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
					func_175811_a(worldIn,
							Blocks.stone_button.getStateFromMeta(getMetadataWithOffset(Blocks.stone_button, 3)),
							p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
			}
		}

		protected StructureStrongholdPieces.Stronghold.Door getRandomDoor(final Random p_74988_1_) {
			final int var2 = p_74988_1_.nextInt(5);

			switch (var2) {
				case 0:
				case 1:
				default:
					return StructureStrongholdPieces.Stronghold.Door.OPENING;

				case 2:
					return StructureStrongholdPieces.Stronghold.Door.WOOD_DOOR;

				case 3:
					return StructureStrongholdPieces.Stronghold.Door.GRATES;

				case 4:
					return StructureStrongholdPieces.Stronghold.Door.IRON_DOOR;
			}
		}

		protected StructureComponent getNextComponentNormal(final StructureStrongholdPieces.Stairs2 p_74986_1_,
				final List p_74986_2_, final Random p_74986_3_, final int p_74986_4_, final int p_74986_5_) {
			if (coordBaseMode != null) {
				switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[coordBaseMode.ordinal()]) {
					case 1:
						return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_,
								boundingBox.minX + p_74986_4_, boundingBox.minY + p_74986_5_, boundingBox.minZ - 1,
								coordBaseMode, getComponentType());

					case 2:
						return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_,
								boundingBox.minX + p_74986_4_, boundingBox.minY + p_74986_5_, boundingBox.maxZ + 1,
								coordBaseMode, getComponentType());

					case 3:
						return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_,
								boundingBox.minX - 1, boundingBox.minY + p_74986_5_, boundingBox.minZ + p_74986_4_,
								coordBaseMode, getComponentType());

					case 4:
						return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_,
								boundingBox.maxX + 1, boundingBox.minY + p_74986_5_, boundingBox.minZ + p_74986_4_,
								coordBaseMode, getComponentType());
				}
			}

			return null;
		}

		protected StructureComponent getNextComponentX(final StructureStrongholdPieces.Stairs2 p_74989_1_,
				final List p_74989_2_, final Random p_74989_3_, final int p_74989_4_, final int p_74989_5_) {
			if (coordBaseMode != null) {
				switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[coordBaseMode.ordinal()]) {
					case 1:
						return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_,
								boundingBox.minX - 1, boundingBox.minY + p_74989_4_, boundingBox.minZ + p_74989_5_,
								EnumFacing.WEST, getComponentType());

					case 2:
						return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_,
								boundingBox.minX - 1, boundingBox.minY + p_74989_4_, boundingBox.minZ + p_74989_5_,
								EnumFacing.WEST, getComponentType());

					case 3:
						return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_,
								boundingBox.minX + p_74989_5_, boundingBox.minY + p_74989_4_, boundingBox.minZ - 1,
								EnumFacing.NORTH, getComponentType());

					case 4:
						return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_,
								boundingBox.minX + p_74989_5_, boundingBox.minY + p_74989_4_, boundingBox.minZ - 1,
								EnumFacing.NORTH, getComponentType());
				}
			}

			return null;
		}

		protected StructureComponent getNextComponentZ(final StructureStrongholdPieces.Stairs2 p_74987_1_,
				final List p_74987_2_, final Random p_74987_3_, final int p_74987_4_, final int p_74987_5_) {
			if (coordBaseMode != null) {
				switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[coordBaseMode.ordinal()]) {
					case 1:
						return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_,
								boundingBox.maxX + 1, boundingBox.minY + p_74987_4_, boundingBox.minZ + p_74987_5_,
								EnumFacing.EAST, getComponentType());

					case 2:
						return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_,
								boundingBox.maxX + 1, boundingBox.minY + p_74987_4_, boundingBox.minZ + p_74987_5_,
								EnumFacing.EAST, getComponentType());

					case 3:
						return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_,
								boundingBox.minX + p_74987_5_, boundingBox.minY + p_74987_4_, boundingBox.maxZ + 1,
								EnumFacing.SOUTH, getComponentType());

					case 4:
						return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_,
								boundingBox.minX + p_74987_5_, boundingBox.minY + p_74987_4_, boundingBox.maxZ + 1,
								EnumFacing.SOUTH, getComponentType());
				}
			}

			return null;
		}

		protected static boolean canStrongholdGoDeeper(final StructureBoundingBox p_74991_0_) {
			return p_74991_0_ != null && p_74991_0_.minY > 10;
		}

		public static enum Door {
			OPENING("OPENING", 0), WOOD_DOOR("WOOD_DOOR", 1), GRATES("GRATES", 2), IRON_DOOR("IRON_DOOR", 3);

			private Door(final String p_i2086_1_, final int p_i2086_2_) {}
		}
	}

	static final class SwitchEnumFacing {
		static final int[] doorEnum;

		static final int[] field_175951_b = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00001970";

		static {
			try {
				field_175951_b[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var8) {
			}

			try {
				field_175951_b[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var7) {
			}

			try {
				field_175951_b[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_175951_b[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var5) {
			}

			doorEnum = new int[StructureStrongholdPieces.Stronghold.Door.values().length];

			try {
				doorEnum[StructureStrongholdPieces.Stronghold.Door.OPENING.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				doorEnum[StructureStrongholdPieces.Stronghold.Door.WOOD_DOOR.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				doorEnum[StructureStrongholdPieces.Stronghold.Door.GRATES.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				doorEnum[StructureStrongholdPieces.Stronghold.Door.IRON_DOOR.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
