package net.minecraft.client.multiplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class ServerList {

public static final int EaZy = 624;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();

	/** The Minecraft instance. */
	private final Minecraft mc;

	/** List of ServerData instances. */
	private final List servers = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00000891";

	public ServerList(final Minecraft mcIn) {
		mc = mcIn;
		loadServerList();
	}

	/**
	 * Loads a list of servers from servers.dat, by running
	 * ServerData.getServerDataFromNBTCompound on each NBT compound found in the
	 * "servers" tag list.
	 */
	public void loadServerList() {
		try {
			servers.clear();
			final NBTTagCompound var1 = CompressedStreamTools.read(new File(mc.mcDataDir, "servers.dat"));

			if (var1 == null) {
				return;
			}

			final NBTTagList var2 = var1.getTagList("servers", 10);

			for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
				servers.add(ServerData.getServerDataFromNBTCompound(var2.getCompoundTagAt(var3)));
			}
		} catch (final Exception var4) {
			logger.error("Couldn\'t load server list", var4);
		}
	}

	/**
	 * Runs getNBTCompound on each ServerData instance, puts everything into a
	 * "servers" NBT list and writes it to servers.dat.
	 */
	public void saveServerList() {
		try {
			final NBTTagList var1 = new NBTTagList();
			final Iterator var2 = servers.iterator();

			while (var2.hasNext()) {
				final ServerData var3 = (ServerData) var2.next();
				var1.appendTag(var3.getNBTCompound());
			}

			final NBTTagCompound var5 = new NBTTagCompound();
			var5.setTag("servers", var1);
			CompressedStreamTools.safeWrite(var5, new File(mc.mcDataDir, "servers.dat"));
		} catch (final Exception var4) {
			logger.error("Couldn\'t save server list", var4);
		}
	}

	/**
	 * Gets the ServerData instance stored for the given index in the list.
	 */
	public ServerData getServerData(final int p_78850_1_) {
		return (ServerData) servers.get(p_78850_1_);
	}

	/**
	 * Removes the ServerData instance stored for the given index in the list.
	 */
	public void removeServerData(final int p_78851_1_) {
		servers.remove(p_78851_1_);
	}

	/**
	 * Adds the given ServerData instance to the list.
	 */
	public void addServerData(final ServerData p_78849_1_) {
		servers.add(p_78849_1_);
	}

	/**
	 * Counts the number of ServerData instances in the list.
	 */
	public int countServers() {
		return servers.size();
	}

	/**
	 * Takes two list indexes, and swaps their order around.
	 */
	public void swapServers(final int p_78857_1_, final int p_78857_2_) {
		final ServerData var3 = getServerData(p_78857_1_);
		servers.set(p_78857_1_, getServerData(p_78857_2_));
		servers.set(p_78857_2_, var3);
		saveServerList();
	}

	public void func_147413_a(final int p_147413_1_, final ServerData p_147413_2_) {
		servers.set(p_147413_1_, p_147413_2_);
	}

	public static void func_147414_b(final ServerData p_147414_0_) {
		final ServerList var1 = new ServerList(Minecraft.getMinecraft());
		var1.loadServerList();

		for (int var2 = 0; var2 < var1.countServers(); ++var2) {
			final ServerData var3 = var1.getServerData(var2);

			if (var3.serverName.equals(p_147414_0_.serverName) && var3.serverIP.equals(p_147414_0_.serverIP)) {
				var1.func_147413_a(var2, p_147414_0_);
				break;
			}
		}

		var1.saveServerList();
	}
}
