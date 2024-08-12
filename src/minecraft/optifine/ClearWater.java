package optifine;

import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;

public class ClearWater {

public static final int EaZy = 1881;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static void updateWaterOpacity(final GameSettings settings, final World world) {
		if (settings != null) {
			byte cp = 3;

			if (settings.ofClearWater) {
				cp = 1;
			}

			BlockLeavesBase.setLightOpacity(Blocks.water, cp);
			BlockLeavesBase.setLightOpacity(Blocks.flowing_water, cp);
		}

		if (world != null) {
			final IChunkProvider var25 = world.getChunkProvider();

			if (var25 != null) {
				final Entity rve = Config.getMinecraft().func_175606_aa();

				if (rve != null) {
					final int cViewX = (int) rve.posX / 16;
					final int cViewZ = (int) rve.posZ / 16;
					final int cXMin = cViewX - 512;
					final int cXMax = cViewX + 512;
					final int cZMin = cViewZ - 512;
					final int cZMax = cViewZ + 512;
					int countUpdated = 0;

					for (int threadName = cXMin; threadName < cXMax; ++threadName) {
						for (int cz = cZMin; cz < cZMax; ++cz) {
							if (var25.chunkExists(threadName, cz)) {
								final Chunk c = var25.provideChunk(threadName, cz);

								if (c != null && !(c instanceof EmptyChunk)) {
									final int x0 = threadName << 4;
									final int z0 = cz << 4;
									final int x1 = x0 + 16;
									final int z1 = z0 + 16;
									final BlockPosM posXZ = new BlockPosM(0, 0, 0);
									final BlockPosM posXYZ = new BlockPosM(0, 0, 0);

									for (int x = x0; x < x1; ++x) {
										int z = z0;

										while (z < z1) {
											posXZ.setXyz(x, 0, z);
											final BlockPos posH = world.func_175725_q(posXZ);
											int y = 0;

											while (true) {
												if (y < posH.getY()) {
													posXYZ.setXyz(x, y, z);
													final IBlockState bs = world.getBlockState(posXYZ);

													if (bs.getBlock().getMaterial() != Material.water) {
														++y;
														continue;
													}

													world.markBlocksDirtyVertical(x, z, posXYZ.getY(), posH.getY());
													++countUpdated;
												}

												++z;
												break;
											}
										}
									}
								}
							}
						}
					}

					if (countUpdated > 0) {
						String var26 = "server";

						if (Config.isMinecraftThread()) {
							var26 = "client";
						}

						Config.dbg("ClearWater (" + var26 + ") relighted " + countUpdated + " chunks");
					}
				}
			}
		}
	}
}
