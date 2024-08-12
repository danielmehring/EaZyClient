package net.minecraft.client.gui;

import net.minecraft.util.IChatComponent;

public class ChatLine {

public static final int EaZy = 457;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** GUI Update Counter value this Line was created at */
	private final int updateCounterCreated;
	private final IChatComponent lineString;

	/**
	 * int value to refer to existing Chat Lines, can be 0 which means
	 * unreferrable
	 */
	private final int chatLineID;
	// private static final String __OBFID = "http://https://fuckuskid00000627";

	public ChatLine(final int p_i45000_1_, final IChatComponent p_i45000_2_, final int p_i45000_3_) {
		lineString = p_i45000_2_;
		updateCounterCreated = p_i45000_1_;
		chatLineID = p_i45000_3_;
	}

	public IChatComponent getChatComponent() {
		return lineString;
	}

	public int getUpdatedCounter() {
		return updateCounterCreated;
	}

	public int getChatLineID() {
		return chatLineID;
	}
}
