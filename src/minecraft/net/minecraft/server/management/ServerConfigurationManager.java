package net.minecraft.server.management;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;

public abstract class ServerConfigurationManager {

public static final int EaZy = 1542;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final File FILE_PLAYERBANS = new File("banned-players.json");
	public static final File FILE_IPBANS = new File("banned-ips.json");
	public static final File FILE_OPS = new File("ops.json");
	public static final File FILE_WHITELIST = new File("whitelist.json");
	private static final Logger logger = LogManager.getLogger();
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");

	/** Reference to the MinecraftServer object. */
	private final MinecraftServer mcServer;

	/** A list of player entities that exist on this server. */
	public final List playerEntityList = Lists.newArrayList();
	public final Map field_177454_f = Maps.newHashMap();
	private final UserListBans bannedPlayers;
	private final BanList bannedIPs;

	/** A set containing the OPs. */
	private final UserListOps ops;

	/** The Set of all whitelisted players. */
	private final UserListWhitelist whiteListedPlayers;
	private final Map playerStatFiles;

	/** Reference to the PlayerNBTManager object. */
	private IPlayerFileData playerNBTManagerObj;

	/**
	 * Server setting to only allow OPs and whitelisted players to join the
	 * server.
	 */
	private boolean whiteListEnforced;

	/** The maximum number of players that can be connected at a time. */
	protected int maxPlayers;
	private int viewDistance;
	private WorldSettings.GameType gameType;

	/** True if all players are allowed to use commands (cheats). */
	private boolean commandsAllowedForAll;

	/**
	 * index into playerEntities of player to ping, updated every tick;
	 * currently hardcoded to max at 200 players
	 */
	private int playerPingIndex;
	// private static final String __OBFID = "http://https://fuckuskid00001423";

	public ServerConfigurationManager(final MinecraftServer server) {
		bannedPlayers = new UserListBans(FILE_PLAYERBANS);
		bannedIPs = new BanList(FILE_IPBANS);
		ops = new UserListOps(FILE_OPS);
		whiteListedPlayers = new UserListWhitelist(FILE_WHITELIST);
		playerStatFiles = Maps.newHashMap();
		mcServer = server;
		bannedPlayers.setLanServer(false);
		bannedIPs.setLanServer(false);
		maxPlayers = 8;
	}

	public void initializeConnectionToPlayer(final NetworkManager netManager, final EntityPlayerMP playerIn) {
		final GameProfile var3 = playerIn.getGameProfile();
		final PlayerProfileCache var4 = mcServer.getPlayerProfileCache();
		final GameProfile var5 = var4.func_152652_a(var3.getId());
		final String var6 = var5 == null ? var3.getName() : var5.getName();
		var4.func_152649_a(var3);
		final NBTTagCompound var7 = readPlayerDataFromFile(playerIn);
		playerIn.setWorld(mcServer.worldServerForDimension(playerIn.dimension));
		playerIn.theItemInWorldManager.setWorld((WorldServer) playerIn.worldObj);
		String var8 = "local";

		if (netManager.getRemoteAddress() != null) {
			var8 = netManager.getRemoteAddress().toString();
		}

		logger.info(playerIn.getName() + "[" + var8 + "] logged in with entity id " + playerIn.getEntityId() + " at ("
				+ playerIn.posX + ", " + playerIn.posY + ", " + playerIn.posZ + ")");
		final WorldServer var9 = mcServer.worldServerForDimension(playerIn.dimension);
		final WorldInfo var10 = var9.getWorldInfo();
		final BlockPos var11 = var9.getSpawnPoint();
		func_72381_a(playerIn, (EntityPlayerMP) null, var9);
		final NetHandlerPlayServer var12 = new NetHandlerPlayServer(mcServer, netManager, playerIn);
		var12.sendPacket(new S01PacketJoinGame(playerIn.getEntityId(), playerIn.theItemInWorldManager.getGameType(),
				var10.isHardcoreModeEnabled(), var9.provider.getDimensionId(), var9.getDifficulty(), getMaxPlayers(),
				var10.getTerrainType(), var9.getGameRules().getGameRuleBooleanValue("reducedDebugInfo")));
		var12.sendPacket(new S3FPacketCustomPayload("MC|Brand",
				new PacketBuffer(Unpooled.buffer()).writeString(getServerInstance().getServerModName())));
		var12.sendPacket(new S41PacketServerDifficulty(var10.getDifficulty(), var10.isDifficultyLocked()));
		var12.sendPacket(new S05PacketSpawnPosition(var11));
		var12.sendPacket(new S39PacketPlayerAbilities(playerIn.capabilities));
		var12.sendPacket(new S09PacketHeldItemChange(playerIn.inventory.currentItem));
		playerIn.getStatFile().func_150877_d();
		playerIn.getStatFile().func_150884_b(playerIn);
		func_96456_a((ServerScoreboard) var9.getScoreboard(), playerIn);
		mcServer.refreshStatusNextTick();
		ChatComponentTranslation var13;

		if (!playerIn.getName().equalsIgnoreCase(var6)) {
			var13 = new ChatComponentTranslation("multiplayer.player.joined.renamed",
					new Object[] { playerIn.getDisplayName(), var6 });
		} else {
			var13 = new ChatComponentTranslation("multiplayer.player.joined",
					new Object[] { playerIn.getDisplayName() });
		}

		var13.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		sendChatMsg(var13);
		playerLoggedIn(playerIn);
		var12.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw,
				playerIn.rotationPitch);
		updateTimeAndWeatherForPlayer(playerIn, var9);

