package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class DefaultStateMapper extends StateMapperBase {

public static final int EaZy = 687;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002477";

	@Override
	protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
		return new ModelResourceLocation(
				(ResourceLocation) Block.blockRegistry.getNameForObject(p_178132_1_.getBlock()),
				func_178131_a(p_178132_1_.getProperties()));
	}
}
