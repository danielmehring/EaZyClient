package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;

public class BetterGrass {

public static final int EaZy = 1872;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static IBakedModel modelEmpty = new SimpleBakedModel(new ArrayList(), new ArrayList(), false, false,
			(TextureAtlasSprite) null, (ItemCameraTransforms) null);
	private static IBakedModel modelCubeMycelium = null;
	private static IBakedModel modelCubeGrassSnowy = null;
	private static IBakedModel modelCubeGrass = null;

	public static void update() {
		modelCubeGrass = BlockModelUtils.makeModelCube("minecraft:blocks/grass_top", 0);
		modelCubeGrassSnowy = BlockModelUtils.makeModelCube("minecraft:blocks/snow", -1);
		modelCubeMycelium = BlockModelUtils.makeModelCube("minecraft:blocks/mycelium_top", -1);
	}

	public static List getFaceQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos,
			final EnumFacing facing, final List quads) {
		if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
			if (block instanceof BlockMycelium) {
				return Config.isBetterGrassFancy()
						? getBlockAt(blockPos.offsetDown(), facing, blockAccess) == Blocks.mycelium
								? modelCubeMycelium.func_177551_a(facing) : quads
						: modelCubeMycelium.func_177551_a(facing);
			} else {
				if (block instanceof BlockGrass) {
					final Block blockUp = blockAccess.getBlockState(blockPos.offsetUp()).getBlock();
					final boolean snowy = blockUp == Blocks.snow || blockUp == Blocks.snow_layer;

					if (!Config.isBetterGrassFancy()) {
						if (snowy) {
							return modelCubeGrassSnowy.func_177551_a(facing);
						}

						return modelCubeGrass.func_177551_a(facing);
					}

					if (snowy) {
						if (getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer) {
							return modelCubeGrassSnowy.func_177551_a(facing);
						}
					} else if (getBlockAt(blockPos.offsetDown(), facing, blockAccess) == Blocks.grass) {
						return modelCubeGrass.func_177551_a(facing);
					}
				}

				return quads;
			}
		} else {
			return quads;
		}
	}

	private static Block getBlockAt(final BlockPos blockPos, final EnumFacing facing, final IBlockAccess blockAccess) {
		final BlockPos pos = blockPos.offset(facing);
		final Block block = blockAccess.getBlockState(pos).getBlock();
		return block;
	}
}
