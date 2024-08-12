package net.minecraft.server.management;

import java.util.Date;

import com.google.gson.JsonObject;

public class IPBanEntry extends BanEntry {

public static final int EaZy = 1536;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001883";

	public IPBanEntry(final String p_i46330_1_) {
		this(p_i46330_1_, (Date) null, (String) null, (Date) null, (String) null);
	}

	public IPBanEntry(final String p_i1159_1_, final Date p_i1159_2_, final String p_i1159_3_, final Date p_i1159_4_,
			final String p_i1159_5_) {
		super(p_i1159_1_, p_i1159_2_, p_i1159_3_, p_i1159_4_, p_i1159_5_);
	}

	public IPBanEntry(final JsonObject p_i46331_1_) {
		super(func_152647_b(p_i46331_1_), p_i46331_1_);
	}

	private static String func_152647_b(final JsonObject p_152647_0_) {
		return p_152647_0_.has("ip") ? p_152647_0_.get("ip").getAsString() : null;
	}

	@Override
	protected void onSerialization(final JsonObject data) {
		if (getValue() != null) {
			data.addProperty("ip", (String) getValue());
			super.onSerialization(data);
		}
	}
}
