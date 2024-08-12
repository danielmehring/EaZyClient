package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class ComponentScatteredFeaturePieces {

public static final int EaZy = 1807;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000473";

	public static void registerScatteredFeaturePieces() {
		MapGenStructureIO.registerStructureComponent(ComponentScatteredFeaturePieces.DesertPyramid.class, "TeDP");
		MapGenStructureIO.registerStructureComponent(ComponentScatteredFeaturePieces.JunglePyramid.class, "TeJP");
		MapGenStructureIO.registerStructureComponent(ComponentScatteredFeaturePieces.SwampHut.class, "TeSH");
	}

	public static class DesertPyramid extends ComponentScatteredFeaturePieces.Feature {
		private final boolean[] field_74940_h = new boolean[4];
		private static final List itemsToGenerateInTemple = Lists.newArrayList(
				new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
						new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
						new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15),
						new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2),
						new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20),
						new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16),
						new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
						new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
		// private static final String __OBFID =
		// "http://https://fuckuskid00000476";

		public DesertPyramid() {}

		public DesertPyramid(final Random p_i2062_1_, final int p_i2062_2_, final int p_i2062_3_) {
			super(p_i2062_1_, p_i2062_2_, 64, p_i2062_3_, 21, 15, 21);
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("hasPlacedChest0", field_74940_h[0]);
			p_143012_1_.setBoolean("hasPlacedChest1", field_74940_h[1]);
			p_143012_1_.setBoolean("hasPlacedChest2", field_74940_h[2]);
			p_143012_1_.setBoolean("hasPlacedChest3", field_74940_h[3]);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			field_74940_h[0] = p_143011_1_.getBoolean("hasPlacedChest0");
			field_74940_h[1] = p_143011_1_.getBoolean("hasPlacedChest1");
			field_74940_h[2] = p_143011_1_.getBoolean("hasPlacedChest2");
			field_74940_h[3] = p_143011_1_.getBoolean("hasPlacedChest3");
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			func_175804_a(worldIn, p_74875_3_, 0, -4, 0, scatteredFeatureSizeX - 1, 0, scatteredFeatureSizeZ - 1,
					Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
			int var4;

			for (var4 = 1; var4 <= 9; ++var4) {
				func_175804_a(worldIn, p_74875_3_, var4, var4, var4, scatteredFeatureSizeX - 1 - var4, var4,
						scatteredFeatureSizeZ - 1 - var4, Blocks.sandstone.getDefaultState(),
						Blocks.sandstone.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, var4 + 1, var4, var4 + 1, scatteredFeatureSizeX - 2 - var4, var4,
						scatteredFeatureSizeZ - 2 - var4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(),
						false);
			}

			int var5;

			for (var4 = 0; var4 < scatteredFeatureSizeX; ++var4) {
				for (var5 = 0; var5 < scatteredFeatureSizeZ; ++var5) {
					final byte var6 = -5;
					func_175808_b(worldIn, Blocks.sandstone.getDefaultState(), var4, var6, var5, p_74875_3_);
				}
			}

			var4 = getMetadataWithOffset(Blocks.sandstone_stairs, 3);
			var5 = getMetadataWithOffset(Blocks.sandstone_stairs, 2);
			final int var14 = getMetadataWithOffset(Blocks.sandstone_stairs, 0);
			final int var7 = getMetadataWithOffset(Blocks.sandstone_stairs, 1);
			final int var8 = ~EnumDyeColor.ORANGE.getDyeColorDamage() & 15;
			final int var9 = ~EnumDyeColor.BLUE.getDyeColorDamage() & 15;
			func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 9, 4, Blocks.sandstone.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 1, 10, 1, 3, 10, 3, Blocks.sandstone.getDefaultState(),
					Blocks.sandstone.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), 2, 10, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var5), 2, 10, 4, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var14), 0, 10, 2, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var7), 4, 10, 2, p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 5, 0, 0, scatteredFeatureSizeX - 1, 9, 4,
					Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 4, 10, 1, scatteredFeatureSizeX - 2, 10, 3,
					Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), scatteredFeatureSizeX - 3, 10, 0,
					p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var5), scatteredFeatureSizeX - 3, 10, 4,
					p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var14), scatteredFeatureSizeX - 5, 10, 2,
					p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var7), scatteredFeatureSizeX - 1, 10, 2,
					p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 8, 0, 0, 12, 4, 4, Blocks.sandstone.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 9, 1, 0, 11, 3, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9,
					1, 1, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9,
					2, 1, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9,
					3, 1, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					10, 3, 1, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					11, 3, 1, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					11, 2, 1, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					11, 1, 1, p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 8, 3, 3, Blocks.sandstone.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 4, 1, 2, 8, 2, 2, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 12, 1, 1, 16, 3, 3, Blocks.sandstone.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 12, 1, 2, 16, 2, 2, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 4, 5, scatteredFeatureSizeX - 6, 4, scatteredFeatureSizeZ - 6,
					Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 9, 4, 9, 11, 4, 11, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 8, 1, 8, 8, 3, 8,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175804_a(worldIn, p_74875_3_, 12, 1, 8, 12, 3, 8,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175804_a(worldIn, p_74875_3_, 8, 1, 12, 8, 3, 12,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175804_a(worldIn, p_74875_3_, 12, 1, 12, 12, 3, 12,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175804_a(worldIn, p_74875_3_, 1, 1, 5, 4, 4, 11, Blocks.sandstone.getDefaultState(),
					Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 5, 1, 5, scatteredFeatureSizeX - 2, 4, 11,
					Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 6, 7, 9, 6, 7, 11, Blocks.sandstone.getDefaultState(),
					Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 7, 7, 9, scatteredFeatureSizeX - 7, 7, 11,
					Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 5, 5, 9, 5, 7, 11,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 6, 5, 9, scatteredFeatureSizeX - 6, 7, 11,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 5, 5, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 5, 6, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 6, 6, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), scatteredFeatureSizeX - 6, 5, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), scatteredFeatureSizeX - 6, 6, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), scatteredFeatureSizeX - 7, 6, 10, p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 2, 4, 4, 2, 6, 4, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 3, 4, 4, scatteredFeatureSizeX - 3, 6, 4,
					Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), 2, 4, 5, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), 2, 3, 4, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), scatteredFeatureSizeX - 3, 4, 5,
					p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), scatteredFeatureSizeX - 3, 3, 4,
					p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 1, 1, 3, 2, 2, 3, Blocks.sandstone.getDefaultState(),
					Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 3, 1, 3, scatteredFeatureSizeX - 2, 2, 3,
					Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getDefaultState(), 1, 1, 2, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getDefaultState(), scatteredFeatureSizeX - 2, 1, 2,
					p_74875_3_);
			func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SAND.func_176624_a()), 1,
					2, 2, p_74875_3_);
			func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SAND.func_176624_a()),
					scatteredFeatureSizeX - 2, 2, 2, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var7), 2, 1, 2, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var14), scatteredFeatureSizeX - 3, 1, 2,
					p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 4, 3, 5, 4, 3, 18, Blocks.sandstone.getDefaultState(),
					Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 5, 3, 5, scatteredFeatureSizeX - 5, 3, 17,
					Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 3, 1, 5, 4, 2, 16, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, scatteredFeatureSizeX - 6, 1, 5, scatteredFeatureSizeX - 5, 2, 16,
					Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
			int var10;

			for (var10 = 5; var10 <= 17; var10 += 2) {
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 4, 1, var10,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), 4, 2,
						var10, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
						scatteredFeatureSizeX - 5, 1, var10, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()),
						scatteredFeatureSizeX - 5, 2, var10, p_74875_3_);
			}

			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 10, 0, 7, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 10, 0, 8, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 9, 0, 9, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 11, 0, 9, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 8, 0, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 12, 0, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 7, 0, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 13, 0, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 9, 0, 11, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 11, 0, 11, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 10, 0, 12, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 10, 0, 13, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 10, 0, 10, p_74875_3_);

			for (var10 = 0; var10 <= scatteredFeatureSizeX - 1; var10 += scatteredFeatureSizeX - 1) {
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 2, 1,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 2, 2, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 2, 3,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 3, 1,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 3, 2, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 3, 3,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 4, 1, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), var10, 4,
						2, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 4, 3, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 5, 1,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 5, 2, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 5, 3,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 6, 1, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), var10, 6,
						2, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 6, 3, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 7, 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 7, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 7, 3, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 8, 1,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 8, 2,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 8, 3,
						p_74875_3_);
			}

			for (var10 = 2; var10 <= scatteredFeatureSizeX - 3; var10 += scatteredFeatureSizeX - 3 - 2) {
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10 - 1, 2,
						0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 2, 0, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10 + 1, 2,
						0, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10 - 1, 3,
						0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 3, 0, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10 + 1, 3,
						0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10 - 1, 4, 0,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), var10, 4,
						0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10 + 1, 4, 0,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10 - 1, 5,
						0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 5, 0, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10 + 1, 5,
						0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10 - 1, 6, 0,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), var10, 6,
						0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10 + 1, 6, 0,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10 - 1, 7, 0,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10, 7, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), var10 + 1, 7, 0,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10 - 1, 8,
						0, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10, 8, 0,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var10 + 1, 8,
						0, p_74875_3_);
			}

			func_175804_a(worldIn, p_74875_3_, 8, 4, 0, 12, 6, 0,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 8, 6, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 12, 6, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 9, 5, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()),
					10, 5, 0, p_74875_3_);
			func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var8), 11, 5, 0, p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 8, -14, 8, 12, -11, 12,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175804_a(worldIn, p_74875_3_, 8, -10, 8, 12, -10, 12,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), false);
			func_175804_a(worldIn, p_74875_3_, 8, -9, 8, 12, -9, 12,
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
			func_175804_a(worldIn, p_74875_3_, 8, -8, 8, 12, -1, 12, Blocks.sandstone.getDefaultState(),
					Blocks.sandstone.getDefaultState(), false);
			func_175804_a(worldIn, p_74875_3_, 9, -11, 9, 11, -1, 11, Blocks.air.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.stone_pressure_plate.getDefaultState(), 10, -11, 10, p_74875_3_);
			func_175804_a(worldIn, p_74875_3_, 9, -13, 9, 11, -13, 11, Blocks.tnt.getDefaultState(),
					Blocks.air.getDefaultState(), false);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 8, -11, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 8, -10, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()),
					7, -10, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 7,
					-11, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 12, -11, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 12, -10, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()),
					13, -10, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					13, -11, 10, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, -11, 8, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, -10, 8, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()),
					10, -10, 7, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					10, -11, 7, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, -11, 12, p_74875_3_);
			func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, -10, 12, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()),
					10, -10, 13, p_74875_3_);
			func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()),
					10, -11, 13, p_74875_3_);
			final Iterator var15 = EnumFacing.Plane.HORIZONTAL.iterator();

			while (var15.hasNext()) {
				final EnumFacing var11 = (EnumFacing) var15.next();

				if (!field_74940_h[var11.getHorizontalIndex()]) {
					final int var12 = var11.getFrontOffsetX() * 2;
					final int var13 = var11.getFrontOffsetZ() * 2;
					field_74940_h[var11.getHorizontalIndex()] = func_180778_a(worldIn, p_74875_3_, p_74875_2_,
							10 + var12, -11, 10 + var13,
							WeightedRandomChestContent.func_177629_a(itemsToGenerateInTemple,
									new WeightedRandomChestContent[] {
											Items.enchanted_book.getRandomEnchantedBook(p_74875_2_) }),
							2 + p_74875_2_.nextInt(5));
				}
			}

			return true;
		}
	}

	abstract static class Feature extends StructureComponent {
		protected int scatteredFeatureSizeX;
		protected int scatteredFeatureSizeY;
		protected int scatteredFeatureSizeZ;
		protected int field_74936_d = -1;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000479";

		public Feature() {}

		protected Feature(final Random p_i2065_1_, final int p_i2065_2_, final int p_i2065_3_, final int p_i2065_4_,
				final int p_i2065_5_, final int p_i2065_6_, final int p_i2065_7_) {
			super(0);
			scatteredFeatureSizeX = p_i2065_5_;
			scatteredFeatureSizeY = p_i2065_6_;
			scatteredFeatureSizeZ = p_i2065_7_;
			coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2065_1_);

			switch (ComponentScatteredFeaturePieces.SwitchEnumFacing.field_175956_a[coordBaseMode.ordinal()]) {
				case 1:
				case 2:
					boundingBox = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_,
							p_i2065_2_ + p_i2065_5_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_7_ - 1);
					break;

				default:
					boundingBox = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_,
							p_i2065_2_ + p_i2065_7_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_5_ - 1);
			}
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			p_143012_1_.setInteger("Width", scatteredFeatureSizeX);
			p_143012_1_.setInteger("Height", scatteredFeatureSizeY);
			p_143012_1_.setInteger("Depth", scatteredFeatureSizeZ);
			p_143012_1_.setInteger("HPos", field_74936_d);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			scatteredFeatureSizeX = p_143011_1_.getInteger("Width");
			scatteredFeatureSizeY = p_143011_1_.getInteger("Height");
			scatteredFeatureSizeZ = p_143011_1_.getInteger("Depth");
			field_74936_d = p_143011_1_.getInteger("HPos");
		}

		protected boolean func_74935_a(final World worldIn, final StructureBoundingBox p_74935_2_,
				final int p_74935_3_) {
			if (field_74936_d >= 0) {
				return true;
			} else {
				int var4 = 0;
				int var5 = 0;

				for (int var6 = boundingBox.minZ; var6 <= boundingBox.maxZ; ++var6) {
					for (int var7 = boundingBox.minX; var7 <= boundingBox.maxX; ++var7) {
						final BlockPos var8 = new BlockPos(var7, 64, var6);

						if (p_74935_2_.func_175898_b(var8)) {
							var4 += Math.max(worldIn.func_175672_r(var8).getY(),
									worldIn.provider.getAverageGroundLevel());
							++var5;
						}
					}
				}

				if (var5 == 0) {
					return false;
				} else {
					field_74936_d = var4 / var5;
					boundingBox.offset(0, field_74936_d - boundingBox.minY + p_74935_3_, 0);
					return true;
				}
			}
		}
	}

	public static class JunglePyramid extends ComponentScatteredFeaturePieces.Feature {
		private boolean field_74947_h;
		private boolean field_74948_i;
		private boolean field_74945_j;
		private boolean field_74946_k;
		private static final List field_175816_i = Lists.newArrayList(
				new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
						new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
						new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15),
						new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2),
						new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20),
						new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16),
						new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
						new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
						new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
		private static final List field_175815_j = Lists.newArrayList(
				new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.arrow, 0, 2, 7, 30) });
		private static ComponentScatteredFeaturePieces.JunglePyramid.Stones junglePyramidsRandomScatteredStones = new ComponentScatteredFeaturePieces.JunglePyramid.Stones(
				(ComponentScatteredFeaturePieces.SwitchEnumFacing) null);
		// private static final String __OBFID =
		// "http://https://fuckuskid00000477";

		public JunglePyramid() {}

		public JunglePyramid(final Random p_i2064_1_, final int p_i2064_2_, final int p_i2064_3_) {
			super(p_i2064_1_, p_i2064_2_, 64, p_i2064_3_, 12, 10, 15);
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("placedMainChest", field_74947_h);
			p_143012_1_.setBoolean("placedHiddenChest", field_74948_i);
			p_143012_1_.setBoolean("placedTrap1", field_74945_j);
			p_143012_1_.setBoolean("placedTrap2", field_74946_k);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			field_74947_h = p_143011_1_.getBoolean("placedMainChest");
			field_74948_i = p_143011_1_.getBoolean("placedHiddenChest");
			field_74945_j = p_143011_1_.getBoolean("placedTrap1");
			field_74946_k = p_143011_1_.getBoolean("placedTrap2");
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (!func_74935_a(worldIn, p_74875_3_, 0)) {
				return false;
			} else {
				final int var4 = getMetadataWithOffset(Blocks.stone_stairs, 3);
				final int var5 = getMetadataWithOffset(Blocks.stone_stairs, 2);
				final int var6 = getMetadataWithOffset(Blocks.stone_stairs, 0);
				final int var7 = getMetadataWithOffset(Blocks.stone_stairs, 1);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, -4, 0, scatteredFeatureSizeX - 1, 0,
						scatteredFeatureSizeZ - 1, false, p_74875_2_, junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 1, 2, 9, 2, 2, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 1, 12, 9, 2, 12, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 1, 3, 2, 2, 11, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, 1, 3, 9, 2, 11, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 3, 1, 10, 6, 1, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 3, 13, 10, 6, 13, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 3, 2, 1, 6, 12, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 10, 3, 2, 10, 6, 12, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 3, 2, 9, 3, 12, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 6, 2, 9, 6, 12, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 3, 7, 3, 8, 7, 11, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 8, 4, 7, 8, 10, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithAir(worldIn, p_74875_3_, 3, 1, 3, 8, 2, 11);
				fillWithAir(worldIn, p_74875_3_, 4, 3, 6, 7, 3, 9);
				fillWithAir(worldIn, p_74875_3_, 2, 4, 2, 9, 5, 12);
				fillWithAir(worldIn, p_74875_3_, 4, 6, 5, 7, 6, 9);
				fillWithAir(worldIn, p_74875_3_, 5, 7, 6, 6, 7, 8);
				fillWithAir(worldIn, p_74875_3_, 5, 1, 2, 6, 2, 2);
				fillWithAir(worldIn, p_74875_3_, 5, 2, 12, 6, 2, 12);
				fillWithAir(worldIn, p_74875_3_, 5, 5, 1, 6, 5, 1);
				fillWithAir(worldIn, p_74875_3_, 5, 5, 13, 6, 5, 13);
				func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 5, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, 5, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 5, 9, p_74875_3_);
				func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, 5, 9, p_74875_3_);
				int var8;

				for (var8 = 0; var8 <= 14; var8 += 14) {
					fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 4, var8, 2, 5, var8, false, p_74875_2_,
							junglePyramidsRandomScatteredStones);
					fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 4, var8, 4, 5, var8, false, p_74875_2_,
							junglePyramidsRandomScatteredStones);
					fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, 4, var8, 7, 5, var8, false, p_74875_2_,
							junglePyramidsRandomScatteredStones);
					fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, 4, var8, 9, 5, var8, false, p_74875_2_,
							junglePyramidsRandomScatteredStones);
				}

				fillWithRandomizedBlocks(worldIn, p_74875_3_, 5, 6, 0, 6, 6, 0, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);

				for (var8 = 0; var8 <= 11; var8 += 11) {
					for (int var9 = 2; var9 <= 12; var9 += 2) {
						fillWithRandomizedBlocks(worldIn, p_74875_3_, var8, 4, var9, var8, 5, var9, false, p_74875_2_,
								junglePyramidsRandomScatteredStones);
					}

					fillWithRandomizedBlocks(worldIn, p_74875_3_, var8, 6, 5, var8, 6, 5, false, p_74875_2_,
							junglePyramidsRandomScatteredStones);
					fillWithRandomizedBlocks(worldIn, p_74875_3_, var8, 6, 9, var8, 6, 9, false, p_74875_2_,
							junglePyramidsRandomScatteredStones);
				}

				fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 7, 2, 2, 9, 2, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, 7, 2, 9, 9, 2, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, 7, 12, 2, 9, 12, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, 7, 12, 9, 9, 12, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 9, 4, 4, 9, 4, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, 9, 4, 7, 9, 4, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 9, 10, 4, 9, 10, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, 9, 10, 7, 9, 10, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 5, 9, 7, 6, 9, 7, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 5, 9, 6, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 6, 9, 6, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var5), 5, 9, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var5), 6, 9, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 4, 0, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 5, 0, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 6, 0, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 7, 0, 0, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 4, 1, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 4, 2, 9, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 4, 3, 10, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 7, 1, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 7, 2, 9, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 7, 3, 10, p_74875_3_);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 9, 4, 1, 9, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, 1, 9, 7, 1, 9, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 10, 7, 2, 10, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 5, 4, 5, 6, 4, 5, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var6), 4, 4, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var7), 7, 4, 5, p_74875_3_);

				for (var8 = 0; var8 < 4; ++var8) {
					func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var5), 5, 0 - var8, 6 + var8,
							p_74875_3_);
					func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var5), 6, 0 - var8, 6 + var8,
							p_74875_3_);
					fillWithAir(worldIn, p_74875_3_, 5, 0 - var8, 7 + var8, 6, 0 - var8, 9 + var8);
				}

				fillWithAir(worldIn, p_74875_3_, 1, -3, 12, 10, -1, 13);
				fillWithAir(worldIn, p_74875_3_, 1, -3, 1, 3, -1, 13);
				fillWithAir(worldIn, p_74875_3_, 1, -3, 1, 9, -1, 5);

				for (var8 = 1; var8 <= 13; var8 += 2) {
					fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, -3, var8, 1, -2, var8, false, p_74875_2_,
							junglePyramidsRandomScatteredStones);
				}

				for (var8 = 2; var8 <= 12; var8 += 2) {
					fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, -1, var8, 3, -1, var8, false, p_74875_2_,
							junglePyramidsRandomScatteredStones);
				}

				fillWithRandomizedBlocks(worldIn, p_74875_3_, 2, -2, 1, 5, -2, 1, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 7, -2, 1, 9, -2, 1, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 6, -3, 1, 6, -3, 1, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 6, -1, 1, 6, -1, 1, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				func_175811_a(worldIn, Blocks.tripwire_hook
						.getStateFromMeta(
								getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.EAST.getHorizontalIndex()))
						.withProperty(BlockTripWireHook.field_176265_M, true), 1, -3, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.tripwire_hook
						.getStateFromMeta(
								getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.WEST.getHorizontalIndex()))
						.withProperty(BlockTripWireHook.field_176265_M, true), 4, -3, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 2, -3, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 3, -3, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 7, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 6, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 4, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 3, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 5, -3, 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 4, -3, 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 3, -3, 1, p_74875_3_);

				if (!field_74945_j) {
					field_74945_j = func_175806_a(worldIn, p_74875_3_, p_74875_2_, 3, -2, 1,
							EnumFacing.NORTH.getIndex(), field_175815_j, 2);
				}

				func_175811_a(worldIn, Blocks.vine.getStateFromMeta(15), 3, -2, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.tripwire_hook
						.getStateFromMeta(
								getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.NORTH.getHorizontalIndex()))
						.withProperty(BlockTripWireHook.field_176265_M, true), 7, -3, 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.tripwire_hook
						.getStateFromMeta(
								getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.SOUTH.getHorizontalIndex()))
						.withProperty(BlockTripWireHook.field_176265_M, true), 7, -3, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 7, -3, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 7, -3, 3, p_74875_3_);
				func_175811_a(worldIn, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.field_176294_M, true), 7, -3, 4, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 8, -3, 6, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 9, -3, 6, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 9, -3, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 9, -3, 4, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 9, -2, 4, p_74875_3_);

				if (!field_74946_k) {
					field_74946_k = func_175806_a(worldIn, p_74875_3_, p_74875_2_, 9, -2, 3, EnumFacing.WEST.getIndex(),
							field_175815_j, 2);
				}

				func_175811_a(worldIn, Blocks.vine.getStateFromMeta(15), 8, -1, 3, p_74875_3_);
				func_175811_a(worldIn, Blocks.vine.getStateFromMeta(15), 8, -2, 3, p_74875_3_);

				if (!field_74947_h) {
					field_74947_h = func_180778_a(worldIn, p_74875_3_, p_74875_2_, 8, -3, 3,
							WeightedRandomChestContent.func_177629_a(field_175816_i,
									new WeightedRandomChestContent[] {
											Items.enchanted_book.getRandomEnchantedBook(p_74875_2_) }),
							2 + p_74875_2_.nextInt(5));
				}

				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 9, -3, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 8, -3, 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 4, -3, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 5, -2, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 5, -1, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 6, -3, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 7, -2, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 7, -1, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 8, -3, 5, p_74875_3_);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 9, -1, 1, 9, -1, 5, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithAir(worldIn, p_74875_3_, 8, -3, 8, 10, -1, 10);
				func_175811_a(worldIn, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 8, -2, 11,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 9, -2, 11,
						p_74875_3_);
				func_175811_a(worldIn, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 10, -2, 11,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.lever.getStateFromMeta(BlockLever.func_176357_a(
								EnumFacing.getFront(getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))),
						8, -2, 12, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.lever.getStateFromMeta(BlockLever.func_176357_a(
								EnumFacing.getFront(getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))),
						9, -2, 12, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.lever.getStateFromMeta(BlockLever.func_176357_a(
								EnumFacing.getFront(getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))),
						10, -2, 12, p_74875_3_);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 8, -3, 8, 8, -3, 10, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(worldIn, p_74875_3_, 10, -3, 8, 10, -3, 10, false, p_74875_2_,
						junglePyramidsRandomScatteredStones);
				func_175811_a(worldIn, Blocks.mossy_cobblestone.getDefaultState(), 10, -2, 9, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 8, -2, 9, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 8, -2, 10, p_74875_3_);
				func_175811_a(worldIn, Blocks.redstone_wire.getDefaultState(), 10, -1, 9, p_74875_3_);
				func_175811_a(worldIn, Blocks.sticky_piston.getStateFromMeta(EnumFacing.UP.getIndex()), 9, -2, 8,
						p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sticky_piston.getStateFromMeta(
								getMetadataWithOffset(Blocks.sticky_piston, EnumFacing.WEST.getIndex())),
						10, -2, 8, p_74875_3_);
				func_175811_a(worldIn,
						Blocks.sticky_piston.getStateFromMeta(
								getMetadataWithOffset(Blocks.sticky_piston, EnumFacing.WEST.getIndex())),
						10, -1, 8, p_74875_3_);
				func_175811_a(worldIn, Blocks.unpowered_repeater.getStateFromMeta(
						getMetadataWithOffset(Blocks.unpowered_repeater, EnumFacing.NORTH.getHorizontalIndex())), 10,
						-2, 10, p_74875_3_);

				if (!field_74948_i) {
					field_74948_i = func_180778_a(worldIn, p_74875_3_, p_74875_2_, 9, -3, 10,
							WeightedRandomChestContent.func_177629_a(field_175816_i,
									new WeightedRandomChestContent[] {
											Items.enchanted_book.getRandomEnchantedBook(p_74875_2_) }),
							2 + p_74875_2_.nextInt(5));
				}

				return true;
			}
		}

		static class Stones extends StructureComponent.BlockSelector {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000478";

			private Stones() {}

			@Override
			public void selectBlocks(final Random p_75062_1_, final int p_75062_2_, final int p_75062_3_,
					final int p_75062_4_, final boolean p_75062_5_) {
				if (p_75062_1_.nextFloat() < 0.4F) {
					field_151562_a = Blocks.cobblestone.getDefaultState();
				} else {
					field_151562_a = Blocks.mossy_cobblestone.getDefaultState();
				}
			}

			Stones(final ComponentScatteredFeaturePieces.SwitchEnumFacing p_i45583_1_) {
				this();
			}
		}
	}

	public static class SwampHut extends ComponentScatteredFeaturePieces.Feature {
		private boolean hasWitch;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000480";

		public SwampHut() {}

		public SwampHut(final Random p_i2066_1_, final int p_i2066_2_, final int p_i2066_3_) {
			super(p_i2066_1_, p_i2066_2_, 64, p_i2066_3_, 7, 5, 9);
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			super.writeStructureToNBT(p_143012_1_);
			p_143012_1_.setBoolean("Witch", hasWitch);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			super.readStructureFromNBT(p_143011_1_);
			hasWitch = p_143011_1_.getBoolean("Witch");
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (!func_74935_a(worldIn, p_74875_3_, 0)) {
				return false;
			} else {
				func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 5, 1, 7,
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()),
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
				func_175804_a(worldIn, p_74875_3_, 1, 4, 2, 5, 4, 7,
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()),
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
				func_175804_a(worldIn, p_74875_3_, 2, 1, 0, 4, 1, 0,
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()),
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
				func_175804_a(worldIn, p_74875_3_, 2, 2, 2, 3, 3, 2,
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()),
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
				func_175804_a(worldIn, p_74875_3_, 1, 2, 3, 1, 3, 6,
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()),
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
				func_175804_a(worldIn, p_74875_3_, 5, 2, 3, 5, 3, 6,
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()),
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
				func_175804_a(worldIn, p_74875_3_, 2, 2, 7, 4, 3, 7,
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()),
						Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
				func_175804_a(worldIn, p_74875_3_, 1, 0, 2, 1, 3, 2, Blocks.log.getDefaultState(),
						Blocks.log.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 5, 0, 2, 5, 3, 2, Blocks.log.getDefaultState(),
						Blocks.log.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 1, 0, 7, 1, 3, 7, Blocks.log.getDefaultState(),
						Blocks.log.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 5, 0, 7, 5, 3, 7, Blocks.log.getDefaultState(),
						Blocks.log.getDefaultState(), false);
				func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 2, 3, 2, p_74875_3_);
				func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 3, 3, 7, p_74875_3_);
				func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 3, 4, p_74875_3_);
				func_175811_a(worldIn, Blocks.air.getDefaultState(), 5, 3, 4, p_74875_3_);
				func_175811_a(worldIn, Blocks.air.getDefaultState(), 5, 3, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.flower_pot.getDefaultState().withProperty(BlockFlowerPot.field_176443_b,
						BlockFlowerPot.EnumFlowerType.MUSHROOM_RED), 1, 3, 5, p_74875_3_);
				func_175811_a(worldIn, Blocks.crafting_table.getDefaultState(), 3, 2, 6, p_74875_3_);
				func_175811_a(worldIn, Blocks.cauldron.getDefaultState(), 4, 2, 6, p_74875_3_);
				func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 2, 1, p_74875_3_);
				func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 5, 2, 1, p_74875_3_);
				final int var4 = getMetadataWithOffset(Blocks.oak_stairs, 3);
				final int var5 = getMetadataWithOffset(Blocks.oak_stairs, 1);
				final int var6 = getMetadataWithOffset(Blocks.oak_stairs, 0);
				final int var7 = getMetadataWithOffset(Blocks.oak_stairs, 2);
				func_175804_a(worldIn, p_74875_3_, 0, 4, 1, 6, 4, 1, Blocks.spruce_stairs.getStateFromMeta(var4),
						Blocks.spruce_stairs.getStateFromMeta(var4), false);
				func_175804_a(worldIn, p_74875_3_, 0, 4, 2, 0, 4, 7, Blocks.spruce_stairs.getStateFromMeta(var6),
						Blocks.spruce_stairs.getStateFromMeta(var6), false);
				func_175804_a(worldIn, p_74875_3_, 6, 4, 2, 6, 4, 7, Blocks.spruce_stairs.getStateFromMeta(var5),
						Blocks.spruce_stairs.getStateFromMeta(var5), false);
				func_175804_a(worldIn, p_74875_3_, 0, 4, 8, 6, 4, 8, Blocks.spruce_stairs.getStateFromMeta(var7),
						Blocks.spruce_stairs.getStateFromMeta(var7), false);
				int var8;
				int var9;

				for (var8 = 2; var8 <= 7; var8 += 5) {
					for (var9 = 1; var9 <= 5; var9 += 4) {
						func_175808_b(worldIn, Blocks.log.getDefaultState(), var9, -1, var8, p_74875_3_);
					}
				}

				if (!hasWitch) {
					var8 = getXWithOffset(2, 5);
					var9 = getYWithOffset(2);
					final int var10 = getZWithOffset(2, 5);

					if (p_74875_3_.func_175898_b(new BlockPos(var8, var9, var10))) {
						hasWitch = true;
						final EntityWitch var11 = new EntityWitch(worldIn);
						var11.setLocationAndAngles(var8 + 0.5D, var9, var10 + 0.5D, 0.0F, 0.0F);
						var11.func_180482_a(worldIn.getDifficultyForLocation(new BlockPos(var8, var9, var10)),
								(IEntityLivingData) null);
						worldIn.spawnEntityInWorld(var11);
					}
				}

				return true;
			}
		}
	}

	static final class SwitchEnumFacing {
		static final int[] field_175956_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00001971";

		static {
			try {
				field_175956_a[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_175956_a[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
