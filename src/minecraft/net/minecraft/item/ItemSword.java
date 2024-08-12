package net.minecraft.item;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemSword extends Item {

public static final int EaZy = 1331;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final float field_150934_a;
	public final Item.ToolMaterial repairMaterial;
	// private static final String __OBFID = "http://https://fuckuskid00000072";

	public ItemSword(final Item.ToolMaterial p_i45356_1_) {
		repairMaterial = p_i45356_1_;
		maxStackSize = 1;
		setMaxDamage(p_i45356_1_.getMaxUses());
		setCreativeTab(CreativeTabs.tabCombat);
		field_150934_a = 4.0F + p_i45356_1_.getDamageVsEntity();
	}

	public float func_150931_i() {
		return repairMaterial.getDamageVsEntity();
	}

	@Override
	public float getStrVsBlock(final ItemStack stack, final Block p_150893_2_) {
		if (p_150893_2_ == Blocks.web) {
			return 15.0F;
		} else {
			final Material var3 = p_150893_2_.getMaterial();
			return var3 != Material.plants && var3 != Material.vine && var3 != Material.coral && var3 != Material.leaves
					&& var3 != Material.gourd ? 1.0F : 1.5F;
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the
	 * entry argument beside ev. They just raise the damage on the stack.
	 * 
	 * @param target
	 *            The Entity being hit
	 * @param attacker
	 *            the attacking entity
	 */
	@Override
	public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
		stack.damageItem(1, attacker);
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger
	 * the "Use Item" statistic.
	 */
	@Override
	public boolean onBlockDestroyed(final ItemStack stack, final World worldIn, final Block blockIn, final BlockPos pos,
			final EntityLivingBase playerIn) {
		if (blockIn.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(2, playerIn);
		}

		return true;
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@Override
	public boolean isFull3D() {
		return true;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return EnumAction.BLOCK;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 72000;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	@Override
	public boolean canHarvestBlock(final Block blockIn) {
		return blockIn == Blocks.web;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return repairMaterial.getEnchantability();
	}

	/**
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName() {
		return repairMaterial.toString();
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
		return repairMaterial.getBaseItemForRepair() == repair.getItem() ? true
				: super.getIsRepairable(toRepair, repair);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit
	 * damage.
	 */
	@Override
	public Multimap getItemAttributeModifiers() {
		final Multimap var1 = super.getItemAttributeModifiers();
		var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(itemModifierUUID, "Weapon modifier", field_150934_a, 0));
		return var1;
	}
}
