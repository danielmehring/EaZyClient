package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemFood extends Item {

public static final int EaZy = 1299;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Number of ticks to run while 'EnumAction'ing until result. */
	public final int itemUseDuration;

	/** The amount this food item heals the player. */
	private final int healAmount;
	private final float saturationModifier;

	/** Whether wolves like this food (true for raw and cooked porkchop). */
	private final boolean isWolfsFavoriteMeat;

	/**
	 * If this field is true, the food can be consumed even if the player don't
	 * need to eat.
	 */
	private boolean alwaysEdible;

	/**
	 * represents the potion effect that will occurr upon eating this food. Set
	 * by setPotionEffect
	 */
	private int potionId;

	/** set by setPotionEffect */
	private int potionDuration;

	/** set by setPotionEffect */
	private int potionAmplifier;

	/** probably of the set potion effect occurring */
	private float potionEffectProbability;
	// private static final String __OBFID = "http://https://fuckuskid00000036";

	public ItemFood(final int p_i45339_1_, final float p_i45339_2_, final boolean p_i45339_3_) {
		itemUseDuration = 32;
		healAmount = p_i45339_1_;
		isWolfsFavoriteMeat = p_i45339_3_;
		saturationModifier = p_i45339_2_;
		setCreativeTab(CreativeTabs.tabFood);
	}

	public ItemFood(final int p_i45340_1_, final boolean p_i45340_2_) {
		this(p_i45340_1_, 0.6F, p_i45340_2_);
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.).
	 * Not called when the player stops using the Item before the action is
	 * complete.
	 */
	@Override
	public ItemStack onItemUseFinish(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
		--stack.stackSize;
		playerIn.getFoodStats().addStats(this, stack);
		worldIn.playSoundAtEntity(playerIn, "random.burp", 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
		onFoodEaten(stack, worldIn, playerIn);
		playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		return stack;
	}

	protected void onFoodEaten(final ItemStack p_77849_1_, final World worldIn, final EntityPlayer p_77849_3_) {
		if (!worldIn.isRemote && potionId > 0 && worldIn.rand.nextFloat() < potionEffectProbability) {
			p_77849_3_.addPotionEffect(new PotionEffect(potionId, potionDuration * 20, potionAmplifier));
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return EnumAction.EAT;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		if (playerIn.canEat(alwaysEdible)) {
			playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		}

		return itemStackIn;
	}

	public int getHealAmount(final ItemStack itemStackIn) {
		return healAmount;
	}

	public float getSaturationModifier(final ItemStack itemStackIn) {
		return saturationModifier;
	}

	/**
	 * Whether wolves like this food (true for raw and cooked porkchop).
	 */
	public boolean isWolfsFavoriteMeat() {
		return isWolfsFavoriteMeat;
	}

	/**
	 * sets a potion effect on the item. Args: int potionId, int duration (will
	 * be multiplied by 20), int amplifier, float probability of effect
	 * happening
	 */
	public ItemFood setPotionEffect(final int p_77844_1_, final int duration, final int amplifier,
			final float probability) {
		potionId = p_77844_1_;
		potionDuration = duration;
		potionAmplifier = amplifier;
		potionEffectProbability = probability;
		return this;
	}

	/**
	 * Set the field 'alwaysEdible' to true, and make the food edible even if
	 * the player don't need to eat.
	 */
	public ItemFood setAlwaysEdible() {
		alwaysEdible = true;
		return this;
	}
}
