package net.minecraft.client.stream;

import net.minecraft.entity.EntityLivingBase;

public class MetadataCombat extends Metadata {

public static final int EaZy = 918;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002377";

	public MetadataCombat(final EntityLivingBase p_i46067_1_, final EntityLivingBase p_i46067_2_) {
		super("player_combat");
		func_152808_a("player", p_i46067_1_.getName());

		if (p_i46067_2_ != null) {
			func_152808_a("primary_opponent", p_i46067_2_.getName());
		}

		if (p_i46067_2_ != null) {
			func_152807_a("Combat between " + p_i46067_1_.getName() + " and " + p_i46067_2_.getName());
		} else {
			func_152807_a("Combat between " + p_i46067_1_.getName() + " and others");
		}
	}
}
