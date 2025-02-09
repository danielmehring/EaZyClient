package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

public class StructureNetherBridgePieces {

public static final int EaZy = 1820;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final StructureNetherBridgePieces.PieceWeight[] primaryComponents = new StructureNetherBridgePieces.PieceWeight[] {
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Straight.class, 30, 0, true),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing3.class, 10, 4),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing.class, 10, 4),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Stairs.class, 10, 3),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Throne.class, 5, 2),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Entrance.class, 5, 1) };
	private static final StructureNetherBridgePieces.PieceWeight[] secondaryComponents = new StructureNetherBridgePieces.PieceWeight[] {
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor5.class, 25, 0, true),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing2.class, 15, 5),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor2.class, 5, 10),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor.class, 5, 10),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor3.class, 10, 3, true),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor4.class, 7, 2),
			new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.NetherStalkRoom.class, 5, 2) };
	// private static final String __OBFID = "http://https://fuckuskid00000453";

	public static void registerNetherFortressPieces() {
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Crossing3.class, "NeBCr");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.End.class, "NeBEF");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Straight.class, "NeBS");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor3.class, "NeCCS");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor4.class, "NeCTB");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Entrance.class, "NeCE");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Crossing2.class, "NeSCSC");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor.class, "NeSCLT");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor5.class, "NeSC");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Corridor2.class, "NeSCRT");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.NetherStalkRoom.class, "NeCSR");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Throne.class, "NeMT");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Crossing.class, "NeRC");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Stairs.class, "NeSR");
		MapGenStructureIO.registerStructureComponent(StructureNetherBridgePieces.Start.class, "NeStart");
	}

	private static StructureNetherBridgePieces.Piece func_175887_b(
			final StructureNetherBridgePieces.PieceWeight p_175887_0_, final List p_175887_1_, final Random p_175887_2_,
			final int p_175887_3_, final int p_175887_4_, final int p_175887_5_, final EnumFacing p_175887_6_,
			final int p_175887_7_) {
		final Class var8 = p_175887_0_.weightClass;
		Object var9 = null;

		if (var8 == StructureNetherBridgePieces.Straight.class) {
			var9 = StructureNetherBridgePieces.Straight.func_175882_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Crossing3.class) {
			var9 = StructureNetherBridgePieces.Crossing3.func_175885_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Crossing.class) {
			var9 = StructureNetherBridgePieces.Crossing.func_175873_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Stairs.class) {
			var9 = StructureNetherBridgePieces.Stairs.func_175872_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_,
					p_175887_5_, p_175887_7_, p_175887_6_);
		} else if (var8 == StructureNetherBridgePieces.Throne.class) {
			var9 = StructureNetherBridgePieces.Throne.func_175874_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_,
					p_175887_5_, p_175887_7_, p_175887_6_);
		} else if (var8 == StructureNetherBridgePieces.Entrance.class) {
			var9 = StructureNetherBridgePieces.Entrance.func_175881_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Corridor5.class) {
			var9 = StructureNetherBridgePieces.Corridor5.func_175877_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Corridor2.class) {
			var9 = StructureNetherBridgePieces.Corridor2.func_175876_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Corridor.class) {
			var9 = StructureNetherBridgePieces.Corridor.func_175879_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Corridor3.class) {
			var9 = StructureNetherBridgePieces.Corridor3.func_175883_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Corridor4.class) {
			var9 = StructureNetherBridgePieces.Corridor4.func_175880_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.Crossing2.class) {
			var9 = StructureNetherBridgePieces.Crossing2.func_175878_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		} else if (var8 == StructureNetherBridgePieces.NetherStalkRoom.class) {
			var9 = StructureNetherBridgePieces.NetherStalkRoom.func_175875_a(p_175887_1_, p_175887_2_, p_175887_3_,
					p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
		}

		return (StructureNetherBridgePieces.Piece) var9;
	}

	public static class Corridor extends StructureNetherBridgePieces.Piece {
		private boolean field_111021_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000461";

		public Corridor() {}

		public Corridor(final int p_i45615_1_, final Random p_i45615_2_, final StructureBoundingBox p_i45615_3_,
				final EnumFacing p_i45615_4_) {
			super(p_i45615_1_);
			coordBaseMode = p_i45615_4_;
			boundingBox = p_i45615_3_;
			field_111021_b = p_i45615_2_.nextInt(3) == 0;
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			field_111021_b = p_143011_1_.getBoolean("Chest");
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Chest", field_111021_b);
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentX((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
		}

		public static StructureNetherBridgePieces.Corridor func_175879_a(final List p_175879_0_,
				final Random p_175879_1_, final int p_175879_2_, final int p_175879_3_, final int p_175879_4_,
				final EnumFacing p_175879_5_, final int p_175879_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175879_2_, p_175879_3_, p_175879_4_,
					-1, 0, 0, 5, 7, 5, p_175879_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175879_0_, var7) == null
					? new StructureNetherBridgePieces.Corridor(p_175879_6_, p_175879_1_, var7, p_175879_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 3, 5, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			if (field_111021_b && p_74875_3_
					.func_175898_b(new BlockPos(getXWithOffset(3, 3), getYWithOffset(2), getZWithOffset(3, 3)))) {
				field_111021_b = false;
				func_180778_a(worldIn, p_74875_3_, p_74875_2_, 3, 2, 3, field_111019_a, 2 + p_74875_2_.nextInt(4));
			}

			func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			for (int var4 = 0; var4 <= 4; ++var4) {
				for (int var5 = 0; var5 <= 4; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class Corridor2 extends StructureNetherBridgePieces.Piece {
		private boolean field_111020_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000463";

		public Corridor2() {}

		public Corridor2(final int p_i45613_1_, final Random p_i45613_2_, final StructureBoundingBox p_i45613_3_,
				final EnumFacing p_i45613_4_) {
			super(p_i45613_1_);
			coordBaseMode = p_i45613_4_;
			boundingBox = p_i45613_3_;
			field_111020_b = p_i45613_2_.nextInt(3) == 0;
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			field_111020_b = p_143011_1_.getBoolean("Chest");
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Chest", field_111020_b);
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentZ((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
		}

		public static StructureNetherBridgePieces.Corridor2 func_175876_a(final List p_175876_0_,
				final Random p_175876_1_, final int p_175876_2_, final int p_175876_3_, final int p_175876_4_,
				final EnumFacing p_175876_5_, final int p_175876_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175876_2_, p_175876_3_, p_175876_4_,
					-1, 0, 0, 5, 7, 5, p_175876_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175876_0_, var7) == null
					? new StructureNetherBridgePieces.Corridor2(p_175876_6_, p_175876_1_, var7, p_175876_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			if (field_111020_b && p_74875_3_
					.func_175898_b(new BlockPos(getXWithOffset(1, 3), getYWithOffset(2), getZWithOffset(1, 3)))) {
				field_111020_b = false;
				func_180778_a(worldIn, p_74875_3_, p_74875_2_, 1, 2, 3, field_111019_a, 2 + p_74875_2_.nextInt(4));
			}

			func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			for (int var4 = 0; var4 <= 4; ++var4) {
				for (int var5 = 0; var5 <= 4; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class Corridor3 extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000457";

		public Corridor3() {}

		public Corridor3(final int p_i45619_1_, final Random p_i45619_2_, final StructureBoundingBox p_i45619_3_,
				final EnumFacing p_i45619_4_) {
			super(p_i45619_1_);
			coordBaseMode = p_i45619_4_;
			boundingBox = p_i45619_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
		}

		public static StructureNetherBridgePieces.Corridor3 func_175883_a(final List p_175883_0_,
				final Random p_175883_1_, final int p_175883_2_, final int p_175883_3_, final int p_175883_4_,
				final EnumFacing p_175883_5_, final int p_175883_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175883_2_, p_175883_3_, p_175883_4_,
					-1, -7, 0, 5, 14, 10, p_175883_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175883_0_, var7) == null
					? new StructureNetherBridgePieces.Corridor3(p_175883_6_, p_175883_1_, var7, p_175883_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			final int var4 = getMetadataWithOffset(Blocks.nether_brick_stairs, 2);

			for (int var5 = 0; var5 <= 9; ++var5) {
				final int var6 = Math.max(1, 7 - var5);
				final int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
				final int var8 = var5;
				func_175804_a(worldIn, p_74875_3_, 0, 0, var5, 4, var6, var5, Blocks.nether_brick.getDefaultState(),
						Blocks.nether_brick.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 1, var6 + 1, var5, 3, var7 - 1, var5, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);

				if (var5 <= 6) {
					func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 1, var6 + 1, var5,
							p_74875_3_);
					func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 2, var6 + 1, var5,
							p_74875_3_);
					func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 3, var6 + 1, var5,
							p_74875_3_);
				}

				func_175804_a(worldIn, p_74875_3_, 0, var7, var5, 4, var7, var5, Blocks.nether_brick.getDefaultState(),
						Blocks.nether_brick.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 0, var6 + 1, var5, 0, var7 - 1, var5,
						Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 4, var6 + 1, var5, 4, var7 - 1, var5,
						Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);

				if ((var5 & 1) == 0) {
					func_175804_a(worldIn, p_74875_3_, 0, var6 + 2, var5, 0, var6 + 3, var5,
							Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
							false);
					func_175804_a(worldIn, p_74875_3_, 4, var6 + 2, var5, 4, var6 + 3, var5,
							Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
							false);
				}

				for (int var9 = 0; var9 <= 4; ++var9) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var9, -1, var8, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class Corridor4 extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000458";

		public Corridor4() {}

		public Corridor4(final int p_i45618_1_, final Random p_i45618_2_, final StructureBoundingBox p_i45618_3_,
				final EnumFacing p_i45618_4_) {
			super(p_i45618_1_);
			coordBaseMode = p_i45618_4_;
			boundingBox = p_i45618_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			byte var4 = 1;

			if (coordBaseMode == EnumFacing.WEST || coordBaseMode == EnumFacing.NORTH) {
				var4 = 5;
			}

			getNextComponentX((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 0, var4,
					p_74861_3_.nextInt(8) > 0);
			getNextComponentZ((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 0, var4,
					p_74861_3_.nextInt(8) > 0);
		}

		public static StructureNetherBridgePieces.Corridor4 func_175880_a(final List p_175880_0_,
				final Random p_175880_1_, final int p_175880_2_, final int p_175880_3_, final int p_175880_4_,
				final EnumFacing p_175880_5_, final int p_175880_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175880_2_, p_175880_3_, p_175880_4_,
					-3, 0, 0, 9, 7, 9, p_175880_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175880_0_, var7) == null
					? new StructureNetherBridgePieces.Corridor4(p_175880_6_, p_175880_1_, var7, p_175880_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 8, 1, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 8, 5, 8, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 8, 6, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 2, 5, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 2, 0, 8, 5, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 8, 2, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 1, 4, 2, 2, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 1, 4, 7, 2, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 4, 0, 5, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 3, 4, 8, 5, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 3, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 3, 5, 7, 5, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);

			for (int var4 = 0; var4 <= 5; ++var4) {
				for (int var5 = 0; var5 <= 8; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var5, -1, var4, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class Corridor5 extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000462";

		public Corridor5() {}

		public Corridor5(final int p_i45614_1_, final Random p_i45614_2_, final StructureBoundingBox p_i45614_3_,
				final EnumFacing p_i45614_4_) {
			super(p_i45614_1_);
			coordBaseMode = p_i45614_4_;
			boundingBox = p_i45614_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
		}

		public static StructureNetherBridgePieces.Corridor5 func_175877_a(final List p_175877_0_,
				final Random p_175877_1_, final int p_175877_2_, final int p_175877_3_, final int p_175877_4_,
				final EnumFacing p_175877_5_, final int p_175877_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175877_2_, p_175877_3_, p_175877_4_,
					-1, 0, 0, 5, 7, 5, p_175877_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175877_0_, var7) == null
					? new StructureNetherBridgePieces.Corridor5(p_175877_6_, p_175877_1_, var7, p_175877_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			for (int var4 = 0; var4 <= 4; ++var4) {
				for (int var5 = 0; var5 <= 4; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class Crossing extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000468";

		public Crossing() {}

		public Crossing(final int p_i45610_1_, final Random p_i45610_2_, final StructureBoundingBox p_i45610_3_,
				final EnumFacing p_i45610_4_) {
			super(p_i45610_1_);
			coordBaseMode = p_i45610_4_;
			boundingBox = p_i45610_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 2, 0, false);
			getNextComponentX((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
			getNextComponentZ((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
		}

		public static StructureNetherBridgePieces.Crossing func_175873_a(final List p_175873_0_,
				final Random p_175873_1_, final int p_175873_2_, final int p_175873_3_, final int p_175873_4_,
				final EnumFacing p_175873_5_, final int p_175873_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175873_2_, p_175873_3_, p_175873_4_,
					-2, 0, 0, 7, 9, 7, p_175873_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175873_0_, var7) == null
					? new StructureNetherBridgePieces.Crossing(p_175873_6_, p_175873_1_, var7, p_175873_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 6, 7, 6, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 1, 6, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 6, 1, 6, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 2, 0, 6, 6, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 2, 6, 6, 6, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 6, 1, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 5, 0, 6, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 2, 0, 6, 6, 1, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 2, 5, 6, 6, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 6, 0, 4, 6, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 6, 6, 4, 6, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 6, 2, 0, 6, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 6, 2, 6, 6, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);

			for (int var4 = 0; var4 <= 6; ++var4) {
				for (int var5 = 0; var5 <= 6; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class Crossing2 extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000460";

		public Crossing2() {}

		public Crossing2(final int p_i45616_1_, final Random p_i45616_2_, final StructureBoundingBox p_i45616_3_,
				final EnumFacing p_i45616_4_) {
			super(p_i45616_1_);
			coordBaseMode = p_i45616_4_;
			boundingBox = p_i45616_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
			getNextComponentX((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
			getNextComponentZ((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
		}

		public static StructureNetherBridgePieces.Crossing2 func_175878_a(final List p_175878_0_,
				final Random p_175878_1_, final int p_175878_2_, final int p_175878_3_, final int p_175878_4_,
				final EnumFacing p_175878_5_, final int p_175878_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175878_2_, p_175878_3_, p_175878_4_,
					-1, 0, 0, 5, 7, 5, p_175878_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175878_0_, var7) == null
					? new StructureNetherBridgePieces.Crossing2(p_175878_6_, p_175878_1_, var7, p_175878_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 0, 5, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			for (int var4 = 0; var4 <= 4; ++var4) {
				for (int var5 = 0; var5 <= 4; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class Crossing3 extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000454";

		public Crossing3() {}

		public Crossing3(final int p_i45622_1_, final Random p_i45622_2_, final StructureBoundingBox p_i45622_3_,
				final EnumFacing p_i45622_4_) {
			super(p_i45622_1_);
			coordBaseMode = p_i45622_4_;
			boundingBox = p_i45622_3_;
		}

		protected Crossing3(final Random p_i2042_1_, final int p_i2042_2_, final int p_i2042_3_) {
			super(0);
			coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2042_1_);

			switch (StructureNetherBridgePieces.SwitchEnumFacing.field_175888_a[coordBaseMode.ordinal()]) {
				case 1:
				case 2:
					boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73,
							p_i2042_3_ + 19 - 1);
					break;

				default:
					boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73,
							p_i2042_3_ + 19 - 1);
			}
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 8, 3, false);
			getNextComponentX((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 3, 8, false);
			getNextComponentZ((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 3, 8, false);
		}

		public static StructureNetherBridgePieces.Crossing3 func_175885_a(final List p_175885_0_,
				final Random p_175885_1_, final int p_175885_2_, final int p_175885_3_, final int p_175885_4_,
				final EnumFacing p_175885_5_, final int p_175885_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175885_2_, p_175885_3_, p_175885_4_,
					-8, -3, 0, 19, 10, 19, p_175885_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175885_0_, var7) == null
					? new StructureNetherBridgePieces.Crossing3(p_175885_6_, p_175885_1_, var7, p_175885_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 7, 3, 0, 11, 4, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 7, 18, 4, 11, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 5, 0, 10, 7, 18, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 8, 18, 7, 10, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 5, 0, 7, 5, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 5, 11, 7, 5, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 11, 5, 0, 11, 5, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 11, 5, 11, 11, 5, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 7, 7, 5, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 11, 5, 7, 18, 5, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 11, 7, 5, 11, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 11, 5, 11, 18, 5, 11, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 2, 0, 11, 2, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 2, 13, 11, 2, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 0, 0, 11, 1, 3, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 0, 15, 11, 1, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			int var4;
			int var5;

			for (var4 = 7; var4 <= 11; ++var4) {
				for (var5 = 0; var5 <= 2; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 18 - var5, p_74875_3_);
				}
			}

			func_175804_a(worldIn, p_74875_3_, 0, 2, 7, 5, 2, 11, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 13, 2, 7, 18, 2, 11, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 0, 7, 3, 1, 11, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 15, 0, 7, 18, 1, 11, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			for (var4 = 0; var4 <= 2; ++var4) {
				for (var5 = 7; var5 <= 11; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 18 - var4, -1, var5, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class End extends StructureNetherBridgePieces.Piece {
		private int fillSeed;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000455";

		public End() {}

		public End(final int p_i45621_1_, final Random p_i45621_2_, final StructureBoundingBox p_i45621_3_,
				final EnumFacing p_i45621_4_) {
			super(p_i45621_1_);
			coordBaseMode = p_i45621_4_;
			boundingBox = p_i45621_3_;
			fillSeed = p_i45621_2_.nextInt();
		}

		public static StructureNetherBridgePieces.End func_175884_a(final List p_175884_0_, final Random p_175884_1_,
				final int p_175884_2_, final int p_175884_3_, final int p_175884_4_, final EnumFacing p_175884_5_,
				final int p_175884_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175884_2_, p_175884_3_, p_175884_4_,
					-1, -3, 0, 5, 10, 8, p_175884_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175884_0_, var7) == null
					? new StructureNetherBridgePieces.End(p_175884_6_, p_175884_1_, var7, p_175884_5_) : null;
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			fillSeed = p_143011_1_.getInteger("Seed");
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setInteger("Seed", fillSeed);
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			final Random var4 = new Random(fillSeed);
			int var5;
			int var6;
			int var7;

			for (var5 = 0; var5 <= 4; ++var5) {
				for (var6 = 3; var6 <= 4; ++var6) {
					var7 = var4.nextInt(8);
					func_175804_a(worldIn, p_74875_3_, var5, var6, 0, var5, var6, var7,
							Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
				}
			}

			var5 = var4.nextInt(8);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 0, 5, var5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			var5 = var4.nextInt(8);
			func_175804_a(worldIn, p_74875_3_, 4, 5, 0, 4, 5, var5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			for (var5 = 0; var5 <= 4; ++var5) {
				var6 = var4.nextInt(5);
				func_175804_a(worldIn, p_74875_3_, var5, 2, 0, var5, 2, var6, Blocks.nether_brick.getDefaultState(),
						Blocks.nether_brick.getDefaultState(), false);
			}

			for (var5 = 0; var5 <= 4; ++var5) {
				for (var6 = 0; var6 <= 1; ++var6) {
					var7 = var4.nextInt(3);
					func_175804_a(worldIn, p_74875_3_, var5, var6, 0, var5, var6, var7,
							Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
				}
			}

			return true;
		}
	}

	public static class Entrance extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000459";

		public Entrance() {}

		public Entrance(final int p_i45617_1_, final Random p_i45617_2_, final StructureBoundingBox p_i45617_3_,
				final EnumFacing p_i45617_4_) {
			super(p_i45617_1_);
			coordBaseMode = p_i45617_4_;
			boundingBox = p_i45617_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
		}

		public static StructureNetherBridgePieces.Entrance func_175881_a(final List p_175881_0_,
				final Random p_175881_1_, final int p_175881_2_, final int p_175881_3_, final int p_175881_4_,
				final EnumFacing p_175881_5_, final int p_175881_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175881_2_, p_175881_3_, p_175881_4_,
					-5, -3, 0, 13, 14, 13, p_175881_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175881_0_, var7) == null
					? new StructureNetherBridgePieces.Entrance(p_175881_6_, p_175881_1_, var7, p_175881_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			int var4;

			for (var4 = 1; var4 <= 11; var4 += 2) {
				func_175804_a(worldIn, p_74875_3_, var4, 10, 0, var4, 11, 0,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
				func_175804_a(worldIn, p_74875_3_, var4, 10, 12, var4, 11, 12,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
				func_175804_a(worldIn, p_74875_3_, 0, 10, var4, 0, 11, var4,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
				func_175804_a(worldIn, p_74875_3_, 12, 10, var4, 12, 11, var4,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
				func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), var4, 13, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), var4, 13, 12, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, var4, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, var4, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 12, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, var4 + 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, var4 + 1, p_74875_3_);
			}

			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, p_74875_3_);

			for (var4 = 3; var4 <= 9; var4 += 2) {
				func_175804_a(worldIn, p_74875_3_, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence.getDefaultState(),
						Blocks.nether_brick_fence.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 11, 7, var4, 11, 8, var4,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
			}

			func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			int var5;

			for (var4 = 4; var4 <= 8; ++var4) {
				for (var5 = 0; var5 <= 2; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 12 - var5, p_74875_3_);
				}
			}

			for (var4 = 0; var4 <= 2; ++var4) {
				for (var5 = 4; var5 <= 8; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 12 - var4, -1, var5, p_74875_3_);
				}
			}

			func_175804_a(worldIn, p_74875_3_, 5, 5, 5, 7, 5, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 1, 6, 6, 4, 6, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 6, 0, 6, p_74875_3_);
			func_175811_a(worldIn, Blocks.flowing_lava.getDefaultState(), 6, 5, 6, p_74875_3_);
			final BlockPos var6 = new BlockPos(getXWithOffset(6, 6), getYWithOffset(5), getZWithOffset(6, 6));

			if (p_74875_3_.func_175898_b(var6)) {
				worldIn.func_175637_a(Blocks.flowing_lava, var6, p_74875_2_);
			}

			return true;
		}
	}

	public static class NetherStalkRoom extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000464";

		public NetherStalkRoom() {}

		public NetherStalkRoom(final int p_i45612_1_, final Random p_i45612_2_, final StructureBoundingBox p_i45612_3_,
				final EnumFacing p_i45612_4_) {
			super(p_i45612_1_);
			coordBaseMode = p_i45612_4_;
			boundingBox = p_i45612_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 5, 11, true);
		}

		public static StructureNetherBridgePieces.NetherStalkRoom func_175875_a(final List p_175875_0_,
				final Random p_175875_1_, final int p_175875_2_, final int p_175875_3_, final int p_175875_4_,
				final EnumFacing p_175875_5_, final int p_175875_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175875_2_, p_175875_3_, p_175875_4_,
					-5, -3, 0, 13, 14, 13, p_175875_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175875_0_, var7) == null
					? new StructureNetherBridgePieces.NetherStalkRoom(p_175875_6_, p_175875_1_, var7, p_175875_5_)
					: null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			int var4;

			for (var4 = 1; var4 <= 11; var4 += 2) {
				func_175804_a(worldIn, p_74875_3_, var4, 10, 0, var4, 11, 0,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
				func_175804_a(worldIn, p_74875_3_, var4, 10, 12, var4, 11, 12,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
				func_175804_a(worldIn, p_74875_3_, 0, 10, var4, 0, 11, var4,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
				func_175804_a(worldIn, p_74875_3_, 12, 10, var4, 12, 11, var4,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
				func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), var4, 13, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), var4, 13, 12, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, var4, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, var4, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 12, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, var4 + 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, var4 + 1, p_74875_3_);
			}

			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, p_74875_3_);

			for (var4 = 3; var4 <= 9; var4 += 2) {
				func_175804_a(worldIn, p_74875_3_, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence.getDefaultState(),
						Blocks.nether_brick_fence.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 11, 7, var4, 11, 8, var4,
						Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(),
						false);
			}

			var4 = getMetadataWithOffset(Blocks.nether_brick_stairs, 3);
			int var5;
			int var6;
			int var7;

			for (var5 = 0; var5 <= 6; ++var5) {
				var6 = var5 + 4;

				for (var7 = 5; var7 <= 7; ++var7) {
					func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), var7, 5 + var5, var6,
							p_74875_3_);
				}

				if (var6 >= 5 && var6 <= 8) {
					func_175804_a(worldIn, p_74875_3_, 5, 5, var6, 7, var5 + 4, var6,
							Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
				} else if (var6 >= 9 && var6 <= 10) {
					func_175804_a(worldIn, p_74875_3_, 5, 8, var6, 7, var5 + 4, var6,
							Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
				}

				if (var5 >= 1) {
					func_175804_a(worldIn, p_74875_3_, 5, 6 + var5, var6, 7, 9 + var5, var6,
							Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
				}
			}

			for (var5 = 5; var5 <= 7; ++var5) {
				func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), var5, 12, 11, p_74875_3_);
			}

			func_175804_a(worldIn, p_74875_3_, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 13, 12, 7, 13, 12, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 2, 3, 5, 3, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 9, 3, 5, 10, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 4, 2, 5, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 9, 5, 2, 10, 5, 3, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 9, 5, 9, 10, 5, 10, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 10, 5, 4, 10, 5, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			var5 = getMetadataWithOffset(Blocks.nether_brick_stairs, 0);
			var6 = getMetadataWithOffset(Blocks.nether_brick_stairs, 1);
			func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 2, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 3, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 9, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 2, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 3, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 9, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 10, p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 3, 4, 4, 4, 4, 8, Blocks.soul_sand.getDefaultState(),
					Blocks.soul_sand.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 4, 4, 9, 4, 8, Blocks.soul_sand.getDefaultState(),
					Blocks.soul_sand.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 3, 5, 4, 4, 5, 8, Blocks.nether_wart.getDefaultState(),
					Blocks.nether_wart.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 5, 4, 9, 5, 8, Blocks.nether_wart.getDefaultState(),
					Blocks.nether_wart.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			int var8;

			for (var7 = 4; var7 <= 8; ++var7) {
				for (var8 = 0; var8 <= 2; ++var8) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, var8, p_74875_3_);
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, 12 - var8, p_74875_3_);
				}
			}

			for (var7 = 0; var7 <= 2; ++var7) {
				for (var8 = 4; var8 <= 8; ++var8) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, var8, p_74875_3_);
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 12 - var7, -1, var8, p_74875_3_);
				}
			}

			return true;
		}
	}

	abstract static class Piece extends StructureComponent {
		protected static final List field_111019_a = Lists.newArrayList(
				new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5),
						new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5),
						new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15),
						new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5),
						new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10),
						new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8),
						new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5),
						new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3),
						new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 2, 4, 2) });
		// private static final String __OBFID =
		// "http://https://fuckuskid00000466";

		public Piece() {}

		protected Piece(final int p_i2054_1_) {
			super(p_i2054_1_);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {}

		private int getTotalWeight(final List p_74960_1_) {
			boolean var2 = false;
			int var3 = 0;
			StructureNetherBridgePieces.PieceWeight var5;

			for (final Iterator var4 = p_74960_1_.iterator(); var4.hasNext(); var3 += var5.field_78826_b) {
				var5 = (StructureNetherBridgePieces.PieceWeight) var4.next();

				if (var5.field_78824_d > 0 && var5.field_78827_c < var5.field_78824_d) {
					var2 = true;
				}
			}

			return var2 ? var3 : -1;
		}

		private StructureNetherBridgePieces.Piece func_175871_a(final StructureNetherBridgePieces.Start p_175871_1_,
				final List p_175871_2_, final List p_175871_3_, final Random p_175871_4_, final int p_175871_5_,
				final int p_175871_6_, final int p_175871_7_, final EnumFacing p_175871_8_, final int p_175871_9_) {
			final int var10 = getTotalWeight(p_175871_2_);
			final boolean var11 = var10 > 0 && p_175871_9_ <= 30;
			int var12 = 0;

			while (var12 < 5 && var11) {
				++var12;
				int var13 = p_175871_4_.nextInt(var10);
				final Iterator var14 = p_175871_2_.iterator();

				while (var14.hasNext()) {
					final StructureNetherBridgePieces.PieceWeight var15 = (StructureNetherBridgePieces.PieceWeight) var14
							.next();
					var13 -= var15.field_78826_b;

					if (var13 < 0) {
						if (!var15.func_78822_a(p_175871_9_)
								|| var15 == p_175871_1_.theNetherBridgePieceWeight && !var15.field_78825_e) {
							break;
						}

						final StructureNetherBridgePieces.Piece var16 = StructureNetherBridgePieces.func_175887_b(var15,
								p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_,
								p_175871_9_);

						if (var16 != null) {
							++var15.field_78827_c;
							p_175871_1_.theNetherBridgePieceWeight = var15;

							if (!var15.func_78823_a()) {
								p_175871_2_.remove(var15);
							}

							return var16;
						}
					}
				}
			}

			return StructureNetherBridgePieces.End.func_175884_a(p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_,
					p_175871_7_, p_175871_8_, p_175871_9_);
		}

		private StructureComponent func_175870_a(final StructureNetherBridgePieces.Start p_175870_1_,
				final List p_175870_2_, final Random p_175870_3_, final int p_175870_4_, final int p_175870_5_,
				final int p_175870_6_, final EnumFacing p_175870_7_, final int p_175870_8_, final boolean p_175870_9_) {
			if (Math.abs(p_175870_4_ - p_175870_1_.getBoundingBox().minX) <= 112
					&& Math.abs(p_175870_6_ - p_175870_1_.getBoundingBox().minZ) <= 112) {
				List var10 = p_175870_1_.primaryWeights;

				if (p_175870_9_) {
					var10 = p_175870_1_.secondaryWeights;
				}

				final StructureNetherBridgePieces.Piece var11 = func_175871_a(p_175870_1_, var10, p_175870_2_,
						p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_ + 1);

				if (var11 != null) {
					p_175870_2_.add(var11);
					p_175870_1_.field_74967_d.add(var11);
				}

				return var11;
			} else {
				return StructureNetherBridgePieces.End.func_175884_a(p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_,
						p_175870_6_, p_175870_7_, p_175870_8_);
			}
		}

		protected StructureComponent getNextComponentNormal(final StructureNetherBridgePieces.Start p_74963_1_,
				final List p_74963_2_, final Random p_74963_3_, final int p_74963_4_, final int p_74963_5_,
				final boolean p_74963_6_) {
			if (coordBaseMode != null) {
				switch (StructureNetherBridgePieces.SwitchEnumFacing.field_175888_a[coordBaseMode.ordinal()]) {
					case 1:
						return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, boundingBox.minX + p_74963_4_,
								boundingBox.minY + p_74963_5_, boundingBox.minZ - 1, coordBaseMode, getComponentType(),
								p_74963_6_);

					case 2:
						return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, boundingBox.minX + p_74963_4_,
								boundingBox.minY + p_74963_5_, boundingBox.maxZ + 1, coordBaseMode, getComponentType(),
								p_74963_6_);

					case 3:
						return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, boundingBox.minX - 1,
								boundingBox.minY + p_74963_5_, boundingBox.minZ + p_74963_4_, coordBaseMode,
								getComponentType(), p_74963_6_);

					case 4:
						return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, boundingBox.maxX + 1,
								boundingBox.minY + p_74963_5_, boundingBox.minZ + p_74963_4_, coordBaseMode,
								getComponentType(), p_74963_6_);
				}
			}

			return null;
		}

		protected StructureComponent getNextComponentX(final StructureNetherBridgePieces.Start p_74961_1_,
				final List p_74961_2_, final Random p_74961_3_, final int p_74961_4_, final int p_74961_5_,
				final boolean p_74961_6_) {
			if (coordBaseMode != null) {
				switch (StructureNetherBridgePieces.SwitchEnumFacing.field_175888_a[coordBaseMode.ordinal()]) {
					case 1:
						return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, boundingBox.minX - 1,
								boundingBox.minY + p_74961_4_, boundingBox.minZ + p_74961_5_, EnumFacing.WEST,
								getComponentType(), p_74961_6_);

					case 2:
						return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, boundingBox.minX - 1,
								boundingBox.minY + p_74961_4_, boundingBox.minZ + p_74961_5_, EnumFacing.WEST,
								getComponentType(), p_74961_6_);

					case 3:
						return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, boundingBox.minX + p_74961_5_,
								boundingBox.minY + p_74961_4_, boundingBox.minZ - 1, EnumFacing.NORTH,
								getComponentType(), p_74961_6_);

					case 4:
						return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, boundingBox.minX + p_74961_5_,
								boundingBox.minY + p_74961_4_, boundingBox.minZ - 1, EnumFacing.NORTH,
								getComponentType(), p_74961_6_);
				}
			}

			return null;
		}

		protected StructureComponent getNextComponentZ(final StructureNetherBridgePieces.Start p_74965_1_,
				final List p_74965_2_, final Random p_74965_3_, final int p_74965_4_, final int p_74965_5_,
				final boolean p_74965_6_) {
			if (coordBaseMode != null) {
				switch (StructureNetherBridgePieces.SwitchEnumFacing.field_175888_a[coordBaseMode.ordinal()]) {
					case 1:
						return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, boundingBox.maxX + 1,
								boundingBox.minY + p_74965_4_, boundingBox.minZ + p_74965_5_, EnumFacing.EAST,
								getComponentType(), p_74965_6_);

					case 2:
						return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, boundingBox.maxX + 1,
								boundingBox.minY + p_74965_4_, boundingBox.minZ + p_74965_5_, EnumFacing.EAST,
								getComponentType(), p_74965_6_);

					case 3:
						return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, boundingBox.minX + p_74965_5_,
								boundingBox.minY + p_74965_4_, boundingBox.maxZ + 1, EnumFacing.SOUTH,
								getComponentType(), p_74965_6_);

					case 4:
						return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, boundingBox.minX + p_74965_5_,
								boundingBox.minY + p_74965_4_, boundingBox.maxZ + 1, EnumFacing.SOUTH,
								getComponentType(), p_74965_6_);
				}
			}

			return null;
		}

		protected static boolean isAboveGround(final StructureBoundingBox p_74964_0_) {
			return p_74964_0_ != null && p_74964_0_.minY > 10;
		}
	}

	static class PieceWeight {
		public Class weightClass;
		public final int field_78826_b;
		public int field_78827_c;
		public int field_78824_d;
		public boolean field_78825_e;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000467";

		public PieceWeight(final Class p_i2055_1_, final int p_i2055_2_, final int p_i2055_3_,
				final boolean p_i2055_4_) {
			weightClass = p_i2055_1_;
			field_78826_b = p_i2055_2_;
			field_78824_d = p_i2055_3_;
			field_78825_e = p_i2055_4_;
		}

		public PieceWeight(final Class p_i2056_1_, final int p_i2056_2_, final int p_i2056_3_) {
			this(p_i2056_1_, p_i2056_2_, p_i2056_3_, false);
		}

		public boolean func_78822_a(final int p_78822_1_) {
			return field_78824_d == 0 || field_78827_c < field_78824_d;
		}

		public boolean func_78823_a() {
			return field_78824_d == 0 || field_78827_c < field_78824_d;
		}
	}

	public static class Stairs extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000469";

		public Stairs() {}

		public Stairs(final int p_i45609_1_, final Random p_i45609_2_, final StructureBoundingBox p_i45609_3_,
				final EnumFacing p_i45609_4_) {
			super(p_i45609_1_);
			coordBaseMode = p_i45609_4_;
			boundingBox = p_i45609_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentZ((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 6, 2, false);
		}

		public static StructureNetherBridgePieces.Stairs func_175872_a(final List p_175872_0_, final Random p_175872_1_,
				final int p_175872_2_, final int p_175872_3_, final int p_175872_4_, final int p_175872_5_,
				final EnumFacing p_175872_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175872_2_, p_175872_3_, p_175872_4_,
					-2, 0, 0, 7, 11, 7, p_175872_6_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175872_0_, var7) == null
					? new StructureNetherBridgePieces.Stairs(p_175872_5_, p_175872_1_, var7, p_175872_6_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 6, 10, 6, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 1, 8, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 2, 0, 6, 8, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 1, 0, 8, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 2, 1, 6, 8, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 2, 6, 5, 8, 6, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 5, 2, 5, p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 4, 2, 5, 4, 3, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 3, 2, 5, 3, 4, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 2, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 2, 5, 1, 6, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 7, 1, 5, 7, 4, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 8, 2, 6, 8, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 6, 0, 4, 8, 0, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);

			for (int var4 = 0; var4 <= 6; ++var4) {
				for (int var5 = 0; var5 <= 6; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
				}
			}

			return true;
		}
	}

	public static class Start extends StructureNetherBridgePieces.Crossing3 {
		public StructureNetherBridgePieces.PieceWeight theNetherBridgePieceWeight;
		public List primaryWeights;
		public List secondaryWeights;
		public List field_74967_d = Lists.newArrayList();
		// private static final String __OBFID =
		// "http://https://fuckuskid00000470";

		public Start() {}

		public Start(final Random p_i2059_1_, final int p_i2059_2_, final int p_i2059_3_) {
			super(p_i2059_1_, p_i2059_2_, p_i2059_3_);
			primaryWeights = Lists.newArrayList();
			StructureNetherBridgePieces.PieceWeight[] var4 = StructureNetherBridgePieces.primaryComponents;
			int var5 = var4.length;
			int var6;
			StructureNetherBridgePieces.PieceWeight var7;

			for (var6 = 0; var6 < var5; ++var6) {
				var7 = var4[var6];
				var7.field_78827_c = 0;
				primaryWeights.add(var7);
			}

			secondaryWeights = Lists.newArrayList();
			var4 = StructureNetherBridgePieces.secondaryComponents;
			var5 = var4.length;

			for (var6 = 0; var6 < var5; ++var6) {
				var7 = var4[var6];
				var7.field_78827_c = 0;
				secondaryWeights.add(var7);
			}
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
		}
	}

	public static class Straight extends StructureNetherBridgePieces.Piece {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000456";

		public Straight() {}

		public Straight(final int p_i45620_1_, final Random p_i45620_2_, final StructureBoundingBox p_i45620_3_,
				final EnumFacing p_i45620_4_) {
			super(p_i45620_1_);
			coordBaseMode = p_i45620_4_;
			boundingBox = p_i45620_3_;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			getNextComponentNormal((StructureNetherBridgePieces.Start) p_74861_1_, p_74861_2_, p_74861_3_, 1, 3, false);
		}

		public static StructureNetherBridgePieces.Straight func_175882_a(final List p_175882_0_,
				final Random p_175882_1_, final int p_175882_2_, final int p_175882_3_, final int p_175882_4_,
				final EnumFacing p_175882_5_, final int p_175882_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175882_2_, p_175882_3_, p_175882_4_,
					-1, -3, 0, 5, 10, 19, p_175882_5_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175882_0_, var7) == null
					? new StructureNetherBridgePieces.Straight(p_175882_6_, p_175882_1_, var7, p_175882_5_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 4, 4, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 5, 0, 3, 7, 18, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 0, 5, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 5, 0, 4, 5, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 2, 5, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 2, 13, 4, 2, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 3, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 0, 15, 4, 1, 18, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);

			for (int var4 = 0; var4 <= 4; ++var4) {
				for (int var5 = 0; var5 <= 2; ++var5) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 18 - var5, p_74875_3_);
				}
			}

			func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 4, 0, 4, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 3, 14, 0, 4, 14, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 1, 17, 0, 4, 17, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 3, 4, 4, 4, 4, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 3, 14, 4, 4, 14, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 1, 17, 4, 4, 17, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			return true;
		}
	}

	static final class SwitchEnumFacing {
		static final int[] field_175888_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00001997";

		static {
			try {
				field_175888_a[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_175888_a[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_175888_a[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_175888_a[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}

	public static class Throne extends StructureNetherBridgePieces.Piece {
		private boolean hasSpawner;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000465";

		public Throne() {}

		public Throne(final int p_i45611_1_, final Random p_i45611_2_, final StructureBoundingBox p_i45611_3_,
				final EnumFacing p_i45611_4_) {
			super(p_i45611_1_);
			coordBaseMode = p_i45611_4_;
			boundingBox = p_i45611_3_;
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			hasSpawner = p_143011_1_.getBoolean("Mob");
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Mob", hasSpawner);
		}

		public static StructureNetherBridgePieces.Throne func_175874_a(final List p_175874_0_, final Random p_175874_1_,
				final int p_175874_2_, final int p_175874_3_, final int p_175874_4_, final int p_175874_5_,
				final EnumFacing p_175874_6_) {
			final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175874_2_, p_175874_3_, p_175874_4_,
					-2, 0, 0, 7, 8, 9, p_175874_6_);
			return isAboveGround(var7) && StructureComponent.findIntersecting(p_175874_0_, var7) == null
					? new StructureNetherBridgePieces.Throne(p_175874_5_, p_175874_1_, var7, p_175874_6_) : null;
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 6, 7, 7, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 0, 0, 5, 1, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 2, 1, 5, 2, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 3, 2, 5, 3, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 4, 3, 5, 4, 7, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 1, 4, 2, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 2, 0, 5, 4, 2, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 5, 2, 1, 5, 3, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 5, 2, 5, 5, 3, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 0, 5, 3, 0, 5, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 5, 3, 6, 5, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 5, 8, 5, 5, 8, Blocks.nether_brick.getDefaultState(),
					Blocks.nether_brick.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 1, 6, 3, p_74875_3_);
			func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 5, 6, 3, p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence.getDefaultState(),
					Blocks.nether_brick_fence.getDefaultState(), false);

			if (!hasSpawner) {
				final BlockPos var4 = new BlockPos(getXWithOffset(3, 5), getYWithOffset(5), getZWithOffset(3, 5));

				if (p_74875_3_.func_175898_b(var4)) {
					hasSpawner = true;
					worldIn.setBlockState(var4, Blocks.mob_spawner.getDefaultState(), 2);
					final TileEntity var5 = worldIn.getTileEntity(var4);

					if (var5 instanceof TileEntityMobSpawner) {
						((TileEntityMobSpawner) var5).getSpawnerBaseLogic().setEntityName("Blaze");
					}
				}
			}

			for (int var6 = 0; var6 <= 6; ++var6) {
				for (int var7 = 0; var7 <= 6; ++var7) {
					func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var6, -1, var7, p_74875_3_);
				}
			}

			return true;
		}
	}
}
