package net.minecraft.entity.boss;

public final class BossStatus {

public static final int EaZy = 1100;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static float healthScale;
	public static int statusBarTime;
	public static String bossName;
	public static boolean hasColorModifier;
	// private static final String __OBFID = "http://https://fuckuskid00000941";

	public static void setBossStatus(final IBossDisplayData p_82824_0_, final boolean p_82824_1_) {
		healthScale = p_82824_0_.getHealth() / p_82824_0_.getMaxHealth();
		statusBarTime = 100;
		bossName = p_82824_0_.getDisplayName().getFormattedText();
		hasColorModifier = p_82824_1_;
	}
}
