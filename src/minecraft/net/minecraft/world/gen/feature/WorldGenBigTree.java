package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class WorldGenBigTree extends WorldGenAbstractTree {

public static final int EaZy = 1734;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private Random field_175949_k;
	private World field_175946_l;
	private BlockPos field_175947_m;
	int heightLimit;
	int height;
	double heightAttenuation;
	double field_175944_d;
	double field_175945_e;
	double leafDensity;
	int field_175943_g;
	int field_175950_h;

	/**
	 * Sets the distance limit for how far away the generator will populate
	 * leaves from the base leaf node.
	 */
	int leafDistanceLimit;
	List field_175948_j;
	// private static final String __OBFID = "http://https://fuckuskid00000400";

	public WorldGenBigTree(final boolean p_i2008_1_) {
		super(p_i2008_1_);
		field_175947_m = BlockPos.ORIGIN;
		heightAttenuation = 0.618D;
		field_175944_d = 0.381D;
		field_175945_e = 1.0D;
		leafDensity = 1.0D;
		field_175943_g = 1;
		field_175950_h = 12;
		leafDistanceLimit = 4;
	}

	/**
	 * Generates a list of leaf nodes for the tree, to be populated by
	 * generateLeaves.
	 */
	void generateLeafNodeList() {
		height = (int) (heightLimit * heightAttenuation);

		if (height >= heightLimit) {
			height = heightLimit - 1;
		}

		int var1 = (int) (1.382D + Math.pow(leafDensity * heightLimit / 13.0D, 2.0D));

		if (var1 < 1) {
			var1 = 1;
		}

		final int var2 = field_175947_m.getY() + height;
		int var3 = heightLimit - leafDistanceLimit;
		field_175948_j = Lists.newArrayList();
		field_175948_j.add(new WorldGenBigTree.FoliageCoordinates(field_175947_m.offsetUp(var3), var2));

		for (; var3 >= 0; --var3) {
			final float var4 = layerSize(var3);

			if (var4 >= 0.0F) {
				for (int var5 = 0; var5 < var1; ++var5) {
					final double var6 = field_175945_e * var4 * (field_175949_k.nextFloat() + 0.328D);
					final double var8 = field_175949_k.nextFloat() * 2.0F * Math.PI;
					final double var10 = var6 * Math.sin(var8) + 0.5D;
					final double var12 = var6 * Math.cos(var8) + 0.5D;
					final BlockPos var14 = field_175947_m.add(var10, var3 - 1, var12);
					final BlockPos var15 = var14.offsetUp(leafDistanceLimit);

					if (func_175936_a(var14, var15) == -1) {
						final int var16 = field_175947_m.getX() - var14.getX();
						final int var17 = field_175947_m.getZ() - var14.getZ();
						final double var18 = var14.getY() - Math.sqrt(var16 * var16 + var17 * var17) * field_175944_d;
						final int var20 = var18 > var2 ? var2 : (int) var18;
						final BlockPos var21 = new BlockPos(field_175947_m.getX(), var20, field_175947_m.getZ());

						if (func_175936_a(var21, var14) == -1) {
							field_175948_j.add(new WorldGenBigTree.FoliageCoordinates(var14, var21.getY()));
						}
					}
				}
			}
		}
	}

	void func_180712_a(final BlockPos p_180712_1_, final float p_180712_2_, final Block p_180712_3_) {
		final int var4 = (int) (p_180712_2_ + 0.618D);

		for (int var5 = -var4; var5 <= var4; ++var5) {
			for (int var6 = -var4; var6 <= var4; ++var6) {
				if (Math.pow(Math.abs(var5) + 0.5D, 2.0D) + Math.pow(Math.abs(var6) + 0.5D, 2.0D) <= p_180712_2_
						* p_180712_2_) {
					final BlockPos var7 = p_180712_1_.add(var5, 0, var6);
					final Material var8 = field_175946_l.getBlockState(var7).getBlock().getMaterial();

					if (var8 == Material.air || var8 == Material.leaves) {
						func_175905_a(field_175946_l, var7, p_180712_3_, 0);
					}
				}
			}
		}
	}

	/**
	 * Gets the rough size of a layer of the tree.
	 */
	float layerSize(final int p_76490_1_) {
		if (p_76490_1_ < heightLimit * 0.3F) {
			return -1.0F;
		} else {
			final float var2 = heightLimit / 2.0F;
			final float var3 = var2 - p_76490_1_;
			float var4 = MathHelper.sqrt_float(var2 * var2 - var3 * var3);

			if (var3 == 0.0F) {
				var4 = var2;
			} else if (Math.abs(var3) >= var2) {
				return 0.0F;
			}

			return var4 * 0.5F;
		}
	}

	float leafSize(final int p_76495_1_) {
		return p_76495_1_ >= 0 && p_76495_1_ < leafDistanceLimit
				? p_76495_1_ != 0 && p_76495_1_ != leafDistanceLimit - 1 ? 3.0F : 2.0F : -1.0F;
	}

	void func_175940_a(final BlockPos p_175940_1_) {
		for (int var2 = 0; var2 < leafDistanceLimit; ++var2) {
			func_180712_a(p_175940_1_.offsetUp(var2), leafSize(var2), Blocks.leaves);
		}
	}

	void func_175937_a(final BlockPos p_175937_1_, final BlockPos p_175937_2_, final Block p_175937_3_) {
		final BlockPos var4 = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
		final int var5 = func_175935_b(var4);
		final float var6 = (float) var4.getX() / (float) var5;
		final float var7 = (float) var4.getY() / (float) var5;
		final float var8 = (float) var4.getZ() / (float) var5;

		for (int var9 = 0; var9 <= var5; ++var9) {
			final BlockPos var10 = p_175937_1_.add(0.5F + var9 * var6, 0.5F + var9 * var7, 0.5F + var9 * var8);
			final BlockLog.EnumAxis var11 = func_175938_b(p_175937_1_, var10);
			func_175903_a(field_175946_l, var10, p_175937_3_.getDefaultState().withProperty(BlockLog.AXIS_PROP, var11));
		}
	}

	private int func_175935_b(final BlockPos p_175935_1_) {
		final int var2 = MathHelper.abs_int(p_175935_1_.getX());
		final int var3 = MathHelper.abs_int(p_175935_1_.getY());
		final int var4 = MathHelper.abs_int(p_175935_1_.getZ());
		return var4 > var2 && var4 > var3 ? var4 : var3 > var2 ? var3 : var2;
	}

	private BlockLog.EnumAxis func_175938_b(final BlockPos p_175938_1_, final BlockPos p_175938_2_) {
		BlockLog.EnumAxis var3 = BlockLog.EnumAxis.Y;
		final int var4 = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
		final int var5 = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
		final int var6 = Math.max(var4, var5);

		if (var6 > 0) {
			if (var4 == var6) {
				var3 = BlockLog.EnumAxis.X;
			} else if (var5 == var6) {
				var3 = BlockLog.EnumAxis.Z;
			}
		}

		return var3;
	}

	void func_175941_b() {
		final Iterator var1 = field_175948_j.iterator();

		while (var1.hasNext()) {
			final WorldGenBigTree.FoliageCoordinates var2 = (WorldGenBigTree.FoliageCoordinates) var1.next();
			func_175940_a(var2);
		}
	}

	/**
	 * Indicates whether or not a leaf node requires additional wood to be added
	 * to preserve integrity.
	 */
	boolean leafNodeNeedsBase(final int p_76493_1_) {
		return p_76493_1_ >= heightLimit * 0.2D;
	}

	void func_175942_c() {
		final BlockPos var1 = field_175947_m;
		final BlockPos var2 = field_175947_m.offsetUp(height);
		final Block var3 = Blocks.log;
		func_175937_a(var1, var2, var3);

		if (field_175943_g == 2) {
			func_175937_a(var1.offsetEast(), var2.offsetEast(), var3);
			func_175937_a(var1.offsetEast().offsetSouth(), var2.offsetEast().offsetSouth(), var3);
			func_175937_a(var1.offsetSouth(), var2.offsetSouth(), var3);
		}
	}

	void func_175939_d() {
		final Iterator var1 = field_175948_j.iterator();

		while (var1.hasNext()) {
			final WorldGenBigTree.FoliageCoordinates var2 = (WorldGenBigTree.FoliageCoordinates) var1.next();
			final int var3 = var2.func_177999_q();
			final BlockPos var4 = new BlockPos(field_175947_m.getX(), var3, field_175947_m.getZ());

			if (leafNodeNeedsBase(var3 - field_175947_m.getY())) {
				func_175937_a(var4, var2, Blocks.log);
			}
		}
	}

	int func_175936_a(final BlockPos p_175936_1_, final BlockPos p_175936_2_) {
		final BlockPos var3 = p_175936_2_.add(-p_175936_1_.getX(), -p_175936_1_.getY(), -p_175936_1_.getZ());
		final int var4 = func_175935_b(var3);
		final float var5 = (float) var3.getX() / (float) var4;
		final float var6 = (float) var3.getY() / (float) var4;
		final float var7 = (float) var3.getZ() / (float) var4;

		if (var4 == 0) {
			return -1;
		} else {
			for (int var8 = 0; var8 <= var4; ++var8) {
				final BlockPos var9 = p_175936_1_.add(0.5F + var8 * var5, 0.5F + var8 * var6, 0.5F + var8 * var7);

				if (!func_150523_a(field_175946_l.getBlockState(var9).getBlock())) {
					return var8;
				}
			}

			return -1;
		}
	}

	@Override
	public void func_175904_e() {
		leafDistanceLimit = 5;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		field_175946_l = worldIn;
		field_175947_m = p_180709_3_;
		field_175949_k = new Random(p_180709_2_.nextLong());

		if (heightLimit == 0) {
			heightLimit = 5 + field_175949_k.nextInt(field_175950_h);
		}

		if (!validTreeLocation()) {
			return false;
		} else {
			generateLeafNodeList();
			func_175941_b();
			func_175942_c();
			func_175939_d();
			return true;
		}
	}

	/**
	 * Returns a boolean indicating whether or not the current location for the
	 * tree, spanning basePos to to the height limit, is valid.
	 */
	private boolean validTreeLocation() {
		final Block var1 = field_175946_l.getBlockState(field_175947_m.offsetDown()).getBlock();

		if (var1 != Blocks.dirt && var1 != Blocks.grass && var1 != Blocks.farmland) {
			return false;
		} else {
			final int var2 = func_175936_a(field_175947_m, field_175947_m.offsetUp(heightLimit - 1));

			if (var2 == -1) {
				return true;
			} else if (var2 < 6) {
				return false;
			} else {
				heightLimit = var2;
				return true;
			}
		}
	}

	static class FoliageCoordinates extends BlockPos {
		private final int field_178000_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002001";

		public FoliageCoordinates(final BlockPos p_i45635_1_, final int p_i45635_2_) {
			super(p_i45635_1_.getX(), p_i45635_1_.getY(), p_i45635_1_.getZ());
			field_178000_b = p_i45635_2_;
		}

		public int func_177999_q() {
			return field_178000_b;
		}
	}
}
