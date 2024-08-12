package net.minecraft.world.gen.structure;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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

public class StructureMineshaftPieces {

public static final int EaZy = 1818;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final List field_175893_a = Lists.newArrayList(
			new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
					new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
					new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
					new WeightedRandomChestContent(Items.dye, EnumDyeColor.BLUE.getDyeColorDamage(), 4, 9, 5),
					new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3),
					new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10),
					new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
					new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1),
					new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1),
					new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10),
					new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10),
					new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
					new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1) });
	// private static final String __OBFID = "http://https://fuckuskid00000444";

	public static void registerStructurePieces() {
		MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Corridor.class, "MSCorridor");
		MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Cross.class, "MSCrossing");
		MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Room.class, "MSRoom");
		MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Stairs.class, "MSStairs");
	}

	private static StructureComponent func_175892_a(final List p_175892_0_, final Random p_175892_1_,
			final int p_175892_2_, final int p_175892_3_, final int p_175892_4_, final EnumFacing p_175892_5_,
			final int p_175892_6_) {
		final int var7 = p_175892_1_.nextInt(100);
		StructureBoundingBox var8;

		if (var7 >= 80) {
			var8 = StructureMineshaftPieces.Cross.func_175813_a(p_175892_0_, p_175892_1_, p_175892_2_, p_175892_3_,
					p_175892_4_, p_175892_5_);

			if (var8 != null) {
				return new StructureMineshaftPieces.Cross(p_175892_6_, p_175892_1_, var8, p_175892_5_);
			}
		} else if (var7 >= 70) {
			var8 = StructureMineshaftPieces.Stairs.func_175812_a(p_175892_0_, p_175892_1_, p_175892_2_, p_175892_3_,
					p_175892_4_, p_175892_5_);

			if (var8 != null) {
				return new StructureMineshaftPieces.Stairs(p_175892_6_, p_175892_1_, var8, p_175892_5_);
			}
		} else {
			var8 = StructureMineshaftPieces.Corridor.func_175814_a(p_175892_0_, p_175892_1_, p_175892_2_, p_175892_3_,
					p_175892_4_, p_175892_5_);

			if (var8 != null) {
				return new StructureMineshaftPieces.Corridor(p_175892_6_, p_175892_1_, var8, p_175892_5_);
			}
		}

		return null;
	}

	private static StructureComponent func_175890_b(final StructureComponent p_175890_0_, final List p_175890_1_,
			final Random p_175890_2_, final int p_175890_3_, final int p_175890_4_, final int p_175890_5_,
			final EnumFacing p_175890_6_, final int p_175890_7_) {
		if (p_175890_7_ > 8) {
			return null;
		} else if (Math.abs(p_175890_3_ - p_175890_0_.getBoundingBox().minX) <= 80
				&& Math.abs(p_175890_5_ - p_175890_0_.getBoundingBox().minZ) <= 80) {
			final StructureComponent var8 = func_175892_a(p_175890_1_, p_175890_2_, p_175890_3_, p_175890_4_,
					p_175890_5_, p_175890_6_, p_175890_7_ + 1);

			if (var8 != null) {
				p_175890_1_.add(var8);
				var8.buildComponent(p_175890_0_, p_175890_1_, p_175890_2_);
			}

			return var8;
		} else {
			return null;
		}
	}

	public static class Corridor extends StructureComponent {
		private boolean hasRails;
		private boolean hasSpiders;
		private boolean spawnerPlaced;
		private int sectionCount;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000445";

		public Corridor() {}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			p_143012_1_.setBoolean("hr", hasRails);
			p_143012_1_.setBoolean("sc", hasSpiders);
			p_143012_1_.setBoolean("hps", spawnerPlaced);
			p_143012_1_.setInteger("Num", sectionCount);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			hasRails = p_143011_1_.getBoolean("hr");
			hasSpiders = p_143011_1_.getBoolean("sc");
			spawnerPlaced = p_143011_1_.getBoolean("hps");
			sectionCount = p_143011_1_.getInteger("Num");
		}

		public Corridor(final int p_i45625_1_, final Random p_i45625_2_, final StructureBoundingBox p_i45625_3_,
				final EnumFacing p_i45625_4_) {
			super(p_i45625_1_);
			coordBaseMode = p_i45625_4_;
			boundingBox = p_i45625_3_;
			hasRails = p_i45625_2_.nextInt(3) == 0;
			hasSpiders = !hasRails && p_i45625_2_.nextInt(23) == 0;

			if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.SOUTH) {
				sectionCount = p_i45625_3_.getXSize() / 5;
			} else {
				sectionCount = p_i45625_3_.getZSize() / 5;
			}
		}

		public static StructureBoundingBox func_175814_a(final List p_175814_0_, final Random p_175814_1_,
				final int p_175814_2_, final int p_175814_3_, final int p_175814_4_, final EnumFacing p_175814_5_) {
			final StructureBoundingBox var6 = new StructureBoundingBox(p_175814_2_, p_175814_3_, p_175814_4_,
					p_175814_2_, p_175814_3_ + 2, p_175814_4_);
			int var7;

			for (var7 = p_175814_1_.nextInt(3) + 2; var7 > 0; --var7) {
				final int var8 = var7 * 5;

				switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[p_175814_5_.ordinal()]) {
					case 1:
						var6.maxX = p_175814_2_ + 2;
						var6.minZ = p_175814_4_ - (var8 - 1);
						break;

					case 2:
						var6.maxX = p_175814_2_ + 2;
						var6.maxZ = p_175814_4_ + var8 - 1;
						break;

					case 3:
						var6.minX = p_175814_2_ - (var8 - 1);
						var6.maxZ = p_175814_4_ + 2;
						break;

					case 4:
						var6.maxX = p_175814_2_ + var8 - 1;
						var6.maxZ = p_175814_4_ + 2;
				}

				if (StructureComponent.findIntersecting(p_175814_0_, var6) == null) {
					break;
				}
			}

			return var7 > 0 ? var6 : null;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			final int var4 = getComponentType();
			final int var5 = p_74861_3_.nextInt(4);

			if (coordBaseMode != null) {
				switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[coordBaseMode.ordinal()]) {
					case 1:
						if (var5 <= 1) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX,
									boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ - 1, coordBaseMode,
									var4);
						} else if (var5 == 2) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.minX - 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3),
									boundingBox.minZ, EnumFacing.WEST, var4);
						} else {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.maxX + 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3),
									boundingBox.minZ, EnumFacing.EAST, var4);
						}

						break;

					case 2:
						if (var5 <= 1) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX,
									boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.maxZ + 1, coordBaseMode,
									var4);
						} else if (var5 == 2) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.minX - 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3),
									boundingBox.maxZ - 3, EnumFacing.WEST, var4);
						} else {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.maxX + 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3),
									boundingBox.maxZ - 3, EnumFacing.EAST, var4);
						}

						break;

					case 3:
						if (var5 <= 1) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.minX - 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3),
									boundingBox.minZ, coordBaseMode, var4);
						} else if (var5 == 2) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX,
									boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ - 1,
									EnumFacing.NORTH, var4);
						} else {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX,
									boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.maxZ + 1,
									EnumFacing.SOUTH, var4);
						}

						break;

					case 4:
						if (var5 <= 1) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.maxX + 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3),
									boundingBox.minZ, coordBaseMode, var4);
						} else if (var5 == 2) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.maxX - 3, boundingBox.minY - 1 + p_74861_3_.nextInt(3),
									boundingBox.minZ - 1, EnumFacing.NORTH, var4);
						} else {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.maxX - 3, boundingBox.minY - 1 + p_74861_3_.nextInt(3),
									boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
						}
				}
			}

			if (var4 < 8) {
				int var6;
				int var7;

				if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.SOUTH) {
					for (var6 = boundingBox.minX + 3; var6 + 3 <= boundingBox.maxX; var6 += 5) {
						var7 = p_74861_3_.nextInt(5);

						if (var7 == 0) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, var6,
									boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, var4 + 1);
						} else if (var7 == 1) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, var6,
									boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, var4 + 1);
						}
					}
				} else {
					for (var6 = boundingBox.minZ + 3; var6 + 3 <= boundingBox.maxZ; var6 += 5) {
						var7 = p_74861_3_.nextInt(5);

						if (var7 == 0) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.minX - 1, boundingBox.minY, var6, EnumFacing.WEST, var4 + 1);
						} else if (var7 == 1) {
							StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
									boundingBox.maxX + 1, boundingBox.minY, var6, EnumFacing.EAST, var4 + 1);
						}
					}
				}
			}
		}

		@Override
		protected boolean func_180778_a(final World worldIn, final StructureBoundingBox p_180778_2_,
				final Random p_180778_3_, final int p_180778_4_, final int p_180778_5_, final int p_180778_6_,
				final List p_180778_7_, final int p_180778_8_) {
			final BlockPos var9 = new BlockPos(getXWithOffset(p_180778_4_, p_180778_6_), getYWithOffset(p_180778_5_),
					getZWithOffset(p_180778_4_, p_180778_6_));

			if (p_180778_2_.func_175898_b(var9)
					&& worldIn.getBlockState(var9).getBlock().getMaterial() == Material.air) {
				final int var10 = p_180778_3_.nextBoolean() ? 1 : 0;
				worldIn.setBlockState(var9, Blocks.rail.getStateFromMeta(getMetadataWithOffset(Blocks.rail, var10)), 2);
				final EntityMinecartChest var11 = new EntityMinecartChest(worldIn, var9.getX() + 0.5F,
						var9.getY() + 0.5F, var9.getZ() + 0.5F);
				WeightedRandomChestContent.generateChestContents(p_180778_3_, p_180778_7_, var11, p_180778_8_);
				worldIn.spawnEntityInWorld(var11);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				final int var8 = sectionCount * 5 - 1;
				func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 2, 1, var8, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				func_175805_a(worldIn, p_74875_3_, p_74875_2_, 0.8F, 0, 2, 0, 2, 2, var8, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);

				if (hasSpiders) {
					func_175805_a(worldIn, p_74875_3_, p_74875_2_, 0.6F, 0, 0, 0, 2, 1, var8,
							Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false);
				}

				int var9;
				int var10;

				for (var9 = 0; var9 < sectionCount; ++var9) {
					var10 = 2 + var9 * 5;
					func_175804_a(worldIn, p_74875_3_, 0, 0, var10, 0, 1, var10, Blocks.oak_fence.getDefaultState(),
							Blocks.air.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, 2, 0, var10, 2, 1, var10, Blocks.oak_fence.getDefaultState(),
							Blocks.air.getDefaultState(), false);

					if (p_74875_2_.nextInt(4) == 0) {
						func_175804_a(worldIn, p_74875_3_, 0, 2, var10, 0, 2, var10, Blocks.planks.getDefaultState(),
								Blocks.air.getDefaultState(), false);
						func_175804_a(worldIn, p_74875_3_, 2, 2, var10, 2, 2, var10, Blocks.planks.getDefaultState(),
								Blocks.air.getDefaultState(), false);
					} else {
						func_175804_a(worldIn, p_74875_3_, 0, 2, var10, 2, 2, var10, Blocks.planks.getDefaultState(),
								Blocks.air.getDefaultState(), false);
					}

					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1F, 0, 2, var10 - 1, Blocks.web.getDefaultState());
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1F, 2, 2, var10 - 1, Blocks.web.getDefaultState());
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1F, 0, 2, var10 + 1, Blocks.web.getDefaultState());
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1F, 2, 2, var10 + 1, Blocks.web.getDefaultState());
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05F, 0, 2, var10 - 2,
							Blocks.web.getDefaultState());
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05F, 2, 2, var10 - 2,
							Blocks.web.getDefaultState());
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05F, 0, 2, var10 + 2,
							Blocks.web.getDefaultState());
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05F, 2, 2, var10 + 2,
							Blocks.web.getDefaultState());
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05F, 1, 2, var10 - 1,
							Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
					func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.05F, 1, 2, var10 + 1,
							Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));

					if (p_74875_2_.nextInt(100) == 0) {
						func_180778_a(worldIn, p_74875_3_, p_74875_2_, 2, 0, var10 - 1,
								WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.field_175893_a,
										new WeightedRandomChestContent[] {
												Items.enchanted_book.getRandomEnchantedBook(p_74875_2_) }),
								3 + p_74875_2_.nextInt(4));
					}

					if (p_74875_2_.nextInt(100) == 0) {
						func_180778_a(worldIn, p_74875_3_, p_74875_2_, 0, 0, var10 + 1,
								WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.field_175893_a,
										new WeightedRandomChestContent[] {
												Items.enchanted_book.getRandomEnchantedBook(p_74875_2_) }),
								3 + p_74875_2_.nextInt(4));
					}

					if (hasSpiders && !spawnerPlaced) {
						final int var11 = getYWithOffset(0);
						int var12 = var10 - 1 + p_74875_2_.nextInt(3);
						final int var13 = getXWithOffset(1, var12);
						var12 = getZWithOffset(1, var12);
						final BlockPos var14 = new BlockPos(var13, var11, var12);

						if (p_74875_3_.func_175898_b(var14)) {
							spawnerPlaced = true;
							worldIn.setBlockState(var14, Blocks.mob_spawner.getDefaultState(), 2);
							final TileEntity var15 = worldIn.getTileEntity(var14);

							if (var15 instanceof TileEntityMobSpawner) {
								((TileEntityMobSpawner) var15).getSpawnerBaseLogic().setEntityName("CaveSpider");
							}
						}
					}
				}

				for (var9 = 0; var9 <= 2; ++var9) {
					for (var10 = 0; var10 <= var8; ++var10) {
						final byte var17 = -1;
						final IBlockState var18 = func_175807_a(worldIn, var9, var17, var10, p_74875_3_);

						if (var18.getBlock().getMaterial() == Material.air) {
							final byte var19 = -1;
							func_175811_a(worldIn, Blocks.planks.getDefaultState(), var9, var19, var10, p_74875_3_);
						}
					}
				}

				if (hasRails) {
					for (var9 = 0; var9 <= var8; ++var9) {
						final IBlockState var16 = func_175807_a(worldIn, 1, -1, var9, p_74875_3_);

						if (var16.getBlock().getMaterial() != Material.air && var16.getBlock().isFullBlock()) {
							func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.7F, 1, 0, var9,
									Blocks.rail.getStateFromMeta(getMetadataWithOffset(Blocks.rail, 0)));
						}
					}
				}

				return true;
			}
		}
	}

	public static class Cross extends StructureComponent {
		private EnumFacing corridorDirection;
		private boolean isMultipleFloors;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000446";

		public Cross() {}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			p_143012_1_.setBoolean("tf", isMultipleFloors);
			p_143012_1_.setInteger("D", corridorDirection.getHorizontalIndex());
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			isMultipleFloors = p_143011_1_.getBoolean("tf");
			corridorDirection = EnumFacing.getHorizontal(p_143011_1_.getInteger("D"));
		}

		public Cross(final int p_i45624_1_, final Random p_i45624_2_, final StructureBoundingBox p_i45624_3_,
				final EnumFacing p_i45624_4_) {
			super(p_i45624_1_);
			corridorDirection = p_i45624_4_;
			boundingBox = p_i45624_3_;
			isMultipleFloors = p_i45624_3_.getYSize() > 3;
		}

		public static StructureBoundingBox func_175813_a(final List p_175813_0_, final Random p_175813_1_,
				final int p_175813_2_, final int p_175813_3_, final int p_175813_4_, final EnumFacing p_175813_5_) {
			final StructureBoundingBox var6 = new StructureBoundingBox(p_175813_2_, p_175813_3_, p_175813_4_,
					p_175813_2_, p_175813_3_ + 2, p_175813_4_);

			if (p_175813_1_.nextInt(4) == 0) {
				var6.maxY += 4;
			}

			switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[p_175813_5_.ordinal()]) {
				case 1:
					var6.minX = p_175813_2_ - 1;
					var6.maxX = p_175813_2_ + 3;
					var6.minZ = p_175813_4_ - 4;
					break;

				case 2:
					var6.minX = p_175813_2_ - 1;
					var6.maxX = p_175813_2_ + 3;
					var6.maxZ = p_175813_4_ + 4;
					break;

				case 3:
					var6.minX = p_175813_2_ - 4;
					var6.minZ = p_175813_4_ - 1;
					var6.maxZ = p_175813_4_ + 3;
					break;

				case 4:
					var6.maxX = p_175813_2_ + 4;
					var6.minZ = p_175813_4_ - 1;
					var6.maxZ = p_175813_4_ + 3;
			}

			return StructureComponent.findIntersecting(p_175813_0_, var6) != null ? null : var6;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			final int var4 = getComponentType();

			switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[corridorDirection.ordinal()]) {
				case 1:
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1,
							boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, var4);
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1,
							boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, var4);
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1,
							boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, var4);
					break;

				case 2:
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1,
							boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1,
							boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, var4);
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1,
							boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, var4);
					break;

				case 3:
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1,
							boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, var4);
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1,
							boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1,
							boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, var4);
					break;

				case 4:
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1,
							boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, var4);
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1,
							boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1,
							boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, var4);
			}

			if (isMultipleFloors) {
				if (p_74861_3_.nextBoolean()) {
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1,
							boundingBox.minY + 3 + 1, boundingBox.minZ - 1, EnumFacing.NORTH, var4);
				}

				if (p_74861_3_.nextBoolean()) {
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1,
							boundingBox.minY + 3 + 1, boundingBox.minZ + 1, EnumFacing.WEST, var4);
				}

				if (p_74861_3_.nextBoolean()) {
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1,
							boundingBox.minY + 3 + 1, boundingBox.minZ + 1, EnumFacing.EAST, var4);
				}

				if (p_74861_3_.nextBoolean()) {
					StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1,
							boundingBox.minY + 3 + 1, boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
				}
			}
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				if (isMultipleFloors) {
					func_175804_a(worldIn, p_74875_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ,
							boundingBox.maxX - 1, boundingBox.minY + 3 - 1, boundingBox.maxZ,
							Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1,
							boundingBox.maxX, boundingBox.minY + 3 - 1, boundingBox.maxZ - 1,
							Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, boundingBox.minX + 1, boundingBox.maxY - 2, boundingBox.minZ,
							boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, boundingBox.minX, boundingBox.maxY - 2, boundingBox.minZ + 1,
							boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, boundingBox.minX + 1, boundingBox.minY + 3, boundingBox.minZ + 1,
							boundingBox.maxX - 1, boundingBox.minY + 3, boundingBox.maxZ - 1,
							Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
				} else {
					func_175804_a(worldIn, p_74875_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ,
							boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
					func_175804_a(worldIn, p_74875_3_, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1,
							boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.air.getDefaultState(),
							Blocks.air.getDefaultState(), false);
				}

				func_175804_a(worldIn, p_74875_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1,
						boundingBox.minX + 1, boundingBox.maxY, boundingBox.minZ + 1, Blocks.planks.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ - 1,
						boundingBox.minX + 1, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.planks.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, boundingBox.maxX - 1, boundingBox.minY, boundingBox.minZ + 1,
						boundingBox.maxX - 1, boundingBox.maxY, boundingBox.minZ + 1, Blocks.planks.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, boundingBox.maxX - 1, boundingBox.minY, boundingBox.maxZ - 1,
						boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.planks.getDefaultState(),
						Blocks.air.getDefaultState(), false);

				for (int var4 = boundingBox.minX; var4 <= boundingBox.maxX; ++var4) {
					for (int var5 = boundingBox.minZ; var5 <= boundingBox.maxZ; ++var5) {
						if (func_175807_a(worldIn, var4, boundingBox.minY - 1, var5, p_74875_3_).getBlock()
								.getMaterial() == Material.air) {
							func_175811_a(worldIn, Blocks.planks.getDefaultState(), var4, boundingBox.minY - 1, var5,
									p_74875_3_);
						}
					}
				}

				return true;
			}
		}
	}

	public static class Room extends StructureComponent {
		private final List roomsLinkedToTheRoom = Lists.newLinkedList();
		// private static final String __OBFID =
		// "http://https://fuckuskid00000447";

		public Room() {}

		public Room(final int p_i2037_1_, final Random p_i2037_2_, final int p_i2037_3_, final int p_i2037_4_) {
			super(p_i2037_1_);
			boundingBox = new StructureBoundingBox(p_i2037_3_, 50, p_i2037_4_, p_i2037_3_ + 7 + p_i2037_2_.nextInt(6),
					54 + p_i2037_2_.nextInt(6), p_i2037_4_ + 7 + p_i2037_2_.nextInt(6));
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			final int var4 = getComponentType();
			int var6 = boundingBox.getYSize() - 3 - 1;

			if (var6 <= 0) {
				var6 = 1;
			}

			int var5;
			StructureComponent var7;
			StructureBoundingBox var8;

			for (var5 = 0; var5 < boundingBox.getXSize(); var5 += 4) {
				var5 += p_74861_3_.nextInt(boundingBox.getXSize());

				if (var5 + 3 > boundingBox.getXSize()) {
					break;
				}

				var7 = StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
						boundingBox.minX + var5, boundingBox.minY + p_74861_3_.nextInt(var6) + 1, boundingBox.minZ - 1,
						EnumFacing.NORTH, var4);

				if (var7 != null) {
					var8 = var7.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, boundingBox.minZ, var8.maxX,
							var8.maxY, boundingBox.minZ + 1));
				}
			}

			for (var5 = 0; var5 < boundingBox.getXSize(); var5 += 4) {
				var5 += p_74861_3_.nextInt(boundingBox.getXSize());

				if (var5 + 3 > boundingBox.getXSize()) {
					break;
				}

				var7 = StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_,
						boundingBox.minX + var5, boundingBox.minY + p_74861_3_.nextInt(var6) + 1, boundingBox.maxZ + 1,
						EnumFacing.SOUTH, var4);

				if (var7 != null) {
					var8 = var7.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, boundingBox.maxZ - 1,
							var8.maxX, var8.maxY, boundingBox.maxZ));
				}
			}

			for (var5 = 0; var5 < boundingBox.getZSize(); var5 += 4) {
				var5 += p_74861_3_.nextInt(boundingBox.getZSize());

				if (var5 + 3 > boundingBox.getZSize()) {
					break;
				}

				var7 = StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1,
						boundingBox.minY + p_74861_3_.nextInt(var6) + 1, boundingBox.minZ + var5, EnumFacing.WEST,
						var4);

				if (var7 != null) {
					var8 = var7.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.minX, var8.minY, var8.minZ,
							boundingBox.minX + 1, var8.maxY, var8.maxZ));
				}
			}

			for (var5 = 0; var5 < boundingBox.getZSize(); var5 += 4) {
				var5 += p_74861_3_.nextInt(boundingBox.getZSize());

				if (var5 + 3 > boundingBox.getZSize()) {
					break;
				}

				var7 = StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1,
						boundingBox.minY + p_74861_3_.nextInt(var6) + 1, boundingBox.minZ + var5, EnumFacing.EAST,
						var4);

				if (var7 != null) {
					var8 = var7.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.maxX - 1, var8.minY, var8.minZ,
							boundingBox.maxX, var8.maxY, var8.maxZ));
				}
			}
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				func_175804_a(worldIn, p_74875_3_, boundingBox.minX, boundingBox.minY, boundingBox.minZ,
						boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, Blocks.dirt.getDefaultState(),
						Blocks.air.getDefaultState(), true);
				func_175804_a(worldIn, p_74875_3_, boundingBox.minX, boundingBox.minY + 1, boundingBox.minZ,
						boundingBox.maxX, Math.min(boundingBox.minY + 3, boundingBox.maxY), boundingBox.maxZ,
						Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
				final Iterator var4 = roomsLinkedToTheRoom.iterator();

				while (var4.hasNext()) {
					final StructureBoundingBox var5 = (StructureBoundingBox) var4.next();
					func_175804_a(worldIn, p_74875_3_, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY,
							var5.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
				}

				func_180777_a(worldIn, p_74875_3_, boundingBox.minX, boundingBox.minY + 4, boundingBox.minZ,
						boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, Blocks.air.getDefaultState(), false);
				return true;
			}
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
			final NBTTagList var2 = new NBTTagList();
			final Iterator var3 = roomsLinkedToTheRoom.iterator();

			while (var3.hasNext()) {
				final StructureBoundingBox var4 = (StructureBoundingBox) var3.next();
				var2.appendTag(var4.func_151535_h());
			}

			p_143012_1_.setTag("Entrances", var2);
		}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
			final NBTTagList var2 = p_143011_1_.getTagList("Entrances", 11);

			for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
				roomsLinkedToTheRoom.add(new StructureBoundingBox(var2.getIntArray(var3)));
			}
		}
	}

	public static class Stairs extends StructureComponent {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000449";

		public Stairs() {}

		public Stairs(final int p_i45623_1_, final Random p_i45623_2_, final StructureBoundingBox p_i45623_3_,
				final EnumFacing p_i45623_4_) {
			super(p_i45623_1_);
			coordBaseMode = p_i45623_4_;
			boundingBox = p_i45623_3_;
		}

		@Override
		protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {}

		@Override
		protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {}

		public static StructureBoundingBox func_175812_a(final List p_175812_0_, final Random p_175812_1_,
				final int p_175812_2_, final int p_175812_3_, final int p_175812_4_, final EnumFacing p_175812_5_) {
			final StructureBoundingBox var6 = new StructureBoundingBox(p_175812_2_, p_175812_3_ - 5, p_175812_4_,
					p_175812_2_, p_175812_3_ + 2, p_175812_4_);

			switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[p_175812_5_.ordinal()]) {
				case 1:
					var6.maxX = p_175812_2_ + 2;
					var6.minZ = p_175812_4_ - 8;
					break;

				case 2:
					var6.maxX = p_175812_2_ + 2;
					var6.maxZ = p_175812_4_ + 8;
					break;

				case 3:
					var6.minX = p_175812_2_ - 8;
					var6.maxZ = p_175812_4_ + 2;
					break;

				case 4:
					var6.maxX = p_175812_2_ + 8;
					var6.maxZ = p_175812_4_ + 2;
			}

			return StructureComponent.findIntersecting(p_175812_0_, var6) != null ? null : var6;
		}

		@Override
		public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_,
				final Random p_74861_3_) {
			final int var4 = getComponentType();

			if (coordBaseMode != null) {
				switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[coordBaseMode.ordinal()]) {
					case 1:
						StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX,
								boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, var4);
						break;

					case 2:
						StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX,
								boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
						break;

					case 3:
						StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1,
								boundingBox.minY, boundingBox.minZ, EnumFacing.WEST, var4);
						break;

					case 4:
						StructureMineshaftPieces.func_175890_b(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1,
								boundingBox.minY, boundingBox.minZ, EnumFacing.EAST, var4);
				}
			}
		}

		@Override
		public boolean addComponentParts(final World worldIn, final Random p_74875_2_,
				final StructureBoundingBox p_74875_3_) {
			if (isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
				return false;
			} else {
				func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);
				func_175804_a(worldIn, p_74875_3_, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(),
						Blocks.air.getDefaultState(), false);

				for (int var4 = 0; var4 < 5; ++var4) {
					func_175804_a(worldIn, p_74875_3_, 0, 5 - var4 - (var4 < 4 ? 1 : 0), 2 + var4, 2, 7 - var4,
							2 + var4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
				}

				return true;
			}
		}
	}

	static final class SwitchEnumFacing {
		static final int[] field_175894_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00001998";

		static {
			try {
				field_175894_a[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_175894_a[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_175894_a[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_175894_a[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
