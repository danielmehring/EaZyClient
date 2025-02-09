package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor {

public static final int EaZy = 1836;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private boolean field_176180_f;
	private boolean field_176181_g;
	private boolean field_176183_h;
	private boolean field_176184_i;
	private boolean field_176182_j;
	// private static final String __OBFID = "http://https://fuckuskid00001965";

	@Override
	public void func_176162_a(final IBlockAccess p_176162_1_, final Entity p_176162_2_) {
		super.func_176162_a(p_176162_1_, p_176162_2_);
		field_176182_j = field_176183_h;
	}

	@Override
	public void func_176163_a() {
		super.func_176163_a();
		field_176183_h = field_176182_j;
	}

	@Override
	public PathPoint func_176161_a(final Entity p_176161_1_) {
		int var2;

		if (field_176184_i && p_176161_1_.isInWater()) {
			var2 = (int) p_176161_1_.getEntityBoundingBox().minY;

			for (Block var3 = field_176169_a.getBlockState(new BlockPos(MathHelper.floor_double(p_176161_1_.posX), var2,
					MathHelper.floor_double(p_176161_1_.posZ))).getBlock(); var3 == Blocks.flowing_water
							|| var3 == Blocks.water; var3 = field_176169_a
									.getBlockState(new BlockPos(MathHelper.floor_double(p_176161_1_.posX), var2,
											MathHelper.floor_double(p_176161_1_.posZ)))
									.getBlock()) {
				++var2;
			}

			field_176183_h = false;
		} else {
			var2 = MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minY + 0.5D);
		}

		return func_176159_a(MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minX), var2,
				MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minZ));
	}

	@Override
	public PathPoint func_176160_a(final Entity p_176160_1_, final double p_176160_2_, final double p_176160_4_,
			final double p_176160_6_) {
		return func_176159_a(MathHelper.floor_double(p_176160_2_ - p_176160_1_.width / 2.0F),
				MathHelper.floor_double(p_176160_4_), MathHelper.floor_double(p_176160_6_ - p_176160_1_.width / 2.0F));
	}

	@Override
	public int func_176164_a(final PathPoint[] p_176164_1_, final Entity p_176164_2_, final PathPoint p_176164_3_,
			final PathPoint p_176164_4_, final float p_176164_5_) {
		int var6 = 0;
		byte var7 = 0;

		if (func_176177_a(p_176164_2_, p_176164_3_.xCoord, p_176164_3_.yCoord + 1, p_176164_3_.zCoord) == 1) {
			var7 = 1;
		}

		final PathPoint var8 = func_176171_a(p_176164_2_, p_176164_3_.xCoord, p_176164_3_.yCoord,
				p_176164_3_.zCoord + 1, var7);
		final PathPoint var9 = func_176171_a(p_176164_2_, p_176164_3_.xCoord - 1, p_176164_3_.yCoord,
				p_176164_3_.zCoord, var7);
		final PathPoint var10 = func_176171_a(p_176164_2_, p_176164_3_.xCoord + 1, p_176164_3_.yCoord,
				p_176164_3_.zCoord, var7);
		final PathPoint var11 = func_176171_a(p_176164_2_, p_176164_3_.xCoord, p_176164_3_.yCoord,
				p_176164_3_.zCoord - 1, var7);

		if (var8 != null && !var8.visited && var8.distanceTo(p_176164_4_) < p_176164_5_) {
			p_176164_1_[var6++] = var8;
		}

		if (var9 != null && !var9.visited && var9.distanceTo(p_176164_4_) < p_176164_5_) {
			p_176164_1_[var6++] = var9;
		}

		if (var10 != null && !var10.visited && var10.distanceTo(p_176164_4_) < p_176164_5_) {
			p_176164_1_[var6++] = var10;
		}

		if (var11 != null && !var11.visited && var11.distanceTo(p_176164_4_) < p_176164_5_) {
			p_176164_1_[var6++] = var11;
		}

		return var6;
	}

	private PathPoint func_176171_a(final Entity p_176171_1_, final int p_176171_2_, int p_176171_3_,
			final int p_176171_4_, final int p_176171_5_) {
		PathPoint var6 = null;
		final int var7 = func_176177_a(p_176171_1_, p_176171_2_, p_176171_3_, p_176171_4_);

		if (var7 == 2) {
			return func_176159_a(p_176171_2_, p_176171_3_, p_176171_4_);
		} else {
			if (var7 == 1) {
				var6 = func_176159_a(p_176171_2_, p_176171_3_, p_176171_4_);
			}

			if (var6 == null && p_176171_5_ > 0 && var7 != -3 && var7 != -4
					&& func_176177_a(p_176171_1_, p_176171_2_, p_176171_3_ + p_176171_5_, p_176171_4_) == 1) {
				var6 = func_176159_a(p_176171_2_, p_176171_3_ + p_176171_5_, p_176171_4_);
				p_176171_3_ += p_176171_5_;
			}

			if (var6 != null) {
				int var8 = 0;
				int var9;

				for (var9 = 0; p_176171_3_ > 0; var6 = func_176159_a(p_176171_2_, p_176171_3_, p_176171_4_)) {
					var9 = func_176177_a(p_176171_1_, p_176171_2_, p_176171_3_ - 1, p_176171_4_);

					if (field_176183_h && var9 == -1) {
						return null;
					}

					if (var9 != 1) {
						break;
					}

					if (var8++ >= p_176171_1_.getMaxFallHeight()) {
						return null;
					}

					--p_176171_3_;

					if (p_176171_3_ <= 0) {
						return null;
					}
				}

				if (var9 == -2) {
					return null;
				}
			}

			return var6;
		}
	}

	private int func_176177_a(final Entity p_176177_1_, final int p_176177_2_, final int p_176177_3_,
			final int p_176177_4_) {
		return func_176170_a(field_176169_a, p_176177_1_, p_176177_2_, p_176177_3_, p_176177_4_, field_176168_c,
				field_176165_d, field_176166_e, field_176183_h, field_176181_g, field_176180_f);
	}

	public static int func_176170_a(final IBlockAccess p_176170_0_, final Entity p_176170_1_, final int p_176170_2_,
			final int p_176170_3_, final int p_176170_4_, final int p_176170_5_, final int p_176170_6_,
			final int p_176170_7_, final boolean p_176170_8_, final boolean p_176170_9_, final boolean p_176170_10_) {
		boolean var11 = false;
		final BlockPos var12 = new BlockPos(p_176170_1_);

		for (int var13 = p_176170_2_; var13 < p_176170_2_ + p_176170_5_; ++var13) {
			for (int var14 = p_176170_3_; var14 < p_176170_3_ + p_176170_6_; ++var14) {
				for (int var15 = p_176170_4_; var15 < p_176170_4_ + p_176170_7_; ++var15) {
					final BlockPos var16 = new BlockPos(var13, var14, var15);
					final Block var17 = p_176170_0_.getBlockState(var16).getBlock();

					if (var17.getMaterial() != Material.air) {
						if (var17 != Blocks.trapdoor && var17 != Blocks.iron_trapdoor) {
							if (var17 != Blocks.flowing_water && var17 != Blocks.water) {
								if (!p_176170_10_ && var17 instanceof BlockDoor
										&& var17.getMaterial() == Material.wood) {
									return 0;
								}
							} else {
								if (p_176170_8_) {
									return -1;
								}

								var11 = true;
							}
						} else {
							var11 = true;
						}

						if (p_176170_1_.worldObj.getBlockState(var16).getBlock() instanceof BlockRailBase) {
							if (!(p_176170_1_.worldObj.getBlockState(var12).getBlock() instanceof BlockRailBase)
									&& !(p_176170_1_.worldObj.getBlockState(var12.offsetDown())
											.getBlock() instanceof BlockRailBase)) {
								return -3;
							}
						} else if (!var17.isPassable(p_176170_0_, var16) && (!p_176170_9_
								|| !(var17 instanceof BlockDoor) || var17.getMaterial() != Material.wood)) {
							if (var17 instanceof BlockFence || var17 instanceof BlockFenceGate
									|| var17 instanceof BlockWall) {
								return -3;
							}

							if (var17 == Blocks.trapdoor || var17 == Blocks.iron_trapdoor) {
								return -4;
							}

							final Material var18 = var17.getMaterial();

							if (var18 != Material.lava) {
								return 0;
							}

							if (!p_176170_1_.func_180799_ab()) {
								return -2;
							}
						}
					}
				}
			}
		}

		return var11 ? 2 : 1;
	}

	public void func_176175_a(final boolean p_176175_1_) {
		field_176180_f = p_176175_1_;
	}

	public void func_176172_b(final boolean p_176172_1_) {
		field_176181_g = p_176172_1_;
	}

	public void func_176176_c(final boolean p_176176_1_) {
		field_176183_h = p_176176_1_;
	}

	public void func_176178_d(final boolean p_176178_1_) {
		field_176184_i = p_176178_1_;
	}

	public boolean func_176179_b() {
		return field_176180_f;
	}

	public boolean func_176174_d() {
		return field_176184_i;
	}

	public boolean func_176173_e() {
		return field_176183_h;
	}
}
