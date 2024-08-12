package net.minecraft.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;

import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

public class NetworkPlayerInfo {

public static final int EaZy = 631;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public final GameProfile gameProfile;
	private WorldSettings.GameType gameType;

	/** Player response time to server in milliseconds */
	public int responseTime;
	private boolean field_178864_d = false;
	private ResourceLocation field_178865_e;
	private ResourceLocation field_178862_f;
	private String field_178863_g;
	private IChatComponent field_178872_h;
	private int field_178873_i = 0;
	private int field_178870_j = 0;
	private long field_178871_k = 0L;
	private long field_178868_l = 0L;
	private long field_178869_m = 0L;
	// private static final String __OBFID = "http://https://fuckuskid00000888";

	public NetworkPlayerInfo(final GameProfile p_i46294_1_) {
		gameProfile = p_i46294_1_;
	}

	public NetworkPlayerInfo(final S38PacketPlayerListItem.AddPlayerData p_i46295_1_) {
		gameProfile = p_i46295_1_.func_179962_a();
		gameType = p_i46295_1_.func_179960_c();
		responseTime = p_i46295_1_.func_179963_b();
	}

	public GameProfile func_178845_a() {
		return gameProfile;
	}

	public WorldSettings.GameType getGameType() {
		return gameType;
	}

	public int getResponseTime() {
		return responseTime;
	}

	protected void func_178839_a(final WorldSettings.GameType p_178839_1_) {
		gameType = p_178839_1_;
	}

	protected void func_178838_a(final int p_178838_1_) {
		responseTime = p_178838_1_;
	}

	public boolean func_178856_e() {
		return field_178865_e != null;
	}

	public String func_178851_f() {
		return field_178863_g == null ? DefaultPlayerSkin.getModelNameFromUUID(gameProfile.getId()) : field_178863_g;
	}

	public ResourceLocation func_178837_g() {
		if (field_178865_e == null) {
			func_178841_j();
		}

		return Objects.firstNonNull(field_178865_e, DefaultPlayerSkin.func_177334_a(gameProfile.getId()));
	}

	public ResourceLocation func_178861_h() {
		if (field_178862_f == null) {
			func_178841_j();
		}

		return field_178862_f;
	}

	public ScorePlayerTeam func_178850_i() {

		return Minecraft.theWorld.getScoreboard().getPlayersTeam(func_178845_a().getName());
	}

	protected void func_178841_j() {
		synchronized (this) {
			if (!field_178864_d) {
				field_178864_d = true;
				Minecraft.getMinecraft().getSkinManager().func_152790_a(gameProfile,
						new SkinManager.SkinAvailableCallback() {
							// private static final String __OBFID =
							// "http://https://fuckuskid00002619";

							@Override
							public void func_180521_a(final Type p_180521_1_, final ResourceLocation p_180521_2_,
									final MinecraftProfileTexture p_180521_3_) {
								switch (NetworkPlayerInfo.SwitchType.field_178875_a[p_180521_1_.ordinal()]) {
									case 1:
										field_178865_e = p_180521_2_;
										field_178863_g = p_180521_3_.getMetadata("model");

										if (field_178863_g == null) {
											field_178863_g = "default";
										}

										break;

									case 2:
										field_178862_f = p_180521_2_;
								}
							}
						}, true);
			}
		}
	}

	public void func_178859_a(final IChatComponent p_178859_1_) {
		field_178872_h = p_178859_1_;
	}

	public IChatComponent func_178854_k() {
		return field_178872_h;
	}

	public IChatComponent getPlayerName() {
		return field_178872_h;
	}

	public GameProfile getGameProfile() {
		return gameProfile;
	}

	public ScorePlayerTeam getScoreTeam() {

		return Minecraft.theWorld.getScoreboard().getPlayersTeam(getGameProfile().getName());
	}

	public String getPlayerNameForReal() {
		return getPlayerName() != null ? getPlayerName().getFormattedText()
				: ScorePlayerTeam.formatPlayerName(getScoreTeam(), getGameProfile().getName());
	}

	public int func_178835_l() {
		return field_178873_i;
	}

	public void func_178836_b(final int p_178836_1_) {
		field_178873_i = p_178836_1_;
	}

	public int func_178860_m() {
		return field_178870_j;
	}

	public void func_178857_c(final int p_178857_1_) {
		field_178870_j = p_178857_1_;
	}

	public long func_178847_n() {
		return field_178871_k;
	}

	public void func_178846_a(final long p_178846_1_) {
		field_178871_k = p_178846_1_;
	}

	public long func_178858_o() {
		return field_178868_l;
	}

	public void func_178844_b(final long p_178844_1_) {
		field_178868_l = p_178844_1_;
	}

	public long func_178855_p() {
		return field_178869_m;
	}

	public void func_178843_c(final long p_178843_1_) {
		field_178869_m = p_178843_1_;
	}

	static final class SwitchType {
		static final int[] field_178875_a = new int[Type.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002618";

		static {
			try {
				field_178875_a[Type.SKIN.ordinal()] = 1;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_178875_a[Type.CAPE.ordinal()] = 2;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
