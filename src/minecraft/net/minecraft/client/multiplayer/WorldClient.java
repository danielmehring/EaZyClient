package net.minecraft.client.multiplayer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityFireworkStarterFX;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

import com.google.common.collect.Sets;

import optifine.BlockPosM;
import optifine.Config;
import optifine.DynamicLights;
import optifine.PlayerControllerOF;
import optifine.Reflector;

public class WorldClient extends World {

public static final int EaZy = 626;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static boolean showBarrier = false;
	/** The packets that need to be sent to the server. */
	private final NetHandlerPlayClient sendQueue;

	/** The ChunkProviderClient instance */
	private ChunkProviderClient clientChunkProvider;

	/** Contains all entities for this client, both spawned and non-spawned. */
	private final Set entityList = Sets.newHashSet();

	/**
	 * Contains all entities for this client that were not spawned due to a
	 * non-present chunk. The game will attempt to spawn up to 10 pending
	 * entities with each subsequent tick until the spawn queue is empty.
	 */
	private final Set entitySpawnQueue = Sets.newHashSet();
	private final Minecraft mc = Minecraft.getMinecraft();
	private final Set previousActiveChunkSet = Sets.newHashSet();
	// private static final String __OBFID = "http://https://fuckuskid00000882";
	private final BlockPosM randomTickPosM = new BlockPosM(0, 0, 0, 3);
	private boolean playerUpdate = false;

	public WorldClient(final NetHandlerPlayClient p_i45063_1_, final WorldSettings p_i45063_2_, final int p_i45063_3_,
			final EnumDifficulty p_i45063_4_, final Profiler p_i45063_5_) {
		super(new SaveHandlerMP(), new WorldInfo(p_i45063_2_, "MpServer"),
				WorldProvider.getProviderForDimension(p_i45063_3_), p_i45063_5_, true);
		sendQueue = p_i45063_1_;
		getWorldInfo().setDifficulty(p_i45063_4_);
		provider.registerWorld(this);
		setSpawnLocation(new BlockPos(8, 64, 8));
		chunkProvider = createChunkProvider();
		mapStorage = new SaveDataMemoryStorage();
		calculateInitialSkylight();
		calculateInitialWeather();
		Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { this });

