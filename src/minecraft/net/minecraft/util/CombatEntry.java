package net.minecraft.util;

import net.minecraft.entity.EntityLivingBase;

public class CombatEntry {

public static final int EaZy = 1603;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final DamageSource damageSrc;
	private final float field_94568_c;
	private final String field_94566_e;
	private final float field_94564_f;
	// private static final String __OBFID = "http://https://fuckuskid00001519";

	public CombatEntry(final DamageSource p_i1564_1_, final int p_i1564_2_, final float p_i1564_3_,
			final float p_i1564_4_, final String p_i1564_5_, final float p_i1564_6_) {
		damageSrc = p_i1564_1_;
		field_94568_c = p_i1564_4_;
		field_94566_e = p_i1564_5_;
		field_94564_f = p_i1564_6_;
	}

	/**
	 * Get the DamageSource of the CombatEntry instance.
	 */
	public DamageSource getDamageSrc() {
		return damageSrc;
	}

	public float func_94563_c() {
		return field_94568_c;
	}

	public boolean func_94559_f() {
		return damageSrc.getEntity() instanceof EntityLivingBase;
	}

	public String func_94562_g() {
		return field_94566_e;
	}

	public IChatComponent func_151522_h() {
		return getDamageSrc().getEntity() == null ? null : getDamageSrc().getEntity().getDisplayName();
	}

	public float func_94561_i() {
		return damageSrc == DamageSource.outOfWorld ? Float.MAX_VALUE : field_94564_f;
	}
}
