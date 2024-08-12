package optifine;

import net.minecraft.block.state.BlockStateBase;

public class MatchBlock {

public static final int EaZy = 1929;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int blockId = -1;
	private int[] metadatas = null;

	public MatchBlock(final int blockId) {
		this.blockId = blockId;
	}

	public MatchBlock(final int blockId, final int metadata) {
		this.blockId = blockId;

		if (metadata >= 0 && metadata <= 15) {
			metadatas = new int[] { metadata };
		}
	}

	public MatchBlock(final int blockId, final int[] metadatas) {
		this.blockId = blockId;
		this.metadatas = metadatas;
	}

	public int getBlockId() {
		return blockId;
	}

	public int[] getMetadatas() {
		return metadatas;
	}

	public boolean matches(final BlockStateBase blockState) {
		return blockState.getBlockId() != blockId ? false : Matches.metadata(blockState.getMetadata(), metadatas);
	}

	public boolean matches(final int id, final int metadata) {
		return id != blockId ? false : Matches.metadata(metadata, metadatas);
	}

	public void addMetadata(final int metadata) {
		if (metadatas != null) {
			if (metadata >= 0 && metadata <= 15) {
				for (final int metadata2 : metadatas) {
					if (metadata2 == metadata) {
						return;
					}
				}

				metadatas = Config.addIntToArray(metadatas, metadata);
			}
		}
	}

	@Override
	public String toString() {
		return "" + blockId + ":" + Config.arrayToString(metadatas);
	}
}
