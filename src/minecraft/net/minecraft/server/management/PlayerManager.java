package net.minecraft.server.management;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class PlayerManager {

public static final int EaZy = 1539;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_152627_a = LogManager.getLogger();
	private final WorldServer theWorldServer;

	/** players in the current instance */
	private final List players = Lists.newArrayList();

	/** the hash of all playerInstances created */
	private final LongHashMap playerInstances = new LongHashMap();

	/** the playerInstances(chunks) that need to be updated */
	private final List playerInstancesToUpdate = Lists.newArrayList();

	/** This field is using when chunk should be processed (every 8000 ticks) */
	private final List playerInstanceList = Lists.newArrayList();

	/**
	 * Number of chunks the server sends to the client. Valid 3<=x<=15. In
	 * server.properties.
	 */
	private int playerViewRadius;

	/** time what is using to check if InhabitedTime should be calculated */
	private long previousTotalWorldTime;

	/** x, z direction vectors: east, south, west, north */
	private final int[][] xzDirectionsConst = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
	// private static final String __OBFID = "http://https://fuckuskid00001434";

	public PlayerManager(final WorldServer p_i1176_1_) {
		theWorldServer = p_i1176_1_;
		func_152622_a(p_i1176_1_.func_73046_m().getConfigurationManager().getViewDistance());
	}

	/**
	 * Returns the MinecraftServer associated with the PlayerManager.
	 */
	public WorldServer getMinecraftServer() {
		return theWorldServer;
	}

	/**
	 * updates all the player instances that need to be updated
	 */
	public void updatePlayerInstances() {
		final long var1 = theWorldServer.getTotalWorldTime();
		int var3;
		PlayerManager.PlayerInstance var4;

		if (var1 - previousTotalWorldTime > 8000L) {
			previousTotalWorldTime = var1;

			for (var3 = 0; var3 < playerInstanceList.size(); ++var3) {
				var4 = (PlayerManager.PlayerInstance) playerInstanceList.get(var3);
				var4.onUpdate();
				var4.processChunk();
			}
		} else {
			for (var3 = 0; var3 < playerInstancesToUpdate.size(); ++var3) {
				var4 = (PlayerManager.PlayerInstance) playerInstancesToUpdate.get(var3);
				var4.onUpdate();
			}
		}

		playerInstancesToUpdate.clear();

		if (players.isEmpty()) {
			final WorldProvider var5 = theWorldServer.provider;

			if (!var5.canRespawnHere()) {
				theWorldServer.theChunkProviderServer.unloadAllChunks();
			}
		}
	}

	public boolean func_152621_a(final int p_152621_1_, final int p_152621_2_) {
		final long var3 = p_152621_1_ + 2147483647L | p_152621_2_ + 2147483647L << 32;
		return playerInstances.getValueByKey(var3) != null;
	}

	/**
	 * passi n the chunk x and y and a flag as to whether or not the instance
	 * should be made if it doesnt exist
	 */
	private PlayerManager.PlayerInstance getPlayerInstance(final int p_72690_1_, final int p_72690_2_,
			final boolean p_72690_3_) {
		final long var4 = p_72690_1_ + 2147483647L | p_72690_2_ + 2147483647L << 32;
		PlayerManager.PlayerInstance var6 = (PlayerManager.PlayerInstance) playerInstances.getValueByKey(var4);

		if (var6 == null && p_72690_3_) {
			var6 = new PlayerManager.PlayerInstance(p_72690_1_, p_72690_2_);
			playerInstances.add(var4, var6);
			playerInstanceList.add(var6);
		}

		return var6;
	}

	public void func_180244_a(final BlockPos p_180244_1_) {
		final int var2 = p_180244_1_.getX() >> 4;
		final int var3 = p_180244_1_.getZ() >> 4;
		final PlayerManager.PlayerInstance var4 = getPlayerInstance(var2, var3, false);

		if (var4 != null) {
			var4.flagChunkForUpdate(p_180244_1_.getX() & 15, p_180244_1_.getY(), p_180244_1_.getZ() & 15);
		}
	}

	/**
	 * Adds an EntityPlayerMP to the PlayerManager and to all player instances
	 * within player visibility
	 */
	public void addPlayer(final EntityPlayerMP p_72683_1_) {
		final int var2 = (int) p_72683_1_.posX >> 4;
		final int var3 = (int) p_72683_1_.posZ >> 4;
		p_72683_1_.managedPosX = p_72683_1_.posX;
		p_72683_1_.managedPosZ = p_72683_1_.posZ;

		for (int var4 = var2 - playerViewRadius; var4 <= var2 + playerViewRadius; ++var4) {
			for (int var5 = var3 - playerViewRadius; var5 <= var3 + playerViewRadius; ++var5) {
				getPlayerInstance(var4, var5, true).addPlayer(p_72683_1_);
			}
		}

		players.add(p_72683_1_);
		filterChunkLoadQueue(p_72683_1_);
	}

	/**
	 * Removes all chunks from the given player's chunk load queue that are not
	 * in viewing range of the player.
	 */
	public void filterChunkLoadQueue(final EntityPlayerMP p_72691_1_) {
		final ArrayList var2 = Lists.newArrayList(p_72691_1_.loadedChunks);
		int var3 = 0;
		final int var4 = playerViewRadius;
		final int var5 = (int) p_72691_1_.posX >> 4;
		final int var6 = (int) p_72691_1_.posZ >> 4;
		int var7 = 0;
		int var8 = 0;
		ChunkCoordIntPair var9 = getPlayerInstance(var5, var6, true).currentChunk;
		p_72691_1_.loadedChunks.clear();

		if (var2.contains(var9)) {
			p_72691_1_.loadedChunks.add(var9);
		}

		int var10;

		for (var10 = 1; var10 <= var4 * 2; ++var10) {
			for (int var11 = 0; var11 < 2; ++var11) {
				final int[] var12 = xzDirectionsConst[var3++ % 4];

				for (int var13 = 0; var13 < var10; ++var13) {
					var7 += var12[0];
					var8 += var12[1];
					var9 = getPlayerInstance(var5 + var7, var6 + var8, true).currentChunk;

					if (var2.contains(var9)) {
						p_72691_1_.loadedChunks.add(var9);
					}
				}
			}
		}

		var3 %= 4;

		for (var10 = 0; var10 < var4 * 2; ++var10) {
			var7 += xzDirectionsConst[var3][0];
			var8 += xzDirectionsConst[var3][1];
			var9 = getPlayerInstance(var5 + var7, var6 + var8, true).currentChunk;

			if (var2.contains(var9)) {
				p_72691_1_.loadedChunks.add(var9);
			}
		}
	}

	/**
	 * Removes an EntityPlayerMP from the PlayerManager.
	 */
	public void removePlayer(final EntityPlayerMP p_72695_1_) {
		final int var2 = (int) p_72695_1_.managedPosX >> 4;
		final int var3 = (int) p_72695_1_.managedPosZ >> 4;

		for (int var4 = var2 - playerViewRadius; var4 <= var2 + playerViewRadius; ++var4) {
			for (int var5 = var3 - playerViewRadius; var5 <= var3 + playerViewRadius; ++var5) {
				final PlayerManager.PlayerInstance var6 = getPlayerInstance(var4, var5, false);

				if (var6 != null) {
					var6.removePlayer(p_72695_1_);
				}
			}
		}

		players.remove(p_72695_1_);
	}

	/**
	 * Determine if two rectangles centered at the given points overlap for the
	 * provided radius. Arguments: x1, z1, x2, z2, radius.
	 */
	private boolean overlaps(final int p_72684_1_, final int p_72684_2_, final int p_72684_3_, final int p_72684_4_,
			final int p_72684_5_) {
		final int var6 = p_72684_1_ - p_72684_3_;
		final int var7 = p_72684_2_ - p_72684_4_;
		return var6 >= -p_72684_5_ && var6 <= p_72684_5_ ? var7 >= -p_72684_5_ && var7 <= p_72684_5_ : false;
	}

	/**
	 * update chunks around a player being moved by server logic (e.g. cart,
	 * boat)
	 */
	public void updateMountedMovingPlayer(final EntityPlayerMP p_72685_1_) {
		final int var2 = (int) p_72685_1_.posX >> 4;
		final int var3 = (int) p_72685_1_.posZ >> 4;
		final double var4 = p_72685_1_.managedPosX - p_72685_1_.posX;
		final double var6 = p_72685_1_.managedPosZ - p_72685_1_.posZ;
		final double var8 = var4 * var4 + var6 * var6;

		if (var8 >= 64.0D) {
			final int var10 = (int) p_72685_1_.managedPosX >> 4;
			final int var11 = (int) p_72685_1_.managedPosZ >> 4;
			final int var12 = playerViewRadius;
			final int var13 = var2 - var10;
			final int var14 = var3 - var11;

			if (var13 != 0 || var14 != 0) {
				for (int var15 = var2 - var12; var15 <= var2 + var12; ++var15) {
					for (int var16 = var3 - var12; var16 <= var3 + var12; ++var16) {
						if (!overlaps(var15, var16, var10, var11, var12)) {
							getPlayerInstance(var15, var16, true).addPlayer(p_72685_1_);
						}

						if (!overlaps(var15 - var13, var16 - var14, var2, var3, var12)) {
							final PlayerManager.PlayerInstance var17 = getPlayerInstance(var15 - var13, var16 - var14,
									false);

							if (var17 != null) {
								var17.removePlayer(p_72685_1_);
							}
						}
					}
				}

				filterChunkLoadQueue(p_72685_1_);
				p_72685_1_.managedPosX = p_72685_1_.posX;
				p_72685_1_.managedPosZ = p_72685_1_.posZ;
			}
		}
	}

	public boolean isPlayerWatchingChunk(final EntityPlayerMP p_72694_1_, final int p_72694_2_, final int p_72694_3_) {
		final PlayerManager.PlayerInstance var4 = getPlayerInstance(p_72694_2_, p_72694_3_, false);
		return var4 != null && var4.playersWatchingChunk.contains(p_72694_1_)
				&& !p_72694_1_.loadedChunks.contains(var4.currentChunk);
	}

	public void func_152622_a(int p_152622_1_) {
		p_152622_1_ = MathHelper.clamp_int(p_152622_1_, 3, 32);

		if (p_152622_1_ != playerViewRadius) {
			final int var2 = p_152622_1_ - playerViewRadius;
			final ArrayList var3 = Lists.newArrayList(players);
			final Iterator var4 = var3.iterator();

			while (var4.hasNext()) {
				final EntityPlayerMP var5 = (EntityPlayerMP) var4.next();
				final int var6 = (int) var5.posX >> 4;
				final int var7 = (int) var5.posZ >> 4;
				int var8;
				int var9;

				if (var2 > 0) {
					for (var8 = var6 - p_152622_1_; var8 <= var6 + p_152622_1_; ++var8) {
						for (var9 = var7 - p_152622_1_; var9 <= var7 + p_152622_1_; ++var9) {
							final PlayerManager.PlayerInstance var10 = getPlayerInstance(var8, var9, true);

							if (!var10.playersWatchingChunk.contains(var5)) {
								var10.addPlayer(var5);
							}
						}
					}
				} else {
					for (var8 = var6 - playerViewRadius; var8 <= var6 + playerViewRadius; ++var8) {
						for (var9 = var7 - playerViewRadius; var9 <= var7 + playerViewRadius; ++var9) {
							if (!overlaps(var8, var9, var6, var7, p_152622_1_)) {
								getPlayerInstance(var8, var9, true).removePlayer(var5);
							}
						}
					}
				}
			}

			playerViewRadius = p_152622_1_;
		}
	}

	/**
	 * Get the furthest viewable block given player's view distance
	 */
	public static int getFurthestViewableBlock(final int p_72686_0_) {
		return p_72686_0_ * 16 - 16;
	}

	class PlayerInstance {
		private final List playersWatchingChunk = Lists.newArrayList();
		private final ChunkCoordIntPair currentChunk;
		private final short[] locationOfBlockChange = new short[64];
		private int numBlocksToUpdate;
		private int flagsYAreasToUpdate;
		private long previousWorldTime;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001435";

		public PlayerInstance(final int p_i1518_2_, final int p_i1518_3_) {
			currentChunk = new ChunkCoordIntPair(p_i1518_2_, p_i1518_3_);
			getMinecraftServer().theChunkProviderServer.loadChunk(p_i1518_2_, p_i1518_3_);
		}

		public void addPlayer(final EntityPlayerMP p_73255_1_) {
			if (playersWatchingChunk.contains(p_73255_1_)) {
				PlayerManager.field_152627_a.debug("Failed to add player. {} already is in chunk {}, {}", new Object[] {
						p_73255_1_, currentChunk.chunkXPos, currentChunk.chunkZPos});
			} else {
				if (playersWatchingChunk.isEmpty()) {
					previousWorldTime = theWorldServer.getTotalWorldTime();
				}

				playersWatchingChunk.add(p_73255_1_);
				p_73255_1_.loadedChunks.add(currentChunk);
			}
		}

		public void removePlayer(final EntityPlayerMP p_73252_1_) {
			if (playersWatchingChunk.contains(p_73252_1_)) {
				final Chunk var2 = theWorldServer.getChunkFromChunkCoords(currentChunk.chunkXPos,
						currentChunk.chunkZPos);

				if (var2.isPopulated()) {
					p_73252_1_.playerNetServerHandler.sendPacket(new S21PacketChunkData(var2, true, 0));
				}

				playersWatchingChunk.remove(p_73252_1_);
				p_73252_1_.loadedChunks.remove(currentChunk);

				if (playersWatchingChunk.isEmpty()) {
					final long var3 = currentChunk.chunkXPos + 2147483647L | currentChunk.chunkZPos + 2147483647L << 32;
					increaseInhabitedTime(var2);
					playerInstances.remove(var3);
					playerInstanceList.remove(this);

					if (numBlocksToUpdate > 0) {
						playerInstancesToUpdate.remove(this);
					}

					getMinecraftServer().theChunkProviderServer.dropChunk(currentChunk.chunkXPos,
							currentChunk.chunkZPos);
				}
			}
		}

		public void processChunk() {
			increaseInhabitedTime(
					theWorldServer.getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPos));
		}

		private void increaseInhabitedTime(final Chunk p_111196_1_) {
			p_111196_1_.setInhabitedTime(
					p_111196_1_.getInhabitedTime() + theWorldServer.getTotalWorldTime() - previousWorldTime);
			previousWorldTime = theWorldServer.getTotalWorldTime();
		}

		public void flagChunkForUpdate(final int p_151253_1_, final int p_151253_2_, final int p_151253_3_) {
			if (numBlocksToUpdate == 0) {
				playerInstancesToUpdate.add(this);
			}

			flagsYAreasToUpdate |= 1 << (p_151253_2_ >> 4);

			if (numBlocksToUpdate < 64) {
				final short var4 = (short) (p_151253_1_ << 12 | p_151253_3_ << 8 | p_151253_2_);

				for (int var5 = 0; var5 < numBlocksToUpdate; ++var5) {
					if (locationOfBlockChange[var5] == var4) {
						return;
					}
				}

				locationOfBlockChange[numBlocksToUpdate++] = var4;
			}
		}

		public void sendToAllPlayersWatchingChunk(final Packet p_151251_1_) {
			for (int var2 = 0; var2 < playersWatchingChunk.size(); ++var2) {
				final EntityPlayerMP var3 = (EntityPlayerMP) playersWatchingChunk.get(var2);

				if (!var3.loadedChunks.contains(currentChunk)) {
					var3.playerNetServerHandler.sendPacket(p_151251_1_);
				}
			}
		}

		public void onUpdate() {
			if (numBlocksToUpdate != 0) {
				int var1;
				int var2;
				int var3;

				if (numBlocksToUpdate == 1) {
					var1 = (locationOfBlockChange[0] >> 12 & 15) + currentChunk.chunkXPos * 16;
					var2 = locationOfBlockChange[0] & 255;
					var3 = (locationOfBlockChange[0] >> 8 & 15) + currentChunk.chunkZPos * 16;
					final BlockPos var4 = new BlockPos(var1, var2, var3);
					sendToAllPlayersWatchingChunk(new S23PacketBlockChange(theWorldServer, var4));

					if (theWorldServer.getBlockState(var4).getBlock().hasTileEntity()) {
						sendTileToAllPlayersWatchingChunk(theWorldServer.getTileEntity(var4));
					}
				} else {
					int var7;

					if (numBlocksToUpdate == 64) {
						var1 = currentChunk.chunkXPos * 16;
						var2 = currentChunk.chunkZPos * 16;
						sendToAllPlayersWatchingChunk(new S21PacketChunkData(
								theWorldServer.getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPos),
								false, flagsYAreasToUpdate));

						for (var3 = 0; var3 < 16; ++var3) {
							if ((flagsYAreasToUpdate & 1 << var3) != 0) {
								var7 = var3 << 4;
								final List var5 = theWorldServer.func_147486_a(var1, var7, var2, var1 + 16, var7 + 16,
										var2 + 16);

								for (int var6 = 0; var6 < var5.size(); ++var6) {
									sendTileToAllPlayersWatchingChunk((TileEntity) var5.get(var6));
								}
							}
						}
					} else {
						sendToAllPlayersWatchingChunk(
								new S22PacketMultiBlockChange(numBlocksToUpdate, locationOfBlockChange, theWorldServer
										.getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPos)));

						for (var1 = 0; var1 < numBlocksToUpdate; ++var1) {
							var2 = (locationOfBlockChange[var1] >> 12 & 15) + currentChunk.chunkXPos * 16;
							var3 = locationOfBlockChange[var1] & 255;
							var7 = (locationOfBlockChange[var1] >> 8 & 15) + currentChunk.chunkZPos * 16;
							final BlockPos var8 = new BlockPos(var2, var3, var7);

							if (theWorldServer.getBlockState(var8).getBlock().hasTileEntity()) {
								sendTileToAllPlayersWatchingChunk(theWorldServer.getTileEntity(var8));
							}
						}
					}
				}

				numBlocksToUpdate = 0;
				flagsYAreasToUpdate = 0;
			}
		}

		private void sendTileToAllPlayersWatchingChunk(final TileEntity p_151252_1_) {
			if (p_151252_1_ != null) {
				final Packet var2 = p_151252_1_.getDescriptionPacket();

				if (var2 != null) {
					sendToAllPlayersWatchingChunk(var2);
				}
			}
		}
	}
}
