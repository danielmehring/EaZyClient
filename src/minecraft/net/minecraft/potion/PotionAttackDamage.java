package net.minecraft.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;

public class PotionAttackDamage extends Potion {

public static final int EaZy = 1492;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001525";

	protected PotionAttackDamage(final int p_i45900_1_, final ResourceLocation p_i45900_2_, final boolean p_i45900_3_,
			final int p_i45900_4_) {
		super(p_i45900_1_, p_i45900_2_, p_i45900_3_, p_i45900_4_);
	}

	@Override
	public double func_111183_a(final int p_111183_1_, final AttributeModifier p_111183_2_) {
		return id == Potion.weakness.id ? (double) (-0.5F * (p_111183_1_ + 1)) : 1.3D * (p_111183_1_ + 1);
	}
}
