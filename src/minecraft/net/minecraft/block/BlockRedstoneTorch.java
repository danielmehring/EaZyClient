package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BlockRedstoneTorch extends BlockTorch {

public static final int EaZy = 366;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static Map field_150112_b = Maps.newHashMap();
	private final boolean field_150113_a;
	// private static final String __OBFID = "http://https://fuckuskid00000298";

	private boolean func_176598_a(final World worldIn, final BlockPos p_176598_2_, final boolean p_176598_3_) {
		if (!field_150112_b.containsKey(worldIn)) {
			field_150112_b.put(worldIn, Lists.newArrayList());
		}

		final List var4 = (List) field_150112_b.get(worldIn);

		if (p_176598_3_) {
			var4.add(new BlockRedstoneTorch.Toggle(p_176598_2_, worldIn.getTotalWorldTime()));
		}

		int var5 = 0;

		for (int var6 = 0; var6 < var4.size(); ++var6) {
			final BlockRedstoneTorch.Toggle var7 = (BlockRedstoneTorch.Toggle) var4.get(var6);

			if (var7.field_180111_a.equals(p_176598_2_)) {
				++var5;

				if (var5 >= 8) {
					return true;
				}
			}
		}

		return false;
	}

	protected BlockRedstoneTorch(final boolean p_i45423_1_) {
		field_150113_a = p_i45423_1_;
		setTickRandomly(true);
		setCreativeTab((CreativeTabs) null);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(final World worldIn) {
		return 2;
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (field_150113_a) {
			final EnumFacing[] var4 = EnumFacing.values();
			final int var5 = var4.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				final EnumFacing var7 = var4[var6];
				worldIn.notifyNeighborsOfStateChange(pos.offset(var7), this);
			}
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (field_150113_a) {
			final EnumFacing[] var4 = EnumFacing.values();
			final int var5 = var4.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				final EnumFacing var7 = var4[var6];
				worldIn.notifyNeighborsOfStateChange(pos.offset(var7), this);
			}
		}
	}

	@Override
	public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return field_150113_a && state.getValue(FACING_PROP) != side ? 15 : 0;
	}

	private boolean func_176597_g(final World worldIn, final BlockPos p_176597_2_, final IBlockState p_176597_3_) {
		final EnumFacing var4 = ((EnumFacing) p_176597_3_.getValue(FACING_PROP)).getOpposite();
		return worldIn.func_175709_b(p_176597_2_.offset(var4), var4);
	}

	/**
	 * Called randomly when setTickRandomly is set to true (used by e.g. crops
	 * to grow, etc.)
	 */
	@Override
	public void randomTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		final boolean var5 = func_176597_g(worldIn, pos, state);
		final List var6 = (List) field_150112_b.get(worldIn);

		while (var6 != null && !var6.isEmpty()
				&& worldIn.getTotalWorldTime() - ((BlockRedstoneTorch.Toggle) var6.get(0)).field_150844_d > 60L) {
			var6.remove(0);
		}

		if (field_150113_a) {
			if (var5) {
				worldIn.setBlockState(pos, Blocks.unlit_redstone_torch.getDefaultState().withProperty(FACING_PROP,
						state.getValue(FACING_PROP)), 3);

				if (func_176598_a(worldIn, pos, true)) {
					worldIn.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, "random.fizz",
							0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

					for (int var7 = 0; var7 < 5; ++var7) {
						final double var8 = pos.getX() + rand.nextDouble() * 0.6D + 0.2D;
						final double var10 = pos.getY() + rand.nextDouble() * 0.6D + 0.2D;
						final double var12 = pos.getZ() + rand.nextDouble() * 0.6D + 0.2D;
						worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var8, var10, var12, 0.0D, 0.0D, 0.0D,
								new int[0]);
					}

					worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 160);
				}
			}
		} else if (!var5 && !func_176598_a(worldIn, pos, false)) {
			worldIn.setBlockState(pos,
					Blocks.redstone_torch.getDefaultState().withProperty(FACING_PROP, state.getValue(FACING_PROP)), 3);
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!func_176592_e(worldIn, pos, state)) {
			if (field_150113_a == func_176597_g(worldIn, pos, state)) {
				worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
			}
		}
	}

	@Override
	public int isProvidingStrongPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return side == EnumFacing.DOWN ? isProvidingWeakPower(worldIn, pos, state, side) : 0;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Item.getItemFromBlock(Blocks.redstone_torch);
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (field_150113_a) {
			double var5 = pos.getX() + 0.5F + (rand.nextFloat() - 0.5F) * 0.2D;
			double var7 = pos.getY() + 0.7F + (rand.nextFloat() - 0.5F) * 0.2D;
			double var9 = pos.getZ() + 0.5F + (rand.nextFloat() - 0.5F) * 0.2D;
			final EnumFacing var11 = (EnumFacing) state.getValue(FACING_PROP);

			if (var11.getAxis().isHorizontal()) {
				final EnumFacing var12 = var11.getOpposite();
				var5 += 0.27000001072883606D * var12.getFrontOffsetX();
				var7 += 0.2199999988079071D;
				var9 += 0.27000001072883606D * var12.getFrontOffsetZ();
			}

			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, var5, var7, var9, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Item.getItemFromBlock(Blocks.redstone_torch);
	}

	@Override
	public boolean isAssociatedBlock(final Block other) {
		return other == Blocks.unlit_redstone_torch || other == Blocks.redstone_torch;
	}

	static class Toggle {
		BlockPos field_180111_a;
		long field_150844_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000299";

		public Toggle(final BlockPos p_i45688_1_, final long p_i45688_2_) {
			field_180111_a = p_i45688_1_;
			field_150844_d = p_i45688_2_;
		}
	}
}
