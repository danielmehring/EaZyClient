package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class ItemFishFood extends ItemFood {

public static final int EaZy = 1296;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Indicates whether this fish is "cooked" or not. */
	private final boolean cooked;
	// private static final String __OBFID = "http://https://fuckuskid00000032";

	public ItemFishFood(final boolean p_i45338_1_) {
		super(0, 0.0F, false);
		cooked = p_i45338_1_;
	}

	@Override
	public int getHealAmount(final ItemStack itemStackIn) {
		final ItemFishFood.FishType var2 = ItemFishFood.FishType.getFishTypeForItemStack(itemStackIn);
		return cooked && var2.getCookable() ? var2.getCookedHealAmount() : var2.getUncookedHealAmount();
	}

	@Override
	public float getSaturationModifier(final ItemStack itemStackIn) {
		final ItemFishFood.FishType var2 = ItemFishFood.FishType.getFishTypeForItemStack(itemStackIn);
		return cooked && var2.getCookable() ? var2.getCookedSaturationModifier() : var2.getUncookedSaturationModifier();
	}

	@Override
	public String getPotionEffect(final ItemStack stack) {
		return ItemFishFood.FishType.getFishTypeForItemStack(stack) == ItemFishFood.FishType.PUFFERFISH
				? PotionHelper.field_151423_m : null;
	}

	@Override
	protected void onFoodEaten(final ItemStack p_77849_1_, final World worldIn, final EntityPlayer p_77849_3_) {
		final ItemFishFood.FishType var4 = ItemFishFood.FishType.getFishTypeForItemStack(p_77849_1_);

		if (var4 == ItemFishFood.FishType.PUFFERFISH) {
			p_77849_3_.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
			p_77849_3_.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
			p_77849_3_.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
		}

		super.onFoodEaten(p_77849_1_, worldIn, p_77849_3_);
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 * 
	 * @param subItems
	 *            The List of sub-items. This is a List of ItemStacks.
	 */
	@Override
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final List subItems) {
		final ItemFishFood.FishType[] var4 = ItemFishFood.FishType.values();
		final int var5 = var4.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			final ItemFishFood.FishType var7 = var4[var6];

			if (!cooked || var7.getCookable()) {
				subItems.add(new ItemStack(this, 1, var7.getItemDamage()));
			}
		}
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		final ItemFishFood.FishType var2 = ItemFishFood.FishType.getFishTypeForItemStack(stack);
		return this.getUnlocalizedName() + "." + var2.getUnlocalizedNamePart() + "."
				+ (cooked && var2.getCookable() ? "cooked" : "raw");
	}

	public static enum FishType {
		COD("COD", 0, 0, "cod", 2, 0.1F, 5, 0.6F), SALMON("SALMON", 1, 1, "salmon", 2, 0.1F, 6, 0.8F), CLOWNFISH(
				"CLOWNFISH", 2, 2, "clownfish", 1, 0.1F), PUFFERFISH("PUFFERFISH", 3, 3, "pufferfish", 1, 0.1F);
		private static final Map itemDamageToFishTypeMap = Maps.newHashMap();
		private final int itemDamage;
		private final String unlocalizedNamePart;
		private final int uncookedHealAmount;
		private final float uncookedSaturationModifier;
		private final int cookedHealAmount;
		private final float cookedSaturationModifier;
		private boolean cookable = false;

		private FishType(final String p_i45336_1_, final int p_i45336_2_, final int p_i45336_3_,
				final String p_i45336_4_, final int p_i45336_5_, final float p_i45336_6_, final int p_i45336_7_,
				final float p_i45336_8_) {
			itemDamage = p_i45336_3_;
			unlocalizedNamePart = p_i45336_4_;
			uncookedHealAmount = p_i45336_5_;
			uncookedSaturationModifier = p_i45336_6_;
			cookedHealAmount = p_i45336_7_;
			cookedSaturationModifier = p_i45336_8_;
			cookable = true;
		}

		private FishType(final String p_i45337_1_, final int p_i45337_2_, final int p_i45337_3_,
				final String p_i45337_4_, final int p_i45337_5_, final float p_i45337_6_) {
			itemDamage = p_i45337_3_;
			unlocalizedNamePart = p_i45337_4_;
			uncookedHealAmount = p_i45337_5_;
			uncookedSaturationModifier = p_i45337_6_;
			cookedHealAmount = 0;
			cookedSaturationModifier = 0.0F;
			cookable = false;
		}

		public int getItemDamage() {
			return itemDamage;
		}

		public String getUnlocalizedNamePart() {
			return unlocalizedNamePart;
		}

		public int getUncookedHealAmount() {
			return uncookedHealAmount;
		}

		public float getUncookedSaturationModifier() {
			return uncookedSaturationModifier;
		}

		public int getCookedHealAmount() {
			return cookedHealAmount;
		}

		public float getCookedSaturationModifier() {
			return cookedSaturationModifier;
		}

		public boolean getCookable() {
			return cookable;
		}

		public static ItemFishFood.FishType getFishTypeForItemDamage(final int p_150974_0_) {
			final ItemFishFood.FishType var1 = (ItemFishFood.FishType) itemDamageToFishTypeMap
					.get(p_150974_0_);
			return var1 == null ? COD : var1;
		}

		public static ItemFishFood.FishType getFishTypeForItemStack(final ItemStack p_150978_0_) {
			return p_150978_0_.getItem() instanceof ItemFishFood ? getFishTypeForItemDamage(p_150978_0_.getMetadata())
					: COD;
		}

		static {
			final ItemFishFood.FishType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final ItemFishFood.FishType var3 = var0[var2];
				itemDamageToFishTypeMap.put(Integer.valueOf(var3.getItemDamage()), var3);
			}
		}
	}
}
