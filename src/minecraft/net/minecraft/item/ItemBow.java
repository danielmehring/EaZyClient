package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBow extends Item {

public static final int EaZy = 1276;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final String[] bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
	// private static final String __OBFID = "http://https://fuckuskid00001777";

	public ItemBow() {
		maxStackSize = 1;
		setMaxDamage(384);
		setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse
	 * button).
	 * 
	 * @param timeLeft
	 *            The amount of ticks left before the using would have been
	 *            complete
	 */
	@Override
	public void onPlayerStoppedUsing(final ItemStack stack, final World worldIn, final EntityPlayer playerIn,
			final int timeLeft) {
		final boolean var5 = playerIn.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

		if (var5 || playerIn.inventory.hasItem(Items.arrow)) {
			final int var6 = getMaxItemUseDuration(stack) - timeLeft;
			float var7 = var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

			if (var7 < 0.1D) {
				return;
			}
			// Bow Pulling
			if (var7 > 1.0F) {
				var7 = 1.0F;
			}

			final EntityArrow var8 = new EntityArrow(worldIn, playerIn, var7 * 2.0F);

			if (var7 == 1.0F) {
				var8.setIsCritical(true);
			}

			final int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

			if (var9 > 0) {
				var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
			}

			final int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

			if (var10 > 0) {
				var8.setKnockbackStrength(var10);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
				var8.setFire(100);
			}

			stack.damageItem(1, playerIn);
			worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

			if (var5) {
				var8.canBePickedUp = 2;
			} else {
				playerIn.inventory.consumeInventoryItem(Items.arrow);
			}

			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(var8);
			}
		}
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.).
	 * Not called when the player stops using the Item before the action is
	 * complete.
	 */
	@Override
	public ItemStack onItemUseFinish(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
		return stack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return EnumAction.BOW;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(Items.arrow)) {
			playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		}

		return itemStackIn;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return 1;
	}
}
