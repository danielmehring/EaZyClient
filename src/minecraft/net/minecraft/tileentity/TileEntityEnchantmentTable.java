package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IInteractionObject;

import java.util.Random;

public class TileEntityEnchantmentTable extends TileEntity implements IUpdatePlayerListBox, IInteractionObject {

public static final int EaZy = 1578;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public int tickCount;
	public float pageFlip;
	public float pageFlipPrev;
	public float field_145932_k;
	public float field_145929_l;
	public float bookSpread;
	public float bookSpreadPrev;
	public float bookRotation;
	public float bookRotationPrev;
	public float field_145924_q;
	private static Random field_145923_r = new Random();
	private String field_145922_s;
	// private static final String __OBFID = "http://https://fuckuskid00000354";

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (hasCustomName()) {
			compound.setString("CustomName", field_145922_s);
		}
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);

		if (compound.hasKey("CustomName", 8)) {
			field_145922_s = compound.getString("CustomName");
		}
	}

	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update() {
		bookSpreadPrev = bookSpread;
		bookRotationPrev = bookRotation;
		final EntityPlayer var1 = worldObj.getClosestPlayer(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
				3.0D);

		if (var1 != null) {
			final double var2 = var1.posX - (pos.getX() + 0.5F);
			final double var4 = var1.posZ - (pos.getZ() + 0.5F);
			field_145924_q = (float) Math.atan2(var4, var2);
			bookSpread += 0.1F;

			if (bookSpread < 0.5F || field_145923_r.nextInt(40) == 0) {
				final float var6 = field_145932_k;

				do {
					field_145932_k += field_145923_r.nextInt(4) - field_145923_r.nextInt(4);
				}
				while (var6 == field_145932_k);
			}
		} else {
			field_145924_q += 0.02F;
			bookSpread -= 0.1F;
		}

		while (bookRotation >= (float) Math.PI) {
			bookRotation -= (float) Math.PI * 2F;
		}

		while (bookRotation < -(float) Math.PI) {
			bookRotation += (float) Math.PI * 2F;
		}

		while (field_145924_q >= (float) Math.PI) {
			field_145924_q -= (float) Math.PI * 2F;
		}

		while (field_145924_q < -(float) Math.PI) {
			field_145924_q += (float) Math.PI * 2F;
		}

		float var7;

		for (var7 = field_145924_q - bookRotation; var7 >= (float) Math.PI; var7 -= (float) Math.PI * 2F) {
		}

		while (var7 < -(float) Math.PI) {
			var7 += (float) Math.PI * 2F;
		}

		bookRotation += var7 * 0.4F;
		bookSpread = MathHelper.clamp_float(bookSpread, 0.0F, 1.0F);
		++tickCount;
		pageFlipPrev = pageFlip;
		float var3 = (field_145932_k - pageFlip) * 0.4F;
		final float var8 = 0.2F;
		var3 = MathHelper.clamp_float(var3, -var8, var8);
		field_145929_l += (var3 - field_145929_l) * 0.9F;
		pageFlip += field_145929_l;
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return hasCustomName() ? field_145922_s : "container.enchant";
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return field_145922_s != null && field_145922_s.length() > 0;
	}

	public void func_145920_a(final String p_145920_1_) {
		field_145922_s = p_145920_1_;
	}

	@Override
	public IChatComponent getDisplayName() {
		return hasCustomName() ? new ChatComponentText(getName())
				: new ChatComponentTranslation(getName(), new Object[0]);
	}

	@Override
	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
		return new ContainerEnchantment(playerInventory, worldObj, pos);
	}

	@Override
	public String getGuiID() {
		return "minecraft:enchanting_table";
	}
}
