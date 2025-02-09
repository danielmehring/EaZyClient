package net.minecraft.entity.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2 {

public static final int EaZy = 1089;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_179478_e;
	private final EntityVillager field_179477_f;
	// private static final String __OBFID = "http://https://fuckuskid00002251";

	public EntityAIVillagerInteract(final EntityVillager p_i45886_1_) {
		super(p_i45886_1_, EntityVillager.class, 3.0F, 0.02F);
		field_179477_f = p_i45886_1_;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		super.startExecuting();

		if (field_179477_f.func_175555_cq() && closestEntity instanceof EntityVillager
				&& ((EntityVillager) closestEntity).func_175557_cr()) {
			field_179478_e = 10;
		} else {
			field_179478_e = 0;
		}
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		super.updateTask();

		if (field_179478_e > 0) {
			--field_179478_e;

			if (field_179478_e == 0) {
				final InventoryBasic var1 = field_179477_f.func_175551_co();

				for (int var2 = 0; var2 < var1.getSizeInventory(); ++var2) {
					final ItemStack var3 = var1.getStackInSlot(var2);
					ItemStack var4 = null;

					if (var3 != null) {
						final Item var5 = var3.getItem();
						int var6;

						if ((var5 == Items.bread || var5 == Items.potato || var5 == Items.carrot)
								&& var3.stackSize > 3) {
							var6 = var3.stackSize / 2;
							var3.stackSize -= var6;
							var4 = new ItemStack(var5, var6, var3.getMetadata());
						} else if (var5 == Items.wheat && var3.stackSize > 5) {
							var6 = var3.stackSize / 2 / 3 * 3;
							final int var7 = var6 / 3;
							var3.stackSize -= var6;
							var4 = new ItemStack(Items.bread, var7, 0);
						}

						if (var3.stackSize <= 0) {
							var1.setInventorySlotContents(var2, (ItemStack) null);
						}
					}

					if (var4 != null) {
						final double var11 = field_179477_f.posY - 0.30000001192092896D + field_179477_f.getEyeHeight();
						final EntityItem var12 = new EntityItem(field_179477_f.worldObj, field_179477_f.posX, var11,
								field_179477_f.posZ, var4);
						final float var8 = 0.3F;
						final float var9 = field_179477_f.rotationYawHead;
						final float var10 = field_179477_f.rotationPitch;
						var12.motionX = -MathHelper.sin(var9 / 180.0F * (float) Math.PI)
								* MathHelper.cos(var10 / 180.0F * (float) Math.PI) * var8;
						var12.motionZ = MathHelper.cos(var9 / 180.0F * (float) Math.PI)
								* MathHelper.cos(var10 / 180.0F * (float) Math.PI) * var8;
						var12.motionY = -MathHelper.sin(var10 / 180.0F * (float) Math.PI) * var8 + 0.1F;
						var12.setDefaultPickupDelay();
						field_179477_f.worldObj.spawnEntityInWorld(var12);
						break;
					}
				}
			}
		}
	}
}
