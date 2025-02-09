package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.google.common.base.Predicate;

public final class IEntitySelector {

public static final int EaZy = 968;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final Predicate selectAnything = new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001541";
		public boolean func_180131_a(final Entity p_180131_1_) {
			return p_180131_1_.isEntityAlive();
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_180131_a((Entity) p_apply_1_);
		}
	};
	public static final Predicate field_152785_b = new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001542";
		public boolean func_180130_a(final Entity p_180130_1_) {
			return p_180130_1_.isEntityAlive() && p_180130_1_.riddenByEntity == null
					&& p_180130_1_.ridingEntity == null;
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_180130_a((Entity) p_apply_1_);
		}
	};
	public static final Predicate selectInventories = new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001867";
		public boolean func_180102_a(final Entity p_180102_1_) {
			return p_180102_1_ instanceof IInventory && p_180102_1_.isEntityAlive();
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_180102_a((Entity) p_apply_1_);
		}
	};
	public static final Predicate field_180132_d = new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002256";
		public boolean func_180103_a(final Entity p_180103_1_) {
			return !(p_180103_1_ instanceof EntityPlayer) || !((EntityPlayer) p_180103_1_).isSpectatorMode();
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_180103_a((Entity) p_apply_1_);
		}
	};
	// private static final String __OBFID = "http://https://fuckuskid00002257";

	public static class ArmoredMob implements Predicate {
		private final ItemStack field_96567_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001543";

		public ArmoredMob(final ItemStack p_i1584_1_) {
			field_96567_c = p_i1584_1_;
		}

		public boolean func_180100_a(final Entity p_180100_1_) {
			if (!p_180100_1_.isEntityAlive()) {
				return false;
			} else if (!(p_180100_1_ instanceof EntityLivingBase)) {
				return false;
			} else {
				final EntityLivingBase var2 = (EntityLivingBase) p_180100_1_;
				return var2.getEquipmentInSlot(EntityLiving.getArmorPosition(field_96567_c)) != null ? false
						: var2 instanceof EntityLiving ? ((EntityLiving) var2).canPickUpLoot()
								: var2 instanceof EntityArmorStand ? true : var2 instanceof EntityPlayer;
			}
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_180100_a((Entity) p_apply_1_);
		}
	}
}
