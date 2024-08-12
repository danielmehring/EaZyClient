package net.minecraft.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RenderPotion extends RenderSnowball {

public static final int EaZy = 776;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002430";

	public RenderPotion(final RenderManager p_i46136_1_, final RenderItem p_i46136_2_) {
		super(p_i46136_1_, Items.potionitem, p_i46136_2_);
	}

	public ItemStack func_177085_a(final EntityPotion p_177085_1_) {
		return new ItemStack(field_177084_a, 1, p_177085_1_.getPotionDamage());
	}

	@Override
	public ItemStack func_177082_d(final Entity p_177082_1_) {
		return func_177085_a((EntityPotion) p_177082_1_);
	}
}
