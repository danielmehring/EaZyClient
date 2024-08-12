package net.minecraft.client.stream;

import net.minecraft.entity.EntityLivingBase;

public class MetadataPlayerDeath extends Metadata {

public static final int EaZy = 919;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002376";

	public MetadataPlayerDeath(final EntityLivingBase p_i46066_1_, final EntityLivingBase p_i46066_2_) {
		super("player_death");

		if (p_i46066_1_ != null) {
			func_152808_a("player", p_i46066_1_.getName());
		}

		if (p_i46066_2_ != null) {
			func_152808_a("killer", p_i46066_2_.getName());
		}
	}
}
