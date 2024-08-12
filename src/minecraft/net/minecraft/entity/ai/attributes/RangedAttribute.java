package net.minecraft.entity.ai.attributes;

import net.minecraft.util.MathHelper;

public class RangedAttribute extends BaseAttribute {

public static final int EaZy = 1040;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final double minimumValue;
	private final double maximumValue;
	private String description;
	// private static final String __OBFID = "http://https://fuckuskid00001568";

	public RangedAttribute(final IAttribute p_i45891_1_, final String p_i45891_2_, final double p_i45891_3_,
			final double p_i45891_5_, final double p_i45891_7_) {
		super(p_i45891_1_, p_i45891_2_, p_i45891_3_);
		minimumValue = p_i45891_5_;
		maximumValue = p_i45891_7_;

		if (p_i45891_5_ > p_i45891_7_) {
			throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
		} else if (p_i45891_3_ < p_i45891_5_) {
			throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
		} else if (p_i45891_3_ > p_i45891_7_) {
			throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
		}
	}

	public RangedAttribute setDescription(final String p_111117_1_) {
		description = p_111117_1_;
		return this;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public double clampValue(double p_111109_1_) {
		p_111109_1_ = MathHelper.clamp_double(p_111109_1_, minimumValue, maximumValue);
		return p_111109_1_;
	}
}
