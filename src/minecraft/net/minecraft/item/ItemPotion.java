package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ItemPotion extends Item {

public static final int EaZy = 1314;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Contains a map from integers to the list of potion effects that potions
	 * with that damage value confer (to prevent recalculating it).
	 */
	private final Map effectCache = Maps.newHashMap();
	private static final Map field_77835_b = Maps.newLinkedHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00000055";

	public ItemPotion() {
		setMaxStackSize(1);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabBrewing);
	}

	/**
	 * Returns a list of potion effects for the specified itemstack.
	 */
	public List getEffects(final ItemStack p_77832_1_) {
		if (p_77832_1_.hasTagCompound() && p_77832_1_.getTagCompound().hasKey("CustomPotionEffects", 9)) {
			final ArrayList var7 = Lists.newArrayList();
			final NBTTagList var3 = p_77832_1_.getTagCompound().getTagList("CustomPotionEffects", 10);

			for (int var4 = 0; var4 < var3.tagCount(); ++var4) {
				final NBTTagCompound var5 = var3.getCompoundTagAt(var4);
				final PotionEffect var6 = PotionEffect.readCustomPotionEffectFromNBT(var5);

				if (var6 != null) {
					var7.add(var6);
				}
			}

			return var7;
		} else {
			List var2 = (List) effectCache.get(p_77832_1_.getMetadata());

			if (var2 == null) {
				var2 = PotionHelper.getPotionEffects(p_77832_1_.getMetadata(), false);
				effectCache.put(p_77832_1_.getMetadata(), var2);
			}

			return var2;
		}
	}

	/**
	 * Returns a list of effects for the specified potion damage value.
	 */
	public List getEffects(final int p_77834_1_) {
		List var2 = (List) effectCache.get(p_77834_1_);

		if (var2 == null) {
			var2 = PotionHelper.getPotionEffects(p_77834_1_, false);
			effectCache.put(p_77834_1_, var2);
		}

		return var2;
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.).
	 * Not called when the player stops using the Item before the action is
	 * complete.
	 */
	@Override
	public ItemStack onItemUseFinish(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
		if (!playerIn.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if (!worldIn.isRemote) {
			final List var4 = this.getEffects(stack);

			if (var4 != null) {
				final Iterator var5 = var4.iterator();

				while (var5.hasNext()) {
					final PotionEffect var6 = (PotionEffect) var5.next();
					playerIn.addPotionEffect(new PotionEffect(var6));
				}
			}
		}

		playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

		if (!playerIn.capabilities.isCreativeMode) {
			if (stack.stackSize <= 0) {
				return new ItemStack(Items.glass_bottle);
			}

			playerIn.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
		}

		return stack;
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
		return EnumAction.DRINK;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		if (isSplash(itemStackIn.getMetadata())) {
			if (!playerIn.capabilities.isCreativeMode) {
				--itemStackIn.stackSize;
			}

			worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(new EntityPotion(worldIn, playerIn, itemStackIn));
			}

			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
			return itemStackIn;
		} else {
			playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
			return itemStackIn;
		}
	}

	/**
	 * returns wether or not a potion is a throwable splash potion based on
	 * damage value
	 */
	public static boolean isSplash(final int p_77831_0_) {
		return (p_77831_0_ & 16384) != 0;
	}

	public int getColorFromDamage(final int p_77620_1_) {
		return PotionHelper.func_77915_a(p_77620_1_, false);
	}

	@Override
	public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
		return renderPass > 0 ? 16777215 : getColorFromDamage(stack.getMetadata());
	}

	public boolean isEffectInstant(final int p_77833_1_) {
		final List var2 = this.getEffects(p_77833_1_);

		if (var2 != null && !var2.isEmpty()) {
			final Iterator var3 = var2.iterator();
			PotionEffect var4;

			do {
				if (!var3.hasNext()) {
					return false;
				}

				var4 = (PotionEffect) var3.next();
			}
			while (!Potion.potionTypes[var4.getPotionID()].isInstant());

			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		if (stack.getMetadata() == 0) {
			return StatCollector.translateToLocal("item.emptyPotion.name").trim();
		} else {
			String var2 = "";

			if (isSplash(stack.getMetadata())) {
				var2 = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
			}

			final List var3 = Items.potionitem.getEffects(stack);
			String var4;

			if (var3 != null && !var3.isEmpty()) {
				var4 = ((PotionEffect) var3.get(0)).getEffectName();
				var4 = var4 + ".postfix";
				return var2 + StatCollector.translateToLocal(var4).trim();
			} else {
				var4 = PotionHelper.func_77905_c(stack.getMetadata());
				return StatCollector.translateToLocal(var4).trim() + " " + super.getItemStackDisplayName(stack);
			}
		}
	}

	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 * 
	 * @param tooltip
	 *            All lines to display in the Item's tooltip. This is a List of
	 *            Strings.
	 * @param advanced
	 *            Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer playerIn, final List tooltip,
			final boolean advanced) {
		if (stack.getMetadata() != 0) {
			final List var5 = Items.potionitem.getEffects(stack);
			final HashMultimap var6 = HashMultimap.create();
			Iterator var16;

			if (var5 != null && !var5.isEmpty()) {
				var16 = var5.iterator();

				while (var16.hasNext()) {
					final PotionEffect var8 = (PotionEffect) var16.next();
					String var9 = StatCollector.translateToLocal(var8.getEffectName()).trim();
					final Potion var10 = Potion.potionTypes[var8.getPotionID()];
					final Map var11 = var10.func_111186_k();

					if (var11 != null && var11.size() > 0) {
						final Iterator var12 = var11.entrySet().iterator();

						while (var12.hasNext()) {
							final Entry var13 = (Entry) var12.next();
							final AttributeModifier var14 = (AttributeModifier) var13.getValue();
							final AttributeModifier var15 = new AttributeModifier(var14.getName(),
									var10.func_111183_a(var8.getAmplifier(), var14), var14.getOperation());
							var6.put(((IAttribute) var13.getKey()).getAttributeUnlocalizedName(), var15);
						}
					}

					if (var8.getAmplifier() > 0) {
						var9 = var9 + " "
								+ StatCollector.translateToLocal("potion.potency." + var8.getAmplifier()).trim();
					}

					if (var8.getDuration() > 20) {
						var9 = var9 + " (" + Potion.getDurationString(var8) + ")";
					}

					if (var10.isBadEffect()) {
						tooltip.add(EnumChatFormatting.RED + var9);
					} else {
						tooltip.add(EnumChatFormatting.GRAY + var9);
					}
				}
			} else {
				final String var7 = StatCollector.translateToLocal("potion.empty").trim();
				tooltip.add(EnumChatFormatting.GRAY + var7);
			}

			if (!var6.isEmpty()) {
				tooltip.add("");
				tooltip.add(
						EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
				var16 = var6.entries().iterator();

				while (var16.hasNext()) {
					final Entry var17 = (Entry) var16.next();
					final AttributeModifier var18 = (AttributeModifier) var17.getValue();
					final double var19 = var18.getAmount();
					double var20;

					if (var18.getOperation() != 1 && var18.getOperation() != 2) {
						var20 = var18.getAmount();
					} else {
						var20 = var18.getAmount() * 100.0D;
					}

					if (var19 > 0.0D) {
						tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted(
								"attribute.modifier.plus." + var18.getOperation(),
								new Object[] { ItemStack.DECIMALFORMAT.format(var20),
										StatCollector.translateToLocal("attribute.name." + (String) var17.getKey()) }));
					} else if (var19 < 0.0D) {
						var20 *= -1.0D;
						tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
								"attribute.modifier.take." + var18.getOperation(),
								new Object[] { ItemStack.DECIMALFORMAT.format(var20),
										StatCollector.translateToLocal("attribute.name." + (String) var17.getKey()) }));
					}
				}
			}
		}
	}

	@Override
	public boolean hasEffect(final ItemStack stack) {
		final List var2 = this.getEffects(stack);
		return var2 != null && !var2.isEmpty();
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
		super.getSubItems(itemIn, tab, subItems);
		int var5;

		if (field_77835_b.isEmpty()) {
			for (int var4 = 0; var4 <= 15; ++var4) {
				for (var5 = 0; var5 <= 1; ++var5) {
					int var6;

					if (var5 == 0) {
						var6 = var4 | 8192;
					} else {
						var6 = var4 | 16384;
					}

					for (int var7 = 0; var7 <= 2; ++var7) {
						int var8 = var6;

						if (var7 != 0) {
							if (var7 == 1) {
								var8 = var6 | 32;
							} else if (var7 == 2) {
								var8 = var6 | 64;
							}
						}

						final List var9 = PotionHelper.getPotionEffects(var8, false);

						if (var9 != null && !var9.isEmpty()) {
							field_77835_b.put(var9, var8);
						}
					}
				}
			}
		}
		field_77835_b.put(new ArrayList<>(), 16384);
		final Iterator var10 = field_77835_b.values().iterator();

		while (var10.hasNext()) {
			var5 = ((Integer) var10.next());
			subItems.add(new ItemStack(itemIn, 1, var5));
		}
	}
}