		if (Minecraft.playerController != null && Minecraft.playerController.getClass() == PlayerControllerMP.class) {
			Minecraft.playerController = new PlayerControllerOF(mc, p_i45063_1_);
		}
	}

	/**
	 * Runs a single tick for the world
	 */
	@Override
	public void tick() {
		super.tick();
		func_82738_a(getTotalWorldTime() + 1L);

		if (getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
			setWorldTime(getWorldTime() + 1L);
		}

		theProfiler.startSection("reEntryProcessing");

		for (int var1 = 0; var1 < 10 && !entitySpawnQueue.isEmpty(); ++var1) {
			final Entity var2 = (Entity) entitySpawnQueue.iterator().next();
			entitySpawnQueue.remove(var2);

			if (!loadedEntityList.contains(var2)) {
				spawnEntityInWorld(var2);
			}
		}

		theProfiler.endStartSection("chunkCache");
		clientChunkProvider.unloadQueuedChunks();
		theProfiler.endStartSection("blocks");
		func_147456_g();
		theProfiler.endSection();
	}

	/**
	 * Invalidates an AABB region of blocks from the receive queue, in the event
	 * that the block has been modified client-side in the intervening 80
	 * receive ticks.
	 */
	public void invalidateBlockReceiveRegion(final int p_73031_1_, final int p_73031_2_, final int p_73031_3_,
			final int p_73031_4_, final int p_73031_5_, final int p_73031_6_) {}

	/**
	 * Creates the chunk provider for this world. Called in the constructor.
	 * Retrieves provider from worldProvider?
	 */
	@Override
	protected IChunkProvider createChunkProvider() {
		clientChunkProvider = new ChunkProviderClient(this);
		return clientChunkProvider;
	}

	@Override
	protected void func_147456_g() {
		super.func_147456_g();
		previousActiveChunkSet.retainAll(activeChunkSet);

		if (previousActiveChunkSet.size() == activeChunkSet.size()) {
			previousActiveChunkSet.clear();
		}

		int var1 = 0;
		final Iterator var2 = activeChunkSet.iterator();

		while (var2.hasNext()) {
			final ChunkCoordIntPair var3 = (ChunkCoordIntPair) var2.next();

			if (!previousActiveChunkSet.contains(var3)) {
				final int var4 = var3.chunkXPos * 16;
				final int var5 = var3.chunkZPos * 16;
				theProfiler.startSection("getChunk");
				final Chunk var6 = getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
				func_147467_a(var4, var5, var6);
				theProfiler.endSection();
				previousActiveChunkSet.add(var3);
				++var1;

				if (var1 >= 10) {
					return;
				}
			}
		}
	}

	public void doPreChunk(final int p_73025_1_, final int p_73025_2_, final boolean p_73025_3_) {
		if (p_73025_3_) {
			clientChunkProvider.loadChunk(p_73025_1_, p_73025_2_);
		} else {
			clientChunkProvider.unloadChunk(p_73025_1_, p_73025_2_);
		}

		if (!p_73025_3_) {
			this.markBlockRangeForRenderUpdate(p_73025_1_ * 16, 0, p_73025_2_ * 16, p_73025_1_ * 16 + 15, 256,
					p_73025_2_ * 16 + 15);
		}
	}

	/**
	 * Called when an entity is spawned in the world. This includes players.
	 */
	@Override
	public boolean spawnEntityInWorld(final Entity p_72838_1_) {
		final boolean var2 = super.spawnEntityInWorld(p_72838_1_);
		entityList.add(p_72838_1_);

		if (!var2) {
			entitySpawnQueue.add(p_72838_1_);
		} else if (p_72838_1_ instanceof EntityMinecart) {
			mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart) p_72838_1_));
		}

		return var2;
	}

	/**
	 * Schedule the entity for removal during the next tick. Marks the entity
	 * dead in anticipation.
	 */
	@Override
	public void removeEntity(final Entity p_72900_1_) {
		super.removeEntity(p_72900_1_);
		entityList.remove(p_72900_1_);
	}

	@Override
	protected void onEntityAdded(final Entity p_72923_1_) {
		super.onEntityAdded(p_72923_1_);

		if (entitySpawnQueue.contains(p_72923_1_)) {
			entitySpawnQueue.remove(p_72923_1_);
		}
	}

	@Override
	protected void onEntityRemoved(final Entity p_72847_1_) {
		super.onEntityRemoved(p_72847_1_);
		if (entityList.contains(p_72847_1_)) {
			if (p_72847_1_.isEntityAlive()) {
				entitySpawnQueue.add(p_72847_1_);
			} else {
				entityList.remove(p_72847_1_);
			}
		}
	}

	/**
	 * Add an ID to Entity mapping to entityHashSet
	 */
	public void addEntityToWorld(final int p_73027_1_, final Entity p_73027_2_) {
		final Entity var3 = getEntityByID(p_73027_1_);

		if (var3 != null) {
			removeEntity(var3);
		}

		entityList.add(p_73027_2_);
		p_73027_2_.setEntityId(p_73027_1_);

		if (!spawnEntityInWorld(p_73027_2_)) {
			entitySpawnQueue.add(p_73027_2_);
		}

		entitiesById.addKey(p_73027_1_, p_73027_2_);
	}

	/**
	 * Returns the Entity with the given ID, or null if it doesn't exist in this
	 * World.
	 */
	@Override
	public Entity getEntityByID(final int p_73045_1_) {
		return p_73045_1_ == Minecraft.thePlayer.getEntityId() ? Minecraft.thePlayer : super.getEntityByID(p_73045_1_);
	}

	public Entity removeEntityFromWorld(final int p_73028_1_) {
		final Entity var2 = (Entity) entitiesById.removeObject(p_73028_1_);

		if (var2 != null) {
			entityList.remove(var2);
			removeEntity(var2);
		}

		return var2;
	}

	public boolean func_180503_b(final BlockPos p_180503_1_, final IBlockState p_180503_2_) {
		final int var3 = p_180503_1_.getX();
		final int var4 = p_180503_1_.getY();
		final int var5 = p_180503_1_.getZ();
		invalidateBlockReceiveRegion(var3, var4, var5, var3, var4, var5);
		return super.setBlockState(p_180503_1_, p_180503_2_, 3);
	}

	/**
	 * If on MP, sends a quitting packet.
	 */
	@Override
	public void sendQuittingDisconnectingPacket() {
		sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
	}

	/**
	 * Updates all weather states.
	 */
	@Override
	protected void updateWeather() {}

	@Override
	protected int getRenderDistanceChunks() {
		return Minecraft.gameSettings.renderDistanceChunks;
	}

	public void doVoidFogParticles(final int p_73029_1_, final int p_73029_2_, final int p_73029_3_) {
		final byte var4 = 16;
		final Random var5 = new Random();
		final ItemStack var6 = Minecraft.thePlayer.getHeldItem();
		final boolean var7 = Minecraft.playerController.func_178889_l() == WorldSettings.GameType.CREATIVE
				&& var6 != null && Block.getBlockFromItem(var6.getItem()) == Blocks.barrier;
		final BlockPosM blockPosM = randomTickPosM;

		for (int var8 = 0; var8 < 1000; ++var8) {
			final int var9 = p_73029_1_ + rand.nextInt(var4) - rand.nextInt(var4);
			final int var10 = p_73029_2_ + rand.nextInt(var4) - rand.nextInt(var4);
			final int var11 = p_73029_3_ + rand.nextInt(var4) - rand.nextInt(var4);
			blockPosM.setXyz(var9, var10, var11);
			final IBlockState var13 = getBlockState(blockPosM);
			var13.getBlock().randomDisplayTick(this, blockPosM, var13, var5);

			if ((var7 || showBarrier) && var13.getBlock() == Blocks.barrier) {
				this.spawnParticle(EnumParticleTypes.BARRIER, var9 + 0.5f, var10 + 0.5f, var11 + 0.5f, 0.0, 0.0, 0.0,
						new int[0]);
			}
		}
	}

	/**
	 * also releases skins.
	 */
	public void removeAllEntities() {
		loadedEntityList.removeAll(unloadedEntityList);
		int var1;
		Entity var2;
		int var3;
		int var4;

		for (var1 = 0; var1 < unloadedEntityList.size(); ++var1) {
			var2 = (Entity) unloadedEntityList.get(var1);
			var3 = var2.chunkCoordX;
			var4 = var2.chunkCoordZ;

			if (var2.addedToChunk && isChunkLoaded(var3, var4, true)) {
				getChunkFromChunkCoords(var3, var4).removeEntity(var2);
			}
		}

		for (var1 = 0; var1 < unloadedEntityList.size(); ++var1) {
			onEntityRemoved((Entity) unloadedEntityList.get(var1));
		}

		unloadedEntityList.clear();

		for (var1 = 0; var1 < loadedEntityList.size(); ++var1) {
			var2 = (Entity) loadedEntityList.get(var1);

			if (var2.ridingEntity != null) {
				if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
					continue;
				}

				var2.ridingEntity.riddenByEntity = null;
				var2.ridingEntity = null;
			}

			if (var2.isDead) {
				var3 = var2.chunkCoordX;
				var4 = var2.chunkCoordZ;

				if (var2.addedToChunk && isChunkLoaded(var3, var4, true)) {
					getChunkFromChunkCoords(var3, var4).removeEntity(var2);
				}

				loadedEntityList.remove(var1--);
				onEntityRemoved(var2);
			}
		}
	}

	/**
	 * Adds some basic stats of the world to the given crash report.
	 */
	@Override
	public CrashReportCategory addWorldInfoToCrashReport(final CrashReport report) {
		final CrashReportCategory var2 = super.addWorldInfoToCrashReport(report);
		var2.addCrashSectionCallable("Forced entities", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000883";

			@Override
			public String call() {
				return entityList.size() + " total; " + entityList.toString();
			}
		});
		var2.addCrashSectionCallable("Retry entities", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000884";

			@Override
			public String call() {
				return entitySpawnQueue.size() + " total; " + entitySpawnQueue.toString();
			}
		});
		var2.addCrashSectionCallable("Server brand", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000885";

			@Override
			public String call() {
				return Minecraft.thePlayer.getClientBrand();
			}
		});
		var2.addCrashSectionCallable("Server type", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000886";

			@Override
			public String call() {
				return mc.getIntegratedServer() == null ? "Non-integrated multiplayer server"
						: "Integrated singleplayer server";
			}
		});
		return var2;
	}

	public void func_175731_a(final BlockPos p_175731_1_, final String p_175731_2_, final float p_175731_3_,
			final float p_175731_4_, final boolean p_175731_5_) {
		playSound(p_175731_1_.getX() + 0.5D, p_175731_1_.getY() + 0.5D, p_175731_1_.getZ() + 0.5D, p_175731_2_,
				p_175731_3_, p_175731_4_, p_175731_5_);
	}

	/**
	 * par8 is loudness, all pars passed to
	 * minecraftInstance.sndManager.playSound
	 */
	@Override
	public void playSound(final double x, final double y, final double z, final String soundName, final float volume,
			final float pitch, final boolean distanceDelay) {
		final double var11 = mc.func_175606_aa().getDistanceSq(x, y, z);
		final PositionedSoundRecord var13 = new PositionedSoundRecord(new ResourceLocation(soundName), volume, pitch,
				(float) x, (float) y, (float) z);

		if (distanceDelay && var11 > 100.0D) {
			final double var14 = Math.sqrt(var11) / 40.0D;
			mc.getSoundHandler().playDelayedSound(var13, (int) (var14 * 20.0D));
		} else {
			mc.getSoundHandler().playSound(var13);
		}
	}

	@Override
	public void makeFireworks(final double x, final double y, final double z, final double motionX,
			final double motionY, final double motionZ, final NBTTagCompound compund) {
		Minecraft.effectRenderer.addEffect(new EntityFireworkStarterFX(this, x, y, z, motionX, motionY, motionZ,
				Minecraft.effectRenderer, compund));
	}

	public void setWorldScoreboard(final Scoreboard p_96443_1_) {
		worldScoreboard = p_96443_1_;
	}

	/**
	 * Sets the world time.
	 */
	@Override
	public void setWorldTime(long time) {
		if (time < 0L) {
			time = -time;
			getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
		} else {
			getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
		}

		super.setWorldTime(time);
	}

	@Override
	public int getCombinedLight(final BlockPos pos, final int lightValue) {
		int combinedLight = super.getCombinedLight(pos, lightValue);

		if (Config.isDynamicLights()) {
			combinedLight = DynamicLights.getCombinedLight(pos, combinedLight);
		}

		return combinedLight;
	}

	@Override
	public boolean setBlockState(final BlockPos pos, final IBlockState newState, final int flags) {
		playerUpdate = isPlayerActing();
		final boolean res = super.setBlockState(pos, newState, flags);
		playerUpdate = false;
		return res;
	}

	private boolean isPlayerActing() {
		if (Minecraft.playerController instanceof PlayerControllerOF) {
			final PlayerControllerOF pcof = (PlayerControllerOF) Minecraft.playerController;
			return pcof.isActing();
		} else {
			return false;
		}
	}

	public boolean isPlayerUpdate() {
		return playerUpdate;
	}
}
