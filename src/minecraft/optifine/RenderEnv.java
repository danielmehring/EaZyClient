package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import java.util.BitSet;
import java.util.List;

public class RenderEnv {

public static final int EaZy = 1961;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private IBlockState blockState;
	private final GameSettings gameSettings;
	private int blockId = -1;
	private int metadata = -1;
	private int breakingAnimation = -1;
	private final float[] quadBounds;
	private final BitSet boundsFlags;
	private final BlockModelRenderer.AmbientOcclusionFace aoFace;
	private BlockPosM colorizerBlockPosM;
	private boolean[] borderFlags;
	private static ThreadLocal threadLocalInstance = new ThreadLocal();

	private RenderEnv(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
		quadBounds = new float[EnumFacing.VALUES.length * 2];
		boundsFlags = new BitSet(3);
		aoFace = new BlockModelRenderer.AmbientOcclusionFace();
		colorizerBlockPosM = null;
		borderFlags = null;
		this.blockState = blockState;
		gameSettings = Config.getGameSettings();
	}

	public static RenderEnv getInstance(final IBlockAccess blockAccessIn, final IBlockState blockStateIn,
			final BlockPos blockPosIn) {
		RenderEnv re = (RenderEnv) threadLocalInstance.get();

		if (re == null) {
			re = new RenderEnv(blockAccessIn, blockStateIn, blockPosIn);
			threadLocalInstance.set(re);
			return re;
		} else {
			re.reset(blockAccessIn, blockStateIn, blockPosIn);
			return re;
		}
	}

	private void reset(final IBlockAccess blockAccessIn, final IBlockState blockStateIn, final BlockPos blockPosIn) {
		blockState = blockStateIn;
		blockId = -1;
		metadata = -1;
		breakingAnimation = -1;
		boundsFlags.clear();
	}

	public int getBlockId() {
		if (blockId < 0) {
			if (blockState instanceof BlockStateBase) {
				final BlockStateBase bsb = (BlockStateBase) blockState;
				blockId = bsb.getBlockId();
			} else {
				blockId = Block.getIdFromBlock(blockState.getBlock());
			}
		}

		return blockId;
	}

	public int getMetadata() {
		if (metadata < 0) {
			if (blockState instanceof BlockStateBase) {
				final BlockStateBase bsb = (BlockStateBase) blockState;
				metadata = bsb.getMetadata();
			} else {
				metadata = blockState.getBlock().getMetaFromState(blockState);
			}
		}

		return metadata;
	}

	public float[] getQuadBounds() {
		return quadBounds;
	}

	public BitSet getBoundsFlags() {
		return boundsFlags;
	}

	public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
		return aoFace;
	}

	public boolean isBreakingAnimation(final List listQuads) {
		if (breakingAnimation < 0 && listQuads.size() > 0) {
			if (listQuads.get(0) instanceof BreakingFour) {
				breakingAnimation = 1;
			} else {
				breakingAnimation = 0;
			}
		}

		return breakingAnimation == 1;
	}

	public boolean isBreakingAnimation(final BakedQuad quad) {
		if (breakingAnimation < 0) {
			if (quad instanceof BreakingFour) {
				breakingAnimation = 1;
			} else {
				breakingAnimation = 0;
			}
		}

		return breakingAnimation == 1;
	}

	public boolean isBreakingAnimation() {
		return breakingAnimation == 1;
	}

	public IBlockState getBlockState() {
		return blockState;
	}

	public BlockPosM getColorizerBlockPosM() {
		if (colorizerBlockPosM == null) {
			colorizerBlockPosM = new BlockPosM(0, 0, 0);
		}

		return colorizerBlockPosM;
	}

	public boolean[] getBorderFlags() {
		if (borderFlags == null) {
			borderFlags = new boolean[4];
		}

		return borderFlags;
	}
}