		if (mcServer.getResourcePackUrl().length() > 0) {
			playerIn.func_175397_a(mcServer.getResourcePackUrl(), mcServer.getResourcePackHash());
		}

		final Iterator var14 = playerIn.getActivePotionEffects().iterator();

		while (var14.hasNext()) {
			final PotionEffect var15 = (PotionEffect) var14.next();
			var12.sendPacket(new S1DPacketEntityEffect(playerIn.getEntityId(), var15));
		}

		playerIn.addSelfToInternalCraftingInventory();

		if (var7 != null && var7.hasKey("Riding", 10)) {
			final Entity var16 = EntityList.createEntityFromNBT(var7.getCompoundTag("Riding"), var9);

			if (var16 != null) {
				var16.forceSpawn = true;
				var9.spawnEntityInWorld(var16);
				playerIn.mountEntity(var16);
				var16.forceSpawn = false;
			}
		}
	}

	protected void func_96456_a(final ServerScoreboard scoreboardIn, final EntityPlayerMP playerIn) {
		final HashSet var3 = Sets.newHashSet();
		final Iterator var4 = scoreboardIn.getTeams().iterator();

		while (var4.hasNext()) {
			final ScorePlayerTeam var5 = (ScorePlayerTeam) var4.next();
			playerIn.playerNetServerHandler.sendPacket(new S3EPacketTeams(var5, 0));
		}

		for (int var9 = 0; var9 < 19; ++var9) {
			final ScoreObjective var10 = scoreboardIn.getObjectiveInDisplaySlot(var9);

			if (var10 != null && !var3.contains(var10)) {
				final List var6 = scoreboardIn.func_96550_d(var10);
				final Iterator var7 = var6.iterator();

				while (var7.hasNext()) {
					final Packet var8 = (Packet) var7.next();
					playerIn.playerNetServerHandler.sendPacket(var8);
				}

				var3.add(var10);
			}
		}
	}

	/**
	 * Sets the NBT manager to the one for the WorldServer given.
	 */
	public void setPlayerManager(final WorldServer[] p_72364_1_) {
		playerNBTManagerObj = p_72364_1_[0].getSaveHandler().getPlayerNBTManager();
		p_72364_1_[0].getWorldBorder().addListener(new IBorderListener() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002267";
			@Override
			public void onSizeChanged(final WorldBorder border, final double newSize) {
				ServerConfigurationManager.this
						.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_SIZE));
			}

			@Override
			public void func_177692_a(final WorldBorder border, final double p_177692_2_, final double p_177692_4_,
					final long p_177692_6_) {
				ServerConfigurationManager.this.sendPacketToAllPlayers(
						new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.LERP_SIZE));
			}

			@Override
			public void onCenterChanged(final WorldBorder border, final double x, final double z) {
				ServerConfigurationManager.this.sendPacketToAllPlayers(
						new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_CENTER));
			}

			@Override
			public void onWarningTimeChanged(final WorldBorder border, final int p_177691_2_) {
				ServerConfigurationManager.this.sendPacketToAllPlayers(
						new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_TIME));
			}

			@Override
			public void onWarningDistanceChanged(final WorldBorder border, final int p_177690_2_) {
				ServerConfigurationManager.this.sendPacketToAllPlayers(
						new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
			}

			@Override
			public void func_177696_b(final WorldBorder border, final double p_177696_2_) {}

			@Override
			public void func_177695_c(final WorldBorder border, final double p_177695_2_) {}
		});
	}

	public void func_72375_a(final EntityPlayerMP playerIn, final WorldServer worldIn) {
		final WorldServer var3 = playerIn.getServerForPlayer();

		if (worldIn != null) {
			worldIn.getPlayerManager().removePlayer(playerIn);
		}

		var3.getPlayerManager().addPlayer(playerIn);
		var3.theChunkProviderServer.loadChunk((int) playerIn.posX >> 4, (int) playerIn.posZ >> 4);
	}

	public int getEntityViewDistance() {
		return PlayerManager.getFurthestViewableBlock(getViewDistance());
	}

	/**
	 * called during player login. reads the player information from disk.
	 */
	public NBTTagCompound readPlayerDataFromFile(final EntityPlayerMP playerIn) {
		final NBTTagCompound var2 = mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
		NBTTagCompound var3;

		if (playerIn.getName().equals(mcServer.getServerOwner()) && var2 != null) {
			playerIn.readFromNBT(var2);
			var3 = var2;
			logger.debug("loading single player");
		} else {
			var3 = playerNBTManagerObj.readPlayerData(playerIn);
		}

		return var3;
	}

	/**
	 * also stores the NBTTags if this is an intergratedPlayerList
	 */
	protected void writePlayerData(final EntityPlayerMP playerIn) {
		playerNBTManagerObj.writePlayerData(playerIn);
		final StatisticsFile var2 = (StatisticsFile) playerStatFiles.get(playerIn.getUniqueID());

		if (var2 != null) {
			var2.func_150883_b();
		}
	}

	/**
	 * Called when a player successfully logs in. Reads player data from disk
	 * and inserts the player into the world.
	 */
	public void playerLoggedIn(final EntityPlayerMP playerIn) {
		playerEntityList.add(playerIn);
		field_177454_f.put(playerIn.getUniqueID(), playerIn);
		sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER,
				new EntityPlayerMP[] { playerIn }));
		final WorldServer var2 = mcServer.worldServerForDimension(playerIn.dimension);
		var2.spawnEntityInWorld(playerIn);
		func_72375_a(playerIn, (WorldServer) null);

		for (int var3 = 0; var3 < playerEntityList.size(); ++var3) {
			final EntityPlayerMP var4 = (EntityPlayerMP) playerEntityList.get(var3);
			playerIn.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(
					S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { var4 }));
		}
	}

	/**
	 * using player's dimension, update their movement when in a vehicle (e.g.
	 * cart, boat)
	 */
	public void serverUpdateMountedMovingPlayer(final EntityPlayerMP playerIn) {
		playerIn.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(playerIn);
	}

	/**
	 * Called when a player disconnects from the game. Writes player data to
	 * disk and removes them from the world.
	 */
	public void playerLoggedOut(final EntityPlayerMP playerIn) {
		playerIn.triggerAchievement(StatList.leaveGameStat);
		writePlayerData(playerIn);
		final WorldServer var2 = playerIn.getServerForPlayer();

		if (playerIn.ridingEntity != null) {
			var2.removePlayerEntityDangerously(playerIn.ridingEntity);
			logger.debug("removing player mount");
		}

		var2.removeEntity(playerIn);
		var2.getPlayerManager().removePlayer(playerIn);
		playerEntityList.remove(playerIn);
		field_177454_f.remove(playerIn.getUniqueID());
		playerStatFiles.remove(playerIn.getUniqueID());
		sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER,
				new EntityPlayerMP[] { playerIn }));
	}

	/**
	 * checks ban-lists, then white-lists, then space for the server. Returns
	 * null on success, or an error message
	 */
	public String allowUserToConnect(final SocketAddress address, final GameProfile profile) {
		String var4;

		if (bannedPlayers.isBanned(profile)) {
			final UserListBansEntry var5 = (UserListBansEntry) bannedPlayers.getEntry(profile);
			var4 = "You are banned from this server!\nReason: " + var5.getBanReason();

			if (var5.getBanEndDate() != null) {
				var4 = var4 + "\nYour ban will be removed on " + dateFormat.format(var5.getBanEndDate());
			}

			return var4;
		} else if (!canJoin(profile)) {
			return "You are not white-listed on this server!";
		} else if (bannedIPs.isBanned(address)) {
			final IPBanEntry var3 = bannedIPs.getBanEntry(address);
			var4 = "Your IP address is banned from this server!\nReason: " + var3.getBanReason();

			if (var3.getBanEndDate() != null) {
				var4 = var4 + "\nYour ban will be removed on " + dateFormat.format(var3.getBanEndDate());
			}

			return var4;
		} else {
			return playerEntityList.size() >= maxPlayers ? "The server is full!" : null;
		}
	}

	/**
	 * also checks for multiple logins across servers
	 */
	public EntityPlayerMP createPlayerForUser(final GameProfile profile) {
		final UUID var2 = EntityPlayer.getUUID(profile);
		final ArrayList var3 = Lists.newArrayList();
		EntityPlayerMP var5;

		for (int var4 = 0; var4 < playerEntityList.size(); ++var4) {
			var5 = (EntityPlayerMP) playerEntityList.get(var4);

			if (var5.getUniqueID().equals(var2)) {
				var3.add(var5);
			}
		}

		final Iterator var6 = var3.iterator();

		while (var6.hasNext()) {
			var5 = (EntityPlayerMP) var6.next();
			var5.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
		}

		Object var7;

		if (mcServer.isDemo()) {
			var7 = new DemoWorldManager(mcServer.worldServerForDimension(0));
		} else {
			var7 = new ItemInWorldManager(mcServer.worldServerForDimension(0));
		}

		return new EntityPlayerMP(mcServer, mcServer.worldServerForDimension(0), profile, (ItemInWorldManager) var7);
	}

	/**
	 * Called on respawn
	 */
	public EntityPlayerMP recreatePlayerEntity(final EntityPlayerMP playerIn, final int dimension,
			final boolean conqueredEnd) {
		playerIn.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(playerIn);
		playerIn.getServerForPlayer().getEntityTracker().untrackEntity(playerIn);
		playerIn.getServerForPlayer().getPlayerManager().removePlayer(playerIn);
		playerEntityList.remove(playerIn);
		mcServer.worldServerForDimension(playerIn.dimension).removePlayerEntityDangerously(playerIn);
		final BlockPos var4 = playerIn.func_180470_cg();
		final boolean var5 = playerIn.isSpawnForced();
		playerIn.dimension = dimension;
		Object var6;

		if (mcServer.isDemo()) {
			var6 = new DemoWorldManager(mcServer.worldServerForDimension(playerIn.dimension));
		} else {
			var6 = new ItemInWorldManager(mcServer.worldServerForDimension(playerIn.dimension));
		}

		final EntityPlayerMP var7 = new EntityPlayerMP(mcServer, mcServer.worldServerForDimension(playerIn.dimension),
				playerIn.getGameProfile(), (ItemInWorldManager) var6);
		var7.playerNetServerHandler = playerIn.playerNetServerHandler;
		var7.clonePlayer(playerIn, conqueredEnd);
		var7.setEntityId(playerIn.getEntityId());
		var7.func_174817_o(playerIn);
		final WorldServer var8 = mcServer.worldServerForDimension(playerIn.dimension);
		func_72381_a(var7, playerIn, var8);
		BlockPos var9;

		if (var4 != null) {
			var9 = EntityPlayer.func_180467_a(mcServer.worldServerForDimension(playerIn.dimension), var4, var5);

			if (var9 != null) {
				var7.setLocationAndAngles(var9.getX() + 0.5F, var9.getY() + 0.1F, var9.getZ() + 0.5F, 0.0F, 0.0F);
				var7.func_180473_a(var4, var5);
			} else {
				var7.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0F));
			}
		}

		var8.theChunkProviderServer.loadChunk((int) var7.posX >> 4, (int) var7.posZ >> 4);

		while (!var8.getCollidingBoundingBoxes(var7, var7.getEntityBoundingBox()).isEmpty() && var7.posY < 256.0D) {
			var7.setPosition(var7.posX, var7.posY + 1.0D, var7.posZ);
		}

		var7.playerNetServerHandler.sendPacket(new S07PacketRespawn(var7.dimension, var7.worldObj.getDifficulty(),
				var7.worldObj.getWorldInfo().getTerrainType(), var7.theItemInWorldManager.getGameType()));
		var9 = var8.getSpawnPoint();
		var7.playerNetServerHandler.setPlayerLocation(var7.posX, var7.posY, var7.posZ, var7.rotationYaw,
				var7.rotationPitch);
		var7.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(var9));
		var7.playerNetServerHandler
				.sendPacket(new S1FPacketSetExperience(var7.experience, var7.experienceTotal, var7.experienceLevel));
		updateTimeAndWeatherForPlayer(var7, var8);
		var8.getPlayerManager().addPlayer(var7);
		var8.spawnEntityInWorld(var7);
		playerEntityList.add(var7);
		field_177454_f.put(var7.getUniqueID(), var7);
		var7.addSelfToInternalCraftingInventory();
		var7.setHealth(var7.getHealth());
		return var7;
	}

	/**
	 * moves provided player from overworld to nether or vice versa
	 */
	public void transferPlayerToDimension(final EntityPlayerMP playerIn, final int dimension) {
		final int var3 = playerIn.dimension;
		final WorldServer var4 = mcServer.worldServerForDimension(playerIn.dimension);
		playerIn.dimension = dimension;
		final WorldServer var5 = mcServer.worldServerForDimension(playerIn.dimension);
		playerIn.playerNetServerHandler.sendPacket(new S07PacketRespawn(playerIn.dimension,
				playerIn.worldObj.getDifficulty(), playerIn.worldObj.getWorldInfo().getTerrainType(),
				playerIn.theItemInWorldManager.getGameType()));
		var4.removePlayerEntityDangerously(playerIn);
		playerIn.isDead = false;
		transferEntityToWorld(playerIn, var3, var4, var5);
		func_72375_a(playerIn, var4);
		playerIn.playerNetServerHandler.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ,
				playerIn.rotationYaw, playerIn.rotationPitch);
		playerIn.theItemInWorldManager.setWorld(var5);
		updateTimeAndWeatherForPlayer(playerIn, var5);
		syncPlayerInventory(playerIn);
		final Iterator var6 = playerIn.getActivePotionEffects().iterator();

		while (var6.hasNext()) {
			final PotionEffect var7 = (PotionEffect) var6.next();
			playerIn.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(playerIn.getEntityId(), var7));
		}
	}

	/**
	 * Transfers an entity from a world to another world.
	 */
	public void transferEntityToWorld(final Entity entityIn, final int p_82448_2_, final WorldServer p_82448_3_,
			final WorldServer p_82448_4_) {
		double var5 = entityIn.posX;
		double var7 = entityIn.posZ;
		final double var9 = 8.0D;
		final float var11 = entityIn.rotationYaw;
		p_82448_3_.theProfiler.startSection("moving");

		if (entityIn.dimension == -1) {
			var5 = MathHelper.clamp_double(var5 / var9, p_82448_4_.getWorldBorder().minX() + 16.0D,
					p_82448_4_.getWorldBorder().maxX() - 16.0D);
			var7 = MathHelper.clamp_double(var7 / var9, p_82448_4_.getWorldBorder().minZ() + 16.0D,
					p_82448_4_.getWorldBorder().maxZ() - 16.0D);
			entityIn.setLocationAndAngles(var5, entityIn.posY, var7, entityIn.rotationYaw, entityIn.rotationPitch);

			if (entityIn.isEntityAlive()) {
				p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
			}
		} else if (entityIn.dimension == 0) {
			var5 = MathHelper.clamp_double(var5 * var9, p_82448_4_.getWorldBorder().minX() + 16.0D,
					p_82448_4_.getWorldBorder().maxX() - 16.0D);
			var7 = MathHelper.clamp_double(var7 * var9, p_82448_4_.getWorldBorder().minZ() + 16.0D,
					p_82448_4_.getWorldBorder().maxZ() - 16.0D);
			entityIn.setLocationAndAngles(var5, entityIn.posY, var7, entityIn.rotationYaw, entityIn.rotationPitch);

			if (entityIn.isEntityAlive()) {
				p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
			}
		} else {
			BlockPos var12;

			if (p_82448_2_ == 1) {
				var12 = p_82448_4_.getSpawnPoint();
			} else {
				var12 = p_82448_4_.func_180504_m();
			}

			var5 = var12.getX();
			entityIn.posY = var12.getY();
			var7 = var12.getZ();
			entityIn.setLocationAndAngles(var5, entityIn.posY, var7, 90.0F, 0.0F);

			if (entityIn.isEntityAlive()) {
				p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
			}
		}

		p_82448_3_.theProfiler.endSection();

		if (p_82448_2_ != 1) {
			p_82448_3_.theProfiler.startSection("placing");
			var5 = MathHelper.clamp_int((int) var5, -29999872, 29999872);
			var7 = MathHelper.clamp_int((int) var7, -29999872, 29999872);

			if (entityIn.isEntityAlive()) {
				entityIn.setLocationAndAngles(var5, entityIn.posY, var7, entityIn.rotationYaw, entityIn.rotationPitch);
				p_82448_4_.getDefaultTeleporter().func_180266_a(entityIn, var11);
				p_82448_4_.spawnEntityInWorld(entityIn);
				p_82448_4_.updateEntityWithOptionalForce(entityIn, false);
			}

			p_82448_3_.theProfiler.endSection();
		}

		entityIn.setWorld(p_82448_4_);
	}

	/**
	 * self explanitory
	 */
	public void onTick() {
		if (++playerPingIndex > 600) {
			sendPacketToAllPlayers(
					new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, playerEntityList));
			playerPingIndex = 0;
		}
	}

	public void sendPacketToAllPlayers(final Packet packetIn) {
		for (int var2 = 0; var2 < playerEntityList.size(); ++var2) {
			((EntityPlayerMP) playerEntityList.get(var2)).playerNetServerHandler.sendPacket(packetIn);
		}
	}

	public void sendPacketToAllPlayersInDimension(final Packet packetIn, final int dimension) {
		for (int var3 = 0; var3 < playerEntityList.size(); ++var3) {
			final EntityPlayerMP var4 = (EntityPlayerMP) playerEntityList.get(var3);

			if (var4.dimension == dimension) {
				var4.playerNetServerHandler.sendPacket(packetIn);
			}
		}
	}

	public void func_177453_a(final EntityPlayer p_177453_1_, final IChatComponent p_177453_2_) {
		final Team var3 = p_177453_1_.getTeam();

		if (var3 != null) {
			final Collection var4 = var3.getMembershipCollection();
			final Iterator var5 = var4.iterator();

			while (var5.hasNext()) {
				final String var6 = (String) var5.next();
				final EntityPlayerMP var7 = getPlayerByUsername(var6);

				if (var7 != null && var7 != p_177453_1_) {
					var7.addChatMessage(p_177453_2_);
				}
			}
		}
	}

	public void func_177452_b(final EntityPlayer p_177452_1_, final IChatComponent p_177452_2_) {
		final Team var3 = p_177452_1_.getTeam();

		if (var3 == null) {
			sendChatMsg(p_177452_2_);
		} else {
			for (int var4 = 0; var4 < playerEntityList.size(); ++var4) {
				final EntityPlayerMP var5 = (EntityPlayerMP) playerEntityList.get(var4);

				if (var5.getTeam() != var3) {
					var5.addChatMessage(p_177452_2_);
				}
			}
		}
	}

	public String func_180602_f() {
		String var1 = "";

		for (int var2 = 0; var2 < playerEntityList.size(); ++var2) {
			if (var2 > 0) {
				var1 = var1 + ", ";
			}

			var1 = var1 + ((EntityPlayerMP) playerEntityList.get(var2)).getName();
		}

		return var1;
	}

	/**
	 * Returns an array of the usernames of all the connected players.
	 */
	public String[] getAllUsernames() {
		final String[] var1 = new String[playerEntityList.size()];

		for (int var2 = 0; var2 < playerEntityList.size(); ++var2) {
			var1[var2] = ((EntityPlayerMP) playerEntityList.get(var2)).getName();
		}

		return var1;
	}

	public GameProfile[] getAllProfiles() {
		final GameProfile[] var1 = new GameProfile[playerEntityList.size()];

		for (int var2 = 0; var2 < playerEntityList.size(); ++var2) {
			var1[var2] = ((EntityPlayerMP) playerEntityList.get(var2)).getGameProfile();
		}

		return var1;
	}

	public UserListBans getBannedPlayers() {
		return bannedPlayers;
	}

	public BanList getBannedIPs() {
		return bannedIPs;
	}

	public void addOp(final GameProfile profile) {
		ops.addEntry(new UserListOpsEntry(profile, mcServer.getOpPermissionLevel()));
	}

	public void removeOp(final GameProfile profile) {
		ops.removeEntry(profile);
	}

	public boolean canJoin(final GameProfile profile) {
		return !whiteListEnforced || ops.hasEntry(profile) || whiteListedPlayers.hasEntry(profile);
	}

	public boolean canSendCommands(final GameProfile profile) {
		return ops.hasEntry(profile)
				|| mcServer.isSinglePlayer() && mcServer.worldServers[0].getWorldInfo().areCommandsAllowed()
						&& mcServer.getServerOwner().equalsIgnoreCase(profile.getName())
				|| commandsAllowedForAll;
	}

	public EntityPlayerMP getPlayerByUsername(final String username) {
		final Iterator var2 = playerEntityList.iterator();
		EntityPlayerMP var3;

		do {
			if (!var2.hasNext()) {
				return null;
			}

			var3 = (EntityPlayerMP) var2.next();
		}
		while (!var3.getName().equalsIgnoreCase(username));

		return var3;
	}

	/**
	 * params: x,y,z,r,dimension. The packet is sent to all players within r
	 * radius of x,y,z (r^2>x^2+y^2+z^2)
	 */
	public void sendToAllNear(final double x, final double y, final double z, final double radius, final int dimension,
			final Packet packetIn) {
		sendToAllNearExcept((EntityPlayer) null, x, y, z, radius, dimension, packetIn);
	}

	/**
	 * params: srcPlayer,x,y,z,r,dimension. The packet is not sent to the
	 * srcPlayer, but all other players within the search radius
	 */
	public void sendToAllNearExcept(final EntityPlayer p_148543_1_, final double x, final double y, final double z,
			final double radius, final int dimension, final Packet p_148543_11_) {
		for (int var12 = 0; var12 < playerEntityList.size(); ++var12) {
			final EntityPlayerMP var13 = (EntityPlayerMP) playerEntityList.get(var12);

			if (var13 != p_148543_1_ && var13.dimension == dimension) {
				final double var14 = x - var13.posX;
				final double var16 = y - var13.posY;
				final double var18 = z - var13.posZ;

				if (var14 * var14 + var16 * var16 + var18 * var18 < radius * radius) {
					var13.playerNetServerHandler.sendPacket(p_148543_11_);
				}
			}
		}
	}

	/**
	 * Saves all of the players' current states.
	 */
	public void saveAllPlayerData() {
		for (int var1 = 0; var1 < playerEntityList.size(); ++var1) {
			writePlayerData((EntityPlayerMP) playerEntityList.get(var1));
		}
	}

	public void addWhitelistedPlayer(final GameProfile profile) {
		whiteListedPlayers.addEntry(new UserListWhitelistEntry(profile));
	}

	public void removePlayerFromWhitelist(final GameProfile profile) {
		whiteListedPlayers.removeEntry(profile);
	}

	public UserListWhitelist getWhitelistedPlayers() {
		return whiteListedPlayers;
	}

	public String[] getWhitelistedPlayerNames() {
		return whiteListedPlayers.getKeys();
	}

	public UserListOps getOppedPlayers() {
		return ops;
	}

	public String[] getOppedPlayerNames() {
		return ops.getKeys();
	}

	/**
	 * Either does nothing, or calls readWhiteList.
	 */
	public void loadWhiteList() {}

	/**
	 * Updates the time and weather for the given player to those of the given
	 * world
	 */
	public void updateTimeAndWeatherForPlayer(final EntityPlayerMP playerIn, final WorldServer worldIn) {
		final WorldBorder var3 = mcServer.worldServers[0].getWorldBorder();
		playerIn.playerNetServerHandler
				.sendPacket(new S44PacketWorldBorder(var3, S44PacketWorldBorder.Action.INITIALIZE));
		playerIn.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(worldIn.getTotalWorldTime(),
				worldIn.getWorldTime(), worldIn.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));

		if (worldIn.isRaining()) {
			playerIn.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0F));
			playerIn.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, worldIn.getRainStrength(1.0F)));
			playerIn.playerNetServerHandler
					.sendPacket(new S2BPacketChangeGameState(8, worldIn.getWeightedThunderStrength(1.0F)));
		}
	}

	/**
	 * sends the players inventory to himself
	 */
	public void syncPlayerInventory(final EntityPlayerMP playerIn) {
		playerIn.sendContainerToPlayer(playerIn.inventoryContainer);
		playerIn.setPlayerHealthUpdated();
		playerIn.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(playerIn.inventory.currentItem));
	}

	/**
	 * Returns the number of players currently on the server.
	 */
	public int getCurrentPlayerCount() {
		return playerEntityList.size();
	}

	/**
	 * Returns the maximum number of players allowed on the server.
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Returns an array of usernames for which player.dat exists for.
	 */
	public String[] getAvailablePlayerDat() {
		return mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
	}

	public void setWhiteListEnabled(final boolean whitelistEnabled) {
		whiteListEnforced = whitelistEnabled;
	}

	public List getPlayersMatchingAddress(final String address) {
		final ArrayList var2 = Lists.newArrayList();
		final Iterator var3 = playerEntityList.iterator();

		while (var3.hasNext()) {
			final EntityPlayerMP var4 = (EntityPlayerMP) var3.next();

			if (var4.getPlayerIP().equals(address)) {
				var2.add(var4);
			}
		}

		return var2;
	}

	/**
	 * Gets the View Distance.
	 */
	public int getViewDistance() {
		return viewDistance;
	}

	public MinecraftServer getServerInstance() {
		return mcServer;
	}

	/**
	 * On integrated servers, returns the host's player data to be written to
	 * level.dat.
	 */
	public NBTTagCompound getHostPlayerData() {
		return null;
	}

	public void func_152604_a(final WorldSettings.GameType p_152604_1_) {
		gameType = p_152604_1_;
	}

	private void func_72381_a(final EntityPlayerMP p_72381_1_, final EntityPlayerMP p_72381_2_, final World worldIn) {
		if (p_72381_2_ != null) {
			p_72381_1_.theItemInWorldManager.setGameType(p_72381_2_.theItemInWorldManager.getGameType());
		} else if (gameType != null) {
			p_72381_1_.theItemInWorldManager.setGameType(gameType);
		}

		p_72381_1_.theItemInWorldManager.initializeGameType(worldIn.getWorldInfo().getGameType());
	}

	/**
	 * Sets whether all players are allowed to use commands (cheats) on the
	 * server.
	 */
	public void setCommandsAllowedForAll(final boolean p_72387_1_) {
		commandsAllowedForAll = p_72387_1_;
	}

	/**
	 * Kicks everyone with "Server closed" as reason.
	 */
	public void removeAllPlayers() {
		for (int var1 = 0; var1 < playerEntityList.size(); ++var1) {
			((EntityPlayerMP) playerEntityList.get(var1)).playerNetServerHandler.kickPlayerFromServer("Server closed");
		}
	}

	public void sendChatMsgImpl(final IChatComponent component, final boolean isChat) {
		mcServer.addChatMessage(component);
		final int var3 = isChat ? 1 : 0;
		sendPacketToAllPlayers(new S02PacketChat(component, (byte) var3));
	}

	/**
	 * Sends the given string to every player as chat message.
	 */
	public void sendChatMsg(final IChatComponent component) {
		sendChatMsgImpl(component, true);
	}

	public StatisticsFile getPlayerStatsFile(final EntityPlayer playerIn) {
		final UUID var2 = playerIn.getUniqueID();
		StatisticsFile var3 = var2 == null ? null : (StatisticsFile) playerStatFiles.get(var2);

		if (var3 == null) {
			final File var4 = new File(mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(),
					"stats");
			final File var5 = new File(var4, var2.toString() + ".json");

			if (!var5.exists()) {
				final File var6 = new File(var4, playerIn.getName() + ".json");

				if (var6.exists() && var6.isFile()) {
					var6.renameTo(var5);
				}
			}

			var3 = new StatisticsFile(mcServer, var5);
			var3.func_150882_a();
			playerStatFiles.put(var2, var3);
		}

		return var3;
	}

	public void setViewDistance(final int distance) {
		viewDistance = distance;

		if (mcServer.worldServers != null) {
			final WorldServer[] var2 = mcServer.worldServers;
			final int var3 = var2.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				final WorldServer var5 = var2[var4];

				if (var5 != null) {
					var5.getPlayerManager().func_152622_a(distance);
				}
			}
		}
	}

	public EntityPlayerMP func_177451_a(final UUID p_177451_1_) {
		return (EntityPlayerMP) field_177454_f.get(p_177451_1_);
	}
}
