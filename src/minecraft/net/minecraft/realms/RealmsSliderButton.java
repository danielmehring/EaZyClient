package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class RealmsSliderButton extends RealmsButton {

public static final int EaZy = 1517;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public float value;
	public boolean sliding;
	private final float minValue;
	private final float maxValue;
	private int steps;
	// private static final String __OBFID = "http://https://fuckuskid00001834";

	public RealmsSliderButton(final int p_i1056_1_, final int p_i1056_2_, final int p_i1056_3_, final int p_i1056_4_,
			final int p_i1056_5_, final int p_i1056_6_) {
		this(p_i1056_1_, p_i1056_2_, p_i1056_3_, p_i1056_4_, p_i1056_6_, 0, 1.0F, p_i1056_5_);
	}

	public RealmsSliderButton(final int p_i1057_1_, final int p_i1057_2_, final int p_i1057_3_, final int p_i1057_4_,
			final int p_i1057_5_, final int p_i1057_6_, final float p_i1057_7_, final float p_i1057_8_) {
		super(p_i1057_1_, p_i1057_2_, p_i1057_3_, p_i1057_4_, 20, "");
		value = 1.0F;
		minValue = p_i1057_7_;
		maxValue = p_i1057_8_;
		value = toPct(p_i1057_6_);
		getProxy().displayString = getMessage();
	}

	public String getMessage() {
		return "";
	}

	public float toPct(final float p_toPct_1_) {
		return MathHelper.clamp_float((clamp(p_toPct_1_) - minValue) / (maxValue - minValue), 0.0F, 1.0F);
	}

	public float toValue(final float p_toValue_1_) {
		return clamp(minValue + (maxValue - minValue) * MathHelper.clamp_float(p_toValue_1_, 0.0F, 1.0F));
	}

	public float clamp(float p_clamp_1_) {
		p_clamp_1_ = clampSteps(p_clamp_1_);
		return MathHelper.clamp_float(p_clamp_1_, minValue, maxValue);
	}

	protected float clampSteps(float p_clampSteps_1_) {
		if (steps > 0) {
			p_clampSteps_1_ = steps * Math.round(p_clampSteps_1_ / steps);
		}

		return p_clampSteps_1_;
	}

	@Override
	public int getYImage(final boolean p_getYImage_1_) {
		return 0;
	}

	@Override
	public void renderBg(final int p_renderBg_1_, final int p_renderBg_2_) {
		if (getProxy().visible) {
			if (sliding) {
				value = (float) (p_renderBg_1_ - (getProxy().xPosition + 4))
						/ (float) (getProxy().getButtonWidth() - 8);
				value = MathHelper.clamp_float(value, 0.0F, 1.0F);
				final float var3 = toValue(value);
				this.clicked(var3);
				value = toPct(var3);
				getProxy().displayString = getMessage();
			}

			Minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			blit(getProxy().xPosition + (int) (value * (getProxy().getButtonWidth() - 8)), getProxy().yPosition, 0, 66,
					4, 20);
			blit(getProxy().xPosition + (int) (value * (getProxy().getButtonWidth() - 8)) + 4, getProxy().yPosition,
					196, 66, 4, 20);
		}
	}

	@Override
	public void clicked(final int p_clicked_1_, final int p_clicked_2_) {
		value = (float) (p_clicked_1_ - (getProxy().xPosition + 4)) / (float) (getProxy().getButtonWidth() - 8);
		value = MathHelper.clamp_float(value, 0.0F, 1.0F);
		this.clicked(toValue(value));
		getProxy().displayString = getMessage();
		sliding = true;
	}

	public void clicked(final float p_clicked_1_) {}

	@Override
	public void released(final int p_released_1_, final int p_released_2_) {
		sliding = false;
	}
}
