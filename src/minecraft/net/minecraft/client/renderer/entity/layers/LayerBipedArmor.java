package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

public class LayerBipedArmor extends LayerArmorBase {

public static final int EaZy = 716;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002417";

	public LayerBipedArmor(final RendererLivingEntity p_i46116_1_) {
		super(p_i46116_1_);
	}

	@Override
	protected void func_177177_a() {
		field_177189_c = new ModelBiped(0.5F);
		field_177186_d = new ModelBiped(1.0F);
	}

	protected void func_177195_a(final ModelBiped p_177195_1_, final int p_177195_2_) {
		func_177194_a(p_177195_1_);

		switch (p_177195_2_) {
			case 1:
				p_177195_1_.bipedRightLeg.showModel = true;
				p_177195_1_.bipedLeftLeg.showModel = true;
				break;

			case 2:
				p_177195_1_.bipedBody.showModel = true;
				p_177195_1_.bipedRightLeg.showModel = true;
				p_177195_1_.bipedLeftLeg.showModel = true;
				break;

			case 3:
				p_177195_1_.bipedBody.showModel = true;
				p_177195_1_.bipedRightArm.showModel = true;
				p_177195_1_.bipedLeftArm.showModel = true;
				break;

			case 4:
				p_177195_1_.bipedHead.showModel = true;
				p_177195_1_.bipedHeadwear.showModel = true;
		}
	}

	protected void func_177194_a(final ModelBiped p_177194_1_) {
		p_177194_1_.func_178719_a(false);
	}

	@Override
	protected void func_177179_a(final ModelBase p_177179_1_, final int p_177179_2_) {
		func_177195_a((ModelBiped) p_177179_1_, p_177179_2_);
	}
}
