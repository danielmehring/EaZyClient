package net.minecraft.scoreboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.util.Iterator;
import java.util.List;

public class ScoreHealthCriteria extends ScoreDummyCriteria {

public static final int EaZy = 1525;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000623";

	public ScoreHealthCriteria(final String p_i2312_1_) {
		super(p_i2312_1_);
	}

	@Override
	public int func_96635_a(final List p_96635_1_) {
		float var2 = 0.0F;
		EntityPlayer var4;

		for (final Iterator var3 = p_96635_1_.iterator(); var3
				.hasNext(); var2 += var4.getHealth() + var4.getAbsorptionAmount()) {
			var4 = (EntityPlayer) var3.next();
		}

		if (p_96635_1_.size() > 0) {
			var2 /= p_96635_1_.size();
		}

		return MathHelper.ceiling_float_int(var2);
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@Override
	public IScoreObjectiveCriteria.EnumRenderType func_178790_c() {
		return IScoreObjectiveCriteria.EnumRenderType.HEARTS;
	}
}
