package net.minecraft.block.state;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class BlockWorldState {

public static final int EaZy = 424;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final World world;
	private final BlockPos pos;
	private IBlockState field_177514_c;
	private TileEntity field_177511_d;
	private boolean field_177512_e;
	// private static final String __OBFID = "http://https://fuckuskid00002026";

	public BlockWorldState(final World worldIn, final BlockPos p_i45659_2_) {
		world = worldIn;
		pos = p_i45659_2_;
	}

	public IBlockState func_177509_a() {
		if (field_177514_c == null && world.isBlockLoaded(pos)) {
			field_177514_c = world.getBlockState(pos);
		}

		return field_177514_c;
	}

	public TileEntity func_177507_b() {
		if (field_177511_d == null && !field_177512_e) {
			field_177511_d = world.getTileEntity(pos);
			field_177512_e = true;
		}

		return field_177511_d;
	}

	public BlockPos getPos() {
		return pos;
	}

	public static Predicate hasState(final Predicate p_177510_0_) {
		return new Predicate() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002025";
			public boolean func_177503_a(final BlockWorldState p_177503_1_) {
				return p_177503_1_ != null && p_177510_0_.apply(p_177503_1_.func_177509_a());
			}

			@Override
			public boolean apply(final Object p_apply_1_) {
				return func_177503_a((BlockWorldState) p_apply_1_);
			}
		};
	}
}
