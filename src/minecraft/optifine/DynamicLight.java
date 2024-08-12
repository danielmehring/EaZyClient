package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DynamicLight {

public static final int EaZy = 1897;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private Entity entity = null;
	private double offsetY = 0.0D;
	private double lastPosX = -2.147483648E9D;
	private double lastPosY = -2.147483648E9D;
	private double lastPosZ = -2.147483648E9D;
	private int lastLightLevel = 0;
	private boolean underwater = false;
	private long timeCheckMs = 0L;
	private Set<BlockPos> setLitChunkPos = new HashSet();
	private final BlockPosM blockPosMutable = new BlockPosM(0, 0, 0);

	public DynamicLight(final Entity entity) {
		this.entity = entity;
		offsetY = entity.getEyeHeight();
	}

	public void update(final RenderGlobal renderGlobal) {
		if (Config.isDynamicLightsFast()) {
			final long posX = System.currentTimeMillis();

			if (posX < timeCheckMs + 500L) {
				return;
			}

			timeCheckMs = posX;
		}

		final double posX1 = entity.posX - 0.5D;
		final double posY = entity.posY - 0.5D + offsetY;
		final double posZ = entity.posZ - 0.5D;
		final int lightLevel = DynamicLights.getLightLevel(entity);
		final double dx = posX1 - lastPosX;
		final double dy = posY - lastPosY;
		final double dz = posZ - lastPosZ;
		final double delta = 0.1D;

		if (Math.abs(dx) > delta || Math.abs(dy) > delta || Math.abs(dz) > delta || lastLightLevel != lightLevel) {
			lastPosX = posX1;
			lastPosY = posY;
			lastPosZ = posZ;
			lastLightLevel = lightLevel;
			underwater = false;
			final WorldClient world = renderGlobal.getWorld();

			if (world != null) {
				blockPosMutable.setXyz(MathHelper.floor_double(posX1), MathHelper.floor_double(posY),
						MathHelper.floor_double(posZ));
				final IBlockState setNewPos = world.getBlockState(blockPosMutable);
				final Block dirX = setNewPos.getBlock();
				underwater = dirX == Blocks.water;
			}

			final HashSet setNewPos1 = new HashSet();

			if (lightLevel > 0) {
				final EnumFacing dirX1 = (MathHelper.floor_double(posX1) & 15) >= 8 ? EnumFacing.EAST : EnumFacing.WEST;
				final EnumFacing dirY = (MathHelper.floor_double(posY) & 15) >= 8 ? EnumFacing.UP : EnumFacing.DOWN;
				final EnumFacing dirZ = (MathHelper.floor_double(posZ) & 15) >= 8 ? EnumFacing.SOUTH : EnumFacing.NORTH;
				final BlockPos pos = new BlockPos(posX1, posY, posZ);
				final RenderChunk chunkView = renderGlobal.getRenderChunk(pos);
				final RenderChunk chunkX = renderGlobal.getRenderChunk(chunkView, dirX1);
				final RenderChunk chunkZ = renderGlobal.getRenderChunk(chunkView, dirZ);
				final RenderChunk chunkXZ = renderGlobal.getRenderChunk(chunkX, dirZ);
				final RenderChunk chunkY = renderGlobal.getRenderChunk(chunkView, dirY);
				final RenderChunk chunkYX = renderGlobal.getRenderChunk(chunkY, dirX1);
				final RenderChunk chunkYZ = renderGlobal.getRenderChunk(chunkY, dirZ);
				final RenderChunk chunkYXZ = renderGlobal.getRenderChunk(chunkYX, dirZ);
				updateChunkLight(chunkView, setLitChunkPos, setNewPos1);
				updateChunkLight(chunkX, setLitChunkPos, setNewPos1);
				updateChunkLight(chunkZ, setLitChunkPos, setNewPos1);
				updateChunkLight(chunkXZ, setLitChunkPos, setNewPos1);
				updateChunkLight(chunkY, setLitChunkPos, setNewPos1);
				updateChunkLight(chunkYX, setLitChunkPos, setNewPos1);
				updateChunkLight(chunkYZ, setLitChunkPos, setNewPos1);
				updateChunkLight(chunkYXZ, setLitChunkPos, setNewPos1);
			}

			updateLitChunks(renderGlobal);
			setLitChunkPos = setNewPos1;
		}
	}

	private void updateChunkLight(final RenderChunk renderChunk, final Set<BlockPos> setPrevPos,
			final Set<BlockPos> setNewPos) {
		if (renderChunk != null) {
			final CompiledChunk compiledChunk = renderChunk.func_178571_g();

			if (compiledChunk != null && !compiledChunk.func_178489_a()) {
				renderChunk.func_178575_a(true);
			}

			final BlockPos pos = renderChunk.func_178568_j();

			if (setPrevPos != null) {
				setPrevPos.remove(pos);
			}

			if (setNewPos != null) {
				setNewPos.add(pos);
			}
		}
	}

	public void updateLitChunks(final RenderGlobal renderGlobal) {
		final Iterator it = setLitChunkPos.iterator();

		while (it.hasNext()) {
			final BlockPos posOld = (BlockPos) it.next();
			final RenderChunk chunkOld = renderGlobal.getRenderChunk(posOld);
			updateChunkLight(chunkOld, (Set) null, (Set) null);
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public double getLastPosX() {
		return lastPosX;
	}

	public double getLastPosY() {
		return lastPosY;
	}

	public double getLastPosZ() {
		return lastPosZ;
	}

	public int getLastLightLevel() {
		return lastLightLevel;
	}

	public boolean isUnderwater() {
		return underwater;
	}

	public double getOffsetY() {
		return offsetY;
	}

	@Override
	public String toString() {
		return "Entity: " + entity + ", offsetY: " + offsetY;
	}
}
