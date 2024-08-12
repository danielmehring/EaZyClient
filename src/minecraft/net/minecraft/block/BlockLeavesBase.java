package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import java.util.IdentityHashMap;
import java.util.Map;

import optifine.Config;

public class BlockLeavesBase extends Block {

public static final int EaZy = 323;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected boolean field_150121_P;
	// private static final String __OBFID = "http://https://fuckuskid00000326";
	private static Map mapOriginalOpacity = new IdentityHashMap();

	protected BlockLeavesBase(final Material materialIn, final boolean fancyGraphics) {
		super(materialIn);
		field_150121_P = fancyGraphics;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		return Config.isCullFacesLeaves() && worldIn.getBlockState(pos).getBlock() == this ? false
				: super.shouldSideBeRendered(worldIn, pos, side);
	}

	public static void setLightOpacity(final Block block, final int opacity) {
		if (!mapOriginalOpacity.containsKey(block)) {
			mapOriginalOpacity.put(block, block.getLightOpacity());
		}

		block.setLightOpacity(opacity);
	}

	public static void restoreLightOpacity(final Block block) {
		if (mapOriginalOpacity.containsKey(block)) {
			final int opacity = ((Integer) mapOriginalOpacity.get(block));
			setLightOpacity(block, opacity);
		}
	}
}
