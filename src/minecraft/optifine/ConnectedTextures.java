package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConnectedTextures {

public static final int EaZy = 1887;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static Map[] spriteQuadMaps = null;
	private static ConnectedProperties[][] blockProperties = null;
	private static ConnectedProperties[][] tileProperties = null;
	private static boolean multipass = false;
	private static final int[] ctmIndexes = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0,
			36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0 };
	public static final IBlockState AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
	private static TextureAtlasSprite emptySprite = null;

	public static synchronized BakedQuad getConnectedTexture(final IBlockAccess blockAccess,
			final IBlockState blockState, final BlockPos blockPos, final BakedQuad quad, final RenderEnv renderEnv) {
		final TextureAtlasSprite spriteIn = quad.getSprite();

		if (spriteIn == null) {
			return quad;
		} else {
			final Block block = blockState.getBlock();
			final EnumFacing side = quad.getFace();

			if (block instanceof BlockPane && spriteIn.getIconName().startsWith("minecraft:blocks/glass_pane_top")) {
				final IBlockState sprite = blockAccess.getBlockState(blockPos.offset(quad.getFace()));

				if (sprite == blockState) {
					return getQuad(emptySprite, block, blockState, quad);
				}
			}

			final TextureAtlasSprite sprite1 = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, side,
					spriteIn, renderEnv);
			return sprite1 == spriteIn ? quad : getQuad(sprite1, block, blockState, quad);
		}
	}

	private static BakedQuad getQuad(final TextureAtlasSprite sprite, final Block block, final IBlockState blockState,
			final BakedQuad quadIn) {
		if (spriteQuadMaps == null) {
			return quadIn;
		} else {
			final int spriteIndex = sprite.getIndexInMap();

			if (spriteIndex >= 0 && spriteIndex < spriteQuadMaps.length) {
				Object quadMap = spriteQuadMaps[spriteIndex];

				if (quadMap == null) {
					quadMap = new IdentityHashMap(1);
					spriteQuadMaps[spriteIndex] = (Map) quadMap;
				}

				BakedQuad quad = (BakedQuad) ((Map) quadMap).get(quadIn);

				if (quad == null) {
					quad = makeSpriteQuad(quadIn, sprite);
					((Map) quadMap).put(quadIn, quad);
				}

				return quad;
			} else {
				return quadIn;
			}
		}
	}

	private static BakedQuad makeSpriteQuad(final BakedQuad quad, final TextureAtlasSprite sprite) {
		final int[] data = quad.func_178209_a().clone();
		final TextureAtlasSprite spriteFrom = quad.getSprite();

		for (int bq = 0; bq < 4; ++bq) {
			fixVertex(data, bq, spriteFrom, sprite);
		}

		final BakedQuad var5 = new BakedQuad(data, quad.func_178211_c(), quad.getFace(), sprite);
		return var5;
	}

	private static void fixVertex(final int[] data, final int vertex, final TextureAtlasSprite spriteFrom,
			final TextureAtlasSprite spriteTo) {
		final int mul = data.length / 4;
		final int pos = mul * vertex;
		final float u = Float.intBitsToFloat(data[pos + 4]);
		final float v = Float.intBitsToFloat(data[pos + 4 + 1]);
		final double su16 = spriteFrom.getSpriteU16(u);
		final double sv16 = spriteFrom.getSpriteV16(v);
		data[pos + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU(su16));
		data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV(sv16));
	}

	private static TextureAtlasSprite getConnectedTextureMultiPass(final IBlockAccess blockAccess,
			final IBlockState blockState, final BlockPos blockPos, final EnumFacing side, final TextureAtlasSprite icon,
			final RenderEnv renderEnv) {
		final TextureAtlasSprite newIcon = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, icon,
				true, renderEnv);

		if (!multipass) {
			return newIcon;
		} else if (newIcon == icon) {
			return newIcon;
		} else {
			TextureAtlasSprite mpIcon = newIcon;

			for (int i = 0; i < 3; ++i) {
				final TextureAtlasSprite newMpIcon = getConnectedTextureSingle(blockAccess, blockState, blockPos, side,
						mpIcon, false, renderEnv);

				if (newMpIcon == mpIcon) {
					break;
				}

				mpIcon = newMpIcon;
			}

			return mpIcon;
		}
	}

	public static TextureAtlasSprite getConnectedTextureSingle(final IBlockAccess blockAccess,
			final IBlockState blockState, final BlockPos blockPos, final EnumFacing facing,
			final TextureAtlasSprite icon, final boolean checkBlocks, final RenderEnv renderEnv) {
		blockState.getBlock();

		if (!(blockState instanceof BlockStateBase)) {
			return icon;
		} else {
			final BlockStateBase blockStateBase = (BlockStateBase) blockState;
			int blockId;
			ConnectedProperties[] cps;
			int side;
			int i;
			ConnectedProperties cp;
			TextureAtlasSprite newIcon;

			if (tileProperties != null) {
				blockId = icon.getIndexInMap();

				if (blockId >= 0 && blockId < tileProperties.length) {
					cps = tileProperties[blockId];

					if (cps != null) {
						side = getSide(facing);

						for (i = 0; i < cps.length; ++i) {
							cp = cps[i];

							if (cp != null && cp.matchesBlockId(blockStateBase.getBlockId())) {
								newIcon = getConnectedTexture(cp, blockAccess, blockStateBase, blockPos, side, icon,
										renderEnv);

								if (newIcon != null) {
									return newIcon;
								}
							}
						}
					}
				}
			}

			if (blockProperties != null && checkBlocks) {
				blockId = renderEnv.getBlockId();

				if (blockId >= 0 && blockId < blockProperties.length) {
					cps = blockProperties[blockId];

					if (cps != null) {
						side = getSide(facing);

						for (i = 0; i < cps.length; ++i) {
							cp = cps[i];

							if (cp != null && cp.matchesIcon(icon)) {
								newIcon = getConnectedTexture(cp, blockAccess, blockStateBase, blockPos, side, icon,
										renderEnv);

								if (newIcon != null) {
									return newIcon;
								}
							}
						}
					}
				}
			}

			return icon;
		}
	}

	public static int getSide(final EnumFacing facing) {
		if (facing == null) {
			return -1;
		} else {
			switch (ConnectedTextures.NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[facing
					.ordinal()]) {
				case 1:
					return 0;

				case 2:
					return 1;

				case 3:
					return 5;

				case 4:
					return 4;

				case 5:
					return 2;

				case 6:
					return 3;

				default:
					return -1;
			}
		}
	}

	private static EnumFacing getFacing(final int side) {
		switch (side) {
			case 0:
				return EnumFacing.DOWN;

			case 1:
				return EnumFacing.UP;

			case 2:
				return EnumFacing.NORTH;

			case 3:
				return EnumFacing.SOUTH;

			case 4:
				return EnumFacing.WEST;

			case 5:
				return EnumFacing.EAST;

			default:
				return EnumFacing.UP;
		}
	}

	private static TextureAtlasSprite getConnectedTexture(final ConnectedProperties cp, final IBlockAccess blockAccess,
			final BlockStateBase blockState, final BlockPos blockPos, final int side, final TextureAtlasSprite icon,
			final RenderEnv renderEnv) {
		int vertAxis = 0;
		final int metadata = blockState.getMetadata();
		int metadataCheck = metadata;
		final Block block = blockState.getBlock();

		if (block instanceof BlockRotatedPillar) {
			vertAxis = getWoodAxis(side, metadata);

			if (cp.getMetadataMax() <= 3) {
				metadataCheck = metadata & 3;
			}
		}

		if (block instanceof BlockQuartz) {
			vertAxis = getQuartzAxis(side, metadata);

			if (cp.getMetadataMax() <= 2 && metadataCheck > 2) {
				metadataCheck = 2;
			}
		}

		if (!cp.matchesBlock(blockState.getBlockId(), metadataCheck)) {
			return null;
		} else {
			int y;

			if (side >= 0 && cp.faces != 63) {
				y = side;

				if (vertAxis != 0) {
					y = fixSideByAxis(side, vertAxis);
				}

				if ((1 << y & cp.faces) == 0) {
					return null;
				}
			}

			y = blockPos.getY();

			if (y >= cp.minHeight && y <= cp.maxHeight) {
				if (cp.biomes != null) {
					final BiomeGenBase blockBiome = blockAccess.getBiomeGenForCoords(blockPos);

					if (!cp.matchesBiome(blockBiome)) {
						return null;
					}
				}

				switch (cp.method) {
					case 1:
						return getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, icon,
								metadata, renderEnv);

					case 2:
						return getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side,
								icon, metadata);

					case 3:
						return getConnectedTextureTop(cp, blockAccess, blockState, blockPos, vertAxis, side, icon,
								metadata);

					case 4:
						return getConnectedTextureRandom(cp, blockPos, side);

					case 5:
						return getConnectedTextureRepeat(cp, blockPos, side);

					case 6:
						return getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon,
								metadata);

					case 7:
						return getConnectedTextureFixed(cp);

					case 8:
						return getConnectedTextureHorizontalVertical(cp, blockAccess, blockState, blockPos, vertAxis,
								side, icon, metadata);

					case 9:
						return getConnectedTextureVerticalHorizontal(cp, blockAccess, blockState, blockPos, vertAxis,
								side, icon, metadata);

					default:
						return null;
				}
			} else {
				return null;
			}
		}
	}

	private static int fixSideByAxis(final int side, final int vertAxis) {
		switch (vertAxis) {
			case 0:
				return side;

			case 1:
				switch (side) {
					case 0:
						return 2;

					case 1:
						return 3;

					case 2:
						return 1;

					case 3:
						return 0;

					default:
						return side;
				}

			case 2:
				switch (side) {
					case 0:
						return 4;

					case 1:
						return 5;

					case 2:
					case 3:
					default:
						return side;

					case 4:
						return 1;

					case 5:
						return 0;
				}

			default:
				return side;
		}
	}

	private static int getWoodAxis(final int side, final int metadata) {
		final int orient = (metadata & 12) >> 2;

		switch (orient) {
			case 1:
				return 2;

			case 2:
				return 1;

			default:
				return 0;
		}
	}

	private static int getQuartzAxis(final int side, final int metadata) {
		switch (metadata) {
			case 3:
				return 2;

			case 4:
				return 1;

			default:
				return 0;
		}
	}

	private static TextureAtlasSprite getConnectedTextureRandom(final ConnectedProperties cp, final BlockPos blockPos,
			final int side) {
		if (cp.tileIcons.length == 1) {
			return cp.tileIcons[0];
		} else {
			final int face = side / cp.symmetry * cp.symmetry;
			final int rand = Config.getRandom(blockPos, face) & Integer.MAX_VALUE;
			int index = 0;

			if (cp.weights == null) {
				index = rand % cp.tileIcons.length;
			} else {
				final int randWeight = rand % cp.sumAllWeights;
				final int[] sumWeights = cp.sumWeights;

				for (int i = 0; i < sumWeights.length; ++i) {
					if (randWeight < sumWeights[i]) {
						index = i;
						break;
					}
				}
			}

			return cp.tileIcons[index];
		}
	}

	private static TextureAtlasSprite getConnectedTextureFixed(final ConnectedProperties cp) {
		return cp.tileIcons[0];
	}

	private static TextureAtlasSprite getConnectedTextureRepeat(final ConnectedProperties cp, final BlockPos blockPos,
			final int side) {
		if (cp.tileIcons.length == 1) {
			return cp.tileIcons[0];
		} else {
			final int x = blockPos.getX();
			final int y = blockPos.getY();
			final int z = blockPos.getZ();
			int nx = 0;
			int ny = 0;

			switch (side) {
				case 0:
					nx = x;
					ny = z;
					break;

				case 1:
					nx = x;
					ny = z;
					break;

				case 2:
					nx = -x - 1;
					ny = -y;
					break;

				case 3:
					nx = x;
					ny = -y;
					break;

				case 4:
					nx = z;
					ny = -y;
					break;

				case 5:
					nx = -z - 1;
					ny = -y;
			}

			nx %= cp.width;
			ny %= cp.height;

			if (nx < 0) {
				nx += cp.width;
			}

			if (ny < 0) {
				ny += cp.height;
			}

			final int index = ny * cp.width + nx;
			return cp.tileIcons[index];
		}
	}

	private static TextureAtlasSprite getConnectedTextureCtm(final ConnectedProperties cp,
			final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis,
			final int side, final TextureAtlasSprite icon, final int metadata, final RenderEnv renderEnv) {
		final boolean[] borders = renderEnv.getBorderFlags();

		switch (side) {
			case 0:
				borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
				borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
				borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
				borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
				break;

			case 1:
				borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
				borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
				borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
				borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
				break;

			case 2:
				borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
				borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
				borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
				borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
				break;

			case 3:
				borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
				borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
				borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
				borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
				break;

			case 4:
				borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
				borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
				borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
				borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
				break;

			case 5:
				borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
				borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
				borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
				borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
		}

		byte index = 0;

		if (borders[0] & !borders[1] & !borders[2] & !borders[3]) {
			index = 3;
		} else if (!borders[0] & borders[1] & !borders[2] & !borders[3]) {
			index = 1;
		} else if (!borders[0] & !borders[1] & borders[2] & !borders[3]) {
			index = 12;
		} else if (!borders[0] & !borders[1] & !borders[2] & borders[3]) {
			index = 36;
		} else if (borders[0] & borders[1] & !borders[2] & !borders[3]) {
			index = 2;
		} else if (!borders[0] & !borders[1] & borders[2] & borders[3]) {
			index = 24;
		} else if (borders[0] & !borders[1] & borders[2] & !borders[3]) {
			index = 15;
		} else if (borders[0] & !borders[1] & !borders[2] & borders[3]) {
			index = 39;
		} else if (!borders[0] & borders[1] & borders[2] & !borders[3]) {
			index = 13;
		} else if (!borders[0] & borders[1] & !borders[2] & borders[3]) {
			index = 37;
		} else if (!borders[0] & borders[1] & borders[2] & borders[3]) {
			index = 25;
		} else if (borders[0] & !borders[1] & borders[2] & borders[3]) {
			index = 27;
		} else if (borders[0] & borders[1] & !borders[2] & borders[3]) {
			index = 38;
		} else if (borders[0] & borders[1] & borders[2] & !borders[3]) {
			index = 14;
		} else if (borders[0] & borders[1] & borders[2] & borders[3]) {
			index = 26;
		}

		if (index == 0) {
			return cp.tileIcons[index];
		} else if (!Config.isConnectedTexturesFancy()) {
			return cp.tileIcons[index];
		} else {
			switch (side) {
				case 0:
					borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast().offsetNorth(), side,
							icon, metadata);
					borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest().offsetNorth(), side,
							icon, metadata);
					borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast().offsetSouth(), side,
							icon, metadata);
					borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest().offsetSouth(), side,
							icon, metadata);
					break;

				case 1:
					borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast().offsetSouth(), side,
							icon, metadata);
					borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest().offsetSouth(), side,
							icon, metadata);
					borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast().offsetNorth(), side,
							icon, metadata);
					borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest().offsetNorth(), side,
							icon, metadata);
					break;

				case 2:
					borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest().offsetDown(), side,
							icon, metadata);
					borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast().offsetDown(), side,
							icon, metadata);
					borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest().offsetUp(), side, icon,
							metadata);
					borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast().offsetUp(), side, icon,
							metadata);
					break;

				case 3:
					borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast().offsetDown(), side,
							icon, metadata);
					borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest().offsetDown(), side,
							icon, metadata);
					borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast().offsetUp(), side, icon,
							metadata);
					borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest().offsetUp(), side, icon,
							metadata);
					break;

				case 4:
					borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown().offsetSouth(), side,
							icon, metadata);
					borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown().offsetNorth(), side,
							icon, metadata);
					borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp().offsetSouth(), side,
							icon, metadata);
					borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp().offsetNorth(), side,
							icon, metadata);
					break;

				case 5:
					borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown().offsetNorth(), side,
							icon, metadata);
					borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown().offsetSouth(), side,
							icon, metadata);
					borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp().offsetNorth(), side,
							icon, metadata);
					borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp().offsetSouth(), side,
							icon, metadata);
			}

			if (index == 13 && borders[0]) {
				index = 4;
			} else if (index == 15 && borders[1]) {
				index = 5;
			} else if (index == 37 && borders[2]) {
				index = 16;
			} else if (index == 39 && borders[3]) {
				index = 17;
			} else if (index == 14 && borders[0] && borders[1]) {
				index = 7;
			} else if (index == 25 && borders[0] && borders[2]) {
				index = 6;
			} else if (index == 27 && borders[3] && borders[1]) {
				index = 19;
			} else if (index == 38 && borders[3] && borders[2]) {
				index = 18;
			} else if (index == 14 && !borders[0] && borders[1]) {
				index = 31;
			} else if (index == 25 && borders[0] && !borders[2]) {
				index = 30;
			} else if (index == 27 && !borders[3] && borders[1]) {
				index = 41;
			} else if (index == 38 && borders[3] && !borders[2]) {
				index = 40;
			} else if (index == 14 && borders[0] && !borders[1]) {
				index = 29;
			} else if (index == 25 && !borders[0] && borders[2]) {
				index = 28;
			} else if (index == 27 && borders[3] && !borders[1]) {
				index = 43;
			} else if (index == 38 && !borders[3] && borders[2]) {
				index = 42;
			} else if (index == 26 && borders[0] && borders[1] && borders[2] && borders[3]) {
				index = 46;
			} else if (index == 26 && !borders[0] && borders[1] && borders[2] && borders[3]) {
				index = 9;
			} else if (index == 26 && borders[0] && !borders[1] && borders[2] && borders[3]) {
				index = 21;
			} else if (index == 26 && borders[0] && borders[1] && !borders[2] && borders[3]) {
				index = 8;
			} else if (index == 26 && borders[0] && borders[1] && borders[2] && !borders[3]) {
				index = 20;
			} else if (index == 26 && borders[0] && borders[1] && !borders[2] && !borders[3]) {
				index = 11;
			} else if (index == 26 && !borders[0] && !borders[1] && borders[2] && borders[3]) {
				index = 22;
			} else if (index == 26 && !borders[0] && borders[1] && !borders[2] && borders[3]) {
				index = 23;
			} else if (index == 26 && borders[0] && !borders[1] && borders[2] && !borders[3]) {
				index = 10;
			} else if (index == 26 && borders[0] && !borders[1] && !borders[2] && borders[3]) {
				index = 34;
			} else if (index == 26 && !borders[0] && borders[1] && borders[2] && !borders[3]) {
				index = 35;
			} else if (index == 26 && borders[0] && !borders[1] && !borders[2] && !borders[3]) {
				index = 32;
			} else if (index == 26 && !borders[0] && borders[1] && !borders[2] && !borders[3]) {
				index = 33;
			} else if (index == 26 && !borders[0] && !borders[1] && borders[2] && !borders[3]) {
				index = 44;
			} else if (index == 26 && !borders[0] && !borders[1] && !borders[2] && borders[3]) {
				index = 45;
			}

			return cp.tileIcons[index];
		}
	}

	private static boolean isNeighbour(final ConnectedProperties cp, final IBlockAccess iblockaccess,
			final IBlockState blockState, final BlockPos blockPos, final int side, final TextureAtlasSprite icon,
			final int metadata) {
		final IBlockState neighbourState = iblockaccess.getBlockState(blockPos);

		if (blockState == neighbourState) {
			return true;
		} else if (cp.connect == 2) {
			if (neighbourState == null) {
				return false;
			} else if (neighbourState == AIR_DEFAULT_STATE) {
				return false;
			} else {
				final TextureAtlasSprite neighbourIcon = getNeighbourIcon(iblockaccess, blockPos, neighbourState, side);
				return neighbourIcon == icon;
			}
		} else {
			return cp.connect == 3
					? neighbourState == null ? false
							: neighbourState == AIR_DEFAULT_STATE ? false
									: neighbourState.getBlock().getMaterial() == blockState.getBlock().getMaterial()
					: false;
		}
	}

	private static TextureAtlasSprite getNeighbourIcon(final IBlockAccess iblockaccess, final BlockPos blockPos,
			IBlockState neighbourState, final int side) {
		neighbourState = neighbourState.getBlock().getActualState(neighbourState, iblockaccess, blockPos);
		final IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().func_175023_a()
				.func_178125_b(neighbourState);

		if (model == null) {
			return null;
		} else {
			final EnumFacing facing = getFacing(side);
			final List quads = model.func_177551_a(facing);

			if (quads.size() > 0) {
				final BakedQuad var10 = (BakedQuad) quads.get(0);
				return var10.getSprite();
			} else {
				final List quadsGeneral = model.func_177550_a();

				for (int i = 0; i < quadsGeneral.size(); ++i) {
					final BakedQuad quad = (BakedQuad) quadsGeneral.get(i);

					if (quad.getFace() == facing) {
						return quad.getSprite();
					}
				}

				return null;
			}
		}
	}

	private static TextureAtlasSprite getConnectedTextureHorizontal(final ConnectedProperties cp,
			final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis,
			final int side, final TextureAtlasSprite icon, final int metadata) {
		boolean left;
		boolean right;
		left = false;
		right = false;
		label46:

		switch (vertAxis) {
			case 0:
				switch (side) {
					case 0:
					case 1:
						return null;

					case 2:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
						break label46;

					case 3:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
						break label46;

					case 4:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
						break label46;

					case 5:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);

					default:
						break label46;
				}

			case 1:
				switch (side) {
					case 0:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
						break label46;

					case 1:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
						break label46;

					case 2:
					case 3:
						return null;

					case 4:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
						break label46;

					case 5:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);

					default:
						break label46;
				}

			case 2:
				switch (side) {
					case 0:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
						break;

					case 1:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
						break;

					case 2:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
						break;

					case 3:
						left = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
						right = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
						break;

					case 4:
					case 5:
						return null;
				}
		}

		byte index1;

		if (left) {
			if (right) {
				index1 = 1;
			} else {
				index1 = 2;
			}
		} else if (right) {
			index1 = 0;
		} else {
			index1 = 3;
		}

		return cp.tileIcons[index1];
	}

	private static TextureAtlasSprite getConnectedTextureVertical(final ConnectedProperties cp,
			final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis,
			final int side, final TextureAtlasSprite icon, final int metadata) {
		boolean bottom = false;
		boolean top = false;

		switch (vertAxis) {
			case 0:
				if (side == 1 || side == 0) {
					return null;
				}

				bottom = isNeighbour(cp, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
				top = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
				break;

			case 1:
				if (side == 3 || side == 2) {
					return null;
				}

				bottom = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
				top = isNeighbour(cp, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
				break;

			case 2:
				if (side == 5 || side == 4) {
					return null;
				}

				bottom = isNeighbour(cp, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
				top = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
		}

		byte index1;

		if (bottom) {
			if (top) {
				index1 = 1;
			} else {
				index1 = 2;
			}
		} else if (top) {
			index1 = 0;
		} else {
			index1 = 3;
		}

		return cp.tileIcons[index1];
	}

	private static TextureAtlasSprite getConnectedTextureHorizontalVertical(final ConnectedProperties cp,
			final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis,
			final int side, final TextureAtlasSprite icon, final int metadata) {
		final TextureAtlasSprite[] tileIcons = cp.tileIcons;
		final TextureAtlasSprite iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis,
				side, icon, metadata);

		if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
			return iconH;
		} else {
			final TextureAtlasSprite iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos,
					vertAxis, side, icon, metadata);
			return iconV == tileIcons[0] ? tileIcons[4]
					: iconV == tileIcons[1] ? tileIcons[5] : iconV == tileIcons[2] ? tileIcons[6] : iconV;
		}
	}

	private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(final ConnectedProperties cp,
			final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis,
			final int side, final TextureAtlasSprite icon, final int metadata) {
		final TextureAtlasSprite[] tileIcons = cp.tileIcons;
		final TextureAtlasSprite iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis,
				side, icon, metadata);

		if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
			return iconV;
		} else {
			final TextureAtlasSprite iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos,
					vertAxis, side, icon, metadata);
			return iconH == tileIcons[0] ? tileIcons[4]
					: iconH == tileIcons[1] ? tileIcons[5] : iconH == tileIcons[2] ? tileIcons[6] : iconH;
		}
	}

	private static TextureAtlasSprite getConnectedTextureTop(final ConnectedProperties cp,
			final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis,
			final int side, final TextureAtlasSprite icon, final int metadata) {
		boolean top = false;

		switch (vertAxis) {
			case 0:
				if (side == 1 || side == 0) {
					return null;
				}

				top = isNeighbour(cp, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
				break;

			case 1:
				if (side == 3 || side == 2) {
					return null;
				}

				top = isNeighbour(cp, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
				break;

			case 2:
				if (side == 5 || side == 4) {
					return null;
				}

				top = isNeighbour(cp, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
		}

		return top ? cp.tileIcons[0] : null;
	}

	public static void updateIcons(final TextureMap textureMap) {
		blockProperties = null;
		tileProperties = null;
		spriteQuadMaps = null;

		if (Config.isConnectedTextures()) {
			final IResourcePack[] rps = Config.getResourcePacks();

			for (int locEmpty = rps.length - 1; locEmpty >= 0; --locEmpty) {
				final IResourcePack rp = rps[locEmpty];
				updateIcons(textureMap, rp);
			}

			updateIcons(textureMap, Config.getDefaultResourcePack());
			final ResourceLocation var4 = new ResourceLocation("mcpatcher/ctm/default/empty");
			emptySprite = textureMap.func_174942_a(var4);
			spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];

			if (blockProperties.length <= 0) {
				blockProperties = null;
			}

			if (tileProperties.length <= 0) {
				tileProperties = null;
			}
		}
	}

	public static void updateIcons(final TextureMap textureMap, final IResourcePack rp) {
		final String[] names = ResUtils.collectFiles(rp, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
		Arrays.sort(names);
		final List tileList = makePropertyList(tileProperties);
		final List blockList = makePropertyList(blockProperties);

		for (final String name : names) {
			Config.dbg("ConnectedTextures: " + name);

			try {
				final ResourceLocation e = new ResourceLocation(name);
				final InputStream in = rp.getInputStream(e);

				if (in == null) {
					Config.warn("ConnectedTextures file not found: " + name);
				} else {
					final Properties props = new Properties();
					props.load(in);
					final ConnectedProperties cp = new ConnectedProperties(props, name);

					if (cp.isValid(name)) {
						cp.updateIcons(textureMap);
						addToTileList(cp, tileList);
						addToBlockList(cp, blockList);
					}
				}
			} catch (final FileNotFoundException var11) {
				Config.warn("ConnectedTextures file not found: " + name);
			} catch (final Exception var12) {
				var12.printStackTrace();
			}
		}

		blockProperties = propertyListToArray(blockList);
		tileProperties = propertyListToArray(tileList);
		multipass = detectMultipass();
		Config.dbg("Multipass connected textures: " + multipass);
	}

	private static List makePropertyList(final ConnectedProperties[][] propsArr) {
		final ArrayList list = new ArrayList();

		if (propsArr != null) {
			for (final ConnectedProperties[] props : propsArr) {
				ArrayList propList = null;

				if (props != null) {
					propList = new ArrayList(Arrays.asList(props));
				}

				list.add(propList);
			}
		}

		return list;
	}

	private static boolean detectMultipass() {
		final ArrayList propList = new ArrayList();
		int props;
		ConnectedProperties[] matchIconSet;

		for (props = 0; props < tileProperties.length; ++props) {
			matchIconSet = tileProperties[props];

			if (matchIconSet != null) {
				propList.addAll(Arrays.asList(matchIconSet));
			}
		}

		for (props = 0; props < blockProperties.length; ++props) {
			matchIconSet = blockProperties[props];

			if (matchIconSet != null) {
				propList.addAll(Arrays.asList(matchIconSet));
			}
		}

		final ConnectedProperties[] var6 = (ConnectedProperties[]) propList
				.toArray(new ConnectedProperties[propList.size()]);
		final HashSet var7 = new HashSet();
		final HashSet tileIconSet = new HashSet();

		for (final ConnectedProperties cp : var6) {
			if (cp.matchTileIcons != null) {
				var7.addAll(Arrays.asList(cp.matchTileIcons));
			}

			if (cp.tileIcons != null) {
				tileIconSet.addAll(Arrays.asList(cp.tileIcons));
			}
		}

		var7.retainAll(tileIconSet);
		return !var7.isEmpty();
	}

	private static ConnectedProperties[][] propertyListToArray(final List list) {
		final ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];

		for (int i = 0; i < list.size(); ++i) {
			final List subList = (List) list.get(i);

			if (subList != null) {
				final ConnectedProperties[] subArr = (ConnectedProperties[]) subList
						.toArray(new ConnectedProperties[subList.size()]);
				propArr[i] = subArr;
			}
		}

		return propArr;
	}

	private static void addToTileList(final ConnectedProperties cp, final List tileList) {
		if (cp.matchTileIcons != null) {
			for (final TextureAtlasSprite matchTileIcon : cp.matchTileIcons) {
				final TextureAtlasSprite icon = matchTileIcon;

				if (!(icon instanceof TextureAtlasSprite)) {
					Config.warn(
							"TextureAtlasSprite is not TextureAtlasSprite: " + icon + ", name: " + icon.getIconName());
				} else {
					final int tileId = icon.getIndexInMap();

					if (tileId < 0) {
						Config.warn("Invalid tile ID: " + tileId + ", icon: " + icon.getIconName());
					} else {
						addToList(cp, tileList, tileId);
					}
				}
			}
		}
	}

	private static void addToBlockList(final ConnectedProperties cp, final List blockList) {
		if (cp.matchBlocks != null) {
			for (final MatchBlock matchBlock : cp.matchBlocks) {
				final int blockId = matchBlock.getBlockId();

				if (blockId < 0) {
					Config.warn("Invalid block ID: " + blockId);
				} else {
					addToList(cp, blockList, blockId);
				}
			}
		}
	}

	private static void addToList(final ConnectedProperties cp, final List list, final int id) {
		while (id >= list.size()) {
			list.add((Object) null);
		}

		Object subList = list.get(id);

		if (subList == null) {
			subList = new ArrayList();
			list.set(id, subList);
		}

		((List) subList).add(cp);
	}

	private static String[] getDefaultCtmPaths() {
		final ArrayList list = new ArrayList();
		final String defPath = "mcpatcher/ctm/default/";

		if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
			list.add(defPath + "glass.properties");
			list.add(defPath + "glasspane.properties");
		}

		if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
			list.add(defPath + "bookshelf.properties");
		}

		if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
			list.add(defPath + "sandstone.properties");
		}

		final String[] colors = new String[] { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
				"gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };

		for (int paths = 0; paths < colors.length; ++paths) {
			final String color = colors[paths];

			if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + color + ".png"))) {
				list.add(defPath + paths + "_glass_" + color + "/glass_" + color + ".properties");
				list.add(defPath + paths + "_glass_" + color + "/glass_pane_" + color + ".properties");
			}
		}

		final String[] var5 = (String[]) list.toArray(new String[list.size()]);
		return var5;
	}

	public static int getPaneTextureIndex(final boolean linkP, final boolean linkN, final boolean linkYp,
			final boolean linkYn) {
		return linkN && linkP ? linkYp ? linkYn ? 34 : 50 : linkYn ? 18 : 2
				: linkN && !linkP ? linkYp ? linkYn ? 35 : 51 : linkYn ? 19 : 3
						: !linkN && linkP ? linkYp ? linkYn ? 33 : 49 : linkYn ? 17 : 1
								: linkYp ? linkYn ? 32 : 48 : linkYn ? 16 : 0;
	}

	public static int getReversePaneTextureIndex(final int texNum) {
		final int col = texNum % 16;
		return col == 1 ? texNum + 2 : col == 3 ? texNum - 2 : texNum;
	}

	public static TextureAtlasSprite getCtmTexture(final ConnectedProperties cp, final int ctmIndex,
			final TextureAtlasSprite icon) {
		if (cp.method != 1) {
			return icon;
		} else if (ctmIndex >= 0 && ctmIndex < ctmIndexes.length) {
			final int index = ctmIndexes[ctmIndex];
			final TextureAtlasSprite[] ctmIcons = cp.tileIcons;
			return index >= 0 && index < ctmIcons.length ? ctmIcons[index] : icon;
		} else {
			return icon;
		}
	}

	static class NamelessClass379831726 {
		static final int[] $SwitchMap$net$minecraft$util$EnumFacing = new int[EnumFacing.values().length];

		static {
			try {
				$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.DOWN.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.UP.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.EAST.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.WEST.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.NORTH.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.SOUTH.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
