package net.minecraft.client.stream;

import tv.twitch.ErrorCode;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.chat.ChatUserInfo;

public class NullStream implements IStream {

public static final int EaZy = 920;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Throwable field_152938_a;
	// private static final String __OBFID = "http://https://fuckuskid00001809";

	public NullStream(final Throwable p_i1006_1_) {
		field_152938_a = p_i1006_1_;
	}

	/**
	 * Shuts down a steam
	 */
	@Override
	public void shutdownStream() {}

	@Override
	public void func_152935_j() {}

	@Override
	public void func_152922_k() {}

	@Override
	public boolean func_152936_l() {
		return false;
	}

	@Override
	public boolean func_152924_m() {
		return false;
	}

	@Override
	public boolean func_152934_n() {
		return false;
	}

	@Override
	public void func_152911_a(final Metadata p_152911_1_, final long p_152911_2_) {}

	@Override
	public void func_176026_a(final Metadata p_176026_1_, final long p_176026_2_, final long p_176026_4_) {}

	@Override
	public boolean isPaused() {
		return false;
	}

	@Override
	public void func_152931_p() {}

	@Override
	public void func_152916_q() {}

	@Override
	public void func_152933_r() {}

	@Override
	public void func_152915_s() {}

	@Override
	public void func_152930_t() {}

	@Override
	public void func_152914_u() {}

	@Override
	public IngestServer[] func_152925_v() {
		return new IngestServer[0];
	}

	@Override
	public void func_152909_x() {}

	@Override
	public IngestServerTester func_152932_y() {
		return null;
	}

	@Override
	public boolean func_152908_z() {
		return false;
	}

	@Override
	public int func_152920_A() {
		return 0;
	}

	@Override
	public boolean func_152927_B() {
		return false;
	}

	@Override
	public String func_152921_C() {
		return null;
	}

	@Override
	public ChatUserInfo func_152926_a(final String p_152926_1_) {
		return null;
	}

	@Override
	public void func_152917_b(final String p_152917_1_) {}

	@Override
	public boolean func_152928_D() {
		return false;
	}

	@Override
	public ErrorCode func_152912_E() {
		return null;
	}

	@Override
	public boolean func_152913_F() {
		return false;
	}

	@Override
	public void func_152910_a(final boolean p_152910_1_) {}

	@Override
	public boolean func_152929_G() {
		return false;
	}

	@Override
	public IStream.AuthFailureReason func_152918_H() {
		return IStream.AuthFailureReason.ERROR;
	}

	public Throwable func_152937_a() {
		return field_152938_a;
	}
}
