package net.minecraft.item;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.block.BlockDispenser;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemArmor extends Item {

public static final int EaZy = 1268;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Holds the 'base' maxDamage that each armorType have. */
	private static final int[] maxDamageArray = new int[] { 11, 16, 15, 13 };
	public static final String[] EMPTY_SLOT_NAMES = new String[] { "minecraft:items/empty_armor_slot_helmet",
			"minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings",
			"minecraft:items/empty_armor_slot_boots" };
	private static final IBehaviorDispenseItem dispenserBehavior = new BehaviorDefaultDispenseItem() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001767";
		@Override
		protected ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
			final BlockPos var3 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
			final int var4 = var3.getX();
			final int var5 = var3.getY();
			final int var6 = var3.getZ();
			final AxisAlignedBB var7 = new AxisAlignedBB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
			final List var8 = source.getWorld().func_175647_a(EntityLivingBase.class, var7,
					Predicates.and(IEntitySelector.field_180132_d, new IEntitySelector.ArmoredMob(stack)));

			if (var8.size() > 0) {
				final EntityLivingBase var9 = (EntityLivingBase) var8.get(0);
				final int var10 = var9 instanceof EntityPlayer ? 1 : 0;
				final int var11 = EntityLiving.getArmorPosition(stack);
				final ItemStack var12 = stack.copy();
				var12.stackSize = 1;
				var9.setCurrentItemOrArmor(var11 - var10, var12);

				if (var9 instanceof EntityLiving) {
					((EntityLiving) var9).setEquipmentDropChance(var11, 2.0F);
				}

				--stack.stackSize;
				return stack;
			} else {
				return super.dispenseStack(source, stack);
			}
		}
	};

	/**
	 * Stores the armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots
	 */
	public final int armorType;

	/** Holds the amount of damage that the armor reduces at full durability. */
	public final int damageReduceAmount;

	/**
	 * Used on RenderPlayer to select the correspondent armor to be rendered on
	 * the player: 0 is cloth, 1 is chain, 2 is iron, 3 is diamond and 4 is
	 * gold.
	 */
	public final int renderIndex;

	/** The EnumArmorMaterial used for this ItemArmor */
	private final ItemArmor.ArmorMaterial material;
	// private static final String __OBFID = "http://https://fuckuskid00001766";

	public ItemArmor(final ItemArmor.ArmorMaterial p_i45325_1_, final int p_i45325_2_, final int p_i45325_3_) {
		material = p_i45325_1_;
		armorType = p_i45325_3_;
		renderIndex = p_i45325_2_;
		damageReduceAmount = p_i45325_1_.getDamageReductionAmount(p_i45325_3_);
		setMaxDamage(p_i45325_1_.getDurability(p_i45325_3_));
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
	}

	@Override
	public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
		if (renderPass > 0) {
			return 16777215;
		} else {
			int var3 = getColor(stack);

			if (var3 < 0) {
				var3 = 16777215;
			}

			return var3;
		}
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return material.getEnchantability();
	}

	/**
	 * Return the armor material for this armor item.
	 */
	public ItemArmor.ArmorMaterial getArmorMaterial() {
		return material;
	}

	/**
	 * Return whether the specified armor ItemStack has a color.
	 */
	public boolean hasColor(final ItemStack p_82816_1_) {
		return material != ItemArmor.ArmorMaterial.LEATHER ? false
				: !p_82816_1_.hasTagCompound() ? false
						: !p_82816_1_.getTagCompound().hasKey("display", 10) ? false
								: p_82816_1_.getTagCompound().getCompoundTag("display").hasKey("color", 3);
	}

	/**
	 * Return the color for the specified armor ItemStack.
	 */
	public int getColor(final ItemStack p_82814_1_) {
		if (material != ItemArmor.ArmorMaterial.LEATHER) {
			return -1;
		} else {
			final NBTTagCompound var2 = p_82814_1_.getTagCompound();

			if (var2 != null) {
				final NBTTagCompound var3 = var2.getCompoundTag("display");

				if (var3 != null && var3.hasKey("color", 3)) {
					return var3.getInteger("color");
				}
			}

			return 10511680;
		}
	}

	/**
	 * Remove the color from the specified armor ItemStack.
	 */
	public void removeColor(final ItemStack p_82815_1_) {
		if (material == ItemArmor.ArmorMaterial.LEATHER) {
			final NBTTagCompound var2 = p_82815_1_.getTagCompound();

			if (var2 != null) {
				final NBTTagCompound var3 = var2.getCompoundTag("display");

				if (var3.hasKey("color")) {
					var3.removeTag("color");
				}
			}
		}
	}

	public void func_82813_b(final ItemStack p_82813_1_, final int p_82813_2_) {
		if (material != ItemArmor.ArmorMaterial.LEATHER) {
			throw new UnsupportedOperationException("Can\'t dye non-leather!");
		} else {
			NBTTagCompound var3 = p_82813_1_.getTagCompound();

			if (var3 == null) {
				var3 = new NBTTagCompound();
				p_82813_1_.setTagCompound(var3);
			}

			final NBTTagCompound var4 = var3.getCompoundTag("display");

			if (!var3.hasKey("display", 10)) {
				var3.setTag("display", var4);
			}

			var4.setInteger("color", p_82813_2_);
		}
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 * 
	 * @param toRepair
	 *            The ItemStack to be repaired
	 * @param repair
	 *            The ItemStack that should repair this Item (leather for
	 *            leather armor, etc.)
	 */
	@Override
	public boolean getIsRepairable(final ItemStack toRepair, final ItemStack repair) {
		return material.getBaseItemForRepair() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		final int var4 = EntityLiving.getArmorPosition(itemStackIn) - 1;
		final ItemStack var5 = playerIn.getCurrentArmor(var4);

		if (var5 == null) {
			playerIn.setCurrentItemOrArmor(var4, itemStackIn.copy());
			itemStackIn.stackSize = 0;
		}

		return itemStackIn;
	}

	public static enum ArmorMaterial {
		LEATHER("LEATHER", 0, "leather", 5, new int[] { 1, 3, 2, 1 }, 15, 1), CHAIN("CHAIN", 1, "chainmail", 15,
				new int[] { 2, 5, 4, 1 }, 12, 3), IRON("IRON", 2, "iron", 15, new int[] { 2, 6, 5, 2 }, 9, 4), GOLD(
						"GOLD", 3, "gold", 7, new int[] { 2, 5, 3, 1 }, 25,
						2), DIAMOND("DIAMOND", 4, "diamond", 33, new int[] { 3, 8, 6, 3 }, 10, 5);
		private final String field_179243_f;
		private final int maxDamageFactor;
		private final int[] damageReductionAmountArray;
		private final int enchantability;

		private final int autoArmor;

		private ArmorMaterial(final String p_i45789_1_, final int p_i45789_2_, final String p_i45789_3_,
				final int p_i45789_4_, final int[] p_i45789_5_, final int p_i45789_6_, final int p_i45789_7_) {
			field_179243_f = p_i45789_3_;
			maxDamageFactor = p_i45789_4_;
			damageReductionAmountArray = p_i45789_5_;
			enchantability = p_i45789_6_;
			autoArmor = p_i45789_7_;
		}

		public int getDurability(final int p_78046_1_) {
			return ItemArmor.maxDamageArray[p_78046_1_] * maxDamageFactor;
		}

		public int getDamageReductionAmount(final int p_78044_1_) {
			return damageReductionAmountArray[p_78044_1_];
		}

		public int getAutoArmor() {
			return autoArmor;
		}

		public int getEnchantability() {
			return enchantability;
		}

		public Item getBaseItemForRepair() {
			return this == LEATHER ? Items.leather
					: this == CHAIN ? Items.iron_ingot
							: this == GOLD ? Items.gold_ingot
									: this == IRON ? Items.iron_ingot : this == DIAMOND ? Items.diamond : null;
		}

		public String func_179242_c() {
			return field_179243_f;
		}
	}
}
