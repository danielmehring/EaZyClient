package net.minecraft.client.stream;

import net.minecraft.stats.Achievement;

public class MetadataAchievement extends Metadata {

public static final int EaZy = 917;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001824";

	public MetadataAchievement(final Achievement p_i1032_1_) {
		super("achievement");
		func_152808_a("achievement_id", p_i1032_1_.statId);
		func_152808_a("achievement_name", p_i1032_1_.getStatName().getUnformattedText());
		func_152808_a("achievement_description", p_i1032_1_.getDescription());
		func_152807_a("Achievement \'" + p_i1032_1_.getStatName().getUnformattedText() + "\' obtained!");
	}
}
