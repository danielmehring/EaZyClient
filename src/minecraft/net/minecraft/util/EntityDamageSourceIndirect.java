package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EntityDamageSourceIndirect extends EntityDamageSource {

public static final int EaZy = 1609;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Entity indirectEntity;
	// private static final String __OBFID = "http://https://fuckuskid00001523";

	public EntityDamageSourceIndirect(final String p_i1568_1_, final Entity p_i1568_2_, final Entity p_i1568_3_) {
		super(p_i1568_1_, p_i1568_2_);
		indirectEntity = p_i1568_3_;
	}

	@Override
	public Entity getSourceOfDamage() {
		return damageSourceEntity;
	}

	@Override
	public Entity getEntity() {
		return indirectEntity;
	}

	/**
	 * Gets the death message that is displayed when the player dies
	 */
	@Override
	public IChatComponent getDeathMessage(final EntityLivingBase p_151519_1_) {
		final IChatComponent var2 = indirectEntity == null ? damageSourceEntity.getDisplayName()
				: indirectEntity.getDisplayName();
		final ItemStack var3 = indirectEntity instanceof EntityLivingBase
				? ((EntityLivingBase) indirectEntity).getHeldItem() : null;
		final String var4 = "death.attack." + damageType;
		final String var5 = var4 + ".item";
		return var3 != null && var3.hasDisplayName() && StatCollector.canTranslate(var5)
				? new ChatComponentTranslation(var5,
						new Object[] { p_151519_1_.getDisplayName(), var2, var3.getChatComponent() })
				: new ChatComponentTranslation(var4, new Object[] { p_151519_1_.getDisplayName(), var2 });
	}
}
