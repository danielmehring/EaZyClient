package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ItemNameTag extends Item {

public static final int EaZy = 1311;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000052";

	public ItemNameTag() {
		setCreativeTab(CreativeTabs.tabTools);
	}

	/**
	 * Returns true if the item can be used on the given entity, e.g. shears on
	 * sheep.
	 */
	@Override
	public boolean itemInteractionForEntity(final ItemStack stack, final EntityPlayer playerIn,
			final EntityLivingBase target) {
		if (!stack.hasDisplayName()) {
			return false;
		} else if (target instanceof EntityLiving) {
			final EntityLiving var4 = (EntityLiving) target;
			var4.setCustomNameTag(stack.getDisplayName());
			var4.enablePersistence();
			--stack.stackSize;
			return true;
		} else {
			return super.itemInteractionForEntity(stack, playerIn, target);
		}
	}
}
