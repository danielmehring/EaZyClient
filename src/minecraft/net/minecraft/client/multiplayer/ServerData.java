package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class ServerData {

public static final int EaZy = 623;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public String serverName;
	public String serverIP;

	/**
	 * the string indicating number of players on and capacity of the server
	 * that is shown on the server browser (i.e. "5/20" meaning 5 slots used out
	 * of 20 slots total)
	 */
	public String populationInfo;

	/**
	 * (better variable name would be 'hostname') server name as displayed in
	 * the server browser's second line (grey text)
	 */
	public String serverMOTD;

	/** last server ping that showed up in the server browser */
	public long pingToServer;
	public int version = 47;

	/** Game version for this server. */
	public String gameVersion = "1.8";
	public boolean field_78841_f;
	public String playerList;
	private ServerData.ServerResourceMode resourceMode;
	private String serverIcon;
	// private static final String __OBFID = "http://https://fuckuskid00000890";

	public ServerData(final String p_i1193_1_, final String p_i1193_2_) {
		resourceMode = ServerData.ServerResourceMode.PROMPT;
		serverName = p_i1193_1_;
		serverIP = p_i1193_2_;
	}

	/**
	 * Returns an NBTTagCompound with the server's name, IP and maybe
	 * acceptTextures.
	 */
	public NBTTagCompound getNBTCompound() {
		final NBTTagCompound var1 = new NBTTagCompound();
		var1.setString("name", serverName);
		var1.setString("ip", serverIP);

		if (serverIcon != null) {
			var1.setString("icon", serverIcon);
		}

		if (resourceMode == ServerData.ServerResourceMode.ENABLED) {
			var1.setBoolean("acceptTextures", true);
		} else if (resourceMode == ServerData.ServerResourceMode.DISABLED) {
			var1.setBoolean("acceptTextures", false);
		}

		return var1;
	}

	public ServerData.ServerResourceMode getResourceMode() {
		return resourceMode;
	}

	public void setResourceMode(final ServerData.ServerResourceMode mode) {
		resourceMode = mode;
	}

	/**
	 * Takes an NBTTagCompound with 'name' and 'ip' keys, returns a ServerData
	 * instance.
	 */
	public static ServerData getServerDataFromNBTCompound(final NBTTagCompound nbtCompound) {
		final ServerData var1 = new ServerData(nbtCompound.getString("name"), nbtCompound.getString("ip"));

		if (nbtCompound.hasKey("icon", 8)) {
			var1.setBase64EncodedIconData(nbtCompound.getString("icon"));
		}

		if (nbtCompound.hasKey("acceptTextures", 1)) {
			if (nbtCompound.getBoolean("acceptTextures")) {
				var1.setResourceMode(ServerData.ServerResourceMode.ENABLED);
			} else {
				var1.setResourceMode(ServerData.ServerResourceMode.DISABLED);
			}
		} else {
			var1.setResourceMode(ServerData.ServerResourceMode.PROMPT);
		}

		return var1;
	}

	/**
	 * Returns the base-64 encoded representation of the server's icon, or null
	 * if not available
	 */
	public String getBase64EncodedIconData() {
		return serverIcon;
	}

	public void setBase64EncodedIconData(final String icon) {
		serverIcon = icon;
	}

	public void copyFrom(final ServerData serverDataIn) {
		serverIP = serverDataIn.serverIP;
		serverName = serverDataIn.serverName;
		setResourceMode(serverDataIn.getResourceMode());
		serverIcon = serverDataIn.serverIcon;
	}

	public static enum ServerResourceMode {
		ENABLED("ENABLED", 0, "enabled"), DISABLED("DISABLED", 1, "disabled"), PROMPT("PROMPT", 2, "prompt");
		private final IChatComponent motd;

		private ServerResourceMode(final String p_i1053_1_, final int p_i1053_2_, final String p_i1053_3_) {
			motd = new ChatComponentTranslation("addServer.resourcePack." + p_i1053_3_, new Object[0]);
		}

		public IChatComponent getMotd() {
			return motd;
		}
	}
}
