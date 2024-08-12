package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.world.ColorizerGrass;

import com.google.common.base.Function;

public class ItemDoublePlant extends ItemMultiTexture {

public static final int EaZy = 1284;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000021";

	public ItemDoublePlant(final Block p_i45787_1_, final Block p_i45787_2_, final Function p_i45787_3_) {
		super(p_i45787_1_, p_i45787_2_, p_i45787_3_);
	}

	@Override
	public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
		final BlockDoublePlant.EnumPlantType var3 = BlockDoublePlant.EnumPlantType.func_176938_a(stack.getMetadata());
		return var3 != BlockDoublePlant.EnumPlantType.GRASS && var3 != BlockDoublePlant.EnumPlantType.FERN
				? super.getColorFromItemStack(stack, renderPass) : ColorizerGrass.getGrassColor(0.5D, 1.0D);
	}
}
