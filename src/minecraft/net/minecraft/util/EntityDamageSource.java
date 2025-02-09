package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityDamageSource extends DamageSource {

public static final int EaZy = 1608;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected Entity damageSourceEntity;
	private boolean field_180140_r = false;
	// private static final String __OBFID = "http://https://fuckuskid00001522";

	public EntityDamageSource(final String p_i1567_1_, final Entity p_i1567_2_) {
		super(p_i1567_1_);
		damageSourceEntity = p_i1567_2_;
	}

	public EntityDamageSource func_180138_v() {
		field_180140_r = true;
		return this;
	}

	public boolean func_180139_w() {
		return field_180140_r;
	}

	@Override
	public Entity getEntity() {
		return damageSourceEntity;
	}

	/**
	 * Gets the death message that is displayed when the player dies
	 */
	@Override
	public IChatComponent getDeathMessage(final EntityLivingBase p_151519_1_) {
		final ItemStack var2 = damageSourceEntity instanceof EntityLivingBase
				? ((EntityLivingBase) damageSourceEntity).getHeldItem() : null;
		final String var3 = "death.attack." + damageType;
		final String var4 = var3 + ".item";
		return var2 != null && var2.hasDisplayName() && StatCollector.canTranslate(var4)
				? new ChatComponentTranslation(var4,
						new Object[] { p_151519_1_.getDisplayName(), damageSourceEntity.getDisplayName(),
								var2.getChatComponent() })
				: new ChatComponentTranslation(var3,
						new Object[] { p_151519_1_.getDisplayName(), damageSourceEntity.getDisplayName() });
	}

	/**
	 * Return whether this damage source will have its damage amount scaled
	 * based on the current difficulty.
	 */
	@Override
	public boolean isDifficultyScaled() {
		return damageSourceEntity != null && damageSourceEntity instanceof EntityLivingBase
				&& !(damageSourceEntity instanceof EntityPlayer);
	}
}
