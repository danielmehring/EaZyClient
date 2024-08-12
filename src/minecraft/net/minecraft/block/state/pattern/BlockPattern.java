package net.minecraft.block.state.pattern;

import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

import java.util.Iterator;

import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

public class BlockPattern {

public static final int EaZy = 427;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Predicate[][][] field_177689_a;
	private final int field_177687_b;
	private final int field_177688_c;
	private final int field_177686_d;
	// private static final String __OBFID = "http://https://fuckuskid00002024";

	public BlockPattern(final Predicate[][][] p_i45657_1_) {
		field_177689_a = p_i45657_1_;
		field_177687_b = p_i45657_1_.length;

		if (field_177687_b > 0) {
			field_177688_c = p_i45657_1_[0].length;

			if (field_177688_c > 0) {
				field_177686_d = p_i45657_1_[0][0].length;
			} else {
				field_177686_d = 0;
			}
		} else {
			field_177688_c = 0;
			field_177686_d = 0;
		}
	}

	public int func_177685_b() {
		return field_177688_c;
	}

	public int func_177684_c() {
		return field_177686_d;
	}

	private BlockPattern.PatternHelper func_177682_a(final BlockPos p_177682_1_, final EnumFacing p_177682_2_,
			final EnumFacing p_177682_3_, final LoadingCache p_177682_4_) {
		for (int var5 = 0; var5 < field_177686_d; ++var5) {
			for (int var6 = 0; var6 < field_177688_c; ++var6) {
				for (int var7 = 0; var7 < field_177687_b; ++var7) {
					if (!field_177689_a[var7][var6][var5].apply(p_177682_4_
							.getUnchecked(func_177683_a(p_177682_1_, p_177682_2_, p_177682_3_, var5, var6, var7)))) {
						return null;
					}
				}
			}
		}

		return new BlockPattern.PatternHelper(p_177682_1_, p_177682_2_, p_177682_3_, p_177682_4_);
	}

	public BlockPattern.PatternHelper func_177681_a(final World worldIn, final BlockPos p_177681_2_) {
		final LoadingCache var3 = CacheBuilder.newBuilder().build(new BlockPattern.CacheLoader(worldIn));
		final int var4 = Math.max(Math.max(field_177686_d, field_177688_c), field_177687_b);
		final Iterator var5 = BlockPos.getAllInBox(p_177681_2_, p_177681_2_.add(var4 - 1, var4 - 1, var4 - 1))
				.iterator();

		while (var5.hasNext()) {
			final BlockPos var6 = (BlockPos) var5.next();
			final EnumFacing[] var7 = EnumFacing.values();
			final int var8 = var7.length;

			for (int var9 = 0; var9 < var8; ++var9) {
				final EnumFacing var10 = var7[var9];
				final EnumFacing[] var11 = EnumFacing.values();
				final int var12 = var11.length;

				for (int var13 = 0; var13 < var12; ++var13) {
					final EnumFacing var14 = var11[var13];

					if (var14 != var10 && var14 != var10.getOpposite()) {
						final BlockPattern.PatternHelper var15 = func_177682_a(var6, var10, var14, var3);

						if (var15 != null) {
							return var15;
						}
					}
				}
			}
		}

		return null;
	}

	protected static BlockPos func_177683_a(final BlockPos p_177683_0_, final EnumFacing p_177683_1_,
			final EnumFacing p_177683_2_, final int p_177683_3_, final int p_177683_4_, final int p_177683_5_) {
		if (p_177683_1_ != p_177683_2_ && p_177683_1_ != p_177683_2_.getOpposite()) {
			final Vec3i var6 = new Vec3i(p_177683_1_.getFrontOffsetX(), p_177683_1_.getFrontOffsetY(),
					p_177683_1_.getFrontOffsetZ());
			final Vec3i var7 = new Vec3i(p_177683_2_.getFrontOffsetX(), p_177683_2_.getFrontOffsetY(),
					p_177683_2_.getFrontOffsetZ());
			final Vec3i var8 = var6.crossProduct(var7);
			return p_177683_0_.add(var7.getX() * -p_177683_4_ + var8.getX() * p_177683_3_ + var6.getX() * p_177683_5_,
					var7.getY() * -p_177683_4_ + var8.getY() * p_177683_3_ + var6.getY() * p_177683_5_,
					var7.getZ() * -p_177683_4_ + var8.getZ() * p_177683_3_ + var6.getZ() * p_177683_5_);
		} else {
			throw new IllegalArgumentException("Invalid forwards & up combination");
		}
	}

	static class CacheLoader extends com.google.common.cache.CacheLoader {
		private final World field_177680_a;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002023";

		public CacheLoader(final World worldIn) {
			field_177680_a = worldIn;
		}

		public BlockWorldState func_177679_a(final BlockPos p_177679_1_) {
			return new BlockWorldState(field_177680_a, p_177679_1_);
		}

		@Override
		public Object load(final Object p_load_1_) {
			return func_177679_a((BlockPos) p_load_1_);
		}
	}

	public static class PatternHelper {
		private final BlockPos field_177674_a;
		private final EnumFacing field_177672_b;
		private final EnumFacing field_177673_c;
		private final LoadingCache field_177671_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002022";

		public PatternHelper(final BlockPos p_i45655_1_, final EnumFacing p_i45655_2_, final EnumFacing p_i45655_3_,
				final LoadingCache p_i45655_4_) {
			field_177674_a = p_i45655_1_;
			field_177672_b = p_i45655_2_;
			field_177673_c = p_i45655_3_;
			field_177671_d = p_i45655_4_;
		}

		public EnumFacing func_177669_b() {
			return field_177672_b;
		}

		public EnumFacing func_177668_c() {
			return field_177673_c;
		}

		public BlockWorldState func_177670_a(final int p_177670_1_, final int p_177670_2_, final int p_177670_3_) {
			return (BlockWorldState) field_177671_d.getUnchecked(BlockPattern.func_177683_a(field_177674_a,
					func_177669_b(), func_177668_c(), p_177670_1_, p_177670_2_, p_177670_3_));
		}
	}
}
