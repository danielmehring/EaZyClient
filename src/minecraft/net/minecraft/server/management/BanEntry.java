package net.minecraft.server.management;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

public abstract class BanEntry extends UserListEntry {

public static final int EaZy = 1534;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	protected final Date banStartDate;
	protected final String bannedBy;
	protected final Date banEndDate;
	protected final String reason;
	// private static final String __OBFID = "http://https://fuckuskid00001395";

	public BanEntry(final Object p_i46334_1_, final Date p_i46334_2_, final String p_i46334_3_, final Date p_i46334_4_,
			final String p_i46334_5_) {
		super(p_i46334_1_);
		banStartDate = p_i46334_2_ == null ? new Date() : p_i46334_2_;
		bannedBy = p_i46334_3_ == null ? "(Unknown)" : p_i46334_3_;
		banEndDate = p_i46334_4_;
		reason = p_i46334_5_ == null ? "Banned by an operator." : p_i46334_5_;
	}

	protected BanEntry(final Object p_i1174_1_, final JsonObject p_i1174_2_) {
		super(p_i1174_1_, p_i1174_2_);
		Date var3;

		try {
			var3 = p_i1174_2_.has("created") ? dateFormat.parse(p_i1174_2_.get("created").getAsString()) : new Date();
		} catch (final ParseException var7) {
			var3 = new Date();
		}

		banStartDate = var3;
		bannedBy = p_i1174_2_.has("source") ? p_i1174_2_.get("source").getAsString() : "(Unknown)";
		Date var4;

		try {
			var4 = p_i1174_2_.has("expires") ? dateFormat.parse(p_i1174_2_.get("expires").getAsString()) : null;
		} catch (final ParseException var6) {
			var4 = null;
		}

		banEndDate = var4;
		reason = p_i1174_2_.has("reason") ? p_i1174_2_.get("reason").getAsString() : "Banned by an operator.";
	}

	public Date getBanEndDate() {
		return banEndDate;
	}

	public String getBanReason() {
		return reason;
	}

	@Override
	boolean hasBanExpired() {
		return banEndDate == null ? false : banEndDate.before(new Date());
	}

	@Override
	protected void onSerialization(final JsonObject data) {
		data.addProperty("created", dateFormat.format(banStartDate));
		data.addProperty("source", bannedBy);
		data.addProperty("expires", banEndDate == null ? "forever" : dateFormat.format(banEndDate));
		data.addProperty("reason", reason);
	}
}
