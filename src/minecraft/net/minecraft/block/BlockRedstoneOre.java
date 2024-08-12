package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneOre extends Block {

public static final int EaZy = 364;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final boolean isOn;
	// private static final String __OBFID = "http://https://fuckuskid00000294";

	public BlockRedstoneOre(final boolean p_i45420_1_) {
		super(Material.rock);

		if (p_i45420_1_) {
			setTickRandomly(true);
		}

		isOn = p_i45420_1_;
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(final World worldIn) {
		return 30;
	}

	@Override
	public void onBlockClicked(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
		setOn(worldIn, pos);
		super.onBlockClicked(worldIn, pos, playerIn);
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the
	 * block)
	 */
	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final Entity entityIn) {
		setOn(worldIn, pos);
		super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		setOn(worldIn, pos);
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	private void setOn(final World worldIn, final BlockPos p_176352_2_) {
		spawnRedstoneParticles(worldIn, p_176352_2_);

		if (this == Blocks.redstone_ore) {
			worldIn.setBlockState(p_176352_2_, Blocks.lit_redstone_ore.getDefaultState());
		}
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (this == Blocks.lit_redstone_ore) {
			worldIn.setBlockState(pos, Blocks.redstone_ore.getDefaultState());
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
		return Items.redstone;
	}

	/**
	 * Get the quantity dropped based on the given fortune level
	 */
	@Override
	public int quantityDroppedWithBonus(final int fortune, final Random random) {
		return quantityDropped(random) + random.nextInt(fortune + 1);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 4 + random.nextInt(2);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 * 
	 * @param chance
	 *            The chance that each Item is actually spawned (1.0 = always,
	 *            0.0 = never)
	 * @param fortune
	 *            The player's fortune level
	 */
	@Override
	public void dropBlockAsItemWithChance(final World worldIn, final BlockPos pos, final IBlockState state,
			final float chance, final int fortune) {
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);

		if (getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this)) {
			final int var6 = 1 + worldIn.rand.nextInt(5);
			dropXpOnBlockBreak(worldIn, pos, var6);
		}
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (isOn) {
			spawnRedstoneParticles(worldIn, pos);
		}
	}

	private void spawnRedstoneParticles(final World worldIn, final BlockPos p_180691_2_) {
		final Random var3 = worldIn.rand;
		final double var4 = 0.0625D;

		for (int var6 = 0; var6 < 6; ++var6) {
			double var7 = p_180691_2_.getX() + var3.nextFloat();
			double var9 = p_180691_2_.getY() + var3.nextFloat();
			double var11 = p_180691_2_.getZ() + var3.nextFloat();

			if (var6 == 0 && !worldIn.getBlockState(p_180691_2_.offsetUp()).getBlock().isOpaqueCube()) {
				var9 = p_180691_2_.getY() + var4 + 1.0D;
			}

			if (var6 == 1 && !worldIn.getBlockState(p_180691_2_.offsetDown()).getBlock().isOpaqueCube()) {
				var9 = p_180691_2_.getY() - var4;
			}

			if (var6 == 2 && !worldIn.getBlockState(p_180691_2_.offsetSouth()).getBlock().isOpaqueCube()) {
				var11 = p_180691_2_.getZ() + var4 + 1.0D;
			}

			if (var6 == 3 && !worldIn.getBlockState(p_180691_2_.offsetNorth()).getBlock().isOpaqueCube()) {
				var11 = p_180691_2_.getZ() - var4;
			}

			if (var6 == 4 && !worldIn.getBlockState(p_180691_2_.offsetEast()).getBlock().isOpaqueCube()) {
				var7 = p_180691_2_.getX() + var4 + 1.0D;
			}

			if (var6 == 5 && !worldIn.getBlockState(p_180691_2_.offsetWest()).getBlock().isOpaqueCube()) {
				var7 = p_180691_2_.getX() - var4;
			}

			if (var7 < p_180691_2_.getX() || var7 > p_180691_2_.getX() + 1 || var9 < 0.0D
					|| var9 > p_180691_2_.getY() + 1 || var11 < p_180691_2_.getZ() || var11 > p_180691_2_.getZ() + 1) {
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, var7, var9, var11, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

	@Override
	protected ItemStack createStackedBlock(final IBlockState state) {
		return new ItemStack(Blocks.redstone_ore);
	}
}
