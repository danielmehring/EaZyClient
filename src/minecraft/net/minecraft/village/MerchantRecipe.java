package net.minecraft.village;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MerchantRecipe {

public static final int EaZy = 1666;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Item the Villager buys. */
	private ItemStack itemToBuy;

	/** Second Item the Villager buys. */
	private ItemStack secondItemToBuy;

	/** Item the Villager sells. */
	private ItemStack itemToSell;

	/**
	 * Saves how much has been tool used when put into to slot to be enchanted.
	 */
	private int toolUses;

	/** Maximum times this trade can be used. */
	private int maxTradeUses;
	private boolean field_180323_f;
	// private static final String __OBFID = "http://https://fuckuskid00000126";

	public MerchantRecipe(final NBTTagCompound p_i1940_1_) {
		readFromTags(p_i1940_1_);
	}

	public MerchantRecipe(final ItemStack p_i1941_1_, final ItemStack p_i1941_2_, final ItemStack p_i1941_3_) {
		this(p_i1941_1_, p_i1941_2_, p_i1941_3_, 0, 7);
	}

	public MerchantRecipe(final ItemStack p_i45760_1_, final ItemStack p_i45760_2_, final ItemStack p_i45760_3_,
			final int p_i45760_4_, final int p_i45760_5_) {
		itemToBuy = p_i45760_1_;
		secondItemToBuy = p_i45760_2_;
		itemToSell = p_i45760_3_;
		toolUses = p_i45760_4_;
		maxTradeUses = p_i45760_5_;
		field_180323_f = true;
	}

	public MerchantRecipe(final ItemStack p_i1942_1_, final ItemStack p_i1942_2_) {
		this(p_i1942_1_, (ItemStack) null, p_i1942_2_);
	}

	public MerchantRecipe(final ItemStack p_i1943_1_, final Item p_i1943_2_) {
		this(p_i1943_1_, new ItemStack(p_i1943_2_));
	}

	/**
	 * Gets the itemToBuy.
	 */
	public ItemStack getItemToBuy() {
		return itemToBuy;
	}

	/**
	 * Gets secondItemToBuy.
	 */
	public ItemStack getSecondItemToBuy() {
		return secondItemToBuy;
	}

	/**
	 * Gets if Villager has secondItemToBuy.
	 */
	public boolean hasSecondItemToBuy() {
		return secondItemToBuy != null;
	}

	/**
	 * Gets itemToSell.
	 */
	public ItemStack getItemToSell() {
		return itemToSell;
	}

	public int func_180321_e() {
		return toolUses;
	}

	public int func_180320_f() {
		return maxTradeUses;
	}

	public void incrementToolUses() {
		++toolUses;
	}

	public void func_82783_a(final int p_82783_1_) {
		maxTradeUses += p_82783_1_;
	}

	public boolean isRecipeDisabled() {
		return toolUses >= maxTradeUses;
	}

	public void func_82785_h() {
		toolUses = maxTradeUses;
	}

	public boolean func_180322_j() {
		return field_180323_f;
	}

	public void readFromTags(final NBTTagCompound p_77390_1_) {
		final NBTTagCompound var2 = p_77390_1_.getCompoundTag("buy");
		itemToBuy = ItemStack.loadItemStackFromNBT(var2);
		final NBTTagCompound var3 = p_77390_1_.getCompoundTag("sell");
		itemToSell = ItemStack.loadItemStackFromNBT(var3);

		if (p_77390_1_.hasKey("buyB", 10)) {
			secondItemToBuy = ItemStack.loadItemStackFromNBT(p_77390_1_.getCompoundTag("buyB"));
		}

		if (p_77390_1_.hasKey("uses", 99)) {
			toolUses = p_77390_1_.getInteger("uses");
		}

		if (p_77390_1_.hasKey("maxUses", 99)) {
			maxTradeUses = p_77390_1_.getInteger("maxUses");
		} else {
			maxTradeUses = 7;
		}

		if (p_77390_1_.hasKey("rewardExp", 1)) {
			field_180323_f = p_77390_1_.getBoolean("rewardExp");
		} else {
			field_180323_f = true;
		}
	}

	public NBTTagCompound writeToTags() {
		final NBTTagCompound var1 = new NBTTagCompound();
		var1.setTag("buy", itemToBuy.writeToNBT(new NBTTagCompound()));
		var1.setTag("sell", itemToSell.writeToNBT(new NBTTagCompound()));

		if (secondItemToBuy != null) {
			var1.setTag("buyB", secondItemToBuy.writeToNBT(new NBTTagCompound()));
		}

		var1.setInteger("uses", toolUses);
		var1.setInteger("maxUses", maxTradeUses);
		var1.setBoolean("rewardExp", field_180323_f);
		return var1;
	}
}
