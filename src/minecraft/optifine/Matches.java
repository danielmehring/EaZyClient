package optifine;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.BiomeGenBase;

public class Matches {

public static final int EaZy = 1930;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static boolean block(final BlockStateBase blockStateBase, final MatchBlock[] matchBlocks) {
		if (matchBlocks == null) {
			return true;
		} else {
			for (final MatchBlock mb : matchBlocks) {
				if (mb.matches(blockStateBase)) {
					return true;
				}
			}

			return false;
		}
	}

	public static boolean block(final int blockId, final int metadata, final MatchBlock[] matchBlocks) {
		if (matchBlocks == null) {
			return true;
		} else {
			for (final MatchBlock mb : matchBlocks) {
				if (mb.matches(blockId, metadata)) {
					return true;
				}
			}

			return false;
		}
	}

	public static boolean blockId(final int blockId, final MatchBlock[] matchBlocks) {
		if (matchBlocks == null) {
			return true;
		} else {
			for (final MatchBlock mb : matchBlocks) {
				if (mb.getBlockId() == blockId) {
					return true;
				}
			}

			return false;
		}
	}

	public static boolean metadata(final int metadata, final int[] metadatas) {
		if (metadatas == null) {
			return true;
		} else {
			for (final int metadata2 : metadatas) {
				if (metadata2 == metadata) {
					return true;
				}
			}

			return false;
		}
	}

	public static boolean sprite(final TextureAtlasSprite sprite, final TextureAtlasSprite[] sprites) {
		if (sprites == null) {
			return true;
		} else {
			for (final TextureAtlasSprite sprite2 : sprites) {
				if (sprite2 == sprite) {
					return true;
				}
			}

			return false;
		}
	}

	public static boolean biome(final BiomeGenBase biome, final BiomeGenBase[] biomes) {
		if (biomes == null) {
			return true;
		} else {
			for (final BiomeGenBase biome2 : biomes) {
				if (biome2 == biome) {
					return true;
				}
			}

			return false;
		}
	}
}
