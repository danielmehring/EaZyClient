package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class NpcMerchant implements IMerchant {

public static final int EaZy = 1173;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Instance of Merchants Inventory. */
	private final InventoryMerchant theMerchantInventory;

	/** This merchant's current player customer. */
	private final EntityPlayer customer;

	/** The MerchantRecipeList instance. */
	private MerchantRecipeList recipeList;
	private final IChatComponent field_175548_d;
	// private static final String __OBFID = "http://https://fuckuskid00001705";

	public NpcMerchant(final EntityPlayer p_i45817_1_, final IChatComponent p_i45817_2_) {
		customer = p_i45817_1_;
		field_175548_d = p_i45817_2_;
		theMerchantInventory = new InventoryMerchant(p_i45817_1_, this);
	}

	@Override
	public EntityPlayer getCustomer() {
		return customer;
	}

	@Override
	public void setCustomer(final EntityPlayer p_70932_1_) {}

	@Override
	public MerchantRecipeList getRecipes(final EntityPlayer p_70934_1_) {
		return recipeList;
	}

	@Override
	public void setRecipes(final MerchantRecipeList p_70930_1_) {
		recipeList = p_70930_1_;
	}

	@Override
	public void useRecipe(final MerchantRecipe p_70933_1_) {
		p_70933_1_.incrementToolUses();
	}

	/**
	 * Notifies the merchant of a possible merchantrecipe being fulfilled or
	 * not. Usually, this is just a sound byte being played depending if the
	 * suggested itemstack is not null.
	 */
	@Override
	public void verifySellingItem(final ItemStack p_110297_1_) {}

	@Override
	public IChatComponent getDisplayName() {
		return field_175548_d != null ? field_175548_d
				: new ChatComponentTranslation("entity.Villager.name", new Object[0]);
	}
}
