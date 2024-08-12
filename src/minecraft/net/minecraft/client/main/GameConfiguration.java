package net.minecraft.client.main;

import net.minecraft.util.Session;

import java.io.File;
import java.net.Proxy;

import com.mojang.authlib.properties.PropertyMap;

public class GameConfiguration {

	public static final int EaZy = 564;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public final GameConfiguration.UserInformation userInfo;
	public final GameConfiguration.DisplayInformation displayInfo;
	public final GameConfiguration.FolderInformation folderInfo;
	public final GameConfiguration.GameInformation gameInfo;
	public final GameConfiguration.ServerInformation serverInfo;
	// private static final String __OBFID = "http://https://fuckuskid00001918";

	public GameConfiguration(final GameConfiguration.UserInformation _userInfo,
			final GameConfiguration.DisplayInformation _displayInfo,
			final GameConfiguration.FolderInformation _folderInfo, final GameConfiguration.GameInformation _gameInfo,
			final GameConfiguration.ServerInformation _serverInfo) {
		userInfo = _userInfo;
		displayInfo = _displayInfo;
		folderInfo = _folderInfo;
		gameInfo = _gameInfo;
		serverInfo = _serverInfo;
	}

	public static class DisplayInformation {
		public final int field_178764_a;
		public final int field_178762_b;
		public final boolean field_178763_c;
		public final boolean field_178761_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001917";

		public DisplayInformation(final int p_i45490_1_, final int p_i45490_2_, final boolean p_i45490_3_,
				final boolean p_i45490_4_) {
			field_178764_a = p_i45490_1_;
			field_178762_b = p_i45490_2_;
			field_178763_c = p_i45490_3_;
			field_178761_d = p_i45490_4_;
		}
	}

	public static class FolderInformation {
		public final File mcDataDir;
		public final File fileResourcepacks;
		public final File fileAssets;
		public final String field_178757_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001916";

		public FolderInformation(final File p_i45489_1_, final File p_i45489_2_, final File p_i45489_3_,
				final String p_i45489_4_) {
			mcDataDir = p_i45489_1_;
			fileResourcepacks = p_i45489_2_;
			fileAssets = p_i45489_3_;
			field_178757_d = p_i45489_4_;
		}
	}

	public static class GameInformation {
		public final boolean field_178756_a;
		public final String field_178755_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001915";

		public GameInformation(final boolean p_i45488_1_, final String p_i45488_2_) {
			field_178756_a = p_i45488_1_;
			field_178755_b = p_i45488_2_;
		}
	}

	public static class ServerInformation {
		public final String field_178754_a;
		public final int field_178753_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001914";

		public ServerInformation(final String p_i45487_1_, final int p_i45487_2_) {
			field_178754_a = p_i45487_1_;
			field_178753_b = p_i45487_2_;
		}
	}

	public static class UserInformation {
		public final Session field_178752_a;
		public final PropertyMap field_178750_b;
		public final Proxy field_178751_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001913";

		public UserInformation(final Session p_i45486_1_, final PropertyMap p_i45486_2_, final Proxy p_i45486_3_) {
			field_178752_a = p_i45486_1_;
			field_178750_b = p_i45486_2_;
			field_178751_c = p_i45486_3_;
		}
	}
}
