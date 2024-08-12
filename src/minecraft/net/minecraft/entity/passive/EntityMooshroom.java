package net.minecraft.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityMooshroom extends EntityCow {

public static final int EaZy = 1180;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001645";

	public EntityMooshroom(final World worldIn) {
		super(worldIn);
		setSize(0.9F, 1.3F);
		field_175506_bl = Blocks.mycelium;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();

		if (var2 != null && var2.getItem() == Items.bowl && getGrowingAge() >= 0) {
			if (var2.stackSize == 1) {
				p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem,
						new ItemStack(Items.mushroom_stew));
				return true;
			}

			if (p_70085_1_.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew))
					&& !p_70085_1_.capabilities.isCreativeMode) {
				p_70085_1_.inventory.decrStackSize(p_70085_1_.inventory.currentItem, 1);
				return true;
			}
		}

		if (var2 != null && var2.getItem() == Items.shears && getGrowingAge() >= 0) {
			setDead();
			worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX, posY + height / 2.0F, posZ, 0.0D, 0.0D,
					0.0D, new int[0]);

			if (!worldObj.isRemote) {
				final EntityCow var3 = new EntityCow(worldObj);
				var3.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
				var3.setHealth(getHealth());
				var3.renderYawOffset = renderYawOffset;

				if (hasCustomName()) {
					var3.setCustomNameTag(getCustomNameTag());
				}

				worldObj.spawnEntityInWorld(var3);

				for (int var4 = 0; var4 < 5; ++var4) {
					worldObj.spawnEntityInWorld(
							new EntityItem(worldObj, posX, posY + height, posZ, new ItemStack(Blocks.red_mushroom)));
				}

				var2.damageItem(1, p_70085_1_);
				playSound("mob.sheep.shear", 1.0F, 1.0F);
			}

			return true;
		} else {
			return super.interact(p_70085_1_);
		}
	}

	@Override
	public EntityMooshroom createChild(final EntityAgeable p_90011_1_) {
		return new EntityMooshroom(worldObj);
	}

}
