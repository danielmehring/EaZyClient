package net.minecraft.server.integrated;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

import java.net.SocketAddress;

import com.mojang.authlib.GameProfile;

public class IntegratedPlayerList extends ServerConfigurationManager {

public static final int EaZy = 1531;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Holds the NBT data for the host player's save file, so this can be
	 * written to level.dat.
	 */
	private NBTTagCompound hostPlayerData;
	// private static final String __OBFID = "http://https://fuckuskid00001128";

	public IntegratedPlayerList(final IntegratedServer p_i1314_1_) {
		super(p_i1314_1_);
		setViewDistance(10);
	}

	/**
	 * also stores the NBTTags if this is an intergratedPlayerList
	 */
	@Override
	protected void writePlayerData(final EntityPlayerMP playerIn) {
		if (playerIn.getName().equals(func_180603_b().getServerOwner())) {
			hostPlayerData = new NBTTagCompound();
			playerIn.writeToNBT(hostPlayerData);
		}

		super.writePlayerData(playerIn);
	}

	/**
	 * checks ban-lists, then white-lists, then space for the server. Returns
	 * null on success, or an error message
	 */
	@Override
	public String allowUserToConnect(final SocketAddress address, final GameProfile profile) {
		return profile.getName().equalsIgnoreCase(func_180603_b().getServerOwner())
				&& getPlayerByUsername(profile.getName()) != null ? "That name is already taken."
						: super.allowUserToConnect(address, profile);
	}

	public IntegratedServer func_180603_b() {
		return (IntegratedServer) super.getServerInstance();
	}

	/**
	 * On integrated servers, returns the host's player data to be written to
	 * level.dat.
	 */
	@Override
	public NBTTagCompound getHostPlayerData() {
		return hostPlayerData;
	}

	@Override
	public MinecraftServer getServerInstance() {
		return func_180603_b();
	}
}
